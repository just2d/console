package com.nuoshi.console.persistence.write.taofang.agent;

import com.nuoshi.console.domain.agent.AgentRecharge;


public interface AgentRechargeWriteMapper {

	int addRecharge(AgentRecharge agentRecharge);

	int updateRechargeToSuccess(AgentRecharge agentRecharge);

	int updateRechargeToChong(AgentRecharge agentRecharge);

	int updateRechargeToIng(AgentRecharge agentRecharge);

	int updateRechargeToFail(AgentRecharge agentRecharge);
	
	int updateBankMsg(AgentRecharge agentRecharge);
	
	//读方法
	AgentRecharge getRechargeByRechargeNo(AgentRecharge agentRecharge);

	

}
