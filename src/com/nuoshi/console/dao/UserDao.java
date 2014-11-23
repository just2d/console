package com.nuoshi.console.dao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.nuoshi.console.domain.user.Role;
import com.nuoshi.console.domain.user.User;
import com.nuoshi.console.persistence.read.admin.user.RoleReadMapper;
import com.nuoshi.console.persistence.read.admin.user.UserMapper;
import com.nuoshi.console.persistence.write.taofang.agent.TFUserWriteMapper;

/**
 *<b>www.taofang.com</b>
 * <b>function:</b>
 * @author lizhenmin
 * @createDate 2011 Jul 21, 2011 2:01:05 PM
 * @email lizm@taofang.com
 * @version 1.0
 */
@Repository
public class UserDao {
	@Resource
	private UserMapper userMapper;
	@Resource
	private TFUserWriteMapper tFUserWriteMapper;
	@Resource
	private RoleReadMapper roleReadMapper;
	public List<User> getAllUserByPage() {
		List<User>  users = userMapper.getAllUserByPage();
		for (User user : users) {
			List<Role> roles = roleReadMapper.getRoleByUserId(user.getId());
			String roleName = "";
			if(roles!=null&&roles.size()>0){
				for (Role role : roles) {
					roleName = roleName+ ","+role.getName();
				}
				user.setRoleName(roleName.substring(1));
			}
		}
		
		 
		return users;
	}
	
	public List<User> getAllConsoleUser(){
		return userMapper.getAllConsoleUser();
	}
	
	public User getUserByName(String username) {
		// TODO Auto-generated method stub
		return userMapper.getUserByName(username);
	}

	public User verifyUser(User paraUser) {
		return userMapper.verifyUser(paraUser);
	}

	public User selectUserById(int id) {
		return userMapper.selectUserById(id);
	}

	public void updateUserInfo(User user) {
		userMapper.updateUserInfo(user);
	}

	public void updateUserStatus(User user) {
		userMapper.updateUserStatus(user);
	}

	public void logoutUserStatus(int id) {
		userMapper.logoutUserStatus(id);
	}

	public List<User> selectUserByRole(String role1, String role2) {
		return userMapper.selectUserByRole(role1, role2);
	}
	
	public void addUser(User user) {
		userMapper.addUser(user);
	}
	
	public void delUser(int id) {
		userMapper.deleteRoles(id+"");
		userMapper.delUser(id);
	} 
	
	public List<User> getAllSearchUserByPage(String chnName) {
		List<User>  users = userMapper.getAllSearchUserByPage(chnName);
		for (User user : users) {
			List<Role> roles = roleReadMapper.getRoleByUserId(user.getId());
			String roleName = "";
			if(roles!=null&&roles.size()>0){
				for (Role role : roles) {
					roleName= ","+role.getName();
				}
				user.setRoleName(roleName.substring(1));
			}
		}
		return users;
	}
	public void deleteRoles(String userId) {
		userMapper.deleteRoles(userId);
	}
	public void saveRoles(String userId, String roleId) {
		userMapper.saveRoles(userId,roleId);
	}
	
	public List<User> getAllUserByRole(String role) {
		return userMapper.getAllUserByRole(role);
	}
	
	public List<User> getAllZixunUser(String role) {
		return userMapper.getAllZixunUser(role);
	}
	
	/**
	 * 增加或减去用户有效问题的数量（除去删除的）
	 * 
	 * @param userId
	 * @param operate
	 * @author wangjh Feb 29, 2012 4:12:23 PM
	 */
	public void updateQuestionCount(Integer userId, boolean isAdd) {
		tFUserWriteMapper.updateQuestionCount(userId, isAdd);
	}
	/**
	 * 增加或减去用户有效回答的数量（除去删除的）
	 * 
	 * @param userId
	 * @param operate
	 * @author wangjh Feb 29, 2012 4:12:23 PM
	 */
	public void updateAnswerCount(Integer userId, boolean isAdd) {
		tFUserWriteMapper.updateAnswerCount(userId, isAdd);
	}
	
	public List<User> getAllUsersByRoleId(int id){
		return userMapper.getAllUsersByRoleId(id);
	}
}
