package com.nuoshi.console.dao;


import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.nuoshi.console.domain.bbs.Function;
import com.nuoshi.console.persistence.read.forum.BbsFunctionReadMapper;


@Repository
public class ForumFunctionDao {
	
	@Resource
	private BbsFunctionReadMapper BbsFunctionReadMapper;

	public List<Function> getAll(){
		return BbsFunctionReadMapper.getAll();
	}
	
}
