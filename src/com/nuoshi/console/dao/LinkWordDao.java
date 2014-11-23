package com.nuoshi.console.dao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.nuoshi.console.domain.control.LinkWord;
import com.nuoshi.console.persistence.read.admin.control.LinkWordReadMapper;
import com.nuoshi.console.persistence.write.admin.control.LinkWordWriteMapper;

@Repository
public class LinkWordDao {
	@Resource
	private LinkWordReadMapper linkWordReadMapper;
	
	@Resource
	private LinkWordWriteMapper linkWordWriteMapper;
	
	public int addLinkWord(LinkWord linkWord) {
		return linkWordWriteMapper.addLinkWord(linkWord);
	}
	
	public int updateLinkWord(LinkWord linkWord) {
		return linkWordWriteMapper.updateLinkWord(linkWord);
	}
	
	public LinkWord getLinkWordById(int id) {
		return linkWordReadMapper.getLinkWordById(id);
	}
	
	public List<LinkWord> getLinkWordList(List<String> conditions) {
		return linkWordReadMapper.getLinkWordByPage(conditions);
	}
	
	public int deleteLinkWord(int id) {
		return linkWordWriteMapper.deleteLinkWord(id);
	}
}
