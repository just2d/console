package com.nuoshi.console.persistence.read.taofang.photo;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.nuoshi.console.domain.auditHistory.SubAuditHistory;

public interface PhotoRentReadMapper {

	List<SubAuditHistory> getNewRentPhotosByHouseListAndType(@Param("houseIdIntList") List<Integer> houseIdIntList, @Param("photoIdList") List<Integer> photoIdList,
			@Param("type") Integer type);;

}
