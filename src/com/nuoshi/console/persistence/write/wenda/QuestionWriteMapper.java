package com.nuoshi.console.persistence.write.wenda;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.nuoshi.console.domain.wenda.Category;

public interface QuestionWriteMapper {

	public void sign(@Param("sign")String auditSign, @Param("idList")List<Integer> idList);

	public void updateStatus(@Param("idList")List<Integer> idList, @Param("status")Integer status,@Param("solvingStatus")Integer solvingStatus,  @Param("isClearAuditSign")boolean isClearAuditSign);

	/**
	 * 更改问题类别（一级二级类别id和名称）
	 * @param questionId 问题id
	 * @param category
	 * @author wangjh
	 * Mar 14, 2012 3:21:12 PM
	 */
	void updateQuestionCategory(@Param("questionId")Integer questionId, @Param("category")Category category,@Param("questionUrl")String questionUrl);
	
	public void updateQuestionByCategory(@Param("category")Category category);
	/**
	 * 重新统计问题的回答数量
	 * @param questionId
	 * @param statusList
	 * @author wangjh
	 * Mar 14, 2012 4:21:53 PM
	 */
	public void recountAnswerForQuestion(@Param("questionId")Integer questionId,
			@Param("statusList")List<Integer> statusList);

	public void changeQuestionBlack(@Param("userId")int userId,@Param("type")int type);


}
