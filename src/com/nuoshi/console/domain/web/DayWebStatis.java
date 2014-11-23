package com.nuoshi.console.domain.web;

import java.io.Serializable;


public class DayWebStatis implements Serializable {

	/**
	* 城市ID
	*/
	private Integer cityId ;
	/**
	* 录入时间，精确到秒
	*/
	private Integer entryDate ;
	/**
	* 付费经纪人
	*/
	private Integer payAgentNum ;
	private Integer freeAgentNum ;
	/**
	* 认证经纪人数
	*/
	private Integer verifyAgentNum ;
	/**
	* 在线二手房数
	*/
	private Integer onlineResaleNum ;
	/**
	* 在线出租房数
	*/
	private Integer onlineRentNum ;
	/**
	* 发布的二手房源数
	*/
	private Integer pubResaleNum ;
	/**
	* 发布的租房数
	*/
	private Integer pubRentNum ;
	/**
	* 登录的经纪人数
	*/
	private Integer loginUserNum ;
	/**
	* 发布过房源的经纪人
	*/
	private Integer pubHouseAgentNum ;
	private String cityName ;
	private String distName ;
	private static final long serialVersionUID = 1L;

	/**
	* 城市ID
	*/
	public Integer getCityId() {
			return this.cityId;
		}
	/**
	* 城市ID
	*/
	public void setCityId(Integer cityId) {
			this.cityId = cityId;
		}
	/**
	* 录入时间，精确到秒
	*/
	public Integer getEntryDate() {
			return this.entryDate;
		}
	/**
	* 录入时间，精确到秒
	*/
	public void setEntryDate(Integer entryDate) {
			this.entryDate = entryDate;
		}
	/**
	* 付费经纪人
	*/
	public Integer getPayAgentNum() {
			return this.payAgentNum;
		}
	/**
	* 付费经纪人
	*/
	public void setPayAgentNum(Integer payAgentNum) {
			this.payAgentNum = payAgentNum;
		}
	public Integer getFreeAgentNum() {
			return this.freeAgentNum;
		}
	public void setFreeAgentNum(Integer freeAgentNum) {
			this.freeAgentNum = freeAgentNum;
		}
	/**
	* 认证经纪人数
	*/
	public Integer getVerifyAgentNum() {
			return this.verifyAgentNum;
		}
	/**
	* 认证经纪人数
	*/
	public void setVerifyAgentNum(Integer verifyAgentNum) {
			this.verifyAgentNum = verifyAgentNum;
		}
	/**
	* 在线二手房数
	*/
	public Integer getOnlineResaleNum() {
			return this.onlineResaleNum;
		}
	/**
	* 在线二手房数
	*/
	public void setOnlineResaleNum(Integer onlineResaleNum) {
			this.onlineResaleNum = onlineResaleNum;
		}
	/**
	* 在线出租房数
	*/
	public Integer getOnlineRentNum() {
			return this.onlineRentNum;
		}
	/**
	* 在线出租房数
	*/
	public void setOnlineRentNum(Integer onlineRentNum) {
			this.onlineRentNum = onlineRentNum;
		}
	/**
	* 发布的二手房源数
	*/
	public Integer getPubResaleNum() {
			return this.pubResaleNum;
		}
	/**
	* 发布的二手房源数
	*/
	public void setPubResaleNum(Integer pubResaleNum) {
			this.pubResaleNum = pubResaleNum;
		}
	/**
	* 发布的租房数
	*/
	public Integer getPubRentNum() {
			return this.pubRentNum;
		}
	/**
	* 发布的租房数
	*/
	public void setPubRentNum(Integer pubRentNum) {
			this.pubRentNum = pubRentNum;
		}
	/**
	* 登录的经纪人数
	*/
	public Integer getLoginUserNum() {
			return this.loginUserNum;
		}
	/**
	* 登录的经纪人数
	*/
	public void setLoginUserNum(Integer loginUserNum) {
			this.loginUserNum = loginUserNum;
		}
	/**
	* 发布过房源的经纪人
	*/
	public Integer getPubHouseAgentNum() {
			return this.pubHouseAgentNum;
		}
	/**
	* 发布过房源的经纪人
	*/
	public void setPubHouseAgentNum(Integer pubHouseAgentNum) {
			this.pubHouseAgentNum = pubHouseAgentNum;
		}
	public String getCityName() {
			return this.cityName;
		}
	public void setCityName(String cityName) {
			this.cityName = cityName;
		}
	public String getDistName() {
		return distName;
	}
	public void setDistName(String distName) {
		this.distName = distName;
	}
}