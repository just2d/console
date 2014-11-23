package com.nuoshi.console.domain.estate;

import java.io.Serializable;

import com.nuoshi.console.domain.topic.Estate;

public class EstateData extends Estate implements Serializable {

	private static final long serialVersionUID = 8017658878846437071L;

	private int extinfoid;
	private int photocnt;
	private int layoutcnt;
	private String namepy;
	private String alias;
	private int photoid;
	private int groupid;
	private int CateID;
	private int fromhouse;
	private String busline_id;
	private String busstation_id;
	private String subline_id;
	private String substation_id;
	private int backupLayoutCount;// 备选数量.
	private int backupCommCount;// 备选数量.
	private int commCount;// 精选库数量
	private int layoutCount;
	private String rtUrl;

	public String getRtUrl() {
		return rtUrl;
	}

	public void setRtUrl(String rtUrl) {
		this.rtUrl = rtUrl;
	}

	public int getExtinfoid() {
		return extinfoid;
	}

	public void setExtinfoid(int extinfoid) {
		this.extinfoid = extinfoid;
	}

	public int getPhotocnt() {
		return photocnt;
	}

	public void setPhotocnt(int photocnt) {
		this.photocnt = photocnt;
	}

	public int getLayoutcnt() {
		return layoutcnt;
	}

	public void setLayoutcnt(int layoutcnt) {
		this.layoutcnt = layoutcnt;
	}

	public String getNamepy() {
		return namepy;
	}

	public void setNamepy(String namepy) {
		this.namepy = namepy;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public int getPhotoid() {
		return photoid;
	}

	public void setPhotoid(int photoid) {
		this.photoid = photoid;
	}

	public int getGroupid() {
		return groupid;
	}

	public void setGroupid(int groupid) {
		this.groupid = groupid;
	}

	public int getCateID() {
		return CateID;
	}

	public void setCateID(int cateID) {
		CateID = cateID;
	}

	public int getFromhouse() {
		return fromhouse;
	}

	public void setFromhouse(int fromhouse) {
		this.fromhouse = fromhouse;
	}

	public String getBusline_id() {
		return busline_id;
	}

	public void setBusline_id(String buslineId) {
		busline_id = buslineId;
	}

	public String getBusstation_id() {
		return busstation_id;
	}

	public void setBusstation_id(String busstationId) {
		busstation_id = busstationId;
	}

	public String getSubline_id() {
		return subline_id;
	}

	public void setSubline_id(String sublineId) {
		subline_id = sublineId;
	}

	public String getSubstation_id() {
		return substation_id;
	}

	public void setSubstation_id(String substationId) {
		substation_id = substationId;
	}

	public int getBackupLayoutCount() {
		return backupLayoutCount;
	}

	public void setBackupLayoutCount(int backupLayoutCount) {
		this.backupLayoutCount = backupLayoutCount;
	}

	public int getBackupCommCount() {
		return backupCommCount;
	}

	public void setBackupCommCount(int backupCommCount) {
		this.backupCommCount = backupCommCount;
	}

	public int getCommCount() {
		return commCount;
	}

	public void setCommCount(int commCount) {
		this.commCount = commCount;
	}

	public int getLayoutCount() {
		return layoutCount;
	}

	public void setLayoutCount(int layoutCount) {
		this.layoutCount = layoutCount;
	}

}
