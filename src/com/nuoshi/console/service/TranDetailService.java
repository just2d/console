package com.nuoshi.console.service;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.nuoshi.console.common.util.RechargeUtil;
import com.nuoshi.console.common.yibaozhifu.Configuration;
import com.nuoshi.console.common.yibaozhifu.DigestUtil;
import com.nuoshi.console.common.yibaozhifu.PaymentForOnlineService;
import com.nuoshi.console.common.yibaozhifu.QueryResult;
import com.nuoshi.console.domain.agent.AgentMaster;
import com.nuoshi.console.domain.agent.AgentRecharge;
import com.nuoshi.console.domain.agent.TranDetail;
import com.nuoshi.console.domain.agent.TranDetailAnalysis;
import com.nuoshi.console.domain.crm.Saler;
import com.nuoshi.console.domain.pckage.AgentPackage;
import com.nuoshi.console.persistence.read.crm.SalerReadMapper;
import com.nuoshi.console.persistence.read.taofang.agent.TFUserReadMapper;
import com.nuoshi.console.persistence.read.taofang.agent.TranDetailReadMapper;
import com.nuoshi.console.persistence.read.taofang.pckage.AgentPackageReadMapper;
import com.nuoshi.console.persistence.write.taofang.agent.AgentMasterWriteMapper;
import com.nuoshi.console.persistence.write.taofang.agent.AgentRechargeWriteMapper;
import com.nuoshi.console.persistence.write.taofang.agent.TranDetailWriteMapper;

@Service
public class TranDetailService {
	
	private HashMap<String,HashMap<Integer,String>> cache = new HashMap<String,HashMap<Integer,String>>();

	@Resource
	private AgentRechargeWriteMapper agentRechargeWriteMapper;
	@Resource
	private TranDetailReadMapper tranDetailReadMapper;
	@Resource
	private AgentMasterWriteMapper agentMasterWriteMapper;
	@Resource
	private TranDetailWriteMapper tranDetailWriteMapper;
	@Resource
	private TFUserReadMapper tFUserReadMapper;
	@Resource
	private SalerReadMapper salerReadMapper;
	@Resource
	private AgentPackageReadMapper packageReadMapper;

	// 查询符合条件的支付成功的订单并分页
	public List<AgentRecharge> getSuccessTranDetail(String userName, String rechargeType, String startDate,
			String endDate, String rechargeNo, String rechargeState) {

		int rechargeNoInt = 0;
		if (StringUtils.isNotBlank(rechargeNo) && RechargeUtil.isInteger(rechargeNo)) {
			rechargeNoInt = Integer.valueOf(rechargeNo);
		}
		int rechargeTypeInt = -1;
		if (StringUtils.isNotBlank(rechargeType) && RechargeUtil.isInteger(rechargeType)) {
			rechargeTypeInt = Integer.valueOf(rechargeType);
		}
		if (StringUtils.isNotBlank(startDate)) {
			if("起始日期".equals(startDate.trim())){
				startDate="";
			}else{
				startDate = startDate.trim().replaceAll("-", "");
			}
		}
		if (StringUtils.isNotBlank(endDate)) {
			if("终止日期".equals(endDate.trim())){
				endDate = "";
			}else{
				endDate = endDate.trim().replaceAll("-", "");
				endDate = RechargeUtil.getDateTomorrow(endDate);
			}
		}
		int rechargeStateInt = 0;
		if (StringUtils.isNotBlank(rechargeState) && RechargeUtil.isInteger(rechargeState)) {
			rechargeStateInt = Integer.valueOf(rechargeState);
		}
		List<AgentRecharge> list = tranDetailReadMapper.getSuccessTranDetailByPage(rechargeNoInt, userName,
				rechargeTypeInt, startDate, endDate, rechargeStateInt);
		if(CollectionUtils.isNotEmpty(list)){
			for(AgentRecharge recharge:list){
				recharge.setCityName(LocaleService.getName(recharge.getAgentCityId()));
			}
		}
		return list;
	}
	//查询支付成功订单的总计金额
	public Double getSuccessTranDetailAmount(String userName, String rechargeType, String startDate, String endDate,
			String rechargeNo, String rechargeState) {

		int rechargeNoInt = 0;
		if (StringUtils.isNotBlank(rechargeNo) && RechargeUtil.isInteger(rechargeNo)) {
			rechargeNoInt = Integer.valueOf(rechargeNo);
		}
		int rechargeTypeInt = -1;
		if (StringUtils.isNotBlank(rechargeType) && RechargeUtil.isInteger(rechargeType)) {
			rechargeTypeInt = Integer.valueOf(rechargeType);
		}
		if (StringUtils.isNotBlank(startDate)) {
			if("起始日期".equals(startDate.trim())){
				startDate="";
			}else{
				startDate = startDate.trim().replaceAll("-", "");
			}
		}
		if (StringUtils.isNotBlank(endDate)) {
			if("终止日期".equals(endDate.trim())){
				endDate = "";
			}else{
				endDate = endDate.trim().replaceAll("-", "");
				endDate = RechargeUtil.getDateTomorrow(endDate);
			}
		}
		int rechargeStateInt = 0;
		if (StringUtils.isNotBlank(rechargeState) && RechargeUtil.isInteger(rechargeState)) {
			rechargeStateInt = Integer.valueOf(rechargeState);
		}
		Double zhongji = tranDetailReadMapper.getSuccessTranDetailAmount(rechargeNoInt, userName,
				rechargeTypeInt, startDate, endDate, rechargeStateInt);
		return zhongji;
	}

	// 查询所有符合条件的支付成功的订单
	public List<AgentRecharge> getDownloadSuccessTranDetail(String userName, String rechargeType, String startDate,
			String endDate, String rechargeNo, String rechargeState) {

		int rechargeNoInt = 0;
		if (StringUtils.isNotBlank(rechargeNo) && RechargeUtil.isInteger(rechargeNo)) {
			rechargeNoInt = Integer.valueOf(rechargeNo);
		}
		int rechargeTypeInt = -1;
		if (StringUtils.isNotBlank(rechargeType) && RechargeUtil.isInteger(rechargeType)) {
			rechargeTypeInt = Integer.valueOf(rechargeType);
		}
		if (StringUtils.isNotBlank(startDate)) {
			if("起始日期".equals(startDate.trim())){
				startDate="";
			}else{
				startDate = startDate.trim().replaceAll("-", "");
			}
		}
		if (StringUtils.isNotBlank(endDate)) {
			if("终止日期".equals(endDate.trim())){
				endDate = "";
			}else{
				endDate = endDate.trim().replaceAll("-", "");
				endDate = RechargeUtil.getDateTomorrow(endDate);
			}
		}
		int rechargeStateInt = 0;
		if (StringUtils.isNotBlank(rechargeState) && RechargeUtil.isInteger(rechargeState)) {
			rechargeStateInt = Integer.valueOf(rechargeState);
		}

		List<AgentRecharge> list = tranDetailReadMapper.getSuccessTranDetail(rechargeNoInt, userName, rechargeTypeInt,
				startDate, endDate, rechargeStateInt);
		if(CollectionUtils.isNotEmpty(list)){
			for(AgentRecharge recharge:list){
				recharge.setCityName(LocaleService.getName(recharge.getAgentCityId()));
			}
		}
		return list;
	}

	// 查询符合条件的未支付成功的订单并分页
	public List<AgentRecharge> getNotSuccessTranDetail(String userName, String rechargeType, String startDate,
			String endDate, String rechargeNo, String rechargeState) {

		int rechargeNoInt = 0;
		if (StringUtils.isNotBlank(rechargeNo) && RechargeUtil.isInteger(rechargeNo)) {
			rechargeNoInt = Integer.valueOf(rechargeNo);
		}
		int rechargeTypeInt = -1;
		if (StringUtils.isNotBlank(rechargeType) && RechargeUtil.isInteger(rechargeType)) {
			rechargeTypeInt = Integer.valueOf(rechargeType);
		}
		if (StringUtils.isNotBlank(startDate) ) {
			if("起始日期".equals(startDate.trim())){
				startDate="";
			}else{
				startDate = startDate.trim().replaceAll("-", "");
			}
		}
		if (StringUtils.isNotBlank(endDate)) {
			if("终止日期".equals(endDate.trim())){
				endDate = "";
			}else{
				endDate = endDate.trim().replaceAll("-", "");
				endDate = RechargeUtil.getDateTomorrow(endDate);
			}
		}
		int rechargeStateInt = 0;
		if (StringUtils.isNotBlank(rechargeState) && RechargeUtil.isInteger(rechargeState)) {
			rechargeStateInt = Integer.valueOf(rechargeState);
		}

		List<AgentRecharge> list = tranDetailReadMapper.getNotSuccessTranDetailByPage(rechargeNoInt, userName,
				rechargeTypeInt, startDate, endDate, rechargeStateInt);
		if(CollectionUtils.isNotEmpty(list)){
			for(AgentRecharge recharge:list){
				recharge.setCityName(LocaleService.getName(recharge.getAgentCityId()));
			}
		}
		return list;
	}

	// 查询所有符合条件的未支付成功的订单
	public List<AgentRecharge> getDownloadNotSuccessTranDetail(String userName, String rechargeType, String startDate,
			String endDate, String rechargeNo, String rechargeState) {

		int rechargeNoInt = 0;
		if (StringUtils.isNotBlank(rechargeNo) && RechargeUtil.isInteger(rechargeNo)) {
			rechargeNoInt = Integer.valueOf(rechargeNo);
		}
		int rechargeTypeInt = -1;
		if (StringUtils.isNotBlank(rechargeType) && RechargeUtil.isInteger(rechargeType)) {
			rechargeTypeInt = Integer.valueOf(rechargeType);
		}
		if (StringUtils.isNotBlank(startDate)) {
			if("起始日期".equals(startDate.trim())){
				startDate="";
			}else{
				startDate = startDate.trim().replaceAll("-", "");
			}
		}
		if (StringUtils.isNotBlank(endDate)) {
			if("终止日期".equals(endDate.trim())){
				endDate = "";
			}else{
				endDate = endDate.trim().replaceAll("-", "");
				endDate = RechargeUtil.getDateTomorrow(endDate);
			}
		}
		int rechargeStateInt = 0;
		if (StringUtils.isNotBlank(rechargeState) && RechargeUtil.isInteger(rechargeState)) {
			rechargeStateInt = Integer.valueOf(rechargeState);
		}

		List<AgentRecharge> list = tranDetailReadMapper.getNotSuccessTranDetail(rechargeNoInt, userName,
				rechargeTypeInt, startDate, endDate, rechargeStateInt);
		if(CollectionUtils.isNotEmpty(list)){
			for(AgentRecharge recharge:list){
				recharge.setCityName(LocaleService.getName(recharge.getAgentCityId()));
			}
		}
		return list;
	}
	
	public Double getNotSuccessTranDetailAmount(String userName, String rechargeType, String startDate, String endDate,
			String rechargeNo, String rechargeState) {
		int rechargeNoInt = 0;
		if (StringUtils.isNotBlank(rechargeNo) && RechargeUtil.isInteger(rechargeNo)) {
			rechargeNoInt = Integer.valueOf(rechargeNo);
		}
		int rechargeTypeInt = -1;
		if (StringUtils.isNotBlank(rechargeType) && RechargeUtil.isInteger(rechargeType)) {
			rechargeTypeInt = Integer.valueOf(rechargeType);
		}
		if (StringUtils.isNotBlank(startDate)) {
			if("起始日期".equals(startDate.trim())){
				startDate="";
			}else{
				startDate = startDate.trim().replaceAll("-", "");
			}
		}
		if (StringUtils.isNotBlank(endDate)) {
			if("终止日期".equals(endDate.trim())){
				endDate = "";
			}else{
				endDate = endDate.trim().replaceAll("-", "");
				endDate = RechargeUtil.getDateTomorrow(endDate);
			}
		}
		int rechargeStateInt = 0;
		if (StringUtils.isNotBlank(rechargeState) && RechargeUtil.isInteger(rechargeState)) {
			rechargeStateInt = Integer.valueOf(rechargeState);
		}

		Double zhongji = tranDetailReadMapper.getNotSuccessTranDetailAmount(rechargeNoInt, userName,
				rechargeTypeInt, startDate, endDate, rechargeStateInt);
		return zhongji;
	}

	// 查询符合条件的交易信息并分页
	public List<TranDetail> getTranDetail(String cityId, String nick, String type, String startDate, String endDate) {
		int cityIdInt = 0;
		if (StringUtils.isNotBlank(cityId) && RechargeUtil.isInteger(cityId)) {
			cityIdInt = Integer.valueOf(cityId);
		}

		int typeInt = -1;
		if (StringUtils.isNotBlank(type) && RechargeUtil.isInteger(type)) {
			typeInt = Integer.valueOf(type);
		}

		if (StringUtils.isNotBlank(startDate)) {
			if("起始日期".equals(startDate.trim())){
				startDate="";
			}else{
				startDate = startDate.trim().replaceAll("-", "");
			}
		}
		if (StringUtils.isNotBlank(endDate)) {
			if("终止日期".equals(endDate.trim())){
				endDate = "";
			}else{
				endDate = endDate.trim().replaceAll("-", "");
				endDate = RechargeUtil.getDateTomorrow(endDate);
			}
		}

		List<TranDetail> list = tranDetailReadMapper.getTranDetailByPage(cityIdInt, nick, typeInt, startDate, endDate);
		
	
		if(CollectionUtils.isNotEmpty(list)){
				for(TranDetail td:list){
					if(td.getSalerId()!=null&&!"".equals(td.getSalerId().toString())){
						td.setSalerName(getSalers().get(td.getSalerId()));
					}
					if(td.getPackageId()!=null&&!"".equals(td.getPackageId().toString()) && td.getPackageId() !=0 ){
						AgentPackage apackage = packageReadMapper.getPackageById(td.getPackageId());
						if(apackage !=null){
							td.setPackageName(apackage.getPackageName());
						}else{
							td.setPackageName("");
						}
						
					}
					td.setCityName(LocaleService.getName(td.getCityId()));
				}
			}
	
		return list;
	}
	
	private HashMap<Integer,String> getSalers(){
		HashMap<Integer,String> salersMap =  cache.get("com.taofang.salerName");
		if(salersMap==null){
			List<Saler> salersList = salerReadMapper.getSalersCache();
			salersMap = new HashMap<Integer, String>();
			for(Saler saler:salersList ){
				salersMap.put(saler.getId(), saler.getChnName());
			}
			cache.put("com.taofang.salerName", salersMap);
		}
		return salersMap;
	}
	
	private HashMap<Integer,String> getPackage(){
		HashMap<Integer,String> packagesMap = cache.get("com.taofang.packageName");
		if(packagesMap==null){
			List<AgentPackage> packageList = packageReadMapper.getPackageCache();
			packagesMap = new HashMap<Integer, String>();
			for(AgentPackage agentPackage:packageList){
				packagesMap.put(agentPackage.getId(), agentPackage.getPackageName());
			}
			cache.put("com.taofang.packageName", packagesMap);
		}
		return packagesMap;
	}
	
	//查询订单类型及金额以计算总计金额
	public List<TranDetail> getTranDetailZhongji(String cityId, String nick, String type, String startDate,
			String endDate) {
		int cityIdInt = 0;
		if (StringUtils.isNotBlank(cityId) && RechargeUtil.isInteger(cityId)) {
			cityIdInt = Integer.valueOf(cityId);
		}

		int typeInt = -1;
		if (StringUtils.isNotBlank(type) && RechargeUtil.isInteger(type)) {
			typeInt = Integer.valueOf(type);
		}

		if (StringUtils.isNotBlank(startDate)) {
			if("起始日期".equals(startDate.trim())){
				startDate="";
			}else{
				startDate = startDate.trim().replaceAll("-", "");
			}
		}
		if (StringUtils.isNotBlank(endDate)) {
			if("终止日期".equals(endDate.trim())){
				endDate = "";
			}else{
				endDate = endDate.trim().replaceAll("-", "");
				endDate = RechargeUtil.getDateTomorrow(endDate);
			}
		}

		List<TranDetail> list = tranDetailReadMapper.getTranDetailZhongji(cityIdInt, nick, typeInt, startDate, endDate);
		if(CollectionUtils.isNotEmpty(list)){
			for(TranDetail td:list){
				td.setCityName(LocaleService.getName(td.getCityId()));
			}
		}
		return list;

	}

	// 查询某条订单的支付状态，若为成功则为用户充值，若为不成功则更新状态为支付失败
	public String queryTranResult(String orderTobankno, String name) {
		String errorStr = "";
		
		if (!RechargeUtil.isLong(orderTobankno) || Long.valueOf(orderTobankno) < 0) {
			errorStr = "银行订单号格式不正确,此订单号为"+orderTobankno;
			throw new RuntimeException(errorStr);
		}
		
		// 向易宝查询某条订单的支付状态
		QueryResult qr = PaymentForOnlineService.queryByOrder(orderTobankno);

		String keyValue = Configuration.getInstance().getValue("keyValue");
		StringBuffer s = new StringBuffer();
		s.append(qr.getR0_Cmd());
		s.append(qr.getR1_Code());
		s.append(qr.getR2_TrxId());
		s.append(qr.getR3_Amt());
		s.append(qr.getR4_Cur());
		s.append(qr.getR5_Pid());
		s.append(qr.getR6_Order());
		s.append(qr.getR8_MP());
		s.append(qr.getRb_PayStatus());
		s.append(qr.getRc_RefundCount());
		s.append(qr.getRd_RefundAmt());

		// 将返回信息签名
		String signRes = DigestUtil.hmacSign(s.toString(), keyValue);

		String hmac = qr.getHmac();

		if (!RechargeUtil.isLong(qr.getR8_MP()) || Long.valueOf(qr.getR8_MP()) < 0) {
			errorStr = "经纪人信息不正确,经纪人id为"+qr.getR8_MP();
			throw new RuntimeException(errorStr);
		}
		if (!RechargeUtil.isDouble(qr.getR3_Amt()) || Double.valueOf(qr.getR3_Amt()) < 0) {
			errorStr = "订单金额不正确,金额为"+qr.getR3_Amt();
			throw new RuntimeException(errorStr);
		}

		// 验证签名信息一致时
		if (signRes != null && signRes.trim().length() > 0 && signRes.equals(hmac)) {

			if ("1".equals(qr.getR1_Code())) {// 查询正常
				// 订单支付成功时，更新订单状态并为经纪人充值
				if ("SUCCESS".equals(qr.getRb_PayStatus())) {
					errorStr = updaterecharge(Long.valueOf(orderTobankno), Long.valueOf(qr.getR8_MP()),
							Double.valueOf(qr.getR3_Amt()), qr.getR2_TrxId(), name);

				} else {
					// 订单未支付时，更新支付状态为失败
					if ("INIT".equals(qr.getRb_PayStatus())) {
						errorStr = "该订单未支付";
						AgentRecharge agentRecharge = new AgentRecharge();
						agentRecharge.setBankOrderNo(orderTobankno);
						agentRecharge.setRechargeOrderNo(Long.valueOf(orderTobankno));
						agentRecharge.setBankSerialNo(qr.getR2_TrxId());
						int num = agentRechargeWriteMapper.updateRechargeToFail(agentRecharge);
						if (num != 1) {
							errorStr += ",更新订单支付状态为失败时失败,订单号为"+orderTobankno;
							throw new RuntimeException(errorStr);
						} else {
							errorStr += ",已将支付状态更新为失败";
						}
					}
					// 订单已取消时，更新支付状态为失败
					if ("CANCELED".equals(qr.getRb_PayStatus())) {
						errorStr = "该订单已取消";
						AgentRecharge agentRecharge = new AgentRecharge();
						agentRecharge.setBankOrderNo(orderTobankno);
						agentRecharge.setRechargeOrderNo(Long.valueOf(orderTobankno));
						agentRecharge.setBankSerialNo(qr.getR2_TrxId());
						int num = agentRechargeWriteMapper.updateRechargeToFail(agentRecharge);
						if (num != 1) {
							errorStr += ",更新订单支付状态为失败时失败,订单号为"+orderTobankno;
							throw new RuntimeException(errorStr);
						} else {
							errorStr += ",已将支付状态更新为失败";
						}
					}
				}

			}
			// 银行无此订单时，更新支付状态为失败
			if ("50".equals(qr.getR1_Code())) {// 无此订单
				errorStr = "银行无此订单";
				AgentRecharge agentRecharge = new AgentRecharge();
				agentRecharge.setBankOrderNo(orderTobankno);
				agentRecharge.setRechargeOrderNo(Long.valueOf(orderTobankno));
				agentRecharge.setBankSerialNo(qr.getR2_TrxId());
				int num = agentRechargeWriteMapper.updateRechargeToFail(agentRecharge);
				if (num != 1) {
					errorStr += ",更新订单支付状态为失败时失败,订单号为"+orderTobankno;
					throw new RuntimeException(errorStr);
				} else {
					errorStr += ",已将支付状态更新为失败";
				}
			}

		} else {
			errorStr = "交易信息被篡改";
		}

		return errorStr;
	}

	// 支付成功时，更新订单状态并为经纪人充值
	public String updaterecharge(Long rechargeNo, Long agentId, Double amount, String bankserialno, String name) {
		String errorStr = "";
		AgentRecharge agentRecharge = new AgentRecharge();
		agentRecharge.setAgentId(agentId);
		agentRecharge.setRechargeOrderNo(rechargeNo);
		agentRecharge.setRechargeAmount(amount);
		agentRecharge.setBankSerialNo(bankserialno);

		// 根据订单号查询相应订单
		AgentRecharge agentRechargeDb = agentRechargeWriteMapper.getRechargeByRechargeNo(agentRecharge);

		// 比较易宝返回的充值信息与db是否匹配，不匹配的话返回错误提示
		errorStr = RechargeUtil.verifyRechargeInfo(agentRechargeDb, agentId, amount);
		if (!"".equals(errorStr)) {
			throw new RuntimeException(errorStr);
		}

		agentRecharge.setPaidTime(new Timestamp(System.currentTimeMillis()));
		// 将订单状态更新为支付成功
		int updateRechargeNum = agentRechargeWriteMapper.updateRechargeToSuccess(agentRecharge);
		if (updateRechargeNum != 1) {
			errorStr = "更新订单状态为支付成功时失败,订单号为"+agentRechargeDb.getRechargeOrderNo();
			throw new RuntimeException(errorStr);
		}
		Double amountdDouble = agentRecharge.getRechargeAmount();

		// 更新经纪人余额
		int updateBalanceNum = agentMasterWriteMapper.updateAgentBalance(agentRechargeDb.getAgentId(), amountdDouble);
		if (updateBalanceNum != 1) {
			errorStr = "更新经纪人余额时失败,订单号为"+agentRechargeDb.getRechargeOrderNo()+",经纪人id为"+agentRechargeDb.getAgentId();;
			throw new RuntimeException(errorStr);
		}

		// 查询经纪人余额
		AgentMaster agentMaster = agentMasterWriteMapper.getAgentBalance(agentRechargeDb.getAgentId());
		if (agentMaster == null) {
			errorStr = "经纪人信息不正确,订单号为"+agentRechargeDb.getRechargeOrderNo()+",经纪人id为"+agentRechargeDb.getAgentId();
			throw new RuntimeException(errorStr);
		}

		// 创建一条交易明细
		TranDetail tranDetail = RechargeUtil.createTranDetail(agentRechargeDb,agentMaster);
		// 插入交易明细
		int addTranDetaiNum = tranDetailWriteMapper.addTranDetail(tranDetail);

		if (addTranDetaiNum != 1) {
			errorStr = "新增交易纪录时失败,订单号为"+agentRechargeDb.getRechargeOrderNo();
			throw new RuntimeException(errorStr);
		}

		agentRecharge.setEntryId(name);
		agentRecharge.setEntryTime(new Timestamp(System.currentTimeMillis()));
		// 更新订单状态为充值成功
		int updateRechargeChongNum = agentRechargeWriteMapper.updateRechargeToChong(agentRecharge);
		if (updateRechargeChongNum != 1) {
			errorStr = "更新订单状态为充值成功时失败,订单号为"+agentRechargeDb.getRechargeOrderNo();
			throw new RuntimeException(errorStr);
		}

		return errorStr;
	}

	// 根据条件查询交易统计信息
	public TranDetailAnalysis getTranDetailAnalysis(String cityId, String userName, String startDate, String endDate) {

		int cityIdInt = 0;
		if (StringUtils.isNotBlank(cityId)) {
			cityIdInt = Integer.valueOf(cityIdInt);
		}
		if (StringUtils.isNotBlank(startDate)) {
			if("起始日期".equals(startDate.trim())){
				startDate="";
			}else{
				startDate = startDate.trim().replaceAll("-", "");
			}
		}
		if (StringUtils.isNotBlank(endDate) ) {
			if("终止日期".equals(endDate.trim())){
				endDate = "";
			}else{
				endDate = endDate.trim().replaceAll("-", "");
				endDate = RechargeUtil.getDateTomorrow(endDate);
			}
		}

		TranDetailAnalysis tda = new TranDetailAnalysis();
		long agentId = -1;
		AgentMaster am = null;
		// 若淘房账号不为空，先以此为条件查询相应的经纪人信息（包括有无销售人员跟进）
		if (StringUtils.isNotBlank(userName)) {
			// 查询经纪人信息
			am = tFUserReadMapper.selectAgentByNick(userName);
			// 若经纪人信息不为空
			if (am != null && am.getAgentId() > 0) {
				agentId= am.getAgentId();
				// 该经纪人有销售人员跟进
				if (StringUtils.isNotBlank(am.getSalerId())) {
					getAnalysisWithSales(tda, agentId, cityIdInt, startDate, endDate);
				}
				// 该经纪人无销售人员跟进
				else {
					getAnalysisWithoutSales(tda, agentId, cityIdInt, startDate, endDate);
				}
				// 查询充值明细（包括网银，手机充值卡，支付宝）
				getRechargeAnalysis(tda, agentId, cityIdInt, startDate, endDate);
			}
			// 经纪人信息为空时，无须查询直接返回空的统计信息
			else {
				return tda;
			}
		}
		// 查询全网符合条件的交易统计信息
		else {
			getAnalysis(tda, agentId, cityIdInt, startDate, endDate);
			
			// 查询充值明细（包括网银，手机充值卡，支付宝）
			getRechargeAnalysis(tda, agentId, cityIdInt, startDate, endDate);

		}
		return tda;
	}

	/**
	 * 查询充值明细（包括网银，手机充值卡，支付宝）
	 * 
	 * @param tda
	 * @param agentId
	 * @param cityIdInt
	 * @param startDate
	 * @param endDate
	 */
	public void getRechargeAnalysis(TranDetailAnalysis tda, Long agentId, int cityIdInt, String startDate,
			String endDate) {
		// 查询经纪人充值明细(网银)
		TranDetailAnalysis onlineBankAmount = tranDetailReadMapper.getTranDetailAnalysisOnlineBankRecharge(agentId,
				cityIdInt, startDate, endDate);
		if (onlineBankAmount == null || onlineBankAmount.getRechargeOnlineAmount() == null) {
			tda.setRechargeOnlineAmount(0.0);
		} else {
			tda.setRechargeOnlineAmount(onlineBankAmount.getRechargeOnlineAmount());
		}
		// 查询经纪人充值明细(支付宝)
		TranDetailAnalysis zhifubaoAmount = tranDetailReadMapper.getTranDetailAnalysisZhifubaoRecharge(agentId,
				cityIdInt, startDate, endDate);
		if (zhifubaoAmount == null || zhifubaoAmount.getRechargeZhifuboaAmount() == null) {
			tda.setRechargeZhifuboaAmount(0.0);
		} else {
			tda.setRechargeZhifuboaAmount(zhifubaoAmount.getRechargeZhifuboaAmount());
		}
		// 查询经纪人充值明细(手机充值卡)
		TranDetailAnalysis shoujikaAmount = tranDetailReadMapper.getTranDetailAnalysisShoujikaRecharge(agentId,
				cityIdInt, startDate, endDate);
		if (shoujikaAmount == null || shoujikaAmount.getRechargeShoujikaAmount() == null) {
			tda.setRechargeShoujikaAmount(0.0);
		} else {
			tda.setRechargeShoujikaAmount(shoujikaAmount.getRechargeShoujikaAmount());
		}
	}

	/**
	 * 查询有销售人员跟进的统计信息
	 * 
	 * @param tda
	 * @param agentId
	 * @param cityIdInt
	 * @param startDate
	 * @param endDate
	 */
	public void getAnalysisWithSales(TranDetailAnalysis tda, Long agentId, int cityIdInt, String startDate,
			String endDate) {
		// 查询消费激活数据
		TranDetailAnalysis activeWithSales = tranDetailReadMapper.getTranDetailAnalysisActiveWithSales(agentId,
				cityIdInt, startDate, endDate);
		if (activeWithSales == null || activeWithSales.getPurchaseActiveAmountWithSales() == null) {
			tda.setPurchaseActiveAmountWithSales(0.0);
		} else {
			tda.setPurchaseActiveAmountWithSales(activeWithSales.getPurchaseActiveAmountWithSales());
		}
		// 查询充值数据
		TranDetailAnalysis rechargeWithSales = tranDetailReadMapper.getTranDetailAnalysisRechargeWithSales(agentId,
				cityIdInt, startDate, endDate);
		if (rechargeWithSales == null || rechargeWithSales.getRechargeAmountWithSales() == null
				|| rechargeWithSales.getRechargeNumWithSales() == null) {
			tda.setRechargeAmountWithSales(0.0);
			tda.setRechargeNumWithSales(0);
		} else {
			tda.setRechargeAmountWithSales(rechargeWithSales.getRechargeAmountWithSales());
			tda.setRechargeNumWithSales(rechargeWithSales.getRechargeNumWithSales());
		}
		// 查询消费数据
		TranDetailAnalysis purchaseWithSales = tranDetailReadMapper.getTranDetailAnalysisPurchaseWithSales(agentId,
				cityIdInt, startDate, endDate);
		if (purchaseWithSales == null || purchaseWithSales.getPurchaseAmountWithSales() == null) {
			tda.setPurchaseAmountWithSales(0.0);
		} else {
			tda.setPurchaseAmountWithSales(purchaseWithSales.getPurchaseAmountWithSales());
		}
	}

	/**
	 * 查询无销售人员跟进的统计信息
	 * 
	 * @param tda
	 * @param agentId
	 * @param cityIdInt
	 * @param startDate
	 * @param endDate
	 */
	public void getAnalysisWithoutSales(TranDetailAnalysis tda, Long agentId, int cityIdInt, String startDate,
			String endDate) {
		// 查询消费激活数据
		TranDetailAnalysis activeWithoutSales = tranDetailReadMapper.getTranDetailAnalysisActiveWithoutSales(agentId,
				cityIdInt, startDate, endDate);
		if (activeWithoutSales == null || activeWithoutSales.getPurchaseActiveAmountWithoutSales() == null) {
			tda.setPurchaseActiveAmountWithoutSales(0.0);
		} else {
			tda.setPurchaseActiveAmountWithoutSales(activeWithoutSales.getPurchaseActiveAmountWithoutSales());
		}
		// 查询充值数据
		TranDetailAnalysis rechargeWithoutSales = tranDetailReadMapper.getTranDetailAnalysisRechargeWithoutSales(
				agentId, cityIdInt, startDate, endDate);
		if (rechargeWithoutSales == null || rechargeWithoutSales.getRechargeAmountWithoutSales() == null
				|| rechargeWithoutSales.getRechargeNumWithoutSales() == null) {
			tda.setRechargeAmountWithoutSales(0.0);
			tda.setRechargeNumWithoutSales(0);
		} else {
			tda.setRechargeAmountWithoutSales(rechargeWithoutSales.getRechargeAmountWithoutSales());
			tda.setRechargeNumWithoutSales(rechargeWithoutSales.getRechargeNumWithoutSales());
		}
		// 查询消费数据
		TranDetailAnalysis purchaseWithoutSales = tranDetailReadMapper.getTranDetailAnalysisPurchaseWithoutSales(
				agentId, cityIdInt, startDate, endDate);
		if (purchaseWithoutSales == null || purchaseWithoutSales.getPurchaseAmountWithoutSales() == null) {
			tda.setPurchaseAmountWithoutSales(0.0);
		} else {
			tda.setPurchaseAmountWithoutSales(purchaseWithoutSales.getPurchaseAmountWithoutSales());
		}
	}

	/**
	 * 查询全网经纪人的统计信息
	 * 
	 * @param tda
	 * @param agentId
	 * @param cityIdInt
	 * @param startDate
	 * @param endDate
	 */
	public void getAnalysis(TranDetailAnalysis tda, Long agentId, int cityIdInt, String startDate, String endDate) {
		agentId = -1l;
		// 查询消费激活数据(有销售人员跟进)
		TranDetailAnalysis activeWithSales = tranDetailReadMapper.getTranDetailAnalysisActiveWithSales(agentId,
				cityIdInt, startDate, endDate);
		if (activeWithSales == null || activeWithSales.getPurchaseActiveAmountWithSales() == null) {
			tda.setPurchaseActiveAmountWithSales(0.0);
		} else {
			tda.setPurchaseActiveAmountWithSales(activeWithSales.getPurchaseActiveAmountWithSales());
		}
		// 查询充值数据(有销售人员跟进)
		TranDetailAnalysis rechargeWithSales = tranDetailReadMapper.getTranDetailAnalysisRechargeWithSales(agentId,
				cityIdInt, startDate, endDate);
		if (rechargeWithSales == null || rechargeWithSales.getRechargeAmountWithSales() == null
				|| rechargeWithSales.getRechargeNumWithSales() == null) {
			tda.setRechargeAmountWithSales(0.0);
			tda.setRechargeNumWithSales(0);
		} else {
			tda.setRechargeAmountWithSales(rechargeWithSales.getRechargeAmountWithSales());
			tda.setRechargeNumWithSales(rechargeWithSales.getRechargeNumWithSales());
		}
		// 查询消费数据(有销售人员跟进)
		TranDetailAnalysis purchaseWithSales = tranDetailReadMapper.getTranDetailAnalysisPurchaseWithSales(agentId,
				cityIdInt, startDate, endDate);
		if (purchaseWithSales == null || purchaseWithSales.getPurchaseAmountWithSales() == null) {
			tda.setPurchaseAmountWithSales(0.0);
		} else {
			tda.setPurchaseAmountWithSales(purchaseWithSales.getPurchaseAmountWithSales());
		}
		// 查询消费激活数据(无销售人员跟进)
		TranDetailAnalysis activeWithoutSales = tranDetailReadMapper.getTranDetailAnalysisActiveWithoutSales(agentId,
				cityIdInt, startDate, endDate);
		if (activeWithoutSales == null || activeWithoutSales.getPurchaseActiveAmountWithoutSales() == null) {
			tda.setPurchaseActiveAmountWithoutSales(0.0);
		} else {
			tda.setPurchaseActiveAmountWithoutSales(activeWithoutSales.getPurchaseActiveAmountWithoutSales());
		}
		// 查询充值数据(无销售人员跟进)
		TranDetailAnalysis rechargeWithoutSales = tranDetailReadMapper.getTranDetailAnalysisRechargeWithoutSales(
				agentId, cityIdInt, startDate, endDate);
		if (rechargeWithoutSales == null || rechargeWithoutSales.getRechargeAmountWithoutSales() == null
				|| rechargeWithoutSales.getRechargeNumWithoutSales() == null) {
			tda.setRechargeAmountWithoutSales(0.0);
			tda.setRechargeNumWithoutSales(0);
		} else {
			tda.setRechargeAmountWithoutSales(rechargeWithoutSales.getRechargeAmountWithoutSales());
			tda.setRechargeNumWithoutSales(rechargeWithoutSales.getRechargeNumWithoutSales());
		}
		// 查询消费数据(无销售人员跟进)
		TranDetailAnalysis purchaseWithoutSales = tranDetailReadMapper.getTranDetailAnalysisPurchaseWithoutSales(
				agentId, cityIdInt, startDate, endDate);
		if (purchaseWithoutSales == null || purchaseWithoutSales.getPurchaseAmountWithoutSales() == null) {
			tda.setPurchaseAmountWithoutSales(0.0);
		} else {
			tda.setPurchaseAmountWithoutSales(purchaseWithoutSales.getPurchaseAmountWithoutSales());
		}
	}
	public int updateBankMsg(AgentRecharge ar) {
		return agentRechargeWriteMapper.updateBankMsg(ar);
		
	}
	
}