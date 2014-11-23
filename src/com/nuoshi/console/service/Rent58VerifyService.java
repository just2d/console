package com.nuoshi.console.service;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.nuoshi.console.common.Resources;
import com.nuoshi.console.common.util.ApplicationUtil;
import com.nuoshi.console.dao.HistoryRent58Dao;
import com.nuoshi.console.dao.Rent58Dao;

@Service
public class Rent58VerifyService extends BaseService {
	@Resource
	private Rent58Dao rent58Dao;
	@Resource
	private HistoryRent58Dao historyRent58Dao;


	public int deleteRent(int rentid){
		HashMap<String,Object> rent = rent58Dao.getRentById(rentid);
		try{
			historyRent58Dao.intoHistory(rent);
		}catch (Exception e) {
			// TODO: handle exception
		}
		try{
			//刷新缓存数据
			ApplicationUtil.getHttpResponse(Resources.getString("taofang.service.url") + "zufang/refresh/" + rentid+"-1");
		}catch (Exception e) {
			// TODO: handle exception
		}
		
		return rent58Dao.deleteRent(rentid);
	}
	
	public List<HashMap<String,Object>> getAll58RentByPage(HashMap params){
		List<HashMap<String,Object>> resultList = rent58Dao.getAll58RentByPage(params);
		return resultList;
	}
	
	
}
