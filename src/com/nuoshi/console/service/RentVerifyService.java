package com.nuoshi.console.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import com.taofang.biz.domain.constants.HouseConstant;
import com.taofang.biz.service.house.IHousePhotoService;
import com.taofang.biz.util.BizServiceHelper;
import com.nuoshi.console.common.Globals;
import com.nuoshi.console.common.Resources;
import com.nuoshi.console.dao.AgentActionLogDao;
import com.nuoshi.console.dao.AgentMasterDao;
import com.nuoshi.console.dao.HousePhotoDao;
import com.nuoshi.console.dao.PhotoDao;
import com.nuoshi.console.dao.RentDao;
import com.nuoshi.console.domain.agent.AgentMaster;
import com.nuoshi.console.domain.agent.HousePhoto;
import com.nuoshi.console.domain.agent.RejectReason;
import com.nuoshi.console.domain.audit.AuditReject;
import com.nuoshi.console.domain.audit.AuditStep;
import com.nuoshi.console.domain.auditHistory.AuditHistory;
import com.nuoshi.console.domain.auditHistory.HistoryInfo;
import com.nuoshi.console.domain.auditHistory.SubAuditHistory;
import com.nuoshi.console.domain.base.House;
import com.nuoshi.console.domain.base.RejectPhoto;
import com.nuoshi.console.domain.pckage.AgentActionLog;
import com.nuoshi.console.domain.photo.Photo;
import com.nuoshi.console.domain.reason.InvalidReason;
import com.nuoshi.console.domain.rent.Rent;
import com.nuoshi.console.domain.rent.RentInfo;
import com.nuoshi.console.domain.resale.Resale;
import com.nuoshi.console.domain.user.User;
import com.nuoshi.console.persistence.read.taofang.agent.TFUserReadMapper;
import com.nuoshi.console.persistence.write.admin.audit.AuditRejectWriteMapper;
import com.nuoshi.console.web.session.SessionUser;

@Service
public class RentVerifyService extends HouseService {
	@Resource
	private RentDao rentDao;
	@Resource
	private PhotoDao photoDao;
	@Resource
	private HousePhotoDao housePhotoDao;
	@Resource
	private MessageService messageService;
	@Resource
	private TFUserReadMapper tfUserReadMapper;

	@Resource
	private AgentMasterDao agentMasterDao;

	@Resource
	private AgentActionLogDao agentActionLogDao;

	@Resource
	private AuditHistoryService auditHistoryService;
	@Resource
	private AuditRejectWriteMapper auditRejectWriteMapper;
	/**
	 * 获取审核历史记录
	 * @param houseIdList
	 * @return
	 * @author wangjh
	 * Dec 22, 2011 11:06:16 AM
	 */
	public List<SubAuditHistory> getSubHistoryInfo(List<Integer> houseIdList) {
		return rentDao.getSubHistoryInfo(houseIdList);
	}
	
	
	@SuppressWarnings("rawtypes")
	public List<RentInfo> getRent4VerifyByPage(HashMap params) {
		List<RentInfo> resultList = rentDao.getRent4VerifyByPage(params);

		return resultList;
	}

	@SuppressWarnings("rawtypes")
	public List<RentInfo> getAllRent4VerifyByPage(HashMap params) {
		List<RentInfo> resultList = rentDao.getAllRent4VerifyByPage(params);

		return resultList;
	}

	public List<HousePhoto> getHousePhoto(Integer houseId, String category) {
		return rentDao.getHousePhoto(houseId, category);
	}

	public List<RentInfo> getAllRent(Integer authorId) {

		return rentDao.getAllRent(authorId);
	}

	public List<RejectReason> getAllRejectReason(int type) {

		return rentDao.getAllRejectReason(type);
	}

	public String doVerify(Integer reslutType, HashMap<String, Object> params) {
		rentDao.doVerify(reslutType, params);
		return null;
	}

	public int approve(int id,User user) {
		Rent rent = rentDao.selectRentById(id);
		if (rent == null || rent.isDeleted()) {
			return -1;
		}
		//纪录一次错审
		auditHistoryService.handleReaudit(id,2,user,-1);
		
		Double score =  this.getHouseScore(rent);
		if(score>0){
			rentDao.updateHouseScore(id,score);
		}
		BizServiceHelper.getAuditHouseService().cancelTask(id, HouseConstant.RENT,false);
		int ret1 = rentDao.updateAuditStatus(id, House.AUDIT_STATUS_OK);
		int rec2 = rentDao.updateHouseStatus(id, House.HOUSE_STATUS_SHELVED);
		if (ret1 > 0 && rec2>0) {
			return 1;
		} else {
			return -1;
		}
	}

	/**
	 * 批量通过全部审核
	 * 
	 * @param houseIdList
	 *            房源id
	 * @author wangjh Dec 7, 2011 4:40:45 PM
	 */
	public void approve(List<Integer> houseIdList) {
		
		try {
			for (Integer houseid : houseIdList) {
				
				Double score =  this.getHouseScore(rentDao.selectRentById(houseid));
				if(score>0){
					rentDao.updateHouseScore(houseid,score);
				}
			}
		} catch (Exception e) {
			System.out.println("approve : "+e);
		}
		
		rentDao.updateAuditStatusByIdList(houseIdList, House.AUDIT_STATUS_OK, House.HOUSE_STATUS_SHELVED, null);

	}
	/**
	 * 批量设置出租房状态
	 * 
	 * @param houseIdList
	 *            房源id
	 * @author wangjh Dec 7, 2011 4:40:45 PM
	 */
	public void updateAuditStatusByIdList(List<Integer> houseIdList, Integer status, Integer houseStatus, String sign) {
		rentDao.updateAuditStatusByIdList(houseIdList, status, houseStatus, sign);
		
	}
	
	/**
	 * Admin 下架某一个房源
	 * 
	 * @param id
	 *            房源id
	 * @return HouseOpResult
	 */
	public int reject(int id, String reason,User user) {
		Rent rent = rentDao.selectRentById(id);
		if (rent == null || rent.isDeleted()) {
			return -1;
		}
		//纪录一次错审
		auditHistoryService.handleReaudit(id,2,user,-2);
		
		BizServiceHelper.getAuditHouseService().cancelTask(id, HouseConstant.RENT,false);
		AgentMaster author = tfUserReadMapper.selectAgentById(rent.getAuthorid());
		String viewUrl = "http://" + LocaleService.getCode(rent.getCityid()) + ".taofang.com/zufang/" + rent.getId() + "-" + 0 + ".html";
		messageService.sendRentViolateMessage(author, id, reason, viewUrl);

		int result = rentDao.rejectHouse(id);
		int ret = addAuditReject(rent, reason);
		if (result > 0 && ret > 0) {
			if (rent.getHouse_status() == House.HOUSE_STATUS_SHELVED||rent.getHouse_status() == House.HOUSE_STATUS_NOAUDIT_AGENT) {
				int result2 = agentMasterDao.incrementHouseNum(rent.getAuthorid());
				if (result2 < 1) {
					return -1;
				}
				agentActionLogDao.addAgentActionLog(rent.getAuthorid(), id, author.getPackageId(), AgentActionLog.ACTION_TYPE_HOUSE_NUM, Globals.RENT_TYPE,
						House.HOUSE_STATUS_SHELVED.toString(), House.HOUSE_STATUS_ILLEGAL.toString());
				if(rent.getLabels().size()>0) {
					if(rent.isVcr()) {
						int actionType = AgentActionLog.ACTION_TYPE_LABEL_VCR;
						agentActionLogDao.addAgentActionLog(rent.getAuthorid(), id, author.getPackageId(), actionType, Globals.RESALE_TYPE, House.HOUSE_ACTION_LABEL_VALUE, "null");
					}
					if(rent.isSskf()) {
						int actionType = AgentActionLog.ACTION_TYPE_LABEL_SSKF;
						agentActionLogDao.addAgentActionLog(rent.getAuthorid(), id, author.getPackageId(), actionType, Globals.RESALE_TYPE, House.HOUSE_ACTION_LABEL_VALUE, "null");
					}
					if(rent.isRecommend()) {
						int actionType = AgentActionLog.ACTION_TYPE_LABEL_XT;
						agentActionLogDao.addAgentActionLog(rent.getAuthorid(), id, author.getPackageId(), actionType, Globals.RESALE_TYPE, House.HOUSE_ACTION_LABEL_VALUE, "null");
					}
					
				
					return agentMasterDao.incrementLabelNum(rent.getAuthorid(),rent.getLabels().size(), rent.isVcr() ? 1 : 0);
				} 
			}
			return 1;
		} else {
			return -1;
		}
	}


	/**
	 * Admin 下架某一个房源
	 * 
	 * @param id
	 *            房源id
	 * @return HouseOpResult
	 */
	public int reject(int id, String reason) {
		Rent rent = rentDao.selectRentById(id);
		if (rent == null || rent.isDeleted()) {
			return -1;
		}
		BizServiceHelper.getAuditHouseService().cancelTask(id, HouseConstant.RENT,false);
		AgentMaster author = tfUserReadMapper.selectAgentById(rent.getAuthorid());
		String viewUrl = "http://" + LocaleService.getCode(rent.getCityid()) + ".taofang.com/zufang/" + rent.getId() + "-" + 0 + ".html";
		messageService.sendRentViolateMessage(author, id, reason, viewUrl);

		int result = rentDao.rejectHouse(id);
		int ret = addAuditReject(rent, reason);
		if (result > 0 && ret > 0) {
			if (rent.getHouse_status() == House.HOUSE_STATUS_SHELVED||rent.getHouse_status() == House.HOUSE_STATUS_NOAUDIT_AGENT) {
				int result2 = agentMasterDao.incrementHouseNum(rent.getAuthorid());
				if (result2 < 1) {
					return -1;
				}
				agentActionLogDao.addAgentActionLog(rent.getAuthorid(), id, author.getPackageId(), AgentActionLog.ACTION_TYPE_HOUSE_NUM, Globals.RENT_TYPE,
						House.HOUSE_STATUS_SHELVED.toString(), House.HOUSE_STATUS_ILLEGAL.toString());
				if(rent.getLabels().size()>0) {
					if(rent.isVcr()) {
						int actionType = AgentActionLog.ACTION_TYPE_LABEL_VCR;
						agentActionLogDao.addAgentActionLog(rent.getAuthorid(), id, author.getPackageId(), actionType, Globals.RESALE_TYPE, House.HOUSE_ACTION_LABEL_VALUE, "null");
					}
					if(rent.isSskf()) {
						int actionType = AgentActionLog.ACTION_TYPE_LABEL_SSKF;
						agentActionLogDao.addAgentActionLog(rent.getAuthorid(), id, author.getPackageId(), actionType, Globals.RESALE_TYPE, House.HOUSE_ACTION_LABEL_VALUE, "null");
					}
					if(rent.isRecommend()) {
						int actionType = AgentActionLog.ACTION_TYPE_LABEL_XT;
						agentActionLogDao.addAgentActionLog(rent.getAuthorid(), id, author.getPackageId(), actionType, Globals.RESALE_TYPE, House.HOUSE_ACTION_LABEL_VALUE, "null");
					}
					
				
					return agentMasterDao.incrementLabelNum(rent.getAuthorid(),rent.getLabels().size(), rent.isVcr() ? 1 : 0);
				} 
			}
			return 1;
		} else {
			return -1;
		}
	}

	/**
	 * @param rent
	 * @return
	 *//*
	public boolean doUnshelve(Rent rent) {
		int result = rentDao.updateHouseStatus(rent.getId(), House.HOUSE_STATUS_OUTDATE);
		int result1 = agentMasterDao.incrementHouseNum(rent.getAuthorid());
		if (result > 0 && result1 > 0) {
			if (rent.isFresh() || rent.isUrgent()) {
				int result2 = agentMasterDao.incrementLabelNum(rent.getAuthorid());
				return result2 > 0;
			}
		}
		return false; // delete it
	}*/

	/**
	 * 添加违规原因
	 * 
	 * @param id
	 * @param reason
	 * @return
	 */
	public int addInvalidReason(int id, String reason) {
		InvalidReason invalidreason = new InvalidReason();
		invalidreason.setId(id);
		invalidreason.setReason(reason);
		if (rentDao.insertRentInvalidReason(invalidreason) == -1) {
			return -1;
		}
		return 1;
	}
	
	public int addAuditReject(Rent rent, String reason) {
		AuditReject auditReject = new AuditReject();
		auditReject.setEntryTime(new Date());
		auditReject.setHouseId(rent.getId());
		auditReject.setHouseType(HouseConstant.RENT);
		auditReject.setLogId(0);
		auditReject.setAgentId(rent.getAuthorid());
		auditReject.setRejectReason(reason);
		return auditRejectWriteMapper.insertAuditReject(auditReject);
	}

	public void deleteHousePhoto(int houseid, int photoid, int category) {
		rentDao.deleteHousePhoto(houseid, photoid, category);
		Photo photo = photoDao.selectPhotoById(photoid);
		RejectPhoto rejectPhoto = new RejectPhoto();
		rejectPhoto.setHouseId(houseid);
		rejectPhoto.setPhotoBrowse(photo.getM());
		rejectPhoto.setPhotoId(photoid);
		rejectPhoto.setType(2);// 租房图片被删除
		rentDao.updatePhotoStatus(houseid, 1);// 更新状态，标示有图片被删除了

	}

	/**
	 * 审核人员标记房源为本人审核
	 * 
	 * @param auditStatus
	 *            被标记房源的审核状态
	 * @param houseStatus
	 *            被标记房源的发布状态
	 * @param num
	 *            标记数量
	 * @param auditUserId
	 *            审核人员
	 * @author wangjh Nov 29, 2011 11:19:55 AM
	 */
	private List<Integer> signHouse(String auditSign, Integer auditStatus, Integer houseStatus, Integer num, Integer cityId) {
		/**
		 * update 不能使用limit
		 */
		List<Integer> idList = new ArrayList<Integer>();
		try {
			synchronized (this) {
				List<Integer> isVipIdList = rentDao.getRentIdForAudit(auditSign, auditStatus, houseStatus, num, cityId,House.HOUSE_VIP_FLAG_YES);
				if(isVipIdList == null){
					isVipIdList = new ArrayList<Integer>();
				}
				idList.addAll(isVipIdList);
				if(num > isVipIdList.size()){
					num = num - isVipIdList.size();
					List<Integer> noVipIdList = rentDao.getRentIdForAudit(auditSign, auditStatus, houseStatus, num, cityId,House.HOUSE_VIP_FLAG_NO);
					if(noVipIdList != null){
						idList.addAll(noVipIdList);
					}
				}
				
				if (CollectionUtils.isNotEmpty(idList)) {
					rentDao.signHouse(auditSign, idList);
				}
			}
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}
		return idList;
	}

	public List<RentInfo> getRentTask(Integer auditStatus, Integer houseStatus, Integer num, Integer auditStep, Integer auditUserId, Integer cityId) {
		// 获取
		List<RentInfo> rentInfoList = new ArrayList<RentInfo>();
		List<Integer> rentInfoIdList = null;
		// 获取待审核房源id
			rentInfoIdList = rentDao.getRentIdList(auditUserId, num, auditStep, houseStatus, auditStatus, cityId);
		try {
			if (CollectionUtils.isNotEmpty(rentInfoIdList)) {
				// rentInfoList=rentDao.getRentFromAuditTask(auditUserId, num,
				// auditStep, houseStatus, auditStatus,cityId);
				rentInfoList = rentDao.getRentFromAuditTask(rentInfoIdList, houseStatus, auditStatus);
				List<Integer> beanIdList=new ArrayList<Integer>();
				if(CollectionUtils.isNotEmpty(rentInfoList)){
					for (RentInfo rent : rentInfoList) {
						beanIdList.add(rent.getId());
						// 城市缩写。链接地址用
						Integer rentCityId = rent.getCityid();
						if (rentCityId <= 0) {
							// 默认为北京id
							rentCityId = Globals.BEIJINGID;
						}
						rent.setCityDir(LocaleService.getCode(rentCityId));
					}
				}
				if(CollectionUtils.isEmpty(rentInfoList)||rentInfoList.size()<rentInfoIdList.size()){
					//取出不存在bean的id
					List<Integer> noExistBeanId=new ArrayList<Integer>();
					for (Integer id : rentInfoIdList) {
						if(!beanIdList.contains(id)){
							noExistBeanId.add(id);
						}
					}
					if(CollectionUtils.isNotEmpty(noExistBeanId)){
						log.info("删除任务列表存在，但是出租房中不存在的房源！");
						this.deleteAuditTask(noExistBeanId, Globals.HOUSE_TYPE_RENT);
						
					}
					
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rentInfoList;
	}

	/**
	 * 创建审核任务
	 * 
	 * @param auditSign
	 *            审核占位标记
	 * @param auditStatus
	 *            房源的审核状态
	 * @param houseStatus
	 *            房源的状态
	 * @param num
	 *            创建数量
	 * @param auditStep
	 *            审核步骤
	 * @param auditUserId
	 *            审核人员id
	 * @author wangjh Dec 3, 2011 7:18:23 PM
	 */
	public void buildRentTask(String auditSign, Integer auditStatus, Integer houseStatus, Integer num, Integer auditStep, Integer auditUserId, Integer cityId) {
		// 标记
		List<Integer> idList = this.signHouse(auditSign, auditStatus, houseStatus, num, cityId);
		// 向审核任务中添加
		List<RentInfo> rentInfo = null;
		if (CollectionUtils.isNotEmpty(idList)) {
			rentInfo = rentDao.getRentInfoCity(idList);
		}
		if (CollectionUtils.isNotEmpty(rentInfo)) {
			for (RentInfo rentInfo2 : rentInfo) {
				AuditStep as = new AuditStep();
				if(rentInfo2.getUpdateBase()==1){
					as.setStepBase(-1);
				}else{
					as.setStepBase(auditUserId);
				}
				if(rentInfo2.getId()!=null){
					List<Integer> listCategory = rentDao.getUnauditPhotoCategory(rentInfo2.getId());
					if(listCategory!=null){
						if(!listCategory.contains(1)){
							as.setStepLayout(-1);
						}else{
							as.setStepLayout(auditUserId);
						}
						if(!listCategory.contains(2)){
							as.setStepInner(-1);
						}else{
							as.setStepInner(auditUserId);
						}
						if(!listCategory.contains(3)){
							as.setStepEstate(-1);
						}else{
							as.setStepEstate(auditUserId);
						}
					}
				}
				this.buileTaskByParam(rentInfo2, auditStep, auditUserId,as);
				// 构建审核历史对象
				try {
					AuditHistory auditHistory = new AuditHistory();
					auditHistory.setAuthorId(rentInfo2.getAuthorid());
					auditHistory.setAuthorName(rentInfo2.getAuthorName());
					auditHistory.setHouseId(rentInfo2.getId());
					auditHistory.setHouseType(Globals.HOUSE_TYPE_RENT);
					auditHistory.setCityId(rentInfo2.getCityid());
					auditHistoryService.saveAuditHistory(auditHistory);
					log.info("新建审核历史。");
				} catch (Exception e) {
					log.error("保存审核历史出错！", e);
				}
			}
		}
	}
	/**
	 * 创建审核任务
	 * @param houseId
	 * @param auditStep
	 * @param auditUserId
	 * @param cityId
	 * @author wangjh
	 * Dec 26, 2011 3:26:30 PM
	 */
	public void buileTaskByParam(RentInfo info,Integer auditStep,Integer auditUserId,AuditStep as){
		rentDao.buileTaskByParam(info,auditStep, auditUserId, as);
	}
	
	/**
	 * 获取房源对应城市id
	 * @param idList
	 * @return
	 * @author wangjh
	 * Dec 26, 2011 3:50:03 PM
	 */
	public List<RentInfo> getRentInfoCity(List<Integer> idList){
		List<RentInfo> rentInfoCity = rentDao.getRentInfoCity(idList);
		return rentInfoCity;
	}

	/**
	 * 在audit_task表中查询指定人员，指定审核步骤的任务
	 * 
	 * @param auditUserId
	 *            审核人员
	 * @param auditStep
	 *            审核步骤（基本信息，户型图，小区图，室内图）
	 * @return
	 * @author wangjh Dec 3, 2011 5:29:24 PM
	 */
	public List<Integer> getRentTaskIdList(Integer auditUserId, Integer auditStep, Integer num, Integer cityId) {
		return rentDao.getRentTaskIdList(auditUserId, auditStep, Globals.HOUSE_TYPE_RENT, num, cityId);
	}

	/**
	 * 更新audit_task的审核人id,为了使用户先取audit_task中的任务
	 * 
	 * @param auditStep
	 * @param auditUserId
	 * @author wangjh Dec 6, 2011 6:59:13 PM
	 */
	public void updateAuditTaskUser(List<Integer> houseIdList, Integer auditUserId, Integer auditStep) {
		rentDao.updateAuditTaskUser(houseIdList, auditUserId, auditStep);

	}
	
	public void updateRentAuditFlag(List<Integer> houseIdList,Integer type) {
		rentDao.updateRentAuditFlag(houseIdList,type);
	}

	public void updateAuditTaskStatus(List<Integer> houseIdList, Integer auditStep, Integer auditType, Integer auditResult) {
		rentDao.updateAuditTaskStatus(houseIdList, auditStep, auditType, auditResult);
		// 获取房源id中其他图片审核对应的图片数为0的房源idList
//
//		if (Globals.AUDIT_ESTATE_PHOTO == auditStep) {
//			Integer auditSteps = Globals.AUDIT_ESTATE_PHOTO;
//			Integer auditResults = Globals.AUDIT_RESULT_PASS;
//			// 获取小区图为0的房源IdLidt
//			//List<Integer> estateHouseIdList = null;
//			//estateHouseIdList = rentDao.getPhotoCountOByStep(auditSteps, houseIdList);
//			// 更新为通过
//			if (CollectionUtils.isNotEmpty(houseIdList)) {
//				rentDao.updateAuditTaskStatus(houseIdList, auditSteps, auditType, auditResults);
//			}
//		}
//		if (Globals.AUDIT_HOUSEHOLD_PHOTO == auditStep) {
//			Integer auditSteps = Globals.AUDIT_HOUSEHOLD_PHOTO;
//			Integer auditResults = Globals.AUDIT_RESULT_PASS;
//			// 获取户型图为0的房源IdLidt
//			//List<Integer> householdHouseIdList = null;
//			//householdHouseIdList = rentDao.getPhotoCountOByStep(auditSteps, houseIdList);
//			// 更新为通过
//			if (CollectionUtils.isNotEmpty(houseIdList)) {
//				rentDao.updateAuditTaskStatus(houseIdList, auditSteps, auditType, auditResults);
//			}
//		}
//		if (Globals.AUDIT_INDOOR_PHOTOO == auditStep) {
//			Integer auditSteps = Globals.AUDIT_INDOOR_PHOTOO;
//			Integer auditResults = Globals.AUDIT_RESULT_PASS;
//			// 获取室内图为0的房源IdLidt
//			//List<Integer> indoorHouseIdList = null;
//		//	indoorHouseIdList = rentDao.getPhotoCountOByStep(auditSteps, houseIdList);
//			// 更新为通过
//			if (CollectionUtils.isNotEmpty(houseIdList)) {
//				rentDao.updateAuditTaskStatus(houseIdList, auditSteps, auditType, auditResults);
//			}
//		}

	}

	/**
	 * 核最终结果处理
	 * 
	 * @param houseIdList
	 *            需要判断的房源ID list
	 * @param auditType
	 *            房源类型
	 * @author wangjh Dec 7, 2011 4:22:56 PM
	 */
	public Integer finalOutcome(List<Integer> houseIdList, Integer auditType,Integer auditStep) {
		// 先判断4个审核是否都进行过，如果都完成则调用通过的接口通过审核；如果有未完成的则不做操作
		Integer completeNum = 0;
		Integer result = Globals.AUDIT_RESULT_PASS;
		List<Integer> completeHouseIdList = rentDao.getPassHouseIdList(houseIdList, auditType, result,auditStep);
		if (CollectionUtils.isNotEmpty(completeHouseIdList)) {
			//System.out.println("RentVerifyService-清空任务列表：audit_task 列表个数为 = "+completeHouseIdList.size()+","+Arrays.asList(completeHouseIdList).toString());
			this.approve(completeHouseIdList);
			this.deleteAuditTask(completeHouseIdList, auditType);
			try {
				// 记录审核日志--completeHouseIdList通过的房源
				log.info("记录通过的出租房房源记录");
				auditHistoryService.recordLog(completeHouseIdList, result, Globals.HOUSE_TYPE_RENT);

			} catch (Exception e) {
				log.error("记录通过的出租房房源记录异常!");
				System.out.println("RentVerifyService-清空任务列表：audit_task 记录房源日志出错");
			}
			completeNum = completeHouseIdList.size();
		}
		/*else{
			System.out.println("RentVerifyService-清空任务列表：audit_task 为0");
		}*/
		return completeNum;
	}

	public List<RentInfo> getRentInfos_photos(List<RentInfo> rentInfos) {
		List<RentInfo> resultList = rentDao.getRentInfos_photos(rentInfos);
		return resultList;
	}

	public List<HousePhoto> getVerifyingPhotos(List<RentInfo> rentList, Integer type) {
		List<HousePhoto> resultList = rentDao.getVerifyingPhotos(rentList, type);
		return resultList;
	}

	/**
	 * @param rentList
	 * @return 得到所有房源ID
	 */
	public String getVerifyHouseIdList(List<RentInfo> rentList) {
		return rentDao.getVerifyHouseIdList(rentList);
	}

	/**
	 * @param blockList
	 *            被阻止的图片(数据格式为:houseId_photoId,houseId_photoId...)
	 * @param sessionUser
	 *            用户信息
	 * @return 是否成功
	 */
	public List<Integer> handleRejectPhotos(String blockList, SessionUser sessionUser) {
		if (null != sessionUser) {
			return rentDao.handleRejectPhotos(blockList, sessionUser.getUser().getId());
		}
		return null;
	}

	/**
	 * 删除任务清空任务标记
	 * 
	 * @param houseIdList
	 *            房源idList
	 * @param auditType
	 *            房源类型
	 * @author wangjh Dec 7, 2011 8:56:41 PM
	 */
	public void deleteAuditTask(List<Integer> houseIdList, Integer auditType) {
		/* 删除任务 */
		rentDao.deleteAuditTask(houseIdList, auditType);
		/* 只清空标记，不改变房源状态 */
		rentDao.updateAuditStatusByIdList(houseIdList, null, null, null);

	}
	
	/**
	 * 获取指定房源id中不符合审核条件的房源id
	 * 
	 * @param houseIds
	 * @return
	 * @author wangjh Dec 15, 2011 11:19:44 AM
	 */
	public List<Integer> getInconformityHouseIds(List<Integer> houseIds) {
		return rentDao.getInconformityHouseIds(houseIds);

	}

	/**
	 * 获取房源历史信息
	 * 
	 * @param houseIdList
	 * @return
	 * @author wangjh Dec 21, 2011 2:18:23 PM
	 */
	public List<AuditHistory> getHistoryInfo(List<Integer> houseIdList) {
		return rentDao.getHistoryInfo(houseIdList);

	}

	/**
	 * @param houseIdIntList
	 * @param photoIdList
	 * @param type
	 * @return 根据houseidList和category查图片
	 */
	public List<SubAuditHistory> getRentPhotosByHouseListAndType(List<Integer> houseIdIntList, List<Integer> photoIdList, Integer type) {
		return rentDao.getRentPhotosByHouseListAndType(houseIdIntList, photoIdList, type);
	}


	public HistoryInfo getPhotoInfoHistory(HistoryInfo historyInfo){
		return rentDao.getPhotoInfoHistory(historyInfo);
		
	}


	/**
	 * 复审图片,要搬运rent_photo和reject_photo,要更新rent里边的各种photo_count字段
	 * 
	 * @param historyInfo
	 * @param result
	 * @return
	 * @date 2011-12-23下午3:15:02
	 * @author dongyj
	 * @param user 
	 * @throws Exception 
	 */
	public boolean reAuditPhoto(HistoryInfo historyInfo, int result, User user) throws Exception {
		if (null == historyInfo) {
			return false;
		}

		return rentDao.reAuditPhoto(historyInfo, result, user);
	}
	
	public List<HousePhoto> getAllHousePhotoByType(Integer houseid,Integer photoid,Integer type,Integer inalbum){
		List<HousePhoto> housePhotos = rentDao.getAllHousePhotoByType(houseid, photoid, type, inalbum);
		return housePhotos;
	}

	/**
	 * 获取符合条件的房源数量
	 * @return
	 * @author wangjh
	 * Dec 27, 2011 4:14:32 PM
	 */
	public Integer getUnclaimedAwaitHouseAuditCount(Integer houseStatus,Integer auditStatus) {
		return rentDao.getUnclaimedAwaitHouseAuditCount(houseStatus,auditStatus);
	}


	public Integer getPhotoCountByHouseIdList(List<Integer> list,
			Integer auditStep) {
		if(auditStep==null||Globals.AUDIT_BASE_INFO==auditStep){
			log.error("审核步骤参数为空，或者审核步骤为基本信息审核，无法获取图片数量！");
			return 0;
		}
		return rentDao.getPhotoCountByHouseIdList(list,auditStep);
	}


	public Integer getPhotoAuditUnclaimedAwaitCount(
			Integer houseStatus, Integer auditStatus,
			Integer auditStep) {
		return rentDao.getPhotoAuditUnclaimedAwaitCount(
				houseStatus, auditStatus,auditStep);
	}
	protected  List<com.taofang.biz.domain.house.HousePhoto> getAllHousePhoto(Integer houseid) {
		IHousePhotoService photoService = BizServiceHelper.getHousePhotoService();
		return photoService.getRentPhotos(houseid);
	}


	public List<com.taofang.biz.domain.house.HousePhoto> getDecorationByPhotos(
			List<com.taofang.biz.domain.house.HousePhoto> housePhotoList) {
		for(com.taofang.biz.domain.house.HousePhoto housePhoto:housePhotoList){
			Rent rent = rentDao.selectRentById(housePhoto.getHouseId());
			if(rent != null){
				//使用coverFlag存放装修信息
				housePhoto.setCoverFlag(Resources.getString("house.decoration."+rent.getDecoration()));
			}
		}
		return housePhotoList;
	}

}
