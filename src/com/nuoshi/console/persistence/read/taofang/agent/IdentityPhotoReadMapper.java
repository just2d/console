package com.nuoshi.console.persistence.read.taofang.agent;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.nuoshi.console.domain.agent.IdentityPhoto;
import com.nuoshi.console.domain.photo.Photo;

public interface IdentityPhotoReadMapper {
	public IdentityPhoto selectIdentityPhotoById(@Param("id")int id, @Param("agentId")int agentId);
	
	public Photo selectPhotoById(int id);

	public List<IdentityPhoto> selectIdentityPhotoByIdList(@Param("idList")List<Integer> idList);

	public List<Photo> selectPhotoByIdList(@Param("idList")List<Integer> idList);
}
