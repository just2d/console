package com.nuoshi.console.domain.agent.history;

import java.util.Date;

public class OperatHistory {
	public Integer id;
	/**
	 * 操作对象的id
	 */
	public Integer operandsId;
	/**
	 * 操作人id
	 */
	public Integer operatorId;
	/**
	 * 操作人名字
	 */
	public String operatorName;
	/**
	 * 操作内容记录
	 */
	public String operationalContext;
	/**
	 * 操作时间
	 */
	public Date dates;
	/**
	 * 操作类型（以后可能会多个历史都使用）
	 * 1 是 开关经济人在线发布房源的记录
	 */
	public Integer operatorType;
	
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getOperandsId() {
		return operandsId;
	}
	public void setOperandsId(Integer operandsId) {
		this.operandsId = operandsId;
	}
	public Integer getOperatorId() {
		return operatorId;
	}
	public void setOperatorId(Integer operatorId) {
		this.operatorId = operatorId;
	}
	public String getOperatorName() {
		return operatorName;
	}
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}
	public String getOperationalContext() {
		return operationalContext;
	}
	public void setOperationalContext(String operationalContext) {
		this.operationalContext = operationalContext;
	}
	public Date getDates() {
		return dates;
	}
	public void setDates(Date dates) {
		this.dates = dates;
	}
	public Integer getOperatorType() {
		return operatorType;
	}
	public void setOperatorType(Integer operatorType) {
		this.operatorType = operatorType;
	}
	
	
}
