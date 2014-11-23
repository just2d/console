package com.nuoshi.console.web.controller;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nuoshi.console.common.Globals;
import com.nuoshi.console.domain.base.JsonResult;
import com.nuoshi.console.domain.user.Publisher;
import com.nuoshi.console.domain.wenda.Answer;
import com.nuoshi.console.domain.wenda.Category;
import com.nuoshi.console.domain.wenda.Question;
import com.nuoshi.console.service.CategoryManageService;
import com.nuoshi.console.service.LocaleService;
import com.nuoshi.console.service.ReleaseAccountService;

@Controller
@RequestMapping(value = "/publish")
public class ReleaseAccountController {
	protected Logger log = Logger.getLogger(ReleaseAccountController.class);
	@Resource
	ReleaseAccountService releaseAccountService;
	@Resource
	CategoryManageService categoryManageService;
	
	@RequestMapping(value = "/add")
	public String addUser(Model model, HttpServletRequest request,@RequestParam("userId")Integer userId) {
		JsonResult result=new JsonResult();
		result.setResult(true);
		try {
			releaseAccountService.add(userId);
			result.setResult(true);
		} catch (Exception e) {
			result.setMessage(e.getMessage());
			result.setResult(false);
		}
		model.addAttribute("result", result);
		return "json";
	}
	@RequestMapping(value = "/userlist")
	public String userList(Model model, HttpServletRequest request,
			@RequestParam(value="role",required=false)String role,
			@RequestParam(value="userNameOrId",required=false)String userNameOrId) {
		Map<String,String> map=new HashMap<String, String>();
		if(StringUtils.isNotBlank(role)){
			map.put("role", role);
		}
		if(StringUtils.isNotBlank(userNameOrId)){
			map.put("userId", userNameOrId);
			map.put("userName", userNameOrId);
		}
		List<Publisher> list=releaseAccountService.query(map);
		if(CollectionUtils.isNotEmpty(list)){
			Map<Integer, String> cityMap=new HashMap<Integer, String>();
			for (Publisher publisher : list) {
				int cityId=publisher.getCityId();
				cityMap.put(cityId, LocaleService.getName(cityId));
			}
			model.addAttribute("cityMap", cityMap);
		}
		model.addAttribute("publisher", list);
		model.addAttribute("role", role);
		model.addAttribute("userNameOrId", userNameOrId);
		return "tiles:publish.search";
	}

	@RequestMapping(value = "/delete")
	public String deleteUser(Model model, HttpServletRequest request,@RequestParam("userIds")String userIds) {
		List<Integer> ids=this.getIdList(userIds);
		JsonResult result=new JsonResult();
		result.setResult(true);
		try {
			releaseAccountService.delete(ids);
			result.setResult(true);
		} catch (Exception e) {
			result.setMessage(e.getMessage());
			result.setResult(false);
		}
		model.addAttribute("result", result);
		return "json";
	}
	
	private List<Integer> getIdList(String userIds) {
		if (StringUtils.isBlank(userIds)) {
			return null;
		}
		List<Integer> ids = new ArrayList<Integer>();
		String[] strArr = {};
		if (userIds.contains(",")) {
			strArr = userIds.split(",");
			for (int i = 0; i < strArr.length; i++) {
				String str = strArr[i];
				if (StringUtils.isNotBlank(str)) {
					ids.add(Integer.parseInt(str.trim()));
				}
			}
		}else{
			ids.add(Integer.parseInt(userIds.trim()));
		}
		return ids;
	}
	
	
	
	
	@RequestMapping(value = "/wenda/new")
	public String showWenda(Model model, HttpServletRequest request) {
		List<Publisher> userList=releaseAccountService.queryByRole(0, Globals.BEIJINGID);
		model.addAttribute("userList", userList);
		//分类
		Category categoryQuery = new Category();
		categoryQuery.setPid(0);
		List<Category> categoryParam = categoryManageService.queryCategoryList(categoryQuery);
		model.addAttribute("categoryParam", categoryParam);
		return "tiles:publish.wen";
	}
	
	@RequestMapping(value = "/wenda/pub")
	public String publishWenda(Model model, HttpServletRequest request, 
			@RequestParam("answerObj") String answerObj,
			@RequestParam("questionObj") String questionObj) {
		List<Answer> answerList = null;
		Gson gson = new Gson();
		if (StringUtils.isNotBlank(answerObj)) {
			Type type = new TypeToken<List<Answer>>() {
			}.getType();
			answerList = gson.fromJson(answerObj, type);
		}
		Question question = null;
		if (StringUtils.isNotBlank(questionObj)) {
			question = gson.fromJson(questionObj, Question.class);
		}

		return "tiles:publish.wen";
	}
	
}
