package com.nuoshi.console.web.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.nuoshi.console.service.MessageService;

/**
 * @author miaozhuang
 * 
 */
@Controller
public class MsgSendController extends BaseController {
	@Resource
	MessageService messageService;

	/**
	 * 返回发送站内信页面
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/sendmsg", method = RequestMethod.GET)
	public String msg(Model model, HttpServletRequest request) {
		return "tiles:msg.send";
	}
	
	/**
	 * 返回发送站内信页面
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/sendmsg/send")
	public String send(Model model, HttpServletRequest request) {
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		String agentIds = request.getParameter("agentIds");
		try {
			messageService.sendSysMessage(title, content, agentIds);
			model.addAttribute("result", "success");
		} catch (Exception e) {
			model.addAttribute("result", "fail");
			model.addAttribute("error", e.getMessage());
		}
		
		return "json";
	}


}
