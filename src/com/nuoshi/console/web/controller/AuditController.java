package com.nuoshi.console.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nuoshi.console.common.Globals;
import com.nuoshi.console.common.util.SmcConst;
import com.nuoshi.console.common.util.StringFormat;
import com.nuoshi.console.domain.audit.AuditAgentList;
import com.nuoshi.console.domain.audit.AuditPhotoSetting;
import com.nuoshi.console.domain.audit.AuditVcrTask;
import com.nuoshi.console.service.AuditService;
import com.nuoshi.console.service.AuditVcrTaskService;
import com.nuoshi.console.web.session.SessionUser;

@Controller
@RequestMapping(value = "/audit")
public class AuditController  extends BaseController{
	@Resource
	private AuditService auditService;
	
	@Resource
	private AuditVcrTaskService auditVcrTaskService;
	
	@RequestMapping(value = "/alterReaudit/{id}/{type}")
	public String alterReaudit(HttpServletRequest request,@PathVariable("id")Integer id , @PathVariable("type")Integer type){
		Integer houseType=0;
		if(type==Globals.RENT_TYPE){
			houseType=Globals.HOUSE_TYPE_RENT;
		}else if(type==Globals.RESALE_TYPE){
			houseType=Globals.HOUSE_TYPE_RESALE;
		}
		List<Integer> idList=new ArrayList<Integer>();
		idList.add(id);
		//删除任务列表和清空房源标记
		auditService.deleteAuditTask(idList,houseType);
		return "json";
	}
	
	@RequestMapping(value = "/vcr/list/{type}/{cityId}")
	public String list(Model model, HttpServletRequest request, @PathVariable("type")Integer type, @PathVariable("cityId")int cityId){
		String houseIdStr = request.getParameter("houseId");
		int houseId =0;
		if(houseIdStr != null && houseIdStr.trim().length()>0){
			houseId =Integer.valueOf(houseIdStr);
		}
		SessionUser currentUser = (SessionUser) request.getSession().getAttribute(SmcConst.SESSION_USER);
		List<AuditVcrTask> tasks = auditVcrTaskService.getAuditTask(currentUser.getUser().getId(), currentUser.getUser().getChnName(), type, cityId,houseId);
		model.addAttribute("houseId", houseIdStr);
		model.addAttribute("houseType", type);
		model.addAttribute("tasks", tasks);
		model.addAttribute("cityId", cityId);
		return "tiles:house.vcrList";
	}
	
	@RequestMapping(value = "/vcr/check/{type}/{cityId}")
	public String check(Model model, HttpServletRequest request, @PathVariable("type")Integer type, @PathVariable("cityId")int cityId){
		String houseIdStr = request.getParameter("houseId");
		String auditResult = request.getParameter("totalResult");
		if(StringFormat.isNotBlank(auditResult)) {
			auditVcrTaskService.auditVcrResult(auditResult.split(","), type);
		}
		return "redirect:/audit/vcr/list/" + type + "/" + cityId+"?houseId="+houseIdStr;
	}
	
	@RequestMapping(value = "/vcr/history/{type}/{cityId}/{userId}/{result}")
	public String vcrHistory(Model model, HttpServletRequest request, @PathVariable("type")Integer type, 
			@PathVariable("cityId")int cityId, @PathVariable("userId")int userId, @PathVariable("result")int result){
		model.addAttribute("houseType", type);
		model.addAttribute("cityId", cityId);
		model.addAttribute("userId", userId);
		model.addAttribute("result", result);
		model.addAttribute("tasks", auditVcrTaskService.getAuditVcrHistory(userId, cityId, type, result));
		return "tiles:house.vcrHistoryList";
	}
	@RequestMapping(value = "/dealAnomalyTask")
	public String dealAnomalyTask(Model model, HttpServletRequest request){
		int count=auditService.dealAnomalyTask();
		model.addAttribute("dealCount", count);
		return "json";
	}
	//新审核
	@RequestMapping(value = "/photo/manelist/{cityId}/{type}")
	public String photoNameList(Model model, HttpServletRequest request,@PathVariable("cityId")int cityId,@PathVariable("type")int type){
		String agentIdStr = request.getParameter("agentId");
		int agentId = 0;
		if (agentIdStr != null) {
			try {
				agentId = Integer.parseInt(agentIdStr);
			} catch (Exception e) {
				// TODO: handle exception
			}
			if(agentId>0){
				model.addAttribute("agentId", agentId);
			}
		}
		List<AuditAgentList> list = auditService.getPhotoNameList(cityId,type,agentId);
	
		
		model.addAttribute("list", list);
		model.addAttribute("cityId", cityId);
		model.addAttribute("type", type);
		
		return "tiles:house.audit.photo.agentlist";
	}
	//新审核
	@RequestMapping(value = "/photo/namelist/add/{type}")
	public String addNameList(Model model, HttpServletRequest request,@PathVariable("type")int type){
		String agentIdStr = request.getParameter("agentIds");
		if(agentIdStr==null||agentIdStr.isEmpty()){
			model.addAttribute("error", "请填写经纪人的ID");
			return "josn" ;
		}
		String[] agentIds = agentIdStr.split(",");
		if (agentIds.length>0) {
			List<String> ids = new ArrayList<String>();
			for (String id : agentIds) {
				ids.add(id);
				auditService.updatePhotoNameList(ids,type);
			}
			
		}
		
		model.addAttribute("success", true);
		
		return "json";
	}
	//新审核
	@RequestMapping(value = "/photo/setting/{cityId}")
	public String photoSetting(Model model, HttpServletRequest request,@PathVariable("cityId")int cityId){
		
		List<AuditPhotoSetting> photoSettings = auditService.getPhotoSetting(cityId);
		
		model.addAttribute("photoSettings", photoSettings);
		model.addAttribute("cityId", cityId);
		return "tiles:house.audit.photo.setting";
	}
	//新审核
	@RequestMapping(value = "/photo/setting/update/{cityId}")
	public String updatePhotoSetting(Model model, HttpServletRequest request,@PathVariable("cityId")int cityId){
		String illegalRateStr = request.getParameter("illegalRate");
		String timeRuleStr = request.getParameter("timeRule");
		String dayMaxPhotoCountStr = request.getParameter("dayMaxPhotoCount");
		String auditCountStr = request.getParameter("auditCount");
		
		if(illegalRateStr==null||timeRuleStr==null||dayMaxPhotoCountStr==null|auditCountStr==null){
			model.addAttribute("error", "参数不正确");
			return "json" ;
		}
		float illegalRate = 0.0f ;
		int timeRule = 0;
		int dayMaxPhotoCount = 0 ;
		int auditCount = 0 ;
		try{
			illegalRate = Float.parseFloat(illegalRateStr);
			timeRule = Integer.parseInt(timeRuleStr);
			dayMaxPhotoCount = Integer.parseInt(dayMaxPhotoCountStr);
			auditCount = Integer.parseInt(auditCountStr);
			
		}catch (Exception e) {
			model.addAttribute("error", "参数不正确");
			return "json" ;
		}
		
		if(cityId<=0||illegalRate>100){
			model.addAttribute("error", "参数不正确");
			return "json" ;
		}
		AuditPhotoSetting auditPhotoSetting = new AuditPhotoSetting();
		auditPhotoSetting.setCityId(cityId);
		auditPhotoSetting.setAuditCount(auditCount);
		auditPhotoSetting.setDayMaxPhotoCount(dayMaxPhotoCount);
		auditPhotoSetting.setIllegalRate(illegalRate);
		auditPhotoSetting.setTimeRule(timeRule);
		 int i = auditService.updatePhotoSetting(auditPhotoSetting);
		
		 model.addAttribute("success", true);
		
		return "json";
	}
	
}
