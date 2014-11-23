package com.nuoshi.console.domain.pckage;

import java.sql.Timestamp;

/**
 * 经纪人房源数据变动日志表
 * @author Administrator
 *
 */
public class AgentActionLog {
	//修改经纪人状态的操作
	public static int ACTION_TYPE_AGENT_VERIFY = 30;
	
	public static int ACTION_TYPE_AGENT_UPIDCARD = 31;
	
	public static int ACTION_TYPE_AGENT_UPHEAD = 32;
	
	public static int ACTION_TYPE_AGENT_UPNAMECARD = 33;
	
	public static int ACTION_TYPE_AGENT_REGISTER = 34;
	//影响可用发布房源数目的操作
	public static int ACTION_TYPE_HOUSE_NUM = 1;
	//影响可用标签数目的操作
	public static int ACTION_TYPE_LABEL_XT = 20; 
	public static int ACTION_TYPE_LABEL_SSKF = 23; 
	public static int ACTION_TYPE_LABEL_VCR = 24; 
	public static int ACTION_TYPE_LABEL_ALL = 22;
	
	private int id;
	//经纪人ID
	private int agentId;
	/**
	 * 操作类型
	 */
	private int actionType;
	//套餐ID
	private int packageId;
	//房源ID
	private int houseId;
	private int houseCategory;
	//操作之前的状态值
	private String oldValue;
	//操作之后的状态值
	private String newValue;
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
	public int getActionType() {
		return actionType;
	}
	public void setActionType(int actionType) {
		this.actionType = actionType;
	}
	public int getPackageId() {
		return packageId;
	}
	public void setPackageId(int packageId) {
		this.packageId = packageId;
	}
	public int getHouseId() {
		return houseId;
	}
	public void setHouseId(int houseId) {
		this.houseId = houseId;
	}
	public int getHouseCategory() {
		return houseCategory;
	}
	public void setHouseCategory(int houseCategory) {
		this.houseCategory = houseCategory;
	}

	public String getOldValue() {
		return oldValue;
	}
	public void setOldValue(String oldValue) {
		this.oldValue = oldValue;
	}
	public String getNewValue() {
		return newValue;
	}
	public void setNewValue(String newValue) {
		this.newValue = newValue;
	}
	public Timestamp getEntryDateTime() {
		return entryDateTime;
	}
	public void setEntryDateTime(Timestamp entryDateTime) {
		this.entryDateTime = entryDateTime;
	}
	
	
}

