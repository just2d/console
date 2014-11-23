package com.nuoshi.console.view;

import java.util.List;

public class EstateListView extends Pager {
	private int id;
	private String address;// 小区地址
	private Integer blockId;// 地标id;
	private String blockName;// 地标名
	private Integer cityId;// 城市 id
	private String cityName;// 城市名称
	private String completion;// 建筑日期
	private Integer distId;// 地区id
	private String distName;// 地区名称
	private Integer estateId; // 小区id;
	private String estateName;// 小区名称
	private int layoutCnt;// 户型图数量
	private int photoCnt;// 小区图片数量
	private int rentCount;// 出租房源数量
	private int resaleCount;// 二手房源数量
	private String type;// 物业类型
	private String delStatus;// 删除状态
	private String authStatus;// 审核状态
	private String origStatus;// 删除前小区状态.
	private String isClicked;

	/** 以下为可选 参数 **/
	private int lon;
	private String photoURL;
	private float lat;
	private float resaleAvgRate;
	private int resaleAvgPrice;
	private int sourceId;
	private List<String> text;
	private List<Integer> trafficIdList;

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

	public Integer getBlockId() {
		return blockId;
	}

	public void setBlockId(Integer blockId) {
		this.blockId = blockId;
	}

	public String getBlockName() {
		return blockName;
	}

	public void setBlockName(String blockName) {
		this.blockName = blockName;
	}

	public Integer getCityId() {
		return cityId;
	}

	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getCompletion() {
		return completion;
	}

	public void setCompletion(String completion) {
		this.completion = completion;
	}

	public Integer getDistId() {
		return distId;
	}

	public void setDistId(Integer distId) {
		this.distId = distId;
	}

	public String getDistName() {
		return distName;
	}

	public void setDistName(String distName) {
		this.distName = distName;
	}

	public Integer getEstateId() {
		return estateId;
	}

	public void setEstateId(Integer estateId) {
		this.estateId = estateId;
	}

	public String getEstateName() {
		return estateName;
	}

	public void setEstateName(String estateName) {
		this.estateName = estateName;
	}

	public int getLayoutCnt() {
		return layoutCnt;
	}

	public void setLayoutCnt(int layoutCnt) {
		this.layoutCnt = layoutCnt;
	}

	public int getPhotoCnt() {
		return photoCnt;
	}

	public void setPhotoCnt(int photoCnt) {
		this.photoCnt = photoCnt;
	}

	public int getRentCount() {
		return rentCount;
	}

	public void setRentCount(int rentCount) {
		this.rentCount = rentCount;
	}

	public int getResaleCount() {
		return resaleCount;
	}

	public void setResaleCount(int resaleCount) {
		this.resaleCount = resaleCount;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getLon() {
		return lon;
	}

	public void setLon(int lon) {
		this.lon = lon;
	}

	public String getPhotoURL() {
		return photoURL;
	}

	public void setPhotoURL(String photoURL) {
		this.photoURL = photoURL;
	}

	public float getLat() {
		return lat;
	}

	public void setLat(float lat) {
		this.lat = lat;
	}

	public float getResaleAvgRate() {
		return resaleAvgRate;
	}

	public void setResaleAvgRate(float resaleAvgRate) {
		this.resaleAvgRate = resaleAvgRate;
	}

	public int getSourceId() {
		return sourceId;
	}

	public void setSourceId(int sourceId) {
		this.sourceId = sourceId;
	}

	public List<String> getText() {
		return text;
	}

	public void setText(List<String> text) {
		this.text = text;
	}

	public List<Integer> getTrafficIdList() {
		return trafficIdList;
	}

	public void setTrafficIdList(List<Integer> trafficIdList) {
		this.trafficIdList = trafficIdList;
	}

	public int getResaleAvgPrice() {
		return resaleAvgPrice;
	}

	public void setResaleAvgPrice(int resaleAvgPrice) {
		this.resaleAvgPrice = resaleAvgPrice;
	}

	public String getDelStatus() {
		return delStatus;
	}

	public void setDelStatus(String delStatus) {
		this.delStatus = delStatus;
	}

	public String getAuthStatus() {
		return authStatus;
	}

	public void setAuthStatus(String authStatus) {
		this.authStatus = authStatus;
	}

	public String getOrigStatus() {
		return origStatus;
	}

	public void setOrigStatus(String origStatus) {
		this.origStatus = origStatus;
	}

	public String getIsClicked() {
		return isClicked;
	}

	public void setIsClicked(String isClicked) {
		this.isClicked = isClicked;
	}

}