package com.nuoshi.console.domain.auditHistory;

import java.util.Date;

/**
 * 审核历史
 *
 */
public class AuditHistory {
	private Integer id;
	/**
	 * 房源id
	 */
	private Integer houseId;
	/**
	 * 房源类型
	 */
	private Integer houseType;
	/**
	 * 房源创建人id
	 */
	private Integer authorId;
	/**
	 * 房源创建人名字
	 */
	private String authorName;
	/**
	 * 处理时间
	 */
	private Date effectTime;
	/**
	 * 审核结果(通过：-1；打回-2；未通过被复审-3)
	 */
	private Integer auditResult;
	
	private Integer cityId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getHouseId() {
		return houseId;
	}

	public void setHouseId(Integer houseId) {
		this.houseId = houseId;
	}

	public Integer getHouseType() {
		return houseType;
	}

	public void setHouseType(Integer houseType) {
		this.houseType = houseType;
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
		this.authorName = authorName == null ? null : authorName.trim();
	}

	public Date getEffectTime() {
		return effectTime;
	}

	public void setEffectTime(Date effectTime) {
		this.effectTime = effectTime;
	}

	public Integer getAuditResult() {
		return auditResult;
	}

	public void setAuditResult(Integer auditResult) {
		this.auditResult = auditResult;
	}

	public Integer getCityId() {
		return cityId;
	}

	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}
	

}