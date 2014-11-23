package com.nuoshi.console.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.nuoshi.console.dao.AgentPackageRelationDAO;
import com.nuoshi.console.domain.pckage.AgentPackage;
import com.nuoshi.console.domain.pckage.AgentPackageRelation;
import com.nuoshi.console.persistence.read.taofang.pckage.AgentPackageReadMapper;
import com.nuoshi.console.persistence.read.taofang.pckage.AgentPurchaseReadMapper;
import com.nuoshi.console.persistence.write.taofang.pckage.APRelationWriteMapper;
import com.nuoshi.console.persistence.write.taofang.pckage.AgentPackageWriteMapper;

@Service
public class AgentPackageService extends BaseService {
	@Resource
	private AgentPackageReadMapper agentPackageReadMapper;
	
	@Resource
	private AgentPackageWriteMapper agentPackageWriteMapper;
	
	 
	@Resource
	private AgentPackageRelationDAO agentPackageRelationDAO;
	
	public List<AgentPackage> getPackageByCityId(int cityId) {
		return agentPackageReadMapper.getPackageByCityId(cityId);
	}
	
	public AgentPackage getDefaultPackageByCityId(int cityId) {
		return agentPackageReadMapper.getDefaultPackageByCityId(cityId);
	}
	
	public AgentPackage getPackageById(int id) {
		return agentPackageReadMapper.getPackageById(id);
	}
	
	public int addAgentPackage(AgentPackage agentPackage) {
		return agentPackageWriteMapper.addAgentPackage(agentPackage);
	} 
	
	public int delPackage(int id) {
		/*int count = agentPurchaseReadMapper.getCountByPackageId(id);
		if(count < 1) {*/
		agentPackageRelationDAO.delAPRelationByPackageId(id);
			return agentPackageWriteMapper.delPackage(id);
		/*}
		return 0;*/
	}
	
	public int updateAgentPackage(AgentPackage agentPackage) {
		if(agentPackage.getDefaultValue()!=2){
			agentPackageRelationDAO.delAPRelationByPackageId(agentPackage.getId());
		}
		return agentPackageWriteMapper.updateAgentPackage(agentPackage);
	}
	
	public int changeDefaultPackageDays(int packageId, int month, int day) {
		return agentPackageWriteMapper.changeDefaultPackageDays(packageId, month, day);
	}
	
	public boolean exeDefault(int month, int cityId) {
		int result = agentPackageWriteMapper.exeDefault(month, cityId);
		return result > 0;
	}
	
	public boolean exeDefaultWhole(int month) {
		int result = agentPackageWriteMapper.exeDefaultWhole(month);
		return result > 0;
	}
	
	public boolean exeDefaultDelay(int day, int cityId) {
		int result = agentPackageWriteMapper.exeDefaultDelay(day, cityId);
		return result > 0;
	}
	
	public boolean exeDefaultDelayWhole(int day) {
		int result = agentPackageWriteMapper.exeDefaultDelayWhole(day);
		return result > 0;
	}

	public  List<AgentPackage>  getPackageByCityIdAndDisplayStatus(int cityId) {
		// TODO Auto-generated method stub
		return agentPackageReadMapper.getPackageByCityIdAndDisplayStatus(cityId);
	}

	public List<AgentPackageRelation>  getAgentSpecialPackage(int agentId) {
		// TODO Auto-generated method stub
		return agentPackageRelationDAO.getAgentSpecialPackage(agentId);
	}
}
