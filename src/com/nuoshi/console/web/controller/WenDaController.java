package com.nuoshi.console.web.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nuoshi.console.common.Globals;
import com.nuoshi.console.domain.audit.WenDaAuditTask;
import com.nuoshi.console.domain.topic.Locale;
import com.nuoshi.console.domain.user.User;
import com.nuoshi.console.domain.wenda.Answer;
import com.nuoshi.console.domain.wenda.AnswerCondition;
import com.nuoshi.console.domain.wenda.Question;
import com.nuoshi.console.domain.wenda.QuestionCondition;
import com.nuoshi.console.service.AuditService;
import com.nuoshi.console.service.LocaleService;
import com.nuoshi.console.service.WenDaListService;
import com.nuoshi.console.service.WenDaVerifyService;
import com.nuoshi.console.web.session.SessionUser;

@Controller
@RequestMapping(value = "/wenda")
public class WenDaController {
	@Resource
	WenDaVerifyService wenDaVerifyService;
	@Resource
	AuditService auditService;
	@Resource
	WenDaListService wenDaListService;
	/**
	 * 获取审核列表
	 */
	@RequestMapping(value = "/verify/{type}")
	public String wenDaAuditList(Model model, HttpSession session, HttpServletRequest request,@PathVariable("type")Integer type) {
		Integer auditCount=Globals.AUDIT_WEN_DA_COUNT;
		//取到审核人
		SessionUser sessionUser = (SessionUser) session.getAttribute("sessionUser");
		User user=null;
		Integer userId = 0;
		if(sessionUser!=null){
			user=sessionUser.getUser();
			userId = user.getId();
		}
		String cityIdStr = request.getParameter("cityid");
		Integer cityId =-1;
//		Integer cityId = Globals.BEIJINGID;
		if (StringUtils.isNotBlank(cityIdStr)) {
			cityId = Integer.valueOf(cityIdStr);
		}
		if(type==null){
			model.addAttribute("auditTaskList", new ArrayList<WenDaAuditTask>());
			model.addAttribute("cityid", cityId);
			return "json";
		}
		List<WenDaAuditTask> auditTaskList=null;
		try {
			// 获取审核列表
			auditTaskList = auditService.getWenDaTask(auditCount,userId,type,cityId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 将简化版的城市List放入application中.
		ServletContext application = session.getServletContext();
		Map<Integer, Locale> map;
		if (null == application.getAttribute("simpleLocaleMap")) {
			map = LocaleService.getLocalesPool();
			for (Iterator<Entry<Integer, Locale>> iterator = map.entrySet().iterator(); iterator.hasNext();) {
				Map.Entry<Integer, Locale> entry = iterator.next();
				Locale locale = entry.getValue();
				if (locale.getParentId() != 0) {
					iterator.remove();
				}
			}
			application.setAttribute("simpleLocaleMap", map);
		}
		model.addAttribute("auditTaskList", auditTaskList);
		model.addAttribute("cityid", cityId);
		model.addAttribute("type", type);
		return "tiles:wenda.verify";
	}
	@RequestMapping(value = "/doverify/{type}")
	public String doVerify(Model model, HttpSession session, HttpServletRequest request,@PathVariable("type")Integer type){
		//取到审核人
//		SessionUser sessionUser = (SessionUser) session.getAttribute("sessionUser");
//		User user=null;
//		Integer userId = 0;
//		if(sessionUser!=null){
//			user=sessionUser.getUser();
//			userId = user.getId();
//		}
		String cityIdStr = request.getParameter("cityid");
		Integer cityId =-1;
		if (StringUtils.isNotBlank(cityIdStr)) {
			cityId = Integer.valueOf(cityIdStr);
		}
		if(type==null){
			model.addAttribute("auditTaskList", new ArrayList<WenDaAuditTask>());
			model.addAttribute("cityid", cityId);
			model.addAttribute("result",false);
			model.addAttribute("message","审核类型不正确!");
			return "json";
		}
		List<Integer> rejectIdList=new ArrayList<Integer>();
		List<Integer> passIdList=new ArrayList<Integer>();
		String[] idArr = request.getParameterValues("idAndResult");
		final String separator = "#_#";
		if(idArr==null||idArr.length<=0){
			model.addAttribute("cityid", cityId);
			model.addAttribute("result",false);
			model.addAttribute("message","审核失败！返回后的数据为空！");
			return "json";
		}
		for (int i = 0; i < idArr.length; i++) {
			String idAndResult = idArr[i];
			Integer isExist = idAndResult.indexOf(separator);
			// 没有分隔符,则表示是通过的
			if (isExist == -1) {
				if (StringUtils.isBlank(idAndResult)) {
					continue;
				}
				passIdList.add(Integer.valueOf(idAndResult));
			}
			if (isExist != -1) {
				String[] divisionStr = idAndResult.split(separator);
				String idStr = divisionStr[0];
				if (StringUtils.isBlank(idStr)) {
					continue;
				}
				rejectIdList.add(Integer.valueOf(idStr));
			}
		}
		auditService.auditWenDa(passIdList , rejectIdList , type);
		model.addAttribute("cityid", cityId);
		return "redirect:/wenda/verify/"+type;
	}
	/**
	 * 获取问答待审核的数量
	 * @param type 
	 * @param model
	 * @return
	 * @author wangjh
	 * May 3, 2012 4:23:29 PM
	 */
	@RequestMapping(value = "/getRemainingNum/{type}")
	public String getRemainingNum(
			@PathVariable("type") Integer type, Model model) {
		int count=0;
		if(type!=null){
			count=wenDaVerifyService.getRemainingNumByType(type);
		}
		model.addAttribute("count", count);
		return "json";
	}
	
	@RequestMapping(value = "/emptyWenDaTask")
	public String emptyWenDaAuditTask(HttpSession session,Model model){
		SessionUser sessionUser = (SessionUser) session.getAttribute("sessionUser");
		User user = null;
		if(sessionUser==null){
			model.addAttribute("result",false);
			model.addAttribute("message","未登录！");
			return "json";
		}
		user = sessionUser.getUser();
		if(user==null){
			model.addAttribute("result",false);
			model.addAttribute("message","清空审核数据出错，当前未登录！");
		}
		Integer id=user.getId();
		if(id==null){
			model.addAttribute("result",false);
			model.addAttribute("message","清空审核数据出错，当前用户不存在！");
		}
		List<Integer> list=new ArrayList<Integer>();
		list.add(id);
		auditService.emptyWenDaAuditTask(list, 20);
		model.addAttribute("result",true);
		return "json";
	}
	/**
	 * 查询回答列表
	 * @param model
	 * @param condition 条件
	 * @return
	 * @author wangjh
	 * Oct 29, 2012 4:10:57 PM
	 */
	@RequestMapping(value = "/queryAnswerList")
	public String answerList(Model model,@ModelAttribute("condition") AnswerCondition condition,HttpServletRequest request){
		String bDate=request.getParameter("beginD");
		String eDate=request.getParameter("endD");
		condition.setBeginPubTime(bDate);
		condition.setEndPubTime(eDate);
		List<Answer> answerList=wenDaListService.getAnswerList(condition);
		model.addAttribute("statusMap", Globals.ANSWER_STATIC_MAP);
		model.addAttribute("answerList", answerList);
		return "tiles:wenda.answer.list";
	}
	/**
	 * 查询问题列表
	 * @param session
	 * @param model
	 * @param condition 条件
	 * @return
	 * @author wangjh
	 * Oct 29, 2012 4:10:57 PM
	 */
	@RequestMapping(value = "/queryQuestionList")
	public String questionList(Model model,@ModelAttribute("condition") QuestionCondition condition,HttpServletRequest request){
		String bDate=request.getParameter("beginD");
		String eDate=request.getParameter("endD");
		condition.setBeginPubTime(bDate);
		condition.setEndPubTime(eDate);
		List<Question> questionList=wenDaListService.getQuestionList(condition);
		model.addAttribute("statusMap", Globals.QUESTION_STATIC_MAP);
		model.addAttribute("solvingStatusMap", Globals.QUESTION_SOLVING_MAP);
		model.addAttribute("questionList", questionList);
		return "tiles:wenda.question.list";
	}
	@RequestMapping(value = "/reaudit/{type}/{id}")
	public String reauditWenda(Model model,@PathVariable("type")Integer type,@PathVariable("id")Integer id){
		try {
			wenDaVerifyService.reaudit(type, id);
		} catch (Exception e) {
			model.addAttribute("result",1);
			model.addAttribute("message",e.getMessage());
			return "json";
		}
		model.addAttribute("result",0);
		return "json";
	}
	
	
	
}
