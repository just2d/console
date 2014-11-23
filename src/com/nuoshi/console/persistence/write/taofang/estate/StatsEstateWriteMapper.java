package com.nuoshi.console.persistence.write.taofang.estate;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.nuoshi.console.domain.estate.StatsEstate;

public interface StatsEstateWriteMapper {

	
	@Update("update stats_estate t set t.rentAvgPrice = #{avgRentPrice},t.resaleAvgPrice = #{avgResalePrice} where t.id = #{estateId} and statsMonth = #{statsMonth}")
	public void updateEstatePrice(@Param("avgRentPrice")float avgRentPrice,@Param("avgResalePrice")float avgResalePrice,@Param("estateId")int estateId,@Param("statsMonth")String startMonth);
	
	@Update("update stats_estate t set t.resaleAvgPrice = #{avgResalePrice} where t.id = #{estateId} and statsMonth = #{statsMonth}")
	public void updateEstateRsaleAvgPrice(@Param("avgResalePrice")float avgResalePrice,@Param("estateId")float estateId,@Param("statsMonth")String startMonth);
}
