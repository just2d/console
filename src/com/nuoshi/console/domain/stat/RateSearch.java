package com.nuoshi.console.domain.stat;

public class RateSearch {
	/**
	 * 查询内容的时间区间
	 */
	private String beginTime;
	private String endTime;
	/**
	 * 城市id
	 */
	private Integer cityId;
	/**
	 * 统计类型
	 */
	private String rateType;
	
	private String tableName;
	
	
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public Integer getCityId() {
		return cityId;
	}
	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}
	public String getRateType() {
		return rateType;
	}
	public void setRateType(String rateType) {
		this.rateType = rateType;
	}
	

	
	
}
