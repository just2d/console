package com.nuoshi.console.persistence.read.admin.audit;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.nuoshi.console.domain.audit.AgentAuditTask;
import com.nuoshi.console.domain.audit.AuditTask;
import com.nuoshi.console.domain.auditHistory.ReauditHistory;


public interface AuditTaskReadMapper {
	public List<Integer> getHouseIdListFromTask(@Param("auditUserId")Integer auditUserId, 
			@Param("auditStep")Integer auditStep,
			@Param("type")Integer type,
			@Param("noOne")Integer noOne,
			@Param("num")Integer num,
			@Param("cityId")Integer cityId);
	public List<Integer> getHouseIdList(@Param("auditUserId")Integer auditUserId, 
			@Param("auditStep")Integer auditStep,
			@Param("type")Integer type,
			@Param("num")Integer num,
			@Param("cityId")Integer cityId);
	public List<Integer> getPassHouseIdList(@Param("houseIdList")List<Integer> houseIdList,
			@Param("auditType")Integer auditType, 
			@Param("passSign")Integer passSign,
			@Param("auditStep")Integer auditStep);
	public List<Integer> hasNotHandleCount(@Param("houseType")Integer houseType,
			@Param("auditStep")Integer auditStep,
			@Param("untreated")Integer untreated);
	public List<AuditTask> getAuditTaskByUserIdList(@Param("userIdList")List<Integer> userIdList,
			@Param("num")Integer num);
	public List<AuditTask> getAuditTaskByUserId(@Param("userId")Integer userId,
			@Param("num")Integer num);
	public List<AgentAuditTask> getAgentAuditTaskByUserIdList(@Param("userIdList")List<Integer> userIdList,
			@Param("num")Integer num);
	public List<AgentAuditTask> getAgentAuditTaskByUserId(@Param("userId")Integer userId,
			@Param("num")Integer num);
	public List<Integer> getAgentIdList(@Param("auditUserId")Integer auditUserId, @Param("num")Integer num,
			@Param("cityId")Integer cityId);
	public List<Integer> hasNotHandleAgentCount(@Param("untreated")Integer untreated);
	public List<Integer> getWenDaIdList(@Param("auditorId")Integer auditorId,
			@Param("cityId")Integer cityId, @Param("num")int num,@Param("type")Integer type);

	public Integer getAuditHouseType(@Param("auditid")String auditid);
	
	public List<Integer> getAuditTaskAuditCount(@Param("houseType")int houseType);
	
	public List<ReauditHistory> getReauditHstryByNewSubId(@Param("thisHistoryId")String thisHistoryId);
	public int getAnomalyTaskCountByType(@Param("type")int type);
	public List<Integer> getAnomalyHouseIdByType(@Param("type")int type, @Param("num")int num);
	
	/**
	 * 根据house  的id和type 查询任务
	 * @param houseId
	 * @param houseType
	 * @return
	 */
}
