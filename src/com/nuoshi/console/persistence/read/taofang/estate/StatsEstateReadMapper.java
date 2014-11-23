package com.nuoshi.console.persistence.read.taofang.estate;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.nuoshi.console.domain.estate.StatsEstate;

public interface StatsEstateReadMapper {

	/**
	 * 获得小区二手房,租房均价
	 * @param estateId
	 * @return
	 */
	@Select("SELECT t.rentAvgPrice,t.resaleAvgPrice FROM stats_estate t where id = #{estateId} and statsMonth = #{statsMonth}")
	public StatsEstate getPriceByEstateId(@Param("estateId")int estateId ,@Param("statsMonth")String statsMonth);
	
//	@Update("update stats_estate t set t.rentAvgPrice = #{avgRentPrice},t.resaleAvgPrice = #{avgResalePrice} where t.id = #{estateId} and statsMonth = #{statsMonth}")
//	public void updateEstatePrice(@Param("avgRentPrice")int avgRentPrice,@Param("avgResalePrice")int avgResalePrice,@Param("estateId")int estateId,@Param("statsMonth")String startMonth);
//	
//	@Update("update stats_estate t set t.resaleAvgPrice = #{avgResalePrice} where t.id = #{estateId} and statsMonth = #{statsMonth}")
//	public void updateEstateRsaleAvgPrice(@Param("avgResalePrice")int avgResalePrice,@Param("estateId")int estateId,@Param("statsMonth")String startMonth);
}
