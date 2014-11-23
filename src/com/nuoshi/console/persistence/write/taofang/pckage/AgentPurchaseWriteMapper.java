package com.nuoshi.console.persistence.write.taofang.pckage;

import org.apache.ibatis.annotations.Param;

import com.nuoshi.console.domain.pckage.AgentPurchase;

public interface AgentPurchaseWriteMapper {
	public int addAgentPurchase(AgentPurchase agentPurchase);

	public int updateByPort(AgentPurchase agentPurchase);
	
	public int updateActiveStatus(@Param("id")int id,@Param("activeStatus")int activeStatus);

}
