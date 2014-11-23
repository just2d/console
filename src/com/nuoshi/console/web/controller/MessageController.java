package com.nuoshi.console.web.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.gson.Gson;
import com.nuoshi.console.common.JsonResult;
import com.nuoshi.console.common.Resources;
import com.nuoshi.console.domain.user.TFUser;
import com.nuoshi.console.service.AgentMasterService;
import com.nuoshi.console.service.MessageService;
import com.nuoshi.console.service.TaofangUserService;

@Controller
@RequestMapping("/msg")
public class MessageController extends BaseController {

	@Resource
	AgentMasterService agentMasterService;

	@Resource
	TaofangUserService taofangUserService;

	@Resource
	MessageService messageService;
	
	/**
	 * 审核小区通过后发送系统消息
	 * @param content
	 * @param title
	 * @param userId
	 */
	@RequestMapping("/sendestatePassMsg")
	public void sendAuthEstatePassMsg(@RequestParam(value = "userId", required = false) int userId,
			@RequestParam(value = "estateName", required = false) String estateName, HttpServletResponse response) {
		TFUser tfUser = taofangUserService.getUserByUserId(userId);
		if (tfUser != null) {
			String title = Resources.getString("estate_auth_result_pass_title");
			String content = Resources.getString("estate_pass_msg_content").replace("【小区名称】", estateName);
			boolean flag = messageService.sendMessageSys(title, content, tfUser);
			JsonResult result = new JsonResult();
			result.setResult(flag);
			sentResponseData(response, new Gson().toJson(result).toString());
		}
	}

	/**
	 * 删除小区通过后发送系统消息
	 * @param content
	 * @param title
	 * @param userId
	 */
	@RequestMapping("/sendestateDelMsg")
	public void sendAuthEstateDelMsg(@RequestParam(value = "createUserId", required = false) int userId,
			@RequestParam(value = "content", required = false) String content, HttpServletResponse response) {
		TFUser tfUser = taofangUserService.getUserByUserId(userId);
		JsonResult result = new JsonResult();
		if (tfUser != null) {
			String title = Resources.getString("estate_auth_result_pass_title");
			boolean flag = messageService.sendMessageSys(title, content, tfUser);
			result.setResult(flag);
		} else {
			result.setResult(false);
		}
		sentResponseData(response, new Gson().toJson(result).toString());
	}

}
