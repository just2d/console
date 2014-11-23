package com.nuoshi.console.domain.audit;

import com.nuoshi.console.service.LocaleService;


public class AuditAgentList {
	public static int BLACK = 1;//黑名单
	public static int WHITE = 2;//白名单 
	private int  agentId;
	private String name;
	private String mobile;
	private int cityId;
	private int auditRule; //审核规则：0-(默认) 1-严审（手动设置的黑名单）; 2-免审（手动设置的白名单）;3-免审（白名单－通过违规率设置的）　
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
	public int getCityId() {
		return cityId;
	}
	public void setCityId(int cityId) {
		this.cityId = cityId;
	}
	public int getAuditRule() {
		return auditRule;
	}
	public void setAuditRule(int auditRule) {
		this.auditRule = auditRule;
	}
	 public String getCityName(){
		 return LocaleService.getName(this.cityId);
	 }
	
	
}
