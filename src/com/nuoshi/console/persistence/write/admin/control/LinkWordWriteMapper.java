package com.nuoshi.console.persistence.write.admin.control;

import org.apache.ibatis.annotations.Param;

import com.nuoshi.console.domain.control.LinkWord;

public interface LinkWordWriteMapper {
	public int addLinkWord(LinkWord linkWord);
	
	public int updateLinkWord(LinkWord linkWord);
	
	public int deleteLinkWord(@Param("id")int id);
}
