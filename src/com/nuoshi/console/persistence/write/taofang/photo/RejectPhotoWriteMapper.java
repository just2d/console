package com.nuoshi.console.persistence.write.taofang.photo;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.nuoshi.console.domain.photo.RejectPhoto;


public interface RejectPhotoWriteMapper {
	
	public int save(RejectPhoto rejectPhoto);
	
	public void updateDirection(@Param("photoIdList")List<Integer> photoIdList,@Param("status")Integer status);
}