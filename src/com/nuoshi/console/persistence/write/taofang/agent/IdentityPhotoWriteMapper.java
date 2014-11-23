package com.nuoshi.console.persistence.write.taofang.agent;

import org.apache.ibatis.annotations.Param;

public interface IdentityPhotoWriteMapper {

	public int updatePhotoFlags(@Param("photoId")int photoId, @Param("flags")int flags);
}
