package com.nuoshi.console.domain.wenda;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class AnswerCondition {
	/**
	 * 回答id
	 */
	private String answerId;
	/**
	 * 标题 模糊查
	 */
	private String questionTitle;
	/**
	 * 问题ID 精确查
	 */
	private String questionId;
	/**
	 * 详情 模糊查
	 */
	private String content;
	/**
	 * 作者id  精确查
	 */
	private String authorId;
	/**
	 * 作者名 精确查
	 */
	private String authorName;
	/**
	 * 回答时间-区间开始  格式(yyyy-MM-dd HH:mm:ss)
	 */
	private String beginPubTime;
	/**
	 * 回答时间-区间结束  格式(yyyy-MM-dd HH:mm:ss)
	 */
	private String endPubTime;
	/**
	 * 状态：待审核0，通过1，删除2 ,
	 */
	private String status;
	/**
	 * 是否黑名单：0正常；1黑
	 */
	private String blackSign="0";
	/**
	 * 最佳答案 
	 */
	private boolean queryBest =false ;
	/**
	 * 是否倒序排列查询结果
	 */
	private boolean orderByDESC = false;
	
	private final String END_TIME_STR="23:59:59";
	
	private final String BRGIN_TIME_STR="00:00:00";

	private DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	private Log log = LogFactory.getLog(AnswerCondition.class);
	
	public String getBlackSign() {
		return blackSign;
	}

	public void setBlackSign(String blackSign) {
		this.blackSign = blackSign;
	}

	public String getAnswerId() {
		return StringUtils.trimToNull(answerId);
	}

	public void setAnswerId(String answerId) {
		this.answerId = answerId;
	}

	public String getQuestionTitle() {
		return StringUtils.trimToNull(questionTitle);
	}

	public void setQuestionTitle(String questionTitle) {
		this.questionTitle = questionTitle;
	}

	public String getQuestionId() {
		return StringUtils.trimToNull(questionId);
	}

	public void setQuestionId(String questionId) {
		this.questionId = questionId;
	}

	public String getContent() {
		return StringUtils.trimToNull(content);
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getAuthorId() {
		return StringUtils.trimToNull(authorId);
	}

	public void setAuthorId(String authorId) {
		this.authorId = authorId;
	}


	public String getAuthorName() {
		return StringUtils.trimToNull(authorName);
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	public Date getBeginPubTime() {
		Date d= null;
		if(StringUtils.isBlank(beginPubTime)){
			return d;
		}
		try {
			d=dateFormat.parse(beginPubTime+" "+this.BRGIN_TIME_STR);
		} catch (ParseException e) {
			log.error("格式化时间出错！");
			System.out.println("格式化时间出错！");
		}
		return d;
	}

	public void setBeginPubTime(String beginPubTime) {
		this.beginPubTime = beginPubTime;
	}

	public Date getEndPubTime() {
		Date d= null;
		if(StringUtils.isBlank(endPubTime)){
			return d;
		}
		try {
			d=dateFormat.parse(endPubTime+" "+this.END_TIME_STR);
		} catch (ParseException e) {
			log.error("格式化时间出错！");
			System.out.println("格式化时间出错！");
		}
		return d;
	}

	public void setEndPubTime(String endPubTime) {
		this.endPubTime = endPubTime;
	}

	public String getStatus() {
		return StringUtils.trimToNull(status);
	}

	public void setStatus(String status) {
		this.status = status;
	}

	
	public boolean isQueryBest() {
		return queryBest;
	}

	public void setQueryBest(boolean queryBest) {
		this.queryBest = queryBest;
	}

	public boolean isOrderByDESC() {
		return orderByDESC;
	}

	public void setOrderByDESC(boolean orderByDESC) {
		this.orderByDESC = orderByDESC;
	}
}
