package com.nuoshi.console.persistence.read.unionhouse.estate;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.nuoshi.console.domain.estate.UEstateMapping;

public interface UEstateMappingReadMapper {

	//根据ID查找
	public UEstateMapping getById(int id);

	public List<UEstateMapping> getMappingByOwnEstateId(@Param("estateId")Integer estateId,@Param("sourceId") Integer sourceId);
	
	public List<UEstateMapping> getMappingByUnionEstateId(@Param("estateId")Integer estateId,@Param("sourceId") Integer sourceId);
	
	public List<UEstateMapping> getMappingByUnionEstateName(@Param("estateName")String unionEstate, @Param("sourceId")Integer sourceId);

	public List<UEstateMapping> getMappingByOwnEstateName(@Param("estateName")String ownEstate, @Param("sourceId") Integer sourceId);

	public int getEstateMappingNum(@Param("estateId")Integer estateId, @Param("sourceId")Integer sourceId);

	public Integer getUnionEstateMappingNum(@Param("estateId")Integer uEstateId, @Param("sourceId")Integer sourceId);

	public Integer getUnionEstateNum(@Param("estateId")Integer uEstateId, @Param("sourceId")Integer sourceId);

	public Integer getMappingNum(UEstateMapping mapping);

	
  
}