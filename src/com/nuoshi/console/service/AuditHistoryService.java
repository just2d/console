package com.nuoshi.console.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.taofang.biz.domain.constants.AuditConstant;
import com.taofang.biz.local.AgentPhotoUrlUtil;
import com.taofang.biz.util.BizServiceHelper;
import com.nuoshi.console.common.Globals;
import com.nuoshi.console.dao.AuditHistoryDao;
import com.nuoshi.console.dao.RentDao;
import com.nuoshi.console.dao.ResaleDao;
import com.nuoshi.console.domain.agent.AgentMaster;
import com.nuoshi.console.domain.agent.HousePhoto;
import com.nuoshi.console.domain.auditHistory.AuditHistory;
import com.nuoshi.console.domain.auditHistory.AuditorInfo;
import com.nuoshi.console.domain.auditHistory.HistoryInfo;
import com.nuoshi.console.domain.auditHistory.NewSubAuditHistory;
import com.nuoshi.console.domain.auditHistory.ReauditHistory;
import com.nuoshi.console.domain.auditHistory.SubAuditHistory;
import com.nuoshi.console.domain.rent.RentInfo;
import com.nuoshi.console.domain.resale.ResaleInfo;
import com.nuoshi.console.domain.topic.QueryBean;
import com.nuoshi.console.domain.user.User;
import com.nuoshi.console.persistence.read.taofang.rent.RentReadMapper;
import com.nuoshi.console.persistence.read.taofang.resale.ResaleReadMapper;
import com.nuoshi.console.persistence.write.taofang.rent.RentWriteMapper;
import com.nuoshi.console.persistence.write.taofang.resale.ResaleWriteMapper;
import com.nuoshi.console.web.session.SessionUser;

@Service
public class AuditHistoryService extends BaseService {
	@Resource
	private AuditHistoryDao auditHistoryDao;
	@Resource
	private RentVerifyService rentVerifyService;
	@Resource
	private ResaleVerifyService resaleVerifyService;
	@Resource
	private RentQueryBeanBuilder rentQueryBeanBuilder;
	@Resource
	private ResaleQueryBeanBuilder resaleQueryBeanBuilder;
	@Resource
	private AgentMasterService agentMasterService;
	@Resource
	private RentReadMapper rentReadMapper;
	@Resource
	private ResaleReadMapper resaleReadMapper;
	@Resource
	private RentWriteMapper rentWriteMapper;
	@Resource
	private RentDao rentDao;
	@Resource
	private ResaleWriteMapper resaleWriteMapper;
	@Resource
	private ResaleDao resaleDao;
	
	private static final String comma = ",";
	private final String newAuditStringStartTime = "2012-04-25 00:00";
	private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:ss");
	private Date newAuditStartTime = null;
	
	public AuditHistoryService() throws Exception{
		super();
		newAuditStartTime = simpleDateFormat.parse(newAuditStringStartTime);
	}
	
	/**
	 * 记录复审操作日志
	 * @param historyInfo
	 * @param result
	 * @param user
	 * @author wangjh
	 * Dec 23, 2011 3:18:13 PM
	 */
	public void recordReauditLog(HistoryInfo historyInfo,String result, User user) {
		if(StringUtils.isBlank(result)){
			log.error("复审记录操作日志时传入的审核结果为空！");
			return ;
		}
		//审核结果
		Integer resultInt=Integer.valueOf(result);
		ReauditHistory reauditHistory=new ReauditHistory();

		Integer historyId=historyInfo.getAuditId();
		Integer subId=historyInfo.getId();
		Integer reauditUserId=null;
		if(user!=null){
			
			/**
			 * 复审人id
			 */
			reauditUserId =user.getId();
		}
		Integer auditId=historyInfo.getDealerId();

		/**
		 * 复审时间
		 */
		Date nowDate=new Date();
		Date reauditTime=nowDate;
		/**
		 * 复审结果
		 */
		Integer auditResult=resultInt;
		
		reauditHistory.setAuditId(auditId);
		reauditHistory.setHistoryId(historyId);
		if(reauditUserId!=null){
			reauditHistory.setReauditId(reauditUserId);
		}
		reauditHistory.setReauditTime(reauditTime);
		reauditHistory.setResult(auditResult);
		reauditHistory.setSubHistoryId(subId);
		this.saveReauditHistory(reauditHistory);
	}
	
	/**
	 * 先判断是否有此id的复审记录,如果有,就删除,没有就添加
	 * @param reauditHistory
	 * @date 2011-12-26上午11:37:05
	 * @author dongyj
	 */
	private void saveReauditHistory(ReauditHistory reauditHistory) {
		Integer count = auditHistoryDao.getReauditHistoryCount(reauditHistory.getSubHistoryId());
		if (null == count || count == 0) {
			auditHistoryDao.saveReauditHistory(reauditHistory);
		} else if (count > 0) {
			auditHistoryDao.deleteReauditHistory(reauditHistory.getSubHistoryId());
		}
	}

	/**
	 * 通过审核子表id查询审核历史信息
	 * @param subHistoryId
	 * @return
	 * @author wangjh
	 * Dec 23, 2011 6:10:39 PM
	 * @throws Exception 
	 */
	public HistoryInfo selectHistoryInfoById(Integer subHistoryId) throws Exception{
		HistoryInfo historyInfo=auditHistoryDao.selectHistoryInfoById(subHistoryId);
		if(historyInfo==null){
			return historyInfo;
		}
		Integer houseType=null;
		houseType=historyInfo.getHouseType();
		if(houseType==null){
			log.error("房源类型为空！");
			return historyInfo;
		}
		//基本信息不用去查询
		if(Globals.AUDIT_HISTORY_BASEINFO==historyInfo.getAuditStep()){
			return historyInfo;
		}
		//图片需要去查询url和描述
		HistoryInfo historyInfoPhoto=null;
		if(Globals.HOUSE_TYPE_RENT==historyInfo.getHouseType().intValue()){
			historyInfoPhoto=rentVerifyService.getPhotoInfoHistory(historyInfo);
		}
		if(Globals.HOUSE_TYPE_RESALE==historyInfo.getHouseType().intValue()){
			historyInfoPhoto=resaleVerifyService.getPhotoInfoHistory(historyInfo);
		}
		if(Globals.AUDIT_HISTORY_AGENT_HEAD_IMG==historyInfo.getAuditStep().intValue()||Globals.AUDIT_HISTORY_AGENT_IDCARD_IMG==historyInfo.getAuditStep().intValue()){
			historyInfoPhoto = auditHistoryDao.getPhotoInfoHistoryForAgent(historyInfo);
		}
		if(historyInfoPhoto==null){
			log.error("图片历史获取为空！");
			return historyInfo;
		}

		if (Globals.AUDIT_HISTORY_INDOOR == historyInfo.getAuditStep()) {
			String describe = historyInfoPhoto.getPhotoDescribe();
			List<QueryBean> dists = null;
			if (Globals.HOUSE_TYPE_RESALE == historyInfo.getHouseType()) {
				dists = resaleQueryBeanBuilder.getDecorations();
			}
			if (Globals.HOUSE_TYPE_RENT == historyInfo.getHouseType()) {
				dists = rentQueryBeanBuilder.getDecorations();
			}
			Map<String, String> map = new HashMap<String, String>();
			if (CollectionUtils.isNotEmpty(dists)) {
				for (QueryBean queryBean : dists) {
					map.put("" + queryBean.getValMax(), queryBean.getValue());
				}
			}
			historyInfoPhoto.setPhotoDescribe(map.get(describe));
		}
		historyInfo.setPhotoDescribe(historyInfoPhoto.getPhotoDescribe());
		return historyInfo;
		
	}
	public void saveAuditHistory(AuditHistory auditHistory) {
		auditHistoryDao.saveAuditHistory(auditHistory);
	}

	public void updateAuditHistory(AuditHistory auditHistory) {
		auditHistoryDao.updateAuditHistory(auditHistory);
	}

	/**
	 * 通过房源id获取subAuditHistory信息
	 * 
	 * @param houseIdList
	 * @param houseType
	 * @return
	 * @author wangjh Dec 22, 2011 10:59:44 AM
	 */
	public List<SubAuditHistory> getHistoryInfoForBaseInfo(List<Integer> houseIdList, Integer houseType, User user) {
		List<SubAuditHistory> list = null;
		if (CollectionUtils.isEmpty(houseIdList)) {
			log.error("通过房源id在房源中查询子表审核历史基本信息失败! 传入的房源id为空");

		}
		if (Globals.HOUSE_TYPE_RENT == houseType) {
			// 获取审核记录信息
			list = rentVerifyService.getSubHistoryInfo(houseIdList);
			log.info("获取出租房基本信息审核详细记录信息");
		}
		if (Globals.HOUSE_TYPE_RESALE == houseType) {
			// 获取审核记录信息
			list = resaleVerifyService.getSubHistoryInfo(houseIdList);
			log.info("获取二手房基本信息审核详细记录信息");
		}
		for (SubAuditHistory subHistory : list) {
			if (user != null) {
				subHistory.setDealerId(user.getId());
				subHistory.setDealerName(user.getChnName());
			}
			subHistory.setAuditStep(Globals.AUDIT_HISTORY_BASEINFO);
		}
		if (CollectionUtils.isEmpty(list)) {
			log.error("通过房源id在房源中查询子表审核历史基本信息失败!根据房源id查询历史信息为空。");
		}
		return list;
	}

	/**
	 * 批量记录审核历史的结果
	 * 
	 * @param auditHistoryList
	 *            审核历史
	 * @param result
	 *            审核结果
	 * @author wangjh Dec 20, 2011 5:05:04 PM
	 */
	public void recordAuditHistoryResult(List<AuditHistory> auditHistoryList, Integer result, Date dates) {
		auditHistoryDao.updateAuditHistoryForResult(auditHistoryList, result, dates);

	}
	

	/**
	 * 批量获取最后的审核主表记录
	 * 
	 * @param houseIdList
	 *            房源id
	 * @param houseType
	 *            房源类型
	 * @return
	 * @author wangjh Dec 20, 2011 5:06:05 PM
	 */
	public List<AuditHistory> getLastRecordByHouseIds(List<Integer> houseIdList, Integer houseType) {
		return auditHistoryDao.getLastRecordByHouseIds(houseIdList, houseType);
	}

	/**
	 * 记录审核结果（通过打回都可调用）
	 * 
	 * @param completeHouseIdList
	 * @param auditResult
	 * @author wangjh Dec 21, 2011 10:17:08 AM
	 */
	public void recordLog(List<Integer> completeHouseIdList, Integer auditResult, Integer auditType) {
		// 构建审核历史对象
		try {
			// 判断房源是否存在审核历史主表记录,不存在则新建
			this.foundHistory(completeHouseIdList, auditType);
			List<AuditHistory> auditHistoryList = this.getLastRecordByHouseIds(completeHouseIdList, auditType);
			// 审核时间
			Date nowDate = new Date();
			// 批量记录审核历史的结果
			this.recordAuditHistoryResult(auditHistoryList, auditResult, nowDate);
		} catch (Exception e) {
			log.error("保存审核历史审核结果出错！", e);
		}

	}

	public void foundHistory(List<Integer> completeHouseIdList, Integer auditType) {
		// 判断房源是否存在审核历史主表记录
		List<Integer> noRecordIdList = null;
		noRecordIdList = this.getNotRecordIds(completeHouseIdList, auditType);
		if (CollectionUtils.isNotEmpty(noRecordIdList)) {
			
			// 新建审核历史记录
			List<AuditHistory> historyList = getHistoryInfoFrom(noRecordIdList, auditType);
			if (CollectionUtils.isEmpty(historyList)) {
				System.out.println("AuditHistoryService:foundHistory-noRecordIdList.size() = "+noRecordIdList.size());
				for(Integer idTemp : noRecordIdList){
					System.out.println("AuditHistoryService:foundHistory-noRecordIdList = "+idTemp);
				}
				throw new RuntimeException("获取没有审核历史记录的房源信息出错!");
			}
			// 新建历史记录
			for (AuditHistory history : historyList) {
				this.saveAuditHistory(history);
			}
		}
	}
	
	

	public List<AuditHistory> getHistoryInfoFrom(List<Integer> idList, Integer type) {
		List<AuditHistory> list = null;
		if (CollectionUtils.isEmpty(idList)) {
			log.error("通过id查询审核历史基本信息失败! 传入的id为空");

		}
		if (Globals.HOUSE_TYPE_RENT == type) {
			list = rentVerifyService.getHistoryInfo(idList);

		}
		if (Globals.HOUSE_TYPE_RESALE == type) {
			list = resaleVerifyService.getHistoryInfo(idList);
		}
		if (Globals.AGENT_TYPE == type) {
			list = agentMasterService.getHistoryInfo(idList);
		}
		if (CollectionUtils.isEmpty(list)) {
			log.error("通过id查询审核历史基本信息失败!根据id查询历史信息为空。");
		}
		return list;

	}

	/**
	 * 获取没有审核记录的房源id
	 * 
	 * @param auditHistoryList
	 * @return
	 * @author wangjh Dec 21, 2011 1:42:04 PM
	 */
	@SuppressWarnings("unchecked")
	public List<Integer> getNotRecordIds(List<Integer> idList, Integer type) {
		List<Integer> hasRecordIds = auditHistoryDao.getHasRecordIds(idList, type);
		// 去重
		List<Integer> allId = new ArrayList<Integer>(new HashSet<Integer>(idList));
		return new ArrayList<Integer>(CollectionUtils.subtract(allId, hasRecordIds));
	}

	/**
	 * @param subAuditHistories
	 * @date 2011-12-21下午7:22:17
	 * @author dongyj
	 * @List形式插入
	 */
	public void insertSubList(List<SubAuditHistory> subAuditHistories) {
		for (SubAuditHistory subAuditHistory : subAuditHistories) {
			this.insertSub(subAuditHistory);
		}
	}

	/**
	 * @param subAuditHistory
	 * @date 2011-12-21下午7:22:11
	 * @author dongyj
	 * @单条插入
	 */
	public void insertSub(SubAuditHistory subAuditHistory) {
		auditHistoryDao.insertSub(subAuditHistory);
	}

	/**
	 * 添加审核记录字表记录
	 * 
	 * @param houseIdIntList
	 *            所有房源id
	 * @param photoIdList
	 *            所有照片id
	 * @param house_type
	 *            出租or二手
	 * @param type
	 *            图片类型
	 * @date 2011-12-21下午7:25:09
	 * @author dongyj
	 * @param blockList
	 *            违规名单
	 * @param sessionUser
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void addPhotoAuditHistory(List<Integer> houseIdIntList, String blockList, List<Integer> photoIdList, Integer house_type, Integer type, SessionUser sessionUser)
			throws Exception {
		/**
		 * 添加审核记录
		 */
		//去除打回原因
		String sign="#_#";
		String[] blockListEver = blockList.split(",");
		String blockListRecord="";
		Map<String,String> idAndReasonMap=new HashMap<String,String>();
		for (int i = 0; i < blockListEver.length; i++) {
			String[] lists=blockListEver[i].split(sign);
			if (blockList.length() > 1) {
				String[] strArr=lists[0].split("_");
				String key=strArr[0]+"_"+strArr[1];
				idAndReasonMap.put(key, lists[1]);
			}
			//去掉打回原因后的字符串
			blockListRecord+=lists[0]+",";
		}
		// 查找审核主表
		this.foundHistory(houseIdIntList, house_type);
		// 所有rent_photo信息
		List<SubAuditHistory> allSubAuditHistories = null;

		// 查找此房源id下所有此类型的图片
		if (house_type.intValue() == Globals.HOUSE_TYPE_RENT) {
			// auditId 暂存 houseid
			allSubAuditHistories = rentVerifyService.getRentPhotosByHouseListAndType(houseIdIntList, photoIdList, type);
		} else if (house_type.intValue() == Globals.HOUSE_TYPE_RESALE) {
			allSubAuditHistories = resaleVerifyService.getResalePhotosByHouseListAndType(houseIdIntList, photoIdList, type);
		}
		if(allSubAuditHistories==null ){
			System.out.println("AuditHistoryService:addPhotoAuditHistory:allSubAuditHistories is null"+house_type+","+type+","+sessionUser);
		}else{
			System.out.println("AuditHistoryService:addPhotoAuditHistory:allSubAuditHistories size is ="+allSubAuditHistories.size()+","+Arrays.asList(houseIdIntList).toString()+",photoIdList="+Arrays.asList(photoIdList).toString());
		}
		// 查找or插入主表记录
		List<AuditHistory> auditHistories = this.getLastRecordByHouseIds(houseIdIntList, house_type);
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		for (AuditHistory auditHistory : auditHistories) {
			map.put(auditHistory.getHouseId(), auditHistory.getId());
		}
		if (map.size() < 1) {
			throw new Exception("没有取得任何审核主表数据@AuditHistoryService!addPhotoAuditHistory");
		}
		List<HousePhoto> housePhotos = null;
		for (SubAuditHistory subAuditHistory : allSubAuditHistories) {
					if (house_type.intValue() == Globals.HOUSE_TYPE_RENT) {
						housePhotos = rentVerifyService.getAllHousePhotoByType(subAuditHistory.getAuditId(), subAuditHistory.getPhotoId(), type, subAuditHistory.getInalbum());
					} else if (house_type.intValue() == Globals.HOUSE_TYPE_RESALE) {
						housePhotos = resaleVerifyService.getAllHousePhotoByType(subAuditHistory.getAuditId(), subAuditHistory.getPhotoId(), type, subAuditHistory.getInalbum());
					}
						if(housePhotos==null ){
							System.out.println("AuditHistoryService:addPhotoAuditHistory:housePhotos is null");
						}else{
							System.out.println("AuditHistoryService:addPhotoAuditHistory:housePhotossize is ="+housePhotos.size());
						}
						if (null != housePhotos && housePhotos.size() > 0) {
							// 暂存mURL
							subAuditHistory.setPhotoUrl(housePhotos.get(0).getmURL());
						}		
		}
		// 得到所有违规房源photo记录
		if (blockListRecord.length() > 1) {
			if (blockListRecord.endsWith(",")) {
				blockListRecord = blockListRecord.substring(0, blockListRecord.length() - 1);
			}
			String[] blockListArray = blockListRecord.split(",");
			for (int i = 0; i < blockListArray.length; i++) {
				String[] hid_pid_url = blockListArray[i].split("_");
				Integer houseid = Integer.parseInt(hid_pid_url[0]);
				String photoid = hid_pid_url[1];
				for (int j = 0; j < allSubAuditHistories.size(); j++) {
					SubAuditHistory subAuditHistory = allSubAuditHistories.get(j);
					if (subAuditHistory.getAuditId() == houseid.intValue() && subAuditHistory.getPhotoId().intValue() == Integer.parseInt(photoid)) {
						SubAuditHistory history=allSubAuditHistories.get(j);
						String reasons="";
						if(idAndReasonMap!=null&&idAndReasonMap.size()>0){
							reasons=idAndReasonMap.get(history.getHouseId()+"_"+history.getPhotoId());
						}
						if(StringUtils.isNotBlank(reasons)){
							history.setRejectReason(reasons);
						}
					}
				}
			}
		}
		
		// 插入记录
		consistAndInsertHstry(allSubAuditHistories,house_type, type, map, sessionUser);
	}

	/**
	 * 组成List然后插入审核子表
	 * 
	 * @param allSubAuditHistories
	 * @param type
	 * @date 2011-12-21下午7:32:41
	 * @author dongyj
	 * @param map
	 * @param auditResult
	 * @param sessionUser
	 * @throws Exception
	 */
	private void consistAndInsertHstry(List<SubAuditHistory> subAuditHistories,Integer house_type, Integer type, Map<Integer, Integer> map, SessionUser sessionUser)
 throws Exception {
		if (null == subAuditHistories || null == type || null == map) {
			throw new Exception();
			//return;
		}

		/**
		 * 完成新一代日志记录
		 */
		StringBuffer photo_ids = new StringBuffer();
		StringBuffer audit_ids = new StringBuffer();
		StringBuffer audit_results = new StringBuffer();
		List<Integer> validHouseId = new ArrayList<Integer>();
		int photoCount = 0;
		for (SubAuditHistory subAuditHistory : subAuditHistories) {
			if (!validHouseId.contains(subAuditHistory.getAuditId().intValue())) {
				validHouseId.add(subAuditHistory.getAuditId());
			}
			photo_ids.append(subAuditHistory.getPhotoId());
			photoCount++;
			photo_ids.append(comma);
			// subAuditHistory.getAuditId()暂存的house_id的key
			audit_ids.append(map.get(subAuditHistory.getAuditId()));
			audit_ids.append(comma);
			audit_results.append((subAuditHistory.getRejectReason() == null || subAuditHistory.getRejectReason().equals("")) ? Globals.AUDIT_RESULT_PASS
					: Globals.AUDIT_RESULT_REJECT);
			audit_results.append(comma);
		}
		if (photo_ids.length() < 2 || audit_ids.length() < 2 || audit_results.length() < 2) {
			System.out.println("AuditHistoryService:consistAndInsertHstry:photo_ids="+photo_ids+",audit_ids="+audit_ids+",audit_results="+audit_results);
			throw new Exception();
		}
		photo_ids.deleteCharAt(photo_ids.length() - 1);
		audit_ids.deleteCharAt(audit_ids.length() - 1);
		audit_results.deleteCharAt(audit_results.length() - 1);
		int audit_step = type;
		Date audit_time = new Date();
		String dealer_name = sessionUser.getUser().getChnName();
		int dealer_id = sessionUser.getUser().getId();
		NewSubAuditHistory newSubAuditHistory = new NewSubAuditHistory();
		newSubAuditHistory.setAudit_ids(audit_ids.toString());
		newSubAuditHistory.setAudit_results(audit_results.toString());
		newSubAuditHistory.setAudit_step(audit_step);
		newSubAuditHistory.setAudit_time(audit_time);
		newSubAuditHistory.setDealer_id(dealer_id);
		newSubAuditHistory.setDealer_name(dealer_name);
		newSubAuditHistory.setPhoto_ids(photo_ids.toString());
		newSubAuditHistory.setPhotoCount(photoCount);
		newSubAuditHistory.setHouse_type(house_type);
		// 返回id用来更新audit_history表中关联字表的主键的字段sub_ids
		auditHistoryDao.insertNewSubAuditHistory(newSubAuditHistory);

		List<String> ids = new ArrayList<String>();
		for (Integer id : validHouseId) {
			Integer value = map.get(id);
			if (value != null && value.intValue() >= 0) {
				ids.add("" + value);
			}
		}
		// 更新audit_history
		if (CollectionUtils.isEmpty(ids)) {
			return;
		}
		auditHistoryDao.updateAuditHistoryAtSubIds(newSubAuditHistory.getId() + ",", ids, house_type);
	}

	/**
	 * 得到审核记录列表
	 * 
	 * @return
	 * @date 2011-12-22上午10:30:10
	 * @author dongyj
	 * @param params
	 *            参数
	 */
	public List<SubAuditHistory> getSubListByPage(HashMap<String, Object> params) {
		List<SubAuditHistory> subAuditHistories = new ArrayList<SubAuditHistory>();
		subAuditHistories = auditHistoryDao.getSubListByPage(params);
		for (SubAuditHistory subAuditHistory : subAuditHistories) {
			if (null != subAuditHistory.getReResult()) {
				subAuditHistory.setAuditResult(subAuditHistory.getReResult());
			}
		}
		return subAuditHistories;
	}
	/**
	 * 得到审核记录historyInfo列表
	 * 
	 * @return
	 * @date 2011-12-22上午10:30:10
	 * @author dongyj
	 * @param params
	 *            参数
	 */
	public List<HistoryInfo> getHistoryInfoListByPage(HashMap<String, Object> params) {
		List<HistoryInfo> historyInfos = new ArrayList<HistoryInfo>();
		historyInfos = auditHistoryDao.getHistoryInfoListByPage(params);
		for (HistoryInfo historyInfo : historyInfos) {
			if (null != historyInfo.getReResult()) {
				historyInfo.setAuditResult(historyInfo.getReResult());
			}
		}
		return historyInfos;
	}

	/**
	 * 得到页面查询参数,组成查询条件
	 * 
	 * @param conditions
	 * @param houseid
	 * @param authorOrId
	 * @param startTime
	 * @param sHour
	 * @param endTime
	 * @param eHour
	 * @param auditResult
	 * @date 2011-12-22下午4:55:01
	 * @param model
	 * @param params
	 * @param type 查询类型
	 * @param per 是否个人查询.
	 * @param sessionUserId 登陆用户
	 * @param userId 被查看的个人用户id
	 * @param dealerName 处理人名称
	 * @return 0:全部用户,1:个人用户
	 * @author dongyj
	 */
	public void consistQuery(Model model, HashMap<String, Object> params, StringBuffer conditions, String houseid, String authorOrId, String startTime, String sHour,
			String endTime, String eHour, String auditResult, String type, String per, int sessionUserId, String userId, String dealerName) {
		
		// 房屋ID
		if (StringUtils.isNotBlank(houseid)) {
			houseid = houseid.trim();
			conditions.append(" AND audit_history.house_id = " + houseid);
			model.addAttribute("houseid", houseid);
		}
		
		// 房源拥有者ID或者姓名
		if (StringUtils.isNotBlank(authorOrId)) {
			authorOrId = authorOrId.trim();
			// 全部是数字
			String regex = "^-?\\d+$";
			if (authorOrId.matches(regex)) {
				conditions.append(" AND (audit_history.author_id = " + authorOrId + " OR audit_history.author_name='" + authorOrId + "') ");
			} else {
				conditions.append(" AND audit_history.author_name='" + authorOrId + "' ");
			}
			model.addAttribute("authorOrId", authorOrId);
		}
		if (StringUtils.isNotBlank(startTime)) {
			startTime = startTime.trim();
			conditions.append(" AND sub_audit_history.audit_time > '" + startTime + " " + sHour + ":00'");
			model.addAttribute("startTime", startTime);
		}
		if (StringUtils.isNotBlank(endTime)) {
			endTime = endTime.trim();
			conditions.append(" AND sub_audit_history.audit_time < '" + endTime + " " + eHour + ":00'");
			model.addAttribute("endTime", endTime);
		}
		
		// 审核结果过滤
		if (StringUtils.isNotBlank(auditResult)) {
			auditResult = auditResult.trim();
			if (!auditResult.equals("0")) {
				conditions.append(" AND sub_audit_history.audit_result = " + auditResult);
				model.addAttribute("auditResult", auditResult);
			}
		}
		
		// 基本信息:1   图片:2
		if(StringUtils.isNotBlank(type)){
			if (Integer.parseInt(type) == 1) {// 房源基本信息审核
				conditions.append(" AND sub_audit_history.audit_step = " + Globals.AUDIT_HISTORY_BASEINFO);
			} else if (Integer.parseInt(type) == 2) {// 房源图片审核
				conditions.append(" AND sub_audit_history.audit_step < " + Globals.AUDIT_HISTORY_BASEINFO);
			} else if (Integer.parseInt(type) == 3) {// 经纪人图片审核
				conditions.append(" AND ( sub_audit_history.audit_step = " + Globals.AUDIT_HISTORY_AGENT_HEAD_IMG + " OR sub_audit_history.audit_step = " + Globals.AUDIT_HISTORY_AGENT_IDCARD_IMG +") ");
			}
			model.addAttribute("type", type);
		}
		
		// admin审查个人用户审核记录
		if(StringUtils.isNotBlank(per)){
			if("1".equals(per)){
				conditions.append(" AND sub_audit_history.dealer_id = " + sessionUserId);
			}
		}
		
		// 用户自己查看自己记录
		if (StringUtils.isNotBlank(userId)) {
			conditions.append(" AND sub_audit_history.dealer_id = " + Integer.parseInt(userId));
			model.addAttribute("userId", userId);
		}
		
		// 处理人名称
		if (StringUtils.isNotBlank(dealerName)) {
			conditions.append(" AND sub_audit_history.dealer_name = '" + dealerName + "' ");
			model.addAttribute("dealerName", dealerName);
		}
		model.addAttribute("sHour", sHour);
		model.addAttribute("eHour", eHour);
		params.put("conditions", conditions);
	}

	/**
	 * 处理过的任务个数
	 * @param auditUserId
	 * @param beginTime
	 * @param endTime
	 * @author wangjh
	 * Dec 27, 2011 11:31:08 AM
	 */
	public Integer processedAuditCount(Integer auditStep,Integer auditUserId,Integer houseType, Date beginTime,
			Date endTime) {
		if(beginTime!=null&&endTime!=null){
			if(beginTime.getTime()>endTime.getTime()){
				log.error("查询处理任务个数的开始时间大于了结束时间！返回结果为0条数据。");
				return 0;
			}
		}
		if (auditStep != Globals.AUDIT_HISTORY_LAYOUT && auditStep != Globals.AUDIT_HISTORY_INDOOR && auditStep != Globals.AUDIT_HISTORY_COMMUNITY && auditStep != Globals.AUDIT_HISTORY_BASEINFO) {
			return auditHistoryDao.processedAuditCount(auditStep, auditUserId, houseType, beginTime, endTime);
		} else {
			// 新图片记录方式统计数量
			int auditstep=0;
			switch (auditStep) {
			case 1:
				auditstep=2;
				break;
			case 2:
				auditstep=3;
				break;
			case 3:
				auditstep=4;
				break;
			case 4:
				auditstep=1;
				break;
			}
			int housetype=0;
			switch (houseType) {
			case 1:
				housetype=2;
				break;

			case 2:
				housetype=1;
				break;
			}
			List<Integer> countIntegers = auditHistoryDao.getNewPassedPhotoCount(auditstep,auditUserId,housetype,beginTime,endTime);
			int result = 0;
			if(CollectionUtils.isNotEmpty(countIntegers)){
				for (Integer integer : countIntegers) {
					result += integer;
				}
			}
			return result;
		}

	}

	public Integer badHouseAuditNumber(Integer auditStep,Integer requesterId, Integer houseType) {
		return auditHistoryDao.badHouseAuditNumber(auditStep,requesterId,houseType);
	}

	public List<AuditorInfo> getTotalBadRateByUsers(List<User> users,
			Date beginTime, Date endTime) {
		return auditHistoryDao.getTotalBadRateByUsers(users,beginTime, endTime);
	}
	public List<AuditorInfo> getNewBadRateByUsers(List<User> users,
			Date beginTime, Date endTime) {
		return auditHistoryDao.getNewBadRateByUsers(users,beginTime, endTime);
	}
	public List<AuditorInfo> getPhotoBadRateByUsers(List<User> users,
			Date beginTime, Date endTime) {
		return auditHistoryDao.getPhotoBadRateByUsers(users,beginTime, endTime);
	}

	public List<HistoryInfo> getErrorAuditByUser(int userId, String beginTime, String endTime) {
		//TODO 
		List<HistoryInfo> list = new ArrayList<HistoryInfo>();
		try {
			if(StringUtils.isNotBlank(endTime)){
				endTime += " 23:59:59";
			}
			list = auditHistoryDao.getErrorAuditByUser(userId, beginTime, endTime);
			List<HistoryInfo> listPhoto = auditHistoryDao.getErrorAuditPhotoByUser(userId, beginTime, endTime);
			if(listPhoto!=null && listPhoto.size()>0){
				list.addAll(listPhoto);
			}
			
			List<Integer> rentList = new ArrayList<Integer>();
			List<Integer> resaleList = new ArrayList<Integer>();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			if(list!=null && list.size()>0){
				//取根据house_type和re_result(拒绝) 取拒绝原因 invalid_rent
				for(HistoryInfo obj : list){
					Integer houseType = obj.getHouseType();
					if(houseType == 1){
						this.setRentPhotos(obj);
						rentList.add(obj.getHouseId());
					}else if(houseType == 2){
						this.setResalePhotos(obj);
						resaleList.add(obj.getHouseId());
					}else{
						//TODO 经纪人
						Integer agentId = obj.getHouseId();
						AgentMaster agentObj = agentMasterService.selectAgentMasterById(agentId);
						
						agentObj.setMsgIdcard(AgentPhotoUrlUtil.getAgentIDCardPhotoFullUrl(agentObj.getCityId(), agentObj.getAgentId())+"?"+new Date().getTime());
						agentObj.setMsgHead(AgentPhotoUrlUtil.getPendingAgentHeadPortraitFullUrlLarge(agentObj.getCityId(), agentObj.getAgentId())+"?"+new Date().getTime());
						agentObj.setMsgNamecard(AgentPhotoUrlUtil.getAgentBusinessCardPhotoFullUrl(agentObj.getCityId(), agentObj.getAgentId())+"?"+new Date().getTime());
						obj.setHouseObj(agentObj);
						System.out.println("<><><><><><><> "+agentObj.getName());
					}
					obj.setAuditTimeStr(sdf.format(obj.getAuditTime()));
				}
				//获取房源的基本信息描述
				Map<String,Object> mapT = new HashMap<String,Object>();
				if(resaleList.size()>0){
					List<ResaleInfo>  resaleResult = resaleReadMapper.selectResaleInfoByIds(resaleList);
					if(resaleResult!=null && resaleResult.size()>0){
						for(ResaleInfo ri : resaleResult){
							mapT.put(ri.getId()+"_2",ri);
						}
					}
				}
				if(rentList.size()>0){
					List<RentInfo>  rentResult = rentReadMapper.selectRentInfoByIds(rentList);
					if(rentResult!=null && rentResult.size()>0){
						for(RentInfo ri : rentResult){
							mapT.put(ri.getId()+"_1", ri);
						}
					}
				}
				for(HistoryInfo obj : list){
					Integer houseType = obj.getHouseType();
					if(houseType == 1){
						obj.setHouseObj((RentInfo)mapT.get(obj.getHouseId()+"_1"));
					}else if(houseType == 2){
						obj.setHouseObj((ResaleInfo)mapT.get(obj.getHouseId()+"_2"));
					}
				}
				
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	private List<HousePhoto> getHousePhotoByResaleId(int rentId){
		List<HousePhoto> housePhotoList = new ArrayList<HousePhoto>();
		List<com.taofang.biz.domain.house.HousePhoto> bphotoList = BizServiceHelper.getHousePhotoService().getResalePhotos(rentId);
		if(CollectionUtils.isNotEmpty(bphotoList)){
			for(com.taofang.biz.domain.house.HousePhoto bphoto: bphotoList ){
				HousePhoto housePhoto = new HousePhoto();
				
				housePhoto.setCategory(bphoto.getCategory()/100+1+"");
				housePhoto.setInalbum(0);
				housePhoto.setInnertype(0);
				housePhoto.setCover(bphoto.getCoverFlag()=="Y"?1:0);
				housePhoto.setPhotoid(bphoto.getId()+"");
				housePhoto.setlURL(bphoto.getResaleLargePhotoUrl());
				housePhoto.setmURL(bphoto.getResaleMediumPhotoUrl());
				housePhoto.setsURL(bphoto.getResaleSmallPhotoUrl());
				housePhotoList.add(housePhoto);
			}
		}
		return housePhotoList;
	}

	
	private void setResalePhotos(HistoryInfo hq){		
		int id = hq.getHouseId();
		List<HousePhoto> housePhotoes = getHousePhotoByResaleId(id);
		
		//普通图
		List<HousePhoto> houseImgs = new ArrayList<HousePhoto>();
		List<HousePhoto> estateImgs = new ArrayList<HousePhoto>();
		List<HousePhoto> roomImgs = new ArrayList<HousePhoto>();
		for (HousePhoto housePhoto : housePhotoes) {
			if(housePhoto.getCategory().equals("1")){
				houseImgs.add(housePhoto);
				
			}else if(housePhoto.getCategory().equals("2")){
				roomImgs.add(housePhoto);
				
			}else if(housePhoto.getCategory().equals("3")){
				estateImgs.add(housePhoto);
			}
		}
		
		hq.setEstateImgUrls(estateImgs);
		hq.setLayoutImgUrls(houseImgs);
		hq.setInnerImgUrls(roomImgs);
	}
	
	
	private List<HousePhoto> getHousePhotoByRentId(int rentId){
		List<HousePhoto> housePhotoList = new ArrayList<HousePhoto>();
		List<com.taofang.biz.domain.house.HousePhoto> bphotoList = BizServiceHelper.getHousePhotoService().getRentPhotos(rentId);
		if(CollectionUtils.isNotEmpty(bphotoList)){
			for(com.taofang.biz.domain.house.HousePhoto bphoto: bphotoList ){
				HousePhoto housePhoto = new HousePhoto();
				
				housePhoto.setCategory(bphoto.getCategory()/100+1+"");
				housePhoto.setInalbum(0);
				housePhoto.setInnertype(0);
				housePhoto.setCover(bphoto.getCoverFlag()=="Y"?1:0);
				housePhoto.setPhotoid(bphoto.getId()+"");
				housePhoto.setlURL(bphoto.getRentLargePhotoUrl());
				housePhoto.setmURL(bphoto.getRentMediumPhotoUrl());
				housePhoto.setsURL(bphoto.getRentSmallPhotoUrl());
				housePhotoList.add(housePhoto);
			}
		}
		return housePhotoList;
	}
	private void setRentPhotos(HistoryInfo hq){
		int id = hq.getHouseId();
		List<HousePhoto> housePhotoes = getHousePhotoByRentId(id);
		// 普通图
		List<HousePhoto> houseImgs = new ArrayList<HousePhoto>();
		List<HousePhoto> estateImgs = new ArrayList<HousePhoto>();
		List<HousePhoto> roomImgs = new ArrayList<HousePhoto>();
	
		for (HousePhoto housePhoto : housePhotoes) {
			if (housePhoto.getCategory().equals("1")) {
				houseImgs.add(housePhoto);
	
			} else if (housePhoto.getCategory().equals("2")) {
				roomImgs.add(housePhoto);
			} else if (housePhoto.getCategory().equals("3")) {
				estateImgs.add(housePhoto);
			}
	
		}
		hq.setEstateImgUrls(estateImgs);
		hq.setLayoutImgUrls(houseImgs);
		hq.setInnerImgUrls(roomImgs);
	}
	
	public List<AuditorInfo> getEveryCountByUsers(Integer auditStep,
			List<User> users, Date beginTime, Date endTime) {
		int type=-1;
		if(Globals.AUDIT_BASE_INFO==auditStep){
			type=AuditConstant.BASIC_INFO_TASK;
		}
		if(Globals.AUDIT_ESTATE_PHOTO==auditStep){
			type=AuditConstant.OUTDOOR_PHOTO_TASK;
		}
		if(Globals.AUDIT_INDOOR_PHOTOO==auditStep){
			type=AuditConstant.INDOOR_PHOTO_TASK;
		}
		if(Globals.AUDIT_HOUSEHOLD_PHOTO==auditStep){
			type=AuditConstant.LAYOUT_PHOTO_TASK;
		}
		
		List<AuditorInfo> newList  =  auditHistoryDao.getEachForHousePhotoNew(type, users, beginTime, endTime);
		
		 type=-1;
		if(Globals.AUDIT_BASE_INFO==auditStep){
			type=Globals.AUDIT_HISTORY_BASEINFO;
		}
		if(Globals.AUDIT_ESTATE_PHOTO==auditStep){
			type=Globals.AUDIT_HISTORY_COMMUNITY;
		}
		if(Globals.AUDIT_INDOOR_PHOTOO==auditStep){
			type=Globals.AUDIT_HISTORY_INDOOR;
		}
		if(Globals.AUDIT_HOUSEHOLD_PHOTO==auditStep){
			type=Globals.AUDIT_HISTORY_LAYOUT;
		}
		List<AuditorInfo>  list  =null;
		//图片的统计数据更换方式
		if(type <4 && type >0){
			list =  auditHistoryDao.getEachForHousePhoto(type, users, beginTime, endTime);
		}else{
			list =  auditHistoryDao.getEveryCountByUsers(type,users,beginTime, endTime);
		}
		if(CollectionUtils.isEmpty(list)){
			list =  new ArrayList<AuditorInfo>();
		}
		list.addAll(newList);
		return list;
	}

	public HistoryInfo getHistoryInfoBySubId(int parseInt) {
		return auditHistoryDao.getHistoryInfoBySubId(parseInt);
	}

	public List<AuditorInfo> getAgentEveryCountByUsers(List<User> users,
			Date beginTime, Date endTime) {
		return auditHistoryDao.getEveryCountByUsers(Globals.AUDIT_HISTORY_AGENT_IDCARD_IMG,users,beginTime, endTime);
	}

	public void consistNewPhotoQuery(Model model, HashMap<String, Object> params, StringBuffer conditions,String houseid,String authorOrId,String house_type, String startTime, String sHour, String endTime, String eHour,
			String auditResult, String type, String per, int sessionUserId, String userId, String dealerName) throws ParseException {
		if (StringUtils.isNotBlank(houseid)) {
			houseid = houseid.trim();
			conditions.append(" AND house_id = " + houseid);
			model.addAttribute("houseid", houseid);
		}
		// 房源拥有者ID或者姓名
		if (StringUtils.isNotBlank(authorOrId)) {
			authorOrId = authorOrId.trim();
			conditions.append(" AND  agent_id = " + authorOrId );
			model.addAttribute("authorOrId", authorOrId);
		}
		if(StringUtils.isNotBlank(house_type)){
			conditions.append(" AND house_type = " + house_type);
			model.addAttribute("house_type", house_type);
		}
		
		// 审核结果过滤
		if (StringUtils.isNotBlank(auditResult)) {
			auditResult = auditResult.trim();
			if (!"0".equals(auditResult)) {
				if("-1".equals(auditResult)){
					conditions.append(" AND audit_status = 1" );
				}else if("-2".equals(auditResult)){
					conditions.append(" AND audit_status = 2" );
				}
			}
			model.addAttribute("auditResult", auditResult);
		}
		
		
		// 时间
		if (StringUtils.isNotBlank(startTime)) {
			startTime = startTime.trim();
			String queryStartTime = startTime + " " + sHour + ":00";
			Date queryStartTimeDate = simpleDateFormat.parse(queryStartTime);
			if (queryStartTimeDate.after(newAuditStartTime)) {
				conditions.append(" AND entry_time > '" + queryStartTime + "'");
				model.addAttribute("startTime", startTime);
			} else {
				conditions.append(" AND entry_time > '" + newAuditStringStartTime + "'");
				model.addAttribute("startTime", newAuditStringStartTime.split(" ")[0]);
			}
		} else {
			conditions.append(" AND entry_time > '" + newAuditStringStartTime + "'");
			model.addAttribute("startTime", newAuditStringStartTime.split(" ")[0]);
		}
		if (StringUtils.isNotBlank(endTime)) {
			endTime = endTime.trim();
			conditions.append(" AND entry_time < '" + endTime + " " + eHour + ":00'");
			model.addAttribute("endTime", endTime);
		}

		// 处理人ID
		if (StringUtils.isNotBlank(dealerName)) {
			conditions.append(" AND u.user_name = '" + dealerName + "' ");
			model.addAttribute("dealerName", dealerName);
		}
		if (StringUtils.isNotBlank(userId)) {
			conditions.append(" AND auditor = '" + userId + "' ");
			model.addAttribute("userId", userId);
		}
		model.addAttribute("sHour", sHour);
		model.addAttribute("eHour", eHour);
		model.addAttribute("type", type);
		params.put("conditions", conditions);
	}

	public List<HistoryInfo> getNewPhotoHstryListByPage(Model model, String houseid, String authorOrId, String startTime, String sHour, String endTime, String eHour,
			String auditResult, String type, String per, int sessionUserId, String userId, String dealerName, String house_type) throws ParseException {
		HashMap<String, Object> params = new HashMap<String, Object>();
		StringBuffer conditions = new StringBuffer();

		if(StringUtils.isNotBlank(per)){
			userId = "" + sessionUserId;
			model.addAttribute("per", "1");
		}
		
		consistNewPhotoQuery(model, params, conditions,houseid,authorOrId,house_type, startTime, sHour, endTime, eHour, auditResult, type, per, sessionUserId, userId, dealerName);
		return auditHistoryDao.getNewPhotoHstryListByPage(params);
	}
	

	public List<SubAuditHistory> getHouseIdsByAuditIds(List<String> auditIdList) {
		return auditHistoryDao.getHouseIdsByAuditIds(auditIdList);
	}

	public void reauditPhotoPass(Integer photoIdInteger, Integer houseIdInteger, String house_type) {
		// 先在reject_photo里边找到
		HistoryInfo historyInfo = new HistoryInfo();
		historyInfo.setHouseId(houseIdInteger);
		historyInfo.setPhotoId(photoIdInteger);
		historyInfo.setInalbum(0);
		// 转移reject_photo到rent_photo,增加相应photo_count
		historyInfo = rentReadMapper.getRejectPhotoReAudit(historyInfo);
		if (house_type.equals(""+Globals.HOUSE_TYPE_RENT)) {
			reAuditRentPhotoPass(historyInfo);
			//rentWriteMapper.reAuditPhotoPass(historyInfo);
			rentWriteMapper.deleteRejectPhoto(historyInfo.getHouseId(), historyInfo.getPhotoId(), historyInfo.getInalbum());
			rentDao.updateRentPhotoCount(historyInfo.getHouseId());
		} else if (house_type.equals(""+Globals.HOUSE_TYPE_RESALE)) {
			reAuditPhotoPass(historyInfo);
			//resaleWriteMapper.reAuditPhotoPass(historyInfo);
			resaleWriteMapper.deleteRejectPhoto(historyInfo.getHouseId(), historyInfo.getPhotoId(), historyInfo.getInalbum());
			resaleDao.updateResalePhotoCount(historyInfo.getHouseId());
		}
	}
	private void reAuditPhotoPass(HistoryInfo historyInfo){
		com.taofang.biz.domain.house.HousePhoto photo =new com.taofang.biz.domain.house.HousePhoto();
		photo.setId(historyInfo.getPhotoId());
		photo.setHouseId(historyInfo.getHouseId());
		int category=0;
		switch (historyInfo.getAuditStep()) {
		case 1:
			category =10;
			break;
		case 2:
			category =100;
			break;
		case 3:
			category =200;
			break;
		default:
			break;
		}
		photo.setCategory(category);
		BizServiceHelper.getHousePhotoService().storeToResale(photo);
	}
	private void reAuditRentPhotoPass(HistoryInfo historyInfo){
		com.taofang.biz.domain.house.HousePhoto photo =new com.taofang.biz.domain.house.HousePhoto();
		photo.setId(historyInfo.getPhotoId());
		photo.setHouseId(historyInfo.getHouseId());
		int category=0;
		switch (historyInfo.getAuditStep()) {
		case 1:
			category =10;
			break;
		case 2:
			category =100;
			break;
		case 3:
			category =200;
			break;
		default:
			break;
		}
		photo.setCategory(category);
		BizServiceHelper.getHousePhotoService().storeToRent(photo);
	}

	public void reauditPhotoReject( Integer photoIdInteger, Integer houseIdInteger, String house_type, String reason,
 String auditStep, User user) throws Exception {
		HistoryInfo historyInfo = new HistoryInfo();
		historyInfo.setHouseId(houseIdInteger);
		historyInfo.setPhotoId(photoIdInteger);
		historyInfo.setRejectReason(reason);
		historyInfo.setInalbum(0);
		List<HousePhoto> housePhotos = null;
		if (house_type.equals("" + Globals.HOUSE_TYPE_RENT)) {
			housePhotos = rentDao.getAllHousePhotoByType(historyInfo.getHouseId(), historyInfo.getPhotoId(), Integer.parseInt(auditStep), historyInfo.getInalbum());
		} else {
			housePhotos = resaleDao.getAllHousePhotoByType(historyInfo.getHouseId(), historyInfo.getPhotoId(), Integer.parseInt(auditStep), historyInfo.getInalbum());
		}
		if (housePhotos.size() < 1) {
			throw new Exception("没有找到图片");
		}
		historyInfo.setHouseDescribe(housePhotos.get(0).getmURL());
		historyInfo.setAuditStep(Integer.parseInt(housePhotos.get(0).getCategory()));
		if (null != user) {
			historyInfo.setDealerId(user.getId());
		} else {
			throw new Exception("没有User传进来");
		}

		if (house_type.equals("" + Globals.HOUSE_TYPE_RESALE)) {
			historyInfo.setHouseType(Globals.RESALE_TYPE);
			resaleWriteMapper.reAuditPhotoReject(historyInfo);
			resaleWriteMapper.deleteResalePhoto(historyInfo.getHouseId(), historyInfo.getPhotoId());
			resaleWriteMapper.updatePhotoStatus(historyInfo.getHouseId(), 1);
			resaleDao.updateResalePhotoCount(historyInfo.getHouseId());
			// 更新封面照片
			List<String> list = new ArrayList<String>();
			list.add("" + houseIdInteger);
			resaleDao.updateCoverImgList(list);
		} else if (house_type.equals("" + Globals.HOUSE_TYPE_RENT)) {
			historyInfo.setHouseType(Globals.RENT_TYPE);
			rentWriteMapper.reAuditPhotoReject(historyInfo);
			rentWriteMapper.deleteRentPhoto(historyInfo.getHouseId(), historyInfo.getPhotoId());
			rentWriteMapper.updatePhotoStatus(historyInfo.getHouseId(), 1);
			rentDao.updateRentPhotoCount(historyInfo.getHouseId());
			// 更新封面照片
			List<String> list = new ArrayList<String>();
			list.add("" + houseIdInteger);
			rentDao.updateCoverImgList(list);
		}
	}

	public NewSubAuditHistory getNewSubAuditHistory(int id) {
		return auditHistoryDao.getNewSubAuditHistory(id);
	}

	public void addReauditHistory(NewSubAuditHistory newSubAuditHistory, Integer auditIdInteger, Integer photoIdInteger, String result, User user) {
		ReauditHistory reauditHistory = new ReauditHistory();
		reauditHistory.setAuditId(newSubAuditHistory.getDealer_id());
		reauditHistory.setHistoryId(auditIdInteger);
		reauditHistory.setPhoto_id(photoIdInteger);
		reauditHistory.setReauditId(user.getId());
		reauditHistory.setReauditTime(new Date());
		reauditHistory.setResult(Integer.parseInt(result));
		reauditHistory.setSubHistoryId(newSubAuditHistory.getId());
		boolean exists = auditHistoryDao.getReauditHistoryCountNew(newSubAuditHistory.getId(),photoIdInteger.intValue());
		if(exists){// 存在则删除
			auditHistoryDao.deleteReauditHistoryNew(newSubAuditHistory.getId(),photoIdInteger.intValue());
		} else {// 不存在则添加
			auditHistoryDao.addReauditHistoryNew(reauditHistory);
		}
	}
	
	
	public void  handleReaudit(int houseId,int houseType,User user,int result){
		//找到该房源基本信息的审核记录
		HistoryInfo sah = getBaseinfoLastAuditLogByHouse(houseId,houseType);
		//有没有复审记录
		int reauditNum  = 0;
		int type=0;
		if(result == -1){
			type =-2;
		}else if(result == -2){
			type =-1;
		}
		if(sah != null){
			reauditNum  =  auditHistoryDao.getReaudithistoryCount(sah,type);
		}else{
			return;
		}
		//如果有 删除复审记录; 没有 创建一条复审记录
		if(reauditNum >0){
			auditHistoryDao.deleteReauditHistoryByLogId(sah);
		}else if(reauditNum ==0){
			ReauditHistory reauditHistory = new ReauditHistory();
			reauditHistory.setHistoryId(0);
			reauditHistory.setSubHistoryId(0);
			reauditHistory.setAuditId(sah.getDealerId());
			reauditHistory.setReauditId(user.getId());
			reauditHistory.setHouseId(sah.getHouseId());
			reauditHistory.setHouseType(sah.getHouseType());
			reauditHistory.setResult(result);
			reauditHistory.setReauditTime(new Date());
			auditHistoryDao.saveReauditHistory(reauditHistory);
		}
	}

	public HistoryInfo getBaseinfoLastAuditLogByHouse(Integer id, int type) {
		List<HistoryInfo> sahList = auditHistoryDao.getBaseinfoLastAuditLogByHouse(id,type);
		if(CollectionUtils.isNotEmpty(sahList)){
			return sahList.get(0);
		}
		return null;
	}

	public List<HistoryInfo> getReAuditHistory(int houseId, int houseType) {
		return auditHistoryDao.getReAuditHistory(houseId,houseType);
	}


}
