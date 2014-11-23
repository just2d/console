package com.nuoshi.console.web.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.nuoshi.console.domain.audit.AuditEstateLog;
import com.nuoshi.console.domain.topic.Locale;
import com.nuoshi.console.service.EstateUpcAuditHistoryService;
import com.nuoshi.console.service.LocaleService;
import com.nuoshi.console.view.EstateListView;

@Controller
@RequestMapping("/estateupcaudithistory")
public class EstateUpcAuditHistoryController extends BaseController {

	@Resource
	private EstateUpcAuditHistoryService estateUpcAuditHistoryService;


	@Resource
	LocaleService localeService;


	/**
	 * 小区数据审核记录
	 * 
	 * @return
	 */
	@RequestMapping("/list")
	public String list(EstateListView estateCondition,
			@RequestParam(value = "ch", required = false) String ch,
			@RequestParam(value = "actionType", required = true) Integer actionType,
			Model model, HttpServletRequest request) {
		setDefaultCity(estateCondition,model);
		String cityName = getNameById(estateCondition.getCityId());
		String distName = getNameById(estateCondition.getDistId());
		if (distName != null) {
			model.addAttribute("blockList", localeService.getDistLocalByCityId(estateCondition.getDistId()));
		}
		// 用于回显表单项数据
		model.addAttribute("ch", ch);
		model.addAttribute("actionType", actionType);
		model.addAttribute("cityName", cityName);
		List<AuditEstateLog> result = null;
		try {
			// 通过id搜索..
			if (estateCondition.getEstateId() != null && estateCondition.getEstateId()  > 0) {
				result = estateUpcAuditHistoryService.getEstateUpcAuditHistoryByEstateId(estateCondition.getEstateId(),actionType);
			} else {
				// 没通过id搜索
				result = estateUpcAuditHistoryService.getEstateUpcAuditHistoryByCondition(estateCondition,actionType);
			}
			
			model.addAttribute("estateList", result);
			model.addAttribute("condition", estateCondition);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
		}
		
		return "tiles:estateupc.audithistory.list";
	}

	


	

	private String getNameById(Integer id) {

		if (id != null && id != 0 && id != -1) {
			return LocaleService.getName(id.intValue());
		}
		return null;
	}

	private void setDefaultCity(EstateListView condition, Model model) {
		if (condition.getCityId() != null) {
			List<Locale> distList = localeService.getDistLocalByCityId(condition.getCityId());
			model.addAttribute("distList", distList);
		}
	}




}
