package com.nuoshi.console.persistence.read.admin.version;

import java.util.List;

import com.nuoshi.console.domain.version.ProjectVersion;

public interface ProjectVersionReadMapper {

	//根据ID查找
	public ProjectVersion getById(int id);

	public List<ProjectVersion> getAll();
  
}