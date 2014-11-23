package com.nuoshi.console.persistence.write.taofang.agent;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.nuoshi.console.domain.agent.AgentCompany;

public interface AgentCompanyWriteMapper {

	public void deleteAgentCompany(@Param("id")String id) ;

	public int addAgentCompany(AgentCompany agentCompany) ;

	public void updateAgentCompany(AgentCompany agentCompany) ;

	public void refreshStoreCount(@Param("companyIdList")List<Integer> companyIdList);

	public void addCompanyAccount(AgentCompany agentCompany);

	public int updateTfAsAccountName(@Param("userName")String userName, @Param("companyId")int companyId);

	public void deleteTfComAccount(@Param("oldCompanyId")Integer oldCompanyId);

	public void updateTfComAccount(@Param("newCompanyId")Integer newCompanyId,@Param("companyName")String companyName, @Param("oldCompanyId")Integer oldCompanyId);

}
