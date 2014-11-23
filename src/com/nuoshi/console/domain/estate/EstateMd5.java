package com.nuoshi.console.domain.estate;

import java.io.Serializable;

/**
 * 获得小区图的md5值
 * @author ningt
 *
 */
public class EstateMd5 implements Serializable{
	private Integer estateId;
	private String md5;
	private String category;

	public Integer getEstateId() {
		return estateId;
	}

	public void setEstateId(Integer estateId) {
		this.estateId = estateId;
	}

	public String getMd5() {
		return md5;
	}

	public void setMd5(String md5) {
		this.md5 = md5;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

}
