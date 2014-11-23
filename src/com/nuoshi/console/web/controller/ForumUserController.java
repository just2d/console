package com.nuoshi.console.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.gson.Gson;
import com.taofang.commons.Utils;
import com.nuoshi.console.domain.bbs.Forumuser;
import com.nuoshi.console.domain.bbs.Role;
import com.nuoshi.console.domain.user.TFUser;
import com.nuoshi.console.service.ForumRoleService;
import com.nuoshi.console.service.ForumUserService;
import com.nuoshi.console.service.LocaleService;
import com.nuoshi.console.service.TaofangUserService;

@Controller
@RequestMapping(value = "/forumuser")
@SuppressWarnings("unchecked")
public class ForumUserController extends BaseController{

	@Resource
	private ForumUserService forumUserService;
	
	@Resource
	private ForumRoleService forumRoleService;
	
	@Resource
	private TaofangUserService taofangUserService;
	
	
	@RequestMapping(value = "/rolelist")
	public void Rolelist(Model model, HttpServletRequest request,HttpServletResponse response) {

		List<Role> roles=forumRoleService.getAll();
		Gson gson = new Gson();
		if(roles!=null&& roles.size()>0){
			sentResponseInfo(response, gson.toJson(roles).toString());
		}

	}
	@RequestMapping(value = "/checkUserIsExist")
	public void checkUser(@RequestParam(value="userName")String userName,HttpServletResponse response) {

		TFUser tfUser=taofangUserService.getUserByUserName(userName);
		sentResponseInfo(response, gson.toJson(tfUser).toString());

	}
	
	@RequestMapping(value = "/list")
	public String listByPage(Model model, HttpServletRequest request) {
		Map<String,String> conditions=new HashMap<String,String>();
		String search=request.getParameter("search");
		String cityId=request.getParameter("cityId");
		if(search!=null&&search.length()>0){
			conditions.put("name", search);
		}
		if(cityId!=null&&cityId.length()>0&&Integer.parseInt(cityId)>0){
			conditions.put("cityId", cityId);
		}
		List<Forumuser> users =forumUserService.selectByconditionByPage(conditions);
		
		model.addAttribute("userList", users);

		model.addAttribute("ch",request.getParameter("ch"));
		model.addAttribute("cityId",cityId);
		model.addAttribute("search",search);
		List<Role> roles=forumRoleService.getAll();
		model.addAttribute("roleList", roles);
		
		return "tiles:forumuser.list";
	}
	
	
	@RequestMapping(value = "/selectById")
	public String selectById(@RequestParam(value="id")int id,Model model, HttpServletRequest request) {
		
		Forumuser user=forumUserService.selectById(id);
		if(user != null){
			TFUser tfUser=taofangUserService.getUserByUserName(user.getUsername());
			model.addAttribute("tfUser",tfUser);
		}
		model.addAttribute("forumUser", user);
		List<Role> roles=forumRoleService.getAll();
		model.addAttribute("roleList", roles);
		String ch="";
		if(user!=null){
			ch= LocaleService.getFirstName(user.getCityid());
		}
		model.addAttribute("ch",ch);
		return "tiles:forumuser.edit";
	}
	
	
	@RequestMapping(value = "/delete")
	public String delete(@RequestParam(value="id")int id,Model model, HttpServletRequest request) {
		
		int i=forumUserService.deleteById(id);
		if(i>0){
			model.addAttribute("result", "删除成功");
		}else{
			model.addAttribute("result", "删除失败");
		}
		
		return "json";
	}
	
	@RequestMapping(value = "/adduser")
	public String adduser(@ModelAttribute("Forumuser")Forumuser forumuser,Model model, HttpServletRequest request) {
		TFUser tfUser = taofangUserService.getUserByUserName( forumuser.getUsername());
		if(tfUser != null){
			forumuser.setUserRole(tfUser.getRole());
			forumuser.setTrueName(tfUser.getName());
		}
			if(forumuser.getForumid() == null){
				forumuser.setForumid(0);
			}
			int i =forumUserService.addfroumuser(forumuser);
			if(i>0){
				model.addAttribute("result","保存成功");
			}else{
				model.addAttribute("result","保存失败");
			}
			return "redirect:list";

	}
	
	
	@RequestMapping(value = "/edit")
	public String edituser(@ModelAttribute("Forumuser")Forumuser forumuser,Model model, HttpServletRequest request) {

		int i =forumUserService.updateforumuser(forumuser);
		if(i>0){
			model.addAttribute("result","保存成功");
		}else{
			model.addAttribute("result","保存失败");
		}
		List<Forumuser> users =forumUserService.selectAllByPage();
		model.addAttribute("userList", users);
		return "tiles:forumuser.list";
	}
	
	@RequestMapping(value = "/checkName")
	public String checkName(Model model, HttpSession session,
			HttpServletRequest request,HttpServletResponse response) {
		String name = request.getParameter("name");
		String forumId = request.getParameter("forumid");
		Integer forumUserId =null ;
		if(StringUtils.isNotBlank(request.getParameter("id")) ){
			forumUserId = Integer.valueOf(request.getParameter("id"));
		}
		Map paramMap = new HashMap();
		paramMap.put("name",name);
		if(Utils.isNotEmptyString(forumId)){
			paramMap.put("forumId",Integer.valueOf(forumId));
		}
		List<Forumuser> userList=forumUserService.selectByCondition(paramMap);
		Forumuser forumUser = userList != null && userList.size() >0 ? userList.get(0):null;
		
		if (forumUser == null) {
			model.addAttribute("msg", false);
		} else {
			// 修改
			if (forumUserId != null) {
				Forumuser user  = forumUserService.selectByUserId(forumUserId);
				if (user != null
						&& name.equals(user.getUsername())) {
					model.addAttribute("msg", false);
				} else {
					model.addAttribute("msg", true);
				}
			} else {
				// 新增
				model.addAttribute("msg", true);
			}
		}
		return "json";
	}
}
