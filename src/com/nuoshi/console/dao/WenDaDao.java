package com.nuoshi.console.dao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.nuoshi.console.common.Globals;
import com.nuoshi.console.domain.audit.WenDaAuditTask;
import com.nuoshi.console.domain.wenda.Answer;
import com.nuoshi.console.domain.wenda.AnswerCondition;
import com.nuoshi.console.domain.wenda.Category;
import com.nuoshi.console.domain.wenda.Question;
import com.nuoshi.console.domain.wenda.QuestionCondition;
import com.nuoshi.console.persistence.read.wenda.AnswerReadMapper;
import com.nuoshi.console.persistence.read.wenda.QuestionReadMapper;
import com.nuoshi.console.persistence.write.wenda.AnswerWriteMapper;
import com.nuoshi.console.persistence.write.wenda.QuestionWriteMapper;

@Repository
public class WenDaDao {
	@Resource
	private QuestionReadMapper questionReadMapper;
	@Resource
	private AnswerReadMapper answerReadMapper;
	@Resource
	private QuestionWriteMapper questionWriteMapper;
	@Resource
	private AnswerWriteMapper answerWriteMapper;

	public List<Integer> getIdForAudit(Integer num, Integer cityId,
			Integer status, int type) {
		List<Integer> list = null;
		if (Globals.WEN_DA_TYPE_QUESTION == type) {
			list = questionReadMapper.getIdForAudit(num, cityId, status);
		}
		if (Globals.WEN_DA_TYPE_ANSWER == type) {
			list = answerReadMapper.getIdForAudit(num, cityId, status);
		}
		return list;
	}

	public void sign(String auditSign, List<Integer> idList, int type) {
		if (Globals.WEN_DA_TYPE_QUESTION == type) {
			questionWriteMapper.sign(auditSign, idList);
		}
		if (Globals.WEN_DA_TYPE_ANSWER == type) {
			answerWriteMapper.sign(auditSign, idList);
		}
	}

	public List<WenDaAuditTask> getWenDaInfo(List<Integer> idList, Integer type) {
		List<WenDaAuditTask> list=null;
		if (Globals.WEN_DA_TYPE_QUESTION == type) {
			list = questionReadMapper.getQuestionInfo(idList, type);
		}
		if (Globals.WEN_DA_TYPE_ANSWER == type) {
			list = answerReadMapper.getAnswerInfo(idList, type);
		}
		return list;
	}

	public void updateStatus(List<Integer> idList, Integer status,Integer solvingStatus,
			Integer type, boolean isClearAuditSign) {
		if (Globals.WEN_DA_TYPE_QUESTION == type) {
			questionWriteMapper.updateStatus(idList, status,solvingStatus,
					isClearAuditSign);
		}
		if (Globals.WEN_DA_TYPE_ANSWER == type) {
			answerWriteMapper.updateStatus( idList,  status,
					isClearAuditSign);
		}
	}

	/**
	 * 修改问题分类
	 * @param quesionId
	 * @param category
	 * @author wangjh
	 * Mar 14, 2012 3:23:28 PM
	 */
	public void updateQuestionCategory(Integer quesionId, Category category) {
		String questionUrl=category.getQuestionUrlHead()+"_"+quesionId+".html";
		questionWriteMapper.updateQuestionCategory(quesionId, category,questionUrl) ;
	}
	
	/**
	 * 重新统计问题的答案数量
	 * 
	 * @param questionId
	 * @author wangjh Feb 29, 2012 11:02:47 AM
	 */
	public void recountAnswerForQuestion(List<Integer>  questionIdList,
			List<Integer> statusList) {
		for (Integer questionId : questionIdList) {
			questionWriteMapper.recountAnswerForQuestion(questionId, statusList);
		}
	}

	/**
	 * 通过问题的状态获取问题数量
	 * @param statusList
	 * @param solvingStatusList
	 * @return
	 * @author wangjh
	 * May 3, 2012 4:03:18 PM
	 */
	public int getQuestionCountByStatus(List<Integer> statusList, List<Integer> solvingStatusList) {
		return questionReadMapper.getQuestionCountByStatus(statusList, solvingStatusList);
	}

	/**
	 * 通过答案的状态获取答案数量
	 * @param statusList
	 * @return
	 * @author wangjh
	 * May 3, 2012 4:03:18 PM
	 */
	public int getAnswerCountByStatus(List<Integer> statusList) {
		return answerReadMapper.getAnswerCountByStatus(statusList);
	}
	
	/**
	 * 拉黑用户的提问
	 * @param userId
	 * @author wangjh
	 * Aug 23, 2012 3:28:24 PM
	 */
	public void changeQuestionBlack(int userId,int type) {
		questionWriteMapper.changeQuestionBlack(userId,type);
	}

	/**
	 * 拉黑用户的回答
	 * @param userId
	 * @author wangjh
	 * Aug 23, 2012 3:28:24 PM
	 */
	public void changeAnswerBlack(int userId,int type) {
		answerWriteMapper.changeAnswerBlack(userId,type);
	}
	/**
	 * 重新审核回答
	 * @param id
	 * @author wangjh
	 * Oct 31, 2012 10:16:03 AM
	 */
	public void reauditAnswer(List<Integer> ids) {
		this.updateStatus(ids, Globals.ANSWER_STATUS_WAIT_VERIFY, null,Globals.WEN_DA_TYPE_ANSWER, true);
	}

	/**
	 * 重新审核问题
	 * @param id
	 * @author wangjh
	 * Oct 31, 2012 10:22:57 AM
	 */
	public void reauditQuestion(List<Integer> ids) {
		this.updateStatus(ids, Globals.ANSWER_STATUS_WAIT_VERIFY,null,Globals.WEN_DA_TYPE_QUESTION, true);
	}

	/**
	 * 查询问题
	 * @param condition
	 * @return
	 * @author wangjh
	 * Oct 20, 2012 11:27:39 AM
	 */
	public List<Question> getQuestionList(QuestionCondition condition) {
		if(condition==null){
			condition=new QuestionCondition();
		}
		return questionReadMapper.getQuestionListByPage(condition);
	}

	/**
	 * 查询答案
	 * @param condition
	 * @return
	 * @author wangjh
	 * Oct 20, 2012 11:27:39 AM
	 */
	public List<Answer> getAnswerList(AnswerCondition condition) {
		if(condition==null){
			condition=new AnswerCondition();
		}
		return answerReadMapper.getAnswerListByPage(condition);
	}




}
