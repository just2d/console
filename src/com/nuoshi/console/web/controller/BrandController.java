package com.nuoshi.console.web.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nuoshi.console.domain.agent.Brand;
import com.nuoshi.console.domain.base.JsonResult;
import com.nuoshi.console.service.BrandService;


@Controller
@RequestMapping(value = "/brand")
public class BrandController extends BaseController{
	@Resource
	BrandService brandService;
	@RequestMapping(value = "/searchBrand")
	public void searchBrand(HttpServletRequest request, HttpServletResponse response) {
		JsonResult jsonResult = new JsonResult();
		jsonResult.setNumFound(0);
		try {
			List<Brand> brands = brandService.searchBrand();
			jsonResult.setData(gson.toJson(brands));
			jsonResult.setMessage("true");
			jsonResult.setResult(true);
		} catch (Exception e) {
			jsonResult.setMessage("false");
			jsonResult.setResult(false);
		}
		String resultJson = gson.toJson(jsonResult);
		sentResponseInfo(response, resultJson);
	}
	


	@RequestMapping(value = "/addBrand")
	public void addBrand(HttpServletResponse response, HttpServletRequest request,Brand brand) {
		String brandJson=request.getParameter("json");
		brand = gson.fromJson(brandJson, Brand.class);
		JsonResult jsonResult = new JsonResult();
		jsonResult.setNumFound(0);
		try {
			int ret = brandService.addBrand(brand);
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
	

	
	
	
	
	@RequestMapping(value = "/deleteBrand/{id}")
	public void deleteBrand(HttpServletRequest request,HttpServletResponse response, @PathVariable("id") String id){
		JsonResult jsonResult = new JsonResult();
		jsonResult.setNumFound(0);
		try{
			brandService.deleteBrand(id);
			jsonResult.setMessage("true");
			jsonResult.setResult(true);
		}catch(RuntimeException e){
			jsonResult.setMessage("false");
			jsonResult.setResult(false);
		}
		String json = gson.toJson(jsonResult);
		sentResponseInfo(response,json);
	}
	@RequestMapping(value = "/updateBrand")
	public void updateBrand(HttpServletRequest request,HttpServletResponse response,Brand brand){
		String brandJson=request.getParameter("json");
		brand = gson.fromJson(brandJson, Brand.class);
		JsonResult jsonResult = new JsonResult();
		jsonResult.setNumFound(0);
		try {
			brandService.updateBrand(brand);
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
