package com.nuoshi.console.persistence.read.admin.agent.history;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.nuoshi.console.domain.agent.history.OperatHistory;

public interface OperatHistoryReadMapper {

	
	public List<OperatHistory> selectHistoryByOperandsId(@Param("operandsId")Integer operandsId,
			@Param("type")Integer type);

}
