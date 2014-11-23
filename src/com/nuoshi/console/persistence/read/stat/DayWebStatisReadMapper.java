package com.nuoshi.console.persistence.read.stat;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.nuoshi.console.domain.web.DayWebStatis;

public interface DayWebStatisReadMapper {

	public List<DayWebStatis> getCityDataByDate(@Param("date")int date);
	public List<DayWebStatis> getAllDataByDate(@Param("cityId") int cityId,@Param("distId") int distId,@Param("startDate")int startDate,@Param("endDate")int endDate);


}
