package com.nuoshi.console.service;

import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.nuoshi.console.dao.BrandDao;
import com.nuoshi.console.domain.agent.Brand;

@Service
public class BrandService {
	@Resource
	BrandDao brandDao;
	@Resource
	BrokerService brokerServcie;
	
	public int addBrand(Brand brand) {
		if(brand.getCts()==null){
			brand.setCts(new Timestamp(0));
		}
		return brandDao.addBrand(brand);
	}

	public void deleteBrand(String id) {
		brandDao.deleteBrand(id);
		brokerServcie.deleteBrokerByBrandId(id);
	}

	public void updateBrand(Brand brand) {
		if(brand.getCts()==null){
			brand.setCts(new Timestamp(0));
		}
		brandDao.updateBrand(brand);
	}

	public List<Brand> searchBrand() {
		return brandDao.searchBrandBySql();
	}

	public Brand getBrandById(Integer brandId) {
		return brandDao.getBrandById(brandId);
	}

}
