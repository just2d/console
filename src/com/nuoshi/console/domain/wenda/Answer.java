package com.nuoshi.console.domain.wenda;

import java.util.Date;

/**
 * 答案
 */
public class Answer {
	/**
	 * 主键ID
	 */
	private Integer id;
	/**
	 * 问题ID
	 */
	private Integer questionId;
	/**
	 * 标题
	 */
	private String title;
	/**
	 * 答案作者
	 */
	private String author;
	/**
	 * 答案作者ID
	 */
	private Integer authorId;
	/**
	 * 发布时间
	 */
	private Date pubTime;
	/**
	 * 采纳时间
	 */
	private Date adoptTime;
	/**
	 * 评论
	 */
	private String comments;
	/**
	 * 状态
	 */
	private Integer status;
	/**
	 * 回答内容
	 */
	private String content;
	/**
	 * 投票数
	 */
	private Integer poll;
	/**
	 *  问题分类一名称
	 */
	private String categoryName;
	/**
	 *  问题分类二名称
	 */
	private String categoryName1;
	/**
	 *  问题状态
	 */
	private Integer solvingStatus;
	/**
	 *  回答数
	 */
	private Integer answers;
	/**
	 *  问题url
	 */
	private String url;
	/**
	 * 是否加入黑名单
	 */
	private Integer blackSign;
	
	
	public Integer getBlackSign() {
		return blackSign;
	}

	public void setBlackSign(Integer blackSign) {
		this.blackSign = blackSign;
	}
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getSolvingStatus() {
		return solvingStatus;
	}

	public void setSolvingStatus(Integer solvingStatus) {
		this.solvingStatus = solvingStatus;
	}

	public Integer getAnswers() {
		return answers;
	}

	public void setAnswers(Integer answers) {
		this.answers = answers;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getCategoryName1() {
		return categoryName1;
	}

	public void setCategoryName1(String categoryName1) {
		this.categoryName1 = categoryName1;
	}

	public Integer getPoll() {
		return poll;
	}

	public void setPoll(Integer poll) {
		this.poll = poll;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Integer questionId) {
		this.questionId = questionId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title == null ? null : title.trim();
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author == null ? null : author.trim();
	}

	public Integer getAuthorId() {
		return authorId;
	}

	public void setAuthorId(Integer authorId) {
		this.authorId = authorId;
	}

	public String getComments() {
		return comments;
	}

	public Date getPubTime() {
		return pubTime;
	}

	public void setPubTime(Date pubTime) {
		this.pubTime = pubTime;
	}

	public Date getAdoptTime() {
		return adoptTime;
	}

	public void setAdoptTime(Date adoptTime) {
		this.adoptTime = adoptTime;
	}

	public void setComments(String comments) {
		this.comments = comments == null ? null : comments.trim();
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content == null ? null : content.trim();
	}
}