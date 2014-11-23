package com.nuoshi.console.persistence.write.taofang.agent;

import org.apache.ibatis.annotations.Param;

import com.nuoshi.console.domain.agent.Brand;

public interface BrandWriteMapper {

	public void deleteBrand(@Param("id")String id) ;

	public int addBrand(Brand brand) ;

	public void updateBrand(Brand brand) ;

}
