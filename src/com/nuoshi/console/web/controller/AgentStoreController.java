package com.nuoshi.console.web.controller;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nuoshi.console.domain.agent.AgentStore;
import com.nuoshi.console.domain.base.JsonResult;
import com.nuoshi.console.service.AgentStoreService;


@Controller
@RequestMapping(value = "/agentStore")
public class AgentStoreController extends BaseController{
	@Resource
	AgentStoreService agentStoreService;
	
//	//测试   可删
//	@RequestMapping(value = "/searchAgentStore")
//	public void searchAgentStore(HttpServletRequest request, HttpServletResponse response) {
//		JsonResult jsonResult = new JsonResult();
//		jsonResult.setNumFound(0);
//		try {
//			List<Integer> ids=new ArrayList<Integer>();
//			List<AgentStore> agentStores = agentStoreService.searchAgentStoreByIds(ids);
//			jsonResult.setData(gson.toJson(agentStores));
//			jsonResult.setMessage("true");
//			jsonResult.setResult(true);
//		} catch (Exception e) {
//			jsonResult.setMessage("false");
//			jsonResult.setResult(false);
//		}
//		String resultJson = gson.toJson(jsonResult);
//		sentResponseInfo(response, resultJson);
//	}
	


	@RequestMapping(value = "/addAgentStore")
	public void addAgentStore(HttpServletResponse response, HttpServletRequest request,AgentStore agentStore) {
		String agentStoreJson=request.getParameter("json");
		agentStore = gson.fromJson(agentStoreJson, AgentStore.class);
		JsonResult jsonResult = new JsonResult();
		jsonResult.setNumFound(0);
		try {
			int ret = agentStoreService.addAgentStore(agentStore);
			jsonResult.setData(new Integer(ret).toString());
			jsonResult.setMessage("true");
			jsonResult.setResult(true);
		} catch (Exception e) {
			jsonResult.setResult(false);
			jsonResult.setMessage("false");
		}
		String json = gson.toJson(jsonResult);
		sentResponseInfo(response,json);
	}
	

	
	
	
	
	@RequestMapping(value = "/deleteAgentStore/{id}")
	public void deleteAgentStore(HttpServletRequest request,HttpServletResponse response, @PathVariable("id") String id){
		JsonResult jsonResult = new JsonResult();
		jsonResult.setNumFound(0);
		try{
			agentStoreService.deleteAgentStore(id);
			jsonResult.setMessage("true");
			jsonResult.setResult(true);
		}catch(RuntimeException e){
			jsonResult.setMessage("false");
			jsonResult.setResult(false);
		}
		String json = gson.toJson(jsonResult);
		sentResponseInfo(response,json);
	}
	
	
	@RequestMapping(value = "/mergeStore")
	public void mergeStore(HttpServletRequest request,HttpServletResponse response){
		String oldId=request.getParameter("oldStoreId").toString().trim();
		Integer oldStoreId=null;
		if(StringUtils.isNotBlank(oldId)){
			oldStoreId=Integer.valueOf(oldId);
		}
		String newId=request.getParameter("newStoreId").toString().trim();
		Integer newStoreId=null;
		if(StringUtils.isNotBlank(newId)){
			newStoreId=Integer.valueOf(newId);
		}
		String storeName=request.getParameter("newStoreName").toString();
		JsonResult jsonResult = new JsonResult();
		jsonResult.setNumFound(0);
		try {
			agentStoreService.mergeAgentStore(oldStoreId,newStoreId,storeName);
			jsonResult.setMessage("true");
			jsonResult.setResult(true);
		} catch (Exception e) {
			jsonResult.setMessage("false");
			jsonResult.setResult(false);
		}
		String json = gson.toJson(jsonResult);
		sentResponseInfo(response,json);
	}
	
	@RequestMapping(value = "/updateAgentStore")
	public void updateAgentStore(HttpServletRequest request,HttpServletResponse response,AgentStore agentStore){
		String agentStoreJson=request.getParameter("json");
		agentStore = gson.fromJson(agentStoreJson, AgentStore.class);
		JsonResult jsonResult = new JsonResult();
		jsonResult.setNumFound(0);
		try {
			agentStoreService.updateAgentStore(agentStore);
			jsonResult.setMessage("true");
			jsonResult.setResult(true);
		} catch (Exception e) {
			e.printStackTrace();
			jsonResult.setMessage("false");
			jsonResult.setResult(false);
		}
		String json = gson.toJson(jsonResult);
		sentResponseInfo(response,json);
	}
	@RequestMapping(value = "/updateAgentStoreForCompany")
	public void updateAgentStoreForCompany(HttpServletRequest request,HttpServletResponse response,AgentStore agentStore){
		String oldId=request.getParameter("oldCompanyId").trim();
		Integer oldCompanyId = null;
		if(StringUtils.isNotBlank(oldId)){
			oldCompanyId=Integer.valueOf(oldId);
		}
		String newId=request.getParameter("newCompanyId").trim();
		Integer newCompanyId = null;
		if(StringUtils.isNotBlank(newId)){
			newCompanyId=Integer.valueOf(newId);
		}
		JsonResult jsonResult = new JsonResult();
		jsonResult.setNumFound(0);
		try {
			agentStoreService.updateAgentStoreForCompany(oldCompanyId, newCompanyId);
			jsonResult.setMessage("true");
			jsonResult.setResult(true);
		} catch (Exception e) {
			jsonResult.setMessage("false");
			jsonResult.setResult(false);
		}
		String json = gson.toJson(jsonResult);
		sentResponseInfo(response,json);
	}
	
}
