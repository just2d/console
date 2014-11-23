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

import com.nuoshi.console.common.util.SmcConst;
import com.nuoshi.console.common.util.ZtreeHelper;
import com.nuoshi.console.domain.user.Function;
import com.nuoshi.console.domain.user.Role;
import com.nuoshi.console.service.FunctionService;
import com.nuoshi.console.service.RoleService;
import com.nuoshi.console.view.RoleView;
import com.nuoshi.console.web.session.SessionUser;

/**
 *<b>www.taofang.com</b>
 * <b>function:</b>
 * @author lizhenmin
 * @createDate 2011 Jul 21, 2011 10:35:22 AM
 * @email lizm@taofang.com
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/role")
@SuppressWarnings("unchecked")
public class RoleController extends BaseController{
	
	@Autowired
	private RoleService roleService;
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
		List<Role> ls= roleService.getAllByPage();
		model.addAttribute("roleList",ls);
		return "tiles:role.list";
	}
	@RequestMapping(value = "/searchRole", method = RequestMethod.POST)
	public String  searchRole(Model model, HttpServletRequest request) {
		String paraRoleName = request.getParameter("paraRoleName");
		StringBuffer condition = new StringBuffer();
		HashMap params = null;
		if(StringUtils.isNotBlank(paraRoleName)){
			paraRoleName = paraRoleName.trim();
			condition.append(" and r.name like '%"+paraRoleName+"%'" );
		}
		if (StringUtils.isNotBlank(condition.toString())) {
			params = new HashMap<String, Object>();
			params.put("condition", condition);
		}
		List<Role> roleList = roleService.getAllSearchRoleByPage(params);
		model.addAttribute("roleList",roleList);
		model.addAttribute("searchText", paraRoleName);
		return "tiles:role.list";
	}
	@RequestMapping(value = "/checkName")
	public String checkName(Model model, HttpSession session,
			HttpServletRequest request) {
		String name = request.getParameter("name");
		String id = request.getParameter("id");
		Role role = null ;
		if(id!=null){
			  role = roleService.getRoleByName(name,Integer.parseInt(id));
		}else{
			  role = roleService.getRoleByName(name,null);
		}
		if(role!=null){
			model.addAttribute("error", "角色名称已经存在");
		}else{
			model.addAttribute("success", "success");
		}
		
		return "json";
	}
	@RequestMapping(value = "/checkCode")
	public String checkCode(Model model, HttpSession session,
			HttpServletRequest request) {
		String code = request.getParameter("code");
		String id = request.getParameter("id");
		Role role = null ;
		if(id!=null){
			  role = roleService.getRoleByCode(code,Integer.parseInt(id));
		}else{
			  role = roleService.getRoleByCode(code,null);
		}
		if(role!=null){
			model.addAttribute("error", "角色编号已经存在");
		}else{
			model.addAttribute("success", "success");
		}
		
		return "json";
	}
	@RequestMapping(value = "/authority/{id}")
	public String authority(Model model, HttpSession session,
			HttpServletRequest request,@PathVariable("id") Integer id ) {

		Role role = roleService.selectRoleById(id) ;
		
		List<Function> allFunctions = functionService.getAll();
		List<Function> roleFunctions = functionService.selectFunctionByRoleId(id);
		
		model.addAttribute("ZtreeNodes",ZtreeHelper.getZtreeNodes(allFunctions,roleFunctions));
		model.addAttribute("role",role);
		return "tiles:role.authority";
	}
	@RequestMapping(value = "/authority/save")
	public String authoritySave(Model model, HttpSession session,
			HttpServletRequest request ) {
		String roleId = request.getParameter("id");
		
		String functionIds = request.getParameter("ids");
		roleService.deleteFunctions(roleId );
		String[] ids = functionIds.split("_");
		for (String id : ids) {
			if(StringUtils.isNotBlank(id)){
				roleService.saveFunctions(roleId,id);
			}
		}
		
		
		return "redirect:/role/list";
	}
	// 申请修改个人信息
	@RequestMapping(value = "/edit/{id}")
	public String modifyInfo(Model model,HttpServletRequest request,HttpSession session, @PathVariable("id") int id) throws Exception {
		Role role = roleService.selectRoleById(id);
		if(role==null){
			model.addAttribute("error", "查找失败");
		}
		model.addAttribute("role", role);
		return "json";
	}
	// 提交修改个人信息
	@RequestMapping(value = "/edit/save")
	public String save(@ModelAttribute("role") Role role,HttpSession session)
			throws Exception {
		roleService.updateRoleInfo(role);
		return "redirect:/role/list";
	}
	
	@RequestMapping(value = "/addRole", method = RequestMethod.POST)
	public String addRole(@ModelAttribute("role") Role role,HttpSession session) {
		
		roleService.addRole(role);
		return "redirect:/role/list";
	}
	
	//删除角色
	@RequestMapping(value = "/delete")
	public String delRole(Model model,@RequestParam("id")int id,HttpSession session){
		SessionUser sessionUser  = (SessionUser) session.getAttribute(SmcConst.SESSION_USER);
		boolean canDelete = true;
		List<RoleView> roles = roleService.selectAllByUserId((sessionUser.getUser().getId()));
		for (RoleView roleView : roles) {
			if(id == roleView.getId()){
				canDelete = false;
			}
		}
		if(!canDelete){
			model.addAttribute("message", "不能删除自己所在的角色!");
			return "jsp:/msg";
		}
		roleService.delRole(id);
		return "redirect:/role/list";
	}
	
	
}
