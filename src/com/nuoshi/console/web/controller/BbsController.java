package com.nuoshi.console.web.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.gson.Gson;
import com.nuoshi.console.common.BbsGlobals;
import com.nuoshi.console.common.page.impl.Pagination;
import com.nuoshi.console.domain.bbs.TfBbsForums;
import com.nuoshi.console.domain.topic.Locale;
import com.nuoshi.console.service.ForumService;
import com.nuoshi.console.service.LocaleService;

/**
 * 论坛的控制器（论坛，专题，帖子管理）
 * 
 * @author tangwei
 * 
 */
@Controller
@RequestMapping(value = "/bbs/manager")
public class BbsController extends BaseController {

	@Resource
	private ForumService forumService;

	Log log = LogFactory.getLog(BbsController.class);

	/**
	 * 添加板块
	 * 
	 * @param tfBbsForums
	 * @param model
	 * @return
	 */
	@RequestMapping("/saveforum")
	public String addforum(@ModelAttribute("TfBbsForums") TfBbsForums tfBbsForums, Model model,
			HttpServletRequest request) {

		List<TfBbsForums> newsList = null;
		if (tfBbsForums != null && tfBbsForums.getName() != null && tfBbsForums.getName().length() > 0) {
			if (tfBbsForums.getCityId().intValue() == BbsGlobals.OTHER_CITY_ID) {
				Map<String, TfBbsForums> mapname = new HashMap<String, TfBbsForums>();
				List<TfBbsForums> list = ForumService.list(BbsGlobals.OTHER_CITY_ID, -1);
				if (list != null && list.size() > 0) {
					for (TfBbsForums tmp : list) {
						mapname.put(tmp.getCityId() + "=" + tmp.getVisibleRole() + tmp.getName(), tmp);
					}
				}

				Map<Integer, Locale> map = LocaleService.getCityLocalesPool();
				int citynum = 0;
				if (map != null) {
					Locale locale = null;
					for (Map.Entry<Integer, Locale> entry : map.entrySet()) {
						if (ForumService.getForumCitysMap().containsKey(entry.getKey().toString())) {
							continue;
						}
						locale = entry.getValue();
						citynum++;
						int cityId = locale.getId();

						if (mapname.containsKey(cityId + "=" + tfBbsForums.getVisibleRole() + tfBbsForums.getName())) {
							continue;
						}
						tfBbsForums.setCityId(cityId);
						String forumname = request.getParameter("forumname");
						if (forumname != null && forumname.trim().length() > 0) {

							if (mapname.containsKey(cityId + "=" + tfBbsForums.getVisibleRole() + forumname)) {
								TfBbsForums forum = mapname
										.get(cityId + "=" + tfBbsForums.getVisibleRole() + forumname);
								tfBbsForums.setParentId(forum.getId());
								tfBbsForums.setForumType((byte) (forum.getForumType() + 1));
							} else {
								continue;
							}
						} else {
							tfBbsForums.setParentId(0);
							tfBbsForums.setForumType((byte) 0);
						}
						if(mapname.containsKey(cityId + "=" + tfBbsForums.getVisibleRole() + forumname)){
							forumService.add(tfBbsForums);
						}
						
					}
				}
			} else {
				getForum(tfBbsForums);
				forumService.add(tfBbsForums);
			}

			int i = 1;
			if (i > 0) {
				model.addAttribute("result", "保存成功");
			} else {
				model.addAttribute("result", "保存失败");
			}
		}
		Pagination pagination = Pagination.threadLocal.get();
		newsList = forumService.list(pagination);
		model.addAttribute("newList", newsList);

		model.addAttribute("forum", 1);

		return "tiles:bbs.manageforum";
	}

	public void getForum(TfBbsForums tfBbsForums) {
		String idAndForumType = tfBbsForums.getIdAndForumType();
		if (idAndForumType != null && idAndForumType.length() > 0) {
			String[] tmp = idAndForumType.split("-");
			if (tmp != null && tmp.length == 2) {

				tfBbsForums.setParentId(Integer.parseInt(tmp[0]));
				tfBbsForums.setForumType((byte) (Byte.parseByte(tmp[1]) + 1));
			}
		} else {
			tfBbsForums.setForumType((byte) BbsGlobals.FORUM_1);
		}
	}

	/**
	 * 刚开始的论坛管理的跳转
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/forum")
	public String forum(Model model) {
		model.addAttribute("forum", 0);
		return "redirect:/bbs/manager/manageforum";
	}

	@RequestMapping("/addforum")
	public String forumAdd(Model model) {
		model.addAttribute("citys", forumService.getForumCitysMap());
		return "tiles:bbs.addforum";

	}

	@RequestMapping("/addforumlist")
	public void addForumList(@RequestParam(value = "cityId") Integer cityId, HttpServletRequest request,
			HttpServletResponse response) {
		int role = convertStringToInt(request.getParameter("visibleRole"), 0);
		int forumType = convertStringToInt(request.getParameter("forumType"), 0);
		List<TfBbsForums> list = forumService.list(cityId, role);
		Map<Integer, TfBbsForums> map = new HashMap<Integer, TfBbsForums>();
		List<TfBbsForums> forumList = new ArrayList<TfBbsForums>();
		if (list != null && list.size() > 0) {
			Map<String, String> nameMap = new HashMap<String, String>();
			for (TfBbsForums tmp : list) {
				if (forumType != tmp.getForumType()) {
					continue;
				}
				
				if (cityId != null && cityId.intValue() != -1) {
					//下拉框为北京,深圳时.
						if (!nameMap.containsKey(tmp.getName())) {
							map.put(tmp.getId(), tmp);
							nameMap.put(tmp.getName(), tmp.getName());
						}
				} else {
					//下拉框为其他,或除北京,深圳以外的其他城市时.
					//这里是!
					if (!ForumService.getForumCitysMap().containsKey(tmp.getCityId().toString())) {
						if (!nameMap.containsKey(tmp.getName())) {
							map.put(tmp.getId(), tmp);
							nameMap.put(tmp.getName(), tmp.getName());
						}
					}
				}

			}
			for (Map.Entry<Integer, TfBbsForums> entry : map.entrySet()) {
				TfBbsForums forum = entry.getValue();
				String name = "";
				if (forum.getParentId() > 0) {
					TfBbsForums parent = map.get(forum.getParentId());
					if (parent != null) {
						name = parent.getName() + "-" + forum.getName();
					}
				} else {
					name = forum.getName();
				}
				TfBbsForums newforum = new TfBbsForums();
				newforum.setId(entry.getKey());
				newforum.setName(name);
				newforum.setForumType(forum.getForumType());
				newforum.setVisibleRole(forum.getVisibleRole());
				newforum.setVisibleRoleName(BbsGlobals.getVisibleRoleName(forum.getVisibleRole()));
				forumList.add(newforum);
			}
		}
		Gson gson = new Gson();
		sentResponseInfo(response, gson.toJson(forumList).toString());

	}

	public int convertStringToInt(String str, int defaultValue) {
		if (str != null && str.length() > 0) {
			try {
				return Integer.parseInt(str);
			} catch (Exception e) {
				return defaultValue;
			}
		}
		return defaultValue;
	}

	/**
	 * 论坛管理的列表
	 * 
	 * @param forum
	 * @param model
	 * @return
	 */
	@RequestMapping("/manageforum")
	public String manageforum(Model model) {
		List<TfBbsForums> newList = null;
		Pagination pagination = Pagination.threadLocal.get();
		newList = forumService.list(pagination);
		model.addAttribute("newList", newList);
		return "tiles:bbs.manageforum";

	}

	@RequestMapping("/searchforum")
	public String searchforum(Model model, HttpServletRequest request, HttpServletResponse response) {
		List<TfBbsForums> newList = null;
		String search = request.getParameter("search");
		Map<String, String> map = new HashMap<String, String>();
		if (search != null && search.length() > 0) {
			map.put("name", this.getEncoder(search));
			map.put("cityId", request.getParameter("cityId"));
		} else {
			map.put("name", this.getEncoder(request.getParameter("name")));
			map.put("cityId", request.getParameter("cityId"));
			map.put("visibleRole", request.getParameter("visibleRole"));
			String pid = request.getParameter("parentId");
			if (pid != null && pid.length() > 0) {
				String[] tmp = pid.split("-");
				if (tmp != null && tmp.length == 2) {
					pid = tmp[0];
				}
			}
			map.put("parentId", pid);
			map.put("excludeId", request.getParameter("excludeId"));
		}

		if ("json".equals(request.getParameter("callback"))) {
			newList = forumService.list(map, null);

			model.addAttribute("newList", newList);
			if (newList != null && newList.size() > 0) {
				model.addAttribute("totalNum", newList.size());
			} else {
				model.addAttribute("totalNum", 0);
			}

			return "json";
		}
		Pagination pagination = Pagination.threadLocal.get();
		newList = forumService.list(map, pagination);
		model.addAttribute("newList", newList);
		model.addAttribute("search", search);
		model.addAttribute("cityId", request.getParameter("cityId"));
		model.addAttribute("ch", request.getParameter("ch"));
		return "tiles:bbs.manageforum";

	}

	/**
	 * 删除论坛
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/deleteforum")
	public String deleteforum(@RequestParam(value = "id") int id, Model model) {

		forumService.delete(id);
		model.addAttribute("info", "success");
		return "json";

	}

	/**
	 * 查找论坛跳转编辑页面
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/selectByforumId")
	public String selectByforumId(@RequestParam(value = "id") int id, Model model) {

		TfBbsForums forum = null;
		forum = forumService.selectByforumId(id);
		model.addAttribute("newforum", forum);
		String ch = "";
		if (forum != null) {
			ch = LocaleService.getFirstName(forum.getCityId());
		}
		model.addAttribute("ch", ch);
		return "tiles:bbs.editforum";
	}

	/**
	 * 更新论坛的修改
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/editforum")
	public String editforum(@ModelAttribute("TfBbsForums") TfBbsForums tfBbsForums, Model model) {

		if (tfBbsForums != null) {
			/*
			 * TfBbsForums
			 * fourm=forumService.selectByforumName(tfBbsForums.getCityId
			 * ()+"",tfBbsForums.getName().trim()); if(fourm!=null){
			 * model.addAttribute("result","论坛名字重复不能保存"); }else{
			 */
			getForum(tfBbsForums);
			int i = forumService.update(tfBbsForums);

			if (i > 0) {
				model.addAttribute("result", "保存成功");
			} else {
				model.addAttribute("result", "保存失败");
			}
			// }
		}

		List<TfBbsForums> newList = null;
		Pagination pagination = Pagination.threadLocal.get();
		newList = forumService.list(pagination);
		model.addAttribute("newList", newList);
		model.addAttribute("forum", 1);
		return "tiles:bbs.manageforum";
	}

	@RequestMapping("/updateoff")
	public String updateoff(@RequestParam(value = "id") int id, @RequestParam(value = "turnoff") byte turnoff,
			Model model) {

		TfBbsForums tfBbsForums = new TfBbsForums();
		tfBbsForums.setId(id);
		if (turnoff == BbsGlobals.TURNOFF_STATUS) {
			Date now = new Date();
			tfBbsForums.setTurnofftime(now);
		}
		tfBbsForums.setStatus(turnoff);
		int i = forumService.updateoff(tfBbsForums);
		if (i > 0) {
			model.addAttribute("result", "success");
		} else {
			model.addAttribute("result", "faile");
		}

		return "json";
	}

	/**
	 * 下拉框的选择
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/chooseforum")
	public void chooseforum(@RequestParam(value = "parentId") int parentId, Model model, HttpServletResponse response) {
		List<TfBbsForums> list = forumService.chooseForum(parentId);
		Gson gson = new Gson();
		if (list != null && list.size() > 0) {
			sentResponseInfo(response, gson.toJson(list).toString());
		}

	}

}
