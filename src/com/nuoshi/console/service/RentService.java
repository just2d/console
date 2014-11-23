package com.nuoshi.console.service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taofang.biz.domain.constants.HouseConstant;
import com.nuoshi.console.common.Helper;
import com.nuoshi.console.dao.HouseExtraScoreHistoryDAO;
import com.nuoshi.console.domain.estate.UnionLog;
import com.nuoshi.console.domain.house.HouseExtraScoreHistory;
import com.nuoshi.console.domain.rent.Rent;
import com.nuoshi.console.persistence.read.taofang.rent.RentReadMapper;
import com.nuoshi.console.persistence.read.unionhouse.urent.URentReadMapper;
import com.nuoshi.console.persistence.write.taofang.rent.RentWriteMapper;
import com.nuoshi.console.persistence.write.unionhouse.urent.URentWriteMapper;

/**
 * 
 * @author ningt
 *
 */
@Service
@SuppressWarnings("unchecked")
public class RentService {

	@Resource
	private URentReadMapper urentReadMapper;
	@Resource
	private RentReadMapper rentReadMapper;
	
	@Resource
	private RentWriteMapper rentWriteMapper;
	
	@Resource
	private URentWriteMapper urentWriteMapper;
	
	@Autowired
	private HouseExtraScoreHistoryDAO houseExtraScoreHistoryDAO;
	
	/**
	 * 统计小区租房数
	 * @param estateId
	 * @return
	 */
	public int countByEstateId(int estateId){
		int rent58total = count58RentByEstate(estateId);
		int rentTotal = countRentByEstate(estateId);
		return rent58total+rentTotal;
	}
	

	public int  count58RentByEstate(int estateId){
		int rent58total = urentReadMapper.countByEstateId(estateId);
		return rent58total;
	}
	
	public int  countRentByEstate(int estateId){
		int rentTotal = rentReadMapper.countByEstateId(estateId);
		return rentTotal;
	}
	

	/**
	 * 获得小区的所有二手房源
	 * @param estateId
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List<UnionLog> getRentByEstateId(Map paramMap){
		List<UnionLog> list = null;
		String fromTable = (String)paramMap.get("fromTable");
		if(Helper.getConfig("from_table_rent").equals(fromTable)){
			list = rentReadMapper.getRentByEstateId(paramMap);
		}else{
			list = urentReadMapper.getRentByEstateId(paramMap);
			
		}
		return list != null && list.size() >0 ?list:Collections.EMPTY_LIST;
	}
	
	/**
	 * 小区合并
	 * 
	 * @param paramMap
	 */
	@SuppressWarnings("rawtypes")
	public void unionRentEstate(Map paramMap) {
		rentWriteMapper.unionEstate(paramMap);
	} 
	
	@SuppressWarnings("rawtypes")
	public void union58RentEstate(Map paramMap) {
		urentWriteMapper.unionEstate(paramMap);
	}

	/**
	 * 根据经纪人id查询房源分数(baseScore)
	 * 包含的内容为:房源id，房源评分
	 * @param idList 经纪人idList
	 * @return
	 * @author wangjh
	 * Feb 9, 2012 5:00:34 PM
	 */
	public List<Rent> getRentForScore(List<Integer> idList,List<Integer> typeList) {
		return rentReadMapper.getRentForScore(idList,typeList);
	} 
	
	/**
	 * 某经纪人的房源下架
	 * @param agentId
	 * @return
	 * @author wangjh
	 * Feb 10, 2012 2:07:18 PM
	 */
	public boolean outOfStockByAgentId(Integer agentId){
		Boolean flag=true;
		try{
			rentWriteMapper.outOfStockByAgentId(agentId);
		}catch (Exception e) {
			flag=false;
			e.printStackTrace();
		}
		
		return flag;
	}



 public int addExtraScore(int houseId, BigDecimal score, String operator) {
			HouseExtraScoreHistory houseExtraScoreHistory = new HouseExtraScoreHistory();
			houseExtraScoreHistory.setHouseId(houseId);
			houseExtraScoreHistory.setHouseType(HouseConstant.RENT);
			houseExtraScoreHistory.setOperator(operator);
			houseExtraScoreHistory.setScore(score);
			houseExtraScoreHistoryDAO.add(houseExtraScoreHistory);
			
		return rentWriteMapper.addExtraScore(houseId, score);
	}



	public Rent selectRentById(int houseId) {
		return rentReadMapper.selectRentById(houseId);
	}
 
	
}
