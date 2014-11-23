package com.nuoshi.console.service;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.google.gson.reflect.TypeToken;
import com.nuoshi.console.common.NetClient;
import com.nuoshi.console.common.Resources;
import com.nuoshi.console.common.page.impl.Pagination;
import com.nuoshi.console.domain.base.JsonResult;
import com.nuoshi.console.domain.bbs.TfBbsThreads;

@Service
public class ThreadService extends BaseService{

	private Logger logger=Logger.getLogger(ThreadService.class);
	
	 String urlStr=Resources.getString("sys.url.bbs");
	 
	public int update(int id,int postStatus,String releaser,int verifierId){
		String result = "";
		HashMap<String,String> param = new HashMap<String,String>();
		param.put("id", id+"");
		param.put("status", postStatus+"");
		param.put("verifierId", verifierId+"");
		param.put("verifier", releaser);
		try {
			result=NetClient.getHttpResponse(urlStr + "/thread/status",param);
		} catch (Exception e) {
			logger.error("主题审核过程调用出错"+e.getMessage());
			e.printStackTrace();
		}
		JsonResult jsonResult = gson.fromJson(result, JsonResult.class);
		return new Integer(jsonResult.getData());
	}
	
	public int delete(int forumId,int id){
		String result="";
		try {
			result = callService(urlStr + "/thread/delete",forumId,id);
		} catch (Exception e) {
			logger.error("删除主题时出错");
			e.printStackTrace();
		}
		JsonResult jsonResult = gson.fromJson(result, JsonResult.class);
		return new Integer(jsonResult.getData());
	}
	
	public List<TfBbsThreads> list(int forumId,int postStatus,Pagination pagination){
		String result = "";
		HashMap<String,String> param = new HashMap<String,String>();
		param.put("dataType", "json");
		param.put("status", postStatus+"");
		param.put("page.pageSize", pagination.getPageSize()+"");
		param.put("page.pageNo", pagination.getPageNo()+"");
		try {
			result=NetClient.getHttpResponse(urlStr + "/bbs/"+forumId,param);
		} catch (Exception e) {
			logger.error("主题列表过程调用出错"+e.getMessage());
			e.printStackTrace();
		}
		
		JsonResult jsonResult = gson.fromJson(result, JsonResult.class);
		pagination.setTotalCount((int)jsonResult.getNumFound());
		if(jsonResult.isResult()) {
			Type type = new TypeToken<List<TfBbsThreads>>(){}.getType();
			return gson.fromJson(jsonResult.getData(), type);
		} 
		return null;
		
	}
}
