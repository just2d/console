package com.nuoshi.console.web.controller;

import java.io.PrintWriter;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nuoshi.console.common.util.RechargeUtil;
import com.nuoshi.console.common.util.SmcConst;
import com.nuoshi.console.common.util.Utilities;
import com.nuoshi.console.domain.agent.AgentRecharge;
import com.nuoshi.console.domain.agent.TranDetail;
import com.nuoshi.console.domain.agent.TranDetailAnalysis;
import com.nuoshi.console.service.TranDetailService;
import com.nuoshi.console.web.session.SessionUser;

@Controller
@RequestMapping(value = "/order")
public class TranDetailController extends BaseController {
	private final String RESPONSE_TYPE = "application/vnd.ms-excel";

	private final String SUCCESS_TITLE_1 = "订单号";
	private final String SUCCESS_TITLE_2 = "金额";
	private final String SUCCESS_TITLE_7 = "充值方式";
	private final String SUCCESS_TITLE_3 = "支付时间";
	private final String SUCCESS_TITLE_4 = "淘房账号";
	private final String SUCCESS_TITLE_8 = "城市";
	private final String SUCCESS_TITLE_9 = "公司";
	private final String SUCCESS_TITLE_10 = "门店";
	private final String SUCCESS_TITLE_5 = "订单状态";
	private final String SUCCESS_TITLE_6 = "操作人";

	private final String NOT_SUCCESS_TITLE_1 = "订单号";
	private final String NOT_SUCCESS_TITLE_2 = "金额";
	private final String NOT_SUCCESS_TITLE_9 = "充值方式";
	private final String NOT_SUCCESS_TITLE_3 = "订单生成时间";
	private final String NOT_SUCCESS_TITLE_4 = "淘房账号";
	private final String NOT_SUCCESS_TITLE_10 = "城市";
	private final String NOT_SUCCESS_TITLE_5 = "订单状态";
	private final String NOT_SUCCESS_TITLE_6 = "姓名";
	private final String NOT_SUCCESS_TITLE_7 = "公司";
	private final String NOT_SUCCESS_TITLE_8 = "电话";
	@Resource
	private TranDetailService tranDetailService;

	//查询支付成功订单
	@RequestMapping(value = "/successmanage")
	public String successmanage(Model model, HttpServletRequest request) {
		String nick = request.getParameter("nick");
		String rechargeType = request.getParameter("rechargeType");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String orderNo = request.getParameter("orderNo");
		String orderState = request.getParameter("orderState");		
		
		//按条件查询支付成功的订单并分页
		List<AgentRecharge> list = tranDetailService.getSuccessTranDetail(nick, rechargeType, startDate, endDate,
				orderNo, orderState);
		Double zhongji= tranDetailService.getSuccessTranDetailAmount(nick, rechargeType, startDate, endDate,
				orderNo, orderState);
		zhongji = RechargeUtil.doubelRound(zhongji);
		model.addAttribute("rechargeList", list);
		model.addAttribute("zhongji", zhongji);
		Double heji=0.0;
		for(int i=0;i<list.size();i++){
			AgentRecharge ar  = list.get(i);
			heji+= ar.getRechargeAmount();
		}
		heji = RechargeUtil.doubelRound(heji);
		model.addAttribute("heji", heji);
		model.addAttribute("orderState", orderState == null ? "-1" : orderState);
		model.addAttribute("rechargeType", rechargeType == null ? "-1" : rechargeType);
		return "tiles:recharge.successlist";
	}

	//下载支付成功订单的详细信息
	@RequestMapping(value = "/downloadsuccesslist")
	public String downloadsuccesslist(Model model, HttpServletRequest request, HttpServletResponse response) {
		String nick = request.getParameter("nick");
		String rechargeType = request.getParameter("rechargeType");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String orderNo = request.getParameter("orderNo");
		String orderState = request.getParameter("orderState");		
		//查询符合条件的所有支付成功的订单
		List<AgentRecharge> list = tranDetailService.getDownloadSuccessTranDetail(nick, rechargeType, startDate,
				endDate, orderNo, orderState);
		//将支付成功list写入文件供用户下载
		writeSuccessExcel(response, list);
		return null;
	}

	//将支付成功list写入文件供用户下载
	public void writeSuccessExcel(HttpServletResponse response, List<AgentRecharge> list) {
		try {
			response.setContentType(RESPONSE_TYPE);
			response.addHeader("Content-Disposition",
					"attachment;filename=" + new String("successTranDetail.xls".getBytes("gb2312"), "iso8859-1"));
			response.setCharacterEncoding("gb2312");
			PrintWriter out;
			out = response.getWriter();
			out.println(SUCCESS_TITLE_1 + "\t" + SUCCESS_TITLE_2 + "\t" +SUCCESS_TITLE_7 +"\t"+ SUCCESS_TITLE_3 + "\t" + SUCCESS_TITLE_4+ "\t"+ SUCCESS_TITLE_8 + "\t"+ SUCCESS_TITLE_9 + "\t"+ SUCCESS_TITLE_10 
					+ "\t" + SUCCESS_TITLE_5 + "\t" + SUCCESS_TITLE_6);
			for (AgentRecharge agentRecharge : list) {
				String rechargeState = agentRecharge.getRechargeState() == 4 ? "充值成功,交易结束" : "支付成功,等待充值";
				String rechargeType="";
				if(agentRecharge.getRechargeType()==0){
					rechargeType="网上银行";
				}else if(agentRecharge.getRechargeType()==1){
					rechargeType="支付宝";
				}else if(agentRecharge.getRechargeType()==2){
					rechargeType="手机充值卡";
				}
				String rechargeNo = "t" + agentRecharge.getRechargeOrderNo();
				String paidTime="";
				if(agentRecharge.getPaidTime()!=null){
					paidTime = Utilities.formatDefaultDate(agentRecharge.getPaidTime());
				}
				out.println(rechargeNo + "\t" + agentRecharge.getRechargeAmount() + "\t" + rechargeType + "\t" + paidTime
						+ "\t" + agentRecharge.getAgentUserName() + "\t"+ agentRecharge.getCityName() + "\t"+ agentRecharge.getCompany() + "\t"+ agentRecharge.getStore() + "\t" + rechargeState + "\t"
						+ agentRecharge.getEntryId());
			}
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//查询未支付成功订单
	@RequestMapping(value = "/notsuccessmanage")
	public String notsuccessmanage(Model model, HttpServletRequest request) {
		String nick = request.getParameter("nick");
		String rechargeType = request.getParameter("rechargeType");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String orderNo = request.getParameter("orderNo");
		String orderState = request.getParameter("orderState");		
		//查询符合条件的未支付成功订单并分页
		List<AgentRecharge> list = tranDetailService.getNotSuccessTranDetail(nick, rechargeType, startDate, endDate,
				orderNo, orderState);
		Double zhongji= tranDetailService.getNotSuccessTranDetailAmount(nick, rechargeType, startDate, endDate,
						orderNo, orderState);
		zhongji = RechargeUtil.doubelRound(zhongji);
		model.addAttribute("rechargeList", list);
		model.addAttribute("zhongji", zhongji);
		Double heji=0.0;
		for(int i=0;i<list.size();i++){
			AgentRecharge ar  = list.get(i);
			heji+= ar.getRechargeAmount();
		}
		heji = RechargeUtil.doubelRound(heji);
		model.addAttribute("heji", heji);
		model.addAttribute("orderState", orderState == null ? "-1" : orderState);
		model.addAttribute("rechargeType", rechargeType == null ? "-1" : rechargeType);
		return "tiles:recharge.notsuccesslist";
	}

	//下载未支付成功订单的详细信息
	@RequestMapping(value = "/downloadnotsuccesslist")
	public String downloadnotsuccesslist(Model model, HttpServletRequest request,HttpServletResponse response) {
		String nick = request.getParameter("nick");
		String rechargeType = request.getParameter("rechargeType");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String orderNo = request.getParameter("orderNo");
		String orderState = request.getParameter("orderState");		
		//查询符合条件的所有未支付成功的订单
		List<AgentRecharge> list = tranDetailService.getDownloadNotSuccessTranDetail(nick, rechargeType, startDate,
				endDate, orderNo, orderState);
		//将未支付成功list写入文件供用户下载
		writeNotSuccessExcel(response, list);
		return null;
	}

	//将未支付成功list写入文件供用户下载
	public void writeNotSuccessExcel(HttpServletResponse response, List<AgentRecharge> list) {
		try {
			response.setContentType(RESPONSE_TYPE);
			response.addHeader("Content-Disposition",
					"attachment;filename=" + new String("notsuccessTranDetail.xls".getBytes("gb2312"), "iso8859-1"));
			response.setCharacterEncoding("gb2312");
			PrintWriter out;
			out = response.getWriter();
			out.println(NOT_SUCCESS_TITLE_1 + "\t" + NOT_SUCCESS_TITLE_2 + "\t"+ NOT_SUCCESS_TITLE_9 + "\t" + NOT_SUCCESS_TITLE_3 + "\t"
					+ NOT_SUCCESS_TITLE_4 + "\t"+ NOT_SUCCESS_TITLE_10 + "\t" + NOT_SUCCESS_TITLE_5 + "\t" + NOT_SUCCESS_TITLE_6 + "\t"
					+ NOT_SUCCESS_TITLE_7 + "\t" + NOT_SUCCESS_TITLE_8);

			for (AgentRecharge agentRecharge : list) {
				String rechargeState = agentRecharge.getRechargeState() == 1 ? "支付处理中" : "支付失败";
				String orderNo = "t" + agentRecharge.getRechargeOrderNo();
				String rechargeType="";
				if(agentRecharge.getRechargeType()==0){
					rechargeType="网上银行";
				}else if(agentRecharge.getRechargeType()==1){
					rechargeType="支付宝";
				}else if(agentRecharge.getRechargeType()==2){
					rechargeType="手机充值卡";
				}
				String createTime = "";
				if(agentRecharge.getCreateTime()!=null ){
					createTime = Utilities.formatDefaultDate(agentRecharge.getCreateTime());
				}
				out.println(orderNo + "\t" + agentRecharge.getRechargeAmount() + "\t"+ rechargeType + "\t"  + createTime
						+ "\t" + agentRecharge.getAgentUserName() + "\t" + agentRecharge.getCityName()+"\t" + rechargeState + "\t"
						+ agentRecharge.getAgentName() + "\t" + agentRecharge.getCompany() + "\t"
						+ agentRecharge.getAgentMobile());
			}
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//查询后台用户明细
	@RequestMapping(value = "/query")
	public String query(Model model, HttpServletRequest request) {

		String nick = request.getParameter("nick");
		String cityId = request.getParameter("cityId");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String type = request.getParameter("type");
		//查询符合条件的交易信息并分页
		List<TranDetail> list = tranDetailService.getTranDetail(cityId, nick, type, startDate, endDate);
		model.addAttribute("tranDetailList", list);
		double rechargeHeji=0.0;
		double purchaseHeji=0.0;
		for(int i=0;i<list.size();i++){
		 TranDetail td =list.get(i);
		 if(td.getOrderType()==0){
			 rechargeHeji+=td.getAmount();
		 }else if(td.getOrderType()==1){
			 purchaseHeji+=td.getAmount();
		 }
		}
		rechargeHeji = RechargeUtil.doubelRound(rechargeHeji);
		purchaseHeji = RechargeUtil.doubelRound(purchaseHeji);
		model.addAttribute("rechargeHeji", rechargeHeji);
		model.addAttribute("purchaseHeji", purchaseHeji);
		List<TranDetail> listZhongji = tranDetailService.getTranDetailZhongji(cityId, nick, type, startDate, endDate);
		double rechargeZhongji = 0.0;
		double purchaseZhongji = 0.0;
		for(int i=0;i<listZhongji.size();i++){
			 TranDetail td =listZhongji.get(i);
			 if(td.getOrderType()==0){
				 rechargeZhongji+=td.getAmount();
			 }else if(td.getOrderType()==1){
				 purchaseZhongji+=td.getAmount();
			 }
			}
		rechargeZhongji = RechargeUtil.doubelRound(rechargeZhongji);
		purchaseZhongji = RechargeUtil.doubelRound(purchaseZhongji);
		model.addAttribute("rechargeZhongji", rechargeZhongji);
		model.addAttribute("purchaseZhongji", purchaseZhongji);
		
		model.addAttribute("selecttype", type == null ? "-1" : type);
		model.addAttribute("cityId", cityId == null ? 0 : cityId);
		return "tiles:recharge.query";
	}

	//查询某条订单的支付状态，若为成功则为用户充值，若为不成功则更新状态为支付失败
	@RequestMapping(value = "/querytranresult")
	public String queryTranResult(Model model, HttpServletRequest request) {
		String orderTobankno = request.getParameter("orderTobankno");
		String orderaccpbank = request.getParameter("orderaccpbank");
		SessionUser sessionUser = (SessionUser) request.getSession().getAttribute(SmcConst.SESSION_USER);
		String name = sessionUser.getUser().getUserName();
		String errorStr = "";
		if (Integer.valueOf(orderaccpbank) == 0) {
			//查询订单的支付状态，若为支付成功则为用户充值，若为不成功则更新状态为支付失败
			try {
				errorStr = tranDetailService.queryTranResult(orderTobankno, name);
			} catch (Exception e) {
				e.printStackTrace();
				errorStr = e.getMessage();
			}
			if ("".equals(errorStr)) {
				model.addAttribute("info", "success");
			} else {
				model.addAttribute("info", "error");
				model.addAttribute("errorStr", errorStr);
				if ((errorStr.startsWith("该订单未支付") || errorStr.startsWith("该订单已取消") || errorStr.startsWith("银行无此订单"))&&errorStr.endsWith(",已将支付状态更新为失败")) {
					model.addAttribute("errflag", "1");
				} else {
					model.addAttribute("errflag", "0");
					AgentRecharge ar =new AgentRecharge();
					ar.setRechargeOrderNo(Long.valueOf(orderTobankno));
					ar.setBankFeedbackMsg(errorStr);
					tranDetailService.updateBankMsg(ar);
				}
			}
		}
		return "json";
	}

	//查询交易信息的统计情况
	@RequestMapping(value = "/querytranAnalysis")
	public String querytranAnalysis(Model model, HttpServletRequest request) {
		String city = request.getParameter("city");
		String nick = request.getParameter("nick");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		//根据条件查询交易的统计信息
		TranDetailAnalysis tda = tranDetailService.getTranDetailAnalysis(city, nick, startDate, endDate);
		model.addAttribute("tranDetailAnalysis", tda);
		return "json";
	}

}
