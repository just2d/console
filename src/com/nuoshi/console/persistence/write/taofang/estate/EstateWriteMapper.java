package com.nuoshi.console.persistence.write.taofang.estate;

import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import com.nuoshi.console.domain.topic.Estate;

public interface EstateWriteMapper {

	/**
	 * 根据id删除小区(物理删除)
	 * 
	 * @param estateId
	 */
	@Delete("delete from estate where id=#{estateId}")
	public void delelteByEstateId(@Param("estateId") int estateId);

	/**
	 * 根据id编辑小区
	 * 
	 * @param estateId
	 */
	public void updateById(@Param("estateName") String estateName, @Param("estateAddr") String estateAddr, @Param("estateId") int estateId,
			@Param("basicInfo") String basicInfo, @Param("cityId") Integer cityId, @Param("distId") Integer distId,
			@Param("blockId") Integer blockId, @Param("lon") float lon, @Param("lat") float lat, @Param("wyType") String wyType,
			@Param("greenRate") String greenRate, @Param("areaRate") String areaRate, @Param("authStatus") String authStatus,
			@Param("description") String desc, @Param("alias") String alias, @Param("namepy") String namepy, @Param("pinYin") String pinYin);
	
	public void updateById(Estate estate);

	@Update("update estate set del_Status=#{delStatus} where id=#{estateId}")
	public void updateDelStatus(@Param("estateId") int estateId, @Param("delStatus") String delStatus);

	// update estate set auth_Status=#{authStatus} where id=#{estateId}
	public int updateAuthStatus(@Param("estateId") int estateId, @Param("authStatus") String authStatus,
			@Param("origStatus") String origStatus);
	
	public int updateAuthWStatus(@Param("estateId") int estateId, @Param("authStatus") String authStatus,
			@Param("origStatus") String origStatus,@Param("createUserId")int createUserId);
	
	public void updateAuthStatusAndNum(Map paramMap);

	// 插入的时候不插入户型图,小区图数量.也不插入默认照片.
	public void insertEstate(@Param("estateAddr") String estateAddr, @Param("estateName") String estateName,
			@Param("cityId") Integer cityId, @Param("distId") Integer distId, @Param("blockId") Integer blockId,
			@Param("namepy") String namepy, @Param("alias") String alias, @Param("fromhouse") Integer fromhouse, @Param("lon") float lon,
			@Param("lat") float lat, @Param("basicInfo") String basicInfo, @Param("wyType") String wyType,
			@Param("greenRate") String greenRate, @Param("areaRate") String areaRate, @Param("authStatus") String authStatus,
			@Param("remark") String remark, @Param("rentNum") Integer rentNum, @Param("resaleNum") Integer resaleNum,@Param("pinYin")String pinYin);
	
	public void insertEstate(Estate estate);

	/**
	 * 更新小区备选库图片和户型图数量.
	 */
	public void updateBacupAndLayoutNum(@Param("estateId") int estateId,@Param("layoutcnt") int layoutcnt, @Param("layoutphotocnt") int layoutphotocnt,
			@Param("photocnt")int photocnt,@Param("commphotocnt")int commphotocnt);
	
	/**
	 * 更新小区二手房和租房数量.
	 * @param paramMap
	 */
	public void updateResaleAndRentCount(Map paramMap);
	
	/**
	 * 设置小区列表页首图
	 * @param estateId
	 * @param photoId
	 */
	public void setDefaultPhoto(@Param("estateId")Integer estateId,@Param("photoId")Integer photoId);
	
	@Update("update estate set rt_url = #{rtUrl} where id = #{estateId}")
	public void updateRturl(@Param("rtUrl")String rtUrl,@Param("estateId")int estateid);
}
