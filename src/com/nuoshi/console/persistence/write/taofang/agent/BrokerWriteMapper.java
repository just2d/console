package com.nuoshi.console.persistence.write.taofang.agent;

import org.apache.ibatis.annotations.Param;

import com.nuoshi.console.domain.agent.Broker;

public interface BrokerWriteMapper {

	void updateBroker(Broker broker);

	void deleteBroker(@Param("id")String id);

	void addBroker(Broker broker);

	void deleteBrokerByBrandId(@Param("brandId")String brandId);

}
