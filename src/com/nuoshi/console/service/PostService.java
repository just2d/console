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
import com.nuoshi.console.domain.bbs.TfBbsPosts;

@Service
public class PostService extends BaseService{
	private static Logger logger = Logger.getLogger(PostService.class);
	
	 String urlStr=Resources.getString("sys.url.bbs");
	 
	public int update(int id,int postStatus,String releaser,int verifierId){

		String result = "";
		HashMap<String,String> param = new HashMap<String,String>();
		param.put("id", id+"");
		param.put("status", postStatus+"");
		param.put("verifierId", verifierId+"");
		param.put("verifier", releaser);
		try {
			result=NetClient.getHttpResponse(urlStr + "/posts/status",param);
		} catch (Exception e) {
			logger.error("审核过程调用出错"+e.getMessage());
			e.printStackTrace();
		}

		JsonResult jsonResult = gson.fromJson(result, JsonResult.class);
		return new Integer(jsonResult.getData());
	}
	
	public int delete(int forumId,int threadId,int id){
		String result =  callService(urlStr + "/posts/delete", forumId,threadId,id);
		JsonResult jsonResult = gson.fromJson(result, JsonResult.class);
		return new Integer(jsonResult.getData());
	}
	
	public List<TfBbsPosts> list(int first ,int forumId,int threadId,int postStatus,Pagination pagination){
		
		String result = "";
		HashMap<String,String> param = new HashMap<String,String>();
		
		param.put("dataType", "json");
		param.put("first", first+"");
		param.put("status", postStatus+"");
		param.put("page.pageSize", pagination.getPageSize()+"");
		param.put("page.pageNo", pagination.getPageNo()+"");
		try {
			result=NetClient.getHttpResponse(urlStr + "/bbs/"+forumId+"/"+threadId,param);
			
		} catch (Exception e) {
			logger.error("审核过程调用出错"+e.getMessage());
			e.printStackTrace();
		}		
		
		JsonResult jsonResult = gson.fromJson(result, JsonResult.class);
		pagination.setTotalCount((int)jsonResult.getNumFound());
		if(jsonResult.isResult()) {
			Type type = new TypeToken<List<TfBbsPosts>>(){}.getType();
			return gson.fromJson(jsonResult.getData(), type);
		} 
		return null;
		
	}
	public List<TfBbsPosts> selectByThreadId(int forumId,int threadId,int postStatus,Pagination pagination){
		
		String result = "";
		HashMap<String,String> param = new HashMap<String,String>();
		param.put("dataType", "json");
		param.put("threadId", threadId+"");
		param.put("status", postStatus+"");
		param.put("page.pageSize", pagination.getPageSize()+"");
		param.put("page.pageNo", pagination.getPageNo()+"");
		try {
			result=NetClient.getHttpResponse(urlStr + "/bbs/"+forumId+"/"+threadId,param);
		} catch (Exception e) {
			logger.error("根据主题选择调用出错"+e.getMessage());
			e.printStackTrace();
		}		
		JsonResult jsonResult = gson.fromJson(result, JsonResult.class);
		pagination.setTotalCount((int)jsonResult.getNumFound());
		if(jsonResult.isResult()) {
			Type type = new TypeToken<List<TfBbsPosts>>(){}.getType();
			return gson.fromJson(jsonResult.getData(), type);
		} 
		return null;
		
	}
	
	public TfBbsPosts selectBysubject(int forumId,int threadId){
		int first=1;
		String result = "";
		HashMap<String,String> param = new HashMap<String,String>();
		param.put("dataType", "json");
		param.put("threadId", threadId+"");
		param.put("first", first+"");
		try {
			result=NetClient.getHttpResponse(urlStr + "/bbs/"+forumId+"/"+threadId,param);
		} catch (Exception e) {
			logger.error("根据主题选择调用出错"+e.getMessage());
			e.printStackTrace();
		}		
		JsonResult jsonResult = gson.fromJson(result, JsonResult.class);
		if(jsonResult.isResult()) {
			Type type = new TypeToken<List<TfBbsPosts>>(){}.getType();
			List<TfBbsPosts> list= gson.fromJson(jsonResult.getData(), type);
			if(list.size()>0){
				TfBbsPosts posts=list.get(0);
				return posts;
			}
		} 
		return null;
		
	}
	
}
