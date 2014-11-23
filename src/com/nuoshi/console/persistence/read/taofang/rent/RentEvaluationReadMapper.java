package com.nuoshi.console.persistence.read.taofang.rent;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.nuoshi.console.domain.base.HouseEvaluation;

public interface RentEvaluationReadMapper {
	public List<HouseEvaluation> getHouseEvaluationByPage(@Param("houseId")int houseId, @Param("sourceId")int sourceId);
	
	public int runCommand(@Param("sql")String sql);
	
	public float runFloatCommand(@Param("sql")String sql);
}
