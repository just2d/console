package com.nuoshi.console.persistence.write.admin.audit;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.nuoshi.console.domain.audit.AuditStep;
import com.nuoshi.console.domain.audit.WenDaAuditTask;
import com.nuoshi.console.domain.rent.RentInfo;
import com.nuoshi.console.domain.resale.ResaleInfo;

public interface AuditTaskWriteMapper {
	public void buildTaskByIds(@Param("idList")List<Integer> idList,
			@Param("type")Integer type,
			@Param("auditUserId") Integer auditUserId,
			@Param("auditStep")Integer auditStep);
	
	
	public void buildTask(@Param("auditSign")String auditSign, 
			@Param("auditStatus")Integer auditStatus,
			@Param("houseStatus")Integer houseStatus, 
			@Param("num")Integer num,
			@Param("auditStep")Integer auditStep,
			@Param("auditUserId")Integer auditUserId,
			@Param("type")Integer type,
			@Param("cityId") Integer cityId);
	
	
	
	public void buildRentTaskByParam(
			@Param("info")RentInfo info, 
			@Param("auditStep")Integer auditStep,
			@Param("auditUserId")Integer auditUserId,
			@Param("type")Integer type,
			@Param("result")AuditStep as);
	
	public void buildResaleTaskByParam(
			@Param("info")ResaleInfo info, 
			@Param("auditStep")Integer auditStep,
			@Param("auditUserId")Integer auditUserId,
			@Param("type")Integer type,
			@Param("result")AuditStep as);
	
	public void buildTaskByMap(@Param("sqlMap")Map<String,String> sqlMap,
			@Param("auditStep")Integer auditStep);
	
	
	public void setAuditTaskResult(@Param("auditStep")Integer auditStep, 
			@Param("houseIdList")List<Integer> houseIdList,
			@Param("result")Integer result,
			@Param("auditType")Integer auditType);
	
	
	
	public void updateAuditTaskUser(@Param("taskIdList")List<Integer> houseIdList, 
			@Param("auditUserId")Integer auditUserId,
			@Param("auditStep")Integer auditStep);
	
	public void updateAuditTaskStatus(@Param("houseIdList")List<Integer> houseIdList,
			@Param("auditStep")Integer auditStep, 
			@Param("auditType")Integer auditType,
			@Param("auditResult")Integer auditResult);
	
	
	public void deleteAuditTask(@Param("houseIdList")List<Integer> houseIdList, 
			@Param("auditType")Integer auditType);
	

	public void updateAuditTaskAuditUser(@Param("taskIdList")List<Integer> taskIdList,
			@Param("auditStep")Integer auditStep,
			@Param("sign")Integer sign);


	public void emptyAuditTaskByHouseIdList(@Param("houseIdList")List<Integer> houseIdList,
			@Param("houseType")Integer houseType);


	public void buileAgentTaskByParam(@Param("agentId")Integer agentId, @Param("auditUserId")Integer auditUserId,
			@Param("cityId")Integer cityId);


	public void deleteAgentAuditTask(@Param("agentIdList")List<Integer> agentIdList);


	public void updateAgentTaskUser(@Param("agentIdList")List<Integer> agentIdList, @Param("sign")Integer sign);
	public void emptyAgentTask(@Param("auditUserIdList")List<Integer> auditUserIdList, @Param("sign")Integer sign);


	public void emptyWenDaTask(@Param("userIdList")List<Integer> userIdList, @Param("value")int value,
			@Param("emptyNum")Integer emptyNum);
	
	public void deleteWenDaTaskForBlackList(@Param("userIdList")List<Integer> userIdList, @Param("emptyNum")Integer emptyNum);


	public void updateWenDaTaskUser(@Param("wenDaIdList")List<Integer> wenDaIdList,
			@Param("auditUserId")Integer auditUserId,@Param("type")Integer type);


	public void saveWenDaTask(WenDaAuditTask task);


	public void deleteWenDaTask(@Param("idList")List<Integer> idList, @Param("type")Integer type);
	
	

}
