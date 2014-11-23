package com.nuoshi.console.domain.wenda;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class QuestionCondition {
	/**
	 * ID 精确查
	 */
	private String id;
	/**
	 * 标题 模糊查
	 */
	private String title;
	/**
	 * 详情 模糊查
	 */
	private String details;
	/**
	 * 作者id  精确查
	 */
	private String authorId;
	/**
	 * 作者名字 精确查
	 */
	private String authorName;
	/**
	 * 发布时间-区间开始  格式(yyyy-MM-dd HH:mm:ss)
	 */
	private String beginPubTime;
	/**
	 * 发布时间-区间结束  格式(yyyy-MM-dd HH:mm:ss)
	 */
	private String endPubTime;
	/**
	 * 审核人员姓名 精确
	 */
	private String auditName;
	/**
	 * 审核人员ID  精确
	 */
	private String auditUserId;
	/**
	 * 类别id 精确
	 */
	private String categoryId;
	/**
	 * 类别名 模糊
	 */
	private String categoryName;
	/**
	 * 状态：待审核0，通过1，删除2 ,
	 */
	private String status;
	/**
	 * 问题解决的状态：新建0，待回答1，已解决2，关闭3 ,
	 */
	private String solvingStatus;
	/**
	 * 到期时间-区间开始  格式(yyyy-MM-dd HH:mm:ss)
	 */
	private String beginMatureTime ;
	/**
	 * 到期时间-区间结束  格式(yyyy-MM-dd HH:mm:ss)
	 */
	private String endMatureTime ;
	/**
	 * 回答数 精确
	 */
	private String answerCount ;
	/**
	 * 是否倒序排列查询结果
	 */
	private boolean orderByDESC = false;
	/**
	 * 是否黑名单：0正常；1黑
	 */
	private String blackSign="0";

	private DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	private final String END_TIME_STR="23:59:59";
	
	private final String BRGIN_TIME_STR="00:00:00";
	
	private Log log = LogFactory.getLog(QuestionCondition.class);
	
	public String getBlackSign() {
		return blackSign;
	}
	public void setBlackSign(String blackSign) {
		this.blackSign = blackSign;
	}
	public String getTitle() {
		return StringUtils.trimToNull(title);
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDetails() {
		return StringUtils.trimToNull(details);
	}
	public void setDetails(String details) {
		this.details = details;
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
	public String getAuditName() {
		return StringUtils.trimToNull(auditName);
	}
	public void setAuditName(String auditName) {
		this.auditName = auditName;
	}
	public String getAuditUserId() {
		return StringUtils.trimToNull(auditUserId);
	}
	public void setAuditUserId(String auditUserId) {
		this.auditUserId = auditUserId;
	}
	public String getCategoryId() {
		return StringUtils.trimToNull(categoryId);
	}
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	public String getStatus() {
		return StringUtils.trimToNull(status);
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getSolvingStatus() {
		return StringUtils.trimToNull(solvingStatus);
	}
	public void setSolvingStatus(String solvingStatus) {
		this.solvingStatus = solvingStatus;
	}
	public Date getBeginMatureTime() {
		Date d= null;
		if(StringUtils.isBlank(beginMatureTime)){
			return d;
		}
		try {
			d=dateFormat.parse(beginMatureTime+" "+this.BRGIN_TIME_STR);
		} catch (ParseException e) {
			log.error("格式化时间出错！");
			System.out.println("格式化时间出错！");
		}
		return d;
	}
	public void setBeginMatureTime(String beginMatureTime) {
		this.beginMatureTime = beginMatureTime;
	}
	public Date getEndMatureTime() {
		Date d= null;
		if(StringUtils.isBlank(endMatureTime)){
			return d;
		}
		try {
			d=dateFormat.parse(endMatureTime+" "+this.END_TIME_STR);
		} catch (ParseException e) {
			log.error("格式化时间出错！");
			System.out.println("格式化时间出错！");
		}
		return d;
	}
	public void setEndMatureTime(String endMatureTime) {
		this.endMatureTime = endMatureTime;
	}
	public String getAnswerCount() {
		return StringUtils.trimToNull(answerCount);
	}
	public void setAnswerCount(String answerCount) {
		this.answerCount = answerCount;
	}
	public String getCategoryName() {
		return StringUtils.trimToNull(categoryName);
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public String getId() {
		return StringUtils.trimToNull(id);
	}
	public void setId(String id) {
		this.id = id;
	}
	public boolean isOrderByDESC() {
		return orderByDESC;
	}
	public void setOrderByDESC(boolean orderByDESC) {
		this.orderByDESC = orderByDESC;
	}
}
