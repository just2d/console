package com.nuoshi.console.persistence.write.taofang.pckage;

import org.apache.ibatis.annotations.Param;

import com.nuoshi.console.domain.pckage.AgentPackageRelation;

public interface APRelationWriteMapper {
	
	public int addAPRelation(AgentPackageRelation relation);
	
	public int delAPRelation(@Param("agentId")int agentId);
	public int delAPRelationByPackageId(@Param("packageId")int packageId);
}
