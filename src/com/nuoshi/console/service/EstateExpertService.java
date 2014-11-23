package com.nuoshi.console.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.nuoshi.console.common.util.Utilities;
import com.nuoshi.console.domain.estate.EstateExpert;
import com.nuoshi.console.persistence.read.taofang.estate.EstateExpertReadMapper;
import com.nuoshi.console.persistence.write.taofang.estate.EstateExpertWriteMapper;

@Service
public class EstateExpertService {

	@Resource
	private EstateExpertReadMapper  estateExpertReadMapper;
	

	@Resource
	private EstateExpertWriteMapper  estateExpertWriteMapper;


	public List<EstateExpert> getEstateExpert(Integer cityId, Integer agentId, String agentName, String agentPhone,
			String estateName) {
		if(estateName !=null && estateName.trim().length()>0){
			estateName = "%"+estateName.trim()+"%";
		}
		List<EstateExpert> estateExpertList = estateExpertReadMapper.getEstateExpertByPage(cityId,agentId,agentName,agentPhone,estateName);
		
		for(EstateExpert estateExpert:estateExpertList){
			estateExpert.setCityName(LocaleService.getName(estateExpert.getCityId()));
			estateExpert.setDistName(LocaleService.getName(estateExpert.getDistId()));
			estateExpert.setStartTimeStr(Utilities.formatShortDate(estateExpert.getStartTime()));
			estateExpert.setEndTimeStr(Utilities.formatShortDate(estateExpert.getEndTime()));
			estateExpert.setExpertCount(estateExpertReadMapper.getExpertCountByEstateId(estateExpert.getEstateId()));
		}
		return estateExpertList;
	}
	
	public List<EstateExpert> getEstateExpertDownload(Integer cityId, Integer agentId, String agentName,
			String agentPhone, String estateName) {
		if(estateName !=null && estateName.trim().length()>0){
			estateName = "%"+estateName.trim()+"%";
		}
		List<EstateExpert> estateExpertList = estateExpertReadMapper.getEstateExpertDownload(cityId,agentId,agentName,agentPhone,estateName);
		
		for(EstateExpert estateExpert:estateExpertList){
			estateExpert.setCityName(LocaleService.getName(estateExpert.getCityId()));
			estateExpert.setDistName(LocaleService.getName(estateExpert.getDistId()));
			estateExpert.setStartTimeStr(Utilities.formatShortDate(estateExpert.getStartTime()));
			estateExpert.setEndTimeStr(Utilities.formatShortDate(estateExpert.getEndTime()));
			estateExpert.setExpertCount(estateExpertReadMapper.getExpertCountByEstateId(estateExpert.getEstateId()));
		}
		return estateExpertList;
	}
	


	public int deleteEstateExpert(Integer id) {
		return estateExpertWriteMapper.deleteById(id);
	}


	public void updateEstateExpert(EstateExpert estateExpert) {
		estateExpertWriteMapper.update(estateExpert);
	}


	public void save(EstateExpert estateExpert) {
		//查找相同小区  相同级别的小区专家
		EstateExpert estateExpertDB = 	estateExpertReadMapper.getExpertByEstateId(estateExpert);
		
		//不存在 直接插入
		if(estateExpertDB == null){
			estateExpertWriteMapper.add(estateExpert);
		}else{
			estateExpert.setId(estateExpertDB.getId());
			estateExpertWriteMapper.update(estateExpert);
		}
	}

}
