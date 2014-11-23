package com.nuoshi.console.persistence.read.wenda;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.nuoshi.console.domain.wenda.Category;

/**
 * @description
 * @author shanyuqiang
 * @createDate Feb 29, 2012 2:02:00 PM
 * @email shanyq@taofang.com
 * @version 1.0
 */
public interface CategoryManageReadMapper {

	/**
	 * 根据分级对象查询列表
	 * @param category
	 * @return list
	 * @atuhor shanyq
	 */
	public List<Category> getCategoryList(Category category);

	/**
	 * 根据主键查询分级对象
	 * @param id
	 *            主键
	 * @return category
	 * @author shanyq
	 */
	public Category getCategoryById(Integer id);
	/**
	 * 根据主键查询分级对象
	 * @param id
	 *            主键
	 * @return category
	 * @author wangjh
	 */
	public List<Category> getCategoryByIds(@Param("idList")List<Integer> idList);
	
}
