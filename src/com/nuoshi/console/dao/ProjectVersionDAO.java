package com.nuoshi.console.dao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.nuoshi.console.dao.ProjectVersionDAO;
import com.nuoshi.console.persistence.write.admin.version.ProjectVersionWriteMapper;
import com.nuoshi.console.persistence.read.admin.version.ProjectVersionReadMapper;
import com.nuoshi.console.domain.version.ProjectVersion;

@Repository
public class ProjectVersionDAO {

	@Resource
	private ProjectVersionWriteMapper projectVersionWriteMapper;
	
	@Resource
	private ProjectVersionReadMapper projectVersionReadMapper;
	
	public int add(ProjectVersion projectVersion){
		return projectVersionWriteMapper.add(projectVersion);
	} 
	public int deleteById(int id){
		return projectVersionWriteMapper.deleteById(id);
	} 
	public ProjectVersion getById(int id){
		return projectVersionReadMapper.getById(id);
	}
	public List<ProjectVersion> getAll() {
		// TODO Auto-generated method stub
		return projectVersionReadMapper.getAll();
	}
	public int update(ProjectVersion pv) {
		// TODO Auto-generated method stub
		return projectVersionWriteMapper.update(pv);
	} 
	
}
