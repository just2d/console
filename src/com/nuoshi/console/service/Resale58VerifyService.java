package com.nuoshi.console.service;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.nuoshi.console.common.Resources;
import com.nuoshi.console.common.util.ApplicationUtil;
import com.nuoshi.console.dao.HistoryResale58Dao;
import com.nuoshi.console.dao.Resale58Dao;

@Service
public class Resale58VerifyService extends BaseService {
	@Resource
	private Resale58Dao resale58Dao;
	@Resource
	private HistoryResale58Dao historyResale58Dao;
	
	public int deleteResale(int resaleid){
		HashMap<String,Object> resale = resale58Dao.getResaleById(resaleid);
		try{
			historyResale58Dao.intoHistory(resale);
		}catch (Exception e) {
			// TODO: handle exception
		}
		try{
			//刷新缓存数据
			ApplicationUtil.getHttpResponse(Resources.getString("taofang.service.url") + "ershoufang/refresh/" + resaleid+"-1");
		}catch (Exception e) {
			// TODO: handle exception
		}
		
		return resale58Dao.deleteResale(resaleid);
	}
	
	public List<HashMap<String,Object>> getAll58ResaleByPage(HashMap params){
		List<HashMap<String,Object>> resultList = resale58Dao.getAll58ResaleByPage(params);
		return resultList;
	}
}
