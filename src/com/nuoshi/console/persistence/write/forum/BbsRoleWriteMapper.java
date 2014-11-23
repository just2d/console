package com.nuoshi.console.persistence.write.forum;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import com.nuoshi.console.domain.bbs.Role;


public interface BbsRoleWriteMapper {

		public int delRole(int id);
		public int addRole(Role role);
		public int updateStatus(int id,int status);
		public int  updateRoleInfo(Role role);
		public void deleteFunctions(@Param("roleId")String roleId);
		public void saveFunctions(@Param("roleId")String roleId, @Param("functionId")String functionId);
		public int delUserRole(@Param("roleId")String roleId);
		
		@Update("delete  from  role_function  where  roleid=#{roleId}")
		public int delRoleFunctions(@Param("roleId")Integer roleId);
		
		
		
}
