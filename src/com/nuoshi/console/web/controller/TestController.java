package com.nuoshi.console.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nuoshi.console.common.Resources;
import com.nuoshi.console.common.util.MessageResolver;
import com.nuoshi.console.common.util.SmcConst;
import com.nuoshi.console.domain.user.User;
import com.nuoshi.console.service.UserService;
import com.nuoshi.console.web.session.SessionUser;

/**
 * <b>www.taofang.com</b> <b>function:</b>
 * 
 * @author lizhenmin
 * @createDate 2011 Jul 21, 2011 10:35:22 AM
 * @email lizm@taofang.com
 * @version 1.0
 */
/**
 * 测试类
 * 
 * @param admin
 * @return
 */
@Controller
@RequestMapping(value = "/test")
public class TestController extends BaseController{

	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "/jquery/test")
	public String jqueryTest() {

		return "tiles:test.jquery_test";
	}
	@RequestMapping(value = "/validation/test")
	public String validationTest() {

		return "tiles:test.validation";
	}
	@RequestMapping(value = "/json/test")
	public String json(Model model,HttpSession session) {
		SessionUser sessionUser  = (SessionUser) session.getAttribute(SmcConst.SESSION_USER);
		List<User> ls= userService.getAllByPage();
		model.addAttribute("sessionUser", sessionUser);
		model.addAttribute("userList",ls);
		
		return "json";
	}
	@RequestMapping(value = "/date/test")
	public String dateTest(Model model,HttpSession session) {
		
		
		return "/test/date";
	}
	@RequestMapping(value = "/resources/test")
	public String messages(Model model,HttpServletRequest request ,HttpSession session) {
		model.addAttribute(SmcConst.MESSAGE, MessageResolver.getMessage(request, "message.test"));
		String testInfo = Resources.getString("test.application");
		model.addAttribute("testInfo",testInfo);
		
		return "tiles:test.resource";
	}
}
