package com.nuoshi.console.persistence.write.forum;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import com.nuoshi.console.domain.bbs.ForumBlackUser;

public interface BlackUserWriteMapper {

	/**
	 * 加入黑名单或解锁.
	 * @param status
	 * @param userId
	 * @return
	 */
	@Update("update tf_bbs_blackuser set status = #{status} where user_id = #{userId}")
	public int  updateStatus(@Param("status") Integer status,@Param("userId") Integer userId);

	@Insert("insert into tf_bbs_blackuser (user_name,user_id,role,cts,status) values (#{userName},#{userId},#{role},#{cts},#{status})")
	public int addBlackUser(ForumBlackUser blackUser);
	
}
