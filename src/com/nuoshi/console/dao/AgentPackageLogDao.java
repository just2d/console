package com.nuoshi.console.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.nuoshi.console.domain.pckage.AgentPackageLog;
import com.nuoshi.console.persistence.write.taofang.pckage.AgentPackageLogWriteMapper;

@Repository
public class AgentPackageLogDao {
	@Autowired
	private AgentPackageLogWriteMapper agentPackageLogWriteMapper;
	
	public int addAgentPackageLog(AgentPackageLog agentPackageLog) {
		return agentPackageLogWriteMapper.addAgentPackageLog(agentPackageLog);
	}
	
	public int addAgentPackageLog(int agentId, int packageId, int packageStatus) {
		AgentPackageLog agentPackageLog = new AgentPackageLog();
		agentPackageLog.setAgentId(agentId);
		agentPackageLog.setPackageId(packageId);
		agentPackageLog.setPackageStatus(packageStatus);
		return agentPackageLogWriteMapper.addAgentPackageLog(agentPackageLog);
	}
}
