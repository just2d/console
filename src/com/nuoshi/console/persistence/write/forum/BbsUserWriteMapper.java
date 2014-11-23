package com.nuoshi.console.persistence.write.forum;

import org.apache.ibatis.annotations.Param;

import com.nuoshi.console.domain.bbs.Forumuser;

public interface BbsUserWriteMapper {

	public int addforumuser(Forumuser user);
	public int updateforumuser(Forumuser user);
	
	public int updateByRole(int id);
	
	public int deleteById(int id);
	public int deleteRoles(int userId);
	public void saveRoles(@Param("userId")String userId,@Param("roleId") String roleId);
	public int updatefreeze(@Param("id")int id,@Param("freeze") int freeze);
}
