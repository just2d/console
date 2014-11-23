package com.nuoshi.console.dao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.nuoshi.console.domain.topic.QueryBean;
import com.nuoshi.console.persistence.read.taofang.topic.QueryBeanReadMapper;

@Repository
public class QueryBeanDao {
	@Resource
	private QueryBeanReadMapper queryBeanReadMapper;
	
	public List<QueryBean> selQueryBeanByType(String type) {
		return queryBeanReadMapper.selQueryBeanByType(type);
	}
	
	public List<QueryBean> selQueryBeanByScope(int low, int high) {
		return queryBeanReadMapper.selQueryBeanByScope(low, high);
	}
	
	public List<QueryBean> selResalePrices(String type, int scope, int cityScale) {
		return queryBeanReadMapper.selResalePrices(type, scope, cityScale);
	}
}
