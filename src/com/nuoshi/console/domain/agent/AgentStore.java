package com.nuoshi.console.domain.agent;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * 2011-12-15
 *
 */
public class AgentStore implements Serializable {
	private static final long serialVersionUID = 9189692905799743924L;
	private int id;
	private String address;
    private String addressPinyin;
    private Integer cityId;
    private Integer distId;
    private Integer blockId;
    private Integer companyId;
    private String userName;
    private String name;
    private String namePinyin;
    /**
     * 公司类型：1.大客户，0.普通'
     */
    private String storeType;
    private Integer sourceId;
    /**
     * 更新人id
     */
    private Integer auditId;
    /**
     * 更新时间
     */
	private Date auditTime;
	
	
	private String companyName;
	
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
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getAddressPinyin() {
		return addressPinyin;
	}
	public void setAddressPinyin(String addressPinyin) {
		this.addressPinyin = addressPinyin;
	}
	public Integer getCityId() {
		return cityId;
	}
	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}
	public Integer getDistId() {
		return distId;
	}
	public void setDistId(Integer distId) {
		this.distId = distId;
	}
	public Integer getBlockId() {
		return blockId;
	}
	public void setBlockId(Integer blockId) {
		this.blockId = blockId;
	}
	public Integer getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
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
	public String getStoreType() {
		return storeType;
	}
	public void setStoreType(String storeType) {
		this.storeType = storeType;
	}
	public Integer getSourceId() {
		return sourceId;
	}
	public void setSourceId(Integer sourceId) {
		this.sourceId = sourceId;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}


}
