package com.nuoshi.console.domain.base;

public class RejectPhoto {
	private int id;
	private int houseId;
	private int photoId;
	private int type;
	private String reason;
	private String photoBrowse;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getHouseId() {
		return houseId;
	}
	public void setHouseId(int houseId) {
		this.houseId = houseId;
	}
	public int getPhotoId() {
		return photoId;
	}
	public void setPhotoId(int photoId) {
		this.photoId = photoId;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getPhotoBrowse() {
		return photoBrowse;
	}
	public void setPhotoBrowse(String photoBrowse) {
		this.photoBrowse = photoBrowse;
	}
	
	
}
