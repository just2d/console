package com.nuoshi.console.dao;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Repository;

import com.nuoshi.console.domain.audit.AgentAuditTask;
import com.nuoshi.console.domain.audit.AuditAgentList;
import com.nuoshi.console.domain.audit.AuditPhotoSetting;
import com.nuoshi.console.domain.audit.AuditTask;
import com.nuoshi.console.domain.audit.WenDaAuditTask;
import com.nuoshi.console.domain.auditHistory.ReauditHistory;
import com.nuoshi.console.persistence.read.admin.audit.AuditPhotoSettingReadMapper;
import com.nuoshi.console.persistence.read.admin.audit.AuditTaskReadMapper;
import com.nuoshi.console.persistence.read.taofang.audit.AuditAgentListReadMapper;
import com.nuoshi.console.persistence.write.admin.audit.AuditPhotoSettingWriteMapper;
import com.nuoshi.console.persistence.write.admin.audit.AuditTaskWriteMapper;
import com.nuoshi.console.persistence.write.taofang.audit.AuditAgentListWriteMapper;


@Repository
public class AuditDao {

	@Resource
	private AuditTaskReadMapper auditTaskReadMapper;
	@Resource
	private AuditTaskWriteMapper auditTaskWriteMapper;
	@Resource
	private AuditAgentListReadMapper auditAgentListReadMapper;
	@Resource
	private AuditPhotoSettingReadMapper auditPhotoSettingReadMapper;
	@Resource
	private AuditPhotoSettingWriteMapper auditPhotoSettingWriteMapper;
	@Resource
	private AuditAgentListWriteMapper auditAgentListWriteMapper;
	
	
	public List<Integer> hasNotHandleCount(Integer houseType,Integer auditStep,Integer untreated) {
		return auditTaskReadMapper.hasNotHandleCount(houseType,auditStep,untreated);
	}
	
	/**
	 * 通过审核人员的id获取审核任务
	 * @param userIdList 审核人员idList
	 * @param num 获取的数量（获取全部需获取后，处理，然后再获取）
	 * @return
	 * @author wangjh
	 * Dec 30, 2011 3:08:35 PM
	 */
	public List<AuditTask> getAuditTaskByUserIdList(List<Integer> userIdList,Integer num){
		
		if(CollectionUtils.isEmpty(userIdList)){
			return null;
		}
		if(userIdList.size()==1){
			return auditTaskReadMapper.getAuditTaskByUserId(userIdList.get(0), num);
		}
		return auditTaskReadMapper.getAuditTaskByUserIdList( userIdList, num);
		
	}
	public List<AgentAuditTask> getAgentAuditTaskByUserIdList(List<Integer> userIdList,Integer num){
		
		if(CollectionUtils.isEmpty(userIdList)){
			return null;
		}
		if(userIdList.size()==1){
			return auditTaskReadMapper.getAgentAuditTaskByUserId(userIdList.get(0), num);
		}
		return auditTaskReadMapper.getAgentAuditTaskByUserIdList( userIdList, num);
		
	}
	
	public void updateAuditTaskAuditUser(List<Integer> taskIdList,Integer auditStep,Integer sign){
		auditTaskWriteMapper.updateAuditTaskAuditUser(taskIdList,auditStep,sign);
	}

	public void emptyAuditTaskByHouseIdList(List<Integer> houseIdList,
			Integer houseType) {
		auditTaskWriteMapper.emptyAuditTaskByHouseIdList(houseIdList,houseType);
		
	}

	public List<Integer> getAgentIdList(Integer auditUserId, Integer num,
			Integer cityId) {
		return auditTaskReadMapper.getAgentIdList(auditUserId, num, cityId);
	}

	public void buileAgentTaskByParam(Integer agentId, Integer auditUserId,
			Integer cityId) {
		auditTaskWriteMapper.buileAgentTaskByParam(agentId, auditUserId,cityId);
	}

	public void updateAgentTaskUser(List<Integer> agentIdList,
			Integer sign) {
		auditTaskWriteMapper.updateAgentTaskUser(agentIdList, sign);
	}
	public void emptyAgentTask(List<Integer> auditUserIdList,
			Integer sign) {
		auditTaskWriteMapper.emptyAgentTask(auditUserIdList, sign);
	}

	public List<Integer> hasNotHandleAgentCount(Integer auditResultUndistributed) {
		return auditTaskReadMapper.hasNotHandleAgentCount(auditResultUndistributed);
	}

	public void emptyWenDaTask(List<Integer> userIdList,
			int value, Integer emptyNum) {
		auditTaskWriteMapper.emptyWenDaTask(userIdList,
				value, emptyNum);
	}
	public void deleteWenDaTaskForBlackList(List<Integer> userIdList,
			Integer emptyNum) {
		auditTaskWriteMapper.deleteWenDaTaskForBlackList(userIdList,
				 emptyNum);
	}

	public List<Integer> getWenDaIdList(Integer auditUserId,
			Integer cityId, int num,Integer type) {
		return auditTaskReadMapper.getWenDaIdList(auditUserId,cityId,num,type);
	}

	public void updateWenDaTaskUser(List<Integer> wenDaIdList,
			Integer auditUserId,Integer type) {
		auditTaskWriteMapper.updateWenDaTaskUser(wenDaIdList,auditUserId,type);
	}

	public void saveWenDaTask(WenDaAuditTask task) {
		auditTaskWriteMapper.saveWenDaTask(task);
	}

	public void deleteWenDaTask(List<Integer> idList, Integer type) {
		auditTaskWriteMapper.deleteWenDaTask(idList, type);
	}

	public Integer getAuditHouseType(String auditid) {
		return auditTaskReadMapper.getAuditHouseType(auditid);
	}

	public List<ReauditHistory> getReauditHstryByNewSubId(String thisHistoryId) {
		return auditTaskReadMapper.getReauditHstryByNewSubId(thisHistoryId);
	}

	public int getAnomalyTaskCountByType(int type) {
		return auditTaskReadMapper.getAnomalyTaskCountByType(type);
	}

	public List<Integer> getAnomalyHouseIdByType(int type, int num) {
		return auditTaskReadMapper.getAnomalyHouseIdByType(type,num);
	}

	public List<AuditAgentList> getPhotoNameList(int cityId, int type,int agentId) {
		// TODO Auto-generated method stub
		return auditAgentListReadMapper.getPhotoNameListByPage(cityId,type,agentId);
	}

	public int updatePhotoNameList(List<String> ids, int type) {
		// TODO Auto-generated method stub
		return auditAgentListWriteMapper.updatePhotoNameList(ids,type);
	}

	public List<AuditPhotoSetting> getPhotoSetting(int cityId) {
		// TODO Auto-generated method stub
		return auditPhotoSettingReadMapper.getPhotoSettingByPage(cityId);
	}
	public int updatePhotoSetting(AuditPhotoSetting auditPhotoSetting) {
		// TODO Auto-generated method stub
		return auditPhotoSettingWriteMapper.updatePhotoSetting(auditPhotoSetting);
	}
	
	 
}
