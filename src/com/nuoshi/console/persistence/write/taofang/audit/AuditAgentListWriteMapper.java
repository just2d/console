package com.nuoshi.console.persistence.write.taofang.audit;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface AuditAgentListWriteMapper {


	public int updatePhotoNameList(@Param("ids")List<String> ids, @Param("type")int type);

}
