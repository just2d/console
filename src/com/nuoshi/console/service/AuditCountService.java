package com.nuoshi.console.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.taofang.biz.domain.constants.AuditConstant;
import com.taofang.biz.util.BizServiceHelper;
import com.nuoshi.console.common.Globals;
import com.nuoshi.console.domain.auditHistory.AuditorInfo;
import com.nuoshi.console.domain.user.User;

@Service
public class AuditCountService {

	@Resource
	private AuditHistoryService auditHistoryService;
	@Resource
	private AuditService auditService;
	@Resource
	private AgentMasterService agentMasterService;
	
	private Log log=LogFactory.getLog(AuditCountService.class);
	
	/**
	 * 待审核房源数量（基本信息和图片都可使用）
	 * @param type 房源类型
	 * @return
	 * @author wangjh
	 * Dec 27, 2011 11:02:05 AM
	 */
	public Integer awaitAuditCount(Integer type,Integer auditStep){
		int houseType = 0;
		switch (type) {
		case 1:
			houseType =2;
			break;
		case 2:
			houseType =1;
			break;
		}
		int count = 0;
		
		if((Globals.AGENT_TYPE==type)){
			Integer unclaimedCount = null;
			//经纪人的待审房源数量
			unclaimedCount = agentMasterService.getUnclaimedAwaitAuditCount(auditService.getAgentAuditStatus());
			
			Integer hasNotHandleCount = null;
			//获取基本信息领取但未审核的条数
			hasNotHandleCount=auditService.hasNotHandleAgentCount();
			
			if(unclaimedCount==null){
				unclaimedCount=0;
			}
			if(hasNotHandleCount==null){
				hasNotHandleCount=0;
			}
			count=unclaimedCount+hasNotHandleCount;
				
			return count;
		}
		int taskType =0;
		switch (auditStep) {
		case Globals.AUDIT_ESTATE_PHOTO:
			taskType=AuditConstant.OUTDOOR_PHOTO_TASK;
			break;
		case Globals.AUDIT_BASE_INFO:
			taskType=AuditConstant.BASIC_INFO_TASK;
			break;
		case Globals.AUDIT_INDOOR_PHOTOO:
			taskType=AuditConstant.INDOOR_PHOTO_TASK;
			break;
		case Globals.AUDIT_HOUSEHOLD_PHOTO:
			taskType=AuditConstant.LAYOUT_PHOTO_TASK;
			break;
		}
		
		
		switch (houseType) {
		case 2:
			count =BizServiceHelper.getAuditHouseService().getRentTasksAmount(taskType);
			break;

		case 1:
			count =BizServiceHelper.getAuditHouseService().getResaleTasksAmount(taskType);
			break;
		}
		
		return count;
}

	
	
	public Integer processedCountToday(Integer auditStep, Integer type,
			Integer auditUserId) {
		Date nowDate = new Date();
		Date beginTime = null;
		Date endTime = null;
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {
			beginTime = formatter.parse(format.format(nowDate) + " 00:00:00");
			endTime = formatter.parse(format.format(nowDate) + " 23:59:59");
		} catch (Exception e) {
		}
		return this.processedHouseAuditCount(auditStep, type, auditUserId,
				beginTime, endTime);

	}
	/**
	 * 处理任务个数
	 * @param houseType 
	 * @param auditUserId
	 * @param beginTime
	 * @param auditStep 审核步骤：使用审核时的常量
	 * @param endTime
	 * @return
	 * @author wangjh
	 * Dec 27, 2011 11:02:51 AM
	 */
	//处理任务个数（人，时间段）
	public Integer processedHouseAuditCount(Integer auditStep,Integer houseType,
			Integer auditUserId, Date beginTime, Date endTime) {
		Integer type=-1;
		if(Globals.AUDIT_BASE_INFO==auditStep.intValue()){
			type=Globals.AUDIT_HISTORY_BASEINFO;
		}
		if(Globals.AUDIT_ESTATE_PHOTO==auditStep.intValue()){
			type=Globals.AUDIT_HISTORY_COMMUNITY;
		}
		if(Globals.AUDIT_INDOOR_PHOTOO==auditStep.intValue()){
			type=Globals.AUDIT_HISTORY_INDOOR;
		}
		if(Globals.AUDIT_HOUSEHOLD_PHOTO==auditStep.intValue()){
			type=Globals.AUDIT_HISTORY_LAYOUT;
		}
		if(5==auditStep.intValue()){
			type=5;
		}
		Integer count = 0;
		count = auditHistoryService.processedAuditCount(type,auditUserId, houseType,
				beginTime, endTime);
		log.info("统计已经审核通过的房源数量.");
		return count;

	}
	
	/**
	 * 错审次数
	 * @param auditStep 审核步骤（标题，图片，户型图，室内图，小区图）
	 * @param houseType 房源类型（出租，二手）
	 * @param auditUserId 要查询的人
	 * @return
	 * @author wangjh
	 * Dec 27, 2011 4:03:55 PM
	 */
	//错审核次数（人，时间段 ）
	public Integer badHouseAuditNumber(Integer auditStep,Integer houseType,Integer auditUserId){
		Integer count = 0;
		count = auditHistoryService.badHouseAuditNumber(auditStep,auditUserId, houseType);
		log.info("统计错审次数。");
		return count;
	}
	
	
	/**
	 * 获取审核统计的数据
	 * @param users
	 * @param beginTime
	 * @param endTime
	 * @param beginHour
	 * @param endHour
	 * @return
	 * @author wangjh
	 * Apr 26, 2012 2:14:28 PM改
	 */
	public List<AuditorInfo> getAuditSortCount(List<User> users,String beginTime,String endTime){
		Date bTime=null;
		Date eTime=null;
		String beginHour="00:00:00";
		String endHour="23:59:59";
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			if(StringUtils.isNotBlank(beginTime)){
					bTime=format.parse(beginTime+" "+beginHour);
			}
			if(StringUtils.isNotBlank(endTime)){
				eTime=format.parse(endTime+" "+endHour);
			}
		} catch (ParseException e) {
			bTime=null;
			eTime=null;
		}
		return this.getAuditSortCountByUsers(users, bTime, eTime);
	}
	
	/**
	 * 通过审核人员List查询 审核人员统计报表
	 * @param users 审核人员List
	 * @param beginTime 开始时间
	 * @param endTime 截止时间
	 * @return
	 * @author wangjh
	 * Dec 28, 2011 6:58:33 PM
	 */
	public List<AuditorInfo> getAuditSortCountByUsers(List<User> users,
			Date beginTime, Date endTime) {
		if(CollectionUtils.isEmpty(users)){
			return new ArrayList<AuditorInfo>();
		}
		
		//错审率
		List<AuditorInfo> badRateListTotal=null;
		badRateListTotal=this.getTotalBadRateByUsers(users,beginTime, endTime);
		Map<Integer, AuditorInfo> badRateMap=new HashMap<Integer, AuditorInfo>();
		if(CollectionUtils.isNotEmpty(badRateListTotal)){
			for (AuditorInfo auditorInfo : badRateListTotal) {
				badRateMap.put(auditorInfo.getUserId(), auditorInfo);
			}
		}
		
		List<AuditorInfo> result=new ArrayList<AuditorInfo>();
		
		List<AuditorInfo> baseInfoResult=null;
		baseInfoResult=getEveryCountByUsers(Globals.AUDIT_BASE_INFO,users,beginTime, endTime);
		Map<Integer, AuditorInfo> baseInfoMap=new HashMap<Integer, AuditorInfo>();
		if(CollectionUtils.isNotEmpty(baseInfoResult)){
			for (AuditorInfo auditorInfo : baseInfoResult) {
				baseInfoMap.put(auditorInfo.getUserId(), auditorInfo);
			}
		}
		
		List<AuditorInfo> communityResult=null;
		communityResult=getEveryCountByUsers(Globals.AUDIT_ESTATE_PHOTO,users,beginTime, endTime);
		Map<Integer, AuditorInfo> communityMap=new HashMap<Integer, AuditorInfo>();
		if(CollectionUtils.isNotEmpty(communityResult)){
			for (AuditorInfo auditorInfo : communityResult) {
				communityMap.put(auditorInfo.getUserId(), auditorInfo);
			}
		}

		List<AuditorInfo> indoorResult=null;
		indoorResult=getEveryCountByUsers(Globals.AUDIT_INDOOR_PHOTOO,users,beginTime, endTime);
		Map<Integer, AuditorInfo> indoorMap=new HashMap<Integer, AuditorInfo>();
		if(CollectionUtils.isNotEmpty(indoorResult)){
			for (AuditorInfo auditorInfo : indoorResult) {
				indoorMap.put(auditorInfo.getUserId(), auditorInfo);
			}
		}

		List<AuditorInfo> layoutResult=null;
		layoutResult=getEveryCountByUsers(Globals.AUDIT_HOUSEHOLD_PHOTO,users,beginTime, endTime);
		Map<Integer, AuditorInfo> layoutMap=new HashMap<Integer, AuditorInfo>();
		if(CollectionUtils.isNotEmpty(layoutResult)){
			for (AuditorInfo auditorInfo : layoutResult) {
				layoutMap.put(auditorInfo.getUserId(), auditorInfo);
			}
		}
		
		List<AuditorInfo> agentResult=null;
		agentResult=getAgentEveryCountByUsers(users,beginTime, endTime);
		Map<Integer, AuditorInfo> agentMap=new HashMap<Integer, AuditorInfo>();
		if(CollectionUtils.isNotEmpty(agentResult)){
			for (AuditorInfo auditorInfo : agentResult) {
				agentMap.put(auditorInfo.getUserId(), auditorInfo);
			}
		}
		AuditorInfo auditorInfo=null;
		for (User user : users) {
			auditorInfo=new AuditorInfo();
			Integer userId=user.getId();
			auditorInfo.setUser(user);
			auditorInfo.setUserId(userId);
			AuditorInfo tem=new AuditorInfo();
			
			tem=baseInfoMap.get(userId);
			if(tem!=null){
				auditorInfo.setAuditBaseInfoCount(tem.getAuditBaseInfoCount());
			}
			tem=communityMap.get(userId);
			if(tem!=null){
				auditorInfo.setAuditEstatePhotoCount(tem.getAuditEstatePhotoCount());
			}
			tem=layoutMap.get(userId);
			if(tem!=null){
				auditorInfo.setAuditHouseholdPhotoCount(tem.getAuditHouseholdPhotoCount());
			}
			tem=indoorMap.get(userId);
			if(tem!=null){
				auditorInfo.setAuditIndoorPhotoCount(tem.getAuditIndoorPhotoCount());
			}
			tem=agentMap.get(userId);
			if(tem!=null){
				auditorInfo.setAuditAgentCount(tem.getAuditAgentCount());
			}
			tem=badRateMap.get(userId);
			if(tem!=null){
				auditorInfo.setTotalBadCount(tem.getTotalBadCount());
			}
			
			result.add(auditorInfo);
		}
			
		return result;
	}

	
	private List<AuditorInfo> getAgentEveryCountByUsers(List<User> users,
			Date beginTime, Date endTime) {
		return auditHistoryService.getAgentEveryCountByUsers(users,beginTime, endTime);
	}



	/**
	 * 每个人每一步审核的数量
	 * @param auditStep
	 * @param users
	 * @param beginTime
	 * @param endTime
	 * @return
	 * @author wangjh
	 * Dec 29, 2011 10:26:42 AM
	 */
	private List<AuditorInfo> getEveryCountByUsers(Integer auditStep,
			List<User> users, Date beginTime, Date endTime) {
		return auditHistoryService.getEveryCountByUsers(auditStep,users,beginTime, endTime);
	}

	/**
	 * 通过用户list获取错审率
	 * @param users
	 * @param beginTime
	 * @param endTime
	 * @return
	 * @author wangjh
	 * Dec 28, 2011 8:14:38 PM
	 */
	private List<AuditorInfo> getTotalBadRateByUsers(List<User> users,
			Date beginTime, Date endTime) {
		// 审核人id对应的审核信息
		Map<Integer,AuditorInfo> auditorInfoMap=new HashMap<Integer,AuditorInfo>();
		List<AuditorInfo> infoListForHousePhoto= auditHistoryService.getPhotoBadRateByUsers(users,beginTime, endTime);
		// 房源图片的错审次数
		Map<Integer,Integer> mapForPhoto=new HashMap<Integer, Integer>();
		if(CollectionUtils.isNotEmpty(infoListForHousePhoto)){
			for (AuditorInfo auditorInfo : infoListForHousePhoto) {
				mapForPhoto.put(auditorInfo.getUserId(), auditorInfo.getTotalBadCount());
				auditorInfoMap.put(auditorInfo.getUserId(),auditorInfo);
			}
		}
		List<AuditorInfo> infoList= auditHistoryService.getTotalBadRateByUsers(users,beginTime, endTime);
		// 除去房源图片的错审次数
		Map<Integer,Integer> map=new HashMap<Integer, Integer>();
		if(CollectionUtils.isNotEmpty(infoList)){
			for (AuditorInfo auditorInfo : infoList) {
				map.put(auditorInfo.getUserId(), auditorInfo.getTotalBadCount());
				auditorInfoMap.put(auditorInfo.getUserId(),auditorInfo);
			}
		}
		List<AuditorInfo> badNewList= auditHistoryService.getNewBadRateByUsers(users,beginTime, endTime);
		// 除去房源图片的错审次数
		Map<Integer,Integer> badNewMap=new HashMap<Integer, Integer>();
		if(CollectionUtils.isNotEmpty(badNewList)){
			for (AuditorInfo auditorInfo : badNewList) {
				badNewMap.put(auditorInfo.getUserId(), auditorInfo.getTotalBadCount());
				auditorInfoMap.put(auditorInfo.getUserId(),auditorInfo);
			}
		}
		List<AuditorInfo> sumList= new ArrayList<AuditorInfo>();
		for (Integer id : auditorInfoMap.keySet()) {
			AuditorInfo info=auditorInfoMap.get(id);
			int count=map.get(id)==null?0:map.get(id).intValue();
			int countPhoto=mapForPhoto.get(id)==null?0:mapForPhoto.get(id).intValue();
			int newCount=badNewMap.get(id)==null?0:badNewMap.get(id).intValue();
			info.setTotalBadCount(count+countPhoto+newCount);
			sumList.add(info);
		}

		return sumList;
	}


}
