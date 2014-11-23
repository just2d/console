package com.nuoshi.console.dao;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.nuoshi.console.domain.bbs.Forumuser;
import com.nuoshi.console.persistence.read.forum.BbsUserReadMapper;
import com.nuoshi.console.persistence.write.forum.BbsUserWriteMapper;

@Repository
public class FroumUserDao {
	@Resource
	private BbsUserReadMapper bbsUserReadMapper;
	@Resource
	private BbsUserWriteMapper bbsUserWriteMapper;
	
	public int updateByRole(int id){
		return bbsUserWriteMapper.updateByRole(id);
	}
	public Forumuser selectById(int id){
		return bbsUserReadMapper.selectById(id);
	}
	
	public Forumuser selectByUserId(int userId){
		return bbsUserReadMapper.selectByUserId(userId);
	}
	
	@SuppressWarnings("unchecked")
	public List<Forumuser> selectByCondition(Map paramMap){
		List<Forumuser> list = bbsUserReadMapper.selectByCondition(paramMap);
		return list != null && list.size() >0 ? list :Collections.EMPTY_LIST;
	}
	
	public List<Forumuser> selectAllByPage(){
		return bbsUserReadMapper.selectAllByPage();
	}
	
	public List<Forumuser> selectByconditionByPage(Map<String,String> conditions){
		return bbsUserReadMapper.selectByconditionByPage(conditions);
	}
	public int addfroumuser(Forumuser user){
		return bbsUserWriteMapper.addforumuser(user);
	}
	public int updateforumuser(Forumuser user){
		return bbsUserWriteMapper.updateforumuser(user);
	}
	public int deleteById(int id){
		return bbsUserWriteMapper.deleteById(id);
	}
	public int deleteRoles(int userId){
		return bbsUserWriteMapper.deleteRoles(userId);
	}
	public void saveRoles(String userId, String roleId){
		 bbsUserWriteMapper.saveRoles(userId, roleId);
	}
	public int updatefreeze(@Param("id")int id,@Param("freeze") int freeze){
		return bbsUserWriteMapper.updatefreeze(id, freeze);
	}
}
