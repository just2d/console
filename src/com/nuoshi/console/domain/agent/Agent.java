package com.nuoshi.console.domain.agent;

import java.io.Serializable;

import com.nuoshi.console.domain.user.TFUser;

/**
 * Created by: liang Date: 2009-11-9 Time: 15:47:45
 */
public class Agent extends TFUser implements Serializable {
	private static final long serialVersionUID = -4718194141726757609L;
	private int brokerid;
	private int cityid;
	private int distid;
	private int blockid;
	private int province;
	private int sercityid;
	private int serdistid;
	private int serblockid;
	private int points;
	private String brand;
	private String address;
	private String team;

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public boolean getIsagent() {
		return true;
	}

	public int getProvince() {
		return province;
	}

	public void setProvince(int province) {
		this.province = province;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTeam() {
		return team;
	}

	public void setTeam(String team) {
		this.team = team;
	}
}
