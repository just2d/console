package com.nuoshi.console.web.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nuoshi.console.domain.stat.HouseLabelInfo;
import com.nuoshi.console.service.HouseLabelCountService;

@Controller
@RequestMapping("/statis/houselabel")
public class HouseLabelCountController extends BaseController {

	@Resource
	private HouseLabelCountService houseLabelCountService;

	/**
	 * 房源数量初始页面
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/getcount")
	public String getHouseCount(HttpServletRequest request,HttpServletResponse response, Model model) {
		String cityId = request.getParameter("cityId");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		List<HouseLabelInfo> labelsList= houseLabelCountService.getHouseLabelCount(cityId,startDate,endDate);
		model.addAttribute("labelsList", labelsList);
		model.addAttribute("cityId", cityId == null ? 0 : cityId);
		return "tiles:statis.houseLabelCount";
	}

	
}
