package com.nuoshi.console.domain.agent;

import java.io.Serializable;

/**
 * @author wanghongmeng
 * 
 */
public class AgentSearch implements Serializable {
	private static final long serialVersionUID = -9087576657272104360L;
	private String type;
	private String searchtxt;
	private String verifyResult;
	private String accountType;
	private String startDate;
	private String endDate;
	private String city;
	private String dist;
	private String block;
	private String brand;
	private String address;
	private String banFlag;
	private int payStatus;
	
	


	public int getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(int payStatus) {
		this.payStatus = payStatus;
	}

	/**
	 * 经纪人开启关闭状态：Y 关闭 N开启
	 */
	public String getBanFlag() {
		return banFlag;
	}

	public void setBanFlag(String banFlag) {
		this.banFlag = banFlag;
	}

	/**
	 * @return 搜索类型
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type
	 *            搜索类型
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return 搜索内容文本
	 */
	public String getSearchtxt() {
		return searchtxt;
	}

	/**
	 * @param searchtxt
	 *            搜索内容文本
	 */
	public void setSearchtxt(String searchtxt) {
		this.searchtxt = searchtxt;
	}

	/**
	 * @return 认证状态 0 已认证 1 待认证 2 未认证
	 */
	public String getVerifyResult() {
		return verifyResult;
	}

	/**
	 * @param verifyStatus
	 *            认证状态 0 已认证 1 待认证 2 未认证
	 */
	public void setVerifyResult(String verifyResult) {
		this.verifyResult = verifyResult;
	}

	/**
	 * @return 账户类型 0 付费用户 1 免费用户
	 */
	public String getAccountType() {
		return accountType;
	}

	/**
	 * @param accountType
	 *            账户类型 0 付费用户 1 免费用户
	 */
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	/**
	 * @return 注册起始日期
	 */
	public String getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate
	 *            注册起始日期
	 */
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return 注册终止日期
	 */
	public String getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate
	 *            注册终止日期
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	/**
	 * @return 城市id
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @param city
	 *            城市id
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @return 区域id
	 */
	public String getDist() {
		return dist;
	}

	/**
	 * @param dist
	 *            区域id
	 */
	public void setDist(String dist) {
		this.dist = dist;
	}

	/**
	 * @return 商圈id
	 */
	public String getBlock() {
		return block;
	}

	/**
	 * @param block
	 *            商圈id
	 */
	public void setBlock(String block) {
		this.block = block;
	}

	/**
	 * @return 公司名称
	 */
	public String getBrand() {
		return brand;
	}

	/**
	 * @param brand
	 *            公司名称
	 */
	public void setBrand(String brand) {
		this.brand = brand;
	}

	/**
	 * @return 门店名称
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address
	 *            门店名称
	 */
	public void setAddress(String address) {
		this.address = address;
	}
}
