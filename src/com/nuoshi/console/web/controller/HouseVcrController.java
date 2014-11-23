package com.nuoshi.console.web.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nuoshi.console.domain.base.House;
import com.nuoshi.console.domain.user.Function;
import com.nuoshi.console.service.RentService;
import com.nuoshi.console.service.ResaleService;

@Controller
@RequestMapping(value = "/vcr")
public class HouseVcrController {
	
	@Resource
	private ResaleService resaleService;
	
	@Resource
	private RentService rentService;
	
	/**
	 * 获取房源的视频待审核列表
	 * @param model
	 * @param type
	 * @return
	 */
	@RequestMapping(value = "/list/{type}")
	public String list(Model model, @PathVariable("type")int type) {
		List<House> houses = null;
//		if(1 == type) {
//			houses = resaleService.getAuditVcrHouse();
//		} else {
//			houses = rentService.getAuditVcrHouse();
//		}
		model.addAttribute("houses",houses);
		model.addAttribute("type", type);
		return "tiles:vcr.houseList";
	}
	
}
