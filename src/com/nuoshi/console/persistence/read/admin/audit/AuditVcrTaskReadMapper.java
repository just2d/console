package com.nuoshi.console.persistence.read.admin.audit;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.nuoshi.console.domain.audit.AuditVcrTask;

public interface AuditVcrTaskReadMapper {
	/**
	 * 获取房源视频待审核列表
	 * @param userId
	 * @param houseType
	 * @return
	 */
	public List<AuditVcrTask> getAuditVcrTaskByUser(@Param("userId")int userId, @Param("houseType")int houseType, @Param("cityId")int cityId);
	
	/**
	 * 获取指定用户的待审核任务数量
	 * @param userId
	 * @param houseType
	 * @return
	 */
	public int getAuditVcrTaskCount(@Param("userId")int userId, @Param("houseType")int houseType, @Param("cityId")int cityId);
	
	/**
	 * 查看视频审核历史
	 * @param userId
	 * @param houseType
	 * @param cityId
	 * @return
	 */
	public List<AuditVcrTask> getAuditVcrHistoryByPage(@Param("userId")int userId, @Param("houseType")int houseType, @Param("cityId")int cityId, @Param("result")int result);

	public List<AuditVcrTask> getAuditVcrTaskByHouseId(@Param("houseId")Integer houseId, @Param("houseType")int houseType);
}
