package com.nuoshi.console.persistence.write.admin.user;

import org.apache.ibatis.annotations.Param;

import com.nuoshi.console.domain.user.Role;

/**
 *smc
 * <b>function:</b>角色
 * @author lizhenmin
 * @createDate 2011 Jul 20, 2011 2:11:57 PM
 * @email lizm@taofang.com
 * @version 1.0
 */
public interface RoleWriteMapper {

	public int delRole(int id);
	public int addRole(Role role);
	public int updateStatus(int id,int status);
	public int  updateRoleInfo(Role role);
	public void deleteFunctions(@Param("roleId")String roleId);
	public void saveFunctions(@Param("roleId")String roleId, @Param("functionId")String functionId);
	public int delUserRole(@Param("roleId")String roleId);
}
