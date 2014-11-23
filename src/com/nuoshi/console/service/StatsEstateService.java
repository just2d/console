package com.nuoshi.console.service;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import com.nuoshi.console.domain.estate.StatsEstate;
import com.nuoshi.console.persistence.read.taofang.estate.StatsEstateReadMapper;
import com.nuoshi.console.persistence.write.taofang.estate.StatsEstateWriteMapper;

@Service
public class StatsEstateService {

	@Resource
	private StatsEstateReadMapper statsEstateReadMapper;
	@Resource
	private StatsEstateWriteMapper statsEstateWriteMapper;
	/**
	 * 获得小区二手房,租房均价
	 * @param estateId
	 * @return
	 */
	public StatsEstate getPriceByEstateId(int estateId,String statsMoth){
		return statsEstateReadMapper.getPriceByEstateId(estateId,statsMoth);
	}
	
	/**
	 * 修改二手房,租房均价
	 * @param estateId
	 * @return
	 */
	public void updateEstatePrice(int avgRentPrice,int avgResalePrice,int estateId,String date){
		statsEstateWriteMapper.updateEstatePrice(avgRentPrice,avgResalePrice,estateId,date);
	}
	
	/**
	 * 修改二手房,租房均价
	 * @param estateId
	 * @return
	 */
	public void updateEstateResaleAvgPrice(int avgResalePrice,int estateId,String date){
		statsEstateWriteMapper.updateEstateRsaleAvgPrice(avgResalePrice,estateId,date);
	}
}
