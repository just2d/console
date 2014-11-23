package com.nuoshi.console.web.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nuoshi.console.domain.web.DayWebStatis;
import com.nuoshi.console.domain.web.MonthWebStatis;
import com.nuoshi.console.service.WebStatisService;

@Controller
@RequestMapping("/statis/webStatis")
public class WebStatisController extends BaseController {
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	private static SimpleDateFormat monthSdf = new SimpleDateFormat("yyyy-MM");

	@Resource
	private WebStatisService webStatisService;

	/**
	 * 网站统计
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/getDayCount")
	public String getDayWebStatisServiceCount(HttpServletRequest request,HttpServletResponse response, Model model) {
		String dateStr = request.getParameter("date");
		String startDateStr = request.getParameter("startDate");
		String endDateStr = request.getParameter("endDate");
		String cityIdStr = request.getParameter("cityId");
		String distIdStr = request.getParameter("distId");
		String type = request.getParameter("type");
		//　处理日期
		int date = 0 ;
		if(StringUtils.isBlank(dateStr)){
			Calendar ca = Calendar.getInstance();
			ca.add(Calendar.DAY_OF_MONTH, -1);
			dateStr= sdf.format(ca.getTime());
		} 
		
		try{
			date = Integer.parseInt(dateStr.replace("-", ""));
		}catch (Exception e) {
			// TODO: handle exception
		}
		int startDate = 0 ;
		if(StringUtils.isBlank(startDateStr)){
			Calendar ca = Calendar.getInstance();
			ca.add(Calendar.DAY_OF_MONTH, -30);
			startDateStr= sdf.format(ca.getTime());
		}
		
		try{
			startDate = Integer.parseInt(startDateStr.replace("-", ""));
		}catch (Exception e) {
			// TODO: handle exception
		}
		int endDate = 0 ;
		if(StringUtils.isBlank(endDateStr)){
			Calendar ca = Calendar.getInstance();
			endDateStr= sdf.format(ca.getTime());
		} 
		
		try{
			endDate = Integer.parseInt(endDateStr.replace("-", ""));
		}catch (Exception e) {
			// TODO: handle exception
		}
		
		
		int cityId = 0 ;
		int distId = 0 ;
		try{
			cityId = Integer.parseInt(cityIdStr);
			distId = Integer.parseInt(distIdStr);
		}catch (Exception e) {
			// TODO: handle exception
		}
		List<DayWebStatis> dayWebStatisList = null;
		if("city".equals(type)){//按城市展开
			  cityId = 0 ;
			  distId = 0 ;
			dayWebStatisList = webStatisService.getDayCityData(date);
		}else{
			dayWebStatisList  = webStatisService.getDayAllData(cityId,distId,startDate,endDate);
		}
		model.addAttribute("dayWebStatisList", dayWebStatisList);
		model.addAttribute("date", dateStr);
		model.addAttribute("cityId", cityId);
		model.addAttribute("distId", distId);
		model.addAttribute("startDate", startDateStr);
		model.addAttribute("endDate", endDateStr);
		
		return "tiles:statis.dayWebStatis";
	}

	/**
	 * 网站统计
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/getMonthCount")
	public String getMonthWebStatisServiceCount(HttpServletRequest request,HttpServletResponse response, Model model) {
		String dateStr = request.getParameter("date");
		String startDateStr = request.getParameter("startDate");
		String endDateStr = request.getParameter("endDate");
		String cityIdStr = request.getParameter("cityId");
		String distIdStr = request.getParameter("distId");
		String type = request.getParameter("type");
		//　处理日期
		int date = 0 ;
		if(StringUtils.isBlank(dateStr)){
			Calendar ca = Calendar.getInstance();
			dateStr= monthSdf.format(ca.getTime());
		} 
		
		try{
			date = Integer.parseInt(dateStr.replace("-", ""));
		}catch (Exception e) {
			// TODO: handle exception
		}
		int startDate = 0 ;
		if(StringUtils.isBlank(startDateStr)){
			Calendar ca = Calendar.getInstance();
			ca.set(ca.get(Calendar.YEAR), 1,0);
			startDateStr= monthSdf.format(ca.getTime());
		} 
		
		try{
			startDate = Integer.parseInt(startDateStr.replace("-", ""));
		}catch (Exception e) {
			// TODO: handle exception
		}
		int endDate = 0 ;
		if(StringUtils.isBlank(endDateStr)){
			Calendar ca = Calendar.getInstance();
			endDateStr= monthSdf.format(ca.getTime());
		} 
		
		try{
			endDate = Integer.parseInt(endDateStr.replace("-", ""));
		}catch (Exception e) {
			// TODO: handle exception
		}
		
		
		int cityId = 0 ;
		int distId = 0 ;
		try{
			cityId = Integer.parseInt(cityIdStr);
			distId = Integer.parseInt(distIdStr);
		}catch (Exception e) {
			// TODO: handle exception
		}
		List<MonthWebStatis> monthWebStatisList = null;
		if("city".equals(type)){//按城市展开
			monthWebStatisList = webStatisService.getMonthCityData(date);
		}else{
			monthWebStatisList  = webStatisService.getMonthAllData(cityId,distId,startDate,endDate);
		}
		model.addAttribute("monthWebStatisList", monthWebStatisList);
		model.addAttribute("date", dateStr);
		model.addAttribute("startDate", startDateStr);
		model.addAttribute("endDate", endDateStr);
		
		return "tiles:statis.monthWebStatis";
	}

	
}
