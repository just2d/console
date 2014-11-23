package com.nuoshi.console.persistence.read.taofang.photo;

import org.apache.ibatis.annotations.Param;

import com.nuoshi.console.domain.photo.Photo;

public interface PhotoReadMapper {
	
	
	
	public Photo selectPhotoById(@Param("id")int id);
}
