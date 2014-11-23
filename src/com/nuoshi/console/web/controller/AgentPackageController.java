package com.nuoshi.console.web.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.nuoshi.console.common.util.SmcConst;
import com.nuoshi.console.domain.agent.AgentManage;
import com.nuoshi.console.domain.pckage.AgentPackage;
import com.nuoshi.console.domain.pckage.AgentPackageRelation;
import com.nuoshi.console.service.AgentManageService;
import com.nuoshi.console.service.AgentPackageRelationService;
import com.nuoshi.console.service.AgentPackageService;
import com.nuoshi.console.service.LocaleService;
import com.nuoshi.console.web.session.SessionUser;

@Controller
@RequestMapping(value = "/package/manage")
public class AgentPackageController extends BaseController {
	@Autowired
	private AgentPackageService agentPackageService;
	
	@Autowired
	private AgentManageService agentManageService;
	@Autowired
	private AgentPackageRelationService agentPackageRelationService;
	
	@Resource
	private LocaleService localeService;
	
	@RequestMapping(value="/list/{cityId}")
	public String list(Model model, HttpServletRequest request, @PathVariable("cityId")int cityId) {
		try{
			List<AgentPackage> packages = agentPackageService.getPackageByCityId(cityId);
			AgentPackage defaultPackage = agentPackageService.getDefaultPackageByCityId(cityId);
			model.addAttribute("packages", packages);
			model.addAttribute("cityId", cityId);
			model.addAttribute("defaultPackage", defaultPackage);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return "tiles:package.list";
	}
	
	@RequestMapping(value = "/add")
	public String add(Model model, HttpServletRequest request, AgentPackage agentPackage) {
		SessionUser sessionUser  = (SessionUser) request.getSession().getAttribute(SmcConst.SESSION_USER);
		try{
			agentPackage.setEntryId(sessionUser.getUser().getId());
			int result = agentPackageService.addAgentPackage(agentPackage);
			if(result > 0) {
				model.addAttribute("info", "添加套餐成功");
			} else {
				model.addAttribute("info", "添加套餐失败");
			}
			localeService.resetPackageInfo(agentPackage.getCityId());
		}catch(Exception e) {
			e.printStackTrace();
		}
		return "json";
	}
	
	@RequestMapping(value = "/del/{cityId}/{id}")
	public String del(Model model, @PathVariable("cityId")int cityId, @PathVariable("id")int id) {
		
		int result = agentPackageService.delPackage(id);
		if(result > 0) {
			model.addAttribute("delResult", "删除成功！");
		} else {
			model.addAttribute("delResult", "已经存在购买记录，不能删除！");
		}
		List<AgentPackage> packages = agentPackageService.getPackageByCityId(cityId);
		model.addAttribute("packages", packages);
		model.addAttribute("cityId", cityId);
		return "tiles:package.list";
	}
	
	@RequestMapping(value = "/edit/{id}")
	public String del(Model model, @PathVariable("id")int id) {
		
		AgentPackage agentPackage = agentPackageService.getPackageById(id);
		model.addAttribute("agentPackage", agentPackage);
		return "json";
	}
	
	@RequestMapping(value = "/save")
	public String save(Model model, AgentPackage agentPackage) {
		try{
			int result = agentPackageService.updateAgentPackage(agentPackage);
			if(result > 0) {
				model.addAttribute("info", "修改套餐成功");
			} else {
				model.addAttribute("info", "修改套餐失败");
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return "json";
	}
	
	@RequestMapping(value = "/exedefault/{cityId}/{month}")
	public String exeDefault(Model model, HttpServletRequest request, @PathVariable("cityId")int cityId, @PathVariable("month")int month) {
		int result = agentPackageService.changeDefaultPackageDays(7, month, month * 30);
		if(result > 0) {
			boolean res = false;
			if(cityId > 0) {
				res = agentPackageService.exeDefault(month, cityId);
			} else if(0 == cityId){
				res = agentPackageService.exeDefaultWhole(month);
			}
			if(res) {
				model.addAttribute("info", "设置免费套餐终止日期成功");
			} else {
				model.addAttribute("info", "设置免费套餐使用期限成功");
			}
		} else {
			model.addAttribute("info", "设置免费套餐终止日期失败");
		}
		return "json";
	}
	
	@RequestMapping(value = "/exedefaultdelay/{cityId}/{day}")
	public String exeDefaultDelay(Model model, HttpServletRequest request, @PathVariable("cityId")int cityId, @PathVariable("day")int day) {
		boolean result = false;
		if(cityId > 0) {
			result = agentPackageService.exeDefaultDelay(day, cityId);
		} else if(0 == cityId) {
			result = agentPackageService.exeDefaultDelayWhole(day);
		}
		if(result) {
			model.addAttribute("info", "免费套餐使用期限延期成功");
		} else {
			model.addAttribute("info", "免费套餐使用期限延期失败");
		}
		return "json";
	}
	
	
	@RequestMapping(value = "/ajax/packageInfo/get/{id}", method = RequestMethod.GET)
	public String getAgentPackage(Model model, @PathVariable("id") int id) {
		AgentManage agent = agentManageService.getAgentInfo(id);
		if(agent != null) {
			model.addAttribute("packages", agentPackageService.getPackageByCityIdAndDisplayStatus((agent.getCityid())));
			model.addAttribute("agentPackages", agentPackageRelationService.getAgentSpecialPackage(agent.getId()));
		}
		return "json";
	}
	
	@RequestMapping(value = "/ajax/packageInfo/update")
	public String updateAgentPackage(Model model, @RequestParam("agentId")int agentId, @RequestParam("packageIds")String packageIds,@RequestParam("prices")String prices) {
		
		AgentManage agent = agentManageService.getAgentInfo(agentId);
		if(agent != null) {
			agentPackageRelationService.delAPRelation(agentId);
			if(StringUtils.isNotEmpty(packageIds)) {
				String[] packageId = packageIds.split("_");
				String[] price = prices.split("_");
				for(int i = 0;i<packageId.length;i++) {
					agentPackageRelationService.addAPRelation(new AgentPackageRelation(agentId, Integer.parseInt(packageId[i]),Float.parseFloat(price[i])));
				}
			}
			model.addAttribute("message", "操作成功");
		} else {
			model.addAttribute("message", "操作失败");
		}
		return "json";
	}
}
