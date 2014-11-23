package com.nuoshi.console.dao;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.nuoshi.console.domain.pckage.AgentActionLog;
import com.nuoshi.console.persistence.write.taofang.pckage.AgentActionLogWriteMapper;

@Repository
public class AgentActionLogDao {
	@Resource
	private AgentActionLogWriteMapper agentActionLogWriteMapper;
	
	public int addAgentActionLog(int agentId, int houseId, int packageId, int actionType, int houseCategory, String oldValue, String newValue) {
		AgentActionLog actionLog = new AgentActionLog();
		actionLog.setAgentId(agentId);
		actionLog.setPackageId(packageId);
		actionLog.setHouseId(houseId);
		actionLog.setActionType(actionType);
		actionLog.setHouseCategory(houseCategory);
		actionLog.setOldValue(oldValue);
		actionLog.setNewValue(newValue);
		return agentActionLogWriteMapper.addAgentActionLog(actionLog);
	}
}
