package com.nuoshi.console.domain.audit;

import java.util.Date;

public class WenDaAuditTask {
	/**
	 * 问题或答案的id
	 */
	private Integer  id;
	/**
	 * 问题的一级类别
	 */
	private Integer categoryId;
	/**
	 * 问题二级类别
	 */
	private Integer categoryId1;
	/**
	 * 问题一级分类中文名
	 */
	private String categoryName;
	/**
	 * 问题二级分类中文名
	 */
	private String categoryName1;
	/**
	 * 作者名字
	 */
	private String author;
	/**
	 * 作者ID
	 */
	private Integer authorId;
	/**
	 * 标题
	 */
	private String title;
	/**
	 * 描述或答案
	 */
	private String description;
	/**
	 * 发布时间
	 */
	private Date pubTime;
	/**
	 * 状态
	 */
	private Integer status;
	/**
	 * 城市id
	 */
	private Integer cityId;
	/**
	 * 标记是问题还是答案
	 */
	private Integer type;
	/**
	 * 审核人
	 */
	private Integer auditorId;
	
	
	public String getCategoryName1() {
		return categoryName1;
	}
	public void setCategoryName1(String categoryName1) {
		this.categoryName1 = categoryName1;
	}
	public Integer getAuditorId() {
		return auditorId;
	}
	public void setAuditorId(Integer auditorId) {
		this.auditorId = auditorId;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
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
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
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
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getPubTime() {
		return pubTime;
	}
	public void setPubTime(Date pubTime) {
		this.pubTime = pubTime;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getCityId() {
		return cityId;
	}
	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}
	
}
