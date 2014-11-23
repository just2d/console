package com.nuoshi.console.dao;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.nuoshi.console.domain.user.Role;
import com.nuoshi.console.persistence.read.admin.user.RoleReadMapper;
import com.nuoshi.console.persistence.write.admin.user.RoleWriteMapper;
import com.nuoshi.console.view.RoleView;

/**
 *<b>www.taofang.com</b>
 * <b>function:</b>
 * @author lizhenmin
 * @createDate 2011 Jul 21, 2011 2:01:05 PM
 * @email lizm@taofang.com
 * @version 1.0
 */
@Repository
public class RoleDao {
	@Resource
	private RoleReadMapper roleReadMapper;
	@Resource
	private RoleWriteMapper roleWriteMapper;
	public List<Role> getAllRoleByPage() {
		return roleReadMapper.getAllRoleByPage();
	}
	public Role selectRoleById(int id) {
		return roleReadMapper.selectRoleById(id);
	}
	public Role getRoleByName(String name, Integer id) {
		return roleReadMapper.selectRoleByName(name,id);
	}
	public Role getRoleByCode(String code,Integer id) {
		return roleReadMapper.getRoleByCode(code, id);
	}
	public int delRole(int id) {
		 roleWriteMapper.delUserRole(id+"");
		return roleWriteMapper.delRole(id);
	}

	public int updateRoleInfo(Role role) {
		return roleWriteMapper.updateRoleInfo(role);
	}
	public int addRole(Role role) {
		return roleWriteMapper.addRole(role);
	}
	public List<Role> getAllSearchRoleByPage(HashMap params) {
		// TODO Auto-generated method stub
		return roleReadMapper.getAllSearchRoleByPage(params);
	}
	public void deleteFunctions(String roleId) {
		roleWriteMapper.deleteFunctions(roleId);
	}
	public void saveFunctions(String roleId, String functionId) {
		roleWriteMapper.saveFunctions(roleId,functionId);
		
	}
	public List<Role> getAll() {
		return roleReadMapper.getAll();
	}
	public List<RoleView> selectAllByUserId(Integer userId) {
		return roleReadMapper.selectAllByUserId(userId);
	}
}
