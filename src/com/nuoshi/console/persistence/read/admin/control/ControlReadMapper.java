package com.nuoshi.console.persistence.read.admin.control;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.nuoshi.console.domain.control.BlackList;
import com.nuoshi.console.domain.control.SensitiveWord;

/**
 * @author wanghongmeng
 * 
 */
public interface ControlReadMapper {
	/**
	 * @param conditionList
	 *            查询条件
	 * @return 符合查询条件的黑名单集合
	 */
	public List<BlackList> getAllBlackListByPage(
			@Param("conditionList") List<String> conditionList);

	/**
	 * @param mobile
	 *            用户电话
	 * @return 
	 */
	public Integer getBlackCountByLoginName(@Param("loginName")String loginName,@Param("listType")int listType);
	/**
	 * @param userId
	 * @return 
	 */
	public Integer getBlackCountByUserId( @Param("userId")int userId, @Param("listType")int listType);

	/**
	 * @param blackList
	 *            查询条件
	 * @return 符合查询条件的管家此集合
	 */
	public List<SensitiveWord> getAllSensitiveWordByPage(
			@Param("conditionList") List<String> conditionList);
}
