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

import com.nuoshi.console.domain.house.DayHouseQuality;
import com.nuoshi.console.service.HouseQualityService;

@Controller
@RequestMapping("/statis/houseQuality")
public class HouseQualityController extends BaseController {
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	@Resource
	private HouseQualityService houseQualityService;

	/**
	 * 房源数量初始页面
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/getcount")
	public String getHouseHouseQualityCount(HttpServletRequest request,HttpServletResponse response, Model model) {
		String dateStr = request.getParameter("date");
		if(StringUtils.isBlank(dateStr)){
			Calendar ca = Calendar.getInstance();
			ca.add(Calendar.DAY_OF_MONTH, -1);
			dateStr= sdf.format(ca.getTime());
		}
		int date = 0 ;
		try{
			date = Integer.parseInt(dateStr.replace("-", ""));
		}catch (Exception e) {
			// TODO: handle exception
		}
		List<DayHouseQuality> houseQualitys  = houseQualityService.getHouseQualityById(date);
		if(houseQualitys!=null){
			for (DayHouseQuality dayHouseQuality : houseQualitys) {
				if(dayHouseQuality.getType()==1){
					model.addAttribute("resaleQuality", dayHouseQuality);
				}
				if(dayHouseQuality.getType()==2){
					model.addAttribute("rentQuality", dayHouseQuality);
				}
			}
		}
		model.addAttribute("date", dateStr);
		return "tiles:statis.houseQuality";
	}

	
}
