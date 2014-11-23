package com.nuoshi.console.web.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nuoshi.console.common.util.StringFormat;
import com.nuoshi.console.domain.stat.Feedback;
import com.nuoshi.console.service.UserFeedbackService;

@Controller
@RequestMapping(value = "/feedback")
public class UserFeedbackController extends BaseController {
	
	@Resource
	private UserFeedbackService userFeedbackService; 
	
	@RequestMapping(value = "/add")
	public void addUserFeedback(HttpServletRequest request, HttpServletResponse response, Feedback feedback) {
		String msg;
		String callback = request.getParameter("callback");
		String sCallback =  request.getParameter("scriptcallback");
		if(!StringFormat.isNotBlank(feedback.getFeedback())) {
			msg = "请输入您对淘房网搜索的建议！";
		} else {
			try {
				int result = userFeedbackService.addUserFeedback(feedback);
				if(result > 0) {
					msg = "success";
				} else {
					msg = "fail";
				}
			} catch (Exception e) {
				msg = "fail";
				e.printStackTrace();
			}
		}
		
		msg = "'"+msg+"'";
		String responseData = "";
		if(callback != null){
			responseData = callback + "(" + msg +")";
		}else if(sCallback !=null){
			responseData = "<script>"+sCallback+"("+msg+")</script>";
		}else{
			responseData = msg;
		}
		sentResponseData(response, responseData);
	}
}
