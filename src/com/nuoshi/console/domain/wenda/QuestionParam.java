package com.nuoshi.console.domain.wenda;

import java.util.Date;

/**
 * 问题查询参数
 */
public class QuestionParam {
	/**
	 * 问题id
	 */
	private String id;
	/**
	 * 一级分类id
	 */
	private String categoryId;
	/**
	 * 二级分类id
	 */
	private String categoryId1;
	/**
	 * 一级分类中文名
	 */
	private String categoryName;
	/**
	 * 二级分类中文名
	 */
	private String categoryName1;
	/**
	 * 问题作者
	 */
	private String author;
	/**
	 * 问题作者ID
	 */
	private String authorId;
	/**
	 * 标题
	 */
	private String title;
	/**
	 * 发布时间段  开始
	 */
	private Date pubTimeStart;
	/**
	 * 发布时间段 结束
	 */
	private Date pubTimeEnd;
	/**
	 * 回答数量
	 */
	private Integer answers;
	/**
	 * 问题描述
	 */
	private String description;
	/**
	 * 状态:待审核，通过，删除' ,
	 */
	private Integer status;
	/**
	 * '问题解决的状态:待回答，关闭，已解决' ,
	 */
	private Integer solvingStatus;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	public String getCategoryId1() {
		return categoryId1;
	}
	public void setCategoryId1(String categoryId1) {
		this.categoryId1 = categoryId1;
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
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getAuthorId() {
		return authorId;
	}
	public void setAuthorId(String authorId) {
		this.authorId = authorId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Date getPubTimeStart() {
		return pubTimeStart;
	}
	public void setPubTimeStart(Date pubTimeStart) {
		this.pubTimeStart = pubTimeStart;
	}
	public Date getPubTimeEnd() {
		return pubTimeEnd;
	}
	public void setPubTimeEnd(Date pubTimeEnd) {
		this.pubTimeEnd = pubTimeEnd;
	}
	public Integer getAnswers() {
		return answers;
	}
	public void setAnswers(Integer answers) {
		this.answers = answers;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getSolvingStatus() {
		return solvingStatus;
	}
	public void setSolvingStatus(Integer solvingStatus) {
		this.solvingStatus = solvingStatus;
	}

	
}