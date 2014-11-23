package com.nuoshi.console.dao;


import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.nuoshi.console.domain.agent.Broker;
import com.nuoshi.console.persistence.read.taofang.agent.BrokerReadMapper;
import com.nuoshi.console.persistence.write.taofang.agent.BrokerWriteMapper;

@Repository
public class BrokerDao {
	
	@Resource
	private BrokerWriteMapper brokerWriteMapper;
	
	@Resource
	private BrokerReadMapper brokerReadMapper;

	public Integer addBroker(Broker broker) {
		brokerWriteMapper.addBroker(broker);
		return broker.getId();
	}

	public void deleteBroker(String id) {
		brokerWriteMapper.deleteBroker(id);
	}

	public void updateBroker(Broker broker) {
		brokerWriteMapper.updateBroker(broker);
	}

	public Broker searchBrokerById(String id) {
		return brokerReadMapper.searchBrokerById(id);
	}

	public void deleteBrokerByBrandId(String id) {
		brokerWriteMapper.deleteBrokerByBrandId(id);
	}

}
