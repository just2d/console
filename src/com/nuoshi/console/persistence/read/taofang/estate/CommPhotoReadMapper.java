package com.nuoshi.console.persistence.read.taofang.estate;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.nuoshi.console.view.EstatePhotoCondition;
import com.nuoshi.console.view.EstatePhotoDetail;

public interface CommPhotoReadMapper {
	
	/**
	 * 获得小区图.
	 * @param condition
	 * @return
	 */
	public List<EstatePhotoDetail>  getCommPhotoList(EstatePhotoCondition condition);
	
	/**
	 * 获得小区图数.
	 * @param condition
	 * @return
	 */
	public Integer countBackupCommPhoto(EstatePhotoCondition condition);
	
	/**
	 * 获得指定小区的备选库中的小区图.
	 * @param condition
	 * @return
	 */
	public List<EstatePhotoDetail> getBackupCommPhoto(EstatePhotoCondition condition);
	
	/**
	 * 根据id获得户型图.
	 * @param id
	 * @return
	 */
	public EstatePhotoDetail getEstatePhotoById(Integer id);

	/**
	 * 根据排序值获得户型图.
	 * @param order
	 * @return
	 */
	public EstatePhotoDetail getCommPhotoByOrder(@Param("order")Integer order);
	
	/**
	 * 统计小区图数量
	 * @param estateId
	 * @return
	 */
	public int countCommNum(Integer estateId);
	
	/**
	 * 查询下区下是否已有删除的58图片
	 * @param estateId
	 * @return
	 */
//	public int countDelCommNum(Integer estateId);

	
}