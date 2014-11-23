package com.nuoshi.console.domain.pckage;

import java.sql.Timestamp;
import java.text.DecimalFormat;

/**
 * 经纪人套餐
 * @author Administrator
 *
 */
public class AgentPackage {
	private int id;
	private int cityId;
	private String packageName;
	private String description;
	private int effectiveDays;
	private int houseAmount;
	private int labelAmount;
	private int refreshHouseTimes ;
	private int entryId;
	private Timestamp entryDateTime;
	private float price;
	private int effectiveMonth;
	private int defaultValue;
	private int status;
	private int defaultFree;
	private int orderIndex;
	private int vipPicNum;//VIP专区大图个数
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPackageName() {
		return packageName;
	}
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getEffectiveDays() {
		return effectiveDays;
	}
	public void setEffectiveDays(int effectiveDays) {
		this.effectiveDays = effectiveDays;
	}
	public int getHouseAmount() {
		return houseAmount;
	}
	public void setHouseAmount(int houseAmount) {
		this.houseAmount = houseAmount;
	}
	public int getLabelAmount() {
		return labelAmount;
	}
	public void setLabelAmount(int labelAmount) {
		this.labelAmount = labelAmount;
	}
	public int getEntryId() {
		return entryId;
	}
	public void setEntryId(int entryId) {
		this.entryId = entryId;
	}
	public Timestamp getEntryDateTime() {
		return entryDateTime;
	}
	public void setEntryDateTime(Timestamp entryDateTime) {
		this.entryDateTime = entryDateTime;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public int getEffectiveMonth() {
		return effectiveMonth;
	}
	public void setEffectiveMonth(int effectiveMonth) {
		this.effectiveMonth = effectiveMonth;
	}
	public int getCityId() {
		return cityId;
	}
	public void setCityId(int cityId) {
		this.cityId = cityId;
	}
	public String getAvgPrice() {
		if(effectiveMonth == 0) {
			return "0.00";
		}
		DecimalFormat df = new DecimalFormat();
		String pa = "0.00";
		df.applyPattern(pa);
		return df.format(this.price * 1.0f / houseAmount / effectiveMonth);
	}
	public int getDefaultValue() {
		return defaultValue;
	}
	public void setDefaultValue(int defaultValue) {
		this.defaultValue = defaultValue;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getDefaultFree() {
		return defaultFree;
	}
	public void setDefaultFree(int defaultFree) {
		this.defaultFree = defaultFree;
	}
	public int getOrderIndex() {
		return orderIndex;
	}
	public void setOrderIndex(int orderIndex) {
		this.orderIndex = orderIndex;
	}
	public int getVipPicNum() {
		return vipPicNum;
	}
	public void setVipPicNum(int vipPicNum) {
		this.vipPicNum = vipPicNum;
	}
	public int getRefreshHouseTimes() {
		return refreshHouseTimes;
	}
	public void setRefreshHouseTimes(int refreshHouseTimes) {
		this.refreshHouseTimes = refreshHouseTimes;
	}
	public String getInfo(){
		return "房源：" + this.houseAmount + "套；标签：" + this.labelAmount + "套；VIP大图：" + this.vipPicNum + "套";
	}
}
