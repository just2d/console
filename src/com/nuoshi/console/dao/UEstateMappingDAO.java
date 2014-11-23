package com.nuoshi.console.dao;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.nuoshi.console.dao.UEstateMappingDAO;
import com.nuoshi.console.persistence.write.unionhouse.estate.UEstateMappingWriteMapper;
import com.nuoshi.console.persistence.read.unionhouse.estate.UEstateMappingReadMapper;
import com.nuoshi.console.domain.estate.UEstateMapping;

@Repository
public class UEstateMappingDAO {

	@Resource
	private UEstateMappingWriteMapper uEstateMappingWriteMapper;
	
	@Resource
	private UEstateMappingReadMapper uEstateMappingReadMapper;
	
	public int add(UEstateMapping uEstateMapping){
		return uEstateMappingWriteMapper.add(uEstateMapping);
	} 
	public int deleteById(int id){
		return uEstateMappingWriteMapper.deleteById(id);
	} 
	public UEstateMapping getById(int id){
		return uEstateMappingReadMapper.getById(id);
	} 
	
}
