package com.nuoshi.console.persistence.read.taofang.topic;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.nuoshi.console.domain.topic.QueryBean;

public interface QueryBeanReadMapper {
	public List<QueryBean> selQueryBeanByType(String type);
	
	public List<QueryBean> selQueryBeanByScope(@Param("low")int low, @Param("high")int high);
	
	public List<QueryBean> selResalePrices(@Param("type") String type, @Param("scope")int scope, @Param("cityScale")int cityScale);
}
