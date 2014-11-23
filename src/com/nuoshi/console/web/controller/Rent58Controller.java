package com.nuoshi.console.web.controller;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nuoshi.console.service.Rent58VerifyService;

@Controller
@RequestMapping(value = "/rent58")
@SuppressWarnings("unchecked")
public class Rent58Controller extends BaseController {
	@Autowired
	private Rent58VerifyService rent58VerifyService;


	/**
	 * 获取58房源信息
	 */
	@RequestMapping(value = "/allrentsearch/list")
	public String getAllRent4VerifyByPage(Model model, HttpSession session, HttpServletRequest request) {
		String authorid = request.getParameter("authorid");
		String id = request.getParameter("id");
		String authorphone = request.getParameter("authorphone");
		String name = request.getParameter("name");
		String title = request.getParameter("title");
		String cityId = request.getParameter("cityId");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		StringBuffer condition = new StringBuffer();
		HashMap params = null;
		if (StringUtils.isNotBlank(id)) {
			id = id.trim();
			condition.append(" and r.id = '" + id+"'");
		}
		if (StringUtils.isNotBlank(authorid)) {
			authorid = authorid.trim();
			condition.append(" and r.authorid = '" + authorid+"'");
		}
		if (StringUtils.isNotBlank(name)) {
			name = name.trim();
			condition.append(" and r.authorname like '%" + name + "%'");
		}
		if (StringUtils.isNotBlank(title)) {
			title =  title.trim();
			condition.append(" and r.title like '%" + title + "%'");
		}
		if (StringUtils.isNotBlank(authorphone)) {
			authorphone = authorphone.trim();
			condition.append(" and r.authorphone = '"+authorphone+"' ");
		}
		if(StringUtils.isNotBlank(cityId) && Integer.parseInt(cityId) > 0) {
			condition.append(" and r.cityid = " + Integer.parseInt(cityId));
		}
		if(!StringUtils.isBlank(startDate) && !"起始日期".equals(startDate)) {
			if(!StringUtils.isBlank(endDate) && !"终止日期".equals(endDate)) {
				condition.append(" and r.pubdate > '"+startDate+"' AND r.pubdate < '"+endDate+"'");
			} else {
				condition.append(" and r.pubdate > '"+startDate+"'");
			}
		} else {
			if(!StringUtils.isBlank(endDate) && !"终止日期".equals(endDate)) {
				condition.append(" and r.pubdate < '"+endDate+"'");
			} 
		}
		if (StringUtils.isNotBlank(condition.toString())) {
			params = new HashMap<String, Object>();
			params.put("condition", condition);
		}
		List<HashMap<String, Object>> rentList = rent58VerifyService.getAll58RentByPage(params);
		model.addAttribute("rentList", rentList);
		model.addAttribute("cityId", cityId == null ? 0 : cityId);
		return "tiles:58allrent.list";

	}
	/**
	 * 删除58房源信息
	 */
	@RequestMapping(value = "/delete")
	public String delete(Model model, HttpSession session,
			HttpServletRequest request) {
		String id = request.getParameter("id");
		rent58VerifyService.deleteRent(Integer.parseInt(id));

		model.addAttribute("info", "success");
		
		return "json";
		
	}
	/**
	 * 批量删除58房源信息
	 */
	@RequestMapping(value = "/batch/delete")
	public String batchDelete(Model model, HttpSession session,
			HttpServletRequest request) {
		String  id = request.getParameter("ids");
		if(StringUtils.isBlank(id)){
			model.addAttribute("error", "请至少选择一个房源!");
			return "json";
		}
		String[] ids  = id.split(",");
	
			for (int i = 0; i < ids.length; i++) {
				rent58VerifyService.deleteRent(Integer.parseInt(ids[i]));
			}
			
		
		model.addAttribute("info", "success");
		
		return "json";
		
	}


}
