package com.nuoshi.console.persistence.read.taofang.pckage;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.nuoshi.console.domain.pckage.AgentPackageRelation;

public interface APRelationReadMapper {
	
	public List<AgentPackageRelation> getAgentSpecialPackage(@Param("agentId")int agentId);
}
