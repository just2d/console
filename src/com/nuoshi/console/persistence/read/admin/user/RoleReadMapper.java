package com.nuoshi.console.persistence.read.admin.user;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.nuoshi.console.domain.user.Role;
import com.nuoshi.console.view.RoleView;

/**
 *smc
 * <b>function:</b>管理员Mapper
 * @author lizhenmin
 * @createDate 2011 Jul 20, 2011 2:11:57 PM
 * @email lizm@taofang.com
 * @version 1.0
 */
public interface RoleReadMapper {

	public List<Role> getAllRoleByPage();
	public Role selectRoleById(int id);
	public Role selectRoleByName(@Param("name")String name,@Param("id") Integer id);
	public Role getRoleByCode(@Param("code")String code,@Param("id") Integer id);
	public List<Role> getAllSearchRoleByPage(HashMap params);
	public  List<Role> getRoleByUserId(@Param("userId")int userId);
	public List<Role> getAll();
	public List<RoleView> selectAllByUserId(@Param("userId")Integer userId);

}
