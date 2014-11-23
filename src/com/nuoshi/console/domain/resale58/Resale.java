package com.nuoshi.console.domain.resale58;

import java.io.Serializable;

import com.nuoshi.console.domain.base.House;

public class Resale extends House implements Serializable {

	private static final long serialVersionUID = 4835643545811052433L;

	public byte getPright() {
		return pright;
	}

	public void setPright(byte pright) {
		this.pright = pright;
	}

	public byte getAppointment() {
		return appointment;
	}

	public void setAppointment(byte appointment) {
		this.appointment = appointment;
	}

	public byte getRented() {
		return rented;
	}

	public void setRented(byte rented) {
		this.rented = rented;
	}

	// 产权类型
	private byte pright;

	// 是否需要预约
	private byte appointment;

	// 租约
	private byte rented;

}
