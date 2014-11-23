package com.nuoshi.console.persistence.write.admin.publish;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.nuoshi.console.domain.user.Publisher;

public interface ReleaseAccountWriteMapper {
	
	public void add(@Param("user")Publisher user);
	
	public void delete(@Param("ids")List<Integer> ids);
}
