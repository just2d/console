package com.nuoshi.console.domain.base;

import java.sql.Timestamp;

public class HouseEvaluation {
	private int id;
	private int userId;
	private String userName;
	private int houseId;
	private int sourceId;
	private int exist;
	private int priceAcu;
	private int photoAcu;
	private int infoAcu;
	private String commentWords;
	private int valid;
	private int anoymous;
	private Timestamp createTime;
	private int ext;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public int getHouseId() {
		return houseId;
	}
	public void setHouseId(int houseId) {
		this.houseId = houseId;
	}
	public int getSourceId() {
		return sourceId;
	}
	public void setSourceId(int sourceId) {
		this.sourceId = sourceId;
	}
	public int getExist() {
		return exist;
	}
	public void setExist(int exist) {
		this.exist = exist;
	}
	public int getPriceAcu() {
		return priceAcu;
	}
	public void setPriceAcu(int priceAcu) {
		this.priceAcu = priceAcu;
	}
	public int getPhotoAcu() {
		return photoAcu;
	}
	public void setPhotoAcu(int photoAcu) {
		this.photoAcu = photoAcu;
	}
	public int getInfoAcu() {
		return infoAcu;
	}
	public void setInfoAcu(int infoAcu) {
		this.infoAcu = infoAcu;
	}
	public String getCommentWords() {
		return commentWords;
	}
	public void setCommentWords(String commentWords) {
		this.commentWords = commentWords;
	}
	public int getValid() {
		return valid;
	}
	public void setValid(int valid) {
		this.valid = valid;
	}
	public int getAnoymous() {
		return anoymous;
	}
	public void setAnoymous(int anoymous) {
		this.anoymous = anoymous;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	public int getExt() {
		return ext;
	}
	public void setExt(int ext) {
		this.ext = ext;
	}
}
