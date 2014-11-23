package com.nuoshi.console.service;


import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import com.nuoshi.console.dao.FroumUserDao;
import com.nuoshi.console.domain.bbs.Forumuser;


@Service
public class ForumUserService extends BaseService {
	@Resource
	private FroumUserDao froumUserDao;
	
	public Forumuser selectById(int id){
		return froumUserDao.selectById(id);
	}
	
	public Forumuser selectByUserId(int userId){
		return froumUserDao.selectByUserId(userId);
	}
	
	public List<Forumuser> selectByCondition(Map paramMap){
		return froumUserDao.selectByCondition(paramMap);
	}
	
	public List<Forumuser> selectAllByPage(){
		return froumUserDao.selectAllByPage();
	}
	
	public List<Forumuser> selectByconditionByPage(Map<String,String> conditions){
		return froumUserDao.selectByconditionByPage(conditions);
	}
	public int addfroumuser(Forumuser user){
		return froumUserDao.addfroumuser(user);
	}
	public int updateforumuser(Forumuser user){
		return froumUserDao.updateforumuser(user);
	}
	public int deleteById(int id){
		return froumUserDao.deleteById(id);
	}
	public int deleteRoles(int userId){
		return froumUserDao.deleteRoles(userId);
	}
	public void saveRoles(String userId, String roleId){
		 froumUserDao.saveRoles(userId, roleId);
	}
	public int updatefreeze(@Param("id")int id,@Param("freeze") int freeze){
		return froumUserDao.updatefreeze(id, freeze);
	}
	public int updateByRole(int id){
		return froumUserDao.updateByRole(id);
	}
}
