package com.nuoshi.console.web.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.nuoshi.console.common.Resources;
import com.nuoshi.console.common.util.SmcConst;
import com.nuoshi.console.domain.control.BlackList;
import com.nuoshi.console.domain.control.BlackListCondition;
import com.nuoshi.console.domain.control.SensitiveWord;
import com.nuoshi.console.domain.user.User;
import com.nuoshi.console.service.ControlService;
import com.nuoshi.console.web.session.SessionUser;

/**
 * @author wanghongmeng
 * 
 */
@Controller
@RequestMapping(value = "/control")
public class ControlController extends BaseController {
	@Autowired
	private ControlService controlService;
	

	/**
	 * @param model
	 * @return 黑名单列表页面
	 */
	@RequestMapping(value = "/blacklist")
	public String blackList(Model model) {
		List<BlackList> blackList = controlService.getAllBlackListByPage(null);
		model.addAttribute("blackList", blackList);
		return "tiles:control.blacklist";
	}

	/**
	 * @param session
	 * @param model
	 * @param blackList
	 *            要添加的黑名单对象
	 * @return 条件操作结果
	 */
	@RequestMapping(value = "/blacklist/add")
	public String addBlackList(HttpSession session, Model model,BlackList blackList) {
		SessionUser sessionUser  = (SessionUser) session.getAttribute(SmcConst.SESSION_USER);
		User user=sessionUser.getUser();
		boolean flog=true;
		if(user==null){
			model.addAttribute(SmcConst.MESSAGE, "请先登录！");
			flog=false;
			model.addAttribute(SmcConst.RESULT, flog);
			return "json";
		}
		String msg=controlService.addBlackUser(blackList,user);
		if(StringUtils.isBlank(msg)){
			flog=true;
		}else{
			flog=false;
		}
		model.addAttribute(SmcConst.RESULT, flog);
		model.addAttribute(SmcConst.MESSAGE, msg);
		return "json";
	}

	/**
	 * @param search
	 *            查询条件
	 * @param model
	 * @return 符合查询条件的黑名单列表页面
	 */
	@RequestMapping(value = "/blacklist/search")
	public String blackListSearch(Model model,@RequestParam(value="searchLoginName",required=false) String  searchLoginName
			,@RequestParam(value="searchId",required=false) String  searchId) {
		BlackListCondition search= new BlackListCondition();
		search.setBlackUserId(searchId);
		search.setLoginName(searchLoginName);
		List<BlackList> list = controlService.getAllBlackListByPage(search);
		model.addAttribute("blackList", list);
		model.addAttribute("searchLoginName", searchLoginName);
		model.addAttribute("searchId", searchId);
		return "tiles:control.blacklist";
	}

	/**
	 * @param model
	 * @param blackList
	 *            要更新的黑名单集合
	 * @return 更新操作结果
	 */
	@RequestMapping(value = "/blacklist/edit")
	public String updateBlackList(Model model, BlackList blackList,
			HttpSession session) {
		String msg;
		try {
			SessionUser sessionUser = (SessionUser) session
					.getAttribute("sessionUser");
			int userid = sessionUser.getUser().getId();
			blackList.setLastEntryId(userid);
			controlService.updateBlack(blackList);
			msg = Resources.getString("blacklist.edit.succ");
		} catch (Exception e) {
			msg = Resources.getString("blacklist.edit.fail");
			e.printStackTrace();
		}
		model.addAttribute(SmcConst.MESSAGE, msg);
		return "json";
	}
	/**
	 * @param model
	 * @param blackList
	 *            要更新的黑名单集合
	 * @return 更新操作结果
	 */
	@RequestMapping(value = "/blacklist/changeStatus")
	public String updateBlackListStatus(Model model, BlackList blackList,
			HttpSession session) {
		String msg="修改成功。";
		boolean result=true;
		if(blackList==null){
			result=false;
			msg="请先登录！";
			model.addAttribute(SmcConst.RESULT, result);
			model.addAttribute(SmcConst.MESSAGE, msg);
			return "json";
		}
		try {
			SessionUser sessionUser = (SessionUser) session.getAttribute("sessionUser");
			User user = sessionUser.getUser();
			if(user==null){
				result=false;
				msg="请先登录！";
			}else{
				blackList.setLastEntryId(user.getId());
				blackList.setLastEntryName(user.getChnName());
				blackList.setUpdateDate(new java.util.Date());
				controlService.updateBlack(blackList);
				result=true;
				msg = Resources.getString("blacklist.edit.succ");
			}
		} catch (Exception e) {
			result=false;
			msg = Resources.getString("blacklist.edit.fail");
			e.printStackTrace();
		}
		model.addAttribute(SmcConst.RESULT, result);
		model.addAttribute(SmcConst.MESSAGE, msg);
		return "json";
	}

	/**
	 * @param model
	 * @param id
	 *            要删除的黑名单id
	 * @return 删除操作结果
	 */
	@RequestMapping(value = "/blacklist/delete/{id}")
	public String deleteBlackList(Model model, @PathVariable("id") String id) {
		String msg;
		try {
			String[] idArr = id.split(",");
			for (String low : idArr) {
				int delId = Integer.parseInt(low);
				controlService.deleteBlack(delId);
			}
			msg = Resources.getString("blacklist.delete.succ");
		} catch (Exception e) {
			msg = Resources.getString("blacklist.delete.fail");
			e.printStackTrace();
		}
		model.addAttribute(SmcConst.MESSAGE, msg);
		return "json";
	}

	/**
	 * @param model
	 * @return 关键词列表页面
	 */
	@RequestMapping(value = "/sensitiveWord")
	public String sensitiveWordList(Model model) {
		List<SensitiveWord> sensitiveWordList = controlService
				.getAllSensitiveWordByPage(null);
		model.addAttribute("sensitiveWordList", sensitiveWordList);
		return "tiles:control.sensitiveWord";
	}

	/**
	 * @param session
	 * @param model
	 * @param sensitiveWord
	 *            要添加的关键词对象
	 * @return 添加操作结果
	 */
	@RequestMapping(value = "/sensitiveWord/add")
	public String addSensitiveWord(HttpSession session, Model model,
			SensitiveWord sensitiveWord) {
		String msg;
		try {
			SessionUser sessionUser = (SessionUser) session
					.getAttribute("sessionUser");
			int userid = sessionUser.getUser().getId();
			sensitiveWord.setEntryId(userid);
			controlService.addSensitiveWord(sensitiveWord);
			msg = Resources.getString("sensitiveWord.add.succ");
		} catch (Exception e) {
			msg = Resources.getString("sensitiveWord.add.fail");
			e.printStackTrace();
		}
		model.addAttribute(SmcConst.MESSAGE, msg);
		return "json";
	}

	/**
	 * @param search
	 *            查询条件
	 * @param model
	 * @return 符合查询条件的关键词列表页面
	 */
	@RequestMapping(value = "/sensitiveWord/search")
	public String sensitiveWordSearch(SensitiveWord search, Model model) {
		List<SensitiveWord> list = controlService
				.getAllSensitiveWordByPage(search);
		model.addAttribute("sensitiveWordList", list);
		model.addAttribute("search", search);
		return "tiles:control.sensitiveWord";
	}

	/**
	 * @param model
	 * @param sensitiveWord
	 *            要更新的关键词对象
	 * @return 更新操作结果
	 */
	@RequestMapping(value = "/sensitiveWord/edit")
	public String updateSensitiveWord(Model model, SensitiveWord sensitiveWord,
			HttpSession session) {
		String msg;
		try {
			SessionUser sessionUser = (SessionUser) session
					.getAttribute("sessionUser");
			int userid = sessionUser.getUser().getId();
			sensitiveWord.setEntryId(userid);
			controlService.updateSensitiveWord(sensitiveWord);
			msg = Resources.getString("sensitiveWord.edit.succ");
		} catch (Exception e) {
			msg = Resources.getString("sensitiveWord.edit.fail");
			e.printStackTrace();
		}
		model.addAttribute(SmcConst.MESSAGE, msg);
		return "json";
	}

	/**
	 * @param model
	 * @param id
	 *            要删除的关键词id
	 * @return 删除操作结果
	 */
	@RequestMapping(value = "/sensitiveWord/delete/{id}")
	public String deleteSensitiveWord(Model model, @PathVariable("id") String id) {
		String msg;
		try {
			String[] idArr = id.split(",");
			for (String low : idArr) {
				int delId = Integer.parseInt(low);
				controlService.deleteSensitiveWord(delId);
			}
			msg = Resources.getString("sensitiveWord.delete.succ");
		} catch (Exception e) {
			msg = Resources.getString("sensitiveWord.delete.fail");
			e.printStackTrace();
		}
		model.addAttribute(SmcConst.MESSAGE, msg);
		return "json";
	}

	/**
	 * @param session
	 * @param model
	 * @param blackList
	 *            要添加的黑名单对象
	 * @return 条件操作结果
	 */
	@RequestMapping(value = "/blacklist/exist/{account}")
	public String isExist(Model model, @PathVariable("account") String account ,@RequestParam(value="listType",required=false)Integer listType) {
		String result;
		if(listType==null){
			listType=SmcConst.USER_BLACKLIST_LIST_TYPE_AGENT;
		}
		if (!controlService.isUserExistByLoginName(account ,listType)) {
			result = "0";
		} else {
			result = "1";
		}
		model.addAttribute("result", result);
		return "json";
	}
	/**
	 * @param session
	 * @param model
	 * @param blackList
	 *            要添加的黑名单对象
	 * @return 条件操作结果
	 */
	@RequestMapping(value = "/blacklist/existById/{userId}")
	public String isExistByUserId(Model model, @PathVariable("userId") Integer userId ,@RequestParam(value="listType",required=false)Integer listType) {
		String result;
		if(listType==null||listType.intValue()<0){
			listType=SmcConst.USER_BLACKLIST_LIST_TYPE_AGENT;
		}
		if (!controlService.isUserExistByUserId(userId ,listType)) {
			result = "0";
		} else {
			result = "1";
		}
		model.addAttribute("result", result);
		return "json";
	}
	
}