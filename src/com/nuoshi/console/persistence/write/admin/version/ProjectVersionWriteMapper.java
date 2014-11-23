package com.nuoshi.console.persistence.write.admin.version;

import com.nuoshi.console.domain.version.ProjectVersion;

public interface ProjectVersionWriteMapper {

	//添加
	public int add(ProjectVersion projectVersion);
	//删除
	public int deleteById(int id);
	//删除
	public int update(ProjectVersion projectVersion);
  
}