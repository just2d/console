package com.nuoshi.console.persistence.read.taofang.agent;

import com.nuoshi.console.domain.agent.AgentRecharge;

public interface AgentRechargeReadMapper {

	AgentRecharge getRechargeByOrderNoAndAgent(AgentRecharge agentRecharge);

	AgentRecharge getRechargeByOrderNo(AgentRecharge agentRecharge);

}
