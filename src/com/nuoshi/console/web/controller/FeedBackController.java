package com.nuoshi.console.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.gson.Gson;
import com.nuoshi.console.common.util.SmcConst;
import com.nuoshi.console.domain.feedback.FeedBack;
import com.nuoshi.console.service.FeedBackService;
import com.nuoshi.console.web.session.SessionUser;

@Controller
@RequestMapping(value = "/feedBack")
public class FeedBackController extends BaseController {

	@Autowired
	private FeedBackService feedBackService;

	/**
	 * 列表页
	 * 
	 * @return
	 * @date 2012-1-31上午11:17:05
	 * @author dongyj
	 */
	@RequestMapping(value = "/list")
	public String listByPage(Model model, HttpServletRequest request) {
		String mobile = request.getParameter("mobile");
		String flags = request.getParameter("flags");
		model.addAttribute("flags", flags);
		model.addAttribute("mobile", mobile);
		List<FeedBack> feedBacks = new ArrayList<FeedBack>();
		feedBacks = feedBackService.getList(mobile,flags);
		model.addAttribute("list", feedBacks);
		return "tiles:feedBack.list";
	}

	/**
	 * 删除一条数据
	 * 
	 * @param model
	 * @param request
	 * @return
	 * @date 2012-1-31下午1:47:47
	 * @author dongyj
	 */
	@RequestMapping(value = "/delOne")
	public String delOne(Model model, HttpServletRequest request) {
		String id = request.getParameter("id");
		try {
			feedBackService.delOne(id);
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return "json";
	}

	/**
	 * 取得一条数据
	 * 
	 * @param model
	 * @param request
	 * @return
	 * @date 2012-1-31下午3:17:56
	 * @author dongyj
	 */
	@RequestMapping(value = "/getOne")
	public String getOne(Model model, HttpServletRequest request) {
		String id = request.getParameter("id");
		FeedBack feedBack = new FeedBack();
		try {
			feedBack = feedBackService.getOne(id);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			model.addAttribute("error", "没有取得数据!");
			return "json";
		}
		Gson gson = new Gson();
		String res = gson.toJson(feedBack);
		model.addAttribute("res", res);
		return "json";
	}

	/**
	 * 修改或者添加回答
	 * 
	 * @param model
	 * @param request
	 * @return
	 * @date 2012-1-31下午2:36:01
	 * @author dongyj
	 */
	@RequestMapping(value = "/changeReply")
	public String changeReply(Model model, HttpServletRequest request) {
		HttpSession httpSession = request.getSession();
		SessionUser sessionUser = (SessionUser) httpSession.getAttribute(SmcConst.SESSION_USER);
		String id = request.getParameter("id");
		String value = request.getParameter("value");
		if (StringUtils.isNotBlank(id)) {
			try {
				feedBackService.updateOne(id, value, sessionUser);
			} catch (Exception e) {
				e.printStackTrace();
				log.error(e.getMessage());
				model.addAttribute("error", e.getMessage());
			}
		}
		return "json";
	}
}
