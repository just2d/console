package com.nuoshi.console.persistence.read.taofang.agent;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.nuoshi.console.domain.agent.AgentRecharge;
import com.nuoshi.console.domain.agent.TranDetail;
import com.nuoshi.console.domain.agent.TranDetailAnalysis;

public interface TranDetailReadMapper {

	List<AgentRecharge> getSuccessTranDetailByPage(@Param("rechargeNo")int rechargeNoInt,@Param("userName")String userName, @Param("rechargeType")int rechargeTypeInt, @Param("startDate")String startDate,@Param("endDate")String endDate, @Param("rechargeState")int rechargeStateInt);

	List<AgentRecharge> getSuccessTranDetail(@Param("rechargeNo")int rechargeNoInt,@Param("userName")String userName, @Param("rechargeType")int rechargeTypeInt, @Param("startDate")String startDate,@Param("endDate")String endDate, @Param("rechargeState")int rechargeStateInt);

	List<AgentRecharge> getNotSuccessTranDetailByPage(@Param("rechargeNo")int rechargeNoInt,@Param("userName")String userName, @Param("rechargeType")int rechargeTypeInt, @Param("startDate")String startDate,@Param("endDate")String endDate, @Param("rechargeState")int rechargeStateInt);

	List<AgentRecharge> getNotSuccessTranDetail(@Param("rechargeNo")int rechargeNoInt,@Param("userName")String userName, @Param("rechargeType")int rechargeTypeInt, @Param("startDate")String startDate,@Param("endDate")String endDate, @Param("rechargeState")int rechargeStateInt);

	List<TranDetail> getTranDetailByPage(@Param("cityId")int cityIdInt, @Param("userName")String userName,@Param("type")int typeInt,@Param("startDate") String startDate, @Param("endDate")String endDate);

	List<TranDetail> getTranDetailZhongji(@Param("cityId")int cityIdInt, @Param("userName")String userName,@Param("type")int typeInt,@Param("startDate") String startDate, @Param("endDate")String endDate);
	
	TranDetailAnalysis getTranDetailAnalysisActiveWithSales(@Param("agentId")long agentId,@Param("cityId")int cityIdInt, @Param("startDate")String startDate, @Param("endDate")String endDate);

	TranDetailAnalysis getTranDetailAnalysisRechargeWithSales(@Param("agentId")long agentId,@Param("cityId")int cityIdInt, @Param("startDate")String startDate, @Param("endDate")String endDate);

	TranDetailAnalysis getTranDetailAnalysisPurchaseWithSales(@Param("agentId")long agentId,@Param("cityId")int cityIdInt, @Param("startDate")String startDate, @Param("endDate")String endDate);

	TranDetailAnalysis getTranDetailAnalysisActiveWithoutSales(@Param("agentId")long agentId,@Param("cityId")int cityIdInt, @Param("startDate")String startDate, @Param("endDate")String endDate);

	TranDetailAnalysis getTranDetailAnalysisRechargeWithoutSales(@Param("agentId")long agentId,@Param("cityId")int cityIdInt, @Param("startDate")String startDate, @Param("endDate")String endDate);

	TranDetailAnalysis getTranDetailAnalysisPurchaseWithoutSales(@Param("agentId")long agentId,@Param("cityId")int cityIdInt, @Param("startDate")String startDate, @Param("endDate")String endDate);

	TranDetailAnalysis getTranDetailAnalysisOnlineBankRecharge(@Param("agentId")long agentId,@Param("cityId")int cityIdInt, @Param("startDate")String startDate, @Param("endDate")String endDate);

	TranDetailAnalysis getTranDetailAnalysisZhifubaoRecharge(@Param("agentId")long agentId,@Param("cityId")int cityIdInt, @Param("startDate")String startDate, @Param("endDate")String endDate);

	TranDetailAnalysis getTranDetailAnalysisShoujikaRecharge(@Param("agentId")long agentId,@Param("cityId")int cityIdInt, @Param("startDate")String startDate, @Param("endDate")String endDate);

	Double getSuccessTranDetailAmount(@Param("rechargeNo")int rechargeNoInt,@Param("userName")String userName, @Param("rechargeType")int rechargeTypeInt, @Param("startDate")String startDate,@Param("endDate")String endDate, @Param("rechargeState")int rechargeStateInt);

	Double getNotSuccessTranDetailAmount(@Param("rechargeNo")int rechargeNoInt,@Param("userName")String userName, @Param("rechargeType")int rechargeTypeInt, @Param("startDate")String startDate,@Param("endDate")String endDate, @Param("rechargeState")int rechargeStateInt);

	

}
