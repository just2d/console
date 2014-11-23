package com.nuoshi.console.dao;

import java.util.List;


import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.nuoshi.console.domain.agent.history.OperatHistory;
import com.nuoshi.console.persistence.read.admin.agent.history.OperatHistoryReadMapper;
import com.nuoshi.console.persistence.write.admin.agent.history.OperatHistoryWriteMapper;

@Repository
public class OperatHistoryDao {
	@Resource
	private OperatHistoryReadMapper operatHistoryReadMapper;
	@Resource
	private OperatHistoryWriteMapper operatHistoryWriteMapper;
	
	public void addHistory(OperatHistory history){
		operatHistoryWriteMapper.save(history);
	}
	public List<OperatHistory> selectHistory(Integer operandsId,Integer type){
		List<OperatHistory> list=operatHistoryReadMapper.selectHistoryByOperandsId(operandsId,type);
		return list;
	}
}
