package com.nuoshi.console.persistence.read.admin.user;

import java.util.List;

import org.apache.ibatis.annotations.Param;

//import com.nuoshi.console.domain.user.TFUser;
import com.nuoshi.console.domain.user.User;

/**
 *smc
 * <b>function:</b>管理员Mapper
 * @author lizhenmin
 * @createDate 2011 Jul 20, 2011 2:11:57 PM
 * @email lizm@taofang.com
 * @version 1.0
 */
public interface UserMapper {
	public User getUserByName(String username);
	public List<User> getAllUserByPage();
	public User selectUserById(int id);
	public User verifyUser(User user);
	public void updateUserInfo(User user);
	public void updateUserStatus(User user);
	public void logoutUserStatus(int id);
	public List<User> selectUserByRole(@Param("role1") String role1, @Param("role2") String role2);
	public int addUser(User user);
	public int delUser(int id);
	public List<User> getAllSearchUserByPage(String chnName);
	public int deleteRoles(String userId);
	public int saveRoles(@Param("userId")String userId,@Param("roleId") String roleId);
	public List<User> getAllUserByRole(@Param("role")String role);
	public List<User> getAllUsersByRoleId(@Param("roleId")int roleId);
	public List<User> getAllZixunUser(@Param("role")String role);
	public List<User> getAllConsoleUser();
//	public TFUser selectUserMobileById(@Param("userId")Integer userId);
}
