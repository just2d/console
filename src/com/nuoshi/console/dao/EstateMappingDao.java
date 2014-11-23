package com.nuoshi.console.dao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.nuoshi.console.domain.estate.UEstateMapping;
import com.nuoshi.console.persistence.read.unionhouse.estate.UEstateMappingReadMapper;
import com.nuoshi.console.persistence.write.unionhouse.estate.UEstateMappingWriteMapper;

@Repository
public class EstateMappingDao {
	
	@Resource
	private UEstateMappingReadMapper uEstateMappingReadMapper;
	@Resource
	private UEstateMappingWriteMapper uEstateMappingWriteMapper;

	public List<UEstateMapping> getMappingByOwnEstateId(Integer estateId,Integer sourceId) {
		return uEstateMappingReadMapper.getMappingByOwnEstateId(estateId, sourceId);
	}

	public List<UEstateMapping> getMappingByUnionEstateId(Integer estateId, Integer sourceId) {
		return uEstateMappingReadMapper.getMappingByUnionEstateId(estateId,sourceId);
	}
	
	public List<UEstateMapping> getMappingByOwnEstateName(String ownEstate, Integer sourceId) {
		return uEstateMappingReadMapper.getMappingByOwnEstateName(ownEstate, sourceId);
	}

	public List<UEstateMapping> getMappingByUnionEstateName(String unionEstate, Integer sourceId) {
		return uEstateMappingReadMapper.getMappingByUnionEstateName(unionEstate,sourceId);
	}

	public boolean isTfEstateIdExist(Integer estateId, Integer sourceId) {
		Integer mappingNum = uEstateMappingReadMapper.getEstateMappingNum(estateId, sourceId);
		if(mappingNum != null && mappingNum > 0 ) return true;
		return false;
	}

	public boolean isUnionEstateIdExist(Integer uEstateId, Integer sourceId) {
		Integer mappingNum = uEstateMappingReadMapper.getUnionEstateMappingNum(uEstateId,sourceId);
		if(mappingNum != null && mappingNum > 0 ) return true;
		return false;
	}

	public int insert(UEstateMapping mapping) {
		return  uEstateMappingWriteMapper.add(mapping);
	}

	public boolean isUnionEstateExist(Integer uEstateId, Integer sourceId) {
		Integer num = uEstateMappingReadMapper.getUnionEstateNum(uEstateId,sourceId);
		if(num != null && num > 0 ) return true;
		return false;
	}

	public UEstateMapping getById(Integer id) {
		return uEstateMappingReadMapper.getById(id);
	}

	public int update(UEstateMapping mapping) {
		return uEstateMappingWriteMapper.update(mapping);
	}

	public boolean isMappingExist(UEstateMapping mapping) {
		Integer num = uEstateMappingReadMapper.getMappingNum(mapping);
		if(num != null && num > 0 ) return true;
		return false;
	}

	public void delete(Integer id) {
		uEstateMappingWriteMapper.deleteById(id);
		
	}

}
