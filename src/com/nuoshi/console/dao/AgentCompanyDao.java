package com.nuoshi.console.dao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.nuoshi.console.domain.agent.AgentCompany;
import com.nuoshi.console.persistence.read.taofang.agent.AgentCompanyReadMapper;
import com.nuoshi.console.persistence.write.taofang.agent.AgentCompanyWriteMapper;



@Repository
public class AgentCompanyDao {

		
		@Resource
		private AgentCompanyWriteMapper agentCompanyWriteMapper;
		
		@Resource
		private AgentCompanyReadMapper agentCompanyReadMapper;
		
		
		public List<AgentCompany> searchAgentCompanyBySql() {
			return agentCompanyReadMapper.searchAgentCompanyBySql();
		}
		
		
		public void updateAgentCompany(AgentCompany agentCompany) {
			agentCompanyWriteMapper.updateAgentCompany(agentCompany);
		}
		
		public int addAgentCompany(AgentCompany agentCompany) {
			agentCompanyWriteMapper.addAgentCompany(agentCompany);
			return agentCompany.getId();
		}
		
		public void deleteAgentCompany(String id){
			agentCompanyWriteMapper.deleteAgentCompany(id);
		}


		public AgentCompany getAgentCompanyById(Integer agentCompanyId) {
			return agentCompanyReadMapper.getAgentCompanyById(agentCompanyId);
		}


		public void refreshStoreCount(List<Integer> companyIdList) {
			agentCompanyWriteMapper.refreshStoreCount(companyIdList);
		}


		public boolean isCompanyAccountExist(AgentCompany agentCompany) {
			int num = agentCompanyReadMapper.getCompanyAccountNum(agentCompany);
			if(num > 0) return true;
			return false;
		}


		public void addCompanyAccount(AgentCompany agentCompany) {
			agentCompanyWriteMapper.addCompanyAccount(agentCompany);
			
		}


		public boolean isCompanyAccountNameExist(AgentCompany agentCompany) {
			int num = agentCompanyReadMapper.getCompanyAccountNameNum(agentCompany);
			if(num > 0) return true;
			return false;
		}


		public int updateTfAsAccountName(String userName, int companyId) {
			return agentCompanyWriteMapper.updateTfAsAccountName(userName,companyId);
		}


		public void deleteTfComAccount(Integer oldCompanyId) {
			agentCompanyWriteMapper.deleteTfComAccount(oldCompanyId);
			
		}


		public void updateTfComAccount(Integer newCompanyId, String companyName, Integer oldCompanyId) {
			agentCompanyWriteMapper.updateTfComAccount(newCompanyId,companyName,oldCompanyId);
			
		}

}
