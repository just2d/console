package com.nuoshi.console.persistence.read.admin.publish;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.nuoshi.console.domain.user.Publisher;

public interface ReleaseAccountReadMapper {
	
	public Publisher queryById(Integer id);
	
	public List<Publisher> queryByUserId(Integer userId);
	
	public List<Publisher> query(@Param("map")Map<String,String> map);
	
}
