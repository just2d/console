package com.nuoshi.console.web.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.taofang.biz.local.AgentPhotoUrlUtil;
import com.nuoshi.console.common.Globals;
import com.nuoshi.console.common.util.SmcConst;
import com.nuoshi.console.domain.agent.AgentMaster;
import com.nuoshi.console.domain.agent.PhoneVerifyLog;
import com.nuoshi.console.domain.agent.RejectReason;
import com.nuoshi.console.domain.topic.Locale;
import com.nuoshi.console.service.AgentMasterService;
import com.nuoshi.console.service.AuditService;
import com.nuoshi.console.service.LocaleService;
import com.nuoshi.console.web.form.AgentVerifyResultForm;
import com.nuoshi.console.web.session.SessionUser;


@Controller
@RequestMapping(value = "/agentVerify")
public class AgentVerifyController extends BaseController {
	@Autowired
	private AgentMasterService agentVerifyService;
	@Autowired
	private AuditService auditService;
	
	/**
	 * 显示待审核经纪人信息列表
	 * 
	 * @param admin
	 * @return
	 */
	@RequestMapping(value = "/list")
	public String list(HttpServletRequest request ,Model model) {
		String errorMsg = "";
		List<AgentMaster> ls = agentVerifyService.getAllAgentMasterByPage();
		if(ls != null && ls.size() > 0) {
			for(AgentMaster view : ls) {
				view.setIdCardImg(AgentPhotoUrlUtil.getAgentIDCardPhotoFullUrl(view.getCityId(), view.getAgentId()));
				view.setHeadImg(AgentPhotoUrlUtil.getPendingAgentHeadPortraitFullUrlLarge(view.getCityId(), view.getAgentId()));
				view.setNameCardImg(AgentPhotoUrlUtil.getAgentBusinessCardPhotoFullUrl(view.getCityId(), view.getAgentId()));
			}
		}
		
		List<String> idCardReason = new ArrayList<String>();
		List<String> headReason = new ArrayList<String>();
		List<String> nameCardReason = new ArrayList<String>();
		List<RejectReason> reasons = agentVerifyService.getAllRejectReason();
		for(RejectReason reason : reasons) {
			if(reason.getType() == 0) {
				idCardReason.add(reason.getReason());
			} else if(reason.getType() == 1) {
				headReason.add(reason.getReason());
			} else if(reason.getType() == 2) {
				nameCardReason.add(reason.getReason());
			}
		}
		
		model.addAttribute("verifyList",ls);
		model.addAttribute("idCardReason", idCardReason);
		model.addAttribute("headReason", headReason);
		model.addAttribute("nameCardReason", nameCardReason);
		model.addAttribute("errorMsg",errorMsg);
		model.addAttribute("agentIdSearch", "请输入搜索内容");
		model.addAttribute("agentMobile","请输入搜索内容");
		return "tiles:agentVerify.list";
	}
	
	@RequestMapping(value = "/search/{id}/{mobile}/{type}")
	public String search(HttpServletRequest request,Model model, @PathVariable("id")String id, @PathVariable("mobile")String mobile, @PathVariable("type")String type) {
		List<AgentMaster> ls = null;
		String errorMsg = "";
		//获取页面是否只查询北京的条件
		String onlyBeiJing=request.getParameter("onlyBeiJing");
		Boolean flag=true;
		if(StringUtils.isBlank(onlyBeiJing)){
			flag=false;
		}
		List<String> conditions = new ArrayList<String>();
		conditions.add("am.mobile_check_status = 2");
		if("verify".equalsIgnoreCase(type)) {
			conditions.add("am.status in ('221','231','321','331','121','131','211','311')");
		}
		if(!"0".equals(id)) {
			conditions.add("u.id like '%"+id+"%'");
		}
		if(!"=".equals(mobile)) {
			conditions.add("u.mobile like '%"+mobile.trim()+"%'");
		}
		/**
		 * 只审核北京经纪人
		 * wangjh 12-02 改
		 */
		if(flag){
			conditions.add(" u.cityid = "+Globals.BEIJINGID+" ");
		}
		
		ls = agentVerifyService.getAllAgentMasterByPage(conditions);
		
		if(ls != null && ls.size() > 0) {
			for(AgentMaster view : ls) {
				view.setIdCardImg(AgentPhotoUrlUtil.getAgentIDCardPhotoFullUrl(view.getCityId(), view.getAgentId())+"?"+new Date().getTime());
				view.setHeadImg(AgentPhotoUrlUtil.getPendingAgentHeadPortraitFullUrlLarge(view.getCityId(), view.getAgentId())+"?"+new Date().getTime());
				view.setNameCardImg(AgentPhotoUrlUtil.getAgentBusinessCardPhotoFullUrl(view.getCityId(), view.getAgentId())+"?"+new Date().getTime());
			}
		}
		
		List<String> idCardReason = new ArrayList<String>();
		List<String> headReason = new ArrayList<String>();
		List<String> nameCardReason = new ArrayList<String>();
		List<RejectReason> reasons = agentVerifyService.getAllRejectReason();
		for(RejectReason reason : reasons) {
			if(reason.getType() == 0) {
				idCardReason.add(reason.getReason());
			} else if(reason.getType() == 1) {
				headReason.add(reason.getReason());
			} else if(reason.getType() == 2) {
				nameCardReason.add(reason.getReason());
			}
		}
		
		model.addAttribute("verifyList",ls);
		model.addAttribute("idCardReason", idCardReason);
		model.addAttribute("headReason", headReason);
		model.addAttribute("nameCardReason", nameCardReason);
		model.addAttribute("errorMsg",errorMsg);
		model.addAttribute("agentIdSearch", "0".equals(id) ? "请输入搜索内容" : id);
		model.addAttribute("agentMobile", "=".endsWith(mobile) ? "请输入搜索内容": mobile);
		request.setAttribute("onlyBeiJing",flag?"true":"false");
		return "tiles:agentVerify.list";
	}
	
	@RequestMapping(value="/verifyReq")
	public String verify(HttpServletRequest request, HttpSession session, Model model) {
		SessionUser sessionUser  = (SessionUser) session.getAttribute(SmcConst.SESSION_USER);
		String verifyResult = request.getParameter("verifyResult");
		if(verifyResult == null) {
			model.addAttribute("verifyInfo","没有传入正确的信息。");
			return "json";
		}
		AgentVerifyResultForm formItem = new AgentVerifyResultForm();
		String[] items = verifyResult.split("_");
		formItem.setAgentId(Integer.parseInt(items[0]));
		formItem.setIdCardResult(Integer.parseInt(items[1]));
		formItem.setIdCardMsg(items[2]);
		formItem.setHeadResult(Integer.parseInt(items[3]));
		formItem.setHeadMsg(items[4]);
		String verifyRes = agentVerifyService.doVerify(formItem, sessionUser.getUser().getId());
		model.addAttribute("verifyInfo", verifyRes);
		return "json";
	}
	
	@RequestMapping(value="/rejectReason")
	public String rejectReasonList(HttpServletRequest request, Model model) {
		int type = -1;
		int addRecord = 0;
		List<RejectReason> reasons = null;
		String typeString = request.getParameter("type");
		String addRecordStr = request.getParameter("addRecord");
		if(typeString != null) {
			type = Integer.parseInt(typeString);
		}
		if(addRecordStr != null) {
			addRecord = Integer.parseInt(addRecordStr);
		}
		if(type == -1) {
			reasons = agentVerifyService.getAllRejectReason2ByPage();
		} else {
			reasons =  agentVerifyService.getAllRejectReasonByPage(type);
		}
		model.addAttribute("addRecord", addRecord);
		model.addAttribute("reasons",reasons);
		return "tiles:agentVerify.rejectReason";
	}
	
	@RequestMapping(value="/addRejectReason")
	public String addRejectReason(HttpServletRequest request, Model model) {
		String inputRecord = request.getParameter("inputRecord");
		if(inputRecord != null) {
			String[] record = inputRecord.split("_");
			RejectReason rejectReason = new RejectReason();
			rejectReason.setType(Integer.parseInt(record[0]));
			rejectReason.setReason(record[1]);
			agentVerifyService.addRejectReason(rejectReason);
		}
		return "redirect:/agentVerify/rejectReason?addRecord=1";
	}
	
	@RequestMapping(value="/updateRejectReason")
	public String updateRejectReason(HttpServletRequest request, Model model) {
		String updateRecord = request.getParameter("updateRecord");
		if(updateRecord != null) {
			String[] record = updateRecord.split("_");
			RejectReason rejectReason = new RejectReason();
			rejectReason.setId(Integer.parseInt(record[0]));
			rejectReason.setType(Integer.parseInt(record[1]));
			rejectReason.setReason(record[2]);
			agentVerifyService.updateRejectReason(rejectReason);
		}
		return "redirect:/agentVerify/rejectReason";
	}
	
	@RequestMapping(value="/delRejectReason")
	public String delRejectReason(HttpServletRequest request, Model model) {
		String idStr = request.getParameter("delReasonId");
		if(idStr != null) {
			agentVerifyService.delRejectReason(Integer.parseInt(idStr));
		}
		return "redirect:/agentVerify/rejectReason";
	}
	
	@RequestMapping(value = "/connect400phone/{id}/{number}", method = RequestMethod.GET)
	public String editAgent(Model model, @PathVariable("id") int id, @PathVariable("number")String number) {
		String connectResult = agentVerifyService.connect400Phone(id, number);
		model.addAttribute("connectResult", connectResult);
		return "json";
	}
	
	/**
	 * 经纪人图片列表
	 * @param model
	 * @return
	 * @date 2012-1-4下午2:27:15
	 * @author dongyj
	 */
	@RequestMapping(value = "/photoList")
	public String verifyPhotosList(Model model, HttpServletRequest request) {
		String cityid = request.getParameter("cityid");
		Integer cityId = 1;
		if(StringUtils.isNotBlank(cityid)){
			cityId = Integer.parseInt(cityid);
		}
		SessionUser sessionUser = (SessionUser) request.getSession().getAttribute(SmcConst.SESSION_USER);
		Integer userId=sessionUser.getUser().getId();
		putSimpleLocaleMapInApplication(request);
		List<RejectReason> badIdreasons = agentVerifyService.getAllRejectReasonByType(0);// 退回身份证照片理由
		List<RejectReason> badHeadreasons = agentVerifyService.getAllRejectReasonByType(1);// 退回头像理由

		// 得到页面需要的数据
		List<AgentMaster> agentMasters2 = new ArrayList<AgentMaster>();
		agentMasters2=auditService.getAgentMasterTask(Globals.AUDIT_AGENT_COUNT,userId , cityId);
		
		model.addAttribute("cityid", cityId);
		model.addAttribute("list", agentMasters2);
		model.addAttribute("badIdreasons", badIdreasons);
		model.addAttribute("badHeadreasons", badHeadreasons);
		return "tiles:agentVerify.photoList";
	}
	
	/**
	 * 处理经纪人头像
	 * @param request
	 * @param model
	 * @return
	 * @date 2012-1-5下午1:32:52
	 * @author dongyj
	 */
	@RequestMapping(value="/verify/photos")
	public String verifyPhotos(HttpServletRequest request, Model model) {
		SessionUser sessionUser = (SessionUser) request.getSession().getAttribute(SmcConst.SESSION_USER);
		String totalResult = request.getParameter("totalResult");
		if (StringUtils.isNotBlank(totalResult)) {
			try {
				agentVerifyService.verifyPhotos(totalResult,sessionUser);
				model.addAttribute("info","scs");
			} catch (Exception e) {
				log.error(e);
				e.printStackTrace();
				model.addAttribute("info", e.getMessage());
			}
		}
		return "json";
	}
	
	/**
	 * 手动验证手机号码列表
	 * @param request
	 * @param model
	 * @return
	 * @date 2012-1-5下午1:25:04
	 * @author dongyj
	 */
	@RequestMapping(value="/verifyMobileList/{type}")
	public String verifyMobileList(HttpServletRequest request, Model model,@PathVariable("type") String type) {
		if(StringUtils.isNotBlank(type)){
			model.addAttribute("type", type);
			if(type.equals("2")){
				return verifyMobileHistory(request, model);
			}
		}
		HashMap<String, Object> params = new HashMap<String, Object>();
		StringBuffer conditions = new StringBuffer();
		String agentId = request.getParameter("agentId");
		String phone = request.getParameter("phone");
		List<AgentMaster> agentMasters = new ArrayList<AgentMaster>();
		try {
			agentVerifyService.consistQuery(agentId, phone, conditions, params, model);
			agentMasters = agentVerifyService.getVerifyMobileAgentByPage(params);
			for (AgentMaster agentMaster : agentMasters) {
				agentMaster.setIdcardNumber("");
				if (agentMaster.getSubmitMobileDate() != null) {
					SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
					agentMaster.setIdcardNumber(simpleDateFormat.format(agentMaster.getSubmitMobileDate()));
				}
			}
		} catch (Exception e) {
			log.error(e);
			model.addAttribute("err", "查询条件输入错误?");
		}
		model.addAttribute("list", agentMasters);
		return "tiles:agentVerify.verifyMobile";
	}
	
	/**
	 * 处理通过和违规手机号码验证
	 * @param request
	 * @param model
	 * @return
	 * @date 2012-1-5下午1:32:52
	 * @author dongyj
	 */
	@RequestMapping(value="/verifyMobile")
	public String verifyMobile(HttpServletRequest request, Model model) {
		SessionUser sessionUser = (SessionUser) request.getSession().getAttribute(SmcConst.SESSION_USER);
		String blockList = request.getParameter("blockList");
		String passList = request.getParameter("passList");
		try {
			agentVerifyService.verifyMobile(blockList,passList,sessionUser);
			model.addAttribute("info", "scs");
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return "json";
	}
	
	
	/**
	 * 审核历史记录
	 * @param request
	 * @param model
	 * @return
	 * @date 2012-1-12下午1:56:16
	 * @author dongyj
	 */
	@RequestMapping(value="/verifyMobileHistory")
	public String verifyMobileHistory(HttpServletRequest request, Model model) {
		String agentId = request.getParameter("agentId");
		String phone = request.getParameter("phone");
		HashMap<String, Object> params = new HashMap<String, Object>();
		StringBuffer conditions = new StringBuffer();
		List<PhoneVerifyLog> phoneVerifyLogs = new ArrayList<PhoneVerifyLog>();
		try {
			model.addAttribute("phone", phone);
			model.addAttribute("agentId", agentId);
			if (StringUtils.isNotBlank(agentId)) {
				String regex = "^\\d+$";
				if(!agentId.matches(regex)){
					throw new Exception("输入经纪人id错误.");
				}
				conditions.append(" AND agent_id = " + agentId);
			}
			if (StringUtils.isNotBlank(phone)) {
				conditions.append(" AND mobile = '" + phone + "'");
			}
			params.put("conditions", conditions);
			phoneVerifyLogs = agentVerifyService.getVerifyPhoneHistoryListByPage(params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("list", phoneVerifyLogs);
		return "tiles:agentVerify.verifyMobileHistoryList";
	}
	
	/**
	 * 将简化版的城市List放入application中.
	 * 取的方法:<select><option value="${entry.key }" selected="selected">${entry.value.code }${entry.value.name }</option></select>
	 * @param request
	 * @date 2012-1-4下午2:53:24
	 * @author dongyj
	 */
	private void putSimpleLocaleMapInApplication(HttpServletRequest request) {
		ServletContext application = request.getSession().getServletContext();
		Map<Integer, Locale> map;
		if (null == application.getAttribute("simpleLocaleMap")) {
			map = LocaleService.getLocalesPool();
			if (null != map) {
				for (Iterator<Entry<Integer, Locale>> iterator = map.entrySet().iterator(); iterator.hasNext();) {
					Map.Entry<Integer, Locale> entry = iterator.next();
					Locale locale = entry.getValue();
					if (locale.getParentId() != 0) {
						iterator.remove();
					}
				}
			}
			application.setAttribute("simpleLocaleMap", map);
		}
	}
	
	
	
}
