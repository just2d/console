package com.nuoshi.console.domain.agent;

import java.io.Serializable;

public class HousePhoto implements Serializable {
	private static final long serialVersionUID = 5559196746725715159L;
	public static final int CATEGORY_DEFAULT = 0; 
	public static final int CATEGORY_LAYOUT = 1; //户型图
	public static final int CATEGORY_INNER = 2; //室内图
	public static final int CATEGORY_COMMUNITY = 3; //室外图
	private int houseid;// 房源id
	private String category;// 图片类型
	private String note;// 图片说明
	private String photoid;// 图片id
	private String usestatus;// 图片使用状态
	private String sURL;// 图片略缩图（小图）
	private String mURL;// 图片普通图（中图）
	private String lURL;// 图片详细图（大图）
	private int inalbum;
	private int sourceid;
	private Integer innertype;//室内图片类型
	private int cover;//  1:手动设置为封面图 0：默认值
	private int photoStatus;// 0:默认；1:符合小区备选(标准640*480）；2:进入小区备选库'
	private int direction;// 朝向（针对户型图），0：默认（无朝向指示）；1：有朝向指 

	public Integer getInnertype() {
		return innertype;
	}

	public void setInnertype(Integer innertype) {
		this.innertype = innertype;
	}

	public int getHouseid() {
		return houseid;
	}

	public void setHouseid(int houseid) {
		this.houseid = houseid;
	}

	public int getSourceid() {
		return sourceid;
	}

	public void setSourceid(int sourceid) {
		this.sourceid = sourceid;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getPhotoid() {
		return photoid;
	}

	public void setPhotoid(String photoid) {
		this.photoid = photoid;
	}

	public String getUsestatus() {
		return usestatus;
	}

	public void setUsestatus(String usestatus) {
		this.usestatus = usestatus;
	}

	public String getsURL() {
		return sURL;
	}

	public void setsURL(String sURL) {
		this.sURL = sURL;
	}

	public String getmURL() {
		return mURL;
	}

	public void setmURL(String mURL) {
		this.mURL = mURL;
	}

	public String getlURL() {
		return lURL;
	}

	public void setlURL(String lURL) {
		this.lURL = lURL;
	}

	public int getInalbum() {
		return inalbum;
	}

	public void setInalbum(int inalbum) {
		this.inalbum = inalbum;
	}

	public int getCover() {
		return cover;
	}

	public void setCover(int cover) {
		this.cover = cover;
	}

	public int getPhotoStatus() {
		return photoStatus;
	}

	public void setPhotoStatus(int photoStatus) {
		this.photoStatus = photoStatus;
	}

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

}
