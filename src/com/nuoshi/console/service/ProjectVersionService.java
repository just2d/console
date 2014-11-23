package com.nuoshi.console.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nuoshi.console.dao.ProjectVersionDAO;
import com.nuoshi.console.domain.version.ProjectVersion;
import com.nuoshi.console.service.ProjectVersionService;

@Service
public class ProjectVersionService extends BaseService {
	@Autowired
	private ProjectVersionDAO projectVersionDAO;

	public int add(ProjectVersion projectVersion) {
		return  projectVersionDAO.add(projectVersion);
	}

	public int deleteById(int id) {
		return projectVersionDAO.deleteById(id);
	}
	
	public ProjectVersion getById(int id) {
		return projectVersionDAO.getById(id);
	}

	public List<ProjectVersion> getAll() {
		return projectVersionDAO.getAll();
	}

	public int update(ProjectVersion pv) {
		return projectVersionDAO.update(pv);
	}
}
