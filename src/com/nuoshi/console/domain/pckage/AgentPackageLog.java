package com.nuoshi.console.domain.pckage;

import java.sql.Timestamp;

/**
 * 用户套餐消费记录
 * @author Administrator
 *
 */
public class AgentPackageLog {
	public static final int PACKAGE_ACTIVE_STATUS_NO_USE = 0;//未使用
	public static final int PACKAGE_ACTIVE_STATUS_USE = 1; //使用中
	public static final int PACKAGE_ACTIVE_STATUS_EXPIRE = 4; //过期
	
	private int id;
	private int agentId;
	private int packageId;
	private int packageStatus;
	private Timestamp entryDateTime;
	
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
	public int getPackageId() {
		return packageId;
	}
	public void setPackageId(int packageId) {
		this.packageId = packageId;
	}
	
	public int getPackageStatus() {
		return packageStatus;
	}
	public void setPackageStatus(int packageStatus) {
		this.packageStatus = packageStatus;
	}
	public Timestamp getEntryDateTime() {
		return entryDateTime;
	}
	public void setEntryDateTime(Timestamp entryDateTime) {
		this.entryDateTime = entryDateTime;
	}
	
	
}

