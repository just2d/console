package com.nuoshi.console.persistence.read.admin.audit;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.nuoshi.console.domain.audit.AuditPhotoSetting;

public interface AuditPhotoSettingReadMapper {
	public List<AuditPhotoSetting> getPhotoSettingByPage(@Param("cityId")int cityId);

}
