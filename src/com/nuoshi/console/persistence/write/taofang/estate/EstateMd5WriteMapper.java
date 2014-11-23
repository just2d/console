package com.nuoshi.console.persistence.write.taofang.estate;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface EstateMd5WriteMapper {

	/**
	 * 存储小区图md5信息
	 * @param category
	 * @param estateId
	 * @param md5
	 */
	public void saveEstateMd5Info(@Param("category") String category, @Param("estateId") Integer estateId, @Param("md5") String md5,@Param("photoid")Integer photoId);
	
	/**
	 * 批量删除图片md5记录
	 */
	public void batchDeleteMd5Info(@Param("idList") List idList,@Param("category")String category);

}
