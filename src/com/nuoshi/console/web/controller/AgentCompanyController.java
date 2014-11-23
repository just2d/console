package com.nuoshi.console.web.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nuoshi.console.domain.agent.AgentCompany;
import com.nuoshi.console.domain.base.JsonResult;
import com.nuoshi.console.service.AgentCompanyService;


@Controller
@RequestMapping(value = "/agentCompany")
public class AgentCompanyController extends BaseController{
	@Resource
	AgentCompanyService agentCompanyService;
	@RequestMapping(value = "/searchAgentCompany")
	public void searchAgentCompany(HttpServletRequest request, HttpServletResponse response) {
		JsonResult jsonResult = new JsonResult();
		jsonResult.setNumFound(0);
		try {
			List<AgentCompany> agentCompanys = agentCompanyService.searchAgentCompany();
			jsonResult.setData(gson.toJson(agentCompanys));
			jsonResult.setMessage("true");
			jsonResult.setResult(true);
		} catch (Exception e) {
			jsonResult.setMessage("false");
			jsonResult.setResult(false);
		}
		String resultJson = gson.toJson(jsonResult);
		sentResponseInfo(response, resultJson);
	}
	


	@RequestMapping(value = "/addAgentCompany")
	public void addAgentCompany(HttpServletResponse response, HttpServletRequest request,AgentCompany agentCompany) {
		String agentCompanyJson=request.getParameter("json");
		agentCompany = gson.fromJson(agentCompanyJson, AgentCompany.class);
		JsonResult jsonResult = new JsonResult();
		jsonResult.setNumFound(0);
		try {
			int ret = agentCompanyService.addAgentCompany(agentCompany);
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
	

	
	
	
	
	@RequestMapping(value = "/deleteAgentCompany/{id}")
	public void deleteAgentCompany(HttpServletRequest request,HttpServletResponse response, @PathVariable("id") String id){
		JsonResult jsonResult = new JsonResult();
		jsonResult.setNumFound(0);
		try{
			agentCompanyService.deleteAgentCompany(id);
			jsonResult.setMessage("true");
			jsonResult.setResult(true);
		}catch(RuntimeException e){
			jsonResult.setMessage("false");
			jsonResult.setResult(false);
		}
		String json = gson.toJson(jsonResult);
		sentResponseInfo(response,json);
	}
	@RequestMapping(value = "/updateAgentCompany")
	public void updateAgentCompany(HttpServletRequest request,HttpServletResponse response,AgentCompany agentCompany){
		String agentCompanyJson=request.getParameter("json");
		agentCompany = gson.fromJson(agentCompanyJson, AgentCompany.class);
		JsonResult jsonResult = new JsonResult();
		jsonResult.setNumFound(0);
		try {
			agentCompanyService.updateAgentCompany(agentCompany);
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
	
	
	
	@RequestMapping(value = "/mergeCompany")
	public void mergeCompany(HttpServletRequest request,HttpServletResponse response){
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
		String companyName=request.getParameter("newCompanyName");
		
		JsonResult jsonResult = new JsonResult();
		jsonResult.setNumFound(0);
		try {
			agentCompanyService.mergeAgentCompany(oldCompanyId,newCompanyId,companyName);
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
