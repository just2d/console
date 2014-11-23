package com.nuoshi.console.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nuoshi.console.dao.UEstateMappingDAO;
import com.nuoshi.console.domain.estate.UEstateMapping;

@Service
public class UEstateMappingService extends BaseService {
	@Autowired
	private UEstateMappingDAO uEstateMappingDAO;

	public int add(UEstateMapping uEstateMapping) {
		return  uEstateMappingDAO.add(uEstateMapping);
	}

	public int deleteById(int id) {
		return uEstateMappingDAO.deleteById(id);
	}
	
	public UEstateMapping getById(int id) {
		return uEstateMappingDAO.getById(id);
	}
}
