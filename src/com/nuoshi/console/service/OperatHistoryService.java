package com.nuoshi.console.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.nuoshi.console.dao.OperatHistoryDao;
import com.nuoshi.console.domain.agent.history.OperatHistory;


@Service
public class OperatHistoryService {
	@Resource
	OperatHistoryDao operatHistoryDao;
	public void save(OperatHistory history){
		operatHistoryDao.addHistory(history);
	}
	public List<OperatHistory> selectHistory(Integer operandsId,Integer type){
		List<OperatHistory> list=operatHistoryDao.selectHistory(operandsId, type);
		return list;
	}
}
