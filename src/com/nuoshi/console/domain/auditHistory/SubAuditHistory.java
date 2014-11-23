package com.nuoshi.console.domain.auditHistory;

import java.util.Date;

/**
 * 审核历史子任务
 * 
 */
public class SubAuditHistory {
	private Integer id;
	/**
	 * 房源id
	 */
	private Integer houseId;
	/**
	 * 房源创建者id
	 */
	private Integer authorId;
	
	/**
	 * 房源创建者姓名
	 */
	private String authorName;
	
	/**
	 * 图片id
	 */
	private Integer photoId;
	/**
	 * 审核步骤（四种：基本信息，图片审核）
	 */
	private Integer auditStep;
	/**
	 * 审核主表主键id关联
	 */
	private Integer auditId;

	/**
	 * 审核时间
	 */
	private Date auditTime;
	
	private String auditTimeStr;
	/**
	 * 审核人
	 */
	private String dealerName;
	/**
	 * 审核人id
	 */
	private Integer dealerId;
	/**
	 * 其他信息
	 */
	private String extra;
	/**
	 * 房源描述
	 */
	private String houseDescribe;
	/**
	 * 审核结果
	 */
	private Integer auditResult;
	/**
	 * 审核的图片是否在相册
	 */
	private Integer inalbum;
	
	/**
	 * 打回原因
	 */
	private String rejectReason;
	
	/**
	 * 复审结果
	 */
	private Integer reResult;
	
	/**
	 * 图片地址
	 */
	private String photoUrl;
	
	private int picModel;
	
	public String getAuditTimeStr() {
		return auditTimeStr;
	}

	public void setAuditTimeStr(String auditTimeStr) {
		this.auditTimeStr = auditTimeStr;
	}

	public String getHouseDescribe() {
		return houseDescribe;
	}

	public void setHouseDescribe(String houseDescribe) {
		this.houseDescribe = houseDescribe;
	}

	public String getRejectReason() {
		return rejectReason;
	}

	public void setRejectReason(String rejectReason) {
		this.rejectReason = rejectReason;
	}

	public Integer getAuditResult() {
		return auditResult;
	}

	public Integer getInalbum() {
		return inalbum;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getPhotoId() {
		return photoId;
	}

	public void setPhotoId(Integer photoId) {
		this.photoId = photoId;
	}

	public Integer getAuditStep() {
		return auditStep;
	}

	public void setAuditStep(Integer auditStep) {
		this.auditStep = auditStep;
	}

	public void setAuditResult(Integer auditResult) {
		this.auditResult = auditResult;
	}

	public void setInalbum(Integer inalbum) {
		this.inalbum = inalbum;
	}

	public Date getAuditTime() {
		return auditTime;
	}

	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}

	public String getDealerName() {
		return dealerName;
	}

	public void setDealerName(String dealerName) {
		this.dealerName = dealerName == null ? null : dealerName.trim();
	}

	public String getExtra() {
		return extra;
	}

	public void setExtra(String extra) {
		this.extra = extra == null ? null : extra.trim();
	}

	public Integer getAuditId() {
		return auditId;
	}

	public void setAuditId(Integer auditId) {
		this.auditId = auditId;
	}

	public Integer getAuthorId() {
		return authorId;
	}

	public void setAuthorId(Integer authorId) {
		this.authorId = authorId;
	}

	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	public Integer getHouseId() {
		return houseId;
	}

	public void setHouseId(Integer houseId) {
		this.houseId = houseId;
	}

	public Integer getDealerId() {
		return dealerId;
	}

	public void setDealerId(Integer dealerId) {
		this.dealerId = dealerId;
	}

	public Integer getReResult() {
		return reResult;
	}

	public void setReResult(Integer reResult) {
		this.reResult = reResult;
	}

	public String getPhotoUrl() {
		return photoUrl;
	}

	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}

	public int getPicModel() {
		return picModel;
	}

	public void setPicModel(int picModel) {
		this.picModel = picModel;
	}


}