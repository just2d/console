package com.nuoshi.console.service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mchange.v2.lang.ThreadUtils;
import com.taofang.biz.domain.constants.HouseConstant;
import com.nuoshi.console.common.Helper;
import com.nuoshi.console.dao.HouseExtraScoreHistoryDAO;
import com.nuoshi.console.domain.estate.UnionLog;
import com.nuoshi.console.domain.house.HouseExtraScoreHistory;
import com.nuoshi.console.domain.resale.Resale;
import com.nuoshi.console.persistence.read.taofang.resale.ResaleReadMapper;
import com.nuoshi.console.persistence.read.unionhouse.uresale.UResaleReadMapper;
import com.nuoshi.console.persistence.write.taofang.resale.ResaleWriteMapper;
import com.nuoshi.console.persistence.write.unionhouse.uresale.UResaleWriteMapper;

/**
 * @author ningt
 * 
 */
@Service
@SuppressWarnings("unchecked")
public class ResaleService {

	@Resource
	private UResaleReadMapper uresaleReadMapper;
	@Resource
	private ResaleReadMapper resaleReadMapper;

	@Resource
	private ResaleWriteMapper resaleWriteMapper;
	
	@Resource
	private UResaleWriteMapper uresaleWriteMapper;

	
	@Autowired
	private HouseExtraScoreHistoryDAO houseExtraScoreHistoryDAO;

	
	
	public Resale selectResaleById(int id) {
		if (resaleReadMapper != null) {
			try {
				Resale resale = resaleReadMapper.selectResaleById(id);
				return resale;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}


	/**
	 * 统计小区二手房数
	 * 
	 * @param estateId
	 * @return
	 */
	public int countByEstateId(int estateId) {
		int resale58total = this.count58ResaleByEstate(estateId);
		int resaleTotal = this.countResaleByEstate(estateId);
		return resale58total + resaleTotal;
	}

	/**
	 * 获得小区的所有二手房源
	 * @param estateId
	 * @return
	 */
	public List<UnionLog> getHouseByEstateId(Map paramMap){
		List<UnionLog> list = null;
		String fromTable = (String)paramMap.get("fromTable");
		if(Helper.getConfig("from_table_resale").equals(fromTable)){
			list = resaleReadMapper.getHouseByEstateId(paramMap);
		}else{
			list = uresaleReadMapper.getHouseByEstateId(paramMap);
			
		}
		return list != null && list.size() >0 ?list:Collections.EMPTY_LIST;
	}
	
	/**
	 * 小区合并
	 * 
	 * @param paramMap
	 */
	public void unionResaleEstate(Map paramMap) {
		resaleWriteMapper.unionEstate(paramMap);
	} 
	
	public void union58ResaleEstate(Map paramMap) {
		uresaleWriteMapper.unionEstate(paramMap);
	} 
	
	
	public int  count58ResaleByEstate(int estateId){
		int resale58total = uresaleReadMapper.countByEstateId(estateId);
		return resale58total;
	}
	
	public int  countResaleByEstate(int estateId){
		int resaleTotal = resaleReadMapper.countByEstateId(estateId);
		return resaleTotal;
	}

	public List<Resale> getResaleForScore(List<Integer> idList,
			List<Integer> typeList) {
		return resaleReadMapper.getResaleForScore(idList,typeList);
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
			resaleWriteMapper.outOfStockByAgentId(agentId);
		}catch (Exception e) {
			flag=false;
			e.printStackTrace();
		}
		
		return flag;
	}
	
	public List<Resale> getAuditVcrHouse(){
		return null;
	}

	public int addExtraScore(int houseId, BigDecimal score, String operator) {
		HouseExtraScoreHistory houseExtraScoreHistory = new HouseExtraScoreHistory();
		houseExtraScoreHistory.setHouseId(houseId);
		houseExtraScoreHistory.setHouseType(HouseConstant.RESALE);
		houseExtraScoreHistory.setOperator(operator);
		houseExtraScoreHistory.setScore(score);
		houseExtraScoreHistoryDAO.add(houseExtraScoreHistory);
		return resaleWriteMapper.addExtraScore(houseId,score);
		
	}
	
}
