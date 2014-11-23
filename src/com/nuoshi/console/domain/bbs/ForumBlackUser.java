package com.nuoshi.console.domain.bbs;

import java.io.Serializable;
import java.util.Date;

public class ForumBlackUser implements Serializable {
	private static final long serialVersionUID = -1288287952061507624L;

	private int id;
	private String userName;
	private int userId;
	private int role;
	private int status;
	private Date cts;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getRole() {
		return role;
	}

	public void setRole(int role) {
		this.role = role;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Date getCts() {
		return cts;
	}

	public void setCts(Date cts) {
		this.cts = cts;
	}

}
