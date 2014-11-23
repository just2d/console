package com.nuoshi.console.persistence.read.forum;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.nuoshi.console.domain.bbs.Role;

public interface BbsRoleReadMapper {

	
		public List<Role> getAllRoleByPage();
		public Role selectRoleByName(@Param("name")String name,@Param("id") Integer id);
		public Role getRoleByCode(@Param("code")String code,@Param("id") Integer id);
	
		public Role selectRoleById(int id);
		
		public List<Role> selectByconditionByPage(@Param("condition")String condition);
		
		public  Role getRoleByUserId(@Param("userId")int userId);
		
		public List<Role> getAll();
		
		@Select("select  functionId from role_function  where roleId=#{roleId}")
		public List<Integer>  getFunctionsByRoleId(@Param("roleId") int roleId);

}
