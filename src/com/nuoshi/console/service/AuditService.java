package com.nuoshi.console.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taofang.biz.domain.audit.house.task.AuditHouseTaskSubmit;
import com.taofang.biz.domain.audit.house.task.HousePhotoAuditTask;
import com.taofang.biz.domain.audit.house.task.HouseTitleDescAuditTask;
import com.taofang.biz.domain.house.HousePhoto;
import com.taofang.biz.local.AgentPhotoUrlUtil;
import com.taofang.biz.service.audit.IAuditHouseService;
import com.taofang.biz.util.BizServiceHelper;
import com.nuoshi.console.common.Globals;
import com.nuoshi.console.common.util.ApplicationUtil;
import com.nuoshi.console.dao.AuditDao;
import com.nuoshi.console.dao.RentDao;
import com.nuoshi.console.dao.ResaleDao;
import com.nuoshi.console.domain.agent.AgentMaster;
import com.nuoshi.console.domain.audit.AuditAgentList;
import com.nuoshi.console.domain.audit.AuditPhotoSetting;
import com.nuoshi.console.domain.audit.AuditStep;
import com.nuoshi.console.domain.audit.AuditTask;
import com.nuoshi.console.domain.audit.WenDaAuditTask;
import com.nuoshi.console.domain.auditHistory.AuditHistory;
import com.nuoshi.console.domain.auditHistory.HistoryInfo;
import com.nuoshi.console.domain.auditHistory.ReauditHistory;
import com.nuoshi.console.domain.auditHistory.SubAuditHistory;
import com.nuoshi.console.domain.base.House;
import com.nuoshi.console.domain.rent.Rent;
import com.nuoshi.console.domain.rent.RentInfo;
import com.nuoshi.console.domain.resale.Resale;
import com.nuoshi.console.domain.resale.ResaleInfo;
import com.nuoshi.console.domain.user.User;
import com.nuoshi.console.persistence.read.taofang.agent.TFUserReadMapper;
import com.nuoshi.console.web.form.AgentVerifyResultForm;
import com.nuoshi.console.web.session.SessionUser;

@Service
public class AuditService {
	private Log log = LogFactory.getLog(AuditService.class);
	@Autowired
	WenDaVerifyService wenDaVerifyService;
	@Autowired
	private ResaleVerifyService resaleVerifyService;
	@Autowired
	private RentVerifyService rentVerifyService;
	@Autowired
	private RentDao rentDao;
	@Autowired
	private ResaleDao resaleDao;
	@Autowired
	private AuditHistoryService auditHistoryService;
	@Autowired
	private AuditDao auditDao;
	@Autowired
	private MessageService messageService;
	@Autowired
	private AgentMasterService agentMasterService;
	@Resource
	private TFUserReadMapper tfUserReadMapper;
	
	/**
	 * 处理房源复审时基本信息的审核逻辑
	 * @param historyInfo 历史信息
	 * @param resultInt 复审结果
	 * @param user 复审人
	 * @author wangjh
	 * Dec 23, 2011 3:21:03 PM
	 */
	public void reauditBaseInfo(HistoryInfo historyInfo, Integer resultInt,
			User user) {
		if(historyInfo==null){
			log.error("进行标题描述复审出错！审核信息historyInfo为空");
			return;
		}
		Integer auditStep=Globals.AUDIT_BASE_INFO;
		Integer houseId=historyInfo.getHouseId();
		List<Integer> houseIdList=new ArrayList<Integer>();
		houseIdList.add(houseId);
		Integer houseType=historyInfo.getHouseType();
		if(houseType==null){
			log.error("复审基本信息，房源类型为空！");
			return ;
		}
		//打回
		if(Globals.AUDIT_RESULT_REJECT==resultInt){
			//复审先设置原审核的总状态
			List<AuditHistory> historyList=auditHistoryService.getLastRecordByHouseIds(houseIdList, houseType);
			AuditHistory history=null;
			if(CollectionUtils.isNotEmpty(historyList)){
				history=historyList.get(0);
			}
			if(history!=null){
				history.setAuditResult(Globals.AUDIT_RESULT_NOPASS_REJECT);
			}
			auditHistoryService.updateAuditHistory(history);
			//获取打回原因
			String rejectReason=historyInfo.getRejectReason();
			if(StringUtils.isBlank(rejectReason)){
				log.error("打回房源，打回原因为空！");
				rejectReason="审核人员未填写打回原因！";
			}
			//调用旧的打回方法
			if(Globals.HOUSE_TYPE_RENT==houseType){
				rentVerifyService.reject(houseId,rejectReason);
				log.info("复审打回出租房！");
			}
			if(Globals.HOUSE_TYPE_RESALE==houseType){
				resaleVerifyService.reject(houseId,rejectReason);
				log.info("复审打回二手房！");
			}
			//删除审核表中的内容
			resaleVerifyService.deleteAuditTask(houseIdList, houseType);
			log.info("记录复审时打回的房源记录");
		}
		//通过
		if(Globals.AUDIT_RESULT_PASS==resultInt){
			if(Globals.HOUSE_TYPE_RENT==houseType){
				
			//修改房源状态为待审核，添加审核标志audit_sign
			rentVerifyService.updateAuditStatusByIdList(houseIdList,House.AUDIT_STATUS_UNVERIFIED, House.HOUSE_STATUS_UNSHELVED, ""+user.getId());
			for (Integer rentId : houseIdList) {
				Rent rent = rentDao.selectRentById(rentId);
				AgentMaster author = tfUserReadMapper.selectAgentById(rent.getAuthorid());
				String viewUrl = "http://" + LocaleService.getCode(rent.getCityid()) + ".taofang.com/rent/" + rent.getId() + "-" + 0 + ".html";
				messageService.sendReAuditBackMessage(author, rentId, viewUrl);
				
			}
			
			//新建任务
			List<RentInfo> rentInfoList=rentVerifyService.getRentInfoCity(houseIdList);
			if(CollectionUtils.isNotEmpty(rentInfoList)){
				for (RentInfo rentInfo : rentInfoList) {
					AuditStep as = new AuditStep();
					if(rentInfo.getUpdateBase()==1){
						as.setStepBase(-1);
					}
					if(rentInfo.getId()!=null){
						List<Integer> listCategory = rentDao.getUnauditPhotoCategory(rentInfo.getId());
						if(listCategory!=null){
							if(!listCategory.contains(1)){
								as.setStepLayout(-1);
							}else{
								as.setStepLayout(user.getId());
							}
							if(!listCategory.contains(2)){
								as.setStepInner(-1);
							}else{
								as.setStepInner(user.getId());
							}
							if(!listCategory.contains(3)){
								as.setStepEstate(-1);
							}else{
								as.setStepEstate(user.getId());
							}
						}
					}
					rentVerifyService.buileTaskByParam(rentInfo, auditStep, user.getId(),as);
				}
			}
			}
			if(Globals.HOUSE_TYPE_RESALE==houseType){
				
				//修改房源状态为待审核，添加审核标志audit_sign
				resaleVerifyService.updateAuditStatusByIdList(houseIdList,House.AUDIT_STATUS_UNVERIFIED, House.HOUSE_STATUS_SHELVED, ""+user.getId());
				
				for (Integer resaleId : houseIdList) {
					Resale resale = resaleDao.selectResaleById(resaleId);
					AgentMaster author = tfUserReadMapper.selectAgentById(resale.getAuthorid());
					String viewUrl = "http://" + LocaleService.getCode(resale.getCityid()) + ".taofang.com/ershoufang/" + resale.getId() + "-" + 0 + ".html";
					messageService.sendReAuditBackMessage(author, resaleId, viewUrl);
					
				}
				//新建任务
				List<ResaleInfo> resaleInfoList=resaleVerifyService.getRentInfoCity(houseIdList);
				if(CollectionUtils.isNotEmpty(resaleInfoList)){
					for (ResaleInfo resaleInfo : resaleInfoList) {
						AuditStep as = new AuditStep();
						if(resaleInfo.getUpdateBase()==1){
							as.setStepBase(-1);
						}else{
							as.setStepBase(user.getId());
						}
						if(resaleInfo.getId()!=null){
							List<Integer> listCategory = resaleDao.getUnauditPhotoCategory(resaleInfo.getId());
							if(listCategory!=null){
								if(!listCategory.contains(1)){
									as.setStepLayout(-1);
								}else{
									as.setStepLayout(user.getId());
								}
								if(!listCategory.contains(2)){
									as.setStepInner(-1);
								}else{
									as.setStepInner(user.getId());
								}
								if(!listCategory.contains(3)){
									as.setStepEstate(-1);
								}else{
									as.setStepEstate(user.getId());
								}
							}
						}
						
						resaleVerifyService.buileTaskByParam(resaleInfo, auditStep, user.getId(),as);
					}
				}
			}
			//调用新的通过方法 并记录审核记录
			this.auditBaseInfo(houseIdList, null, houseType, user);
		}
		
		
	}
	
	// 房源审核基本信息(新)
	public void auditBaseInfo(List<Integer> passHouseId,
			Map<Integer, String> rejectIdAndReason, Integer houseType,User user,List<HouseTitleDescAuditTask> sessionResaleList) {
	
		Map<Integer,HouseTitleDescAuditTask> sessionResaleMap =  new HashMap<Integer, HouseTitleDescAuditTask>();
		if(sessionResaleList != null){
			for(HouseTitleDescAuditTask resaleTask:sessionResaleList){
				sessionResaleMap.put(resaleTask.getHouseId(), resaleTask);
			}
		
			for(Integer resaleId:passHouseId){
				HouseTitleDescAuditTask resaleTask = sessionResaleMap.get(resaleId);
				AuditHouseTaskSubmit task  =new AuditHouseTaskSubmit(resaleTask, houseType.byteValue(), user.getId());
				BizServiceHelper.getAuditHouseService().submitTask(task);
			}
			for(Integer resaleId:rejectIdAndReason.keySet()){
				HouseTitleDescAuditTask resaleTask = sessionResaleMap.get(resaleId);
				AuditHouseTaskSubmit task  =new AuditHouseTaskSubmit(resaleTask, houseType.byteValue(), user.getId());
				task.rejectBasicInfo(rejectIdAndReason.get(resaleId));
				BizServiceHelper.getAuditHouseService().submitTask(task);
			}
		}
	}
	/**
	 * 房源审核基本信息
	 * @param passHouseId
	 * @param rejectIdAndReason
	 * @param houseType
	 * @param auditStep
	 * @author wangjh
	 * Dec 21, 2011 7:01:44 PM
	 */
	public void auditBaseInfo(List<Integer> passHouseId,
			Map<Integer, String> rejectIdAndReason, Integer houseType,User user) {
	
		
		
		Integer auditStep=Globals.AUDIT_BASE_INFO;
		//先记录审核详情，防止先处理会把最终审核结果 设置值
		try{
			List<SubAuditHistory> subHistory=this.getSubAuditHistory(passHouseId,
					rejectIdAndReason,houseType,user);
			
			if(CollectionUtils.isNotEmpty(subHistory)){
				auditHistoryService.insertSubList(subHistory);
				//记录审核详细历史
				log.info("记录审核详细历史");
			}
		}catch (Exception e) {
			e.printStackTrace();
			log.error("记录子表审核记录时出错",e);
		}

		try {
			if (CollectionUtils.isNotEmpty(passHouseId)) {
				this.auditPass(passHouseId, auditStep,
						houseType);
			}
			
			if (rejectIdAndReason!=null&&rejectIdAndReason.size() > 0) {
				this.auditRejectBaseInfo(rejectIdAndReason,
						houseType);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 通过和不通过的房源id获取历史记录详情信息(审核的基本信息)
	 * @param passHouseId 通过房源id集合
	 * @param rejectIdAndReason 不通过的房源map（id和打回原因的map）
	 * @param houseType 房源类型（出租，二手）
	 * @return
	 * @author wangjh
	 * Dec 22, 2011 10:54:52 AM
	 */
	public List<SubAuditHistory> getSubAuditHistory(List<Integer> passHouseId,
			Map<Integer, String> rejectIdAndReason, Integer houseType,User user){
		List<SubAuditHistory> subAuditHistory=null;
		List<AuditHistory> auditHistory=null;
		List<Integer> allHouseIdList=new ArrayList<Integer>();
		//通过房源id  返回子审核历史list，包括过的和不过的
		Map<Integer,Integer> houseIdAndResultMapper=new HashMap<Integer, Integer>();
		if (CollectionUtils.isNotEmpty(passHouseId)) {
			//保存房源id和对应审核结果
			for (Integer passId : passHouseId) {
				houseIdAndResultMapper.put(passId, Globals.AUDIT_RESULT_PASS);
			}
			allHouseIdList.addAll(passHouseId);
		}
		if (rejectIdAndReason!=null&&rejectIdAndReason.size() > 0) {
			List<Integer> noPassList=new ArrayList<Integer>(rejectIdAndReason.keySet());
			for (Integer noPassId : noPassList) {
				houseIdAndResultMapper.put(noPassId, Globals.AUDIT_RESULT_REJECT);
			}
			allHouseIdList.addAll(rejectIdAndReason.keySet());
		}
		if(CollectionUtils.isEmpty(allHouseIdList)){
			log.info("保存审核记录时无审核的房源id。");
			return subAuditHistory;
		}
		//获取subAuditHistory(auditId当前为houseId)
		subAuditHistory=auditHistoryService.getHistoryInfoForBaseInfo(allHouseIdList,houseType,user);
		if(CollectionUtils.isEmpty(subAuditHistory)){
			log.error("房源id对应的审核历史信息不存在！");
			return subAuditHistory;
		}
		//查找主表，没有则新建主表记录
		auditHistoryService.foundHistory(allHouseIdList, houseType);
		auditHistory=auditHistoryService.getLastRecordByHouseIds(allHouseIdList, houseType);
		if(CollectionUtils.isEmpty(auditHistory)){
			log.error("房源id对应审核的主表记录不存在！");
		}
		Map<Integer,AuditHistory> houseIdAndAuditHistoryMapper=new HashMap<Integer, AuditHistory>();
		for (AuditHistory history : auditHistory) {
			houseIdAndAuditHistoryMapper.put(history.getHouseId(), history);
		}
		//设置通过打回
		//将auditId改为审核历史主表id，并设置审核结果
		for (SubAuditHistory subHistory : subAuditHistory) {
			Integer houseId=subHistory.getHouseId();
			AuditHistory history=houseIdAndAuditHistoryMapper.get(houseId);
			//设置审核id
			Integer result=houseIdAndResultMapper.get(houseId);
			//审核记录主表id
			subHistory.setAuditResult(result);
			//打回原因
			String rejectReson=null;
			if(rejectIdAndReason!=null){
				rejectReson=rejectIdAndReason.get(houseId);
			}
			subHistory.setRejectReason(StringUtils.isBlank(rejectReson)?null:rejectReson);
			subHistory.setAuditId(history.getId());
			Date auditTime=new Date();
			subHistory.setAuditTime(auditTime);
		}
		return subAuditHistory;
	}
	/**
	 * 审核人员提取单步的出租房审核任务
	 * 
	 * @param num
	 *            提取任务数量
	 * @param auditUserId
	 *            提取人员id
	 * @return
	 * @author wangjh Nov 25, 2011 4:55:56 PM
	 */
	public synchronized List<RentInfo> getRentTask(Integer num, Integer auditUserId, Integer auditStep, Integer cityId) {
		// 状态是未审核
		Integer auditStatus = House.AUDIT_STATUS_UNVERIFIED;
		// 房屋状态是已发布
		Integer houseStatus = House.HOUSE_STATUS_SHELVED;
		// 当前审核人员的任务标记
		String auditSign = this.generationMark(auditUserId);
		Integer needTaskNum = 0;
		try {
			needTaskNum = this.getTaskCountByAuditStep(auditUserId, auditStep, Globals.HOUSE_TYPE_RENT, cityId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (needTaskNum > 0) {
			// 向任务列表中添加出租房审核任务
			try {
//				Long a=System.currentTimeMillis();
				rentVerifyService.buildRentTask(auditSign, auditStatus, houseStatus, needTaskNum, auditStep, auditUserId, cityId);
//				System.out.println("创建任务的时间："+(System.currentTimeMillis()-a));
				log.debug("需要创建出租房审核任务" + needTaskNum + "条。");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		List<RentInfo> rentInfoList = null;
		// 审核人员领取任务
		try {
			rentInfoList = rentVerifyService.getRentTask(auditStatus, houseStatus, num, auditStep, auditUserId, cityId);
			log.debug("从任务列表领取出租房审核任务。");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return rentInfoList;

	}



	/**
	 * 审核人员提取二手房单步的审核任务
	 * 
	 * @param num
	 *            提取任务数量
	 * @param auditUserId
	 *            提取人员id
	 * @return
	 * @author wangjh Nov 25, 2011 4:55:56 PM
	 */
	public synchronized List<ResaleInfo> getResaleTask(Integer num, Integer auditUserId, Integer auditStep, Integer cityId) {
		// 状态是未审核
		Integer auditStatus = House.AUDIT_STATUS_UNVERIFIED;
		// 房屋状态是已发布
		Integer houseStatus = House.HOUSE_STATUS_SHELVED;
		// 当前审核人员的任务标记
		String auditSign = this.generationMark(auditUserId);
		// 获取需要添加任务的数量
		Integer needTaskNum = this.getTaskCountByAuditStep(auditUserId, auditStep, Globals.HOUSE_TYPE_RESALE, cityId);
		if (needTaskNum > 0) {
			// 向任务列表中添加二手房审核任务
			resaleVerifyService.buildResaleTask(auditSign, auditStatus, houseStatus, needTaskNum, auditStep, auditUserId, cityId);
			log.info("创建二手房审核任务" + needTaskNum + "条。");
		}
		// //审核人员领取任务
		List<ResaleInfo> resaleInfoList = resaleVerifyService.getResaleTask(auditSign, auditStatus, houseStatus, num, auditStep, auditUserId, cityId);
		log.info("从任务列表领取二手房审核任务。");
		return resaleInfoList;

	}

	/**
	 * 通过审核人生成占用任务标记
	 */
	private String generationMark(Integer auditUserId) {
		return "" + auditUserId + new java.util.Date().getTime();

	}

	// 审核通过清楚审核表中已通过的数据，历史记录应该在记录表中 保留审核任务表中id号最大的一条已经通过的记录，为了添加新任务

	/**
	 * 得到指定用户和审核步骤需要从房源表中获取的数量
	 * 
	 * @param auditUserId
	 *            审核人员
	 * @param auditStep
	 *            审核步骤（基本信息，户型图，小区图，室内图）
	 * @param auditType
	 *            二手房或者出租房
	 * @return
	 * @author wangjh Dec 3, 2011 3:02:50 PM
	 */
	public synchronized Integer getTaskCountByAuditStep(Integer auditUserId, Integer auditStep, Integer auditType, Integer cityId) {
		//清空任务列表中该用户领取的任务（如果不清空，他误领的任务别人就无法审核了）
		//if(auditUserId!=null){
			//List<Integer> userIdList=new ArrayList<Integer>();
			//userIdList.add(auditUserId);
			//清空任务的个数（领取任务到一定数量时，每次清空的任务就会控制在20条以内）
			//Integer emptyNum=Globals.EMPTY_TASK_COUNT;
			//this.emptyAuditTask(userIdList, emptyNum);
	//	}
		List<Integer> houseIdList = null;
		// 不符合审核条件的房源id
		List<Integer> inconformityHouseIdList = null;
		// 任务数量
		Integer taskCount = 0;
		// 获取出租房的数量
		if (auditType == Globals.HOUSE_TYPE_RENT) {

			// 存在不符合条件的房源，删除任务并再次获取任务
			boolean flag = true;
			Integer n = 1;
			while (flag) {
				inconformityHouseIdList = null;
				// 任务列表获取审核任务房源id
				log.debug("第" + n + "次获取任务。");
				//synchronized (this) {
					houseIdList = rentVerifyService.getRentTaskIdList(auditUserId, auditStep, Globals.AUDIT_HOUSE_COUNT, cityId);
					if (CollectionUtils.isNotEmpty(houseIdList)) {
						rentVerifyService.updateAuditTaskUser(houseIdList, auditUserId, auditStep);
					}
			//	}
				log.debug("获取的房源id个数为："+houseIdList.size()+"。");
				if(CollectionUtils.isEmpty(houseIdList)){
					flag = false;
					continue;
				}
				if (CollectionUtils.isNotEmpty(houseIdList)) {
					/* 判断获取的任务是否符合审核条件，把不符合的id取出 */
					inconformityHouseIdList = rentVerifyService.getInconformityHouseIds(houseIdList);
					/* 从任务列表中删除不符合审核条件的任务，更改房源的标记为null，（批量取消任务和标记） */
					if (CollectionUtils.isNotEmpty(inconformityHouseIdList)) {
						rentVerifyService.deleteAuditTask(inconformityHouseIdList, auditType);
						log.info("删除获取的任务中不符合审核条件的出租房任务 " + inconformityHouseIdList.size() + " 条！");
						if (log.isDebugEnabled()) {
							for (Integer integer : inconformityHouseIdList) {
								log.debug("删除的任务id：" + integer);
							}
						}
						n++;
						flag = true;
					}
				}
				if (CollectionUtils.isEmpty(inconformityHouseIdList)) {
					flag = false;
				}
			}

		}
		// 获取二手房的数量
		if (auditType == Globals.HOUSE_TYPE_RESALE) {

			// 存在不符合条件的房源，删除任务并再次获取任务
			boolean flag = true;
			Integer n = 1;
			while (flag) {
				inconformityHouseIdList = null;
				// 任务列表获取审核任务房源id
				log.debug("第" + n + "次获取任务。");
				//synchronized (this) {
					houseIdList = resaleVerifyService.getResaleTaskIdList(auditUserId, auditStep, Globals.AUDIT_HOUSE_COUNT, cityId);
					if (CollectionUtils.isNotEmpty(houseIdList)) {
						resaleVerifyService.updateAuditTaskUser(houseIdList, auditUserId, auditStep);
					}
				//}
				if(CollectionUtils.isEmpty(houseIdList)){
					flag = false;
					continue;
				}
				if (CollectionUtils.isNotEmpty(houseIdList)) {
					/* 判断获取的任务是否符合审核条件，把不符合的id取出 */
					inconformityHouseIdList = resaleVerifyService.getInconformityHouseIds(houseIdList);
					/* 从任务列表中删除不符合审核条件的任务，更改房源的标记为null，（批量取消任务和标记） */
					if (CollectionUtils.isNotEmpty(inconformityHouseIdList)) {
						resaleVerifyService.deleteAuditTask(inconformityHouseIdList, auditType);
						log.info("删除获取的任务中不符合审核条件的二手房任务 " + inconformityHouseIdList.size() + " 条！");
						if (log.isDebugEnabled()) {
							for (Integer integer : inconformityHouseIdList) {
								log.debug("删除的任务id：" + integer);
							}
						}
						n++;
						flag = true;
					}
				}
				if (CollectionUtils.isEmpty(inconformityHouseIdList)) {
					flag = false;
				}
			}

		}

		if (CollectionUtils.isNotEmpty(houseIdList)) {
			taskCount = houseIdList.size();
		}
		if (taskCount >= Globals.AUDIT_HOUSE_COUNT) {
			// 不需要从待审核房源中领取任务
			return 0;
		}
		if (taskCount < Globals.AUDIT_HOUSE_COUNT) {
			// 需要获取的任务数量
			return Globals.AUDIT_HOUSE_COUNT - taskCount;
		}
		return taskCount;

	}

	// /**
	// * 向审核任务中添加标记好的任务
	// * @param auditUserId 审核人员
	// * @param auditStep 审核步骤（基本信息，户型图，小区图，室内图）
	// * @param auditType 二手房或者出租房
	// * @author wangjh
	// * Dec 3, 2011 3:07:58 PM
	// */
	// public void buildTask(Integer auditUserId,Integer auditStep,Integer
	// auditType){
	// //当前审核人员的任务标记
	// String auditSign=""+auditUserId+new java.util.Date().getTime();
	// //状态是未审核
	// Integer auditStatus=House.AUDIT_STATUS_UNVERIFIED;
	// //房屋状态是已发布
	// Integer houseStatus=House.HOUSE_STATUS_SHELVED;
	// Integer taskNum=Globals.AUDIT_HOUSE_COUNT;
	// //出租房
	// if(auditType==Globals.HOUSE_TYPE_RENT){
	// rentVerifyService.buildRentTask(auditSign, auditStatus, houseStatus,
	// taskNum, auditStep, auditUserId);
	// }
	// //二手房
	// if(auditType==Globals.HOUSE_TYPE_RENT){
	// resaleVerifyService.buildResaleTask(auditSign, auditStatus, houseStatus,
	// taskNum, auditStep, auditUserId);
	// }
	// }
	// 获取指定人员，指定日期的审核历史，为查看个人当天审核记录

	// ---------------处理待审核任务列表（audit_task）获取基本信息审核任务--------------------
	// X 第一步删除上次取出的无效任务（不需要，即使无效也让审核）
	// 获取提取任务的数量（默认数量-已存在的任务数量）4种获取类别（4个字段），4个默认数量
	// 先从审核列表中未被标记的房源中取（此处只标记--相应字段上记录操作人id），数量够了则 获取任务结束
	// 数量不足 从房源表中取剩余数量的任务到任务列表-----保证所有房源不被落下
	// 为列表添加任务（houseId,houseType）添加任务的类别，需要知道获取任务 的类型（4个类型：基本，图片）
	// 房源添加被提取的标记audit_sign（基本信息，图片 都可以提取任务）
	// 添加到任务表（audit_task）相应的字段写上id（只标记）

	// 提取任务。。。。。相应类型（4个类型：基本，图片），并且id为当前登录人的房源

	/**
	 * 通过审核后修改audit_task的信息
	 * 
	 * @param houseIdList
	 *            通过审核的房源id
	 * @param auditStep
	 *            审核步骤（基本信息，3步图片审核）
	 * @param auditType
	 *            审核类型（出租房，二手房）
	 * @author wangjh Dec 7, 2011 4:00:14 PM
	 */
	public void auditPass(List<Integer> houseIdList, Integer auditStep, Integer auditType) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Integer count = 0;
		if (Globals.HOUSE_TYPE_RENT == auditType) {
			//更新rent的update_base标记为1
			if(auditStep == Globals.AUDIT_BASE_INFO && houseIdList!=null && houseIdList.size()>0) rentVerifyService.updateRentAuditFlag(houseIdList,House.HOUSE_UPDATE_FLAG_FALSE);
			// 更改audti_task的对应信息
			rentVerifyService.updateAuditTaskStatus(houseIdList, auditStep, auditType, Globals.AUDIT_RESULT_PASS);
			// 调用审核最终结果处理--先判断4个审核是否都进行过，如果都完成则调用通过的接口通过审核；如果有未完成的则不做操作
			count = rentVerifyService.finalOutcome(houseIdList, auditType,auditStep);

		}
		if (Globals.HOUSE_TYPE_RESALE == auditType) {
			if(auditStep == Globals.AUDIT_BASE_INFO && houseIdList!=null && houseIdList.size()>0)resaleVerifyService.updateResaleAuditFlag(houseIdList,House.HOUSE_UPDATE_FLAG_FALSE);
			// 更改audti_task的对应信息
			resaleVerifyService.updateAuditTaskStatus(houseIdList, auditStep, auditType, Globals.AUDIT_RESULT_PASS);
			// 调用审核最终结果处理--先判断4个审核是否都进行过，如果都完成则调用通过的接口通过审核；如果有未完成的则不做操作
			count = resaleVerifyService.finalOutcome(houseIdList, auditType,auditStep);

		}
		log.info("有" + count + "条房源通过审核。");
		System.out.println("AuditService:有" + count + "条房源通过审核。"+sdf.format(new Date()));
		Integer result = Globals.AUDIT_RESULT_PASS;
		auditHistoryService.recordLog(houseIdList, result, auditType);

	}

	/**
	 * 处理基本信息的打回
	 * 
	 * @param rejectIdAndReason
	 *            打回的房源id和打回原因的map
	 * @param auditType
	 *            房源类型
	 * @author wangjh Dec 7, 2011 7:25:19 PM
	 */
	public void auditRejectBaseInfo(Map<Integer, String> rejectIdAndReason, Integer auditType) {
		Integer result = Globals.AUDIT_RESULT_REJECT;
		Set<Integer> keySet = rejectIdAndReason.keySet();
		List<Integer> houseIdList = null;
		if (Globals.HOUSE_TYPE_RESALE == auditType) {
			for (Integer key : keySet) {
				resaleVerifyService.reject(key, rejectIdAndReason.get(key));
			}
			houseIdList = new ArrayList<Integer>(keySet);
			
			resaleVerifyService.deleteAuditTask(houseIdList, auditType);
		}
		if (Globals.HOUSE_TYPE_RENT == auditType) {
			for (Integer key : keySet) {
				rentVerifyService.reject(key, rejectIdAndReason.get(key));
			}
			houseIdList = new ArrayList<Integer>(keySet);
			rentVerifyService.deleteAuditTask(houseIdList, auditType);
			log.info("有" + houseIdList.size() + "条房源打回！");
		}
		// 记录审核日志--completeHouseIdList通过的房源
		log.info("记录打回的房源记录");
		auditHistoryService.recordLog(houseIdList, result, auditType);
	}

	
	/**
	 * @param houseIdList
	 *            所有房源id
	 * @param blockList
	 *            有违规记录的房源id
	 * @param photoids
	 *            所有photoids
	 * @param type
	 *            在审的图片的类型/基本信息
	 * @param sessionUser
	 *            审核人
	 * @param house_type
	 *            房源类型,1:rent或者2:resale <br>
	 *            审核图片,添加审核记录
	 * @throws Exception
	 */
	public void verifyPhotos(String houseIdList, String blockList, String photoids, Integer type, SessionUser sessionUser, Integer house_type,List<HousePhotoAuditTask> resaleList ,String taskidsPage) throws Exception {
		if (null == houseIdList || null == photoids || null == type || null == sessionUser || null == house_type) {
			throw new Exception("没有取到页面数据@AuditService!verifyPhotos");
		}
		Map<Integer,HousePhotoAuditTask> sessionTaskMap = new HashMap<Integer, HousePhotoAuditTask>();
		Map<Integer,HousePhoto> sessionPhotoMap = new HashMap<Integer, HousePhoto>();
		Map<Integer,AuditHouseTaskSubmit> taskSubmitMap = new HashMap<Integer, AuditHouseTaskSubmit>();
		List<Integer> taskIdList = new ArrayList<Integer>();
		for(HousePhotoAuditTask task:resaleList){
			taskIdList.add(task.getTaskId());
			sessionTaskMap.put(task.getHouseId(), task);
			for(HousePhoto photo:task.getPhotos()){
				sessionPhotoMap.put(photo.getId(), photo);
			}
		}
		List<Integer> houseIdIntList = new ArrayList<Integer>();
		String taskIdStr="";
		if(CollectionUtils.isNotEmpty(taskIdList)){
			taskIdStr = taskIdList.toString();
		}
		//比较session中的taskId与页面传回的taskId是否相同，不同的话说明传值出现错误，不提交审核结果
		if(!taskIdStr.equals(taskidsPage)){
			throw new RuntimeException();
		}
		
		if (houseIdList.length() > 2) {
			houseIdList = houseIdList.substring(1, houseIdList.length() - 1);
		}
		String[] houseIds = houseIdList.split(",");
		for (int i = 0; i < houseIds.length; i++) {
			Integer ii = Integer.parseInt(houseIds[i].trim());
			houseIdIntList.add(ii);
		}
		
		List<Integer> photoIdList = new ArrayList<Integer>();
		if (photoids.length() > 2) {
			photoids = photoids.substring(1, photoids.length() - 1);
		}
		String[] photoIds = photoids.split(",");
		for (int i = 0; i < photoIds.length; i++) {
			Integer ii = Integer.parseInt(photoIds[i].trim());
			photoIdList.add(ii);
		}
		
		if(blockList !=null && blockList.length()>0){
			String[] blockListArray = blockList.split(",");
			String sign="#_#";
			// 违规房源idList
			List<Integer> rejectHouseIdList = new ArrayList<Integer>();
			for (int i = 0; i < blockListArray.length; i++) {
				String[] reasonArr=blockListArray[i].split(sign);
				String reasonString=reasonArr[1];
				String[] house_photo = reasonArr[0].split("_");
				if(!rejectHouseIdList.contains(Integer.valueOf(house_photo[0]))){
					rejectHouseIdList.add(Integer.valueOf(house_photo[0]));
				}
				AuditHouseTaskSubmit taskSubmit = taskSubmitMap.get(Integer.valueOf(house_photo[0]));
				if( taskSubmit != null){
					taskSubmit.addPhotoRejectReason(Integer.parseInt(house_photo[1]),sessionPhotoMap.get(Integer.parseInt(house_photo[1])).getCategory(), reasonString);
				}else{
					taskSubmit = new AuditHouseTaskSubmit(sessionTaskMap.get(Integer.parseInt(house_photo[0])), house_type.byteValue(), sessionUser.getUser().getId());
					taskSubmit.addPhotoRejectReason(Integer.parseInt(house_photo[1]),sessionPhotoMap.get(Integer.parseInt(house_photo[1])).getCategory(), reasonString);
					taskSubmitMap.put(Integer.parseInt(house_photo[0]), taskSubmit);
				}
				
			}
			
			houseIdIntList.removeAll(rejectHouseIdList);
			
		}
		for(Integer houseId:houseIdIntList){
			AuditHouseTaskSubmit taskSubmit = new AuditHouseTaskSubmit(sessionTaskMap.get(houseId), house_type.byteValue(), sessionUser.getUser().getId());
			taskSubmitMap.put(houseId, taskSubmit);
		}
		
		for(Integer taskId:taskSubmitMap.keySet()){
			BizServiceHelper.getAuditHouseService().submitTask(taskSubmitMap.get(taskId));
		}


	}
	
	
	public void reauditPhotos(String houseId,String blockList, String photoids,String resumes, SessionUser sessionUser, int house_type,
			List<HousePhoto> existPhotoList, List<HousePhoto> rejectPhotoList) throws Exception {
		if (null == photoids || null == sessionUser || houseId==null ) {
			throw new Exception("没有取到页面数据@AuditService!reauditPhotos");
		}
		Map<Integer,HousePhoto> existPhotoMap = new HashMap<Integer, HousePhoto>();
		Map<Integer,HousePhoto> rejectPhotoMap = new HashMap<Integer, HousePhoto>();
		
		List<Integer> sessionPhotoIds = new ArrayList<Integer>();
		if(CollectionUtils.isNotEmpty(existPhotoList)){
			for(HousePhoto existPhoto:existPhotoList){
				sessionPhotoIds.add(existPhoto.getId());
				existPhotoMap.put(existPhoto.getId(), existPhoto);
			}
		}
		
		if(CollectionUtils.isNotEmpty(rejectPhotoList)){
			for(HousePhoto rejectPhoto:rejectPhotoList){
				sessionPhotoIds.add(rejectPhoto.getId());
				rejectPhotoMap.put(rejectPhoto.getId(), rejectPhoto);
			}
		}
		String sessionPhotoidsStr ="";
		if(CollectionUtils.isNotEmpty(sessionPhotoIds)){
			sessionPhotoidsStr = sessionPhotoIds.toString();
		}
		
		//比较session中的photoId与页面传回的photoId是否相同，不同的话说明传值出现错误，不提交审核结果
		if(!photoids.equals(sessionPhotoidsStr)){
			throw new RuntimeException();
		}
		List<HousePhoto> submitPhotoList = new ArrayList<HousePhoto>();
		
		if(blockList !=null && blockList.length()>0){
			String[] blockListArray = blockList.split(",");
			String sign="#_#";
			// 违规房源idList
			for (int i = 0; i < blockListArray.length; i++) {
				String[] reasonArr=blockListArray[i].split(sign);
				String reasonString=reasonArr[1];
				String[] house_photo = reasonArr[0].split("_");
				String photoId = house_photo[1];
				HousePhoto photo = existPhotoMap.get(Integer.valueOf(photoId));
				photo.setReserveFlag(reasonString);
				submitPhotoList.add(photo);
			}
		}
		
		if(resumes != null && resumes.length() >1 ){
			resumes = resumes.substring(0, resumes.length() - 1);
			String[] resumeIds = resumes.split(",");
			for (int i = 0; i < resumeIds.length; i++) {
				Integer resumePhotoId = Integer.parseInt(resumeIds[i].trim());
				HousePhoto photo = rejectPhotoMap.get(resumePhotoId);
				submitPhotoList.add(photo);
			}
		}
		IAuditHouseService iAuditHouseService = BizServiceHelper.getAuditHouseService();
	
		iAuditHouseService.reAuditHousePhoto(Integer.valueOf(houseId), house_type, sessionUser.getUser().getId(), submitPhotoList);

		iAuditHouseService.cancelTask(Integer.valueOf(houseId), house_type, false);
	}
	
	/**
	 * @param houseIdIntList
	 * @param type
	 * <br>
	 *            抽取出的对houseIdList里边的所有图片进行审核通过.type为3种图片类型
	 */
	public void toAuditPass(List<Integer> houseIdIntList, Integer type,Integer house_type) {
		// 没有查到任何区域房源信息
		if (null == houseIdIntList || houseIdIntList.size() < 1 || null == type) {
			return;
		}
		switch (type) {
		case 1:
			this.auditPass(houseIdIntList, Globals.AUDIT_HOUSEHOLD_PHOTO, house_type);
			break;
		case 2:
			this.auditPass(houseIdIntList, Globals.AUDIT_INDOOR_PHOTOO, house_type);
			break;
		case 3:
			this.auditPass(houseIdIntList, Globals.AUDIT_ESTATE_PHOTO, house_type);
			break;
		}
	}
	
	public void reauditAgent(AgentVerifyResultForm resultForm, String subHistoryId,  User user){
		if(StringUtils.isBlank(subHistoryId)){
			log.error("经纪人复审时传入的子表id为空！");
			return ;
		}
		if(user==null){
			log.error("经纪人复审时审核人为空，请重新登录！");
			return ;
		}
		Integer subHistoryIdInt=Integer.valueOf(subHistoryId);
		//审核结果
		Integer resultInt=null;
		//历史信息
		HistoryInfo historyInfo=null;
		try {
			historyInfo = auditHistoryService.selectHistoryInfoById(subHistoryIdInt);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(historyInfo==null){
			log.error("经纪人复审时查找审核历史失败！");
			return ;
		}
		/*复审逻辑开始*/
		if(resultForm==null){
			log.info("经纪人复审结果为空！");
			return;
		}
		AgentMaster agentMaster = agentMasterService.selectAgentMasterById(resultForm.getAgentId());
		if(resultForm.getHeadResult()==null){
			resultForm.setHeadResult(agentMaster.isHeadOk()?Globals.AUDIT_RESULT_PASS: Globals.AUDIT_RESULT_REJECT);
			if(resultForm.getHeadResult()==Globals.AUDIT_RESULT_REJECT){
				resultForm.setHeadMsg(agentMaster.getMsgHead());
			}
		}
		if(resultForm.getIdCardResult()==null){
			resultForm.setIdCardResult(agentMaster.isIdcardOk()?Globals.AUDIT_RESULT_PASS: Globals.AUDIT_RESULT_REJECT);
			if(resultForm.getIdCardResult()==Globals.AUDIT_RESULT_REJECT){
				resultForm.setIdCardMsg(agentMaster.getMsgIdcard());
			}
		}
		if(resultForm.getHeadResult()==Globals.AUDIT_RESULT_PASS&&resultForm.getIdCardResult()==Globals.AUDIT_RESULT_PASS){
			resultInt=Globals.AUDIT_RESULT_PASS;
		}else{
			resultInt=Globals.AUDIT_RESULT_REJECT;
		}
		//复审调用原来的审核方法
		agentMasterService.doVerify(resultForm, user.getId());
		/*复审逻辑结束*/
		
		//记录审核历史
		auditHistoryService.recordReauditLog(historyInfo,""+resultInt,user);
		log.info("经纪人复审记录历史！");
		
	}

	/**
	 * 复审相关
	 * @param subHistoryId 审核历史子表id
	 * @param result 审核结果
	 * @param user 处理人
	 * @author wangjh
	 * Dec 23, 2011 2:15:17 PM
	 * @throws Exception 
	 */
	public void reaudit(String subHistoryId, String result, User user,String reason) throws Exception {
		if(StringUtils.isBlank(subHistoryId)){
			log.error("复审时传入的子表id为空！");
			return ;
		}
		if(StringUtils.isBlank(result)){
			log.error("复审时传入的审核结果为空！");
			return ;
		}
		Integer subHistoryIdInt=Integer.valueOf(subHistoryId);
		//审核结果
		Integer resultInt=Integer.valueOf(result);
		//历史信息
		HistoryInfo historyInfo=auditHistoryService.selectHistoryInfoById(subHistoryIdInt);
		if(historyInfo==null){
			log.error("复审时查找审核历史失败！");
			return ;
		}
		if(StringUtils.isNotBlank(reason)){
			historyInfo.setRejectReason(reason);
		}
		//处理审核业务
		//复审时基本信息处理的业务逻辑
		if(Globals.AUDIT_HISTORY_BASEINFO==historyInfo.getAuditStep()){
			this.reauditBaseInfo(historyInfo,resultInt,user);
			log.info("复审房源基本信息！");
		}
		
		if (Globals.AUDIT_HISTORY_BASEINFO > historyInfo.getAuditStep()) {
			if (historyInfo.getHouseType().intValue() == Globals.HOUSE_TYPE_RENT) {
				rentVerifyService.reAuditPhoto(historyInfo, resultInt, user);
			} else if (historyInfo.getHouseType().intValue() == Globals.HOUSE_TYPE_RESALE) {
				resaleVerifyService.reAuditPhoto(historyInfo, resultInt, user);
			}
		}
		
		//记录审核历史
		auditHistoryService.recordReauditLog(historyInfo,result,user);
		log.info("复审记录历史！");
		
	}

	/**
	 * 领取但未审核的房源数量
	 * @param type
	 * @param auditStep
	 * @return
	 * @author wangjh
	 * Dec 27, 2011 4:47:59 PM
	 */
	public Integer hasNotHandleCount(Integer type,Integer auditStep) {
		Integer count=0;
		List<Integer> list=auditDao.hasNotHandleCount(type,auditStep,Globals.AUDIT_RESULT_UNDISTRIBUTED);
		if(CollectionUtils.isEmpty(list)){
			return 0;
		}
		count=list.size();
		return count;
	}
	
	
	public void emptyAuditTaskByHouseIdList(List<Integer> houseIdList,Integer houseType){
		auditDao.emptyAuditTaskByHouseIdList(houseIdList,houseType);
	}

	/**
	 * 通过用户id清空一定数量的用户领取的任务
	 * @param userIdList 用户id
	 * @param num 清空数量
	 * @author wangjh
	 * Dec 30, 2011 4:07:14 PM
	 */
	public void emptyAuditTask(List<Integer> userIdList,Integer num){
		if(CollectionUtils.isEmpty(userIdList)){
			log.info("清空用户领取的任务时，传入的用户id为空！");
			return;
		}
		List<AuditTask> taskList=auditDao.getAuditTaskByUserIdList(userIdList, num);
		if(CollectionUtils.isEmpty(taskList)){
			return;
		}
		log.info("清空用户领取的任务。");
		//符合清空条件的任务id
		List<Integer> baseInfoTaskIdList=new ArrayList<Integer>();
		List<Integer> indoorPhotoTaskIdList=new ArrayList<Integer>();
		List<Integer> layoutPhotoTaskIdList=new ArrayList<Integer>();
		List<Integer> estatePhotoTaskIdList=new ArrayList<Integer>();
		//筛选任务
		for (AuditTask auditTask : taskList) {
			if(auditTask==null){
				continue;
			}
			Integer taskId=auditTask.getId();
			Integer baseInfo=auditTask.getBaseInfo();
			if(userIdList.contains(baseInfo)){
				//基本信息的任务领取人 在  清空的用户id中
				baseInfoTaskIdList.add(taskId);
			}
			Integer indoorPhoto=auditTask.getIndoorPhoto();
			if(userIdList.contains(indoorPhoto)){
				//室内图的任务领取人 在  清空的用户id中
				indoorPhotoTaskIdList.add(taskId);
			}
			Integer layoutPhoto=auditTask.getHouseholdPhoto();
			if(userIdList.contains(layoutPhoto)){
				//户型图的任务领取人 在  清空的用户id中
				layoutPhotoTaskIdList.add(taskId);
			}
			Integer estatePhoto=auditTask.getEstatePhoto();
			if(userIdList.contains(estatePhoto)){
				//小区图的任务领取人 在  清空的用户id中
				estatePhotoTaskIdList.add(taskId);
			}
		}
		//清空任务
		Integer auditStep=null;
		if(CollectionUtils.isNotEmpty(baseInfoTaskIdList)){
			auditStep=Globals.AUDIT_BASE_INFO;
			auditDao.updateAuditTaskAuditUser(baseInfoTaskIdList, auditStep, Globals.AUDIT_RESULT_UNDISTRIBUTED);
		}
		if(CollectionUtils.isNotEmpty(indoorPhotoTaskIdList)){
			auditStep=Globals.AUDIT_INDOOR_PHOTOO;
			auditDao.updateAuditTaskAuditUser(indoorPhotoTaskIdList, auditStep, Globals.AUDIT_RESULT_UNDISTRIBUTED);
			
		}
		if(CollectionUtils.isNotEmpty(layoutPhotoTaskIdList)){
			auditStep=Globals.AUDIT_HOUSEHOLD_PHOTO;
			auditDao.updateAuditTaskAuditUser(layoutPhotoTaskIdList, auditStep, Globals.AUDIT_RESULT_UNDISTRIBUTED);
			
		}
		if(CollectionUtils.isNotEmpty(estatePhotoTaskIdList)){
			auditStep=Globals.AUDIT_ESTATE_PHOTO;
			auditDao.updateAuditTaskAuditUser(estatePhotoTaskIdList, auditStep, Globals.AUDIT_RESULT_UNDISTRIBUTED);
			
		}
	}
	public void emptyAgentAuditTask(List<Integer> userIdList,Integer num){
		if(CollectionUtils.isEmpty(userIdList)){
			log.info("清空用户领取的任务时，传入的用户id为空！");
			return;
		}
		//
		/*List<AgentAuditTask> taskList=auditDao.getAgentAuditTaskByUserIdList(userIdList, num);
		if(CollectionUtils.isEmpty(taskList)){
			return;
		}
		log.info("清空用户领取的任务。");
		//符合清空条件的任务id
		List<Integer> idList=new ArrayList<Integer>();
		//筛选任务
		for (AgentAuditTask auditTask : taskList) {
			if(auditTask==null){
				continue;
			}
			Integer id=auditTask.getAgentId();
			//经纪人的任务领取人 在  清空的用户id中
			idList.add(id);
			
		}*/
		//清空任务
		if(CollectionUtils.isNotEmpty(userIdList)){
			auditDao.emptyAgentTask(userIdList, Globals.AUDIT_RESULT_UNDISTRIBUTED);
		}
		
	}
	
	
	/**************经纪人审核*****************/
	//获取经纪人审核任务
	public List<AgentMaster> getAgentMasterTask(Integer num, Integer auditUserId, Integer cityId) {
		// 当前审核人员的任务标记
		String auditSign = this.generationMark(auditUserId);
		// 获取需要添加任务的数量
		Integer needTaskNum = this.getAgentMasterTaskCount(auditUserId, cityId);
		if (needTaskNum > 0) {
			// 向任务列表中添加二手房审核任务
			this.buildAgentTask(auditSign, needTaskNum, auditUserId, cityId,this.getAgentAuditStatus());
			log.info("创建经纪人审核任务" + needTaskNum + "条。");
		}
		//获取待审核经济人id
		List<Integer> agentIds=auditDao.getAgentIdList(auditUserId, num, cityId);
		List<AgentMaster> agentMasterList=new ArrayList<AgentMaster>();
		// //审核人员领取任务
		if(CollectionUtils.isEmpty(agentIds)){
			log.info("符合审核条件的经纪人为空！");
			return agentMasterList;
		}
		agentMasterList = agentMasterService.getAgentByIds(agentIds);
		for(AgentMaster agent:agentMasterList){
			agent.setHeadImg(AgentPhotoUrlUtil.getPendingAgentHeadPortraitFullUrlLarge(agent.getCityId(), agent.getAgentId())+"?"+new Date().getTime());
			agent.setIdCardImg(AgentPhotoUrlUtil.getAgentIDCardPhotoFullUrl(agent.getCityId(), agent.getAgentId())+"?"+new Date().getTime());
			agent.setNameCardImg(AgentPhotoUrlUtil.getAgentBusinessCardPhotoFullUrl(agent.getCityId(), agent.getAgentId())+"?"+new Date().getTime());
		}
		log.info("从任务列表经纪人审核任务。");
		return agentMasterList;

	}
	
	
	/**
	 * 获取经纪人可以被审核的状态
	 * @return
	 * @author wangjh
	 * Jan 10, 2012 12:55:50 PM
	 */
	public List<Integer> getAgentAuditStatus(){
		//从原AgentVerifyController 的search方法中找到
		List<Integer> list=new ArrayList<Integer>();
		/*221','231','321','331','121','131','311*/
		list.add(221);
		list.add(231);
		list.add(321);
		list.add(331);
		list.add(121);
		list.add(131);
		list.add(311);
		return list;
	}

	private void buildAgentTask(String auditSign, Integer num,
			Integer auditUserId, Integer cityId,List<Integer> auditStatusList) {
		// 标记
		List<Integer> idList = this.signAgent(auditSign, auditStatusList, num, cityId);
		// 向审核任务中添加
		List<AgentMaster> agentMasterList = null;
		if (CollectionUtils.isNotEmpty(idList)) {
			agentMasterList = agentMasterService.getAgentInfoCity(idList);
		}
		if (CollectionUtils.isNotEmpty(agentMasterList)) {
			for (AgentMaster agentMaster : agentMasterList) {
				//只通过身份证判断任务。如果经纪人审核改为(身份证头像)分开审核 则须将此方法修改，增加type
				this.buileAgentTaskByParam(agentMaster.getAgentId(),  auditUserId, agentMaster.getCityId());
			}
		}
//		try {
//			List<AuditHistory> auditHistory = null;
//			auditHistory= agentMasterService.getHistoryInfo(idList);
//			if(CollectionUtils.isNotEmpty(auditHistory)){
//				for (AuditHistory auditHistory2 : auditHistory) {
//					auditHistoryService.saveAuditHistory(auditHistory2);
//				}
//			}
//			log.info("新建审核历史。");
//		} catch (Exception e) {
//			log.error("保存审核历史出错！", e);
//		}
	}



	private void buileAgentTaskByParam(Integer agentId, Integer auditUserId,
			Integer cityId) {
		auditDao.buileAgentTaskByParam(agentId, auditUserId,
				cityId);
	}

	private List<Integer> signAgent(String auditSign,
			List<Integer> auditStatusList, Integer num, Integer cityId) {
		/**
		 * update 不能使用limit
		 */
		List<Integer> idList = null;
		try {
			idList = agentMasterService.getAgentIdForAudit(auditSign, num, cityId,this.getAgentAuditStatus());
		} catch (Exception e) {
			log.error(e);
		}
		try {
			if (CollectionUtils.isNotEmpty(idList)) {
				agentMasterService.signAgent(auditSign, idList);
			}
		} catch (Exception e) {
			log.error("标记经纪人任务出错！   "+e);
			e.printStackTrace();
		}
		return idList;
	}

	private Integer getAgentMasterTaskCount(Integer auditUserId, Integer cityId) {
		// 清空任务列表中该用户领取的任务（如果不清空，他误领的任务别人就无法审核了）
		if (auditUserId != null) {
			List<Integer> userIdList = new ArrayList<Integer>();
			userIdList.add(auditUserId);
			// 清空任务的个数（领取任务到一定数量时，每次清空的任务就会控制在20条以内）
			Integer emptyNum = Globals.EMPTY_TASK_COUNT;
			this.emptyAgentAuditTask(userIdList, emptyNum);
		}
		List<Integer> agentIdList = null;
		// 不符合审核条件的经纪人id
		List<Integer> inconformityAgentIdList = null;
		// 任务数量
		Integer taskCount = 0;
		// 获取经纪人的数量
		Integer num=Globals.AUDIT_AGENT_COUNT;
		// 存在不符合审核条件的经纪人，删除任务并再次获取任务
		boolean flag = true;
		Integer n = 1;
		while (flag) {
			// 任务列表获取审核任务  返回经纪人id
			log.debug("第" + n + "次获取经纪人审核任务。");
			//只通过身份证判断任务。如果经纪人审核改为分开审核 则须将此方法修改，增加type
			agentIdList = auditDao.getAgentIdList(auditUserId,num,cityId);
			if (CollectionUtils.isNotEmpty(agentIdList)) {
				/* 判断获取的任务是否符合审核条件，把不符合的id取出 */
				inconformityAgentIdList = agentMasterService
						.getInconformityAgentIds(agentIdList,this.getAgentAuditStatus());
				/* 从任务列表中删除不符合审核条件的任务，更改经纪人的审核标记为null，（批量取消任务和标记） */
				if (CollectionUtils.isNotEmpty(inconformityAgentIdList)) {
					agentMasterService.deleteAuditTask(inconformityAgentIdList);
					log.info("删除获取的任务中不符合审核条件的任务 "
							+ inconformityAgentIdList.size() + " 条！");
					if (log.isDebugEnabled()) {
						for (Integer integer : inconformityAgentIdList) {
							log.debug("删除的任务id：" + integer);
						}
					}
					n++;
					flag = true;
				}
			}
			if (CollectionUtils.isEmpty(inconformityAgentIdList)) {
				flag = false;
			}
		}
		if (CollectionUtils.isNotEmpty(agentIdList)) {
			//只通过身份证判断任务。如果经纪人审核改为分开审核 则须将此方法修改，增加任务type
			auditDao.updateAgentTaskUser(agentIdList, auditUserId);
			taskCount = agentIdList.size();
		}
		if (taskCount >= Globals.AUDIT_AGENT_COUNT) {
			// 不需要从待审核房源中领取任务
			return 0;
		}
		if (taskCount < Globals.AUDIT_AGENT_COUNT) {
			// 需要获取的任务数量
			return Globals.AUDIT_AGENT_COUNT - taskCount;
		}
		return taskCount;
	}

	public Integer hasNotHandleAgentCount() {
		Integer count=0;
		List<Integer> list=auditDao.hasNotHandleAgentCount(Globals.AUDIT_RESULT_UNDISTRIBUTED);
		if(CollectionUtils.isEmpty(list)){
			return 0;
		}
		count=list.size();
		return count;
	}
	
	/**
	 *  删除任务清空任务标记( 只清空标记，不改变房源状态 )
	 * @param houseIdList
	 * @param auditType
	 * @author wangjh
	 * Feb 9, 2012 1:33:53 PM
	 */
	public void deleteAuditTask(List<Integer> houseIdList, Integer auditType) {
		if(auditType==Globals.HOUSE_TYPE_RENT){
			rentVerifyService.deleteAuditTask(houseIdList, auditType);
		}
		if(auditType==Globals.HOUSE_TYPE_RESALE){
			resaleVerifyService.deleteAuditTask(houseIdList, auditType);
		}
	}
	
	/**
	 * 审核人员提取问答的审核任务
	 * 
	 * @param num
	 *            提取任务数量
	 * @param auditUserId
	 *            提取人员id
	 * @return
	 * @author wangjh Nov 25, 2011 4:55:56 PM
	 */
	public List<WenDaAuditTask> getWenDaTask(Integer num, Integer auditUserId, Integer type, Integer cityId) {
		// 状态是未审核
		Integer status = null;
		if(Globals.WEN_DA_TYPE_QUESTION==type.intValue()){
			status=Globals.QUESTION_STATUS_WAIT_VERIFY;
		}
		if(Globals.WEN_DA_TYPE_ANSWER==type.intValue()){
			status=Globals.ANSWER_STATUS_WAIT_VERIFY;
		}
		// 当前审核人员的任务标记
		String auditSign = this.generationMark(auditUserId);
		// 获取需要添加任务的数量
		Integer needTaskNum = this.getWenDaTaskCount(auditUserId, type, cityId,num);
		if (needTaskNum > 0) {
			// 向任务列表中添加审核任务
			this.buildWenDaTask(auditSign, type, status, needTaskNum, auditUserId, cityId);
		}
		// //审核人员从审核列表领取任务
		List<WenDaAuditTask> wenDaList = wenDaVerifyService.getAuditTask(auditUserId,type,cityId,num);
		return wenDaList;

	}

	private Integer getWenDaTaskCount(Integer auditUserId, Integer type,
			Integer cityId, int num) {
		// 清空任务列表中该用户领取的任务（如果不清空，他误领的任务别人就无法审核了）
		if (auditUserId != null) {
			List<Integer> userIdList = new ArrayList<Integer>();
			userIdList.add(auditUserId);
			// 清空任务的个数
			Integer emptyNum = Globals.EMPTY_TASK_COUNT;
			this.emptyWenDaAuditTask(userIdList, emptyNum);
		}
		List<Integer> wenDaIdList = null;
		// 任务数量
		int taskCount = 0;
		wenDaIdList = auditDao.getWenDaIdList(auditUserId,cityId,num,type);
		if (CollectionUtils.isNotEmpty(wenDaIdList)) {
			auditDao.updateWenDaTaskUser(wenDaIdList, auditUserId,type);
			taskCount = wenDaIdList.size();
		}
		if (taskCount >= num) {
			// 不需要从待审核房源中领取任务
			return 0;
		}
		if (taskCount < num) {
			// 需要获取的任务数量
			return num - taskCount;
		}
		return taskCount;
	}

	/**
	 * 清空用户领取的任务
	 * @param userIdList
	 * @param emptyNum
	 * @author wangjh
	 * May 15, 2012 4:47:46 PM
	 */
	public  void emptyWenDaAuditTask(List<Integer> userIdList, Integer emptyNum) {
		if(CollectionUtils.isEmpty(userIdList)){
			log.info("清空用户领取的任务时，传入的用户id为空！");
			return;
		}
		//清空任务
		auditDao.emptyWenDaTask(userIdList, Globals.AUDIT_RESULT_UNDISTRIBUTED,emptyNum);
	}
	/**
	 * 删除问答
	 * @param userIdList
	 * @param emptyNum
	 * @author wangjh
	 * May 15, 2012 4:47:46 PM
	 */
	public  void deleteWenDaTaskForBlackList(List<Integer> userIdList, Integer emptyNum) {
		if(CollectionUtils.isEmpty(userIdList)){
			log.info("清空用户领取的任务时，传入的用户id为空！");
			return;
		}
		//清空任务
		auditDao.deleteWenDaTaskForBlackList(userIdList, emptyNum);
	}
	
	/**
	 * 创建问答审核任务到任务列表
	 * @param auditSign 任务标记
	 * @param type 类型（问题？答案？）
	 * @param status 符合审核的状态
	 * @param needTaskNum 需要创建的任务数
	 * @param auditUserId 审核人id
	 * @param cityId 城市id
	 * @author wangjh
	 * Mar 1, 2012 4:01:51 PM
	 */
	public void buildWenDaTask(String auditSign, Integer type, Integer status,
			Integer needTaskNum, Integer auditUserId, Integer cityId) {
		// 标记
		List<Integer> idList = this.signWenDa(auditSign,type, status, needTaskNum, cityId);
		// 向审核任务中添加
		List<WenDaAuditTask> list = null;
		if (CollectionUtils.isNotEmpty(idList)) {
			synchronized (this) {
				list = this.getWenDaInfo(idList, type);
				if (CollectionUtils.isNotEmpty(list)) {
					for (WenDaAuditTask task : list) {
						task.setAuditorId(auditUserId);
						this.saveWenDaTask(task);
					}
				}
			}
		}
	}
	
	private void saveWenDaTask(WenDaAuditTask task) {
		auditDao.saveWenDaTask(task);
	}

	private List<WenDaAuditTask> getWenDaInfo(List<Integer> idList,Integer type) {
		List<WenDaAuditTask> list=wenDaVerifyService.getWenDaInfo(idList,type);
		return list;
		
	}

	private List<Integer> signWenDa(String auditSign, Integer type,
			Integer status, Integer num, Integer cityId) {
		/**
		 * update 不能使用limit
		 */
		List<Integer> idList = null;
		try {
			synchronized (this) {
				idList = wenDaVerifyService.getIdForAudit(num, cityId, status, type);
				if (CollectionUtils.isNotEmpty(idList)) {
					wenDaVerifyService.sign(auditSign, idList, type);
				}
			}
		} catch (Exception e) {
			log.error("标记问答任务出错！   " + e);
			e.printStackTrace();
		}
		return idList;
	}

	/**
	 * 审核问答
	 * 
	 * @param passIdList
	 *            通过的id集合
	 * @param rejectIdList
	 *            不通过的id集合
	 * @author wangjh Mar 2, 2012 5:59:19 PM
	 */
	public void auditWenDa(List<Integer> passIdList,
			List<Integer> rejectIdList, Integer type) {
			List<Integer> allIdList=new ArrayList<Integer>();
			if(CollectionUtils.isNotEmpty(passIdList)){
				wenDaVerifyService.pass(passIdList,type);
				allIdList.addAll(passIdList);
				log.info("本次审核通过的问题有 "+passIdList.size()+" 条。");
			}
			if(CollectionUtils.isNotEmpty(rejectIdList)){
				wenDaVerifyService.reject(rejectIdList,type);
				allIdList.addAll(rejectIdList);
				log.info("本次审核不通过的问题有 "+rejectIdList.size()+" 条。");
			}
			if(CollectionUtils.isNotEmpty(allIdList)){
				this.deleteWenDaTask(allIdList, type);
			}
	}

	/**
	 * 只清除任务列表(注:问答中的审核状态不清除)
	 * @param allIdList 要清除的问答id
	 * @param type 问题答案类型
	 * @author wangjh
	 * Mar 2, 2012 6:17:45 PM
	 */
	private void deleteWenDaTask(List<Integer> idList, Integer type) {
		auditDao.deleteWenDaTask(idList,type);
	}

	/**
	 * 返回colorbox页面(审核过的一页的数据)
	 * @param photoids
	 * @param auditids
	 * @param results
	 * @return
	 * @date 2012-4-17上午11:38:15
	 * @author dongyj
	 * @param request 
	 */
	public List<HistoryInfo> getPhotos(HttpServletRequest request, String photoids, String audit_ids, String auditStep, String results, String house_type) {
		List<HistoryInfo> list = null;
		List<Integer> photoIdList = getIntegerListFromStringByComma(photoids);
		request.getSession().setAttribute("cur_photoIdList", photoIdList);
		List<Integer> auditIntegerIdList = getIntegerListFromStringByComma(audit_ids);
		request.getSession().setAttribute("cur_auditIdList", auditIntegerIdList);

		String[] photoidArray = photoids.split(",");
		String[] auditidArray = audit_ids.split(",");
		String[] resultArray = results.split(",");
		int length1 = photoidArray.length;
		int length2 = resultArray.length;
		int length3 = auditidArray.length;
		if (!((length1 == length2) && (length2 == length3))) {
			return list;
		}
		List<String> passList = new ArrayList<String>();
		List<String> failAndReauditList = new ArrayList<String>();
		List<String> failList = new ArrayList<String>();
		List<String> auditIdList = new ArrayList<String>();

		// 组成通过List和打回List,分别去photo和reject_photo去取url
		for (int i = 0; i < length1; i++) {
			String onePhotoId = photoidArray[i];
			String oneResult = resultArray[i];
			if (oneResult.equals("-1")) {
				passList.add(onePhotoId);
			} else if (oneResult.equals("-2")) {
				failList.add(onePhotoId);
			}
			String oneAuditId = auditidArray[i];
			auditIdList.add(oneAuditId);
		}

		// 得到此页面的复审结果 
		String thisHistoryId = (String) request.getSession().getAttribute("thisHistoryId");
		List<ReauditHistory> reauditHistories = auditDao.getReauditHstryByNewSubId(thisHistoryId);
		Map<Integer, Integer> photoId_reResultMap = new HashMap<Integer, Integer>();
		for (ReauditHistory reauditHistory : reauditHistories) {
			photoId_reResultMap.put(reauditHistory.getPhoto_id(), reauditHistory.getResult());
			// 如果原结果为违规,并且已复审,则要去photo里边去找.不能去reject_photo,反之不用管,因为photo内信息不会删除
			if (reauditHistory.getResult() == -1) {
				failAndReauditList.add("" + reauditHistory.getPhoto_id());
			}
		}
		if (CollectionUtils.isNotEmpty(failAndReauditList)) {
			list = rentDao.getNewPhotos(failAndReauditList, auditStep, Integer.parseInt(house_type), -1);
			for (HistoryInfo historyInfo : list) {
				historyInfo.setAuditResult(-2);
			}
		}

		// 得到所有房源的各种信息(装修/户型)
		List<SubAuditHistory> auditIdhouseIdList = auditHistoryService.getHouseIdsByAuditIds(auditIdList);
		// 主表id和house_id对应
		Map<Integer, Integer> auditIdhouseIdMap = null;
		List<Integer> houseIdList = null;
		if (CollectionUtils.isNotEmpty(auditIdhouseIdList)) {
			houseIdList = new ArrayList<Integer>();
			auditIdhouseIdMap = new HashMap<Integer, Integer>();
			for (SubAuditHistory subAuditHistory : auditIdhouseIdList) {
				auditIdhouseIdMap.put(subAuditHistory.getId(), subAuditHistory.getHouseId());
			}
			// 带有重复houseId的List,和photoids,auditids对应
			for (String auditIdString : auditIdList) {
				houseIdList.add(auditIdhouseIdMap.get(Integer.parseInt(auditIdString)));
			}
			request.getSession().setAttribute("cur_houseIdList", houseIdList);
		}
		// 先得到违规的,再得到正常的
		if (failList.size() > 0) {
			if (CollectionUtils.isEmpty(list)) {
				list = new ArrayList<HistoryInfo>();
			}
			list.addAll(rentDao.getNewPhotos(failList, auditStep, Integer.parseInt(house_type), -2));
		}
		if (passList.size() > 0) {
			if (CollectionUtils.isEmpty(list)) {
				list = new ArrayList<HistoryInfo>();
			}
			list.addAll(rentDao.getNewPhotos(passList, auditStep, Integer.parseInt(house_type), -1));
		}
		
		// 填充图片信息
		Map<Integer, String> map = new HashMap<Integer, String>();
		if (house_type.equals("" + Globals.HOUSE_TYPE_RENT)) {
			// houseid对应图片信息
			List<RentInfo> rentInfos = rentDao.getPhotoExtraInfo(Integer.parseInt(auditStep), houseIdList);
			for (RentInfo rentInfo : rentInfos) {
				map.put(rentInfo.getId(), rentInfo.getExtinfo());
			}
		} else if (house_type.equals("" + Globals.HOUSE_TYPE_RESALE)) {
			List<ResaleInfo> resaleInfos = resaleDao.getPhotoExtraInfo(Integer.parseInt(auditStep), houseIdList);
			for (ResaleInfo resaleInfo : resaleInfos) {
				map.put(resaleInfo.getId(), resaleInfo.getExtinfo());
			}
		}
		for (HistoryInfo historyInfo : list) {
			int index = photoIdList.indexOf(historyInfo.getPhotoId());
			String extinfo = map.get(auditIdhouseIdMap.get(auditIntegerIdList.get(index)));
			// 违规图片
			if (StringUtils.isNotBlank(historyInfo.getRejectReason())) {
				historyInfo.setExtra("违规原因: " + historyInfo.getRejectReason() + "　　　");
			}
			if (StringUtils.isNotBlank(extinfo)) {
				if (!auditStep.equals("2")) {
					historyInfo.setExtra((StringUtils.isBlank(historyInfo.getExtra()) ? "" : historyInfo.getExtra()) + "图片信息: " + extinfo);
				} else {
					historyInfo.setExtra((StringUtils.isBlank(historyInfo.getExtra()) ? "" : historyInfo.getExtra()) + "图片信息: "
							+ ApplicationUtil.getDecoration(Integer.parseInt(extinfo)));
				}
			}
		}

		for (HistoryInfo historyInfo : list) {
			// 此照片复审过
			Integer reResult = photoId_reResultMap.get(historyInfo.getPhotoId().intValue());
			if (null != reResult) {
				historyInfo.setReResult(reResult);
			}
		}
		return list;
	}

	private final List<Integer> getIntegerListFromStringByComma(String ids) {
		List<Integer> integers = new ArrayList<Integer>();
		if (StringUtils.isNotBlank(ids)) {
			String[] array = ids.split(",");
			for (String string : array) {
				integers.add(Integer.parseInt(string));
			}
		}
		return integers;
	}
	
	/**
	 * 处理任务列表中所有状态都为已审核但是房源状态未改变的审核任务
	 * 
	 * @author wangjh
	 * Sep 10, 2012 4:48:43 PM
	 */
	public int dealAnomalyTask(){
		int num=200;
		int dealCount=0;
		//出租房处理
		int rentCount=0;
		int type=Globals.HOUSE_TYPE_RENT;
		rentCount=auditDao.getAnomalyTaskCountByType(type);
		while(rentCount!=0){
			//查询状态都为-1但是未完成的房源id
			List<Integer> houseIdList=auditDao.getAnomalyHouseIdByType(type,num);
			if(houseIdList.size()==0){
				break;
			}
			//调用finalOutcome方法处理
			dealCount+=rentVerifyService.finalOutcome(houseIdList, type, 0);
			rentCount=auditDao.getAnomalyTaskCountByType(type);
		}
		//二手房处理
		type=Globals.HOUSE_TYPE_RESALE;
		int resaleCount=0;
		resaleCount=auditDao.getAnomalyTaskCountByType(type);
		while(resaleCount!=0){
			//查询状态都为-1但是未完成的房源id
			List<Integer> houseIdList=auditDao.getAnomalyHouseIdByType(type,num);
			if(houseIdList.size()==0){
				break;
			}
			//调用finalOutcome方法处理
			dealCount+=resaleVerifyService.finalOutcome(houseIdList, type, 0);
			resaleCount=auditDao.getAnomalyTaskCountByType(type);
		}
		return dealCount;
	}
	/**
	 * 查看图片抽取白名单，黑名单列表
	 * @param cityId
	 * @param type
	 * @param agentId
	 * @return
	 */
	public List<AuditAgentList> getPhotoNameList(int cityId,int type,int agentId) {
		// TODO Auto-generated method stub
		return auditDao.getPhotoNameList(cityId,type,agentId);
	}
/**
 * 设置图片抽取，白名单，黑名单
 * @param ids
 * @param type
 * @return
 */
	public int updatePhotoNameList(List<String> ids,int type) {
		return auditDao.updatePhotoNameList(ids,type);
	}

public List<AuditPhotoSetting> getPhotoSetting(int cityId) {
	// TODO Auto-generated method stub
	return auditDao.getPhotoSetting(cityId);
}
/**
 * 更新图片审核设置
 * @param auditPhotoSetting
 * @return
 */
public int updatePhotoSetting(AuditPhotoSetting auditPhotoSetting) {
	// TODO Auto-generated method stub
	return auditDao.updatePhotoSetting(auditPhotoSetting);
}

public List<HistoryInfo> getReAuditHistory(int houseId, int houseType) {
	
	return auditHistoryService.getReAuditHistory(houseId,houseType);
}

}
