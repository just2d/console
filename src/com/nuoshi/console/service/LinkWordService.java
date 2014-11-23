package com.nuoshi.console.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.nuoshi.console.dao.LinkWordDao;
import com.nuoshi.console.domain.control.LinkWord;

@Service
public class LinkWordService {
	@Resource
	private LinkWordDao linkWordDao;

	public List<LinkWord> getLinkWordList(LinkWord linkWord) {
		List<String> conditions = new ArrayList<String>();
		if (linkWord != null) {
			if(StringUtils.isNotBlank(linkWord.getKeyword()) && !"请输入关键字".equals(linkWord.getKeyword())) {
				conditions.add("keyword LIKE '%"+linkWord.getKeyword()+"%'");
			}
			if(StringUtils.isNotBlank(linkWord.getStartDate()) && !"起始日期".equals(linkWord.getStartDate())) {
				conditions.add("create_time > '"+linkWord.getStartDate()+"'");
			}
			if(StringUtils.isNotBlank(linkWord.getEndDate()) && !"终止日期".equals(linkWord.getEndDate())) {
				conditions.add("create_time < '"+linkWord.getEndDate()+"'");
			}
		}
		if (conditions.size() == 0) {
			conditions = null;
		}
		return linkWordDao.getLinkWordList(conditions);
	}
	
	public int addLinkWord(LinkWord linkWord) {
		return linkWordDao.addLinkWord(linkWord);
	}
	
	public int updateLinkWord(LinkWord linkWord) {
		return linkWordDao.updateLinkWord(linkWord);
	}
	
	public int deleteLinkWord(int id) {
		return linkWordDao.deleteLinkWord(id);
	}

}
