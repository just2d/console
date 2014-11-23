package com.nuoshi.console.persistence.read.taofang.audit;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.nuoshi.console.domain.audit.AuditAgentList;

public interface AuditAgentListReadMapper {

	public List<AuditAgentList> getPhotoNameListByPage(@Param("cityId")int cityId, @Param("type")int type,@Param("agentId")int agentId);

}
