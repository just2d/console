package com.nuoshi.console.domain.pckage;

import java.sql.Timestamp;

/**
 * 经纪人的套餐购买记录
 * @author Administrator
 *
 */
public class AgentPurchase {
	public static final int PACKAGE_ACTIVE_STATUS_NO_USE = 0;//未使用
	public static final int PACKAGE_ACTIVE_STATUS_USE = 1; //使用中
	public static final int PACKAGE_ACTIVE_STATUS_EXPIRE = 4; //过期
	public static final int CAN_USE = 0;
	
	private Integer id;
	private Integer agentId;
	private Integer port;
	private Integer packageId;
	private Timestamp purchaseDate;
	private Double purchasePrice;
	private Timestamp activeDate;
	private Timestamp expiredDate;
	private Integer activeStatus;
	private Integer houseAmount;
	private Integer labelAmount;
	private Integer refreshHouseTimes;
	private Integer consumeHouseAmount;
	private Integer consumeLabelAmount;
    private int storeId;//门店ID
	private boolean flag;//标志位，判断门店Id是否更改
	private Integer usageStatus;//使用状态
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getAgentId() {
		return agentId;
	}
	public void setAgentId(Integer agentId) {
		this.agentId = agentId;
	}
	public Integer getPort() {
		return port;
	}
	public void setPort(Integer port) {
		this.port = port;
	}
	public Integer getPackageId() {
		return packageId;
	}
	public void setPackageId(Integer packageId) {
		this.packageId = packageId;
	}
	public Timestamp getPurchaseDate() {
		return purchaseDate;
	}
	public void setPurchaseDate(Timestamp purchaseDate) {
		this.purchaseDate = purchaseDate;
	}
	public Double getPurchasePrice() {
		return purchasePrice;
	}
	public void setPurchasePrice(Double purchasePrice) {
		this.purchasePrice = purchasePrice;
	}
	public Timestamp getActiveDate() {
		return activeDate;
	}
	public void setActiveDate(Timestamp activeDate) {
		this.activeDate = activeDate;
	}
	public Timestamp getExpiredDate() {
		return expiredDate;
	}
	public void setExpiredDate(Timestamp expiredDate) {
		this.expiredDate = expiredDate;
	}
	public Integer getActiveStatus() {
		return activeStatus;
	}
	public void setActiveStatus(Integer activeStatus) {
		this.activeStatus = activeStatus;
	}
	public Integer getHouseAmount() {
		return houseAmount;
	}
	public void setHouseAmount(Integer houseAmount) {
		this.houseAmount = houseAmount;
	}
	public Integer getLabelAmount() {
		return labelAmount;
	}
	public void setLabelAmount(Integer labelAmount) {
		this.labelAmount = labelAmount;
	}
	public Integer getConsumeHouseAmount() {
		return consumeHouseAmount;
	}
	public void setConsumeHouseAmount(Integer consumeHouseAmount) {
		this.consumeHouseAmount = consumeHouseAmount;
	}
	public Integer getConsumeLabelAmount() {
		return consumeLabelAmount;
	}
	public void setConsumeLabelAmount(Integer consumeLabelAmount) {
		this.consumeLabelAmount = consumeLabelAmount;
	}
	public int getStoreId() {
		return storeId;
	}
	public void setStoreId(int storeId) {
		this.storeId = storeId;
	}
	public boolean isFlag() {
		return flag;
	}
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	public Integer getUsageStatus() {
		return usageStatus;
	}
	public void setUsageStatus(Integer usageStatus) {
		this.usageStatus = usageStatus;
	}
	public Integer getRefreshHouseTimes() {
		return refreshHouseTimes;
	}
	public void setRefreshHouseTimes(Integer refreshHouseTimes) {
		this.refreshHouseTimes = refreshHouseTimes;
	}

	
}

