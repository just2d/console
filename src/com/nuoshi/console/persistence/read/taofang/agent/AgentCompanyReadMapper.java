package com.nuoshi.console.persistence.read.taofang.agent;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.nuoshi.console.domain.agent.AgentCompany;

public interface AgentCompanyReadMapper {

	public List<AgentCompany> searchAgentCompanyBySql() ;

	public AgentCompany getAgentCompanyById(@Param("companyId")Integer companyId);

	public int getCompanyAccountNum(AgentCompany agentCompany);

	public int getCompanyAccountNameNum(AgentCompany agentCompany);

}
