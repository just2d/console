package com.nuoshi.console.dao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.nuoshi.console.domain.agent.IdentityPhoto;
import com.nuoshi.console.domain.photo.Photo;
import com.nuoshi.console.persistence.read.taofang.agent.IdentityPhotoReadMapper;
import com.nuoshi.console.persistence.write.taofang.agent.IdentityPhotoWriteMapper;

@Repository
public class IdentityPhotoDao {
    @Resource
    private IdentityPhotoReadMapper identityPhotoReadMapper;
    
    @Resource
    private IdentityPhotoWriteMapper identityPhotoWriteMapper;
    
    public IdentityPhoto selectIdentityPhotoById(int id, int agentId) {
    	return identityPhotoReadMapper.selectIdentityPhotoById(id, agentId);
    }
    public List<IdentityPhoto> selectIdentityPhotoByIdList(List<Integer> idList) {
    	return identityPhotoReadMapper.selectIdentityPhotoByIdList(idList);
    }
    
    public Photo selectPhotoById(int id) {
    	return identityPhotoReadMapper.selectPhotoById(id);
    }
    
    public int updatePhotoFlags(int photoId, int flags) {
    	return identityPhotoWriteMapper.updatePhotoFlags(photoId, flags);
    }
	public List<Photo> selectPhotoByIdList(List<Integer> idList) {
		return identityPhotoReadMapper.selectPhotoByIdList(idList);
	}
}
