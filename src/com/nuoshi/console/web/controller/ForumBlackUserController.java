package com.nuoshi.console.web.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.nuoshi.console.common.Resources;
import com.nuoshi.console.domain.bbs.ForumBlackUser;
import com.nuoshi.console.domain.user.TFUser;
import com.nuoshi.console.service.ForumBlackUserService;
import com.nuoshi.console.service.MessageService;
import com.nuoshi.console.service.TaofangUserService;

/**
 * bbs 黑名单
 * @author ningtao
 *
 */
@Controller
@RequestMapping("/forum/black")
public class ForumBlackUserController extends BaseController {
	@Resource
	private ForumBlackUserService forumBlackUserService;
	@Resource
	private TaofangUserService taofangUserService;

	@Resource
	private MessageService messageService;

	@RequestMapping("/list")
	public String userList(@RequestParam(value = "userName", required = false) String userName, Model model) {
		userName = StringUtils.trimToEmpty(userName);
		Map<String, String> paramMap = new HashMap<String, String>();
		try {
			if (StringUtils.isNotBlank(userName)) {
				userName = URLDecoder.decode(userName, "utf-8");
				paramMap.put("userName", "%" + userName + "%");
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		List<ForumBlackUser> blackUserList = forumBlackUserService.getUserListByPage(paramMap);
		model.addAttribute("blackList", blackUserList);
		model.addAttribute("userName", userName);
		return "tiles:bbs.blackuserList";

	}

	/**
	 * 加入黑名单或解锁.
	 * @param status
	 * @param userId
	 */
	@RequestMapping("/udpateStatus")
	public String updateStatus(@RequestParam("status") Integer status, @RequestParam("userId") Integer userId,
			Model model) {
		TFUser user = taofangUserService.getUserByUserId(userId);
		int result = forumBlackUserService.updateStatus(status, userId,user.getUserName());
		if (status == 1) {
			if (user != null) {
				String title=Resources.getString("bbs_blackUser_msg_title");
				String message=Resources.getString("bbs_blackUser_msg_content").replaceAll("\\[userName\\]",user.getUserName());
				messageService.sendMessageSys(title,message, user);
			}
		}
		if (result > 0) {
			model.addAttribute("success", true);
		} else {
			model.addAttribute("success", false);
		}
		return "json";
	}

	/**
	 * 添加黑名单
	 * @param status
	 * @param userId
	 */
	@RequestMapping("/addBlackUser")
	public String addStatus(@RequestParam("userInfo") String userInfo, @RequestParam("role") Integer role,@RequestParam("type") Integer type,
			Model model) {
		boolean flag=false;
		try {
			flag=forumBlackUserService.addStatus(userInfo, role, type);
		} catch (Exception e) {
			model.addAttribute("success", flag);
		}
		model.addAttribute("success", flag);
		return "json";
	}

	/**
	 * 验证用户是存在
	 * @param userName
	 * @param model
	 * @return
	 */
	@RequestMapping("/userNameValidate")
	public String validateUserExist(@RequestParam("userInfo") String userInfo, @RequestParam("type") Integer type,Model model) {
		boolean flag=true;
		try {
			flag=forumBlackUserService.isBlackUserExist(userInfo, type);
		} catch (Exception e) {
			log.error("添加黑名单时，查询用户信息出错！");
		}
		if (flag) {
			model.addAttribute("exist", true);
		} else {
			model.addAttribute("exist", false);
		}
		return "json";
	}

}
