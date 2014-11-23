package com.nuoshi.console.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.nuoshi.console.dao.WenDaDao;
import com.nuoshi.console.domain.wenda.Answer;
import com.nuoshi.console.domain.wenda.AnswerCondition;
import com.nuoshi.console.domain.wenda.Question;
import com.nuoshi.console.domain.wenda.QuestionCondition;

@Service
public class WenDaListService {
	@Resource
	private WenDaDao wenDaDao;
	
	/**
	 * 查询问题列表
	 * @param condition
	 * @return
	 * @author wangjh
	 * Oct 20, 2012 10:03:29 AM
	 */
	public List<Question> getQuestionList(QuestionCondition condition){
		return wenDaDao.getQuestionList(condition);
	}
	
	/**
	 * 查询回答列表
	 * @param condition
	 * @return
	 * @author wangjh
	 * Oct 20, 2012 10:03:57 AM
	 */
	public List<Answer> getAnswerList(AnswerCondition condition){
		return wenDaDao.getAnswerList(condition);
	}
}
