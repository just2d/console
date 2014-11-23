package com.nuoshi.console.dao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.nuoshi.console.domain.control.BlackList;
import com.nuoshi.console.domain.control.SensitiveWord;
import com.nuoshi.console.persistence.read.admin.control.ControlReadMapper;
import com.nuoshi.console.persistence.read.taofang.agent.AgentManageReadMapper;
import com.nuoshi.console.persistence.write.admin.control.ControlWriteMapper;

/**
 * @author wanghongmeng
 * 
 */
@Repository
public class ControlDao {
	@Resource
	private ControlReadMapper controlReadMapper;
	@Resource
	private ControlWriteMapper controlWriteMapper;
	@Resource
	private AgentManageReadMapper agentManageReadMapper;

	/**
	 * @param conditionList
	 *            查询条件
	 * @return 符合查询条件的黑名单集合
	 */
	public List<BlackList> getAllBlackListByPage(List<String> conditionList) {
		return controlReadMapper.getAllBlackListByPage(conditionList);
	}

	
	/**
	 * 用户登录名判断是否是有效的黑名单
	 * @param loginName
	 * @param listType
	 * @return
	 * @author wangjh
	 * Aug 16, 2012 3:11:07 PM
	 */
	public Integer isUserExistByLoginName(String loginName,int listType){
		return controlReadMapper.getBlackCountByLoginName(loginName,listType);
	}
	/**
	 * 用户id判断是否是有效的黑名单
	 * @param loginName
	 * @param listType
	 * @return
	 * @author wangjh
	 * Aug 16, 2012 3:11:07 PM
	 */
	public Integer isUserExistByUserId(int userId,int listType){
		return controlReadMapper.getBlackCountByUserId(userId, listType);
	}

	/**
	 * @param mobile
	 *            用户手机号
	 * @return 用户角色(经纪人、普通用户、未注册用户)
	 */
	public Integer getUserRole(String mobile) {
		return agentManageReadMapper.getUserRole(mobile);
	}

	/**
	 * @param blackList
	 *            将要添加的黑名单对象
	 */
	public void addBlack(BlackList blackList) {
		controlWriteMapper.addBlack(blackList);
	}

	/**
	 * @param blackList
	 *            将要更新的黑名单对象
	 */
	public void updateBlack(BlackList blackList) {
		controlWriteMapper.updateBlack(blackList);
	}

	/**
	 * @param id
	 *            要删除的黑名单id
	 */
	public void deleteBlack(int id) {
		controlWriteMapper.deleteBlack(id);
	}

	/**
	 * @param conditionList
	 *            查询条件
	 * @return 符合查询条件的关键词集合
	 */
	public List<SensitiveWord> getAllSensitiveWordByPage(
			List<String> conditionList) {
		return controlReadMapper.getAllSensitiveWordByPage(conditionList);
	}

	/**
	 * @param sensitiveWord
	 *            将要添加的关键词对象
	 */
	public void addSensitiveWord(SensitiveWord sensitiveWord) {
		controlWriteMapper.addSensitiveWord(sensitiveWord);
	}

	/**
	 * @param sensitiveWord
	 *            将要更新的关键词对象
	 */
	public void updateSensitiveWord(SensitiveWord sensitiveWord) {
		controlWriteMapper.updateSensitiveWord(sensitiveWord);
	}

	/**
	 * @param id
	 *            要删除的关键词id
	 */
	public void deleteSensitiveWord(int id) {
		controlWriteMapper.deleteSensitiveWord(id);
	}

	/**
	 * @param mobile
	 *            经纪人电话
	 * @return 经纪人的id
	 */
	public Integer getAgentIdByMobile(String mobile) {
		return agentManageReadMapper.getAgentIdByMobile(mobile);
	}
}