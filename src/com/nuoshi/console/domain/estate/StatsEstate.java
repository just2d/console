package com.nuoshi.console.domain.estate;

import java.io.Serializable;

/**
 * 小区统计
 * 
 * @author ningt
 * 
 */
public class StatsEstate implements Serializable{
	
	private static final long serialVersionUID = -7407055216839208853L;
	private int estateId;
	private int resaleAvgPrice;
	private int rentAvgPrice;

	public int getEstateId() {
		return estateId;
	}

	public void setEstateId(int estateId) {
		this.estateId = estateId;
	}

	public int getResaleAvgPrice() {
		return resaleAvgPrice;
	}

	public void setResaleAvgPrice(int resaleAvgPrice) {
		this.resaleAvgPrice = resaleAvgPrice;
	}

	public int getRentAvgPrice() {
		return rentAvgPrice;
	}

	public void setRentAvgPrice(int rentAvgPrice) {
		this.rentAvgPrice = rentAvgPrice;
	}

}
