package com.nuoshi.console.web.controller;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.gson.Gson;
import com.nuoshi.console.domain.topic.Locale;
import com.nuoshi.console.service.LocaleService;

@Controller
@RequestMapping(value = "/local")
public class LocalController extends BaseController{
	@Resource
	LocaleService localeService;
	@RequestMapping(value = "/cityList")
	public void getCity(HttpServletResponse response){
		Gson gson=new Gson();
		List<Locale> list=localeService.getCities();
		response.setContentType("text/html;charset=UTF-8"); 
		try {
			response.getWriter().write(gson.toJson(list));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @author ningt
	 * @param cityCode
	 * @param response
	 */
	@RequestMapping("/distList/{distId}")
	public void getDistByCityCode(@PathVariable("distId") int cityId,HttpServletResponse response){
		List<Locale> distList = localeService.getDistLocalByCityId(cityId);
		Gson gson = new Gson();
		if(distList!=null&& distList.size()>0){
			sentResponseInfo(response, gson.toJson(distList).toString());
		}
		
		
	}
	
	/**
	 * @author ningt
	 * @param cityCode
	 * @param response
	 */
	@RequestMapping("/blockList")
	public void getDistByCityCode(@RequestParam(value="distName",required = false)String distName,HttpServletResponse response){
		
		List<Locale> distList = localeService.getDistLocalByCityName(distName);
		Gson gson = new Gson();
		if(distList!=null&& distList.size()>0){
			sentResponseInfo(response, gson.toJson(distList).toString());
		}
		
		
	}
	
	/**
	 * 根据首字母和城市获得热点区域
	 * 
	 * @param ch 城市首字母
	 * @return
	 */
	@RequestMapping("/cityList/{ch}")
	public void getCityListByChar(@PathVariable("ch")String ch,HttpServletResponse response){
		List<Locale> distList = localeService.getCityCapitalBlocks(ch);
		Gson gson = new Gson();
		sentResponseInfo(response, gson.toJson(distList).toString());
	}
	
}
