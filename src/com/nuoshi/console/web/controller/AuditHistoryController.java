package com.nuoshi.console.web.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.gson.Gson;
import com.nuoshi.console.common.Globals;
import com.nuoshi.console.common.util.SmcConst;
import com.nuoshi.console.domain.agent.RejectReason;
import com.nuoshi.console.domain.auditHistory.AuditorInfo;
import com.nuoshi.console.domain.auditHistory.HistoryInfo;
import com.nuoshi.console.domain.auditHistory.NewSubAuditHistory;
import com.nuoshi.console.domain.auditHistory.SubAuditHistory;
import com.nuoshi.console.domain.user.User;
import com.nuoshi.console.service.AuditCountService;
import com.nuoshi.console.service.AuditHistoryService;
import com.nuoshi.console.service.AuditService;
import com.nuoshi.console.service.RentVerifyService;
import com.nuoshi.console.service.RoleService;
import com.nuoshi.console.service.UserService;
import com.nuoshi.console.web.form.AgentVerifyResultForm;
import com.nuoshi.console.web.session.SessionUser;

@Controller
@RequestMapping(value = "/auditHistory")
public class AuditHistoryController extends BaseController {
	@Autowired
	private AuditHistoryService auditHistoryService;
	@Autowired
	private RentVerifyService rentVerifyService;
	@Autowired
	private AuditService auditService;
	@Autowired
	private UserService userService;
	@Autowired
	private AuditCountService auditCountService;
	@Resource
	private RoleService roleService;
	
	
	@RequestMapping(value="/reaudit/{subHistoryId}/{result}")
	public String reaudit(Model model, HttpSession session, HttpServletRequest request,
 @PathVariable("subHistoryId") String subHistoryId, @PathVariable("result") String result) {
		SessionUser sessionUser = (SessionUser) session.getAttribute("sessionUser");
		User user = null;
		if (sessionUser != null) {
			user = sessionUser.getUser();
		}
		// 复审，业务处理和记录
		try {
			String reason = request.getParameter("reason");
			HistoryInfo historyInfo = auditHistoryService.getHistoryInfoBySubId(Integer.parseInt(subHistoryId));
			if (historyInfo.getAuditStep() == 5 || historyInfo.getAuditStep() == 6) {
				AgentVerifyResultForm agentVerifyResultForm = new AgentVerifyResultForm();
				agentVerifyResultForm.setAgentId(historyInfo.getHouseId());

				// 有违规原因
				if (null != historyInfo && StringUtils.isNotBlank(reason)) {
					if (historyInfo.getAuditStep().intValue() == Globals.AUDIT_HISTORY_AGENT_HEAD_IMG) {
						agentVerifyResultForm.setHeadMsg(reason);
						agentVerifyResultForm.setHeadResult(Globals.AUDIT_RESULT_REJECT);
						agentVerifyResultForm.setIdCardResult(null);
					} else {
						agentVerifyResultForm.setIdCardMsg(reason);
						agentVerifyResultForm.setIdCardResult(Globals.AUDIT_RESULT_REJECT);
						agentVerifyResultForm.setHeadResult(null);
					}
				} else { // 没有违规原因
					if (historyInfo.getAuditStep().intValue() == Globals.AUDIT_HISTORY_AGENT_HEAD_IMG) {
						agentVerifyResultForm.setHeadResult(Globals.AUDIT_RESULT_PASS);
						agentVerifyResultForm.setIdCardResult(null);
					} else {
						agentVerifyResultForm.setIdCardResult(Globals.AUDIT_RESULT_PASS);
						agentVerifyResultForm.setHeadResult(null);
					}
				}
				auditService.reauditAgent(agentVerifyResultForm, subHistoryId, user);
				model.addAttribute("info", "success");
				return "json";
			}
			auditService.reaudit(subHistoryId, result, user, reason);
			model.addAttribute("info", "success");
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
			e.printStackTrace();
			log.error(e);
		}

		return "json";
	}

	/**
	 * 所有审核记录
	 * @param model
	 * @param session
	 * @param request
	 * @param type 1为基本信息,2为图片
	 * @return
	 * @date 2011-12-29上午10:13:16
	 * @author dongyj
	 */
	@RequestMapping(value = "/list/{type}")
	public String getSubList(Model model, HttpSession session, HttpServletRequest request,@PathVariable("type")String type) {
		HashMap<String, Object> params = new HashMap<String, Object>();
		StringBuffer conditions = new StringBuffer();
		String houseid = request.getParameter("houseid");
		String authorOrId = request.getParameter("authorOrId");
		String startTime = request.getParameter("startTime");
		String sHour = request.getParameter("sHour");
		String endTime = request.getParameter("endTime");
		String eHour = request.getParameter("eHour");
		String auditResult = request.getParameter("auditResult");
		String dealerName = request.getParameter("dealerName");
		String house_type = request.getParameter("house_type");
		// 个人看自己的审核记录
		String per = request.getParameter("per");
		// 管理员看单个人的记录
		String userId = request.getParameter("userId");
		SessionUser user = (SessionUser) session.getAttribute(SmcConst.SESSION_USER);
		int sessionUserId = user.getUser().getId();
		
		List<HistoryInfo> historyInfos = null;

		if (StringUtils.isNotEmpty(type)) {
			if ("3".equals(type)) {
				auditHistoryService.consistQuery(model, params, conditions, houseid, authorOrId, startTime, sHour, endTime, eHour, auditResult, type, per, sessionUserId, userId,
						dealerName);
				historyInfos = auditHistoryService.getHistoryInfoListByPage(params);
			} else {
					try {
						historyInfos = auditHistoryService.getNewPhotoHstryListByPage(model, houseid, authorOrId, startTime, sHour, endTime, eHour, auditResult, type, per,
								sessionUserId, userId, dealerName, house_type);
					} catch (ParseException e) {
						e.printStackTrace();
					}
			}
		}
		
		
		
		if (StringUtils.isNotBlank(type)) {
			if (Integer.parseInt(type) == 1) {// 如果是复审标题记录,需要打回和打回原因
				List<RejectReason> reasons = rentVerifyService.getAllRejectReason(5);// 租房退回理由
				List<RejectReason> deleteReasons = rentVerifyService.getAllRejectReason(6);// 租房删除理由
				model.addAttribute("reasons", reasons);
				model.addAttribute("deleteReasons", deleteReasons);
			} else if (Integer.parseInt(type) == 3) {// 复审经纪人图片,头像和身份证的打回原因
				List<RejectReason> agentIdReasons = rentVerifyService.getAllRejectReason(0);
				List<RejectReason> agentHeadReasons = rentVerifyService.getAllRejectReason(1);
				model.addAttribute("agentIdReasons", agentIdReasons);
				model.addAttribute("agentHeadReasons", agentHeadReasons);
			}
		}
		
		model.addAttribute("list", historyInfos);
		
		if (type.equals("2")) {
			return "tiles:ah.list.photos";
		}
		
		// 是个人用户查看自己的记录
		if (null != per && "1".equals(per)) {
			return "tiles:ah.list.per";
		}
		
		// 管理员看单人的审核记录
		if(StringUtils.isNotBlank(userId)){
			return "tiles:ah.list.per.other.view";
		}
		return "tiles:ah.list";
	}

	@RequestMapping(value="/info/{id}")
	public String getInfo(Model model, HttpSession session, HttpServletRequest request,@PathVariable("id") String id) {
		SubAuditHistory subAuditHistorie = new SubAuditHistory();
		Integer subHistoryId=0;
		if(StringUtils.isNotBlank(id)){
			subHistoryId=Integer.valueOf(id);
		}
		try {
			subAuditHistorie = auditHistoryService.selectHistoryInfoById(subHistoryId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Gson gson=new Gson();
		String resultData=gson.toJson(subAuditHistorie);
		model.addAttribute("info", resultData);
		return "json";
	}
	/**
	 * 获取审核统计的数据
	 * @param model
	 * @param session
	 * @param request
	 * @return
	 * @author wangjh
	 * Apr 26, 2012 2:13:01 PM 修改
	 */
	@RequestMapping(value="/statist")
	public String getStatist(Model model, HttpSession session, HttpServletRequest request) {
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		List<User> users=userService.getAllConsoleUser();
		List<User> houseManagers = new ArrayList<User>();
		for(User user : users) {
			if(roleService.getUserIsHouseManager(user.getId())) {
				houseManagers.add(user);
			}
		}
		if(StringUtils.isBlank(startTime)&&StringUtils.isBlank(endTime)){
			startTime=this.getStringDate(1);
			endTime=this.getStringDate(7);
		}
		List<AuditorInfo> list=auditCountService.getAuditSortCount(houseManagers, startTime, endTime);
		
		model.addAttribute("list", list);
		if (StringUtils.isNotBlank(startTime)) {
			model.addAttribute("startTime", startTime);
		}
		if (StringUtils.isNotBlank(endTime)) {
			model.addAttribute("endTime", endTime);
		}
		return "tiles:ah.statist";
	}
	
	
	@RequestMapping(value="/auditErrorDetail")
	public String getAuditErrorDetail(Model model, HttpSession session, HttpServletRequest request) {
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		String auditId = request.getParameter("auditId");
		if(StringUtils.isBlank(startTime)&&StringUtils.isBlank(endTime)){
			startTime=this.getStringDate(1);
			endTime=this.getStringDate(7);
		}
		int userId = 0;
		if(StringUtils.isNotBlank(auditId)){
			userId = Integer.parseInt(auditId);
		}
		List<HistoryInfo> list = auditHistoryService.getErrorAuditByUser(userId, startTime, endTime);
		
		model.addAttribute("auditId", auditId);
		model.addAttribute("list", list);
		model.addAttribute("startTime", startTime);
		model.addAttribute("endTime", endTime);
		return "tiles:ah.error.statist";
	}
	
	
	/**
	 * 返回上周指定的第几天
	 * @param dayNum 第一天是星期一
	 * @return
	 * @author wangjh
	 * Apr 26, 2012 4:15:25 PM
	 */
	private String getStringDate(int dayNum){
		Date date=this.getDate(-1, dayNum, null);
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
		return format.format(date);
		
	}
	
	/**
	 * 
	 * 获取上周一到周日中的某一天
	 * 
	 * 说明：星期日为第一天。现在要获取上一周的周一到周日的时间
	 * 方法：获取先获取上周周日到周六的日期，再加一
	 * 如果当前是星期日，取到的日期就包括当前日
	 * @param delay （-1上周，1本周，2下周）
	 * @param weeks 获取n天
	 * @param timeStr 字符串的时间（01:01:01）
	 * @return
	 * @author wangjh
	 * Apr 26, 2012 3:27:16 PM
	 */
	public Date getDate(int delay,int weeks,String timeStr){
		Calendar cal = Calendar.getInstance();
		  //n为推迟的周数，1本周，-1向前推迟一周，2下周，依次类推
		int n = delay;
		if(Calendar.getInstance().get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY){
			n=2*n;
		}
		  cal.add(Calendar.DATE, n*7);
		  //想周几，这里就传几Calendar.MONDAY（TUESDAY...）
		  cal.set(Calendar.DAY_OF_WEEK,weeks);
		  SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
		  SimpleDateFormat dateTimeFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		  String dateStr=format.format(cal.getTime());
		  if(StringUtils.isBlank(timeStr)){
			  timeStr="00:00:00";
		  }
		  dateStr+=" "+timeStr;
		  Date dates=cal.getTime();
		  try {
			dates=dateTimeFormat.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		  return DateUtils.addDays(dates,1);
	}
	
	/**
	 * 动态查询审核数量
	 * @param model
	 * @param session
	 * @param request
	 * @return
	 * @date 2011-12-30下午5:51:01
	 * @author dongyj
	 */
	@RequestMapping(value="/getCount")
	public String getCount(Model model, HttpSession session, HttpServletRequest request) {
		String auditUserId = request.getParameter("auditUserId");
		String types = request.getParameter("type");
		String countType = request.getParameter("houseType");
		String await = request.getParameter("await");
		Integer auditStep = null;
		Integer type = null;
		try {
			if (StringUtils.isNotBlank(countType)) {
				if ("resale".equals(countType)) {
					type = Globals.HOUSE_TYPE_RESALE;
				} else if ("rent".equals(countType)) {
					type = Globals.HOUSE_TYPE_RENT;
				}else if("agent".equals(countType)){
					type = Globals.AGENT_TYPE;
				}
			}
			if (StringUtils.isNotBlank(types)) {
				if (("" + Globals.AUDIT_HISTORY_COMMUNITY).equals(types)) {
					auditStep = Globals.AUDIT_ESTATE_PHOTO;
				} else if (("" + Globals.AUDIT_HISTORY_INDOOR).equals(types)) {
					auditStep = Globals.AUDIT_INDOOR_PHOTOO;
				} else if (("" + Globals.AUDIT_HISTORY_LAYOUT).equals(types)) {
					auditStep = Globals.AUDIT_HOUSEHOLD_PHOTO;
				} else if (("" + Globals.AUDIT_HISTORY_BASEINFO).equals(types)) {
					auditStep = Globals.AUDIT_BASE_INFO;
				} else if("5".equals(types)){
					auditStep = 5;
				}
				
				if (StringUtils.isNotBlank(await)) {
					// 待审基本信息
					int awaitCount = auditCountService.awaitAuditCount(type,auditStep);
					model.addAttribute("await", awaitCount);
				}
			}

			// 已审数量
			int resDone = auditCountService.processedCountToday(auditStep, type, Integer.parseInt(auditUserId));
			model.addAttribute("res", resDone);
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
			e.printStackTrace();
		}
		return "json";
	}
	
	@RequestMapping(value="/getphotos")
	public String getPhotos(Model model,HttpServletRequest request){
		String photoids = request.getParameter("photoids");
		String audit_ids = request.getParameter("audit_ids");
		String auditStep = request.getParameter("auditStep");
		String results = request.getParameter("results");
		String thisHistoryId = request.getParameter("thisHistoryId");
		request.getSession().setAttribute("thisHistoryId", thisHistoryId);
		String house_type = request.getParameter("house_type");
		List<HistoryInfo> list = auditService.getPhotos(request,photoids,audit_ids,auditStep,results,house_type);
		List<RejectReason> photoReasons = rentVerifyService.getAllRejectReason(Globals.PHOTO_DELETE_REASON);
		model.addAttribute("photoReasons", photoReasons);
		model.addAttribute("thisHistoryId", thisHistoryId);
		model.addAttribute("house_type", house_type);
		model.addAttribute("auditStep", auditStep);
		model.addAttribute("list", list);
		return "auditHistory/ah_photos_popup";
	}
	
	@RequestMapping(value="/getphotosbyhouseid")
	public String getPhotosByHouseId(Model model,HttpServletRequest request){
		String houseid = request.getParameter("houseid");
		String housetype = request.getParameter("housetype");
		return "auditHistory/ah_photos_popup";
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/reaudit/photo/{photoId}/{result}")
	public String reauditPhoto(Model model, HttpSession session, HttpServletRequest request, @PathVariable("photoId") String photoId, @PathVariable("result") String result) {
		String house_type = request.getParameter("house_type");
		String auditStep = request.getParameter("auditStep");
		try {
			SessionUser sessionUser = (SessionUser) session.getAttribute("sessionUser");
			User user = null;
			if (sessionUser != null) {
				user = sessionUser.getUser();
			}
			List<Integer> cur_photoIdList = (List<Integer>) session.getAttribute("cur_photoIdList");
			List<Integer> cur_houseIdList = (List<Integer>) session.getAttribute("cur_houseIdList");
			List<Integer> cur_auditIdList = (List<Integer>) session.getAttribute("cur_auditIdList");
			Integer photoIdInteger = Integer.parseInt(photoId);
			Integer houseIdInteger = cur_houseIdList.get(cur_photoIdList.indexOf(photoIdInteger));
			Integer auditIdInteger = cur_auditIdList.get(cur_photoIdList.indexOf(photoIdInteger));
			String thisHistoryId = (String) request.getSession().getAttribute("thisHistoryId");
			NewSubAuditHistory newSubAuditHistory = auditHistoryService.getNewSubAuditHistory(Integer.parseInt(thisHistoryId));

			// 变成违规
			if (result.equals("" + Globals.AUDIT_RESULT_REJECT)) {
				String reason = request.getParameter("reason");
				auditHistoryService.reauditPhotoReject(photoIdInteger, houseIdInteger, house_type, reason, auditStep, user);
			} else if (result.equals("" + Globals.AUDIT_RESULT_PASS)) {// 变成通过
				auditHistoryService.reauditPhotoPass(photoIdInteger, houseIdInteger, house_type);
			}

			// 添加复审记录
			auditHistoryService.addReauditHistory(newSubAuditHistory, auditIdInteger, photoIdInteger, result, user);
			model.addAttribute("info", "success");
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("info", "error");
		}
		return "json";
	}
}