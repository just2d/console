package com.nuoshi.console.persistence.read.taofang.agent;


import org.apache.ibatis.annotations.Param;

import com.nuoshi.console.domain.agent.AgentCompany;
import com.nuoshi.console.domain.agent.AgentStore;
import com.nuoshi.console.domain.agent.TfAsAccount;

public interface  AgentStoreReadMapper {
	public AgentStore searchAgentStoreById(@Param("id")String id);

	public int getStoreAccountNum(AgentStore agentStore);

	public AgentCompany getCompanyByStoreId(@Param("storeId")int storeId);

	public TfAsAccount getStoreAccountByName(@Param("storeName")String storeName);

}
