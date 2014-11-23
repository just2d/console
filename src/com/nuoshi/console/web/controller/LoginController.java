package com.nuoshi.console.web.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.octo.captcha.service.image.ImageCaptchaService;
import com.nuoshi.console.common.util.SmcConst;
import com.nuoshi.console.domain.user.Function;
import com.nuoshi.console.domain.user.User;
import com.nuoshi.console.service.AuditService;
import com.nuoshi.console.service.FunctionService;
import com.nuoshi.console.service.RoleService;
import com.nuoshi.console.service.UserService;
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
	public class LoginController extends BaseController{
		@Autowired
		private ImageCaptchaService imageCaptchaService;
		
		@Autowired
		private UserService userService;
		
		@Autowired
		private FunctionService functionService;
		
		@Autowired
		private RoleService roleService;
		
		@Autowired
		private AuditService as;
		/**
		 * 显示管理员列表
		 * 
		 * @param admin
		 * @return
		 */
		@RequestMapping(value = "/index*" ,method = RequestMethod.GET)
		public String login() {
			
			return "redirect:login.html";
		}
		/**
		 * 访问登录页
		 * 
		 * @return
		 */
		@RequestMapping(value = "/login", method = RequestMethod.GET)
		public String loginGet() {
			return "login";
		}
		/**
		 * 用户注销退出系统
		 * 
		 * @return
		 */
		@RequestMapping(value = "/loginOut")
		public String loginOut(HttpSession session) {
			if(session!=null){
				session.invalidate();
			}
			return "jsp:login";
		}
		/**
		 * 用户没有权限
		 * 
		 * @return
		 */
		@RequestMapping(value = "/nopermission")
		public String nopermission(HttpSession session) {
			return "jsp:nopermission";
		}
		/**
		 * 用户登录超时页面
		 * 
		 * @return
		 */
		@RequestMapping(value = "/loginTimeOut")
		public String loginTimeOut(HttpSession session) {
			if(session!=null){
				session.invalidate();
			}
			return "jsp:loginTimeOut";
		}
		@InitBinder
		public void initBinder(WebDataBinder binder) {
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			binder.registerCustomEditor(Date.class, new CustomDateEditor(format, true));
		}
		/**
		 * 请求登录，验证用户
		 * 
		 * @param session
		 * @param loginname
		 * @param password
		 * @param code
		 * @return
		 */
		@RequestMapping(value = "/login", method = RequestMethod.POST)
		public String loginPost(Model model,HttpServletRequest request,HttpSession session, @RequestParam("loginname") String loginname, @RequestParam("password") String password, @RequestParam("code") String code) {
			boolean isHuman = imageCaptchaService.validateResponseForID(session.getId(), StringUtils.upperCase(code));
			User user = new User();
			user.setUserName(loginname);
			user.setPassword(password);
			User dbUser = null;
			String errInfo = "";
			if(isHuman){
				
				 dbUser = userService.verifyUser(user);
				
				
			if (dbUser!=null&&dbUser.getRole()!=null&&dbUser.getRole().indexOf("MC")>=0) {
				
				String userIP = request.getRemoteAddr();
				request.getSession(true).setAttribute("user", dbUser);
				dbUser.setLastLoginDate(new Date());
				dbUser.setOnline(1);
				dbUser.setLoginIP(userIP);
				userService.updateUserStatus(dbUser);
				} else {
					errInfo = "用户名或密码有误！";
				}
			} else {
				errInfo = "验证码输入有误！";
			}
			if(StringUtils.isNotBlank(errInfo)){
				model.addAttribute("errInfo",errInfo);
				return "jsp:login";
			}
			SessionUser sessionUser = new SessionUser();
			if(dbUser.getRole().equalsIgnoreCase("mc")){
				sessionUser.setIsSuperAdmin(true);
			}
			//获取权限
			List<Function> funcList = functionService.selectFunctionByUserId(dbUser.getId());
			sessionUser.setManager(roleService.getUserIsManager(dbUser.getId()));
			if(sessionUser.getIsSuperAdmin()) {
				sessionUser.setManager(true);
			}
			sessionUser.setRights(funcList);//设置权限
			//登录成功，在session中设置用户信息
			sessionUser.setUser(dbUser);
			sessionUser.setAuditService(as);
			session.setAttribute(SmcConst.SESSION_USER, sessionUser);
			//Test ......
			//System.out.println("############################session time set");
			//session.setMaxInactiveInterval(25);
			return "redirect:/login/index";
		}
		@RequestMapping(value = "/login/index")
		public String index(){
			return "tiles:user.index";
		}

}
