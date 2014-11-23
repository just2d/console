package com.nuoshi.console.web.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.gson.Gson;
import com.nuoshi.console.common.ChangePINYIN;
import com.nuoshi.console.common.Helper;
import com.nuoshi.console.common.Resources;
import com.nuoshi.console.common.page.impl.Pagination;
import com.nuoshi.console.common.util.DateUtil;
import com.nuoshi.console.common.util.SmcConst;
import com.nuoshi.console.domain.estate.EstateChangeMessage;
import com.nuoshi.console.domain.estate.EstateData;
import com.nuoshi.console.domain.estate.EstateInfo;
import com.nuoshi.console.domain.estate.EstateMapper;
import com.nuoshi.console.domain.estate.EstateSearch;
import com.nuoshi.console.domain.estate.EstateSearchParam;
import com.nuoshi.console.domain.estate.LogCondition;
import com.nuoshi.console.domain.estate.StatsEstate;
import com.nuoshi.console.domain.estate.UnionLog;
import com.nuoshi.console.domain.topic.Estate;
import com.nuoshi.console.domain.topic.Locale;
import com.nuoshi.console.jms.EstateJms;
import com.nuoshi.console.service.EstateService;
import com.nuoshi.console.service.LocaleService;
import com.nuoshi.console.service.RentService;
import com.nuoshi.console.service.ResaleService;
import com.nuoshi.console.service.StatsEstateService;
import com.nuoshi.console.service.UnionLogService;
import com.nuoshi.console.view.EstateListView;
import com.nuoshi.console.view.EstatePhoto;
import com.nuoshi.console.view.EstatePhotoCondition;
import com.nuoshi.console.web.session.SessionUser;

@Controller
@RequestMapping("/estate")
@SuppressWarnings("unchecked")
public class EstateController extends BaseController {

	@Resource
	private EstateService estateService;

	@Resource
	private ResaleService resaleService;
	@Resource
	private RentService rentService;

	@Resource
	private StatsEstateService statEstateService;
	@Resource
	private UnionLogService unionLogService;

	Log log = LogFactory.getLog(EstateController.class);

	@Resource
	LocaleService localeService;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Integer.class, new CustomNumberEditor(Integer.class, true));
	}

	/**
	 * 小区列表
	 * 
	 * @return
	 */
	@RequestMapping("/list")
	public String list(@RequestParam(value = "cityId", required = false) Integer cityId,
			@RequestParam(value = "distId", required = false) Integer distId,
			@RequestParam(value = "blockId", required = false) Integer blockId,
			@RequestParam(value = "delStatus", required = false) String delStatus,
			@RequestParam(value = "authStatus", required = false) String authStatus,
			@RequestParam(value = "estateName", required = false) String estateName,
			@RequestParam(value = "isClicked", required = false, defaultValue = "1") String isClicked,
			@RequestParam(value = "estateId", required = false) Integer estateId,
			@RequestParam(value = "ch", required = false) String ch, Model model, HttpServletRequest request) {
		EstateListView condition = new EstateListView();
		condition = setCityInfoFromSession(request, cityId, distId, blockId, condition);
		// 现在默认选中北京.根据运营人员实际需要而定
		setDefaultCity(condition, cityId, model);
		String cityName = getNameById(condition.getCityId());
		String distName = getNameById(distId);
		if (distName != null) {
			model.addAttribute("blockList", localeService.getDistLocalByCityId(distId));
		}
		condition.setDelStatus(delStatus);
		condition.setIsClicked(isClicked);//
		condition.setAuthStatus(authStatus);
		condition.setEstateName(estateName);
		// 用于回显表单项数据
		model.addAttribute("ch", ch);
		model.addAttribute("cityName", cityName);
		List<EstateData> result = null;
		Pagination pagination = Pagination.threadLocal.get();
		try {
			// 通过id搜索..
			if (estateId != null) {
				EstateData estateData = estateService.getEstateDataByIdAndAuthStatus(estateId,authStatus);
				setEstateData(estateData);
				if (estateData != null) {
					result = new ArrayList<EstateData>();
					result.add(estateData);
					pagination.setTotalCount(result.size());
				}
			} else {
				// 没通过id搜索
				setPageNo(pagination, request);
				condition.setLimit(pagination.getPageSize());
				condition.setStart((pagination.getPageNo() - 1) * pagination.getPageSize());

				result = estateService.getEstateByCondition(condition);
				if (result != null && result.size() > 0) {
					for (EstateData estateData : result) {
						setEstateData(estateData);
					}
					pagination.setTotalCount(estateService.countByCondition(condition));
				}

				// 在session中存储这些信息以在detail.jsp中使用,当点击'删除','通过审核'时跳转到访问detail.jsp页前的列表页页面.
				request.getSession().setAttribute("pageNo", pagination.getPageNo());
				request.getSession().setAttribute("pageSize", pagination.getPageSize());
				request.getSession().setAttribute("cityId", cityId);
				request.getSession().setAttribute("distId", distId);
				request.getSession().setAttribute("blockId", blockId);
			}
			model.addAttribute("authStatus", authStatus);
			model.addAttribute("estateList", result);
			// 回显数据
			model.addAttribute("estateId", estateId);
			model.addAttribute("condition", condition);
		} catch (Exception e) {
			log.error(e);
		}
		if ("0".equals(condition.getAuthStatus())) {
			// 待审核小区列表
			return "tiles:estate.list";
		} else if ("2".equals(condition.getAuthStatus())) {
			// 未通过审核小区列表
			return "tiles:estate.list_unpast";
		} else if ("1".equals(condition.getAuthStatus())) {
			// 已审核小区列表
			return "tiles:estate.list_past";
		} else if ("3".equals(condition.getAuthStatus())) {
			// 已删除小区列表
			return "tiles:estate.list_del";
		} else {
			return null;
		}

	}

	/**
	 * 刪除小区
	 */
	@RequestMapping("/deleteAction")
	public void delete(@RequestParam(value = "estateId", required = false) String ids, Model model,
			HttpServletResponse response) {
		if (!"".equals(ids) && ids.contains(",") && ids != null) {
			String[] id = ids.split(",");
			for (String estateId : id) {
				estateService.deleteByEstateId(Integer.parseInt(estateId));
			}
		} else {
			estateService.deleteByEstateId(Integer.parseInt(ids));
		}

		String respStr = "{success:true}";
		sentResponseInfo(response, new Gson().toJson(respStr).toString());
	}

	/**
	 * 审核小区
	 */
	@RequestMapping("/authAction")
	public void auth(@RequestParam(value = "authStatus", required = false) String authStatus,
			@RequestParam(value = "origStatus", required = false) String origStatus,
			@RequestParam(value = "estateId", required = false) String estateId, HttpServletResponse response) {
		// 如果执行逻辑删除
		if (!"".equals(estateId) && estateId.contains(",") && estateId != null) {
			String[] ids = estateId.split(",");
			// 执行逻辑删除操作时origStatus值不为空
			for (String id : ids) {
				Estate estate = estateService.getEstateInfoById(Integer.parseInt(id));
				estateService.updateAuthWStatus(authStatus, Integer.parseInt(id), origStatus, estate.getCreateUserId());
			}
		} else {
			Estate estate = estateService.getEstateInfoById(Integer.parseInt(estateId));
			estateService.updateAuthWStatus(authStatus, Integer.parseInt(estateId), origStatus,
					estate.getCreateUserId());
		}
		String respStr = "{success:true}";
		sentResponseInfo(response, new Gson().toJson(respStr).toString());
	}

	/**
	 * 恢复删除了的数据.
	 * 
	 * @param estateId
	 * @param origStatus
	 */
	@RequestMapping("/recover")
	public void reslove(@RequestParam(value = "idStatus", required = false) String idStatus,
			HttpServletResponse response) {
		if (!"".equals(idStatus) && idStatus.contains(",") && idStatus != null) {
			String[] idStatuss = idStatus.split(",");
			// 执行逻辑删除操作时origStatus值不为空
			for (String idSts : idStatuss) {
				recoverAuthStatus(idStatus);
			}
		} else {
			recoverAuthStatus(idStatus);
		}
		String respStr = "{success:true}";
		sentResponseInfo(response, new Gson().toJson(respStr).toString());
	}

	private void recoverAuthStatus(String idStatus) {
		if (StringUtils.isNotBlank(idStatus) && idStatus.contains("-")) {
			String idStss[] = idStatus.split("-");
			if(idStss.length == 2){
				estateService.updateAuthStatus(idStss[1], Integer.parseInt(idStss[0]), null);
			}else{
				estateService.updateAuthStatus("0", Integer.parseInt(idStss[0]), null);
			}
			
		}
	}

	/**
	 * 编辑或查看小区
	 */
	@RequestMapping("/form")
	public String add(@RequestParam(value = "id", required = false) String id,
			@RequestParam(value = "action", required = false) String action,
			@RequestParam(value = "authStatus", required = false) String authStatus,
			@RequestParam(value = "flag", required = false) String flag, Model model) {
		// 编辑小区
		EstateInfo estateInfo = new EstateInfo();
		model.addAttribute("authStatus", authStatus);
		if (StringUtils.isNotBlank(id)) {
			EstateData estateData = estateService.getEstateDataById(Integer.valueOf(id) != null ? Integer.valueOf(id)
					: 0);
			// 获得当前月的出租房源价格,二手房源价格
			StatsEstate statsEstate = statEstateService.getPriceByEstateId(Integer.valueOf(id),
					DateUtil.getLastMonthDateStr(new Date()));
			// 获得小区均价
			if (estateData != null) {
				BeanUtils.copyProperties(estateData, estateInfo);
				estateInfo.setCityName(getNameById(estateInfo.getCityId()));
				estateInfo.setDistName(getNameById(estateInfo.getDistId()));
				estateInfo.setBlockName(getNameById(estateInfo.getBlockId()));
				estateInfo.setResaleAvgPrice(statsEstate != null ? statsEstate.getResaleAvgPrice() : 0);
				estateInfo.setRentAvgPrice(statsEstate != null ? statsEstate.getRentAvgPrice() : 0);
				estateInfo.setExtInfo(estateInfo);
				// 对物业类型进
				model.addAttribute("estateInfo", estateInfo);
			}

		}
		if ("edit".equals(action)) {
			// 获得当前城市下的所有区域
			if (estateInfo.getCityId() != null) {
				List<Locale> distList = localeService.getDistLocalByCityId(estateInfo.getCityId());
				model.addAttribute("distList", distList);
			}
			if (estateInfo.getDistId() != null) {
				List<Locale> distList = localeService.getDistLocalByCityId(estateInfo.getDistId());
				model.addAttribute("blockList", distList);
			}
			return "tiles:estate.form";
		} else {
			model.addAttribute("delFlag", flag);
			return "tiles:estate.detail";
		}

	}

	/**
	 * 跳转到添加页
	 * 
	 * @return
	 */
	@RequestMapping("/add")
	public String add() {
		return "tiles:estate.add";
	}

	/**
	 * 保存编辑后的小区信息
	 * 
	 * @param estateInfo
	 * @param response
	 */
	@RequestMapping("/updateAction")
	public String updateAction(EstateInfo estateInfo, HttpServletResponse response, HttpServletRequest request) {
		// estateInfo = this.setBasicInfo(estateInfo);
		String pinYin = ChangePINYIN.getPingYin(ChangePINYIN.getEstateName(estateInfo.getEstateName()));// heshengqilin
		//只改namepy,pinyin 在新建时创建后不再修改.
		if (StringUtils.isNotBlank(pinYin) && !pinYin.equals(estateInfo.getNamepy())) {
			estateInfo.setNamepy(estateService.getPinYin(pinYin));
		}
		estateService.update(estateInfo);
		// 更新小区均价.
//		if (estateInfo.getRentAvgPrice() != 0 || estateInfo.getResaleAvgPrice() != 0) {
//			statEstateService.updateEstatePrice(estateInfo.getRentAvgPrice(), estateInfo.getResaleAvgPrice(),estateInfo.getEstateId(), DateUtil.formatDate(new Date()));
//		}
		if (estateInfo.getResaleAvgPrice() != 0) {
			statEstateService.updateEstateResaleAvgPrice(estateInfo.getResaleAvgPrice(),estateInfo.getEstateId(), DateUtil.getLastMonthDateStr(new Date()));
		}
		String returnUrl = getReturnUrl(estateInfo.getAuthStatus(), request);
		return "tiles:redirect:list?" + returnUrl;
	}

	/**
	 * 添加方法
	 * 
	 * @return
	 */
	@RequestMapping("/addAction")
	public String addAction(EstateInfo estateInfo, HttpServletResponse response, HttpServletRequest request, Model model) {
		// 管理员添加的房源默认设置为通过审核状态
		// this.setBasicInfo(estateInfo);
		String pinYin = ChangePINYIN.getPingYin(ChangePINYIN.getEstateName(estateInfo.getEstateName()));
		pinYin = estateService.getPinYin(pinYin);
		estateInfo.setPinYin(pinYin);
		estateInfo.setNamepy(pinYin);
		estateService.addEstate(estateInfo);
		// 统计照片数量并插入数据库.
		// 更新小区均价.(认为小区均价在stats_estate表中已有记录)
		String returnUrl = getReturnUrl(estateInfo.getAuthStatus(), request);
		return "tiles:redirect:list?" + returnUrl;
	}

	/**
	 * 显示户型图列表.
	 * 
	 * @param condition
	 * @param model
	 * @return
	 */
	@RequestMapping("/photoList")
	public String photoList(EstatePhotoCondition condition, Model model, HttpServletRequest request) {
		Pagination pagination = Pagination.threadLocal.get();
		setPageNo(pagination, request);
		saveDist(condition, model);
		String cityName = null;
		String distName = null;
		List<EstatePhoto> layoutList = new ArrayList<EstatePhoto>();
		if (condition != null) {
			cityName = getNameById(condition.getCityId());
			distName = getNameById(condition.getDistId());
			if (distName != null) {
				model.addAttribute("blockList", localeService.getDistLocalByCityId(condition.getDistId()));
			}
			if (condition.getEstateId() != null) {
				EstateData estateData = estateService.getEstateDataById(condition.getEstateId());
				setEstateData(estateData);
				EstatePhoto estatePhoto = new EstatePhoto();
				if (estateData != null) {
					BeanUtils.copyProperties(estateData, estatePhoto);
					layoutList.add(estatePhoto);
				}
			} else {
				condition.setEstateName(StringUtils.trim(condition.getEstateName()));
				condition.setStart((pagination.getPageNo() - 1) * pagination.getPageSize());
				condition.setLimit(pagination.getPageSize() + 2);// 每页显示21条记录,同时将第21,22条记录隐藏,从而可以从第20条记录那里获得它的下一个小区id
				layoutList = estateService.getPhotoListByPage(condition);
				int total = 0;
				if (layoutList != null && layoutList.size() > 0) {
					for (EstatePhoto photo : layoutList) {
						photo.setBlockName(getNameById(photo.getBlockId()));
						photo.setCityName(getNameById(photo.getCityId()));
						photo.setDistName(getNameById(photo.getDistId()));
					}
					total = estateService.countPhotoList(condition);
				}
				pagination.setTotalCount(total);
			}

		}

		model.addAttribute("condition", condition);
		model.addAttribute("isEmpty", condition.getEmpty());
		model.addAttribute("cityName", cityName);
		model.addAttribute("layoutList", layoutList);
		return "tiles:layout.list";
	}

	/**
	 * 验证小区名是否重复
	 * 
	 * @param condition
	 * @param response
	 */
	@RequestMapping("/validateName")
	public void validateName(EstateListView condition, HttpServletResponse response) {
		condition.setIsClicked("1");
		int count = estateService.validateByCondition(condition);
		Integer estateId = condition.getEstateId();
		String falseJson = "{\"msg\":false}";// 没重名
		String succJson = "{\"msg\":true}";// 重名了
		if (count == 0) {
			// 新增or 修改
			sentResponseData(response, new Gson().toJson(falseJson).toString());
		} else {
			// 修改
			if (estateId != null) {
				Estate estate = estateService.getEstateInfoById(estateId);
				if (estate != null && estate.getEstateName().equals(condition.getEstateName())) {
					sentResponseData(response, new Gson().toJson(falseJson).toString());
				} else {
					sentResponseData(response, new Gson().toJson(succJson).toString());
				}
			} else {
				// 新增
				sentResponseData(response, new Gson().toJson(succJson).toString());
			}
		}
	}

	/**
	 * 合并小区
	 * 
	 * @param sourceId
	 *            要合并的小区(一个一个小区进行合并,并记录失败记录)
	 * @param targetId
	 *            合并到的小区
	 * @return
	 */
	@RequestMapping("/union")
	public String union(@RequestParam(value = "targetId", required = false) Integer targetId,
			@RequestParam(value = "sourceId", required = false) String sourceId,
			@RequestParam(value = "targetPinYin", required = false) String targetPY, Model model,
			HttpServletRequest request) {
		// 统计现有小区二手房,租房数量
		int resaleCount_Added = 0;
		int rentCount_Added = 0;
		EstateData t_estate = estateService.getEstateDataById(targetId);
		if(null==t_estate||(null!=t_estate.getRtUrl()&&t_estate.getRtUrl().length()>0)){
			model.addAttribute("fail", "该小区不存在或已合并到新小区！");
			return  "json";
		}
		
		t_estate.setCityCode(getCodeById(t_estate.getCityId()));
		// 更新成功后统计的小区二手房,租房总数.
		String[] idStatus = sourceId.split(",");
		if (idStatus != null && idStatus.length > 0) {

			for (String tmp : idStatus) {
				try {
					String[] id_Status = tmp.split("_");
					Integer id = Integer.parseInt(id_Status != null && id_Status.length > 0 ? id_Status[0] : "0");	
					  /*
					    String new_TargetPinYin = estateService.getEstateDataById(targetId).getPinYin();
					    String new_url = "http://www.taofang.com/xq_" + new_TargetPinYin + "/";
						List<UnionLog> unionLogList = unionLogService.getUnionLogBySourceId(id);
						if(unionLogList != null && unionLogList.size() >0 ){
							updateAllChildRturl(unionLogList, new_url);
						}*/
					String s_status = idStatus != null && id_Status.length > 1 ? id_Status[1] : "0";
					// 更新每个小区的二手房,租房
					// 获得该小区的source(来自58还是淘房)
					// 统计这个小区二手房,租房数量(不要用页面上的租房,二手房数量作为参数传进来,那里的值 不准确)
					int resaleCount = resaleService.countResaleByEstate(id);
					int resale58Count = resaleService.count58ResaleByEstate(id);
					int rentCount = rentService.countRentByEstate(id);
					int rent58Count = rentService.count58RentByEstate(id);
					Map paramMap = new HashMap();
					paramMap.put("s_authStatus", s_status);
					paramMap.put("t_estate_id", targetId);
					paramMap.put("s_estate_id", id);
					paramMap.put("estateName", t_estate.getEstateName());
					paramMap.put("cityId", t_estate.getCityId());
					paramMap.put("cityCode", t_estate.getCityCode());
					paramMap.put("distId", t_estate.getDistId());
					paramMap.put("blockId", t_estate.getBlockId());
					HttpSession session = request.getSession();
					SessionUser sessionUser = (SessionUser) session.getAttribute(SmcConst.SESSION_USER);
					paramMap.put("operator", sessionUser.getUser().getChnName());

					if (resaleCount >= 0) {
						// 获得所有的二手房id,名称,所在城市,地区和板块.
						paramMap.put("fromTable", Helper.getConfig("from_table_resale"));
						saveResaleUnionLog(paramMap);
						resaleService.unionResaleEstate(paramMap);
						resaleCount_Added += resaleCount;
					}
					if (resale58Count >= 0) {
						paramMap.put("fromTable", Helper.getConfig("from_table_resale_58"));
						saveResaleUnionLog(paramMap);
						resaleService.union58ResaleEstate(paramMap);
						resaleCount_Added += resale58Count;
					}
					if (rentCount >= 0) {
						paramMap.put("fromTable", Helper.getConfig("from_table_rent"));
						// 合并所有状态的二手房,租房房源
						saveRentUnionLog(paramMap);
						rentService.unionRentEstate(paramMap);
						rentCount_Added += rentCount;
					}
					if (rent58Count >= 0) {
						paramMap.put("fromTable", Helper.getConfig("from_table_rent_58"));
						saveRentUnionLog(paramMap);
						rentService.union58RentEstate(paramMap);
						rentCount_Added += rent58Count;
					}
					// 现在小区表问题很多,不知从哪传上来的,合并的时候同时更新58和淘房的二手房和租房吧.
					// 更新这个小区的estateid,source
					// 将小区置为已删除状态并将二手房,出租房源数量置为0.
					Map delEstateMap = new HashMap();
					delEstateMap.put("rentNum", 0);
					delEstateMap.put("resaleNum", 0);
					delEstateMap.put("authStatus", Helper.getConfig("estate.authStatus.del"));
					delEstateMap.put("origStatus", s_status);
					delEstateMap.put("estateId", id);
					EstateJms.send(id, EstateChangeMessage.ChangeStatus.Delete);
					delEstateMap.put("rt_url", "http://www.taofang.com/xq_" + targetPY + "/");
					estateService.updateAuthStatusAndNum(delEstateMap);
					// 更新合并后小区的在线二手房,租房数量.//不对小区图,小区户型图数量进行处理
					Map countMap = new HashMap();
					countMap.put("estateId", targetId);
					countMap.put("resale_num", resaleCount_Added);
					countMap.put("rent_num", rentCount_Added);
					estateService.updateResaleAndRentCount(countMap);
					EstateJms.send(id, EstateChangeMessage.ChangeStatus.Modify);
					model.addAttribute("success", true);
				} catch (Exception e) {
					model.addAttribute("fail", "操作出错！");
					log.error(e.getMessage(), e);
				}

				// 更新每个小区的户型图,(这个功能先留着吧,以后再做)
			}
		}

		return "json";

	}

	@RequestMapping("/testUnion")
	public void testUnion() {
		List<UnionLog> logs = new ArrayList<UnionLog>();
		UnionLog rootLog = new UnionLog();
		rootLog.setEstateid(4);
		rootLog.setTargetId(5);
		logs.add(rootLog);
		List<Integer> ids = new ArrayList<Integer>();
	//	String rt_url = "http://www.taofang.com/xq_test/";
		//getAllEstateIdByParentId(logs, ids, rt_url);
		for (Integer id : ids) {
			System.out.println("-----" + id);

		}

	}

	public static void main(String args[]) {
		List<UnionLog> logs = new ArrayList<UnionLog>();
		UnionLog rootLog = new UnionLog();
		rootLog.setEstateid(4);
		rootLog.setTargetId(5);
		logs.add(rootLog);
		List<UnionLog> childLogs = new ArrayList<UnionLog>();
		for (int i = 1; i < 4; i++) {
			UnionLog childLog = new UnionLog();
			childLog.setEstateid(i);
			childLog.setTargetId(i + 1);
			childLogs.add(childLog);
		}
		List<Integer> ids = new ArrayList<Integer>();
		// getAllEstateIdByParentId(logs,ids);
		for (Integer id : ids) {
			System.out.println("-----" + id);

		}
	}

	public Integer getTargetid(Map<Integer, Integer> paramMap, Integer key) {
		if (paramMap.get(key) != null && key.intValue() != paramMap.get(key).intValue()) {
			return getTargetid(paramMap, paramMap.get(key));
		}
		return key;
	}

	/**
	 * 更新所以被合并小区子节点的rt_url;
	 * @param logs
	 * @param newRtUrl
	 */
	private void updateAllChildRturl(List<UnionLog> logs, String newRtUrl) {
		for (UnionLog log : logs) {
			estateService.udpateRturl(newRtUrl, log.getEstateid());
			List<UnionLog> tmpLogs = unionLogService.getUnionLogBySourceId(log.getEstateid());
			if (tmpLogs == null || tmpLogs.size() == 0) {
				continue;
			} else {
				updateAllChildRturl(tmpLogs, newRtUrl);
			}
		}

	}

	/**
	 * 小区合并日志
	 * 
	 * @param condition
	 * @return
	 */
	@RequestMapping("/unionLog")
	public String unionLogList(LogCondition condition, Model model) {
		Map paramMap = new HashMap();
		paramMap.put("showHouse", condition.getShowHouse());
		if (condition.getEstateid() != null && condition.getEstateid() != 0) {
			paramMap.put("s_estateid", condition.getEstateid());
		} else {
			condition.setEstateid(null);
		}
		if (condition.getTargetId() != null && condition.getTargetId() != 0) {
			paramMap.put("t_estateid", condition.getTargetId());

		} else {
			condition.setTargetId(null);
		}
		List<UnionLog> logList = unionLogService.list(paramMap);
		model.addAttribute("unionLogList", logList);
		model.addAttribute("condition", condition);
		return "tiles:estate.logList";
	}

	/**
	 * 撤销合并
	 * 
	 * @return
	 */
	@RequestMapping("/cancelUnion")
	public String cancelUnion(LogCondition condition, Model model) {
		try {
			List<UnionLog> unionList = unionLogService.getUnionLogByCondition(condition);
			int resaleNum = 0;
			int rentNum = 0;
			// 统计要撤销的二手房源和小区房源数量
			for (UnionLog unionLog : unionList) {
				String estateName = unionLog.getEstatename();
				unionLog.setEstatename(estateName);
				unionLogService.updateEstateId(unionLog);
				if (StringUtils.isNotBlank(unionLog.getFromTable())) {
					if (unionLog.getFromTable().contains("resale")) {
						resaleNum += unionLog.getNum();
					} else if (unionLog.getFromTable().contains("rent")) {
						rentNum += unionLog.getNum();
					}
				}
			}
			// 更新小区状态
			// 和小区在线二手房和租房数量(统计在线二手房和租房数量的问题存在不准确的问题,这里不处理了,交给后台统计job来处理了,这里只更新小区状态了)
			estateService.updateAuthStatus(condition.getS_authStatus(), condition.getEstateid() != null ? condition
					.getEstateid().intValue() : 0, null);
			// 删除在合并日志中的记录.
			unionLogService.delete(condition);
			model.addAttribute("success", true);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
		}
		return "json";
	}

	@RequestMapping("/initRturl")
	public void updateEstateRtUrl(HttpServletResponse response) {
		Pagination pagination = Pagination.threadLocal.get();
		Map paramMap = new HashMap();
		paramMap.put("showHouse", 0);
		pagination.setPageNo(0);
		pagination.setPageSize(5000);
		List<UnionLog> logList = unionLogService.list(paramMap);
		for (UnionLog log : logList) {
			Estate estateData = estateService.getEstateInfoById(log.getTargetId());
			if (estateData != null) {
				String url = "http://www.taofang.com/xq_" + estateData.getPinYin() + "/";
				estateService.udpateRturl(url, log.getEstateid());
			}
		}
		sentResponseInfo(response, "执行完毕");

	}

	@RequestMapping("/test")
	public String test() {
		return "tiles:forward:list";
	}

	private void setEstateData(EstateData estateData) {
		if (estateData != null) {
			estateData.setCityName(getNameById(estateData.getCityId()));
			estateData.setDistName(getNameById(estateData.getDistId()));
			estateData.setBlockName(getNameById(estateData.getBlockId()));
			estateData.setCityCode(getCodeById(estateData.getCityId()));
		}
	}

	private String getNameById(Integer id) {

		if (id != null && id != 0 && id != -1) {
			return localeService.getName(id.intValue());
		}
		return null;
	}

	private String getCodeById(Integer id) {
		if (id != null && id != 0 && id != -1) {
			return localeService.getCode(id.intValue());
		}
		return null;
	}

	private void setDefaultCity(EstateListView condition, Integer cityId, Model model) {
		if (cityId != null) {
			List<Locale> distList = localeService.getDistLocalByCityId(cityId);
			model.addAttribute("distList", distList);
		}
	}

	private void saveDist(EstatePhotoCondition condition, Model model) {
		Integer cityId = null;
		if (condition != null) {
			cityId = condition.getCityId();
		}
		if (cityId != null) {
			List<Locale> distList = localeService.getDistLocalByCityId(cityId);
			model.addAttribute("distList", distList);
		}
	}

	private void setEstateData(EstatePhoto photo) {
		if (photo != null) {
			photo.setCityName(getNameById(photo.getCityId()));
			photo.setDistName(getNameById(photo.getDistId()));
			photo.setBlockName(getNameById(photo.getBlockId()));
		}
	}

	private String getReturnUrl(String fromStatus, HttpServletRequest request) {
		StringBuffer sb = new StringBuffer();
		HttpSession session = request.getSession();
		Integer cityId = (Integer) session.getAttribute("cityId");
		Integer distId = (Integer) session.getAttribute("distId");
		Integer blockId = (Integer) session.getAttribute("blockId");
		sb.append("authStatus=").append(fromStatus);
		if (cityId != null) {
			sb.append("&cityId=").append(cityId);
		}
		if (distId != null) {
			sb.append("&distId=").append(distId);
		}
		if (blockId != null) {
			sb.append("&blockId=").append(blockId);
		}
		String url = sb.toString();
		return url;
	}

	private EstateListView setCityInfoFromSession(HttpServletRequest request, Integer cityId, Integer distId,
			Integer blockId, EstateListView condition) {
		if (cityId == null) {
			cityId = (Integer) request.getSession().getAttribute("cityId");
		}
		condition.setCityId(cityId);
		if (distId == null) {
			distId = (Integer) request.getSession().getAttribute("distId");
		}
		condition.setDistId(distId);
		if (blockId == null) {
			blockId = (Integer) request.getSession().getAttribute("blockId");
		}
		condition.setBlockId(blockId);
		return condition;
	}

	private void saveResaleUnionLog(Map paramMap) {
		List<UnionLog> unionLogList = resaleService.getHouseByEstateId(paramMap);
		// 记录日志
		if (unionLogList == null || unionLogList.size() == 0) {
			saveUnionLog(paramMap);
		} else {
			unionLogService.save(unionLogList);
		}
	}

	private void saveRentUnionLog(Map paramMap) {
		List<UnionLog> unionLogList = rentService.getRentByEstateId(paramMap);
		// 记录日志
		if (unionLogList == null || unionLogList.size() == 0) {
			saveUnionLog(paramMap);
		} else {
			unionLogService.save(unionLogList);
		}

	}

	private void saveUnionLog(Map paramMap) {
		UnionLog log = new UnionLog();
		System.out.println("--authorStatus--" + log.getS_authStatus() + "----t_estate_id---" + log.getTargetId()
				+ "estate_id---" + log.getEstatename() + "----estatename===" + log.getEstatename() + "----code--"
				+ log.getT_CityCode() + "---operator---" + log.getOperator());
		log.setS_authStatus((String) paramMap.get("s_authStatus"));
		log.setTargetId((Integer) paramMap.get("t_estate_id"));
		log.setEstateid((Integer) paramMap.get("s_estate_id"));
		log.setEstatename((String) paramMap.get("estateName"));
		log.setT_CityCode((String) paramMap.get("cityCode"));
		log.setOperator((String) paramMap.get("operator"));
		unionLogService.insert(log);
	}
	
	@RequestMapping(value = "/getestate")
	public void getestate(Model model, HttpServletRequest request, HttpServletResponse response) {
		try {
			String keyword = request.getParameter("estatename");
			EstateSearchParam estateSearchParam = new EstateSearchParam();
			
			int cityId = 1;
			if(request.getParameter("cityId")!=null){
				try{
					cityId = Integer.parseInt(request.getParameter("cityId"));
				}catch (Exception e) {
					cityId = 1;
				}
			}
			
			int start = 0;
			if(request.getParameter("start")!=null){
				try{
				start = Integer.parseInt(request.getParameter("start"));
				}catch (Exception e) {
					start = 0;
				}
			}
			int limit = 20;
			if(request.getParameter("limit")!=null){
				try{
					limit = Integer.parseInt(request.getParameter("limit"));
				}catch (Exception e) {
					limit = 20;
				}
			}
			estateSearchParam.setStart(start);
			estateSearchParam.setLimit(limit);
			estateSearchParam.setCityId(cityId);
			estateSearchParam.setKeyword(keyword);
			String result = estateService.doSearchForAdd(estateSearchParam);
			EstateMapper estateMapper= new EstateMapper(result);
			List<EstateSearch> eslist = estateMapper.parse();
			model.addAttribute("elist", eslist);
			model.addAttribute("records", estateMapper.getTotalNum());
			model.addAttribute("start", start);
			
		} catch (Exception e) {
			model.addAttribute("code", -1);
			model.addAttribute("msg", Resources.getString("errs.action.estate.namemissing"));
		}

		response.setHeader("Content-Type","text/plain; charset=utf-8");
		String jsonpcallback = request.getParameter("jsonpcallback");
		String scriptcallback = request.getParameter("scriptcallback");
		
		String callback = request.getParameter("callback");
		if(jsonpcallback!=null){
			sentResponseInfo(response, jsonpcallback+"("+gson.toJson(model)+" )");
			
		}else if(callback!=null){
			sentResponseInfo(response, callback+"("+gson.toJson(model)+")");
		}else if(scriptcallback!=null){
			response.setHeader("Content-Type","text/html; charset=utf-8");
			sentResponseInfo(response, "<script>"+ scriptcallback+"( "+gson.toJson(model)+" ) </script>");
		}else{
			sentResponseInfo(response, gson.toJson(model));
		}
		
	}

}
