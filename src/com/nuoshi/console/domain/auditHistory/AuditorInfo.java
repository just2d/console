package com.nuoshi.console.domain.auditHistory;

import java.text.DecimalFormat;

import com.nuoshi.console.domain.user.User;

public class AuditorInfo {
	/**
	 * 用户信息
	 */
	private User user;
	/**
	 * 用户id
	 */
	private Integer userId;
	/**
	 * 房源类型
	 */
	private Integer houseType;
	/**
	 * 审核的基本信息数量
	 */
	private Integer auditBaseInfoCount=0;
	/**
	 * 审核的小区图片数量
	 */
	private Integer auditEstatePhotoCount=0;
	/**
	 * 审核的户型图片数量
	 */
	private Integer auditHouseholdPhotoCount=0;
	/**
	 * 审核的室内图片数量
	 */
	private Integer auditIndoorPhotoCount=0;
	/**
	 *经纪人数量
	 */
	private Integer auditAgentCount=0;
	/**
	 * 基本信息错审次数
	 */
	private Integer badBaseInfoCount=0;
	/**
	 * 小区图错审次数
	 */
	private Integer badEstatePhotoCount=0;
	/**
	 * 户型图错审次数
	 */
	private Integer badHouseholdPhotoCount=0;
	/**
	 * 室内图错审次数
	 */
	private Integer badIndoorPhotoCount=0;
	/**
	 * 总的错审率
	 */
	private String totalBadRate;
	/**
	 * 总的审核数量
	 */
	private Integer totalCount=0;
	/**
	 * 总的审核错审次数
	 */
	private Integer totalBadCount=0;
	
	
	
	
	//保留2位小数
	private DecimalFormat   format   =   new   DecimalFormat( "0.00"); 
	
	
	
	
	public Integer getAuditAgentCount() {
		return auditAgentCount;
	}
	public void setAuditAgentCount(Integer auditAgentCount) {
		this.auditAgentCount = auditAgentCount;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Integer getHouseType() {
		return houseType;
	}
	public void setHouseType(Integer houseType) {
		this.houseType = houseType;
	}
	public Integer getAuditBaseInfoCount() {
		return auditBaseInfoCount;
	}
	public void setAuditBaseInfoCount(Integer auditBaseInfoCount) {
		this.auditBaseInfoCount = auditBaseInfoCount;
	}
	public Integer getAuditEstatePhotoCount() {
		return auditEstatePhotoCount;
	}
	public void setAuditEstatePhotoCount(Integer auditEstatePhotoCount) {
		this.auditEstatePhotoCount = auditEstatePhotoCount;
	}
	public Integer getAuditHouseholdPhotoCount() {
		return auditHouseholdPhotoCount;
	}
	public void setAuditHouseholdPhotoCount(Integer auditHouseholdPhotoCount) {
		this.auditHouseholdPhotoCount = auditHouseholdPhotoCount;
	}
	public Integer getAuditIndoorPhotoCount() {
		return auditIndoorPhotoCount;
	}
	public void setAuditIndoorPhotoCount(Integer auditIndoorPhotoCount) {
		this.auditIndoorPhotoCount = auditIndoorPhotoCount;
	}
	public Integer getBadBaseInfoCount() {
		return badBaseInfoCount;
	}
	public void setBadBaseInfoCount(Integer badBaseInfoCount) {
		this.badBaseInfoCount = badBaseInfoCount;
	}
	public Integer getBadEstatePhotoCount() {
		return badEstatePhotoCount;
	}
	public void setBadEstatePhotoCount(Integer badEstatePhotoCount) {
		this.badEstatePhotoCount = badEstatePhotoCount;
	}
	public Integer getBadHouseholdPhotoCount() {
		return badHouseholdPhotoCount;
	}
	public void setBadHouseholdPhotoCount(Integer badHouseholdPhotoCount) {
		this.badHouseholdPhotoCount = badHouseholdPhotoCount;
	}
	public Integer getBadIndoorPhotoCount() {
		return badIndoorPhotoCount;
	}
	public void setBadIndoorPhotoCount(Integer badIndoorPhotoCount) {
		this.badIndoorPhotoCount = badIndoorPhotoCount;
	}
	public String getTotalBadRate() {
		Double d=0.0;
		Boolean flag=totalBadRate==null;
		if(flag&&this.totalBadCount>=0&&this.totalCount>=0){
			if(this.totalCount!=0){
				d=Double.valueOf(this.totalBadCount)/this.totalCount;
				d=d*100;
				totalBadRate=format.format(d)+" %";
			}else{
				totalBadRate="0.00 %";
			}
		}
		return totalBadRate;
	}
	public void setTotalBadRate(String totalBadRate) {
		this.totalBadRate = totalBadRate;
	}
	public Integer getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}
	public Integer getTotalBadCount() {
		return totalBadCount;
	}
	public void setTotalBadCount(Integer totalBadCount) {
		this.totalBadCount = totalBadCount;
	}
	
	
	
	
}
