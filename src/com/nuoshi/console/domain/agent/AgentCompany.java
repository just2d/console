package com.nuoshi.console.domain.agent;

import java.io.Serializable;
import java.util.Date;


/**
 * 
 * 2011-12-15
 *
 */
public class AgentCompany  implements Serializable{

	private static final long serialVersionUID = 5521883670094643644L;
	private Integer id;
	private String name;
	private String userName;
	private String namePinyin;
	private String companyType;
	private Integer cityId;
	private String cityName;
	private Integer storeCnt;
	private Integer sourceId;
	private String namepy;
	  /**
     * 更新人
     */
	private Integer auditId;
	  /**
     * 更新时间
     */
	private Date auditTime;
	
	
	
    
	public Integer getAuditId() {
		return auditId;
	}
	public void setAuditId(Integer auditId) {
		this.auditId = auditId;
	}
	public Date getAuditTime() {
		return auditTime;
	}
	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public Integer getStoreCnt() {
		return storeCnt;
	}
	public void setStoreCnt(Integer storeCnt) {
		this.storeCnt = storeCnt;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNamePinyin() {
		return namePinyin;
	}
	public void setNamePinyin(String namePinyin) {
		this.namePinyin = namePinyin;
	}
	public String getCompanyType() {
		return companyType;
	}
	public void setCompanyType(String companyType) {
		this.companyType = companyType;
	}
	public Integer getCityId() {
		return cityId;
	}
	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}
	public Integer getSourceId() {
		return sourceId;
	}
	public void setSourceId(Integer sourceId) {
		this.sourceId = sourceId;
	}
	public String getNamepy() {
		return namepy;
	}
	public void setNamepy(String namepy) {
		this.namepy = namepy;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}

	


   
}
