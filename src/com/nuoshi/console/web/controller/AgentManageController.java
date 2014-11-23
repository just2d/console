package com.nuoshi.console.web.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.nuoshi.console.common.Globals;
import com.nuoshi.console.common.Resources;
import com.nuoshi.console.common.phone.PhoneService;
import com.nuoshi.console.common.util.SmcConst;
import com.nuoshi.console.dao.AgentMasterDao;
import com.nuoshi.console.domain.agent.AgentManage;
import com.nuoshi.console.domain.agent.AgentMaster;
import com.nuoshi.console.domain.agent.AgentMsg;
import com.nuoshi.console.domain.agent.AgentSearch;
import com.nuoshi.console.domain.agent.CityDist;
import com.nuoshi.console.domain.agent.HouseInfo;
import com.nuoshi.console.domain.agent.RejectReason;
import com.nuoshi.console.domain.agent.TransferPhone;
import com.nuoshi.console.domain.agent.history.OperatHistory;
import com.nuoshi.console.domain.base.House;
import com.nuoshi.console.domain.pckage.AgentPackageRelation;
import com.nuoshi.console.domain.rent.Rent;
import com.nuoshi.console.domain.resale.Resale;
import com.nuoshi.console.domain.user.TFUser;
import com.nuoshi.console.domain.user.User;
import com.nuoshi.console.persistence.read.taofang.pckage.APRelationReadMapper;
import com.nuoshi.console.persistence.read.taofang.pckage.AgentPackageReadMapper;
import com.nuoshi.console.persistence.write.taofang.pckage.APRelationWriteMapper;
import com.nuoshi.console.service.AgentManageService;
import com.nuoshi.console.service.AgentMasterService;
import com.nuoshi.console.service.LocaleService;
import com.nuoshi.console.service.MessageService;
import com.nuoshi.console.service.OperatHistoryService;
import com.nuoshi.console.service.RentService;
import com.nuoshi.console.service.ResaleService;
import com.nuoshi.console.service.SmsService;
import com.nuoshi.console.service.TransferPhoneService;
import com.nuoshi.console.web.session.SessionUser;

/**
 * @author wanghongmeng
 * 
 */
@Controller
@RequestMapping(value = "/agentManage")
public class AgentManageController extends BaseController {
	@Autowired
	private AgentManageService agentManageService;
	@Autowired
	private AgentMasterService agentMasterService;
	@Autowired
	private TransferPhoneService transferPhoneService;
	@Autowired
	private AgentMasterDao agentMasterDao;
	@Autowired
	private RentService rentService;
	@Autowired
	private ResaleService resaleService;
	@Autowired
	private OperatHistoryService operatHistoryService;
	@Autowired
	private MessageService messageService;
	

	/**
	 * @param model
	 * @return 经纪人管理页面
	 */
	@RequestMapping(value = "/list")
	public String listAgent(Model model) {
		List<AgentManage> agentList = agentManageService.getAllAgentByPage(null);
		List<Integer> idList=new ArrayList<Integer>();
		for(AgentManage manage : agentList) {
			idList.add(manage.getId());
			if(manage.getPhone400() != null && !"".equals(manage.getPhone400())) {
				manage.setBoundDate(transferPhoneService.getBoundDate(manage.getPhone400()));
			}
		}
		//总分数
		Map<Integer,Double> scoreMap=new HashMap<Integer,Double>();
		Map<Integer,Integer> countMap=new HashMap<Integer,Integer>();
		if(CollectionUtils.isNotEmpty(idList)){
			//根据经纪人id查询房源分数(baseScore)
			//包含的内容为:房源id，房源评分
			List<Integer> typeList=new ArrayList<Integer>();
			typeList.add(House.HOUSE_STATUS_UNSHELVED);
			typeList.add(House.HOUSE_STATUS_SHELVED);
			typeList.add(House.HOUSE_STATUS_NOAUDIT_AGENT);
			List<Rent> rentListScore=rentService.getRentForScore(idList,typeList);
			List<Resale> resaleListScore=resaleService.getResaleForScore(idList,typeList);
			if(CollectionUtils.isNotEmpty(resaleListScore)){
				for (Resale resale : resaleListScore) {
					if(resale==null){
						continue;
					}
					Double houseScore=resale.getBaseScore();
					if(houseScore==null){
						houseScore = new Double(0); ;
					}
					Integer agentId=resale.getAuthorid();
					Double mapScore=scoreMap.get(agentId)==null?0:scoreMap.get(agentId);
					Integer mapCount=countMap.get(agentId)==null?0:countMap.get(agentId);
					scoreMap.put(agentId,mapScore+houseScore);
					countMap.put(agentId, ++mapCount);
				}
			}
			if(CollectionUtils.isNotEmpty(rentListScore)){
				for (Rent rent : rentListScore) {
					if(rent==null){
						continue;
					}
					Double houseScore=rent.getBaseScore();
					Integer agentId=rent.getAuthorid();
					if(houseScore==null){
						houseScore = new Double(0); ;
					}
					Double mapScore=scoreMap.get(agentId)==null?0:scoreMap.get(agentId);
					Integer mapCount=countMap.get(agentId)==null?0:countMap.get(agentId);
					scoreMap.put(agentId,mapScore+houseScore);
					countMap.put(agentId, ++mapCount);
				}
			}
		}
		for(AgentManage manage : agentList) {
			Integer id=manage.getId();
			int agentHouseCount = 0;
			Double doubleAve=0.00;
			if(scoreMap.get(id)!=null&&countMap.get(id)!=null){
				doubleAve=scoreMap.get(id)/countMap.get(id);
				agentHouseCount = countMap.get(id);
			}
			DecimalFormat   format   =   new   DecimalFormat( "0.00"); 
			String aveForMat=format.format(doubleAve);
			Double ave=Double.parseDouble(aveForMat);
			manage.setAgentHouseCount(agentHouseCount);
			manage.setAveScore(ave);
		}
		model.addAttribute("agentList", agentList);
		return "tiles:agentManage.list";
	}

	/**
	 * @param model
	 * @param citydir
	 *            经纪人所在城市拼音
	 * @param id
	 *            经纪人id
	 * @param type
	 *            房源状态
	 * @return 经纪人房源页面
	 */
	@RequestMapping(value = "/house/{citydir}/{id}/{type}")
	public String listHouse(Model model,
			@PathVariable("citydir") String citydir,
			@PathVariable("id") int id, @PathVariable("type") int type) {
		List<HouseInfo> houseList = agentManageService.getAllHouseByPage(id, type);
		String agentname = agentManageService.getAgentInfo(id).getName();
		model.addAttribute("houseList", houseList);
		model.addAttribute("citydir", citydir);
		model.addAttribute("id", id);
		model.addAttribute("type", type);
		model.addAttribute("name", agentname);
		return "tiles:agentManage.house";
	}

	/**
	 * @param model
	 * @param id
	 *            经纪人id
	 * @return 经纪人信用页面
	 */
	/*@RequestMapping(value = "/rate/{id}")
	public String listRate(Model model, @PathVariable("id") int id) {
		List<AgentRate> rateList = agentManageService.getAllRateByPage(id);
		AgentManage agent = agentManageService.getAgentInfo(id);
		model.addAttribute("rateList", rateList);
		model.addAttribute("agent", agent);
		return "tiles:agentManage.rate";
	}*/

	/**
	 * @param model
	 * @param id
	 *            经纪人id
	 * @return 删除经纪人的操作结果
	 */
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public String deleteAgent(Model model, HttpServletRequest request, @PathVariable("id") int id) {
		SessionUser sessionUser = (SessionUser) request.getSession().getAttribute("sessionUser");
		User user = sessionUser.getUser();
		String msg = Resources.getString("agent.delete.success");
		try {
			agentManageService.deleteAgent(id, user.getId(), user.getChnName());
		} catch (Exception e) {
			msg = Resources.getString("agent.delete.fail");
			e.printStackTrace();
		}
		model.addAttribute(SmcConst.MESSAGE, msg);
		return "json";
	}

	/**
	 * @param model
	 * @param id
	 *            经纪人id
	 * @return 重置经纪人的操作结果
	 */
	@RequestMapping(value = "/reset/{id}", method = RequestMethod.GET)
	public String resetAgent(Model model, @PathVariable("id") int id) {
		String msg = Resources.getString("agent.reset.success");
		
		try {
			agentManageService.resetAgent(id);		
			
		} catch (Exception e) {
			msg = Resources.getString("agent.reset.fail");
			e.printStackTrace();
		}
		model.addAttribute(SmcConst.MESSAGE, msg);
		return "json";
	}
	
	/**
	 * @param model
	 * @param id
	 *            经纪人id
	 * @return 经纪人具体信息
	 */
	@RequestMapping(value = "/ajax/edit/{id}", method = RequestMethod.GET)
	public String editAgent(Model model, @PathVariable("id") String id) {
		AgentManage agent = agentManageService.getAgentInfo(Integer
				.parseInt(id));
		model.addAttribute("agent", agent);
		return "json";
	}
	

	/**
	 * @param model
	 * @param pid
	 *            地标的父亲id
	 * @return 地标的信息集合
	 */
	@RequestMapping(value = "/ajax/zone/{pid}", method = RequestMethod.GET)
	public String getCityDist(Model model, @PathVariable("pid") String pid) {
		try {
			pid = URLDecoder.decode(pid, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		List<CityDist> distList;
		if (pid.indexOf("请选择") > -1) {
			distList = null;
		} else {
			distList = agentManageService.getCityDist(Integer.parseInt(pid));
		}
		model.addAttribute("distList", distList);
		return "json";
	}

	/**
	 * @param model
	 * @param agent
	 *            经纪人信息
	 * @return 更新经纪人信息操作结果
	 */
	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public String updateAgent(Model model, AgentManage agent) {
		String msg;
		try {
			agentManageService.updateAgent(agent);
			msg = Resources.getString("agent.edit.success");
		} catch (Exception e) {
			msg = Resources.getString("agent.edit.fail");
			e.printStackTrace();
		}
		model.addAttribute(SmcConst.MESSAGE, msg);
		return "json";
	}

	/**
	 * @param model
	 * @param blockid
	 *            商圈id
	 * @return 该商圈内的公司名称集合
	 */
	@RequestMapping(value = "/ajax/brand/{blockid}", method = RequestMethod.GET)
	public String getBrandByBlockId(Model model,
			@PathVariable("blockid") String blockid) {
		try {
			blockid = URLDecoder.decode(blockid, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		List<String> brandList;
		if (blockid.indexOf("请选择") > -1) {
			brandList = null;
		} else {
			brandList = agentManageService.getBrandByBlockId(Integer
					.parseInt(blockid));
		}
		model.addAttribute("list", brandList);
		return "json";
	}

	/**
	 * @param model
	 * @param brand
	 *            公司名称
	 * @return 该公司旗下的门店集合
	 */
	@RequestMapping(value = "/ajax/address/{brand}", method = RequestMethod.GET)
	public String getAddressByBrand(Model model,
			@PathVariable("brand") String brand) {
		try {
			brand = URLDecoder.decode(brand, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		List<String> addressList;
		if (brand.indexOf("请选择") > -1) {
			addressList = null;
		} else {
			addressList = agentManageService.getAddressByBrand(brand);
		}
		model.addAttribute("list", addressList);
		return "json";
	}

	/**
	 * @param model
	 * @param agentSearch
	 *            经纪人搜索条件
	 * @return 符合搜索条件的经纪人管理页面
	 */
	@RequestMapping(value = "/search")
	public String searchAgent(Model model, AgentSearch agentSearch) {
		List<String> conditionList = agentManageService.convertSearchCondition(agentSearch);
		if (conditionList.size() == 0) {
			conditionList = null;
		}
		List<AgentManage> agentList = agentManageService.getAllAgentByPage(conditionList);
		List<Integer> idList=new ArrayList<Integer>();
		for(AgentManage manage : agentList) {
			idList.add(manage.getId());
			if(manage.getPhone400() != null && !"".equals(manage.getPhone400())) {
				manage.setBoundDate(transferPhoneService.getBoundDate(manage.getPhone400()));
			}
		}
		//总分数
		Map<Integer,Double> scoreMap=new HashMap<Integer,Double>();
		Map<Integer,Integer> countMap=new HashMap<Integer,Integer>();
		if(CollectionUtils.isNotEmpty(idList)){
			//根据经纪人id查询房源分数(baseScore)
			//包含的内容为:房源id，房源评分
			List<Integer> typeList=new ArrayList<Integer>();
			typeList.add(House.HOUSE_STATUS_UNSHELVED);
			typeList.add(House.HOUSE_STATUS_SHELVED);
			typeList.add(House.HOUSE_STATUS_NOAUDIT_AGENT);
			List<Rent> rentListScore=rentService.getRentForScore(idList,typeList);
			List<Resale> resaleListScore=resaleService.getResaleForScore(idList,typeList);
			if(CollectionUtils.isNotEmpty(resaleListScore)){
				for (Resale resale : resaleListScore) {
					if(resale==null){
						continue;
					}
					Double houseScore=resale.getBaseScore();
					Integer agentId=resale.getAuthorid();
					if(houseScore==null){
						houseScore = new Double(0); ;
					}
					Double mapScore=scoreMap.get(agentId)==null?0:scoreMap.get(agentId);
					Integer mapCount=countMap.get(agentId)==null?0:countMap.get(agentId);
					scoreMap.put(agentId,mapScore+houseScore);
					countMap.put(agentId, ++mapCount);
				}
			}
			if(CollectionUtils.isNotEmpty(rentListScore)){
				for (Rent rent : rentListScore) {
					if(rent==null){
						continue;
					}
					
					Double houseScore=rent.getBaseScore();
					Integer agentId=rent.getAuthorid();
					if(houseScore==null){
						houseScore = new Double(0); ;
					}
					Double mapScore=scoreMap.get(agentId)==null?0:scoreMap.get(agentId);
					Integer mapCount=countMap.get(agentId)==null?0:countMap.get(agentId);
					scoreMap.put(agentId,mapScore+houseScore);
					countMap.put(agentId, ++mapCount);
				}
			}
		}
		for(AgentManage manage : agentList) {
			Integer id=manage.getId();
			Double doubleAve=0.00;
			int agentHouseCount = 0;
			if(scoreMap.get(id)!=null&&countMap.get(id)!=null){
				doubleAve=scoreMap.get(id)/countMap.get(id);
				agentHouseCount = countMap.get(id);
			}
			DecimalFormat   format   =   new   DecimalFormat( "0.00"); 
			String aveForMat=format.format(doubleAve);
			Double ave=Double.parseDouble(aveForMat);
			manage.setAveScore(ave);
			manage.setAgentHouseCount(agentHouseCount);
		}
		
		model.addAttribute("agentList", agentList);
		model.addAttribute("agentSearch", agentSearch);
		return "tiles:agentManage.list";
	}

	/**
	 * @param model
	 * @return 所有的短信信息集合
	 */
	@RequestMapping(value = "/ajax/msg", method = RequestMethod.GET)
	public String getAllMsg(Model model) {
		List<RejectReason> msgList = agentManageService
				.getAllMsg(SmcConst.AGENT_MSG);
		model.addAttribute("msgList", msgList);
		return "json";
	}

	/**
	 * @param model
	 * @param rejectReason
	 *            增加的短信
	 * @return 增加短信操作结果
	 */
	@RequestMapping(value = "/ajax/msg/add", method = RequestMethod.GET)
	public String addMsg(Model model, RejectReason rejectReason) {
		int result = agentManageService.addMsg(rejectReason);
		String msg;
		if (result > 0) {
			msg = Resources.getString("msg.add.success");
		} else {
			msg = Resources.getString("msg.add.fail");
		}
		model.addAttribute(SmcConst.MESSAGE, msg);
		return "json";
	}

	/**
	 * @param model
	 * @param agentMsg
	 *            发送短信对象，包含经纪人的手机号集合和发送短信内容
	 * @return 发送短信结果
	 */
	@RequestMapping(value = "/ajax/msg/send", method = RequestMethod.GET)
	public String semdMsg(Model model, AgentMsg agentMsg) {
		String[] mobiles = agentMsg.getMobiles().split(",");
		String content = agentMsg.getContent();
		int result = SmsService.sentSms(mobiles, content);
		String msg;
		if (result == 0) {
			msg = Resources.getString("msg.send.success");
		} else {
			msg = Resources.getString("msg.send.fail");
		}
		model.addAttribute(SmcConst.MESSAGE, msg);
		return "json";
	}

	/**
	 * @param model
	 * @param id
	 *            短信id
	 * @return 删除短信操作结果
	 */
	@RequestMapping(value = "/ajax/msg/del/{id}", method = RequestMethod.GET)
	public String delMsg(Model model, @PathVariable("id") String id) {
		String msg;
		try {
			agentManageService.delMsg(Integer.parseInt(id));
			msg = Resources.getString("msg.del.success");
		} catch (Exception e) {
			msg = Resources.getString("msg.del.fail");
			e.printStackTrace();
		}
		model.addAttribute(SmcConst.MESSAGE, msg);
		return "json";
	}
	
	@RequestMapping("/disbound/{agentId}")
	public String disbound400Phone(Model model, HttpServletRequest request, HttpServletResponse response, @PathVariable("agentId")int agentId) {
		PhoneService phoneService = new PhoneService();
	    TransferPhone transferPhone = transferPhoneService.selPhoneByAgentId(agentId);
	    AgentMaster master = agentMasterDao.selectAgentMasterById(agentId);
	    String info = "fail";
	    if(transferPhone != null) {
	    	Boolean delResult = phoneService.deleteUser(transferPhone.getUser400Id());
	    	if(delResult) {
	    		int ret1 = agentManageService.disbound400Phone(agentId);
	    		if(ret1 > 0) {
	    			int ret2 = transferPhoneService.resetPhoneStatus(transferPhone.getPhone());
	    			if(ret2 > 0) {
	    				info = "success";
	    			}
	    		}
	    	}
	    } else if(master != null && master.getV400Number() != null && !"".equals(master.getV400Number())) {
	    	int ret1 = agentManageService.disbound400Phone(agentId);
    		if(ret1 > 0) {
    			int ret2 = transferPhoneService.resetPhoneStatus(master.getV400Number());
    			if(ret2 > 0) {
    				info = "success";
    			}
    		}
	    }
	    model.addAttribute("result", info);
	    return "json";
	}
	
	@RequestMapping("/ajax/switch/{agentId}/{type}/{tipType}")
	public String agentSwitch(Model model, HttpServletRequest request,
			HttpSession session, @PathVariable("agentId") int agentId,
			@PathVariable("type") String type,@PathVariable("tipType")int tipType) {
		Boolean flag = true;
		//  AgentMaster agent = agentMasterService.selectAgentMasterById(agentId);
		if ("Y".equals(type)) {
			// 下架
			try {
				// 更新房源
				rentService.outOfStockByAgentId(agentId);
				resaleService.outOfStockByAgentId(agentId);
			} catch (Exception e) {
				flag = false;
			}
		}
		// 更改状态
		if (flag) {
			agentMasterService.updateAgentForStartStop(agentId, type,tipType);
			try {

				// 发站内信
				String content = "";
				String title = "房源发布通知";
				if ("Y".equals(type)) {
					content = "尊敬的淘房网用户，您好：  <br/>  您的套餐已到期，在线房源已下架，现可发布待发布房源。<br/>  如有问题，可联系淘房工作人员，谢谢！  ";
				}
				if ("N".equals(type)) {
					content = "尊敬的淘房网用户，您好：  <br/>  您的套餐已激活，现可发布在线房源。感谢您对淘房网的支持，祝您多多开单！  ";
				}
				TFUser users = new TFUser();
				users.setId(agentId);
				messageService.sendMessageSys(title, content, users);
			} catch (Exception e) {
				log.error("站内信发送失败！" + e.getMessage());
			}
			try {

				SessionUser sessionUser = (SessionUser) session
						.getAttribute("sessionUser");
				User user = sessionUser.getUser();
				String name = user.getChnName();
				OperatHistory history = new OperatHistory();
				history.setOperandsId(agentId);
				history.setOperatorId(user.getId());
				String operationalContext = ("Y".equals(type)  ? "关闭" : "打开")
						+ "了ID为'" + agentId + "' 的经纪人。";
				history.setOperationalContext(operationalContext);
				history.setOperatorName(name);
				history.setOperatorType(Globals.HISTORY_SWITCH_AGENT);
				history.setDates(new java.util.Date());
				operatHistoryService.save(history);
			} catch (Exception e) {
				log.error("记录操作历史失败！" + e.getMessage());
			}
		}
		model.addAttribute("result", flag ? "succeed" : "fail");
		return "json";
	}
	
	
	
	@RequestMapping("ajax/operatHistory/{agentId}/{type}")
	public String selectHistory(Model model, HttpServletRequest request,
			HttpSession session, @PathVariable("agentId") int agentId,
			@PathVariable("type") Integer type) {
		List<OperatHistory> list=operatHistoryService.selectHistory(agentId, type);
		model.addAttribute("operatHistorys",list);
		return "json";
	}
	
	
}
