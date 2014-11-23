package com.nuoshi.console.domain.agent;

import java.sql.Timestamp;

public class AgentRecharge {
	
	private Long rechargeOrderNo;

	private Long agentId;
	
	private String agentUserName;
	
	private String agentName;
	
	private String agentMobile;
	
	private int agentCityId;
	
	private int rechargeType;
	
	private  Timestamp createTime;
	
	private Timestamp paidTime;
	
	private Double rechargeAmount;
	
	private String currencyType;
	
	private int rechargeState;
	
	private int acptBankNo;
	
	private String payChannel;
	
	private String bankOrderNo;
	
	private String bankFeedbackMsg;
	
	private String bankSerialNo;
	
	private String entryId;
	
	private Timestamp entryTime;
	
	private Integer salerId;
	
	private int queryFailNum;
	
	//以下属性只作为页面展示，不存储在数据库
	
	private String company;
	
	private String cityName;
	
	private String store;

	public Long getRechargeOrderNo() {
		return rechargeOrderNo;
	}

	public void setRechargeOrderNo(Long rechargeOrderNo) {
		this.rechargeOrderNo = rechargeOrderNo;
	}

	public Long getAgentId() {
		return agentId;
	}

	public void setAgentId(Long agentId) {
		this.agentId = agentId;
	}

	public String getAgentUserName() {
		return agentUserName;
	}

	public void setAgentUserName(String agentUserName) {
		this.agentUserName = agentUserName;
	}

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	public String getAgentMobile() {
		return agentMobile;
	}

	public void setAgentMobile(String agentMobile) {
		this.agentMobile = agentMobile;
	}

	public int getAgentCityId() {
		return agentCityId;
	}

	public void setAgentCityId(int agentCityId) {
		this.agentCityId = agentCityId;
	}

	public int getRechargeType() {
		return rechargeType;
	}

	public void setRechargeType(int rechargeType) {
		this.rechargeType = rechargeType;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Timestamp getPaidTime() {
		return paidTime;
	}

	public void setPaidTime(Timestamp paidTime) {
		this.paidTime = paidTime;
	}

	public Double getRechargeAmount() {
		return rechargeAmount;
	}

	public void setRechargeAmount(Double rechargeAmount) {
		this.rechargeAmount = rechargeAmount;
	}

	public String getCurrencyType() {
		return currencyType;
	}

	public void setCurrencyType(String currencyType) {
		this.currencyType = currencyType;
	}

	public int getRechargeState() {
		return rechargeState;
	}

	public void setRechargeState(int rechargeState) {
		this.rechargeState = rechargeState;
	}

	public int getAcptBankNo() {
		return acptBankNo;
	}

	public void setAcptBankNo(int acptBankNo) {
		this.acptBankNo = acptBankNo;
	}

	

	public String getBankOrderNo() {
		return bankOrderNo;
	}

	public void setBankOrderNo(String bankOrderNo) {
		this.bankOrderNo = bankOrderNo;
	}

	public String getBankFeedbackMsg() {
		return bankFeedbackMsg;
	}

	public void setBankFeedbackMsg(String bankFeedbackMsg) {
		this.bankFeedbackMsg = bankFeedbackMsg;
	}

	public String getBankSerialNo() {
		return bankSerialNo;
	}

	public void setBankSerialNo(String bankSerialNo) {
		this.bankSerialNo = bankSerialNo;
	}

	public String getEntryId() {
		return entryId;
	}

	public void setEntryId(String entryId) {
		this.entryId = entryId;
	}

	public Timestamp getEntryTime() {
		return entryTime;
	}

	public void setEntryTime(Timestamp entryTime) {
		this.entryTime = entryTime;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getPayChannel() {
		return payChannel;
	}

	public void setPayChannel(String payChannel) {
		this.payChannel = payChannel;
	}

	public Integer getSalerId() {
		return salerId;
	}

	public void setSalerId(Integer salerId) {
		this.salerId = salerId;
	}

	public int getQueryFailNum() {
		return queryFailNum;
	}

	public void setQueryFailNum(int queryFailNum) {
		this.queryFailNum = queryFailNum;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getStore() {
		return store;
	}

	public void setStore(String store) {
		this.store = store;
	}

	
}
