package com.nuoshi.console.domain.wenda;

import java.util.Date;

/**
 * 问题
 */
public class Question {
	/**
	 * 问题id
	 */
	private Integer id;
	/**
	 * 一级分类id
	 */
	private Integer categoryId;
	/**
	 * 二级分类id
	 */
	private Integer categoryId1;
	/**
	 * 三级分类id（暂时不用）
	 */
	private Integer categoryId2;
	/**
	 * 四级分类id（暂时不用）
	 */
	private Integer categoryId3;
	/**
	 * 一级分类中文名
	 */
	private String categoryName;
	/**
	 * 二级分类中文名
	 */
	private String categoryName1;
	/**
	 * 悬赏财富值
	 */
	private Integer price;
	/**
	 * 问题作者
	 */
	private String author;
	/**
	 * 问题作者ID
	 */
	private Integer authorId;
	/**
	 * 标题
	 */
	private String title;
	/**
	 * 问题链接
	 */
	private String url;
	/**
	 * 分类url
	 */
	private String cateUrl;
	/**
	 * 城市id
	 */
	private Integer cityId;
	/**
	 * 发布时间
	 */
	private Date pubTime;
	/**
	 * 结束时间
	 */
	private Date endTime;
	/**
	 * 回答数量
	 */
	private Integer answers;
	/**
	 * 问题描述
	 */
	private String description;
	/**
	 * '点击次数',
	 */
	private Integer clickCount;
	/**
	 * '关键词',
	 */
	private String keywords;
	/**
	 * 状态:待审核，通过，删除' ,
	 */
	private Integer status;
	/**
	 * '问题解决的状态:待回答，关闭，已解决' ,
	 */
	private Integer solvingStatus;
	/**
	 * 剩余时间
	 */
	private Long remainingTime = -1L;
	/**
	 *  满意答案
	 */
	private String satisfyAnswer; 
	/**
	 *  一级分类url
	 */
	private String cateurl1;
	/**
	 *  二级分类url
	 */
	private String cateurl2;
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

	public String getCateurl1() {
		return cateurl1;
	}

	public void setCateurl1(String cateurl1) {
		this.cateurl1 = cateurl1;
	}

	public String getCateurl2() {
		return cateurl2;
	}

	public void setCateurl2(String cateurl2) {
		this.cateurl2 = cateurl2;
	}

	public String getSatisfyAnswer() {
		return satisfyAnswer;
	}

	public void setSatisfyAnswer(String satisfyAnswer) {
		this.satisfyAnswer = satisfyAnswer;
	}

	public long getRemainingTime() {
		return remainingTime;
	}

	public void setRemainingTime(Long remainingTime) {
		this.remainingTime = remainingTime;
	}

	public String getCategoryName1() {
		return categoryName1;
	}

	public void setCategoryName1(String categoryName1) {
		this.categoryName1 = categoryName1;
	}

	public Integer getClickCount() {
		return clickCount;
	}

	public void setClickCount(Integer clickCount) {
		this.clickCount = clickCount;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public Integer getSolvingStatus() {
		return solvingStatus;
	}

	public void setSolvingStatus(Integer solvingStatus) {
		this.solvingStatus = solvingStatus;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public Integer getCategoryId1() {
		return categoryId1;
	}

	public void setCategoryId1(Integer categoryId1) {
		this.categoryId1 = categoryId1;
	}

	public Integer getCategoryId2() {
		return categoryId2;
	}

	public void setCategoryId2(Integer categoryId2) {
		this.categoryId2 = categoryId2;
	}

	public Integer getCategoryId3() {
		return categoryId3;
	}

	public void setCategoryId3(Integer categoryId3) {
		this.categoryId3 = categoryId3;
	}

	public Integer getPrice() {
		if(price==null){
			price=0;
		}
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title == null ? null : title.trim();
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url == null ? null : url.trim();
	}

	public Integer getCityId() {
		return cityId;
	}

	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}

	public Date getPubTime() {
		return pubTime;
	}

	public void setPubTime(Date pubTime) {
		this.pubTime = pubTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Integer getAnswers() {
		return answers;
	}

	public void setAnswers(Integer answers) {
		this.answers = answers;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description == null ? null : description.trim();
	}

	public String getCateUrl() {
		return cateUrl;
	}

	public void setCateUrl(String cateUrl) {
		this.cateUrl = cateUrl;
	}

}