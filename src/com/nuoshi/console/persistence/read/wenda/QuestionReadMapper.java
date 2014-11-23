package com.nuoshi.console.persistence.read.wenda;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.nuoshi.console.domain.audit.WenDaAuditTask;
import com.nuoshi.console.domain.wenda.Question;
import com.nuoshi.console.domain.wenda.QuestionCondition;

public interface QuestionReadMapper {

	public List<Integer> getIdForAudit(@Param("num")Integer num,
			@Param("cityId")Integer cityId, @Param("status")Integer status) ;

	public List<WenDaAuditTask> getQuestionInfo(@Param("idList")List<Integer> idList , @Param("type")Integer type);

	public int getQuestionCountByStatus(@Param("statusList")List<Integer> statusList, @Param("solvingStatusList")List<Integer> solvingStatusList);

	/**
	 * 获取提问列表
	 * @param condition
	 * @return
	 * @author wangjh
	 * Oct 24, 2012 1:34:45 PM
	 */
	public List<Question> getQuestionListByPage(@Param("condition")QuestionCondition condition);

}
