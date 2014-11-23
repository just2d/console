package com.nuoshi.console.persistence.read.taofang.agent;

import java.util.Date;

import org.apache.ibatis.annotations.Param;

import com.nuoshi.console.domain.agent.TransferPhone;

public interface TransferPhoneReadMapper {
	
	public int selectCount(@Param("date") Date date);
	
	public String selectRecommendPhone(@Param("start") int start, @Param("date") Date date);
	
	public int getBoundDate(@Param("phone")String phone);
	
	public TransferPhone selectPhoneByPhone(@Param("phone")String phone);
	
	public TransferPhone selPhoneByAgentId(@Param("agentId")int agentId);
}
