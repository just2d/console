package com.nuoshi.console.view;

import java.io.Serializable;

public class EstatePhotoDetail extends BasePhoto implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer beds;
	private String source = "0";// 来源:0为淘房,1为58

	public Integer getBeds() {
		return beds;
	}

	public void setBeds(Integer beds) {
		this.beds = beds;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}


}
