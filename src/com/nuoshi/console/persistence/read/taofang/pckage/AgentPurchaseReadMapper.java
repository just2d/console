package com.nuoshi.console.persistence.read.taofang.pckage;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.nuoshi.console.domain.pckage.AgentPurchase;

public interface AgentPurchaseReadMapper {
	public AgentPurchase getAgentPurchase(@Param("agentId")int agentId,@Param("packageId")int packageId,@Param("port")int port);
	
	public List<AgentPurchase> getAvailableActivePackage(@Param("agentId")int agentId);
	public List<AgentPurchase> getAvailableAndUseStatusPackage(@Param("agentId")int agentId);
	
	public List<AgentPurchase> getActiveAndUseStatusPackage(@Param("agentId")int agentId);
	
	public List<AgentPurchase> getTranDetailAnalysisActiveWithSales(@Param("agentId")int agentId);
	
	public int getCountByPackageId(@Param("packageId")int packageId);

}
