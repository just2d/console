package com.nuoshi.console.web.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.nuoshi.console.common.Resources;
import com.nuoshi.console.common.util.SmcConst;
import com.nuoshi.console.domain.agent.AgentSearch;
import com.nuoshi.console.domain.user.TFUser;
import com.nuoshi.console.service.TFUserManageService;

/**
 * @author miaozhuang
 * 
 */
@Controller
@RequestMapping(value = "/tfuserManage")
public class TFUserManageController extends BaseController {
	@Resource
	private TFUserManageService tfuserManageService;
	

	/**
	 * @param model
	 * @return 个人用户管理页面
	 */
	@RequestMapping(value = "/list")
	public String listAgent(Model model,AgentSearch agentSearch) {
		List<TFUser> tfuserList = tfuserManageService.getTFUserByCondition(agentSearch);
		model.addAttribute("tuserList", tfuserList);
		return "tiles:tfuser.list";
	}

	
	/**
	 * @param model
	 * @param id
	 *            个人用户id
	 * @return 删除个人用户的操作结果
	 */
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public String deleteAgent(Model model, HttpServletRequest request, @PathVariable("id") String id) {
		String msg = "删除个人用户成功";
		try {
			String[] idArr = id.split(",");
			for (String low : idArr) {
				int delId = Integer.parseInt(low);
				tfuserManageService.deleteTFUser(delId);
			}
		} catch (Exception e) {
			msg = "删除个人用户失败";
			e.printStackTrace();
		}
		model.addAttribute(SmcConst.MESSAGE, msg);
		return "json";
	}
	
}
