package com.nuoshi.console.domain.auditHistory;

import java.util.Date;

public class ReauditHistory {
	private Integer id;

	/**
	 * 主表id
	 */
	private Integer historyId;

	/**
	 * 字表id
	 */
	private Integer subHistoryId;

	/**
	 * 审核人id
	 */
	private Integer auditId;

	/**
	 * 复审人id
	 */
	private Integer reauditId;

	/**
	 * 复审时间
	 */
	private Date reauditTime;

	/**
	 * 复审结果(总结果)
	 */
	private Integer result;

	/**
	 * 新审核记录的一个图片id
	 */
	private Integer photo_id;
	
	
	private int houseId;
	
	private int houseType;
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getHistoryId() {
		return historyId;
	}

	public void setHistoryId(Integer historyId) {
		this.historyId = historyId;
	}

	public Integer getSubHistoryId() {
		return subHistoryId;
	}

	public void setSubHistoryId(Integer subHistoryId) {
		this.subHistoryId = subHistoryId;
	}

	public Integer getAuditId() {
		return auditId;
	}

	public void setAuditId(Integer auditId) {
		this.auditId = auditId;
	}

	public Integer getReauditId() {
		return reauditId;
	}

	public void setReauditId(Integer reauditId) {
		this.reauditId = reauditId;
	}

	public Date getReauditTime() {
		return reauditTime;
	}

	public void setReauditTime(Date reauditTime) {
		this.reauditTime = reauditTime;
	}

	public Integer getResult() {
		return result;
	}

	public void setResult(Integer result) {
		this.result = result;
	}

	public Integer getPhoto_id() {
		return photo_id;
	}

	public void setPhoto_id(Integer photo_id) {
		this.photo_id = photo_id;
	}

	public int getHouseId() {
		return houseId;
	}

	public void setHouseId(int houseId) {
		this.houseId = houseId;
	}

	public int getHouseType() {
		return houseType;
	}

	public void setHouseType(int houseType) {
		this.houseType = houseType;
	}


}
