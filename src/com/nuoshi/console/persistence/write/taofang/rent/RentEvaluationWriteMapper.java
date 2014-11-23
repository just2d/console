package com.nuoshi.console.persistence.write.taofang.rent;

import org.apache.ibatis.annotations.Param;

public interface RentEvaluationWriteMapper {
	public int delHouseEvaluation(@Param("id")int id);
	
	public int runCommand(@Param("sql")String sql);
}
