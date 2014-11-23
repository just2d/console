package com.nuoshi.console.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.nuoshi.console.common.util.RechargeUtil;
import com.nuoshi.console.dao.EstateMappingDao;
import com.nuoshi.console.domain.estate.UEstateMapping;

@Service
public class EstateMappingService extends BaseService {

	@Resource
	private EstateMappingDao estateMappingDao;

	public List<UEstateMapping> getEstateMapping(String ownEstate, String unionEstate, Integer sourceId) {
		List<UEstateMapping> mappings = null;
		if (StringUtils.isNotBlank(ownEstate)) {
			if (RechargeUtil.isInteger(ownEstate)) {
				mappings = estateMappingDao.getMappingByOwnEstateId(Integer.valueOf(ownEstate),sourceId);
			} else {
					mappings = estateMappingDao.getMappingByOwnEstateName(ownEstate,sourceId);
			}
		} else if (StringUtils.isNotBlank(unionEstate)) {
			if (RechargeUtil.isInteger(unionEstate)) {
				mappings = estateMappingDao.getMappingByUnionEstateId(Integer.valueOf(unionEstate), sourceId);
			} else {
					mappings = estateMappingDao.getMappingByUnionEstateName(unionEstate, sourceId);
			}
		}

		return mappings;
	}

	public String save(UEstateMapping mapping) {
		String errorStr = null;
		
		boolean isMappingExist = estateMappingDao.isMappingExist(mapping);
		if(isMappingExist){
			errorStr = "本映射已存在，不可重复创建！";
			return errorStr;
		}
		boolean isUnionEstateExist = estateMappingDao.isUnionEstateExist(mapping.getuEstateId(),mapping.getSourceId());
		if(!isUnionEstateExist){
			errorStr = "本联盟小区不存在，不可创建映射！";
			return errorStr;
		}
		
		
		boolean isUnionEstateIdExist = estateMappingDao.isUnionEstateIdExist(mapping.getuEstateId(),mapping.getSourceId());
		if(isUnionEstateIdExist){
			errorStr = "本联盟小区 的映射关系已存在，不可重复创建！";
			return errorStr;
		}
		
		boolean isTfEstateIdExist = estateMappingDao.isTfEstateIdExist(mapping.getEstateId(),mapping.getSourceId());
		if(isTfEstateIdExist){
			errorStr = "本淘房小区的映射关系已存在,是否重复创建?";
			return errorStr;
		}
		
		int num = estateMappingDao.insert(mapping);
		if(num != 1){
			errorStr = "创建小区映射失败！";
			return errorStr;
		}
		return errorStr;
	}

	public UEstateMapping getById(Integer id) {
		return estateMappingDao.getById(id);
	}

	public String update(UEstateMapping mapping, UEstateMapping oldMapping) {
		String errorStr = null;
		
		if(oldMapping == null){
			errorStr ="本映射并未修改，无须更新！";
			return errorStr;
		}
		
		if(mapping.getEstateId().equals(oldMapping.getEstateId()) && mapping.getuEstateId().equals(oldMapping.getuEstateId()) && mapping.getSourceId().equals(oldMapping.getSourceId())){
			errorStr ="本映射并未修改，无须更新！";
			return errorStr;
		}
		
		boolean isUnionEstateExist = estateMappingDao.isUnionEstateExist(mapping.getuEstateId(),mapping.getSourceId());
		if(!isUnionEstateExist){
			errorStr = "本联盟小区不存在，不可创建映射！";
			return errorStr;
		}
		//若联盟小区未修改，则不需要验证重复，若已修改，则需要验证是否已存在了
		if(mapping.getuEstateId().equals(oldMapping.getuEstateId()) && mapping.getSourceId().equals(oldMapping.getSourceId())){
			
		}else{
			boolean isUnionEstateIdExist = estateMappingDao.isUnionEstateIdExist(mapping.getuEstateId(),mapping.getSourceId());
			if(isUnionEstateIdExist){
				errorStr = "本联盟小区 的映射关系已存在，不可重复创建！";
				return errorStr;
			}
		}
		
		boolean isTfEstateIdExist = estateMappingDao.isTfEstateIdExist(mapping.getEstateId(),mapping.getSourceId());
		if(isTfEstateIdExist){
			errorStr = "本淘房小区的映射关系已存在,是否重复创建?";
			return errorStr;
		}
		
		int num = estateMappingDao.update(mapping);
		if(num != 1){
			errorStr = "更新小区映射失败！";
			return errorStr;
		}
		return errorStr;
	}

	public String confirmsave(UEstateMapping mapping) {
		String errorStr = null;
		int num = estateMappingDao.insert(mapping);
		if(num != 1){
			errorStr = "创建小区映射失败！";
			return errorStr;
		}
		return errorStr;
	}

	public void delete(Integer id) {
		estateMappingDao.delete(id);
		
	}

	public String confirmupdate(UEstateMapping mapping) {
		String errorStr = null;
		int num = estateMappingDao.update(mapping);
		if(num != 1){
			errorStr = "更新小区映射失败！";
			return errorStr;
		}
		return errorStr;
	}
}
