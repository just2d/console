package com.nuoshi.console.common.util;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.nuoshi.console.domain.agent.AgentMaster;
import com.nuoshi.console.domain.agent.AgentRecharge;
import com.nuoshi.console.domain.agent.TranDetail;

public class RechargeUtil {
	
	public static Double doubelRound(Double d){
		if(d==null) d=0.0;
		String str = d.toString();
		BigDecimal bd =new BigDecimal(str);
		bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
		return bd.doubleValue();
	}
	
	public static String getDateTomorrow(String endDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		try {
			Date date = sdf.parse(endDate);
			Calendar calendar=Calendar.getInstance();   
			calendar.setTime(date); 
			calendar.add(Calendar.DATE, 1);
			date =calendar.getTime();
			String str = sdf.format(date);
			return str;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static boolean isDouble(String str){
		 try{

		       Double.parseDouble(str);

		       return true;

		      }catch(Exception ex){

		        return false;

		     }
	}
	
	public static boolean isInteger(String str){
		 try{

		       Integer.parseInt(str);

		       return true;

		      }catch(Exception ex){

		        return false;

		     }
	}
	
	public static boolean isLong(String str){
		 try{

		       Long.parseLong(str);

		       return true;

		      }catch(Exception ex){

		        return false;

		     }
	}
	
	/**
	 * 比较易宝网银支付返回的充值信息与db是否匹配，不匹配的话给出错误提示
	 * @param agentRechargeDb
	 * @param agentId
	 * @param rechargeAmount
	 * @return
	 */
	public static String verifyRechargeInfo(AgentRecharge	agentRechargeDb,Long agentId,Double rechargeAmount){
		String errorStr="";
		if (agentRechargeDb == null) {
			errorStr = "系统无此订单";
			return errorStr;
		}
		if(agentRechargeDb.getRechargeState()==2||agentRechargeDb.getRechargeState()==4){
			errorStr="该订单已支付成功，无须再次支付";
			return errorStr;
		}
		if(agentId ==null ||!agentId.equals(agentRechargeDb.getAgentId())){
			errorStr="经纪人信息不一致,无法充值,订单号为"+agentRechargeDb.getRechargeOrderNo()+",数据库中经纪人id为"+agentRechargeDb.getAgentId()+",返回经纪人id为"+agentId;
			return errorStr;
		}
		
		if(rechargeAmount ==null ||!rechargeAmount.equals(agentRechargeDb.getRechargeAmount())){
			errorStr="订单金额不匹配,无法充值,订单号为"+agentRechargeDb.getRechargeOrderNo()+",数据库中金额为"+agentRechargeDb.getRechargeAmount()+",返回金额为"+rechargeAmount;
			return errorStr;
		}
		return errorStr;
	}
	
	/**
	 * 根据给定参数生成一条交易明细
	 * @param agentId
	 * @param agentUserName
	 * @param amount
	 * @param balance
	 * @param cityId
	 * @param rechargeOrderNo
	 * @return
	 */
	
	public static TranDetail createTranDetail(AgentRecharge agentRechargeDb,AgentMaster agentMaster){
		TranDetail tranDetail = new TranDetail();
		tranDetail.setAgentId(agentRechargeDb.getAgentId());
		tranDetail.setAgentUserName(agentRechargeDb.getAgentUserName());
		tranDetail.setAmount(agentRechargeDb.getRechargeAmount());
		tranDetail.setBalance(agentMaster.getBalance());
		tranDetail.setCityId(agentRechargeDb.getAgentCityId());
		tranDetail.setOrderType(0);
		tranDetail.setConsumeType(0);
		tranDetail.setOrderNo(agentRechargeDb.getRechargeOrderNo());
		tranDetail.setSalerId(agentRechargeDb.getSalerId());
		return tranDetail;
	}
}
