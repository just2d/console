package com.nuoshi.console.dao;


import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.nuoshi.console.domain.agent.AgentCompany;
import com.nuoshi.console.domain.agent.AgentStore;
import com.nuoshi.console.domain.agent.TfAsAccount;
import com.nuoshi.console.persistence.read.taofang.agent.AgentStoreReadMapper;
import com.nuoshi.console.persistence.write.taofang.agent.AgentStoreWriteMapper;

@Repository
public class AgentStoreDao {
	
	@Resource
	private AgentStoreWriteMapper agentStoreWriteMapper;
	
	@Resource
	private AgentStoreReadMapper agentStoreReadMapper;

	public Integer addAgentStore(AgentStore agentStore) {
		agentStoreWriteMapper.addAgentStore(agentStore);
		return agentStore.getId();
	}

	public void deleteAgentStore(String id) {
		agentStoreWriteMapper.deleteAgentStore(id);
	}

	public void updateAgentStore(AgentStore agentStore) {
		agentStoreWriteMapper.updateAgentStore(agentStore);
	}

	public AgentStore searchAgentStoreById(String id) {
		return agentStoreReadMapper.searchAgentStoreById(id);
	}

	public void deleteAgentStoreByAgentCompanyId(String id) {
		agentStoreWriteMapper.deleteAgentStoreByAgentCompanyId(id);
	}

	public void updateAgentStoreForCompany(Integer oleCompanyId,
			Integer newCompanyId) {
		agentStoreWriteMapper.updateAgentStoreForCompany(oleCompanyId,newCompanyId);
		
	}

	public void deleteTfAsAccount(Integer oldStoreId) {
		agentStoreWriteMapper.deleteTfAsAccount(oldStoreId);
		
	}

	public boolean isStoreAccountExist(AgentStore agentStore) {
		int num = agentStoreReadMapper.getStoreAccountNum(agentStore);
		if(num > 0 ) return true;
		return false;
	}

	public void addStoreAccount(AgentStore agentStore) {
		System.err.println("开始更新:agentStore");
		int storeId = agentStore.getId() ;
		System.err.println("storeId:"+storeId);
		AgentCompany company = agentStoreReadMapper.getCompanyByStoreId(storeId);
		if(company==null){
			System.err.println("没有找到公司 ");
		}
		System.err.println("company.getId():"+company.getId()+"===company.getName():"+company.getName());
		agentStore.setCompanyId(company.getId());
		agentStore.setCompanyName(company.getName());
		agentStoreWriteMapper.addStoreAccount(agentStore);
		System.err.println("agentStore:"+agentStore.getId()+"===");
		agentStore.setId(storeId);
	}

	public TfAsAccount getStoreAccountByName(AgentStore agentStore) {
		TfAsAccount as = agentStoreReadMapper.getStoreAccountByName(agentStore.getName());
		
		return as;
	}

	public int updateTfAsAccountName(String userName,String storeName,int storeId) {
		return agentStoreWriteMapper.updateTfAsAccountName(userName,storeName,storeId);
		
	}

	public void updateTfAsAccount(Integer newStoreId, String storeName, Integer oldStoreId) {
		agentStoreWriteMapper.updateTfAsAccount(newStoreId,storeName,oldStoreId);
	}

}
