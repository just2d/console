package com.nuoshi.console.persistence.read.forum;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.nuoshi.console.domain.bbs.Forumuser;


public interface BbsUserReadMapper {

	public List<Forumuser> selectAllByPage();
	public List<Forumuser> selectByconditionByPage(Map<String,String> conditions);
	
	public Forumuser selectById(int id);
	@Select("select id, roleid,cityid,forumid,status ,userid,username from forumuser where userid = #{userId}")
	public Forumuser selectByUserId(@Param("userId") int userId);
	public List<Forumuser> selectByCondition(Map paramMap);
}
