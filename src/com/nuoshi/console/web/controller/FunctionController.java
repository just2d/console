package com.nuoshi.console.web.controller;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.nuoshi.console.common.util.ZtreeHelper;
import com.nuoshi.console.domain.user.Function;
import com.nuoshi.console.service.FunctionService;

/**
 *<b>www.taofang.com</b>
 * <b>function:</b>
 * @author lizhenmin
 * @createDate 2011 Jul 21, 2011 10:35:22 AM
 * @email lizm@taofang.com
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/function")
@SuppressWarnings("unchecked")
public class FunctionController extends BaseController{
	
	@Autowired
	private FunctionService functionService;
	/**
	 * 显示用户列表
	 * 
	 * @param admin
	 * @return
	 */
	@RequestMapping(value = "/list")
	public String list(Model model) {
		List<Function> ls= functionService.getAllByPage();
		model.addAttribute("functionList",ls);
		return "tiles:function.list";
	}
	/**
	 * 显示功能列表
	 * 
	 * @param admin
	 * @return
	 */
	@RequestMapping(value = "/main")
	public String main(Model model) {
		return "tiles:function.main";
	}
	/**
	 * 显示功能列表
	 * 
	 * @param admin
	 * @return
	 */
	@RequestMapping(value = "/menu")
	public String menu(Model model) {
		List<Function> ls= functionService.getAll();
		model.addAttribute("ZtreeNodes",ZtreeHelper.getZtreeNodes2(ls));
		return "jsp:/user/function/menu";
	}
	/**
	 * 显示功能列表
	 * 
	 * @param admin
	 * @return
	 */
	@RequestMapping(value = "/sub/{id}")
	public String sub(Model model,@PathVariable("id") int id) {
		Function parent = functionService.selectFunctionById(id);
		List<Function> functionList= functionService.getAllSubByPage(id);
		model.addAttribute("parent",parent);
		model.addAttribute("functionList",functionList);
		return "jsp:/user/function/list";
	}
	@RequestMapping(value = "/searchFunction", method = RequestMethod.POST)
	public String  searchFunction(Model model, HttpServletRequest request) {
		String parentId = request.getParameter("parentId");
		Function parent = functionService.selectFunctionById(Integer.parseInt(parentId));
		String paraFunctioname = request.getParameter("paraFunctionName");
		StringBuffer condition = new StringBuffer();
		HashMap params = new HashMap<String, String>();
		if(StringUtils.isNotBlank(paraFunctioname)){
			paraFunctioname = paraFunctioname.trim();
			condition.append(" and  name like '%"+paraFunctioname+"%'" );
		}
		if (StringUtils.isNotBlank(condition.toString())) {
			params = new HashMap<String, Object>();
			params.put("condition", condition);
		}
		params.put("parentId", parentId);
		List<Function> functionList = functionService.getAllSearchFunctionByPage(params);
		model.addAttribute("parent",parent);
		model.addAttribute("functionList",functionList);
		model.addAttribute("searchText", paraFunctioname);
		return "jsp:/user/function/list";
	}
	
	// 申请修改菜单信息
	@RequestMapping(value = "/edit/{id}")
	public String modifyInfo(Model model,HttpServletRequest request,HttpSession session, @PathVariable("id") int id) throws Exception {
		Function function = functionService.selectFunctionById(id);
		if(function==null){
			model.addAttribute("error", "查找失败");
		}
		model.addAttribute("functions", function);
		return "json";
	}
	// 提交修改 信息
	@RequestMapping(value = "/edit/save")
	public String save(@ModelAttribute("function") Function function,HttpSession session)
			throws Exception {
		functionService.updateFunctionInfo(function);
		return "redirect:/function/sub/"+function.getParentId();
	}
	
	@RequestMapping(value = "/addFunction", method = RequestMethod.POST)
	public String addFunction(@ModelAttribute("function") Function function,HttpSession session) {
		
		functionService.addFunction(function);
		return "redirect:/function/sub/"+function.getParentId();
	}
	
	//删除角色
	@RequestMapping(value = "/delete")
	public String delFunction(@RequestParam("id")int id,@RequestParam("parentId")int parentId){
		functionService.delFunction(id);
		return "redirect:/function/sub/"+parentId;
	}
	@RequestMapping(value = "/checkName")
	public String checkName(Model model, HttpSession session,
			HttpServletRequest request) {
		String name = request.getParameter("name");
		String id = request.getParameter("id");
		Function function = null ;
		if(id!=null){
			  function = functionService.getFunctionByName(name, Integer.parseInt(id));
		}else{
			 function = functionService.getFunctionByName(name,null);
		}
		if(function!=null){
			model.addAttribute("error", " 名称已经存在");
		}else{
			model.addAttribute("success", "success");
		}
		
		return "json";
	}
	
}
