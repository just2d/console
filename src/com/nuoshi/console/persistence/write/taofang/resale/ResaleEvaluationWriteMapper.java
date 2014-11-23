package com.nuoshi.console.persistence.write.taofang.resale;

import org.apache.ibatis.annotations.Param;

public interface ResaleEvaluationWriteMapper {
	
	public int delHouseEvaluation(@Param("id")int id);
	
	public int runCommand(@Param("sql")String sql);
}
