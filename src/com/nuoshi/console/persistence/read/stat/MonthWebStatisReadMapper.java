package com.nuoshi.console.persistence.read.stat;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.nuoshi.console.domain.web.MonthWebStatis;

public interface MonthWebStatisReadMapper {

	public List<MonthWebStatis> getCityDataByDate(@Param("date")int date);
	public List<MonthWebStatis> getAllDataByDate(@Param("cityId")int cityId,@Param("distId")int distId,@Param("startDate")int statrDate,@Param("endDate")int endDate);


}
