package com.nuoshi.console.domain.photo;

import java.sql.Timestamp;

/**
 * Author: CHEN Liang <alinous@gmail.com> Date: 2009-8-28 Time: 18:13:45
 */
public class Photo   {

	private int id;
	private int owner;
	private String m;
	private String s;
	private String l;
	private Timestamp cts;
	private byte flags;
	private int sourceid;

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

	public String getM() {
		return m;
	}

	public void setM(String m) {
		this.m = m;
	}

	public Timestamp getCts() {
		return cts;
	}

	public void setCts(Timestamp cts) {
		this.cts = cts;
	}

	public String getS() {
		return s;
	}

	public void setS(String s) {
		this.s = s;
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

	public int getSourceid() {
		return sourceid;
	}

	public void setSourceid(int sourceid) {
		this.sourceid = sourceid;
	}
	
}
