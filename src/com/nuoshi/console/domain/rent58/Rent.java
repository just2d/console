package com.nuoshi.console.domain.rent58;

import java.io.Serializable;

import com.nuoshi.console.domain.base.House;

public class Rent extends House implements Serializable {

	private static final long serialVersionUID = -5369379803580585706L;

	public byte getPaytype() {
		return paytype;
	}

	public void setPaytype(byte paytype) {
		this.paytype = paytype;
	}

	public byte getDeposit() {
		return deposit;
	}

	public void setDeposit(byte deposit) {
		this.deposit = deposit;
	}

	public byte getFlatting() {
		return flatting;
	}

	public void setFlatting(byte flatting) {
		this.flatting = flatting;
	}

	public int getEquipment() {
		return equipment;
	}

	public void setEquipment(int equipment) {
		this.equipment = equipment;
	}

	public int getRentType() {
		return rentType;
	}

	public void setRentType(int rentType) {
		this.rentType = rentType;
	}

	// 支付方式
	private byte paytype;

	// 押金
	private byte deposit;

	// 是否合租 flatting = 1 合租， flatting = 2 整租
	private byte flatting;

	// 装备
	private int equipment;

	private int rentType;
}
