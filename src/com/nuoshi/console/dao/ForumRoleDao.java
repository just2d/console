package com.nuoshi.console.dao;


import java.util.List;

import javax.annotation.Resource;
import org.springframework.stereotype.Repository;

import com.nuoshi.console.domain.bbs.Role;
import com.nuoshi.console.persistence.read.forum.BbsRoleReadMapper;
import com.nuoshi.console.persistence.write.forum.BbsRoleWriteMapper;


@Repository
public class ForumRoleDao {
	
	@Resource
	private BbsRoleReadMapper bbsRoleReadMapper;
	
	@Resource
	private BbsRoleWriteMapper bbsRoleWriteMapper;

	public int delRole(int id) {
		return bbsRoleWriteMapper.delRole(id);
	}

	public int updateRoleInfo(Role role) {
		return bbsRoleWriteMapper.updateRoleInfo(role);
	}
	public int addRole(Role role) {
		return bbsRoleWriteMapper.addRole(role);
	}

	public void deleteFunctions(String roleId) {
		bbsRoleWriteMapper.deleteFunctions(roleId);
	}
	public void saveFunctions(String roleId, String functionId) {
		bbsRoleWriteMapper.saveFunctions(roleId,functionId);
		
	}

	public int updateStatus(int id,int status){
		return bbsRoleWriteMapper.updateStatus(id, status);
	}
	public Role selectRoleById(int id){
		return bbsRoleReadMapper.selectRoleById(id);
	}
	
	public List<Role> selectByconditionByPage(String condition){
		return bbsRoleReadMapper.selectByconditionByPage(condition);
	}
	
	public  Role getRoleByUserId(int userId){
		return bbsRoleReadMapper.getRoleByUserId(userId);
	}
	
	public List<Role> getAll(){
		return bbsRoleReadMapper.getAll();
	}
	
	public List<Role> getAllRoleByPage(){
		return bbsRoleReadMapper.getAllRoleByPage();
	}
	public Role selectRoleByName(String name, Integer id){
		return bbsRoleReadMapper.selectRoleByName(name, id);
	}
	public Role getRoleByCode(String code, Integer id){
		return bbsRoleReadMapper.getRoleByCode(code, id);
	}
	public List<Integer>  getFunctionsByRoleId(int roleId){
		return bbsRoleReadMapper.getFunctionsByRoleId(roleId);
	}
	public int delRoleFunctions(Integer roleId){
		return bbsRoleWriteMapper.delRoleFunctions(roleId);
	}
}
