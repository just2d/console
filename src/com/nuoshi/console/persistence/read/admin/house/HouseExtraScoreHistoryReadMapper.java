package com.nuoshi.console.persistence.read.admin.house;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.nuoshi.console.domain.house.HouseExtraScoreHistory;

public interface HouseExtraScoreHistoryReadMapper {

	 
	public List<HouseExtraScoreHistory> getByHouseId(@Param("houseId")int houseId,@Param("houseType") int houseType);
  
}