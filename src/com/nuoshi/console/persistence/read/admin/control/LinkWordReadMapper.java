package com.nuoshi.console.persistence.read.admin.control;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.nuoshi.console.domain.control.LinkWord;

public interface LinkWordReadMapper {
	
	public LinkWord getLinkWordById(@Param("id")int id);
	
	public List<LinkWord> getLinkWordByPage(@Param("conditions")List<String> conditions);
}
