package com.nuoshi.console.persistence.read.taofang.agent;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.nuoshi.console.domain.agent.AgentMaster;
import com.nuoshi.console.domain.agent.Broker;
import com.nuoshi.console.domain.auditHistory.AuditHistory;
import com.nuoshi.console.domain.pckage.AgentPackage;

public interface AgentMasterReadMapper {

	public AgentMaster selectAgentMasterById(int id);
	
	public List<AgentMaster> getAllAgentMasterByPage();
	
	public List<AgentMaster> getAllAgentMaster2ByPage(@Param("conditions")List<String> conditions);
	
	public List<AgentMaster> getAgentMasterByConditions(@Param("conditions")List<String> conditions);
	
	public String selLocalDir(@Param("id")int id);
	
	public Broker selectBrokerById(@Param("brokerId")int brokerId);
	
	public int selectBrokerIdByBrandAddr(@Param("conditions")List<String> conditions);
	
	public int selectUsedHouseNum(@Param("agentId")int agentId);
	
	public int selectUsedLabelNum(@Param("agentId")int agentId);

	public List<AgentMaster> getAgentByIds(@Param("integers")List<Integer> integers);

	public String getAgentHeadFromPhoto(@Param("imgHeadId")int imgHeadId);

	public List<AgentMaster> getVerifyMobileAgentByPage(HashMap<String,Object> params);

	public AgentPackage selAgentPackageById(@Param("id")int id);

	public int ifVerifiedPhone(@Param("agentId")Integer agentId);

	public List<Integer> getAgentIdForAudit(@Param("auditSign")String auditSign, @Param("num")Integer num,
			@Param("cityId")Integer cityId,@Param("auditStatus")List<Integer> auditStatus);

	public List<AgentMaster> getAgentInfoCity(@Param("idList")List<Integer> idList);

	public String getMobileById(@Param("agentId")Integer agentId);
	
	public List<Integer> getInconformityAgentIds(@Param("agentIdList")List<Integer> agentIdList,@Param("auditStatus")List<Integer> auditStatus);

	public List<AgentMaster> selectAgentMasterForAudit(@Param("idList")List<Integer> idList);

	public List<AuditHistory> getHistoryInfo(@Param("idList")List<Integer> idList,@Param("auditType")Integer auditType);

	public Integer getUnclaimedAwaitAuditCount(@Param("auditStatus")List<Integer> auditStatus);

	public int getPhotoCountForHeadImg(@Param("imgHeadId")int imgHeadId, @Param("agentId")int agentId);

	public AgentMaster selectAgentMasterByMobile(@Param("mobile")String agentPhone);

	public List<Integer> selectAgentsByCompanyId(@Param("companyId")Integer oldCompanyId);
}
