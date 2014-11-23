package com.nuoshi.console.dao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.nuoshi.console.domain.agent.Brand;
import com.nuoshi.console.persistence.read.taofang.agent.BrandReadMapper;
import com.nuoshi.console.persistence.write.taofang.agent.BrandWriteMapper;



@Repository
public class BrandDao {

		
		@Resource
		private BrandWriteMapper brandWriteMapper;
		
		@Resource
		private BrandReadMapper brandReadMapper;
		
		
		public List<Brand> searchBrandBySql() {
			return brandReadMapper.searchBrandBySql();
		}
		
		
		public void updateBrand(Brand brand) {
			brandWriteMapper.updateBrand(brand);
		}
		
		public int addBrand(Brand brand) {
			brandWriteMapper.addBrand(brand);
			return brand.getId();
		}
		
		public void deleteBrand(String id){
			brandWriteMapper.deleteBrand(id);
		}


		public Brand getBrandById(Integer brandId) {
			return brandReadMapper.getBrandById(brandId);
		}

}
