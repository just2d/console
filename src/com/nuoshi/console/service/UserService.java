package com.nuoshi.console.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

//import com.nuoshi.console.dao.TFUserDao;
import com.nuoshi.console.dao.UserDao;
//import com.nuoshi.console.domain.user.TFUser;
import com.nuoshi.console.domain.user.User;

/**
 *<b>www.taofang.com</b>
 * <b>function:</b>
 * @author lizhenmin
 * @createDate 2011 Jul 21, 2011 10:44:48 AM
 * @email lizm@taofang.com
 * @version 1.0
 */
@Service
public class UserService extends BaseService {
	@Resource
	private UserDao userDao;
//	@Resource
//	private TFUserDao tfUserDao;
	public List<User> getAllByPage() {
		List<User> userList = userDao.getAllUserByPage();
		return userList;
	}
	
	public List<User> getAllConsoleUser(){
		return userDao.getAllConsoleUser();
	}
	
	public User  getUserByName(String username) {
		User user = userDao.getUserByName(username);
		return user;
	}

	public User verifyUser(User paraUser) {
		return userDao.verifyUser(paraUser);
	}

	public User selectUserById(int id) {
		return userDao.selectUserById(id);
	}
//	更改经纪人通知crm注释掉2012-1-4
//	public TFUser selectUserMobileById(Integer userId){
//		return tfUserDao.selectUserMobileById(userId);
//		
//	}
	
	public void updateUserInfo(User user) {
		userDao.updateUserInfo(user);
	}

	public void updateUserStatus(User user) {
		userDao.updateUserStatus(user);
	}

	public void logoutUserStatus(int id) {
		userDao.logoutUserStatus(id);
	}

	public List<User> selectUserByRole(String role1, String role2) {
		return userDao.selectUserByRole(role1, role2);
	}
	
	public void addUser(User user) {
		userDao.addUser(user);
	}
	
	public void delUser(int id) {
		userDao.delUser(id);
	}
	
	public List<User> getAllSearchUserByPage(String para) {
		return userDao.getAllSearchUserByPage(para);
	}
	public void deleteRoles(String userId) {
		userDao.deleteRoles(userId);
		
	}
	public void saveRoles(String userId, String id) {
		userDao.saveRoles(userId,id);
	}
	
	public List<User> getAllTopicUser() {
		return userDao.getAllUserByRole("MC");
	}
	
	public List<User> getAllZixunUser() {
		return userDao.getAllZixunUser("MC");
	}
	
	public void cutUserQuestionCount(Integer userId){
		userDao.updateQuestionCount(userId, false);
	}
	public void cutUserAnswerCount(Integer userId){
		userDao.updateAnswerCount(userId, false);
	}
	
	public List<User> getAllUsersByRoleId(int id){
		return userDao.getAllUsersByRoleId(id);
	}
}
