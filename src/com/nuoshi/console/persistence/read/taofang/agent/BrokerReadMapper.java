package com.nuoshi.console.persistence.read.taofang.agent;


import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.nuoshi.console.domain.agent.Broker;

public interface  BrokerReadMapper {
	public Broker searchBrokerById(@Param("id")String id);
	

}
