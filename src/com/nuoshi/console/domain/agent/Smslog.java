package com.nuoshi.console.domain.agent;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by IntelliJ IDEA. User: pekky Date: 2010-9-30 Time: 16:48:03
 */
public class Smslog implements Serializable {

	private static final long serialVersionUID = -4275255283533700193L;
	private int userid;
	private String mobiles;
	private String content;
	private int smscnt;
	private Timestamp cts;

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public String getMobiles() {
		return mobiles;
	}

	public void setMobiles(String mobiles) {
		this.mobiles = mobiles;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getSmscnt() {
		return smscnt;
	}

	public void setSmscnt(int smscnt) {
		this.smscnt = smscnt;
	}

	public Timestamp getCts() {
		return cts;
	}

	public void setCts(Timestamp cts) {
		this.cts = cts;
	}
}
