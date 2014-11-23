package com.nuoshi.console.web.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.nuoshi.console.common.util.SmcConst;
import com.nuoshi.console.domain.user.Role;
import com.nuoshi.console.domain.user.User;
import com.nuoshi.console.service.RoleService;
import com.nuoshi.console.service.UserService;
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
@RequestMapping(value = "/user")
public class UserController extends BaseController{
	
	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;
	/**
	 * 显示用户列表
	 * 
	 * @param admin
	 * @return
	 */
	@RequestMapping(value = "/list")
	public String list(Model model) {
		List<Role> roles = roleService.getAll();
		List<User> ls= userService.getAllByPage();
		model.addAttribute("userList",ls);
		model.addAttribute("roles",roles);
		return "tiles:user.list";
	}
	
	@RequestMapping(value = "/assignRole/{id}")
	public String assignRole(Model model, HttpSession session,
			HttpServletRequest request,@PathVariable("id") Integer id ) {

		User user = userService.selectUserById(id) ;
		List<RoleView>  roles = roleService.selectAllByUserId(id);
		List<Role>  allRoles = roleService.getAll();
		for (Role role : allRoles) {
			if(!checkRole(role.getId(),roles)){
				RoleView rv = new RoleView();
				BeanUtils.copyProperties(role, rv);
				roles.add(rv);
			}
		}
		model.addAttribute("user",user);
		model.addAttribute("roles",roles);
		return "tiles:user.assignRole";
	}
	private boolean checkRole(Integer id,List<RoleView> roles){
		for (Role role : roles) {
			if(role.getId()==id){
				return true;
			}
		}
		return false;
	}
	@RequestMapping(value = "/assignRole/save")
	public String assignRoleSave(Model model, HttpSession session,
			HttpServletRequest request ) {
		String userId = request.getParameter("id");
		
		String[] roleIds = request.getParameterValues("ids");
		userService.deleteRoles(userId );
		if(roleIds!=null){
			for (String id : roleIds) {
				if(StringUtils.isNotBlank(id)){
					userService.saveRoles(userId,id);
				}
			}
		}
		
		return "redirect:/user/list";
	}
	/**
	 * 后台管理首页
	 * 
	 * @param admin
	 * @return
	 */
	@RequestMapping(value = "/index")
	public String index(Model model) {
		return "tiles:user.index";
	}
	// 申请修改个人信息
	@RequestMapping(value = "/modifyInfo")
	public String modifyInfo(Model model,HttpServletRequest request,HttpSession session) throws Exception {
		SessionUser sessionUser = (SessionUser) session.getAttribute(SmcConst.SESSION_USER);
		int id = sessionUser.getUser().getId();
		User user = userService.selectUserById(id);
		model.addAttribute("user", user);
		return "tiles:user.modifyInfo";
	}
	// 提交修改个人信息
	@RequestMapping(value = "/modifyInfo/save")
	public String save(@ModelAttribute("user") User user,HttpSession session)
			throws Exception {
		userService.updateUserInfo(user);
		user = userService.selectUserById(user.getId());
		SessionUser sessionUser = (SessionUser) session.getAttribute(SmcConst.SESSION_USER);
		sessionUser.setUser(user);
		return "redirect:/login/index";
	}
	
	@RequestMapping(value = "/addUser", method = RequestMethod.POST)
	public String addUser(@RequestParam("addUserInfo")String addUserInfo,
			HttpServletRequest request) {
		if(addUserInfo == null || "".equals(addUserInfo)) {
			return "redirect:/user/list";
		}
		String[] para = addUserInfo.split("_");
		User user = new User();
		user.setChnName(para[0]);
		user.setPassword(para[1]);
		user.setEmail(para[2]);
		user.setMobile(para[3]);
		user.setSex(Integer.parseInt(para[4]));
		user.setUserName(para[5]);
		user.setEntryDateTime(new Date());
		userService.addUser(user);
		
		String[] roleIds = request.getParameterValues("roleIds");

		if(roleIds!=null){
			for (String id : roleIds) {
				if(StringUtils.isNotBlank(id)){
					userService.saveRoles(user.getId()+"",id);
				}
			}
		}
		
		return "redirect:/user/list";
	}
	
	@RequestMapping(value = "/modifyUserInfo")
	public String modifyUserInfo(Model model, HttpServletRequest request) throws Exception {
		String id = request.getParameter("id");
		User user = userService.selectUserById(Integer.parseInt(id));
		model.addAttribute("user", user);
		return "tiles:user.modifyUserInfo";
	}
	
	@RequestMapping(value = "/confirmModifyUser")
	public String confirmModifyUser(@ModelAttribute("user") User user) throws Exception {
		userService.updateUserInfo(user);
		return "redirect:/user/list";
	}
	
	//删除用户
	@RequestMapping(value = "/delUser")
	public String delUser(@RequestParam("id")int id) throws Exception {
		userService.delUser(id);
		return "redirect:/user/list";
	}
	
	@RequestMapping(value = "/searchUser", method = RequestMethod.POST)
	public String  searchUser(Model model, HttpServletRequest request) {
		String paraUserName = request.getParameter("paraUserName");
		List<User> users = userService.getAllSearchUserByPage("%" + paraUserName + "%");
		model.addAttribute("userList",users);
		model.addAttribute("searchText", paraUserName);
		return "tiles:user.list";
	}
	
}
