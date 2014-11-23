package com.nuoshi.console.persistence.write.taofang.agent;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.nuoshi.console.domain.agent.AgentMaster;
import com.nuoshi.console.domain.agent.Smslog;

public interface AgentMasterWriteMapper {
	public int updateAgentMasterVerify(AgentMaster agentMaster);
	
	public int add400UserId(@Param("agentId")int agentId, @Param("user400Id")int user400Id, @Param("number")String number);
	
	public int insertSmslog(Smslog smslog);
	
	public int incrementHouseNum(@Param("agentId")int agentId);
	
	public int incrementLabelNum(@Param("agentId")int agentId,@Param("labelNum")int labelNum, @Param("vcrNum")int vcrNum);
	
	public void updateForMobilePass(@Param("integers")List<Integer> integers);

	public void signAgent(@Param("auditSign")String auditSign, @Param("idList")List<Integer> idList);

	public int updateAuditStatusByIdList(@Param("agentIdList")List<Integer> agentIdList,
			@Param("status")Integer status, @Param("auditSign")Integer auditSign);

	public int updateAgentForStartStop(@Param("agentId")Integer agentId, @Param("type")String type,@Param("tipType")Integer tipType);

	public int updateAgentBalance(@Param("agentId")long agentId,@Param("balance")double amountdDouble);

	public AgentMaster getAgentBalance(Long agentId);

	public void updateAgentStoreByStoreId(@Param("oldStoreId")Integer oldStoreId,
			@Param("newStoreId")Integer newStoreId, @Param("storeName")String storeName);

	public void updateAgentCompanyByCompanyId(@Param("oldCompanyid")Integer oldCompanyId,
			@Param("newCompanyid")Integer newCompanyId, @Param("newCompanyName")String companyName);
	
}
