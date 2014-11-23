package com.nuoshi.console.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.nuoshi.console.domain.pckage.AgentPurchase;
import com.nuoshi.console.persistence.read.taofang.pckage.AgentPurchaseReadMapper;
import com.nuoshi.console.persistence.write.taofang.pckage.AgentPurchaseWriteMapper;

@Repository
public class AgentPurchaseDao  {
	@Autowired
	private AgentPurchaseWriteMapper agentPurchaseWriteMapper;
	
	@Autowired
	private AgentPurchaseReadMapper agentPurchaseReadMapper;
	
	public int addAgentPurchase(AgentPurchase agentPurchase) {
		return agentPurchaseWriteMapper.addAgentPurchase(agentPurchase);
	}
	
	public AgentPurchase getAgentPurchase(int agentId,int packageId,int port) {
		return agentPurchaseReadMapper.getAgentPurchase(agentId,packageId,port);
	}

	public int update(AgentPurchase agentPurchase) {
		return agentPurchaseWriteMapper.updateByPort(agentPurchase);
	}
	
	public List<AgentPurchase> getAvailableActivePackage(int agentId) {
		return  agentPurchaseReadMapper.getAvailableActivePackage(agentId);
	}
	
	public int updateActiveStatus(int id,int activeStatus){
		return agentPurchaseWriteMapper.updateActiveStatus(id, activeStatus);
	}
	
	public List<AgentPurchase> getActiveAndUseStatusPackage(int agentId) {
		return agentPurchaseReadMapper.getActiveAndUseStatusPackage(agentId);
	}
	public List<AgentPurchase> getAvailableAndUseStatusPackage(int agentId) {
		return agentPurchaseReadMapper.getAvailableAndUseStatusPackage(agentId);
	}
}
