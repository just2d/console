package com.nuoshi.console.persistence.write.taofang.agent;

import org.apache.ibatis.annotations.Param;

import com.nuoshi.console.domain.agent.AgentMaster;

public interface TFUserWriteMapper {

	public int updateFlags(AgentMaster agentMaster);
	/**
	 * 用户问题加一或减一
	 * @param userId
	 * @author wangjh
	 * Mar 14, 2012 5:14:09 PM
	 */
	public void updateQuestionCount(@Param("userId")Integer userId,@Param("isAdd")boolean isAdd);
	/**
	 * 用户答案加一或减一
	 * @param userId
	 * @author wangjh
	 * Mar 14, 2012 5:14:09 PM
	 */
	public void updateAnswerCount(@Param("userId")Integer userId,@Param("isAdd")boolean isAdd);
	public void delete(@Param("userId")int id);
}
