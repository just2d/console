package com.nuoshi.console.persistence.read.taofang.estate;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.nuoshi.console.domain.estate.EstateData;
import com.nuoshi.console.domain.estate.EstateHouseCount;
import com.nuoshi.console.domain.topic.Estate;
import com.nuoshi.console.view.EstatePhoto;
import com.nuoshi.console.view.EstatePhotoCondition;

public interface EstateReadMapper {

	/**
	 * 
	 * @param startIdx
	 *            从哪条数据开始查询
	 * @param pageSize
	 *            每次最多查询出多少条记录
	 * @return
	 */
	public List<Estate> getBasicInfo(@Param("startIdx") int startIdx, @Param("pageSize") int pageSize);

	/**
	 * 根据查询条件分页显示小区信息
	 * 
	 * @param cityId
	 *            城市id
	 * @param distId
	 *            区域id
	 * @param blockId
	 *            板块id
	 * @param address
	 *            小区地址
	 * @param estateName
	 *            小区名称
	 * @param startIdx
	 * @param pageSize
	 * @return
	 */
	public List<EstateData> queryByCondition(@Param("cityId") Integer cityId, @Param("distId") Integer distId,
			@Param("blockId") Integer blockId, @Param("address") String address, @Param("estateName") String estateName,
			@Param("auth_Status") String authStatus, @Param("isClicked") String isClicked, @Param("startIdx") int startIdx,
			@Param("pageSize") int pageSize);

	/**
	 * 根据条件统计小区信息
	 * 
	 * @param cityId
	 * @param distId
	 * @param blockId
	 * @param address
	 * @param estateName
	 * @param delStatus
	 * @param authStatus
	 * @param startIdx
	 * @param pageSize
	 * @return
	 */
	public int countByCondition(@Param("cityId") Integer cityId, @Param("distId") Integer distId, @Param("blockId") Integer blockId,
			@Param("address") String address, @Param("estateName") String estateName, @Param("auth_Status") String authStatus,
			@Param("isClicked") String isClicked);

	/**
	 * 统计basic_info不为空的记录总数,为分页查询做准备.
	 * 
	 * @return
	 */
	@Select("select count(*) from estate ")
	public int countBasicInfoNum();

	/**
	 * 根据小区id获得小区信息
	 * 
	 * @param estateId
	 * @return
	 */
	public Estate getEstateInfoById(@Param("estateId") int estateId);

	/**
	 * 根据小区id获得小区信息
	 * 
	 * @param estateId
	 * @return
	 */
	public EstateData getEstateDataById(@Param("estateId") int estateId);

	/**
	 * 获得户型图列表.
	 * 
	 * @param condition
	 * @return
	 */
	public List<EstatePhoto> getPhotoList(EstatePhotoCondition condition);

	/**
	 * 统计户型图列表.
	 * 
	 * @param condition
	 * @return
	 */
	public int countPhotoList(EstatePhotoCondition condition);
	
	public EstateHouseCount getEstateHouseCount(@Param("estateId")int estateId);
	
	public EstateHouseCount getNewEstateHouseCount(@Param("pinyin")String pinyin);

	public Double getHouseAvgPriceByType(@Param("estateId")Integer estateId, @Param("houseType")Integer houseType,@Param("month")String month);

	public EstateData getEstateDataByIdAndAuthStatus(@Param("estateId")int estateId, @Param("authStatus")String authStatus);

	public Estate getEstateByName(@Param("name")String name);

}
