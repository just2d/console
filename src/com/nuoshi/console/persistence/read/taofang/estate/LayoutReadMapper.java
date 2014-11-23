package com.nuoshi.console.persistence.read.taofang.estate;

import java.util.List;
import java.util.Map;

import com.nuoshi.console.view.EstatePhoto;
import com.nuoshi.console.view.EstatePhotoDetail;
import com.nuoshi.console.view.EstatePhotoCondition;

/**
 * 户型图
 * @author ningt
 *
 */
public interface LayoutReadMapper {
	
	/**
	 * 统计备选库图片数量
	 * @param map
	 * @return
	 */
	public List<Integer []> countbackPhoto(Map map);
	
	/**
	 * 获得小区的户型图.
	 * @param condition
	 * @return
	 */
	public List<EstatePhotoDetail>  getLayoutPhotoList(EstatePhotoCondition condition);
	
	/**
	 * 获得指定小区的备选库中的小区户型图.
	 * @param condition
	 * @return
	 */
	public List<EstatePhotoDetail> getBackupLayoutPhoto(EstatePhotoCondition condition);
	
	/**
	 * 获得指定小区的备选库中的小区户型图数量.
	 * @param condition
	 * @return
	 */
	public Integer countBackupLayoutPhoto(EstatePhotoCondition condition);
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
	public EstatePhotoDetail getEstatePhotoByOrder(Integer order);
	
	/**
	 * 统计户型图数量
	 * @param estateId
	 * @return
	 */
	public int countLayoutNum(Integer estateId);
	

	
}
