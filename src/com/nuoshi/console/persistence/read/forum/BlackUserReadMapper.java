package com.nuoshi.console.persistence.read.forum;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.nuoshi.console.domain.bbs.ForumBlackUser;

public interface BlackUserReadMapper {

	/**
	 * 获得黑名单列表.
	 * @param paramMap
	 * @return
	 */
	public List<ForumBlackUser> getUserListByPage(Map<String, String> paramMap);

	@Select("select id,user_id userId,user_name userName,role,cts,status from tf_bbs_blackuser where user_name = #{userName}")
	public List<ForumBlackUser> getUserByUserName(@Param("userName")String userName);
	
	@Select("select id,user_id userId,user_name userName,role,cts,status from tf_bbs_blackuser where user_id = #{userId}")
	public List<ForumBlackUser> getUserByUserId(@Param("userId")Integer userId);
}
