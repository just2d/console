package com.nuoshi.console.web.controller;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.taofang.biz.domain.audit.house.task.HousePhotoAuditTask;
import com.taofang.biz.domain.audit.house.task.HouseTitleDescAuditTask;
import com.taofang.biz.domain.constants.AuditConstant;
import com.taofang.biz.domain.constants.HouseConstant;
import com.taofang.biz.domain.house.HousePhoto;
import com.taofang.biz.service.house.IHousePhotoService;
import com.taofang.biz.util.BizServiceHelper;
import com.nuoshi.console.common.Globals;
import com.nuoshi.console.common.Resources;
import com.nuoshi.console.common.util.ApplicationUtil;
import com.nuoshi.console.common.util.SmcConst;
import com.nuoshi.console.domain.agent.AgentManage;
import com.nuoshi.console.domain.agent.RejectReason;

import com.nuoshi.console.domain.house.HouseExtraScoreHistory;

import com.nuoshi.console.domain.auditHistory.HistoryInfo;
import com.nuoshi.console.domain.base.House;
import com.nuoshi.console.domain.rent.Rent;

import com.nuoshi.console.domain.rent.RentInfo;
import com.nuoshi.console.domain.topic.Locale;
import com.nuoshi.console.domain.user.User;
import com.nuoshi.console.service.AgentManageService;
import com.nuoshi.console.service.AuditService;
import com.nuoshi.console.service.HouseExtraScoreHistoryService;
import com.nuoshi.console.service.LocaleService;
import com.nuoshi.console.service.RentService;
import com.nuoshi.console.service.RentVerifyService;
import com.nuoshi.console.web.session.SessionUser;

@Controller
@RequestMapping(value = "/rent")
public class RentController extends BaseController {
	
	//session中存放待审核房源基本信息的key值
	private static final String rent_base_info= "rent_base_info";
	
	//session中存放待审核房源图片信息的key值
	private static final String rent_layout_photo_task= "rent_layout_photo_task";
	
	private static final String rent_indoor_photo_task= "rent_indoor_photo_task";
	
	private static final String rent_outdoor_photo_task= "rent_outdoor_photo_task";
	
	//session中存放某房源存在及拒绝的图片
	private static final String rent_exist_photo= "rent_exist_photo";
	
	private static final String rent_reject_photo= "rent_reject_photo";

	@Autowired
	private RentVerifyService rentVerifyService;
	@Autowired
	private RentService rentService;
	@Autowired
	private AgentManageService agentManageService;
	@Autowired
	private AuditService auditService;
	@Autowired
	private HouseExtraScoreHistoryService houseExtraScoreHistoryService;

	/**
	 * 获取审核的的租房
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/verify/list")
	public String getRent4VerifyByPage(Model model, HttpSession session, HttpServletRequest request) {
		String authorid = request.getParameter("authorid");
		String name = request.getParameter("name");
		String title = request.getParameter("title");
		String id = request.getParameter("id");
		Boolean flag = true;
		String onlyBeiJing = request.getParameter("onlyBeiJing");
		if (StringUtils.isBlank(onlyBeiJing)) {
			flag = false;
		}
		StringBuffer condition = new StringBuffer();
		HashMap params = null;
//		String[] checkResult = null;
//		String paraStr = null;
		String mobile = request.getParameter("mobile");
		if (StringUtils.isNotBlank(mobile)) {
			mobile = mobile.trim();
			condition.append(" and r.author_phone = '" + mobile + "'");
		}
		if (StringUtils.isNotBlank(id)) {
			id = id.trim();
			condition.append(" and r.id = '" + id + "'");
		}

		if (StringUtils.isNotBlank(authorid)) {
			authorid = authorid.trim();
			condition.append(" and r.authorid = '" + authorid + "'");
		}
		if (StringUtils.isNotBlank(name)) {
			name = name.trim();
			condition.append(" and r.author_name like '%" + name + "%'");
		}
		if (StringUtils.isNotBlank(title)) {
			title = title.trim();
			condition.append(" and r.title like '%" + title + "%'");
		}
		/**
		 * 只审核北京房源 wangjh 12-01 改
		 */
		if (flag) {
			condition.append(" and r.city_id = " + Globals.BEIJINGID + " ");
		}

		// 房屋状态已发布
		condition.append("and r.house_status = 1");
		// 审核状态未审核
		condition.append(" and r.audit_status = 0");
		if (StringUtils.isNotBlank(condition.toString())) {
			params = new HashMap<String, Object>();
			params.put("condition", condition);
		}
		List<RejectReason> reasons = rentVerifyService.getAllRejectReason(5);// 租房退回理由
		List<RejectReason> deleteReasons = rentVerifyService.getAllRejectReason(6);// 租房删除理由
		List<RentInfo> rentList = rentVerifyService.getRent4VerifyByPage(params);
//		for (RentInfo para : rentList) {
//			checkResult = agentManageService.checkSensitiveWord(para.getTitle());
//			if (checkResult != null && checkResult.length > 0) {
//				paraStr = para.getTitle();
//				for (String sensitive : checkResult) {
//					paraStr = paraStr.replaceAll(sensitive, "<span style='color:red'>" + sensitive + "</span>");
//				}
//				para.setTitle(paraStr);
//			}
//			checkResult = agentManageService.checkSensitiveWord(para.getExtinfo());
//			if (checkResult != null && checkResult.length > 0) {
//				paraStr = para.getExtinfo();
//				for (String sensitive : checkResult) {
//					paraStr = paraStr.replaceAll(sensitive, "<span style='color:red'>" + sensitive + "</span>");
//				}
//				para.setExtinfo(paraStr);
//			}
//		}

		model.addAttribute("reasons", reasons);
		model.addAttribute("deleteReasons", deleteReasons);
		model.addAttribute("rentList", rentList);
		return "tiles:rent.verify";

	}

	/**
	 * 获取审核的的租房
	 */
	@RequestMapping(value = "/verify/list/verifyPhoto/{type}")
	public String verifing(Model model, HttpSession session, HttpServletRequest request, @PathVariable("type") String type) {
//		Long bt=System.currentTimeMillis();
		// 将简化版的城市List放入application中.
		ServletContext application = request.getSession().getServletContext();
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
		List<RejectReason> reasons = rentVerifyService.getAllRejectReason(Globals.PHOTO_DELETE_REASON);// 图片删除理由
		String cityIdStr = request.getParameter("cityid");
		String circleCount = request.getParameter("circleCount");
		if (StringUtils.isBlank(circleCount)) {
			circleCount = "1";
		} else {
			circleCount = Integer.parseInt(circleCount) + 1 + "";
		}
		Integer cityId = 1;
		if (StringUtils.isNotBlank(cityIdStr)) {
			cityId = Integer.valueOf(cityIdStr);
			if(cityId<0) cityId =0;
		}

		// 已经循环了5次
		if (Integer.parseInt(circleCount) == 6) {
			model.addAttribute("reasons", reasons);
			model.addAttribute("circleCount", circleCount);
			model.addAttribute("cityid", cityId);
			model.addAttribute("type", type);
			return "tiles:rent.verifyPhoto";
		}
		int notPassedPhotoCount =0;
		List<HousePhotoAuditTask> rentList = null;
		// 取数据
		switch (Integer.parseInt(type)) {
		case 1:
			rentList = BizServiceHelper.getAuditHouseService().getRentLayoutPhotoAuditTasks(cityId, Globals.AUDIT_HOUSE_COUNT*2);
			session.removeAttribute(rent_layout_photo_task);
			session.setAttribute(rent_layout_photo_task, rentList);
			notPassedPhotoCount = BizServiceHelper.getAuditHouseService().getRentTasksAmount(AuditConstant.LAYOUT_PHOTO_TASK);
			break;
		case 2:
			rentList = BizServiceHelper.getAuditHouseService().getRentIndoorPhotoAuditTasks(cityId, Globals.AUDIT_HOUSE_COUNT/2);
			session.removeAttribute(rent_indoor_photo_task);
			session.setAttribute(rent_indoor_photo_task, rentList);
			notPassedPhotoCount = BizServiceHelper.getAuditHouseService().getRentTasksAmount(AuditConstant.INDOOR_PHOTO_TASK);
			break;
		case 3:
			rentList = BizServiceHelper.getAuditHouseService().getRentOutdoorPhotoAuditTasks(cityId, Globals.AUDIT_HOUSE_COUNT);
			session.removeAttribute(rent_outdoor_photo_task);
			session.setAttribute(rent_outdoor_photo_task, rentList);
			notPassedPhotoCount = BizServiceHelper.getAuditHouseService().getRentTasksAmount(AuditConstant.OUTDOOR_PHOTO_TASK);
			break;
		}
		//taskIds
		List<Integer> taskIdList = new ArrayList<Integer>();
		// houseidList
		List<Integer> houseIdList = new ArrayList<Integer>();
		List<com.taofang.biz.domain.house.HousePhoto> housePhotoList = new ArrayList<com.taofang.biz.domain.house.HousePhoto>();
		// photoidList
		List<Integer> photoids = new ArrayList<Integer>();
		for (HousePhotoAuditTask rentTask : rentList) {
			taskIdList.add(rentTask.getTaskId());
			if(!houseIdList.contains(rentTask.getHouseId())){
				houseIdList.add(rentTask.getHouseId());
			}
			for(com.taofang.biz.domain.house.HousePhoto housePhoto:rentTask.getPhotos()){
				photoids.add(housePhoto.getId());
				housePhotoList.add(housePhoto);
			}
		}
		
		//室内图取装修情况
		if("2".equals(type)){
			housePhotoList =  rentVerifyService.getDecorationByPhotos(housePhotoList);
		}
		
		model.addAttribute("notPassedPhotoCount", notPassedPhotoCount);
		// 所有照片List
		model.addAttribute("rentList", housePhotoList);
		model.addAttribute("taskids", taskIdList);

		// 所有房源id
		model.addAttribute("reasons", reasons);
		model.addAttribute("houseIdList", houseIdList);
		model.addAttribute("photoids", photoids);
		model.addAttribute("cityid", cityId);
		model.addAttribute("type", Integer.parseInt(type));
//		System.out.println("获取（type="+type+"）图片和房源信息的总时间为："+(System.currentTimeMillis()-bt)+" 毫秒。");
		return "tiles:rent.verifyPhoto";

	}

	/**
	 * 获取审核的租房的基本信息
	 */
	@RequestMapping(value = "/verify/baseInfoList")
	public String baseInfoList(Model model, HttpSession session, HttpServletRequest request) {
		SessionUser sessionUser = (SessionUser) session.getAttribute("sessionUser");
//		Integer houseType=Globals.HOUSE_TYPE_RENT;
//		Integer awaitAuditCount=auditCountService.awaitHouseAuditCount(houseType);
		User user=null;
		if(sessionUser!=null){
			user=sessionUser.getUser();
		}
//		Integer passedAuditCount=0;
//		if(user!=null){
//			passedAuditCount=auditCountService.processedCountToday(auditStep, houseType, user.getId());
//		}
		// 将简化版的城市List放入application中.
		ServletContext application = request.getSession().getServletContext();
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
		String cityIdStr = request.getParameter("cityid");
		Integer cityId = 1;
		if (StringUtils.isNotBlank(cityIdStr)) {
			cityId = Integer.valueOf(cityIdStr);
			if(cityId<0) cityId =0;
		}
//		String[] checkResult = null;
//		String paraStr = null;
		List<RejectReason> reasons = rentVerifyService.getAllRejectReason(5);// 租房退回理由
		List<RejectReason> deleteReasons = rentVerifyService.getAllRejectReason(6);// 租房删除理由
		List<HouseTitleDescAuditTask> rentList = null;
		if (sessionUser != null) {
			user=sessionUser.getUser();
		} else {
			log.error("用户未登录！");
		}
		try {

			// 页面的房源id和打回原因的对应关系
			String[] idAndReasonArr = request.getParameterValues("reason");
			// 页面回来的数据houseId和打回原因的分隔符
			final String separator = "#_#";
			// 打回的房源和原因
			Map<Integer, String> rejectIdAndReason = new HashMap<Integer, String>();
			// 通过的房源
			List<Integer> passHouseId = new ArrayList<Integer>();
			// 整理通过和打回的数据
			if (idAndReasonArr != null && idAndReasonArr.length > 0) {
				for (int i = 0; i < idAndReasonArr.length; i++) {
					String idAndReason = idAndReasonArr[i];
					Integer isExist = idAndReason.indexOf(separator);
					// 通过基本信息审核的房源
					if (isExist == -1) {
						passHouseId.add(Integer.valueOf(idAndReason));
					}
					String[] divisionStr = {};
					// 被打回的房源和打回原因
					if (isExist != -1) {
						divisionStr = idAndReason.split(separator);
						rejectIdAndReason.put(Integer.valueOf(divisionStr[0]), divisionStr[1]);
					}
				}
			}
			
			@SuppressWarnings("unchecked")
			List<HouseTitleDescAuditTask> sessionResaleList = (List<HouseTitleDescAuditTask>) session.getAttribute(rent_base_info);
			auditService.auditBaseInfo(passHouseId,rejectIdAndReason,HouseConstant.RENT,user,sessionResaleList);
			session.removeAttribute(rent_base_info);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			// 获取审核列表
			rentList = BizServiceHelper.getAuditHouseService().getRentTitleDescAuditTasks(cityId, Globals.AUDIT_HOUSE_COUNT);
			//删除之前领取的基本信息和图片信息
			session.removeAttribute(rent_base_info);
			//将基本信息放入session
			if(CollectionUtils.isNotEmpty(rentList)){
				session.setAttribute(rent_base_info, rentList);
			}
			
			int notPassedPhotoCount =0;
			notPassedPhotoCount = BizServiceHelper.getAuditHouseService().getRentTasksAmount(AuditConstant.BASIC_INFO_TASK);
			model.addAttribute("notPassedPhotoCount", notPassedPhotoCount);

			model.addAttribute("reasons", reasons);
			model.addAttribute("deleteReasons", deleteReasons);
			model.addAttribute("rentList", rentList);
			model.addAttribute("cityid", cityId);
//			model.addAttribute("passedAuditCount", passedAuditCount);
//			model.addAttribute("awaitAuditCount", awaitAuditCount);
			
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
		}
		
		String postSubmit = request.getParameter("postSubmit");
		if(StringUtils.isNotBlank(postSubmit)){
			return "redirect:/rent/verify/baseInfoList";
		}
		return "tiles:rent.verify.baseInfoList";

	}

	/**
	 * 刷新页面
	 */
	@RequestMapping(value = "/verify/refreshBaseInfoPage")
	public String refreshBaseInfoPage(Model model, HttpSession session, HttpServletRequest request) {
		try {
			String cityIdStr = request.getParameter("cityid");
			Integer cityId = 1;
			if (StringUtils.isNotBlank(cityIdStr)) {
				cityId = Integer.valueOf(cityIdStr);
				if(cityId<0) cityId =0;
			}
//			String[] checkResult = null;
//			String paraStr = null;
			List<RejectReason> reasons = rentVerifyService.getAllRejectReason(5);// 租房退回理由
			List<RejectReason> deleteReasons = rentVerifyService.getAllRejectReason(6);// 租房删除理由
			List<HouseTitleDescAuditTask>  rentList = null;
			// 获取审核列表
			// 获取审核列表
			rentList = BizServiceHelper.getAuditHouseService().getRentTitleDescAuditTasks(cityId, Globals.AUDIT_HOUSE_COUNT);
			//删除之前领取的基本信息和图片信息
			session.removeAttribute(rent_base_info);
			//将基本信息放入session
			if(CollectionUtils.isNotEmpty(rentList)){
				session.setAttribute(rent_base_info, rentList);
			}
			int notPassedPhotoCount =0;
			notPassedPhotoCount = BizServiceHelper.getAuditHouseService().getRentTasksAmount(AuditConstant.BASIC_INFO_TASK);
			model.addAttribute("notPassedPhotoCount", notPassedPhotoCount);

			model.addAttribute("reasons", reasons);
			model.addAttribute("deleteReasons", deleteReasons);
			model.addAttribute("rentList", rentList);
			model.addAttribute("cityid", cityId);
//		model.addAttribute("passedAuditCount", passedAuditCount);
//		model.addAttribute("awaitAuditCount", awaitAuditCount);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			log.error(e);
		}

		return "tiles:rent.verify.baseInfoList";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/verify/photos")
	public String verifyPhotos(Model model, HttpSession session, HttpServletRequest request) {
		String blockList = request.getParameter("blockList");
		String houseIdList = request.getParameter("houseIdList");
		String photoids = request.getParameter("photoids");
		String taskids = request.getParameter("taskids");
		String type = request.getParameter("type");
		List<HousePhotoAuditTask> rentList = null;
		
		if("1".equals(type.trim())){
			rentList = (List<HousePhotoAuditTask>) session.getAttribute(rent_layout_photo_task);
		}else if("2".equals(type.trim())){
			rentList = (List<HousePhotoAuditTask>) session.getAttribute(rent_indoor_photo_task);
		}else if("3".equals(type.trim())){
			rentList = (List<HousePhotoAuditTask>) session.getAttribute(rent_outdoor_photo_task);
		}
		try {
			auditService.verifyPhotos(houseIdList, blockList, photoids, Integer.parseInt(type), (SessionUser) session.getAttribute(SmcConst.SESSION_USER), HouseConstant.RENT,rentList,taskids);
			model.addAttribute("info", "success");
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("info", "error");
			model.addAttribute("error", e.getMessage());
		}
		if("1".equals(type.trim())){
			session.removeAttribute(rent_layout_photo_task);
		}else if("2".equals(type.trim())){
			session.removeAttribute(rent_indoor_photo_task);
		}else if("3".equals(type.trim())){
			session.removeAttribute(rent_outdoor_photo_task);
		}
		return "json";
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/verify/reauditphotos")
	public String reauditPhotos(Model model,HttpSession session,HttpServletRequest request){
		String blockList = request.getParameter("blockList");
		String photoids = request.getParameter("photoids");
		String resumes = request.getParameter("resumes");
		String houseId = request.getParameter("houseId");
		
		List<HousePhoto> existPhotoList = (List<HousePhoto>) session.getAttribute(rent_exist_photo);
		List<HousePhoto> rejectPhotoList = (List<HousePhoto>) session.getAttribute(rent_reject_photo);
		
		 
		try {
			auditService.reauditPhotos(houseId,blockList, photoids,resumes, (SessionUser) session.getAttribute(SmcConst.SESSION_USER), HouseConstant.RENT,existPhotoList,rejectPhotoList);
			model.addAttribute("info", "success");
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("info", "err");
		}
		session.removeAttribute(rent_exist_photo);
		session.removeAttribute(rent_reject_photo);
		
		return "json";
	}

	@RequestMapping(value="/addExtraScore")
	public String addExtraScore(Model model,HttpSession session,HttpServletRequest request){
		SessionUser sessionUser = (SessionUser) session.getAttribute(SmcConst.SESSION_USER);
		String scoreStr = request.getParameter("score");
		String houseIdStr = request.getParameter("id");
		 if(scoreStr==null||houseIdStr == null ){
			 model.addAttribute("error", "请填写正确的数据");
		 }else{
			 try{
				 int houseId = Integer.parseInt(houseIdStr);
				 BigDecimal score = new BigDecimal(scoreStr);
				 rentService.addExtraScore(houseId,score,sessionUser.getUser().getUserName());
				model.addAttribute("info", "success");
			 }catch (Exception e) {
				 model.addAttribute("error", "请填写正确的数据");
			}
			
			 
		 }
		
		
		return "json";
	}
	
	@RequestMapping(value="/getExtraScoreHistory/{houseId}")
	public String getExtraScoreHistory(Model model,HttpSession session,HttpServletRequest request,@PathVariable("houseId") int houseId){
		 
		List<HouseExtraScoreHistory> houseExtraScoreHistorys = houseExtraScoreHistoryService.getByHouseId(houseId, HouseConstant.RENT);
		model.addAttribute("houseExtraScoreHistorys", houseExtraScoreHistorys);
		
		return "json";
	}
	/**
	 * 获取审核的的租房
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/verify/allrent/list")
	public String getAllRent4VerifyByPage(Model model, HttpSession session, HttpServletRequest request) {
		// 将简化版的城市List放入application中.
		ServletContext application = request.getSession().getServletContext();
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
		String typeStr = request.getParameter("type");
		int type = 0;
		if (StringUtils.isNotBlank(typeStr)) {
			type = Integer.parseInt(typeStr);
			;
		}
		List<RejectReason> reasons = rentVerifyService.getAllRejectReason(5);// 租房退回理由
		List<RejectReason> deleteReasons = rentVerifyService.getAllRejectReason(6);// 租房删除理由
		model.addAttribute("reasons", reasons);
		model.addAttribute("deleteReasons", deleteReasons);
		String authorid = request.getParameter("authorid");
		String name = request.getParameter("name");
		String title = request.getParameter("title");
		String id = request.getParameter("id");
		String cityId = request.getParameter("cityId");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String houselabel = request.getParameter("houselabel");
		StringBuffer condition = new StringBuffer();
		@SuppressWarnings("rawtypes")
		HashMap params = null;
		switch (type) {
		case SmcConst.HOUSE_STATUS_ONSHELVE:
			condition.append(" and r.house_status = 1 ");
			break;
		case SmcConst.HOUSE_STATUS_UNSHELVE:
			condition.append(" and r.house_status = 0 ");
			break;
		case SmcConst.HOUSE_STATUS_OUTOFDATE:
			condition.append(" and r.house_status = 3 ");
			break;
		case SmcConst.HOUSE_STATUS_ILLEGAL:
			condition.append(" and r.house_status = 2 ");
			break;
		case SmcConst.HOUSE_STATUS_DELETED:
			condition.append(" and r.house_status = 4 ");
			break;
		case SmcConst.HOUSE_STATUS_DRAFTBOX:
			condition.append(" and r.house_status = 5 ");
			break;
		case SmcConst.HOUSE_STATUS_ONSHELVE_UNCHECK:
			condition.append(" and r.house_status = 1 ");
			condition.append(" and r.audit_status = 0 ");
			break;
		case SmcConst.HOUSE_STATUS_ONSHELVE_CHECKED:
			condition.append(" and r.house_status = 1 ");
			condition.append(" and r.audit_status = 1 ");
			break;
		case SmcConst.HOUSE_STATUS_NOAUDIT_AGENT:
			condition.append(" and r.house_status = 6 ");
			break;

		default:
			break;
		}
		String mobile = request.getParameter("mobile");
		if (StringUtils.isNotBlank(mobile)) {
			mobile = mobile.trim();
			condition.append(" and r.author_phone = '" + mobile + "'");
		}
		if (StringUtils.isNotBlank(id)) {
			id = id.trim();
			condition.append(" and r.id = '" + id + "'");
		}

		if (StringUtils.isNotBlank(authorid)) {
			authorid = authorid.trim();
			condition.append(" and r.authorid = '" + authorid + "'");
		}
		if (StringUtils.isNotBlank(name)) {
			name = name.trim();
			condition.append(" and r.author_name like '%" + name + "%'");
		}
		if (StringUtils.isNotBlank(title)) {
			title = title.trim();
			condition.append(" and r.title like '%" + title + "%'");
		}
		if(StringUtils.isNotBlank(cityId) && Integer.parseInt(cityId) > 0) {
			condition.append(" and r.city_id = " + Integer.parseInt(cityId));
		}
		if (StringUtils.isNotBlank(houselabel)) {
			houselabel = houselabel.trim();
			condition.append(" and r.house_label like '%" + houselabel + "%'");
		}
		
		if(!StringUtils.isBlank(startDate) && !"起始日期".equals(startDate)) {
			if(!StringUtils.isBlank(endDate) && !"终止日期".equals(endDate)) {
				condition.append(" and r.pubdate > '"+startDate+"' AND r.pubdate < '"+endDate+"'");
			} else {
				condition.append(" and r.pubdate > '"+startDate+"'");
			}
		} else {
			if(!StringUtils.isBlank(endDate) && !"终止日期".equals(endDate)) {
				condition.append(" and r.pubdate < '"+endDate+"'");
			} 
		}
		if (StringUtils.isNotBlank(condition.toString())) {
			params = new HashMap<String, Object>();
			params.put("condition", condition);
		}
		List<RentInfo> rentList = rentVerifyService.getAllRent4VerifyByPage(params);
		model.addAttribute("rentList", rentList);
		model.addAttribute("cityId", cityId == null ? 0 : cityId);
		return "tiles:allrent.verify";

	}

	@RequestMapping(value = "/verify/allrent/list/home")
	public String toHouseListPage(Model model, HttpSession session, HttpServletRequest request) {
		// 将简化版的城市List放入application中.
		model.addAttribute("reasons", null);
		model.addAttribute("deleteReasons", null);
		model.addAttribute("rentList", new ArrayList<RentInfo>());
		model.addAttribute("cityId", 0);
		return "tiles:allrent.verify";

	}
	
	
	@RequestMapping(value = "/house/photo/{houseId}/{category}")
	public String getHousePhoto(Model model, HttpSession session, @PathVariable("houseId") int houseId, @PathVariable("category") String category) {
		List<Integer> photoids = new ArrayList<Integer>();
		IHousePhotoService housePhotoService = BizServiceHelper.getHousePhotoService();
		Rent rent = rentService.selectRentById(houseId);
		List<com.taofang.biz.domain.house.HousePhoto> housePhotoList = housePhotoService.getRentPhotos(houseId);
		
		List<com.taofang.biz.domain.house.HousePhoto> rejectPhotoList = housePhotoService.getRejectedPhotos(rent.getCityid(), HouseConstant.RENT, houseId);
		
		List<HistoryInfo> reauditHistory = auditService.getReAuditHistory(houseId,HouseConstant.RENT);
		
		Map<Integer,HistoryInfo> reauditMap = new HashMap<Integer, HistoryInfo>();
		if(reauditHistory != null && reauditHistory.size()>0 ){
			for(HistoryInfo reaudit : reauditHistory){
				reauditMap.put(reaudit.getPhotoId(), reaudit);
			}
		}
		if(housePhotoList != null && housePhotoList.size()>0 ){
			Iterator<com.taofang.biz.domain.house.HousePhoto>  it = housePhotoList.iterator();
			while (it.hasNext()) {
				com.taofang.biz.domain.house.HousePhoto existPhoto = it.next();
				if(reauditMap.get(existPhoto.getId()) != null) existPhoto.setHdFlag("R");
				if(existPhoto.getCategory()/100+1 != Integer.valueOf(category)){
					it.remove();
				}else{
					photoids.add(existPhoto.getId());
				}
			}
		}
		
		if(rejectPhotoList != null && rejectPhotoList.size()>0){
			Iterator<com.taofang.biz.domain.house.HousePhoto>  ite = rejectPhotoList.iterator();
			while (ite.hasNext()) {
				com.taofang.biz.domain.house.HousePhoto rejectPhoto = ite.next();
				if(reauditMap.get(rejectPhoto.getId()) != null) rejectPhoto.setHdFlag("R");
				rejectPhoto.setEstateId(rent.getEstateid());
				if(rejectPhoto.getCategory()/100+1 != Integer.valueOf(category)){
					ite.remove();
				}else{
					photoids.add(rejectPhoto.getId());
				}
			}
		}
		if("2".equals(category)){
			if(housePhotoList != null && housePhotoList.size()>0 ){
				for(com.taofang.biz.domain.house.HousePhoto housePhoto:housePhotoList){
					housePhoto.setCoverFlag(Resources.getString("house.decoration."+rent.getDecoration()));
				}
			}
			
			if(rejectPhotoList != null && rejectPhotoList.size()>0){
				for(com.taofang.biz.domain.house.HousePhoto rejectPhoto:rejectPhotoList){
					rejectPhoto.setCoverFlag(Resources.getString("house.decoration."+rent.getDecoration()));
				}
			}
			
		}
		
		session.setAttribute(rent_exist_photo, housePhotoList);
		session.setAttribute(rent_reject_photo, rejectPhotoList);
		
		List<RejectReason> reasons = rentVerifyService.getAllRejectReason(Globals.PHOTO_DELETE_REASON);// 图片删除理由
		
		model.addAttribute("houseType", HouseConstant.RENT);
		model.addAttribute("houseId", houseId);
		model.addAttribute("photoids", photoids);
		model.addAttribute("category", category);
		model.addAttribute("reasons", reasons);
		model.addAttribute("housePhotoList", housePhotoList);
		model.addAttribute("rejectPhotoList", rejectPhotoList);
		return "/house/photo/show";

	}

	/**
	 * 获取经纪人租房信息，以列表弐展示：租房标题、户型图、室内图、小区图
	 */
	@RequestMapping(value = "/house/{authorId}")
	public String listHouse(Model model, @PathVariable("authorId") int authorId) {
		List<RejectReason> reasons = rentVerifyService.getAllRejectReason(5);// 租房退回理由
		List<RejectReason> deleteReasons = rentVerifyService.getAllRejectReason(6);// 租房删除理由
		model.addAttribute("reasons", reasons);
		model.addAttribute("deleteReasons", deleteReasons);
		List<RentInfo> rentInfoList = rentVerifyService.getAllRent(authorId);
		AgentManage agent = agentManageService.getAgentInfo(authorId);
		model.addAttribute("rentInfoList", rentInfoList);
		model.addAttribute("agent", agent);
		model.addAttribute("authorId", authorId);
		return "tiles:rent.house.list";
	}

	/**
	 * 
	 */
	@RequestMapping(value = "/verifyReq")
	public String verify(HttpServletRequest request, HttpSession session, Model model) {
		SessionUser sessionUser = (SessionUser) session.getAttribute("sessionUser");
		User user =sessionUser.getUser();
		int ret = -1;
		String id = request.getParameter("id");
		String type = request.getParameter("type");
		String reasons = request.getParameter("reasons");
		HashMap<String, Object> params = new HashMap<String, Object>();
		if (StringUtils.isNotBlank(reasons)) {
			params.put("reasons", reasons);
		}
		if ("2".equals(type)) {// 审核通过
			ret = rentVerifyService.approve(Integer.parseInt(id),user);
		} else if ("1".equals(type)) {// 删除房源

		} else if ("0".equals(type)) {// 退回
			ret = rentVerifyService.reject(Integer.parseInt(id), reasons,user);
		}

		if (ret > 0) {
			model.addAttribute("info", "success");
		} else {
			model.addAttribute("info", "error");
		}
		try {
			ApplicationUtil.getHttpResponse(Resources.getString("taofang.service.url") + "zufang/refresh/" + id + "-0");
		} catch (Exception e) {

		}
		return "json";
	}

	/**
	 * 删除图片
	 * 
	 * @param request
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/photo/delete")
	public String delPhoto(HttpServletRequest request, HttpSession session, Model model) {

		String houseid = request.getParameter("houseid");
		String photoid = request.getParameter("photoid");
		String category = request.getParameter("category");
		try {
			rentVerifyService.deleteHousePhoto(Integer.parseInt(houseid), Integer.parseInt(photoid), Integer.parseInt(category));
			model.addAttribute("info", "success");
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("info", "error");
		}
		try {
			ApplicationUtil.getHttpResponse(Resources.getString("taofang.service.url") + "zufang/refresh/" + houseid + "-0");
		} catch (Exception e) {

		}
		return "json";
	}

	@RequestMapping(value = "/verifyReq/batch")
	public String verifyBatch(HttpServletRequest request, HttpSession session, Model model) {
		SessionUser sessionUser = (SessionUser) session.getAttribute("sessionUser");
		User user =sessionUser.getUser();
		
		String id = request.getParameter("ids");
		if (StringUtils.isBlank(id)) {
			model.addAttribute("error", "请至少选择一个房源!");
			return "json";
		}
		String[] ids = id.split(",");
		String type = request.getParameter("type");
		String reasons = request.getParameter("reasons");

		HashMap<String, Object> params = new HashMap<String, Object>();
		if (StringUtils.isNotBlank(reasons)) {
			params.put("reasons", reasons);
		}
		if ("2".equals(type)) {// 审核通过
			for (int i = 0; i < ids.length; i++) {
				rentVerifyService.approve(Integer.parseInt(ids[i]),user);
			}
		} else if ("1".equals(type)) {// 删除房源

		} else if ("0".equals(type)) {// 退回
			for (int i = 0; i < ids.length; i++) {
				rentVerifyService.reject(Integer.parseInt(ids[i]), reasons,user);
				rentVerifyService.addInvalidReason(Integer.parseInt(ids[i]), reasons);
			}

		}
		for (int i = 0; i < ids.length; i++) {
			try {
				ApplicationUtil.getHttpResponse(Resources.getString("taofang.service.url") + "zufang/refresh/" + id + "-0");
			} catch (Exception e) {
				break;
			}
		}

		model.addAttribute("info", "success");

		return "json";
	}
	
	@RequestMapping(value = "/ajax/updateDirection")
	public String verifing(Model model, HttpSession session, HttpServletRequest request, @RequestParam("passIds") String passIds ,@RequestParam("noPassIds")String noPassIds) {
		Boolean flag=true;
		List<Integer> passIdList=null;
		List<Integer> noPassIdList=null;
		Type type=new TypeToken<List<Integer>>() {}.getType();
		Gson gson=new Gson();
		try{
			if(StringUtils.isNotBlank(passIds)){
				passIdList=gson.fromJson(passIds, type);
			}
			if(StringUtils.isNotBlank(noPassIds)){
				noPassIdList=gson.fromJson(noPassIds, type);
			}
			rentVerifyService.updateDirection(passIdList, noPassIdList);
		}catch (Exception e) {
			e.printStackTrace();
			flag=false;
		}
		model.addAttribute("result", flag);
		return "json";
	}

	@RequestMapping(value = "/verify/backtask")
	public String backtask(HttpServletRequest request, HttpSession session){
		SessionUser sessionUser = (SessionUser) session.getAttribute("sessionUser");
		Integer emptyNum=Globals.EMPTY_TASK_COUNT;
		List<Integer> userIdList = new ArrayList<Integer>();
		userIdList.add(sessionUser.getUser().getId());
		auditService.emptyAuditTask(userIdList, emptyNum);
		return "redirect:/login/index";
	}
	@RequestMapping(value = "/getDescription/{id}")
	public String getDescription(Model model,HttpServletRequest request, @PathVariable("id")int id){
		House house = rentService.selectRentById(id);
		model.addAttribute("house", house);
		return "/house/description";
	}

}
