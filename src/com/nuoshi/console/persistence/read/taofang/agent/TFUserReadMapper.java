package com.nuoshi.console.persistence.read.taofang.agent;

//import org.apache.ibatis.annotations.Param;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.nuoshi.console.domain.agent.AgentMaster;
import com.nuoshi.console.domain.agent.AgentSearch;
import com.nuoshi.console.domain.user.TFUser;

public interface TFUserReadMapper {
	public AgentMaster selectAgentById(int id);
//	更改经纪人通知crm注释掉2012-1-4
//	public TFUser selectUserMobileById(@Param("userId")Integer userId);

	public AgentMaster selectAgentByNick(@Param("nickName")String nick);

	public List<TFUser> getUserByIdStr(@Param("id")String searchtxt);

	public List<TFUser> getUserByConditionByPage(AgentSearch agentSearch);
}
