package com.nuoshi.console.domain.agent;

import java.sql.Timestamp;

public class AgentDeleteLog {
	private int id;
	private int agentId;
	private String name;
	private String mobile;
	private String info;
	private String entryName;
	private int entryId;
	private Timestamp entryTime;
	
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public String getEntryName() {
		return entryName;
	}
	public void setEntryName(String entryName) {
		this.entryName = entryName;
	}
	public int getEntryId() {
		return entryId;
	}
	public void setEntryId(int entryId) {
		this.entryId = entryId;
	}
	public Timestamp getEntryTime() {
		return entryTime;
	}
	public void setEntryTime(Timestamp entryTime) {
		this.entryTime = entryTime;
	}
}
