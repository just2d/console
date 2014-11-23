package com.nuoshi.console.view;

import java.io.Serializable;

import com.nuoshi.console.common.Helper;

/**
 * 图片详情
 * 
 * @author ningt
 * 
 */
public class BasePhoto implements Serializable {
	private static final long serialVersionUID = 666006982329113705L;
	private Integer id;// 主键
	private Integer estateId;
	private String lPhoto;// 大图
	private String order;// 序号
	private String sPhoto;
	private String mPhoto;
	private Integer photoId;
	private int sourceId;

    public static final int L_MAX_HEIGHT    = 480;
    public static final int L_MAX_WIDTH     = 640;

    public static final int M_MAX_HEIGHT    = 174;
    public static final int M_MAX_WIDTH     = 232;

    public static final int S_MAX_HEIGHT    = 75;
    public static final int S_MAX_WIDTH     = 100;

	public String getsPhoto() {
		return sPhoto;
	}

	public void setsPhoto(String sPhoto) {
		this.sPhoto = sPhoto;
	}

	public String getmPhoto() {
		return mPhoto;
	}

	public void setmPhoto(String mPhoto) {
		this.mPhoto = mPhoto;
	}

	public Integer getEstateId() {
		return estateId;
	}

	public void setEstateId(Integer estateId) {
		this.estateId = estateId;
	}

	public String getlPhoto() {
		return lPhoto;
	}

	public void setlPhoto(String lPhoto) {
		this.lPhoto = lPhoto;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getPhotoId() {
		return photoId;
	}

	public void setPhotoId(Integer photoId) {
		this.photoId = photoId;
	}

	public int getSourceId() {
		return sourceId;
	}

	public void setSourceId(int sourceId) {
		this.sourceId = sourceId;
	}

}
