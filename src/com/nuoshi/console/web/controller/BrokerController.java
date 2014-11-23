package com.nuoshi.console.web.controller;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nuoshi.console.domain.agent.Broker;
import com.nuoshi.console.domain.base.JsonResult;
import com.nuoshi.console.service.BrokerService;


@Controller
@RequestMapping(value = "/broker")
public class BrokerController extends BaseController{
	@Resource
	BrokerService brokerService;
	
//	//测试   可删
//	@RequestMapping(value = "/searchBroker")
//	public void searchBroker(HttpServletRequest request, HttpServletResponse response) {
//		JsonResult jsonResult = new JsonResult();
//		jsonResult.setNumFound(0);
//		try {
//			List<Integer> ids=new ArrayList<Integer>();
//			List<Broker> brokers = brokerService.searchBrokerByIds(ids);
//			jsonResult.setData(gson.toJson(brokers));
//			jsonResult.setMessage("true");
//			jsonResult.setResult(true);
//		} catch (Exception e) {
//			jsonResult.setMessage("false");
//			jsonResult.setResult(false);
//		}
//		String resultJson = gson.toJson(jsonResult);
//		sentResponseInfo(response, resultJson);
//	}
	


	@RequestMapping(value = "/addBroker")
	public void addBroker(HttpServletResponse response, HttpServletRequest request,Broker broker) {
		String brokerJson=request.getParameter("json");
		broker = gson.fromJson(brokerJson, Broker.class);
		JsonResult jsonResult = new JsonResult();
		jsonResult.setNumFound(0);
		try {
			int ret = brokerService.addBroker(broker);
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
	

	
	
	
	
	@RequestMapping(value = "/deleteBroker/{id}")
	public void deleteBroker(HttpServletRequest request,HttpServletResponse response, @PathVariable("id") String id){
		JsonResult jsonResult = new JsonResult();
		jsonResult.setNumFound(0);
		try{
			brokerService.deleteBroker(id);
			jsonResult.setMessage("true");
			jsonResult.setResult(true);
		}catch(RuntimeException e){
			jsonResult.setMessage("false");
			jsonResult.setResult(false);
		}
		String json = gson.toJson(jsonResult);
		sentResponseInfo(response,json);
	}
	@RequestMapping(value = "/updateBroker")
	public void updateBroker(HttpServletRequest request,HttpServletResponse response,Broker broker){
		String brokerJson=request.getParameter("json");
		broker = gson.fromJson(brokerJson, Broker.class);
		JsonResult jsonResult = new JsonResult();
		jsonResult.setNumFound(0);
		try {
			brokerService.updateBroker(broker);
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
