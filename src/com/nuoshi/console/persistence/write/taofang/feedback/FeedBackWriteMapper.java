package com.nuoshi.console.persistence.write.taofang.feedback;

import org.apache.ibatis.annotations.Param;

import com.nuoshi.console.domain.feedback.FeedBack;

public interface FeedBackWriteMapper {
	FeedBack selectByPrimaryKey(Integer id);

	int insert(FeedBack record);

	int updateByPrimaryKeySelective(FeedBack record);

	int deleteByPrimaryKey(Integer id);

	void delOne(@Param("id") int id);

	void updateOne(FeedBack feedBack);
}