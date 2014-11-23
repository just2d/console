package com.nuoshi.console.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.taofang.biz.domain.audit.estate.EstateAuditTask;
import com.taofang.biz.domain.estate.EstatePhotoUserUpload;
import com.taofang.biz.service.audit.IAuditEstateService;
import com.taofang.biz.util.BizServiceHelper;
import com.nuoshi.console.common.util.StrUtils;
import com.nuoshi.console.domain.photo.EstatePhotoUserUploadUrl;
import com.nuoshi.console.domain.topic.Locale;
import com.nuoshi.console.domain.user.User;
import com.nuoshi.console.service.CommPhotoService;
import com.nuoshi.console.service.EstateUpcService;
import com.nuoshi.console.service.LocaleService;
import com.nuoshi.console.view.EstatePhoto;
import com.nuoshi.console.view.EstatePhotoDetail;
import com.nuoshi.console.web.session.SessionUser;

@Controller
@RequestMapping("/estateupc")
public class EstateUpcController extends BaseController {
	
	private IAuditEstateService auditEstateService  = BizServiceHelper.getAuditEstateService();
	
	@Resource
	private EstateUpcService estateUpcService;

	@Resource
	CommPhotoService commPhotoService;
	/**
	 * 取单个小区文字审核
	 * 
	 * @return
	 */
	@RequestMapping("/getupctext")
	public String getupctext(Model model, HttpServletRequest request) {
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
		
		String cityIdStr = request.getParameter("cityid");
		int cityId = cityIdStr==null?0:Integer.valueOf(cityIdStr);
		Object[] objs=auditEstateService.getOneEstateAuditTask(cityId);
		model.addAttribute("cityid", cityIdStr);
		model.addAttribute("objs", objs);
		if(objs != null && objs.length ==2){
			com.taofang.biz.domain.estate.Estate now = (com.taofang.biz.domain.estate.Estate) objs[1];
			EstateAuditTask upc = (EstateAuditTask) objs[0];
			model.addAttribute("estate", now);
			model.addAttribute("upc", upc);
		}
		return "tiles:estateupc.getupctext";
	}
	
	
	/**
	 * 取单个小区文字审核
	 * 
	 * @return
	 */
	@RequestMapping("/submitupctext")
	public String submitupctext(Model model, HttpServletRequest request,HttpSession session,com.taofang.biz.domain.estate.Estate estate) {
		String cityIdStr = request.getParameter("cityid");
		String taskIdStr = request.getParameter("taskId");
		String checkCountStr = request.getParameter("checkCount");
		SessionUser sessionUser = (SessionUser) session.getAttribute("sessionUser");
		User user=null;
		if(sessionUser!=null){
			user=sessionUser.getUser();
		}
		estateUpcService.submitUpcText(taskIdStr,checkCountStr,user,estate);
		
		
		return "redirect:/estateupc/getupctext?cityid="+cityIdStr;
	}

	
	/**
	 * 取单个小区图片审核
	 * 
	 * @return
	 */
	@RequestMapping("/getupcphoto")
	public String getupcphoto(Model model, HttpServletRequest request) {
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
		
		String cityIdStr = request.getParameter("cityid");
		int cityId = cityIdStr==null?0:Integer.valueOf(cityIdStr);
		model.addAttribute("cityid", cityIdStr);
		List<EstatePhotoUserUpload> photoUpcList = auditEstateService.getPhotoAuditTaskForOneEstate(cityId);
		
		if(CollectionUtils.isNotEmpty(photoUpcList)){
			Map<Integer,EstatePhotoUserUpload> photoMap = new HashMap<Integer, EstatePhotoUserUpload>();
			List<EstatePhotoUserUploadUrl> pList =  new ArrayList<EstatePhotoUserUploadUrl>();
			for(EstatePhotoUserUpload estatePhoto:photoUpcList){
				photoMap.put(estatePhoto.getId(), estatePhoto);
				pList.add( new EstatePhotoUserUploadUrl(estatePhoto));
			}
			model.addAttribute("photoUpcList", pList);
			request.getSession().removeAttribute("photoMap");
			request.getSession().setAttribute("photoMap", photoMap);
			
			EstatePhotoUserUpload estate = photoUpcList.get(0);
			int estateId = estate.getEstateId();
			EstatePhoto condition = new EstatePhoto();
			condition.setEstateId(estateId);
			List<EstatePhotoDetail> photoList = commPhotoService.getCommPhotoList(condition);
			
			model.addAttribute("estate", estate);
			model.addAttribute("photoList", photoList);
		}
		
		return "tiles:estateupc.getupcphoto";
	}
	
	
	/**
	 * 提交单个小区图片审核
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/submitupcphoto")
	public String submitupcphoto(@RequestParam(value = "toUsingIds", required = false) String toUsingIds,
			@RequestParam(value = "unUsingIds", required = false) String unUsingIds,Model model, HttpServletRequest request) {
		List<Integer> idList = new ArrayList<Integer>();
		List<Integer> unUsingIdList = new ArrayList<Integer>();
		if (toUsingIds != null && toUsingIds.length() > 0) {
			idList = StrUtils.str2IntList(toUsingIds);
		}
		if (unUsingIds != null && unUsingIds.length() > 0) {
			unUsingIdList = StrUtils.str2IntList(unUsingIds);
		}
		
		String cityIdStr = request.getParameter("cityid");
		model.addAttribute("cityid", cityIdStr);
		SessionUser sessionUser = (SessionUser)  request.getSession().getAttribute("sessionUser");
		User user=null;
		if(sessionUser!=null){
			user=sessionUser.getUser();
		}
		Map<Integer,EstatePhotoUserUpload> photoMap = (Map<Integer, EstatePhotoUserUpload>) request.getSession().getAttribute("photoMap");
		request.getSession().removeAttribute("photoMap");
		estateUpcService.submitPhotoAuditTask(idList,unUsingIdList,photoMap,user);
		
		
		return "redirect:/estateupc/getupcphoto?cityid="+cityIdStr;
	}

}
