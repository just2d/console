package com.nuoshi.console.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.nuoshi.console.domain.pckage.AgentPackageRelation;
import com.nuoshi.console.persistence.read.taofang.pckage.APRelationReadMapper;
import com.nuoshi.console.persistence.write.taofang.pckage.APRelationWriteMapper;

@Repository
public class AgentPackageRelationDAO {
	@Autowired
	private APRelationReadMapper aprelationReadMapper;
	@Autowired
	private APRelationWriteMapper aprelationWriteMapper;
	public List<AgentPackageRelation> getAgentSpecialPackage(int agentId){
		return aprelationReadMapper.getAgentSpecialPackage(agentId);
	}
public   int addAPRelation(AgentPackageRelation relation){
	return aprelationWriteMapper.addAPRelation(relation);
}
	
	public int delAPRelation(int agentId){
		return aprelationWriteMapper.delAPRelation(agentId);
	}
	public int delAPRelationByPackageId(int packageId){
		return aprelationWriteMapper.delAPRelationByPackageId(packageId);
	}
}
