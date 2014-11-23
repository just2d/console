package com.nuoshi.console.persistence.read.taofang.pckage;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.nuoshi.console.domain.pckage.AgentPackage;

public interface AgentPackageReadMapper {
	public List<AgentPackage> getPackageByCityId(@Param("cityId")int cityId);
	
	public List<AgentPackage> getPackageByCityIdAndDisplayStatus(@Param("cityId")int cityId);
	
	public AgentPackage getDefaultPackageByCityId(@Param("cityId")int cityId);
	
	public AgentPackage getPackageById(@Param("id")int id);
	
	public AgentPackage getFullFreePackage();

	public List<AgentPackage> getPackageCache();
}
