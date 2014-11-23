package com.nuoshi.console.domain.agent;

import java.io.Serializable;
import java.sql.Timestamp;

public class IdentityPhoto implements Serializable {
	private static final long serialVersionUID = 1755993060950191286L;
	private int id;
	private int owner;
	private String l;
	private String m;
	private String s;
	private Timestamp cts;
	private byte flags;

	// 图片类型：0，身份证图片；1，名片认证图片
	private byte type;

	public String getS() {
		return l;
	}

	public String getM() {
		return l;
	}

	public Timestamp getCts() {
		return cts;
	}

	public void setCts(Timestamp cts) {
		this.cts = cts;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getOwner() {
		return owner;
	}

	public void setOwner(int owner) {
		this.owner = owner;
	}

	public String getL() {
		return l;
	}

	public void setL(String l) {
		this.l = l;
	}

	public byte getFlags() {
		return flags;
	}

	public void setFlags(byte flags) {
		this.flags = flags;
	}

	public byte getType() {
		return type;
	}

	public void setType(byte type) {
		this.type = type;
	}

	public void setM(String m) {
		this.m = m;
	}

	public void setS(String s) {
		this.s = s;
	}

}
