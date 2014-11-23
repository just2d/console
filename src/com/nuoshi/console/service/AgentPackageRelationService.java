package com.nuoshi.console.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.nuoshi.console.dao.AgentPackageRelationDAO;
import com.nuoshi.console.domain.pckage.AgentPackageRelation;

@Service
public class AgentPackageRelationService {
	@Resource
	private AgentPackageRelationDAO agentPackageRelationDAO;
	
	public int addAPRelation(AgentPackageRelation relation){
		return agentPackageRelationDAO.addAPRelation(relation);
	}
	public int delAPRelation(int agentId){
		return agentPackageRelationDAO.delAPRelation(agentId);
	}
	public int delAPRelationByPackageId(int packageId){
		return agentPackageRelationDAO.delAPRelationByPackageId(packageId);
	}
	public   List<AgentPackageRelation> getAgentSpecialPackage(int agentId){
		return agentPackageRelationDAO.getAgentSpecialPackage(agentId);
	}
	
	
}
