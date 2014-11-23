package com.nuoshi.console.view;

public class EstatePhotoCondition extends Pager {
	private Integer cityId;
	private Integer distId;
	private Integer blockId;
	private String photoType = "0";// 照片类型
	private Integer estateId;
	private String estateName;
	
	private String idOrder; //此项有项代表以ID升序排列
	
	private String hasOrNot;  //照片数量的情况（空为不限，yes 表示1到59张 ‘null’ 表示0，full表示 >=60） 
	private String hasOver = "no";// 是否已满(精选库照片数量是否达到60张,默认查询没满的)
	private String empty = "no";

	public Integer getCityId() {
		return cityId;
	}

	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}

	public Integer getDistId() {
		return distId;
	}

	public void setDistId(Integer distId) {
		this.distId = distId;
	}

	public Integer getBlockId() {
		return blockId;
	}

	public void setBlockId(Integer blockId) {
		this.blockId = blockId;
	}

	public String getPhotoType() {
		return photoType;
	}

	public void setPhotoType(String photoType) {
		this.photoType = photoType;
	}

	public Integer getEstateId() {
		return estateId;
	}

	public void setEstateId(Integer estateId) {
		this.estateId = estateId;
	}

	public String getEstateName() {
		return estateName;
	}

	public void setEstateName(String estateName) {
		this.estateName = estateName;
	}

	public String getHasOver() {
		return hasOver;
	}

	public void setHasOver(String hasOver) {
		this.hasOver = hasOver;
	}

	public String getEmpty() {
		return empty;
	}

	public void setEmpty(String empty) {
		this.empty = empty;
	}

	public String getHasOrNot() {
		return hasOrNot;
	}

	public void setHasOrNot(String hasOrNot) {
		this.hasOrNot = hasOrNot;
	}

	public String getIdOrder() {
		return idOrder;
	}

	public void setIdOrder(String idOrder) {
		this.idOrder = idOrder;
	}

}
