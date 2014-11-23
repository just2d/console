package com.nuoshi.console.persistence.write.taofang.photo;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface PhotoRentWriteMapper {

	int updateRentPhotoAuditStatus(@Param("ids")List<Integer> rejectPhotoIds, @Param("status")int photoStatusReject);

}
