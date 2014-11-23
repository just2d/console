package com.nuoshi.console.persistence.read.taofang.agent;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.nuoshi.console.domain.agent.Brand;

public interface BrandReadMapper {

	public List<Brand> searchBrandBySql() ;

	public Brand getBrandById(@Param("brandId")Integer brandId);

}
