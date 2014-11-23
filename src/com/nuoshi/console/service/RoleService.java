package com.nuoshi.console.service;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.nuoshi.console.dao.RoleDao;
import com.nuoshi.console.domain.user.Role;
import com.nuoshi.console.view.RoleView;

/**
 *<b>www.taofang.com</b>
 * <b>function:</b>
 * @author lizhenmin
 * @createDate 2011 Jul 21, 2011 10:44:48 AM
 * @email lizm@taofang.com
 * @version 1.0
 */
@Service
public class RoleService extends BaseService {
	@Resource
	private RoleDao roleDao;
	public List<Role> getAllByPage() {
		List<Role> roleList = roleDao.getAllRoleByPage();
		return roleList;
	}


	public Role selectRoleById(int id) {
		return roleDao.selectRoleById(id);
	}
	public Role getRoleByName(String name,Integer id) {
		return roleDao.getRoleByName(name,id);
	}
	public Role getRoleByCode(String code,Integer id) {
		return roleDao.getRoleByCode(code,id);
	}

	public void updateRoleInfo(Role role) {
		roleDao.updateRoleInfo(role);
	}
	public void addRole(Role role) {
		roleDao.addRole(role);
	}
	public int delRole(int id) {
		return roleDao.delRole(id);
	}


	public List<Role> getAllSearchRoleByPage(HashMap params) {
		return roleDao.getAllSearchRoleByPage(params);
	}


	public void deleteFunctions(String roleId) {
		  roleDao.deleteFunctions(roleId);
	}


	public void saveFunctions(String roleId, String functionId) {
		roleDao.saveFunctions(roleId, functionId);
		
	}


	public List<Role> getAll() {
		return roleDao.getAll();
	}


	public List<RoleView> selectAllByUserId(Integer userId) {
		return roleDao.selectAllByUserId(userId);
	}

	public boolean getUserIsManager(int userId) {
		List<RoleView> result = roleDao.selectAllByUserId(userId);
		if(result != null && result.size() > 0) {
			for(RoleView view : result) {
				if("管理者".equalsIgnoreCase(view.getName())) {
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean getUserIsHouseManager(int userId) {
		List<RoleView> result = roleDao.selectAllByUserId(userId);
		if(result != null && result.size() > 0) {
			for(RoleView view : result) {
				if("房源管理".equalsIgnoreCase(view.getName())) {
					return true;
				}
			}
		}
		return false;
	}
}
