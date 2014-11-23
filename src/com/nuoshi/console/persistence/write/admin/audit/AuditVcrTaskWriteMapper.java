package com.nuoshi.console.persistence.write.admin.audit;

import java.sql.Timestamp;

import org.apache.ibatis.annotations.Param;

public interface AuditVcrTaskWriteMapper {
	
	/**
	 * 添加房源视频审核任务
	 * @param userId
	 * @param userName
	 * @param houseType
	 * @param num
	 * @return
	 */
	public int addAuditVcrTask(@Param("userId")int userId, @Param("userName")String userName, @Param("houseId")int houseId, @Param("houseType")int houseType, 
			@Param("cityId")int cityId, @Param("agentId")int agentId, @Param("complainCount")int complainCount, @Param("vcrSubmitTime")Timestamp vcrSubmitTime);
	
	/**
	 * 记录审核结果
	 * @param houseId
	 * @param houseType
	 * @param result
	 * @return
	 */
	public int changeAuditResult(@Param("houseId")int houseId, @Param("houseType")int houseType, @Param("result")int result, @Param("reason")String reason);
}
