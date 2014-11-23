package com.nuoshi.console.domain.agent;

import java.util.Date;

public class TransferPhone {
	private int id;
	private int agentId;
	private int user400Id;
	private String phone;
	private int status;
	private int verifyDate;
	private Date recommendTime;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getAgentId() {
		return agentId;
	}

	public void setAgentId(int agentId) {
		this.agentId = agentId;
	}

	public int getUser400Id() {
		return user400Id;
	}

	public void setUser400Id(int user400Id) {
		this.user400Id = user400Id;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getVerifyDate() {
		return verifyDate;
	}

	public void setVerifyDate(int verifyDate) {
		this.verifyDate = verifyDate;
	}

	public Date getRecommendTime() {
		return recommendTime;
	}

	public void setRecommendTime(Date recommendTime) {
		this.recommendTime = recommendTime;
	}

}
