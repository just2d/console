package com.nuoshi.console.persistence.write.wenda;

import org.apache.ibatis.annotations.Param;

import com.nuoshi.console.domain.wenda.Category;

/**
 * @description 
 * @author shanyuqiang
 * @createDate Feb 29, 2012 2:02:55 PM
 * @email shanyq@taofang.com
 * @version 1.0
 */
public interface CategoryManageWriteMapper {
	public void saveCategory(Category category);
	public void updateCateogry(Category category);
	public  void updateLocationById(@Param("id")String id, @Param("location")String location);
	public void updateStatusByPid(@Param("pId")Integer pId,@Param("status") int status);
}
