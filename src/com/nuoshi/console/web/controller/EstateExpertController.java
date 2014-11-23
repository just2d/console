package com.nuoshi.console.web.controller;

import java.io.PrintWriter;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.nuoshi.console.common.util.Utilities;
import com.nuoshi.console.domain.estate.EstateData;
import com.nuoshi.console.domain.estate.EstateExpert;
import com.nuoshi.console.domain.agent.AgentMaster;
import com.nuoshi.console.domain.user.User;
import com.nuoshi.console.service.AgentMasterService;
import com.nuoshi.console.service.EstateExpertService;
import com.nuoshi.console.service.EstateService;
import com.nuoshi.console.web.session.SessionUser;

@Controller
@RequestMapping("/estateexpert")
public class EstateExpertController extends BaseController {
	
	private final String RESPONSE_TYPE = "application/vnd.ms-excel";
	
	private final String EXPERT_TITLE_1 = "经纪人ID";
	private final String EXPERT_TITLE_2 = "姓名";
	private final String EXPERT_TITLE_3 = "电话";
	private final String EXPERT_TITLE_4 = "城市-区域";
	private final String EXPERT_TITLE_5 = "小区(专家数量)";
	private final String EXPERT_TITLE_6 = "级别";
	private final String EXPERT_TITLE_7 = "开始日期";
	private final String EXPERT_TITLE_8 = "结束日期";
	
	@Resource
	private EstateExpertService estateExpertService;
	@Resource
	private EstateService estateService;
	
	@Resource
	private AgentMasterService agentMasterService;

	/**
	 * 小区专家列表
	 * 
	 * @return
	 */
	@RequestMapping("/list")
	public String list(@RequestParam(value = "cityId", required = false) Integer cityId,
			@RequestParam(value = "agentId", required = false) Integer agentId,
			@RequestParam(value = "agentName", required = false) String agentName,
			@RequestParam(value = "agentPhone", required = false) String agentPhone,
			@RequestParam(value = "estateName", required = false) String estateName, Model model,
			HttpServletRequest request) {
		
		List<EstateExpert> estateExpertList = estateExpertService.getEstateExpert(cityId,agentId,agentName,agentPhone,estateName);
		model.addAttribute("estateExpertList", estateExpertList);
		
		model.addAttribute("cityId", cityId==null?0:cityId);
		model.addAttribute("agentId", agentId==null?0:cityId);
		model.addAttribute("agentName", agentName);
		model.addAttribute("agentPhone", agentPhone);
		model.addAttribute("estateName", estateName);
		return "tiles:estateexpert.list";
	}
	
	/**
	 * 小区专家列表
	 * 
	 * @return
	 */
	@RequestMapping("/download")
	public String download(@RequestParam(value = "cityId", required = false) Integer cityId,
			@RequestParam(value = "agentId", required = false) Integer agentId,
			@RequestParam(value = "agentName", required = false) String agentName,
			@RequestParam(value = "agentPhone", required = false) String agentPhone,
			@RequestParam(value = "estateName", required = false) String estateName, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		
		List<EstateExpert> estateExpertList = estateExpertService.getEstateExpertDownload(cityId,agentId,agentName,agentPhone,estateName);
		writeExcel(response, estateExpertList);
		return null;
	}
	
	public void writeExcel(HttpServletResponse response, List<EstateExpert> estateExpertList) {
		try {
			response.setContentType(RESPONSE_TYPE);
			response.addHeader("Content-Disposition",
					"attachment;filename=" + new String("estateExpert.xls".getBytes("gb2312"), "iso8859-1"));
			response.setCharacterEncoding("gb2312");
			PrintWriter out;
			out = response.getWriter();
			out.println(EXPERT_TITLE_1 + "\t" + EXPERT_TITLE_2 + "\t" +EXPERT_TITLE_3 +"\t"+ EXPERT_TITLE_4 + "\t" + EXPERT_TITLE_5+ "\t"+ EXPERT_TITLE_6 + "\t"+ EXPERT_TITLE_7 + "\t"+ EXPERT_TITLE_8 );
			for (EstateExpert expert : estateExpertList) {
				String expertType= "";
				switch (expert.getExpertType()) {
				case 1:
					expertType = "金牌";
					break;
				case 2:
					expertType = "银牌";
					break;
				}
				out.println(expert.getAgentId() + "\t" + expert.getAgentName() + "\t" + expert.getAgentPhone() + "\t" + expert.getCityName()+"-"+expert.getDistName()
						+ "\t" + expert.getEstateName()+"("+expert.getExpertCount()+")" + "\t"+ expertType+ "\t"+ expert.getStartTimeStr() + "\t"+ expert.getEndTimeStr());
			}
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 跳转到添加小区专家页面
	 * 
	 * @return
	 */
	@RequestMapping("/add")
	public String add(Model model,HttpServletRequest request) {
		
		return "tiles:estateexpert.add";
	}
	
	/**
	* 添加小区专家
	 * 
	 * @return
	 */
	@RequestMapping("/save")
	public String save(EstateExpert estateExpert,Model model,HttpServletRequest request,HttpSession session) {
		EstateData estate = estateService.getEstateDataById(estateExpert.getEstateId());
		if(estate == null){
			model.addAttribute("result", "fail");
			model.addAttribute("error", "小区不存在！");
			return "json";
		}
		AgentMaster agentMaster = agentMasterService.selectAgentMasterByMobile(estateExpert.getAgentPhone());
		
		if(agentMaster == null){
			model.addAttribute("result", "fail");
			model.addAttribute("error", "经纪人不存在！");
			return "json";
		}
		estateExpert.setAgentId(agentMaster.getId());
		estateExpert.setAgentName(agentMaster.getName());
		SessionUser sessionUser = (SessionUser) session.getAttribute("sessionUser");
		User user=null;
		if(sessionUser!=null){
			user=sessionUser.getUser();
		}
		estateExpert.setAssignedBy(user.getId());
		estateExpert.setStartTime(Utilities.parseShortDate(estateExpert.getStartTimeStr()));
		estateExpert.setEndTime(Utilities.parseShortDate(estateExpert.getEndTimeStr()));
		
		estateExpertService.save(estateExpert);
		
		return "json";
	}
	
	
	
	/**
	 * 更新小区专家
	 * 
	 * @return
	 */
	@RequestMapping("/update")
	public String update(EstateExpert estateExpert,
			 Model model, HttpServletRequest request) {
		estateExpert.setEndTime(Utilities.parseShortDate(estateExpert.getEndTimeStr()));
		estateExpertService.updateEstateExpert(estateExpert);
		
		
		return "json";
	}
	
	/**
	 * 删除小区专家
	 * 
	 * @return
	 */
	@RequestMapping("/delete")
	public String delete(@RequestParam(value = "id", required = true) Integer id,
			 Model model, HttpServletRequest request) {
		
		int deleteNum = estateExpertService.deleteEstateExpert(id);
		if(deleteNum ==1){
			model.addAttribute("result", "success");
		}else{
			model.addAttribute("result", "fail");
		}
		
		return "json";
	}
}
