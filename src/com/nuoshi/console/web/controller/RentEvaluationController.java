package com.nuoshi.console.web.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.nuoshi.console.service.RentEvaluationService;

@Controller
@RequestMapping(value = "/rent/eval")
public class RentEvaluationController {

	@Resource
	private RentEvaluationService rentEvaluationService;
	
	@RequestMapping(value = "/list")
	public String evaluationRentList(Model model, HttpServletRequest request) {
		String cityIdStr = request.getParameter("cityId");
		String queryTypeStr = request.getParameter("queryType");
		String content = request.getParameter("content");
		String sourceIdStr = request.getParameter("sourceId");
		String evalConTypeStr = request.getParameter("evalConType");
		int cityId = 0;
		int queryType = -1;
		int sourceId = 0;
		int evalConType =0;
		try {
			if(StringUtils.isNotEmpty(cityIdStr)) {
				cityId = Integer.parseInt(cityIdStr);
			}
			if(StringUtils.isNotEmpty(queryTypeStr)) {
				queryType = Integer.parseInt(queryTypeStr);
			}
			if(StringUtils.isNotEmpty(sourceIdStr)) {
				sourceId = Integer.parseInt(sourceIdStr);
			}
			if(StringUtils.isNotEmpty(evalConTypeStr)) {
				evalConType = Integer.parseInt(evalConTypeStr);
			}
			model.addAttribute("rents", rentEvaluationService.getRentForEvaluation(cityId,sourceId, queryType, content,evalConType));
			model.addAttribute("cityId", cityId);
			model.addAttribute("queryType", queryType);
			model.addAttribute("sourceId", sourceId);
			model.addAttribute("content", content);
			model.addAttribute("evalConType", evalConType);
			
		} catch(Exception e) {
			
		}
		
		return "tiles:rent.evaluation.list";
	}
	
	@RequestMapping(value="/evalList/{houseId}/{sourceId}")
	public String evalList(Model model, @PathVariable("houseId")int houseId, @PathVariable("sourceId")int sourceId) {
		model.addAttribute("evaluations", rentEvaluationService.getHouseEvaluation(houseId, sourceId));
		model.addAttribute("houseId", houseId);
		model.addAttribute("sourceId", sourceId);
		return "tiles:rent.evaluation.detail";
	}
	
	@RequestMapping(value="/changeVisible/{houseId}/{sourceId}/{status}")
	public String changeVisible(Model model, @PathVariable("houseId")int houseId, @PathVariable("sourceId")int sourceId, @PathVariable("status")int status) {
		int result = rentEvaluationService.changeVisible(houseId, sourceId, status);
		if(result > 0) {
			model.addAttribute("result", true);
		} else {
			model.addAttribute("result", false);
		}
		return "json";
	}
	
	@RequestMapping(value="/realHouse/{houseId}/{sourceId}/{status}")
	public String realHouse(Model model, @PathVariable("houseId")int houseId, @PathVariable("sourceId")int sourceId, @PathVariable("status")int status) {
		int result = rentEvaluationService.realHouse(houseId, sourceId,  status);
		if(result > 0) {
			model.addAttribute("result", true);
		} else {
			model.addAttribute("result", false);
		}
		return "json";
	}
	
	@RequestMapping(value="/delEval/{houseId}/{sourceId}")
	public String delEval(Model model, @RequestParam("ids")String ids, @PathVariable("houseId")int houseId, @PathVariable("sourceId")int sourceId) {
		int result = rentEvaluationService.delHouseEvaluation(ids);
		rentEvaluationService.reCaculateAvgPoint(houseId, sourceId);
		if(result > 0) {
			model.addAttribute("result", true);
		} else {
			model.addAttribute("result", false);
		}
		return "json";
	}
}
