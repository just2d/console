package com.nuoshi.console.persistence.write.taofang.photo;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.nuoshi.console.domain.photo.Photo;

public interface PhotoWriteMapper {

	
	public int save(Photo photo);
	public int updatePhotoAuditStatus(@Param("ids")List<Integer> ids,@Param("status")int auditStatus);
}
