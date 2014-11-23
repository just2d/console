package com.nuoshi.console.persistence.write.taofang.agent;

import org.apache.ibatis.annotations.Param;

public interface TransferPhoneWriteMapper {
	public int updatePhoneStatus(@Param("phone") String phone,
			@Param("currentTime") int currentTime,
			@Param("agentId") int agentId, @Param("user400Id") int user400Id);
	
	public int resetPhoneStatus(@Param("phone")String phone);
}
