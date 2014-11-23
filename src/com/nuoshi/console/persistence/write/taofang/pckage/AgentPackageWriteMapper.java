package com.nuoshi.console.persistence.write.taofang.pckage;

import org.apache.ibatis.annotations.Param;

import com.nuoshi.console.domain.pckage.AgentPackage;

public interface AgentPackageWriteMapper {
	
	public int addAgentPackage(AgentPackage agentPackage);
	
	public int delPackage(@Param("id")int id);
	
	public int updateAgentPackage(AgentPackage agentPackage);
	
	public int changeDefaultPackageDays(@Param("packageId")int packageId, @Param("month")int month, @Param("day")int day);
	
	public int exeDefault(@Param("month")int month, @Param("cityId")int cityId);
	
	public int exeDefaultWhole(@Param("month")int month);
	
	public int exeDefaultDelay(@Param("day")int day, @Param("cityId")int cityId);
	
	public int exeDefaultDelayWhole(@Param("day")int day);
}
