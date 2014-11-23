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
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
import com.nuoshi.console.domain.agent.HouseInfo;
import com.nuoshi.console.domain.agent.RejectReason;
import com.nuoshi.console.domain.auditHistory.HistoryInfo;
import com.nuoshi.console.domain.base.House;
import com.nuoshi.console.domain.house.HouseExtraScoreHistory;
import com.nuoshi.console.domain.resale.Resale;
import com.nuoshi.console.domain.resale.ResaleInfo;
import com.nuoshi.console.domain.topic.Locale;
import com.nuoshi.console.domain.user.User;
import com.nuoshi.console.service.AgentManageService;
import com.nuoshi.console.service.AuditService;
import com.nuoshi.console.service.HouseExtraScoreHistoryService;
import com.nuoshi.console.service.LocaleService;
import com.nuoshi.console.service.ResaleService;
import com.nuoshi.console.service.ResaleVerifyService;
import com.nuoshi.console.web.session.SessionUser;

@Controller
@RequestMapping(value = "/resale")
public class ResaleController extends BaseController {
	
	//session中存放待审核房源基本信息的key值
	private static final String resale_base_info= "resale_base_info";
	
	//session中存放待审核房源图片信息的key值
	private static final String resale_layout_photo_task= "resale_layout_photo_task";
	
	private static final String resale_indoor_photo_task= "resale_indoor_photo_task";
	
	private static final String resale_outdoor_photo_task= "resale_outdoor_photo_task";
	
	//session中存放某房源存在及拒绝的图片
	private static final String resale_exist_photo= "resale_exist_photo";
	
	private static final String resale_reject_photo= "resale_reject_photo";
	
	@Autowired
	private ResaleVerifyService resaleVerifyService;
	@Autowired
	private ResaleService resaleService;
	@Autowired
	private AgentManageService agentManageService;
	@Autowired
	private AuditService auditService;
	@Autowired
	private HouseExtraScoreHistoryService houseExtraScoreHistoryService;
	
	
	private Log log = LogFactory.getLog(ResaleController.class);
	/**
	 * 获取审核的的房源
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/verify/list")
	public String getResale4VerifyByPage(Model model, HttpSession session,
			HttpServletRequest request) {
		String authorid = request.getParameter("authorid");
		String name = request.getParameter("name");
		String title = request.getParameter("title");
		String mobile = request.getParameter("mobile");
		String id = request.getParameter("id");
		Boolean flag =true;
		String onlyBeiJing = request.getParameter("onlyBeiJing");
		if(StringUtils.isBlank(onlyBeiJing)){
			flag=false;
		}
		StringBuffer condition = new StringBuffer();
		HashMap params = null;
//		String[] checkResult = null;
//		String paraStr = null;
		if(StringUtils.isNotBlank(id)){
			id = id.trim();
			condition.append(" and r.id = '"+id+"'" );
		}
		if(StringUtils.isNotBlank(mobile)){
			mobile = mobile.trim();
			condition.append(" and r.author_phone = '"+mobile+"'" );
		}
		if (StringUtils.isNotBlank(authorid)) {
			authorid = authorid.trim(); 
			condition.append(" and r.authorid = '" + authorid+"'");
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
		 * 只审核北京房源
		 * wangjh 12-01 改
		 */
		if(flag){
			condition.append(" and r.city_id = "+Globals.BEIJINGID+" ");
		}
		
		if (StringUtils.isNotBlank(condition.toString())) {
			params = new HashMap<String, Object>();
			params.put("condition", condition);
		}
		List<RejectReason> reasons = resaleVerifyService.getAllRejectReason(3);// 二手房退回理由
		List<RejectReason> deleteReasons = resaleVerifyService
				.getAllRejectReason(4);// 二手房删除理由
		List<ResaleInfo> resaleList = resaleVerifyService.getResale4VerifyByPage(params);
//		for(ResaleInfo para : resaleList){
//			checkResult = agentManageService.checkSensitiveWord(para.getTitle());
//			if(checkResult != null && checkResult.length > 0) {
//				paraStr = para.getTitle();
//				for(String sensitive : checkResult) {
//					paraStr = paraStr.replaceAll(sensitive.trim(), "<span style='color:red'>" + sensitive.trim() + "</span>");
//				}
//				para.setTitle(paraStr);
//			}
//			checkResult = agentManageService.checkSensitiveWord(para.getExtinfo());
//			if(checkResult != null && checkResult.length > 0) {
//				paraStr = para.getExtinfo();
//				for(String sensitive : checkResult) {
//					paraStr = paraStr.replaceAll(sensitive.trim(), "<span style='color:red'>" + sensitive.trim() + "</span>");
//				}
//				para.setExtinfo(paraStr);
//			}
//		}
		model.addAttribute("reasons", reasons);
		model.addAttribute("deleteReasons", deleteReasons);
		model.addAttribute("resaleList", resaleList);
		return "tiles:resale.verify";

	}
	/**
	 * 获取审核的租房的基本信息
	 */
	@RequestMapping(value = "/verify/baseInfoList")
	public String baseInfoList(Model model, HttpSession session,
			HttpServletRequest request) {
		SessionUser sessionUser = (SessionUser) session.getAttribute("sessionUser");
		User user=null;
		if(sessionUser!=null){
			user=sessionUser.getUser();
		}
		// 将简化版的城市List放入application中.
		ServletContext application = request.getSession().getServletContext();
		Map<Integer, Locale> map;
		if (null==application.getAttribute("simpleLocaleMap")) {
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
		String cityIdStr=request.getParameter("cityid");
		Integer cityId=1;
		if(StringUtils.isNotBlank(cityIdStr)){
			cityId=Integer.valueOf(cityIdStr);
			if(cityId<0) cityId =0;
		}
		List<RejectReason> reasons = resaleVerifyService.getAllRejectReason(3);// 二手房退回理由
		List<RejectReason> deleteReasons = resaleVerifyService.getAllRejectReason(4);// 二手房删除理由
		List<HouseTitleDescAuditTask> resaleList = null;
		if (sessionUser != null) {
			user=sessionUser.getUser();
		} else {
			log.error("用户未登录！");
		}
		try {

			// 页面的房源id和打回原因的对应关系
			String[] idAndReasonArr = request.getParameterValues("reason");
			log.debug(idAndReasonArr);
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
						rejectIdAndReason.put(Integer.valueOf(divisionStr[0]),
								divisionStr[1]);
					}
				}
			}
			@SuppressWarnings("unchecked")
			List<HouseTitleDescAuditTask> sessionResaleList = (List<HouseTitleDescAuditTask>) session.getAttribute(resale_base_info);
			auditService.auditBaseInfo(passHouseId,rejectIdAndReason,HouseConstant.RESALE,user,sessionResaleList);
			session.removeAttribute(resale_base_info);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("baseInfoList", e);
		}
		try {
			resaleList = BizServiceHelper.getAuditHouseService().getResaleTitleDescAuditTasks(cityId, Globals.AUDIT_HOUSE_COUNT);
			//删除之前领取的基本信息和图片信息
			session.removeAttribute(resale_base_info);
			//将基本信息放入session
			if(CollectionUtils.isNotEmpty(resaleList)){
				session.setAttribute(resale_base_info, resaleList);
			}
			int notPassedPhotoCount =0;
			notPassedPhotoCount = BizServiceHelper.getAuditHouseService().getResaleTasksAmount(AuditConstant.BASIC_INFO_TASK);
			model.addAttribute("notPassedPhotoCount", notPassedPhotoCount);
			
			model.addAttribute("reasons", reasons);
			model.addAttribute("deleteReasons", deleteReasons);
			model.addAttribute("cityid",cityId);
			model.addAttribute("resaleList", resaleList);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("baseInfoList", e);
		}

		String postSubmit = request.getParameter("postSubmit");
		if(StringUtils.isNotBlank(postSubmit)){
			return "redirect:/resale/verify/baseInfoList";
		}
		return "tiles:resale.verify.baseInfoList";

	}
	/**
	 * 刷新页面
	 */
	@RequestMapping(value = "/verify/refreshBaseInfoPage")
	public String refreshBaseInfoPage(Model model, HttpSession session,
			HttpServletRequest request){
		Integer cityId;
		List<RejectReason> reasons;
		List<RejectReason> deleteReasons;
		List<HouseTitleDescAuditTask> resaleList;
		try {

			String cityIdStr=request.getParameter("cityid");
			cityId = 1;
			if(StringUtils.isNotBlank(cityIdStr)){
				cityId=Integer.valueOf(cityIdStr);
				if(cityId<0)  cityId =0;
			}
			log.debug("cityId = "+cityId);
			reasons = resaleVerifyService.getAllRejectReason(3);
			deleteReasons = resaleVerifyService.getAllRejectReason(4);
			resaleList = null;
			//获取审核列表
			// 获取审核列表
			resaleList = BizServiceHelper.getAuditHouseService().getResaleTitleDescAuditTasks(cityId, Globals.AUDIT_HOUSE_COUNT);
			//删除之前领取的基本信息和图片信息
			session.removeAttribute(resale_base_info);
			//将基本信息放入session
			if(CollectionUtils.isNotEmpty(resaleList)){
				session.setAttribute(resale_base_info, resaleList);
			}
			
			int notPassedPhotoCount =0;
			notPassedPhotoCount = BizServiceHelper.getAuditHouseService().getResaleTasksAmount(AuditConstant.BASIC_INFO_TASK);
			model.addAttribute("notPassedPhotoCount", notPassedPhotoCount);
			
			if(resaleList!=null){
				log.debug("resaleList.size() = "+resaleList.size());
			}

			model.addAttribute("reasons", reasons);
			model.addAttribute("deleteReasons", deleteReasons);
			model.addAttribute("resaleList", resaleList);
			model.addAttribute("cityid",cityId);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			log.error(e);
		}


	return "tiles:resale.verify.baseInfoList";
	}

	/**
	 * 获取审核的的房源
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/verify/allresale/list")
	public String getAllResale4VerifyByPage(Model model, HttpSession session,
			HttpServletRequest request) {
		// 将简化版的城市List放入application中.
		ServletContext application = request.getSession().getServletContext();
		Map<Integer, Locale> map;
		if (null==application.getAttribute("simpleLocaleMap")) {
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
		String authorid = request.getParameter("authorid");
		String name = request.getParameter("name");
		String title = request.getParameter("title");
		String id = request.getParameter("id");
		String cityId = request.getParameter("cityId");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String houselabel = request.getParameter("houselabel");
		StringBuffer condition = new StringBuffer();
		HashMap params = null;
		String typeStr = request.getParameter("type");
		int type =0;
		if(StringUtils.isNotBlank(typeStr)){
			type= Integer.parseInt(typeStr); ;
		}
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
			if(StringUtils.isNotBlank(id)){
				id = id.trim();
				condition.append(" and r.id = '"+id+"'" );
			}
			if(StringUtils.isNotBlank(mobile)){
				mobile = mobile.trim();
				condition.append(" and r.author_phone = '"+mobile+"'" );
			}
			if (StringUtils.isNotBlank(authorid)) {
				authorid = authorid.trim(); 
				condition.append(" and r.authorid = '" + authorid+"'");
			}
			if (StringUtils.isNotBlank(name)) {
				name = name.trim();
				condition.append(" and r.author_name like '%" + name + "%'");
			}
			if (StringUtils.isNotBlank(title)) {
				title = title.trim();
				condition.append(" and r.title like '%" + title + "%'");
			}
			if (StringUtils.isNotBlank(houselabel)) {
				houselabel = houselabel.trim();
				condition.append(" and r.house_label like '%" + houselabel + "%'");
			}
			
			if(StringUtils.isNotBlank(cityId) && Integer.parseInt(cityId) > 0) {
				condition.append(" and r.city_id = " + Integer.parseInt(cityId));
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
		List<RejectReason> reasons = resaleVerifyService.getAllRejectReason(3);// 二手房退回理由
		List<RejectReason> deleteReasons = resaleVerifyService.getAllRejectReason(4);// 二手房删除理由
		model.addAttribute("reasons", reasons);
		model.addAttribute("deleteReasons", deleteReasons);
		List<ResaleInfo> resaleList = resaleVerifyService.getAllResale4VerifyByPage(params);
		model.addAttribute("resaleList", resaleList);
		model.addAttribute("cityId", cityId == null ? 0 : cityId);
		return "tiles:allresale.verify";

	}
	@RequestMapping(value = "/verify/allresale/list/home")
	public String toHouseListPage(Model model, HttpSession session,
			HttpServletRequest request) {
		model.addAttribute("reasons", null);
		model.addAttribute("deleteReasons", null);
		model.addAttribute("resaleList", new ArrayList<ResaleInfo>());
		model.addAttribute("cityId", 0);
		return "tiles:allresale.verify";
		
	}
	

	@RequestMapping(value = "/house/photo/{houseId}/{category}")
	public String getHousePhoto(Model model, HttpSession session,
			@PathVariable("houseId") int houseId,
			@PathVariable("category") String category) {
		List<Integer> photoids = new ArrayList<Integer>();
		IHousePhotoService housePhotoService = BizServiceHelper.getHousePhotoService();
		Resale resale = resaleService.selectResaleById(houseId);
		List<com.taofang.biz.domain.house.HousePhoto> housePhotoList = housePhotoService.getResalePhotos(houseId);
		
		List<com.taofang.biz.domain.house.HousePhoto> rejectPhotoList = housePhotoService.getRejectedPhotos(resale.getCityid(), HouseConstant.RESALE, houseId);
		
		List<HistoryInfo> reauditHistory = auditService.getReAuditHistory(houseId,HouseConstant.RESALE);
		
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
				rejectPhoto.setEstateId(resale.getEstateid());
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
					housePhoto.setCoverFlag(Resources.getString("house.decoration."+resale.getDecoration()));
				}
			}
			
			if(rejectPhotoList != null && rejectPhotoList.size()>0){
				for(com.taofang.biz.domain.house.HousePhoto rejectPhoto:rejectPhotoList){
					rejectPhoto.setCoverFlag(Resources.getString("house.decoration."+resale.getDecoration()));
				}
			}
			
		}
		
		session.setAttribute(resale_exist_photo, housePhotoList);
		session.setAttribute(resale_reject_photo, rejectPhotoList);
		
		List<RejectReason> reasons = resaleVerifyService.getAllRejectReason(Globals.PHOTO_DELETE_REASON);// 图片删除理由
		
		model.addAttribute("houseType", HouseConstant.RESALE);
		model.addAttribute("houseId", houseId);
		model.addAttribute("photoids", photoids);
		model.addAttribute("category", category);
		model.addAttribute("reasons", reasons);
		model.addAttribute("housePhotoList", housePhotoList);
		model.addAttribute("rejectPhotoList", rejectPhotoList);
		return "/house/photo/show";

	}

	@RequestMapping(value = "/house/{citydir}/{id}")
	public String listHouse(Model model,
			@PathVariable("citydir") String citydir, @PathVariable("id") int id) {
		List<HouseInfo> houseList = agentManageService.getAllHouseByPage(id, 0);
		String agentname = agentManageService.getAgentInfo(id).getName();
		model.addAttribute("houseList", houseList);
		model.addAttribute("citydir", citydir);
		model.addAttribute("id", id);
		model.addAttribute("name", agentname);
		return "tiles:agentManage.house";
	}

	/**
	 * 获取经纪人房源信息，以列表弐展示：房源标题、户型图、室内图、小区图
	 */
	@RequestMapping(value = "/house/{authorId}")
	public String listHouse(Model model, @PathVariable("authorId") int authorId) {
		
		List<RejectReason> reasons = resaleVerifyService.getAllRejectReason(3);// 二手房退回理由
		List<RejectReason> deleteReasons = resaleVerifyService.getAllRejectReason(4);// 二手房删除理由
		model.addAttribute("reasons", reasons);
		model.addAttribute("deleteReasons", deleteReasons);
		List<ResaleInfo> resaleInfoList = resaleVerifyService.getAllResale(authorId);
		AgentManage agent = agentManageService.getAgentInfo(authorId);
		model.addAttribute("agent", agent);
		model.addAttribute("resaleInfoList", resaleInfoList);
		model.addAttribute("authorId", authorId);
		return "tiles:resale.house.list";
	}

	/**
	 * 
	 */
	@RequestMapping(value = "/verifyReq")
	public String verify(HttpServletRequest request, HttpSession session,
			Model model) {
		SessionUser sessionUser = (SessionUser) session.getAttribute("sessionUser");
		User user =sessionUser.getUser();
		int ret0= -1;
		String  id = request.getParameter("id");
		String  type = request.getParameter("type");
		String  reasons = request.getParameter("reasons");

		HashMap<String, Object> params = new HashMap<String, Object>();
		if (StringUtils.isNotBlank(reasons)) {
			params.put("reasons", reasons);
		}
		if ("2".equals(type)) {// 审核通过
			ret0 = resaleVerifyService.approve(Integer.parseInt(id),user);
		}else if("1".equals(type)){//删除房源
			
		}else if("0".equals(type)){//退回
			ret0 = resaleVerifyService.reject(Integer.parseInt(id),reasons,user);
		}
	
		if(ret0>=0){
			model.addAttribute("info", "success");
		} else {
			model.addAttribute("info", "error");
		}
		try {
			ApplicationUtil.getHttpResponse(Resources.getString("taofang.service.url") + "ershoufang/refresh/" +id+"-0");
		} catch (Exception e) {
				
		}
		return "json";
	}
	/**
	 * 删除图片
	 * @param request
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/photo/delete")
	public String delPhoto(HttpServletRequest request, HttpSession session,
			Model model) {

		
		String  houseid = request.getParameter("houseid");
		String  photoid = request.getParameter("photoid");
		String  category = request.getParameter("category");
		try {
			resaleVerifyService.deleteHousePhoto(Integer.parseInt(houseid),Integer.parseInt(photoid),Integer.parseInt(category));
			model.addAttribute("info", "success");
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("info", "error");
		}
		try {
			ApplicationUtil.getHttpResponse(Resources.getString("taofang.service.url") + "ershoufang/refresh/" +houseid+"-0");
		} catch (Exception e) {
				
		}
		return "json";
	}
	
	@RequestMapping(value = "/verifyReq/batch")
	public String verifyBatch(HttpServletRequest request, HttpSession session, Model model) {

		SessionUser sessionUser = (SessionUser) session.getAttribute("sessionUser");
		User user =sessionUser.getUser();
		
		String  id = request.getParameter("ids");
		if(StringUtils.isBlank(id)){
			model.addAttribute("error", "请至少选择一个房源!");
			return "json";
		}
		String[] ids  = id.split(",");
		String  type = request.getParameter("type");
		String  reasons = request.getParameter("reasons");

		HashMap<String, Object> params = new HashMap<String, Object>();
		if (StringUtils.isNotBlank(reasons)) {
			params.put("reasons", reasons);
		}
		if ("2".equals(type)) {// 审核通过
			for (int i = 0; i < ids.length; i++) {
				resaleVerifyService.approve(Integer.parseInt(ids[i]),user);
			}
		}else if("1".equals(type)){//删除房源
			
		}else if("0".equals(type)){//退回
			for (int i = 0; i < ids.length; i++) {
				resaleVerifyService.reject(Integer.parseInt(ids[i]),reasons,user);
			}
		}
	
		for (int i = 0; i < ids.length; i++) {
			try {
				ApplicationUtil.getHttpResponse(Resources.getString("taofang.service.url") + "ershoufang/refresh/" +id+"-0");
			} catch (Exception e) {
					break;
			}
		}
		model.addAttribute("info", "success");
		
		return "json";
	}
	
	
	// 审核二手房图片信息
	@RequestMapping(value = "/verify/list/verifyPhoto/{type}")
	public String verifing(Model model,HttpSession session,HttpServletRequest request,@PathVariable("type") String type){

		// 将简化版的城市List放入application中.
		ServletContext application = request.getSession().getServletContext();
		Map<Integer, Locale> map;
		if (null==application.getAttribute("simpleLocaleMap")) {
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
		
		List<RejectReason> reasons = resaleVerifyService.getAllRejectReason(Globals.PHOTO_DELETE_REASON);// 图片删除理由
		String cityIdStr=request.getParameter("cityid");
		String circleCount=request.getParameter("circleCount");
		if(StringUtils.isBlank(circleCount)){
			circleCount="1";
		}else {
			circleCount=Integer.parseInt(circleCount)+1+"";
		}
		Integer cityId=1;
		if(StringUtils.isNotBlank(cityIdStr)){
			cityId=Integer.valueOf(cityIdStr);
			if(cityId<0) cityId =0;
		}
		// 已经循环了5次
		if(Integer.parseInt(circleCount)==6){
			model.addAttribute("reasons", reasons);
			model.addAttribute("circleCount", circleCount);
			model.addAttribute("cityid", cityId);
			model.addAttribute("type", Integer.parseInt(type));
			return "tiles:resale.verifyPhoto";
		}
		int notPassedPhotoCount =0;
		List<HousePhotoAuditTask> resaleList = null;
		//取数据
		switch (Integer.parseInt(type)) {
		case 1:
			resaleList = BizServiceHelper.getAuditHouseService().getResaleLayoutPhotoAuditTasks(cityId, Globals.AUDIT_HOUSE_COUNT*2);
			session.removeAttribute(resale_layout_photo_task);
			session.setAttribute(resale_layout_photo_task, resaleList);
			notPassedPhotoCount = BizServiceHelper.getAuditHouseService().getResaleTasksAmount(AuditConstant.LAYOUT_PHOTO_TASK);
			break;
		case 2:
			resaleList = BizServiceHelper.getAuditHouseService().getResaleIndoorPhotoAuditTasks(cityId, Globals.AUDIT_HOUSE_COUNT/2);
			session.removeAttribute(resale_indoor_photo_task);
			session.setAttribute(resale_indoor_photo_task, resaleList);
			notPassedPhotoCount = BizServiceHelper.getAuditHouseService().getResaleTasksAmount(AuditConstant.INDOOR_PHOTO_TASK);
			break;
		case 3:
			resaleList = BizServiceHelper.getAuditHouseService().getResaleOutdoorPhotoAuditTasks(cityId, Globals.AUDIT_HOUSE_COUNT);
			session.removeAttribute(resale_outdoor_photo_task);
			session.setAttribute(resale_outdoor_photo_task, resaleList);
			notPassedPhotoCount = BizServiceHelper.getAuditHouseService().getResaleTasksAmount(AuditConstant.OUTDOOR_PHOTO_TASK);
			break;
		}
		List<Integer> taskIdList = new ArrayList<Integer>();
		// houseidList
		List<Integer> houseIdList = new ArrayList<Integer>();
		List<com.taofang.biz.domain.house.HousePhoto> housePhotoList = new ArrayList<com.taofang.biz.domain.house.HousePhoto>();
		// photoidList
		List<Integer> photoids = new ArrayList<Integer>();
		for (HousePhotoAuditTask resaleTask : resaleList) {
			taskIdList.add(resaleTask.getTaskId());
			if(!houseIdList.contains(resaleTask.getHouseId())){
				houseIdList.add(resaleTask.getHouseId());
			}
			for(com.taofang.biz.domain.house.HousePhoto housePhoto:resaleTask.getPhotos()){
				photoids.add(housePhoto.getId());
				housePhotoList.add(housePhoto);
			}
		}
		
		
		
		//室内图取装修情况
		if("2".equals(type)){
			housePhotoList =  resaleVerifyService.getDecorationByPhotos(housePhotoList);
		}

		
		model.addAttribute("notPassedPhotoCount", notPassedPhotoCount);
		// 所有照片List
		model.addAttribute("resaleList", housePhotoList);
		model.addAttribute("taskids", taskIdList);
		// 所有房源id
		model.addAttribute("houseIdList", houseIdList);
		model.addAttribute("reasons", reasons);
		model.addAttribute("photoids", photoids);
		model.addAttribute("cityid", cityId);
		model.addAttribute("type", Integer.parseInt(type));
		return "tiles:resale.verifyPhoto";
		
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/verify/photos")
	public String verifyPhotos(Model model,HttpSession session,HttpServletRequest request){
		String blockList = request.getParameter("blockList");
		String houseIdList = request.getParameter("houseIdList");
		String photoids = request.getParameter("photoids");
		String taskids = request.getParameter("taskids");
		String type = request.getParameter("type");
		List<HousePhotoAuditTask> resaleList = null;
		
		if("1".equals(type.trim())){
			resaleList = (List<HousePhotoAuditTask>) session.getAttribute(resale_layout_photo_task);
		}else if("2".equals(type.trim())){
			resaleList = (List<HousePhotoAuditTask>) session.getAttribute(resale_indoor_photo_task);
		}else if("3".equals(type.trim())){
			resaleList = (List<HousePhotoAuditTask>) session.getAttribute(resale_outdoor_photo_task);
		}
		 
		try {
			auditService.verifyPhotos(houseIdList, blockList, photoids, Integer.parseInt(type), (SessionUser) session.getAttribute(SmcConst.SESSION_USER), HouseConstant.RESALE,resaleList,taskids);
			model.addAttribute("info", "success");
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("info", "err");
		}
		if("1".equals(type.trim())){
			session.removeAttribute(resale_layout_photo_task);
		}else if("2".equals(type.trim())){
			session.removeAttribute(resale_indoor_photo_task);
		}else if("3".equals(type.trim())){
			session.removeAttribute(resale_outdoor_photo_task);
		}
		
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
				 resaleService.addExtraScore(houseId,score,sessionUser.getUser().getUserName());
				model.addAttribute("info", "success");
			 }catch (Exception e) {
				 e.printStackTrace();
				 model.addAttribute("error", "请填写正确的数据");
			}
			
			 
		 }
		
		
		return "json";
	}
	@RequestMapping(value="/getExtraScoreHistory/{houseId}")
	public String getExtraScoreHistory(Model model,HttpSession session,HttpServletRequest request,@PathVariable("houseId") int houseId){
		 
		List<HouseExtraScoreHistory> houseExtraScoreHistorys = houseExtraScoreHistoryService.getByHouseId(houseId, HouseConstant.RESALE);
		model.addAttribute("houseExtraScoreHistorys", houseExtraScoreHistorys);
		
		return "json";
	}
	

	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/verify/reauditphotos")
	public String reauditPhotos(Model model,HttpSession session,HttpServletRequest request){
		String blockList = request.getParameter("blockList");
		String photoids = request.getParameter("photoids");
		String resumes = request.getParameter("resumes");
		String houseId = request.getParameter("houseId");
		
		List<HousePhoto> existPhotoList = (List<HousePhoto>) session.getAttribute(resale_exist_photo);
		List<HousePhoto> rejectPhotoList = (List<HousePhoto>) session.getAttribute(resale_reject_photo);
		
		 
		try {
			auditService.reauditPhotos(houseId,blockList, photoids,resumes, (SessionUser) session.getAttribute(SmcConst.SESSION_USER), HouseConstant.RESALE,existPhotoList,rejectPhotoList);
			model.addAttribute("info", "success");
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("info", "err");
		}
		session.removeAttribute(resale_exist_photo);
		session.removeAttribute(resale_reject_photo);
		
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
			resaleVerifyService.updateDirection(passIdList, noPassIdList);
		}catch (Exception e) {
			e.printStackTrace();
			flag=false;
		}
		model.addAttribute("result", flag);
		return "json";
	}
	@RequestMapping(value = "/getDescription/{id}")
	public String getDescription(Model model,HttpServletRequest request, @PathVariable("id")int id){
		House house = resaleService.selectResaleById(id);
		model.addAttribute("house", house);
		return "/house/description";
	}
	
}
