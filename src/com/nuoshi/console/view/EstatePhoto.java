package com.nuoshi.console.view;

import java.io.Serializable;

/**
 * 小区照片
 * 
 * @author ningt
 * 
 */
public class EstatePhoto extends EstatePhotoCondition implements Serializable {

	private static final long serialVersionUID = 1927670459806341133L;

	private int backupLayoutCount;// 备选数量.
	private int backupCommCount;// 备选数量.
	private int commCount;//精选库数量 
	private int layoutCount;
	private String cityName;
	private String distName;
	private String blockName;
	private Integer order;
	

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

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getDistName() {
		return distName;
	}

	public void setDistName(String distName) {
		this.distName = distName;
	}

	public String getBlockName() {
		return blockName;
	}

	public void setBlockName(String blockName) {
		this.blockName = blockName;
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

}
