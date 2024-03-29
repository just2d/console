package com.nuoshi.console.persistence.write.taofang.estate;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.nuoshi.console.view.EstatePhotoDetail;

public interface LayoutWriteMapper {

	/**
	 * 添加备选库图片到精选库
	 * 
	 * @param toUsingIds
	 */
	public void batchAddEstatePhoto(@Param("idList") List idList);
	
	/**
	 * 第一次将备选库中图片转移到精选库时需要将该小区下的58照片删除
	 * @param estateid
	 */
	public void del58LayoutPhotos(@Param("estateid") Integer estateid);

	/**
	 * 删除备选库中数据
	 * 
	 * @param toUsingIds
	 */
	public void batchDelBackupPhoto(@Param("idList") List idList);

	/**
	 * 删除精选库中数据
	 * 
	 * @param toUsingIds
	 */
	public void batchDelLayoutPhoto(@Param("idList") List idList);

	/**
	 * 添加备选库图片到精选库
	 * 
	 * @param toUsingIds
	 */
	public void batchAddBackupPhoto(@Param("idList") List idList, @Param("category") String category);

	/**
	 * 设置默认的户型图
	 * 
	 * @param paramMap
	 */
	public void updateOrderById(Map paramMap);
	
	/**
	 * 插入户型图信息
	 * @param paramMap
	 */
	public int addLayoutPhoto(EstatePhotoDetail photoDetail);

}
