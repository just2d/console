package com.nuoshi.console.persistence.read.taofang.estate;

import org.apache.ibatis.annotations.Param;

import com.nuoshi.console.domain.estate.EstateMd5;

public interface EstateMd5ReadMapper {

	/**
	 * 判断Md5值是否存在
	 * @param md5
	 * @param category
	 * @param estateId
	 * @return
	 */
	public EstateMd5 queryMd5Exist(@Param("md5") String md5, @Param("category") String category, @Param("estateId") Integer estateId);
}
