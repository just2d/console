package com.nuoshi.console.dao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.nuoshi.console.dao.HouseExtraScoreHistoryDAO;
import com.nuoshi.console.persistence.write.admin.house.HouseExtraScoreHistoryWriteMapper;
import com.nuoshi.console.persistence.read.admin.house.HouseExtraScoreHistoryReadMapper;
import com.nuoshi.console.domain.house.HouseExtraScoreHistory;

@Repository
public class HouseExtraScoreHistoryDAO {

	@Resource
	private HouseExtraScoreHistoryWriteMapper houseExtraScoreHistoryWriteMapper;
	
	@Resource
	private HouseExtraScoreHistoryReadMapper houseExtraScoreHistoryReadMapper;
	
	public int add(HouseExtraScoreHistory houseExtraScoreHistory){
		return houseExtraScoreHistoryWriteMapper.add(houseExtraScoreHistory);
	} 
  
	public List<HouseExtraScoreHistory> getByHouseId(int houseId,int houseType){
		return houseExtraScoreHistoryReadMapper.getByHouseId(houseId,houseType);
	} 
	
}
