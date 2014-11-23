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
import com.nuoshi.console.dao.ResaleDao;
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
import com.nuoshi.console.domain.resale.Resale;
import com.nuoshi.console.domain.resale.ResaleInfo;
import com.nuoshi.console.domain.user.User;
import com.nuoshi.console.persistence.read.taofang.agent.TFUserReadMapper;
import com.nuoshi.console.persistence.write.admin.audit.AuditRejectWriteMapper;
import com.nuoshi.console.web.session.SessionUser;

@Service
public class ResaleVerifyService extends HouseService {
	@Resource
	private ResaleDao resaleDao;
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
	 * 批量设置出租房状态
	 * 
	 * @param houseIdList
	 *            房源id
	 * @author wangjh Dec 7, 2011 4:40:45 PM
	 */
	public void updateAuditStatusByIdList(List<Integer> houseIdList, Integer status, Integer houseStatus, String sign) {
		resaleDao.updateAuditStatusByIdList(houseIdList, status, houseStatus, sign);
		
	}
	/**
	 * 获取房源对应城市id
	 * @param idList
	 * @return
	 * @author wangjh
	 * Dec 26, 2011 3:50:03 PM
	 */
	public List<ResaleInfo> getRentInfoCity(List<Integer> idList){
		List<ResaleInfo> resaleInfoCity = resaleDao.getResaleInfoCity(idList);
		return resaleInfoCity;
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
	public void buileTaskByParam(ResaleInfo info,Integer auditStep,Integer auditUserId,AuditStep as){
		resaleDao.buileTaskByParam(info, auditStep, auditUserId,as);
	}
	/**
	 * 获取审核历史记录
	 * @param houseIdList
	 * @return
	 * @author wangjh
	 * Dec 22, 2011 11:06:16 AM
	 */
	public List<SubAuditHistory> getSubHistoryInfo(List<Integer> houseIdList) {
		return resaleDao.getSubHistoryInfo(houseIdList);
	}
	
	
	/**
	 * 
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
		if (resaleDao.insertResaleInvalidReason(invalidreason) == -1) {
			return -1;
		}
		return 1;
	}
	
	public int addAuditReject(Resale resale, String reason) {
		AuditReject auditReject = new AuditReject();
		auditReject.setEntryTime(new Date());
		auditReject.setHouseId(resale.getId());
		auditReject.setHouseType(HouseConstant.RESALE);
		auditReject.setLogId(0);
		auditReject.setAgentId(resale.getAuthorid());
		auditReject.setRejectReason(reason);
		return auditRejectWriteMapper.insertAuditReject(auditReject);
	}
	
	
	public int approve(int id,User user) {
		Resale resale = resaleDao.selectResaleById(id);
		if (resale == null || resale.isDeleted()) {
			return -1;
		}
		//纪录一次错审
		auditHistoryService.handleReaudit(id,1,user,-1);
		
		Double score =  this.getHouseScore(resale);
		if(score>0){
			resaleDao.updateHouseScore(resale.getId(),score);
		}
		BizServiceHelper.getAuditHouseService().cancelTask(id, HouseConstant.RESALE,false);
		int result1 = resaleDao.updateHouseStatus(id, House.HOUSE_STATUS_SHELVED);
		int result2 = resaleDao.updateHouseAuditStatus(id, House.AUDIT_STATUS_OK);
		if(result1 >0 && result2 >0){
			return 1;
		}
		return -1;
	}
	/**
	 * 批量通过全部审核
	 * @param houseIdList 房源id
	 * @author wangjh
	 * Dec 7, 2011 4:40:45 PM
	 */
	public void approve(List<Integer> houseIdList){
		
			for (Integer houseid : houseIdList) {
				
				Double score =  this.getHouseScore(resaleDao.selectResaleById(houseid));
				if(score>0){
					resaleDao.updateHouseScore(houseid,score);
				}
				
			}
		
		resaleDao.updateAuditStatusByIdList(houseIdList, House.AUDIT_STATUS_OK,House.HOUSE_STATUS_SHELVED,null);
		
	}
	
	/**
	 * 下架一个房源 这个不是公开的接口，没有判断房源是否已经被锁上， 此项判断在 下架一组房源中判断
	 * 
	 * @param house
	 * @return
	 *//*
	protected boolean doUnshelve(Resale house) {
		int result = resaleDao.updateHouseStatus(house.getId(), House.HOUSE_STATUS_OUTDATE);
		return result > 0;
	}*/
	public List<RejectReason> getAllRejectReason(int type){
			
			return resaleDao.getAllRejectReason(type);

		}
	public List<ResaleInfo> getAllResale(Integer authorId){
		
		return resaleDao.getAllVerifyResale(authorId);
	}
	
	public List<ResaleInfo> getAllResale4VerifyByPage(HashMap params){
		List<ResaleInfo> resultList = resaleDao.getAllResale4VerifyByPage(params);
		return resultList;
	}
	public List<HousePhoto> getHousePhoto(Integer houseId,String category){
		return resaleDao.getHousePhoto(houseId,category);
	}

	public List<ResaleInfo> getResale4VerifyByPage(HashMap params){
		List<ResaleInfo> resultList = resaleDao.getResale4VerifyByPage(params);
		return resultList;
	}
	
	/**
	 * Admin 下架某一个房源
	 * 
	 * @param id
	 *            房源id
	 * @return HouseOpResult
	 */
	public int reject(int id,String reason,User user) {
		Resale resale = resaleDao.selectResaleById(id);
		if (resale == null || resale.isDeleted()) {
			return -1;
		}
		//纪录一次错审
		 auditHistoryService.handleReaudit(id,1,user,-2);
		
		BizServiceHelper.getAuditHouseService().cancelTask(id, HouseConstant.RESALE,false);
		AgentMaster author = tfUserReadMapper.selectAgentById(resale.getAuthorid());
		String viewUrl = "http://" + LocaleService.getCode(resale.getCityid()) + ".taofang.com/ershoufang/" + resale.getId() + "-" + 0 + ".html";
		messageService.sendResaleViolateMessage(author, id, reason, viewUrl);
		
		int result = resaleDao.rejectHouse(id);
		int ret = addAuditReject(resale, reason);
		if(result > 0 && ret > 0) {
			if(resale.getHouse_status() == House.HOUSE_STATUS_SHELVED||resale.getHouse_status() == House.HOUSE_STATUS_NOAUDIT_AGENT) {
				int result2 =  agentMasterDao.incrementHouseNum(resale.getAuthorid());
				if(result2 < 1) {
					return -1;
				}
				agentActionLogDao.addAgentActionLog(resale.getAuthorid(), id, author.getPackageId(), 
						AgentActionLog.ACTION_TYPE_HOUSE_NUM, Globals.RESALE_TYPE, House.HOUSE_STATUS_SHELVED.toString(), House.HOUSE_STATUS_ILLEGAL.toString());
				if(resale.getLabels().size()>0) {
					if(resale.isVcr()) {
						int actionType = AgentActionLog.ACTION_TYPE_LABEL_VCR;
						agentActionLogDao.addAgentActionLog(resale.getAuthorid(), id, author.getPackageId(), actionType, Globals.RESALE_TYPE, House.HOUSE_ACTION_LABEL_VALUE, "null");
					}
					if(resale.isSskf()) {
						int actionType = AgentActionLog.ACTION_TYPE_LABEL_SSKF;
						agentActionLogDao.addAgentActionLog(resale.getAuthorid(), id, author.getPackageId(), actionType, Globals.RESALE_TYPE, House.HOUSE_ACTION_LABEL_VALUE, "null");
					}
					if(resale.isRecommend()) {
						int actionType = AgentActionLog.ACTION_TYPE_LABEL_XT;
						agentActionLogDao.addAgentActionLog(resale.getAuthorid(), id, author.getPackageId(), actionType, Globals.RESALE_TYPE, House.HOUSE_ACTION_LABEL_VALUE, "null");
					}
					
				
					return agentMasterDao.incrementLabelNum(resale.getAuthorid(),resale.getLabels().size(), resale.isVcr() ? 1 : 0);
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
	public int reject(int id,String reason) {
		Resale resale = resaleDao.selectResaleById(id);
		if (resale == null || resale.isDeleted()) {
			return -1;
		}
		
		BizServiceHelper.getAuditHouseService().cancelTask(id, HouseConstant.RESALE,false);
		AgentMaster author = tfUserReadMapper.selectAgentById(resale.getAuthorid());
		String viewUrl = "http://" + LocaleService.getCode(resale.getCityid()) + ".taofang.com/ershoufang/" + resale.getId() + "-" + 0 + ".html";
		messageService.sendResaleViolateMessage(author, id, reason, viewUrl);
		
		int result = resaleDao.rejectHouse(id);
		int ret = addAuditReject(resale, reason);
		if(result > 0 && ret > 0) {
			if(resale.getHouse_status() == House.HOUSE_STATUS_SHELVED||resale.getHouse_status() == House.HOUSE_STATUS_NOAUDIT_AGENT) {
				int result2 =  agentMasterDao.incrementHouseNum(resale.getAuthorid());
				if(result2 < 1) {
					return -1;
				}
				agentActionLogDao.addAgentActionLog(resale.getAuthorid(), id, author.getPackageId(), 
						AgentActionLog.ACTION_TYPE_HOUSE_NUM, Globals.RESALE_TYPE, House.HOUSE_STATUS_SHELVED.toString(), House.HOUSE_STATUS_ILLEGAL.toString());
				if(resale.getLabels().size()>0) {
					if(resale.isVcr()) {
						int actionType = AgentActionLog.ACTION_TYPE_LABEL_VCR;
						agentActionLogDao.addAgentActionLog(resale.getAuthorid(), id, author.getPackageId(), actionType, Globals.RESALE_TYPE, House.HOUSE_ACTION_LABEL_VALUE, "null");
					}
					if(resale.isSskf()) {
						int actionType = AgentActionLog.ACTION_TYPE_LABEL_SSKF;
						agentActionLogDao.addAgentActionLog(resale.getAuthorid(), id, author.getPackageId(), actionType, Globals.RESALE_TYPE, House.HOUSE_ACTION_LABEL_VALUE, "null");
					}
					if(resale.isRecommend()) {
						int actionType = AgentActionLog.ACTION_TYPE_LABEL_XT;
						agentActionLogDao.addAgentActionLog(resale.getAuthorid(), id, author.getPackageId(), actionType, Globals.RESALE_TYPE, House.HOUSE_ACTION_LABEL_VALUE, "null");
					}
					
				
					return agentMasterDao.incrementLabelNum(resale.getAuthorid(),resale.getLabels().size(), resale.isVcr() ? 1 : 0);
				} 
			}
			return 1;
		} else {
			return -1;
		}
	}
	
	public void deleteHousePhoto(int houseid, int photoid, int category) {
		
		resaleDao.deleteHousePhoto(houseid, photoid, category);
		Photo photo = photoDao.selectPhotoById(photoid);
		RejectPhoto rejectPhoto = new RejectPhoto();
		rejectPhoto.setHouseId(houseid);
		rejectPhoto.setPhotoBrowse(photo.getM());
		rejectPhoto.setPhotoId(photoid);
		rejectPhoto.setType(1);
		resaleDao.updatePhotoStatus(houseid,1);//更新状态，标示有图片被删除了
	}
	
	
	/**
	 * 审核人员标记房源为本人审核
	 * @param auditStatus 被标记房源的审核状态
	 * @param houseStatus 被标记房源的发布状态
	 * @param num 标记数量
	 * @param auditUserId 审核人员
	 * @author wangjh
	 * Nov 29, 2011 11:19:55 AM
	 */
	public List<Integer> signHouse(String auditSign,Integer auditStatus,Integer houseStatus,Integer num,Integer cityId){
		//标记是否有人审核AuditUser+datetime
//		String auditSign=""+auditUserId+new java.util.Date().getTime();
//		StringBuffer updateSql=new StringBuffer();
		/**
		 * update 不能使用limit
		 */
		List<Integer> idList=new ArrayList<Integer>();
		try{
			synchronized (this) {
				List<Integer> isVipId = resaleDao.getResaleIdForAudit(auditSign,auditStatus,houseStatus,num,cityId,House.HOUSE_VIP_FLAG_YES);
				if(isVipId==null){
					isVipId = new ArrayList<Integer>();
				}
				idList.addAll(isVipId);
				if(num > isVipId.size()){
					num = num - isVipId.size();
					List<Integer> unVipId = resaleDao.getResaleIdForAudit(auditSign,auditStatus,houseStatus,num,cityId,House.HOUSE_VIP_FLAG_NO);
					if(unVipId!=null) idList.addAll(unVipId);
				}
				
				if(CollectionUtils.isNotEmpty(idList)){
					resaleDao.signHouse(auditSign,idList);
				}
			}
		}catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}
		return idList;
	}
	public List<ResaleInfo> getResaleTask(String auditSign, Integer auditStatus,
			Integer houseStatus, Integer num,Integer auditStep,Integer auditUserId,Integer cityId) {
		//获取
		List<ResaleInfo> resaleInfoList=new ArrayList<ResaleInfo>();
		List<Integer> resaleInfoIdList=null;
		//获取任务表中待审核房源id
			resaleInfoIdList=resaleDao.getResaleIdList(auditUserId, num, auditStep, houseStatus, auditStatus,cityId);
		if(CollectionUtils.isNotEmpty(resaleInfoIdList)){
//			resaleInfoList=resaleDao.getResaleFromAuditTask(auditUserId, num,auditStep, houseStatus, auditStatus,cityId);
			//二手房中在线房源
			resaleInfoList=resaleDao.getResaleFromAuditTask(resaleInfoIdList,houseStatus, auditStatus);
			List<Integer> beanIdList=new ArrayList<Integer>();
			if(CollectionUtils.isNotEmpty(resaleInfoList)){
				for (ResaleInfo resale : resaleInfoList) {
					beanIdList.add(resale.getId());
					Integer resaleCityId=resale.getCityid();
					if(resaleCityId<=0){
						//默认为北京id
						resaleCityId=Globals.BEIJINGID;
					}
					resale.setCityDir(LocaleService.getCode(resaleCityId));
				}
			}
			if(CollectionUtils.isEmpty(resaleInfoList)||resaleInfoList.size()<resaleInfoIdList.size()){
				//取出不存在bean的id
				List<Integer> noExistBeanId=new ArrayList<Integer>();
				for (Integer id : resaleInfoIdList) {
					if(!beanIdList.contains(id)){
						noExistBeanId.add(id);
					}
				}
				if(CollectionUtils.isNotEmpty(noExistBeanId)){
					log.info("删除任务列表存在，但是出租房中不存在的房源！");
					this.deleteAuditTask(noExistBeanId, Globals.HOUSE_TYPE_RESALE);
					
				}
			}
		}
		return resaleInfoList;
	}
	/**
	 * 在audit_task表中查询指定人员，指定审核步骤的任务
	 * @param auditUserId 审核人员
	 * @param auditStep 审核步骤（基本信息，户型图，小区图，室内图）
	 * @return
	 * @author wangjh
	 * Dec 3, 2011 5:29:24 PM
	 */
	public List<Integer> getResaleTaskIdList(Integer auditUserId, Integer auditStep,Integer num,Integer cityId) {
		return resaleDao.getResaleTaskIdList(auditUserId,auditStep,Globals.HOUSE_TYPE_RESALE,num,cityId);
	}
	
	public void updateResaleAuditFlag(List<Integer> houseIdList,Integer type) {
		resaleDao.updateResaleAuditFlag(houseIdList,type);
	}
	
	/**
	 * 创建审核任务
	 * @param auditSign 审核占位标记
	 * @param auditStatus 房源的审核状态
	 * @param houseStatus 房源的状态
	 * @param num 创建数量
	 * @param auditStep 审核步骤
	 * @param auditUserId 审核人员id
	 * @author wangjh
	 * Dec 3, 2011 7:18:23 PM
	 */
	public void buildResaleTask(String auditSign,Integer auditStatus,Integer houseStatus,Integer num,Integer auditStep,Integer auditUserId,Integer cityId){
		//TODO 可优化，signHouse直接出去所需要的数据，此处getResaleInfoCity就没必要再取一次 zhangyc
		//标记
		List<Integer> idList=this.signHouse(auditSign,auditStatus, houseStatus, num,cityId);
		//向审核任务中添加
		List<ResaleInfo> resaleInfo = null;
		if (CollectionUtils.isNotEmpty(idList)) {
			resaleInfo = resaleDao.getResaleInfoCity(idList);
		}
		if (CollectionUtils.isNotEmpty(resaleInfo)) {
			for (ResaleInfo resaleInfo2 : resaleInfo) {
				//TODO 此处判断标题，图片
				AuditStep as = new AuditStep();
				if(resaleInfo2.getUpdateBase()==1){
					as.setStepBase(-1);
				}else{
					as.setStepBase(auditUserId);
				}
				if(resaleInfo2.getId()!=null){
					List<Integer> listCategory = resaleDao.getUnauditPhotoCategory(resaleInfo2.getId());
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
				resaleDao.buileTaskByParam(resaleInfo2, auditStep, auditUserId,as);
				//构建审核历史对象
				try{
					AuditHistory auditHistory=new AuditHistory();
					auditHistory.setAuthorId(resaleInfo2.getAuthorid());
					auditHistory.setAuthorName(resaleInfo2.getAuthorName());
					auditHistory.setHouseId(resaleInfo2.getId());
					auditHistory.setHouseType(Globals.HOUSE_TYPE_RESALE);
					auditHistory.setCityId(resaleInfo2.getCityid());
					auditHistoryService.saveAuditHistory(auditHistory);
				}catch (Exception e) {
					e.printStackTrace();
					log.error("保存审核历史出错！", e);
				}
			}
		}
	}
	/**
	 * 设置audit_task审核结果
	 * @param auditStep 审核步骤
	 * @param hasPass 通过房源ID的list
	 * @param noPass 打回房源ID的list
	 * @author wangjh
	 * Dec 6, 2011 3:08:30 PM
	 */
	public void setResultToAuditTask(Integer auditStep, List<Integer> hasPass,
			List<Integer> noPass) {
		//通过
		Integer pass=Globals.AUDIT_RESULT_PASS;
		resaleDao.setAuditTaskResult(auditStep,hasPass,pass);
		//打回
		Integer reject=Globals.AUDIT_RESULT_REJECT;
		resaleDao.setAuditTaskResult(auditStep,noPass,reject);
		
	}
	public void updateAuditTaskUser(List<Integer> taskIdList,
			Integer auditUserId,Integer auditStep) {
		resaleDao.updateAuditTaskUser(taskIdList,auditUserId,auditStep);
		
	}
	public void updateAuditTaskStatus(List<Integer> houseIdList,
			Integer auditStep, Integer auditType, Integer auditResult) {
		resaleDao.updateAuditTaskStatus(houseIdList,auditStep,auditType,auditResult);
		//获取房源id中其他图片审核对应的图片数为0的房源idList
		
//		if(Globals.AUDIT_ESTATE_PHOTO==auditStep){
//			Integer auditSteps=Globals.AUDIT_ESTATE_PHOTO;
//			Integer auditResults=Globals.AUDIT_RESULT_PASS;
//			//获取小区图为0的房源IdLidt
//			//List<Integer> estateHouseIdList=null;
//			//estateHouseIdList=resaleDao.getPhotoCountOByStep(auditSteps,houseIdList);
//			//更新为通过
//			if(CollectionUtils.isNotEmpty(houseIdList)){
//				resaleDao.updateAuditTaskStatus(houseIdList,auditSteps,auditType,auditResults);
//			}
//		}
//		if(Globals.AUDIT_HOUSEHOLD_PHOTO==auditStep){
//			Integer auditSteps=Globals.AUDIT_HOUSEHOLD_PHOTO;
//			Integer auditResults=Globals.AUDIT_RESULT_PASS;
//			//获取户型图为0的房源IdLidt
//			//List<Integer> householdHouseIdList=null;
//			//householdHouseIdList=resaleDao.getPhotoCountOByStep(auditSteps,houseIdList);
//			//更新为通过
//			if(CollectionUtils.isNotEmpty(houseIdList)){
//				resaleDao.updateAuditTaskStatus(houseIdList,auditSteps,auditType,auditResults);
//			}
//		}
//		if(Globals.AUDIT_INDOOR_PHOTOO==auditStep){
//			Integer auditSteps=Globals.AUDIT_INDOOR_PHOTOO;
//			Integer auditResults=Globals.AUDIT_RESULT_PASS;
//			//获取室内图为0的房源IdLidt
//			//List<Integer> indoorHouseIdList=null;
//		//	indoorHouseIdList=resaleDao.getPhotoCountOByStep(auditSteps,houseIdList);
//			//更新为通过
//			if(CollectionUtils.isNotEmpty(houseIdList)){
//				resaleDao.updateAuditTaskStatus(houseIdList,auditSteps,auditType,auditResults);
//			}
//		}
	}
	/**
	 * 核最终结果处理
	 * @param houseIdList 需要判断的房源ID list
	 * @param auditType 房源类型
	 * @author wangjh
	 * Dec 7, 2011 4:22:56 PM
	 */
	public Integer finalOutcome(List<Integer> houseIdList, Integer auditType,Integer auditStep) {
		//先判断4个审核是否都进行过，如果都完成则调用通过的接口通过审核；如果有未完成的则不做操作
		Integer completeNum=0;
		Integer result=Globals.AUDIT_RESULT_PASS;
		List<Integer> completeHouseIdList=resaleDao.getPassHouseIdList(houseIdList,auditType,result,auditStep);
		if(CollectionUtils.isNotEmpty(completeHouseIdList)){
			//System.out.println("RealeVerifyService-清空任务列表：个数="+completeHouseIdList.size()+","+Arrays.asList(completeHouseIdList).toString());
			this.approve(completeHouseIdList);
			this.deleteAuditTask(completeHouseIdList, auditType);
			try{
				// 记录审核日志--completeHouseIdList通过的房源
				log.info("记录通过的二手房房源记录");
				auditHistoryService.recordLog(completeHouseIdList,result,Globals.HOUSE_TYPE_RESALE);
				
			}catch (Exception e) {
				log.error("记录通过的二手房房源记录异常!");
			}
			completeNum=completeHouseIdList.size();
		}
		return completeNum;
	}
	public void deleteAuditTask(List<Integer> houseIdList,
			Integer auditType) {
		/*删除任务*/
		resaleDao.deleteAuditTask(houseIdList,
				auditType);
		/*只清空标记，不改变房源状态*/
		resaleDao.updateAuditStatusByIdList(houseIdList, null,null,null);
		
		
	}
	
	public List<HousePhoto> getVerifyingPhotos(List<ResaleInfo> resaleList,
			Integer type) {
		List<HousePhoto> resultList = resaleDao.getVerifyingPhotos(resaleList, type);
		return resultList;
	}
	
	/**
	 * @param resaleList
	 * @return 得到所有房源ID
	 */
	public String getVerifyHouseIdList(List<ResaleInfo> resaleInfos) {
		return resaleDao.getVerifyHouseIdList(resaleInfos);
	}
	
	/**
	 * @param blockList 被阻止的图片(数据格式为:houseId_photoId,houseId_photoId...)
	 * @param sessionUser 用户信息
	 * @return 是否成功
	 */
	public List<Integer> handleRejectPhotos(String blockList,SessionUser sessionUser) {
		if(null!=sessionUser){
			return resaleDao.handleRejectPhotos(blockList,sessionUser.getUser().getId());
		}
		return null;
	}
	/**
	 * 获取指定房源id中不符合审核条件的房源id
	 * @param houseIds
	 * @return
	 * @author wangjh
	 * Dec 15, 2011 11:19:44 AM
	 */
	public List<Integer> getInconformityHouseIds(List<Integer> houseIds){
		return resaleDao.getInconformityHouseIds(houseIds);
		
		
	}
	public List<AuditHistory> getHistoryInfo(List<Integer> houseIdList) {
		return resaleDao.getHistoryInfo(houseIdList);
	}


	public HistoryInfo getPhotoInfoHistory(HistoryInfo historyInfo) {
		return resaleDao.getPhotoInfoHistory(historyInfo);
	}
	
	
	/**
	 * @param houseIdIntList
	 * @param photoIdList
	 * @param type
	 * @return 根据houseidList和category查图片
	 */
	public List<SubAuditHistory> getResalePhotosByHouseListAndType(List<Integer> houseIdIntList, List<Integer> photoIdList, Integer type) {
		return resaleDao.getResalePhotosByHouseListAndType(houseIdIntList, photoIdList, type);
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

		return resaleDao.reAuditPhoto(historyInfo, result, user);
	}


	public List<HousePhoto> getAllHousePhotoByType(Integer houseid,Integer photoid,Integer type,Integer inalbum){
		List<HousePhoto> housePhotos = resaleDao.getAllHousePhotoByType(houseid, photoid, type, inalbum);
		return housePhotos;
	}
	public Integer getUnclaimedAwaitHouseAuditCount(
			Integer houseStatus, Integer auditStatus) {
			return resaleDao.getUnclaimedAwaitHouseAuditCount(houseStatus,auditStatus);
	}
	public Integer getPhotoCountByHouseIdList(List<Integer> list,
			Integer auditStep) {
		if(auditStep==null||Globals.AUDIT_BASE_INFO==auditStep){
			log.error("审核步骤参数为空，或者审核步骤为基本信息审核，无法获取图片数量！");
			return 0;
		}
		return resaleDao.getPhotoCountByHouseIdList(list,auditStep);
	}
	public Integer getPhotoAuditUnclaimedAwaitCount(
			Integer houseStatus, Integer auditStatus,
			Integer auditStep) {
		return resaleDao.getPhotoAuditUnclaimedAwaitCount(houseStatus, auditStatus,
				auditStep);
	}
	protected  List<com.taofang.biz.domain.house.HousePhoto> getAllHousePhoto(Integer houseid) {
		IHousePhotoService photoService = BizServiceHelper.getHousePhotoService();
		return photoService.getResalePhotos(houseid);
	}
	public List<com.taofang.biz.domain.house.HousePhoto> getDecorationByPhotos(
			List<com.taofang.biz.domain.house.HousePhoto> housePhotoList) {
		for(com.taofang.biz.domain.house.HousePhoto housePhoto:housePhotoList){
			Resale resale = resaleDao.selectResaleById(housePhoto.getHouseId());
			if(resale != null){
				//使用coverFlag存放装修信息
				housePhoto.setCoverFlag(Resources.getString("house.decoration."+resale.getDecoration()));
			}
		}
		return housePhotoList;
	}
}
