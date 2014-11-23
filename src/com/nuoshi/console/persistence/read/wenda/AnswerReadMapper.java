package com.nuoshi.console.persistence.read.wenda;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.nuoshi.console.domain.audit.WenDaAuditTask;
import com.nuoshi.console.domain.wenda.Answer;
import com.nuoshi.console.domain.wenda.AnswerCondition;

public interface AnswerReadMapper {

	public List<Integer> getIdForAudit(@Param("num")Integer num,
			@Param("cityId")Integer cityId, @Param("status")Integer status);
	
	public List<WenDaAuditTask> getAnswerInfo(@Param("idList")List<Integer> idList , @Param("type")Integer type);
	
	public int getAnswerCountByStatus(@Param("statusList")List<Integer> statusList);

	/**
	 * 获取回答列表
	 * @param condition
	 * @return
	 * @author wangjh
	 * Oct 24, 2012 1:34:16 PM
	 */
	public List<Answer> getAnswerListByPage(@Param("condition")AnswerCondition condition);
}
