package com.nuoshi.console.domain.audit;

/**
 * 房源审核任务
 * @author wjh
 *
 */
public class AuditTask {
	private Integer id;
	private Integer houseId;
	private Integer houseType;
	private Integer cityId;
	private Integer baseInfo;
	private Integer estatePhoto;
	private Integer householdPhoto;
	private Integer indoorPhoto;
	
	private String pubDate;
	private int layoutCount;
	private int innerCount;
	private int communityCount;
	
	
	public String getPubDate() {
		return pubDate;
	}
	public void setPubDate(String pubDate) {
		this.pubDate = pubDate;
	}
	public int getLayoutCount() {
		return layoutCount;
	}
	public void setLayoutCount(int layoutCount) {
		this.layoutCount = layoutCount;
	}
	public int getInnerCount() {
		return innerCount;
	}
	public void setInnerCount(int innerCount) {
		this.innerCount = innerCount;
	}
	public int getCommunityCount() {
		return communityCount;
	}
	public void setCommunityCount(int communityCount) {
		this.communityCount = communityCount;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getHouseId() {
		return houseId;
	}
	public void setHouseId(Integer houseId) {
		this.houseId = houseId;
	}
	public Integer getHouseType() {
		return houseType;
	}
	public void setHouseType(Integer houseType) {
		this.houseType = houseType;
	}
	public Integer getCityId() {
		return cityId;
	}
	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}
	public Integer getBaseInfo() {
		return baseInfo;
	}
	public void setBaseInfo(Integer baseInfo) {
		this.baseInfo = baseInfo;
	}
	public Integer getEstatePhoto() {
		return estatePhoto;
	}
	public void setEstatePhoto(Integer estatePhoto) {
		this.estatePhoto = estatePhoto;
	}
	public Integer getHouseholdPhoto() {
		return householdPhoto;
	}
	public void setHouseholdPhoto(Integer householdPhoto) {
		this.householdPhoto = householdPhoto;
	}
	public Integer getIndoorPhoto() {
		return indoorPhoto;
	}
	public void setIndoorPhoto(Integer indoorPhoto) {
		this.indoorPhoto = indoorPhoto;
	}
	
	
}
