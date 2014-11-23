package com.nuoshi.console.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import com.nuoshi.console.dao.ForumFunctionDao;
import com.nuoshi.console.dao.ForumRoleDao;
import com.nuoshi.console.domain.bbs.Function;
import com.nuoshi.console.domain.bbs.Role;

@Service
public class ForumRoleService extends BaseService {
	private static Map<Integer, String> roleMap = new HashMap<Integer, String>();

	@Resource
	private ForumRoleDao forumRoleDao;

	@Resource
	ForumFunctionDao forumFunctionDao;

	public String getRoleName(int id) {
		String name = "";
		List<Role> lists = this.getAll();
		if (lists.size() > 0) {
			for (Role role : lists) {
				roleMap.put(role.getId(), role.getName());
			}
		}
		if (roleMap.get(id) != null) {
			name = roleMap.get(id);
		}
		return name;
	}

	public List<Function> getFunctionAll() {
		return forumFunctionDao.getAll();
	}

	public int delRole(int id) {

		return forumRoleDao.delRole(id);
	}

	public int updateRoleInfo(Role role) {
		return forumRoleDao.updateRoleInfo(role);
	}

	public int addRole(Role role) {
		return forumRoleDao.addRole(role);
	}

	public void deleteFunctions(String roleId) {
		forumRoleDao.deleteFunctions(roleId);
	}

	public void saveFunctions(String roleId, String functionId) {
		forumRoleDao.saveFunctions(roleId, functionId);

	}

	public int updateStatus(int id, int status) {
		return forumRoleDao.updateStatus(id, status);
	}

	public Role selectRoleById(int id) {
		return forumRoleDao.selectRoleById(id);
	}

	public List<Role> selectByconditionByPage(String condition) {
		return forumRoleDao.selectByconditionByPage(condition);
	}

	public Role getRoleByUserId(@Param("userId") int userId) {
		return forumRoleDao.getRoleByUserId(userId);
	}

	public List<Role> getAll() {
		return forumRoleDao.getAll();
	}
	public List<Role> getAllRoleByPage(){
		return forumRoleDao.getAllRoleByPage();
	}
	public Role selectRoleByName(String name, Integer id){
		return forumRoleDao.selectRoleByName(name, id);
	}
	public Role getRoleByCode(String code, Integer id){
		return forumRoleDao.getRoleByCode(code, id);
	}
	public List<Integer>  getFunctionsByRoleId(int roleId){
		return forumRoleDao.getFunctionsByRoleId(roleId);
	}
	private void init() {

		List<Role> lists = this.getAll();
		if (lists.size() > 0) {
			for (int i = 0; i < lists.size(); i++) {
				roleMap.put(lists.get(i).getId(), lists.get(i).getName());
			}
		}

	}
	public int delRoleFunctions(Integer roleId){
		return forumRoleDao.delRoleFunctions(roleId);
	}
	

}
