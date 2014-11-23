package com.nuoshi.console.persistence.read.stat;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.nuoshi.console.domain.house.DayHouseQuality;

public interface HouseQualityReadMapper {

	public List<DayHouseQuality> getByDate(@Param("date")int date);


}
