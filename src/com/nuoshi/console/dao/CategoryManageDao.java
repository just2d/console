package com.nuoshi.console.dao;

import java.util.List;

import javax.annotation.Resource;
import org.springframework.stereotype.Repository;
import com.nuoshi.console.domain.wenda.Category;
import com.nuoshi.console.persistence.read.wenda.CategoryManageReadMapper;
import com.nuoshi.console.persistence.write.wenda.CategoryManageWriteMapper;
import com.nuoshi.console.persistence.write.wenda.QuestionWriteMapper;

/**
 * @description
 * @author shanyuqiang
 * @createDate Feb 29, 2012 2:01:18 PM
 * @email shanyq@taofang.com
 * @version 1.0
 */
@Repository
public class CategoryManageDao {
	@Resource
	private CategoryManageReadMapper categoryManageReadMapper;
	@Resource
	private CategoryManageWriteMapper categoryManageWriteMapper;
	@Resource
	private QuestionWriteMapper questionWriteMapper;

	/**
	 * 根据category对象查询list
	 * @return list
	 * @author shanyq
	 */
	public List<Category> getCategoryList(Category category) {
		return categoryManageReadMapper.getCategoryList(category);
	}

	/**
	 * 根据主键获取category对象
	 * @param id 主键
	 * @return category
	 * @author shanyq
	 */
	public Category getCategoryById(Integer id) {
		return categoryManageReadMapper.getCategoryById(id);
	}
	/**
	 * 根据主键获取category对象
	 * @param id 主键
	 * @return category
	 * @author wangjh
	 */
	public List<Category> getCategoryByIds(List<Integer> ids) {
		return categoryManageReadMapper.getCategoryByIds(ids);
	}

	/**
	 * 保存category
	 * @param category
	 * @return void
	 * @author shanyq
	 */
	public void saveCategory(Category category) {
		categoryManageWriteMapper.saveCategory(category);
	}

	/**
	 * 根据对象更新category
	 * @param category
	 * @return void
	 * @atuhor shanyq
	 */
	public void updateCateogry(Category category) {
		categoryManageWriteMapper.updateCateogry(category);
	}
	
	/**
	 *  更改问题分类
	 *  @param category
	 *  @return void
	 *  @author shanyq
	 */
	public void updateQuestions(Category category){
		questionWriteMapper.updateQuestionByCategory(category);
	}
	
	/**
	 *  更新定位
	 *  @param id location
	 *  @return void
	 *  @author shanyq
	 */
	public void updateLocationById(String id, String location){
		categoryManageWriteMapper.updateLocationById(id, location);
	}
	
	public void updateStatusByPid(Integer pId,int status){
		categoryManageWriteMapper.updateStatusByPid(pId,status);
	}
}
