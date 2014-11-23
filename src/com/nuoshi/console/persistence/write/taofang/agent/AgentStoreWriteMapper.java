package com.nuoshi.console.persistence.write.taofang.agent;

import org.apache.ibatis.annotations.Param;

import com.nuoshi.console.domain.agent.AgentStore;

public interface AgentStoreWriteMapper {

	void updateAgentStore(AgentStore agentStore);

	void deleteAgentStore(@Param("id")String id);

	void addAgentStore(AgentStore agentStore);

	void deleteAgentStoreByAgentCompanyId(@Param("companyId")String companyId);

	void updateAgentStoreForCompany(@Param("oleCompanyId")Integer oleCompanyId, @Param("newCompanyId")Integer newCompanyId);

	void deleteTfAsAccount(@Param("oldStoreId")Integer oldStoreId);

	void addStoreAccount(AgentStore agentStore);

	int updateTfAsAccountName(@Param("userName")String userName,@Param("storeName")String storeName, @Param("storeId")int storeId);

	void updateTfAsAccount(@Param("newStoreId")Integer newStoreId, @Param("storeName")String storeName, @Param("oldStoreId")Integer oldStoreId);

}
