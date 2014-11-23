package com.nuoshi.console.persistence.write.admin.agent;

import com.nuoshi.console.domain.agent.AgentDeleteLog;

public interface AgentDeleteLogWriteMapper {
	public int addAgentDeleteLog(AgentDeleteLog log);
}
