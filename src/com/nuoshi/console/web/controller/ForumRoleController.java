package com.nuoshi.console.web.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.nuoshi.console.domain.bbs.Function;
import com.nuoshi.console.domain.bbs.Role;
import com.nuoshi.console.service.ForumRoleService;
import com.nuoshi.console.service.ForumUserService;

@Controller
@RequestMapping(value = "/forum/role")
public class ForumRoleController extends BaseController{
	
	@Resource
	private ForumRoleService forumRoleService;
	@Resource
	private ForumUserService forumUserService;
	
	/**
	 * 显示用户列表
	 * 
	 * @param admin
	 * @return
	 */
	@RequestMapping(value = "/list")
	public String list(Model model) {
		List<Role> ls= forumRoleService.getAllRoleByPage();
		List<Function> functions=forumRoleService.getFunctionAll();
		
		model.addAttribute("functions", functions);
		model.addAttribute("roleList",ls);
		
		return "tiles:forumuser.role";
	}
	
	
	
	@RequestMapping(value = "/searchRole")
	public String  searchRole(@RequestParam("condition")String  condition,Model model, HttpServletRequest request, HttpServletResponse response) {
		String params=null;
		if(StringUtils.isNotBlank(condition)){
			params="%"+condition+"%" ;
		}
		
		List<Role> roleList = forumRoleService.selectByconditionByPage(params);
		model.addAttribute("roleList",roleList);
		model.addAttribute("searchText", condition);
		return "tiles:forumuser.role";
	}
	@RequestMapping(value = "/checkName")
	public String checkName(Model model, HttpSession session,
			HttpServletRequest request) {
		String name = request.getParameter("name");
		String id = request.getParameter("id");
		Role role = null ;
		if(id!=null){
			  role = forumRoleService.selectRoleByName(name,Integer.parseInt(id));
		}else{
			  role = forumRoleService.selectRoleByName(name,null);
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
			  role = forumRoleService.getRoleByCode(code,Integer.parseInt(id));
		}else{
			  role = forumRoleService.getRoleByCode(code,null);
		}
		if(role!=null){
			model.addAttribute("error", "角色编号已经存在");
		}else{
			model.addAttribute("success", "success");
		}
		
		return "json";
	}

	// 申请修改个人信息
	@RequestMapping(value = "/edit/{id}")
	public String modifyInfo(Model model,HttpServletRequest request,HttpSession session, @PathVariable("id") int id) throws Exception {
		Role role = forumRoleService.selectRoleById(id);
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
		forumRoleService.updateRoleInfo(role);
		if(role != null && role.getStatus() == 0){
			forumUserService.updateByRole(role.getId());
			forumRoleService.deleteFunctions(String.valueOf(role.getId()));
		}
		return "redirect:/forum/role/list";
	}
	
	@RequestMapping(value = "/addRole", method = RequestMethod.POST)
	public String addRole(@ModelAttribute("role") Role role,HttpSession session) {
		
		forumRoleService.addRole(role);
		return "redirect:/forum/role/list";
	}
	
	//删除角色
	@RequestMapping(value = "/delete")
	public String delRole(Model model,@RequestParam("id")int id){

		forumRoleService.delRole(id);
		forumUserService.updateByRole(id);
		forumRoleService.deleteFunctions(String.valueOf(id));
	
		return "redirect:/forum/role/list";
	}

	@RequestMapping(value = "/rolefunction")
	public String roleFunction(Model model,@RequestParam("roleId") Integer roleId){
		List<Integer> functionIds=forumRoleService.getFunctionsByRoleId(roleId);
		model.addAttribute("roleFunctions", functionIds);
		return "json";
	}
	@RequestMapping(value = "/power")
	public String power(Model model,@RequestParam("ids")String ids,@RequestParam("roleid")String roleid){

		if(StringUtils.isBlank(ids)){
			model.addAttribute("error", "要修改的数据不能为空!");
			return "json";
		}
		forumRoleService.delRoleFunctions(Integer.parseInt(roleid));
		if(ids!=null&&ids.length()>0){
			String[] id  = ids.split(",");
			for (int i = 0; i < id.length; i++) {
				forumRoleService.saveFunctions(roleid, id[i]);
			}
		}
		model.addAttribute("info", "success");
		return "json";
	}
	
}
