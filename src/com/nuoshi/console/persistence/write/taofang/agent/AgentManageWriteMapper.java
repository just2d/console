package com.nuoshi.console.persistence.write.taofang.agent;

import org.apache.ibatis.annotations.Param;

import com.nuoshi.console.domain.agent.AgentManage;

/**
 * @author wanghongmeng
 * 
 */
public interface AgentManageWriteMapper {
	/**
	 * @param id
	 *            经纪人id
	 */
	public void deleteAgentRent(@Param("id")int id);

	/**
	 * @param id
	 *            经纪人id
	 */
	public void deleteAgentResale(@Param("id")int id);

	/**
	 * @param id
	 *            经纪人id
	 */
	public void deleteUser(@Param("id")int id);

	/**
	 * @param id
	 *            经纪人id
	 */
	//public void deleteRateCount(int id);

	/**
	 * @param id
	 *            经纪人id
	 */
	//public void deleteRate(int id);

	/**
	 * @param id
	 *            经纪人id
	 */
	public void deleteUserStatus(@Param("id")int id);

	/**
	 * @param agent
	 *            经纪人对象
	 */
	public void updateAgent(AgentManage agent);
	
	public void updateAgentMaster(AgentManage agent);

	/**
	 * @param id
	 *            经纪人id
	 */
	public void deleteAgentMaster(@Param("id")int id);
	
	/**
	 * @param id
	 *            经纪人id
	 */
	public void resetAgentMaster(@Param("id")int id);
	
	//public void deleteAgentStatus(int id);
	
	public int disbound400Phone(@Param("agentId")int agentId);

	

}
