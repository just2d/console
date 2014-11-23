package com.nuoshi.console.domain.stat;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.nuoshi.console.domain.agent.HousePhoto;
import com.nuoshi.console.domain.rent.RentInfo;
import com.nuoshi.console.domain.resale.ResaleInfo;

public class HouseQuality {
	private String houseId;
	private int houseType;
	private String houseTitle;
	private String cityId;
	private String cityName;
	private int auditStatus;
	private int reAuditStatus;
	private String auditUserId;
	private String auditUserName;
	private String auditDate;
	private String agentId;
	private String agentName;
	private Timestamp entryTime;
	private Object houseObj;
	
	private List<HousePhoto> layoutImgUrls;// 普通户型图
	private List<HousePhoto> estateImgUrls;// 普通小区图
	private List<HousePhoto> innerImgUrls;// 普通室内图
	
	//
	private String searchStartDate;
	private String searchEndDate;
	private String sqlDateStart;
	private String sqlDateEnd;
	private ResaleInfo resale;
	private RentInfo rent;
	private int searchType;
	
	private int limitNum = 15;
	
	
	
	public String getSearchStartDate() {
		return searchStartDate;
	}
	public void setSearchStartDate(String searchStartDate) {
		this.searchStartDate = searchStartDate;
	}
	public String getSearchEndDate() {
		return searchEndDate;
	}
	public void setSearchEndDate(String searchEndDate) {
		this.searchEndDate = searchEndDate;
	}
	public Object getHouseObj() {
		if(houseType == 1){
			houseObj = rent;
		}else{
			houseObj = resale;
		}
		return houseObj;
	}
	public void setHouseObj(Object houseObj) {
		this.houseObj = houseObj;
	}
	public ResaleInfo getResale() {
		return resale;
	}
	public void setResale(ResaleInfo resale) {
		this.resale = resale;
	}
	public RentInfo getRent() {
		return rent;
	}
	public void setRent(RentInfo rent) {
		this.rent = rent;
	}
	public int getLimitNum() {
		return limitNum;
	}
	public void setLimitNum(int limitNum) {
		this.limitNum = limitNum;
	}
	public int getSearchType() {
		return searchType;
	}
	public void setSearchType(int searchType) {
		this.searchType = searchType;
	}
	public List<HousePhoto> getLayoutImgUrls() {
		return layoutImgUrls;
	}
	public void setLayoutImgUrls(List<HousePhoto> layoutImgUrls) {
		this.layoutImgUrls = layoutImgUrls;
	}
	public List<HousePhoto> getEstateImgUrls() {
		return estateImgUrls;
	}
	public void setEstateImgUrls(List<HousePhoto> estateImgUrls) {
		this.estateImgUrls = estateImgUrls;
	}
	public List<HousePhoto> getInnerImgUrls() {
		return innerImgUrls;
	}
	public void setInnerImgUrls(List<HousePhoto> innerImgUrls) {
		this.innerImgUrls = innerImgUrls;
	}
	public Timestamp getEntryTime() {
		return entryTime;
	}
	public void setEntryTime(Timestamp entryTime) {
		this.entryTime = entryTime;
	}
	public int getHouseType() {
		return houseType;
	}
	public void setHouseType(int houseType) {
		this.houseType = houseType;
	}
	public int getReAuditStatus() {
		return reAuditStatus;
	}
	public void setReAuditStatus(int reAuditStatus) {
		this.reAuditStatus = reAuditStatus;
	}
	public String getAgentId() {
		return agentId;
	}
	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}
	public String getAgentName() {
		return agentName;
	}
	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getHouseId() {
		return houseId;
	}
	public void setHouseId(String houseId) {
		this.houseId = houseId;
	}
	public String getHouseTitle() {
		return houseTitle;
	}
	public void setHouseTitle(String houseTitle) {
		this.houseTitle = houseTitle;
	}
	public String getCityId() {
		return cityId;
	}
	public void setCityId(String cityId) {
		this.cityId = cityId;
	}
	public int getAuditStatus() {
		return auditStatus;
	}
	public void setAuditStatus(int auditStatus) {
		this.auditStatus = auditStatus;
	}
	public String getAuditUserId() {
		return auditUserId;
	}
	public void setAuditUserId(String auditUserId) {
		this.auditUserId = auditUserId;
	}
	public String getAuditUserName() {
		return auditUserName;
	}
	public void setAuditUserName(String auditUserName) {
		this.auditUserName = auditUserName;
	}
	public String getAuditDate() {
		return auditDate;
	}
	public void setAuditDate(String auditDate) {
		this.auditDate = auditDate;
	}
	public String getSqlDateStart() {
		if(StringUtils.isNotBlank(auditDate)){
			sqlDateStart = auditDate +" 00:00:00";
		}
		return sqlDateStart;
	}
	public void setSqlDateStart(String sqlDateStart) {
		this.sqlDateStart = sqlDateStart;
	}
	public String getSqlDateEnd() {
		if(StringUtils.isNotBlank(auditDate)){
			sqlDateEnd = auditDate +" 23:59:59";
		}
		return sqlDateEnd;
	}
	public void setSqlDateEnd(String sqlDateEnd) {
		this.sqlDateEnd = sqlDateEnd;
	}
	private String getToday(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String date = sdf.format(new Date());
		return date;
	}
	
	
	

}
