package com.nuoshi.console.persistence.read.taofang.feedback;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.nuoshi.console.domain.feedback.FeedBack;

public interface FeedBackReadMapper {
	FeedBack selectByPrimaryKey(Integer id);

	int insert(FeedBack record);

	int updateByPrimaryKeySelective(FeedBack record);

	int deleteByPrimaryKey(Integer id);

	List<FeedBack> getListByPage(HashMap<String, StringBuffer> params);

	FeedBack getOne(@Param("id")int id);
	
}