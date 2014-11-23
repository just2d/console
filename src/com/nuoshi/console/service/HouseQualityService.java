package com.nuoshi.console.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.nuoshi.console.domain.house.DayHouseQuality;
import com.nuoshi.console.persistence.read.stat.HouseQualityReadMapper;

@Service
public class HouseQualityService  extends BaseService{

	@Resource
	private HouseQualityReadMapper houseQualityReadMapper;
	
	public  List<DayHouseQuality>  getHouseQualityById(int date) {
		
		return houseQualityReadMapper.getByDate(date);
		 
	}

}
