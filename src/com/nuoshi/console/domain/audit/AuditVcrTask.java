package com.nuoshi.console.domain.audit;

import java.io.Serializable;
import java.sql.Timestamp;

import com.nuoshi.console.domain.base.House;
import com.nuoshi.console.service.LocaleService;

public class AuditVcrTask implements Serializable {
	
	private static final long serialVersionUID = 4048055968807432571L;
	public static final int AUDIT_OK = 1;
	public static final int AUDIT_DELETE = 2;
	
	private int id;
	private int houseId;
	private int houseType;
	private int cityId;
	private int auditResult;
	private String reason;
	private int auditorId;
	private String auditorName;
	private Timestamp auditTime;
	private int agentId;
	private int complainCount;
	private Timestamp vcrTime;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public int getCityId() {
		return cityId;
	}
	public void setCityId(int cityId) {
		this.cityId = cityId;
	}
	public int getAuditResult() {
		return auditResult;
	}
	public void setAuditResult(int auditResult) {
		this.auditResult = auditResult;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public int getAuditorId() {
		return auditorId;
	}
	public void setAuditorId(int auditorId) {
		this.auditorId = auditorId;
	}
	public String getAuditorName() {
		return auditorName;
	}
	public void setAuditorName(String auditorName) {
		this.auditorName = auditorName;
	}
	public Timestamp getAuditTime() {
		return auditTime;
	}
	public void setAuditTime(Timestamp auditTime) {
		this.auditTime = auditTime;
	}
	public String getUrl(){
		if(House.RESALE_TYPE == houseType) {
			return "http://" + LocaleService.getCode(this.cityId) + ".taofang.com/ershoufang/" + this.houseId + "-0.html";
		} else {
			return "http://" + LocaleService.getCode(this.cityId) + ".taofang.com/zufang/" + this.houseId + "-0.html";
		}
	}
	public int getAgentId() {
		return agentId;
	}
	public void setAgentId(int agentId) {
		this.agentId = agentId;
	}
	public int getComplainCount() {
		return complainCount;
	}
	public void setComplainCount(int complainCount) {
		this.complainCount = complainCount;
	}
	public Timestamp getVcrTime() {
		return vcrTime;
	}
	public void setVcrTime(Timestamp vcrTime) {
		this.vcrTime = vcrTime;
	}
}
