package com.nuoshi.console.persistence.read.taofang.user;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.nuoshi.console.domain.user.TFUser;

public interface UserReadMapper {
		@Select("select id,role,user_name as userName from  t_user  where user_name=#{userName}")
		public  TFUser getUserByUserName(@Param("userName") String userName);
		
		@Select("select id,role,user_name as userName,nick_name as nickName,cityid as cityId,name from  t_user  where id=#{userId}")
		public  TFUser getUserByUseId(@Param("userId") int id);
}
