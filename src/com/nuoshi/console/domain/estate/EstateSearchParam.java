package com.nuoshi.console.domain.estate;


public class EstateSearchParam {
	public static final int ORDERBY_RTS_DESC = 0;
	public static final int ORDERBY_PUBDATE_DESC = 1;
	protected int cityId;
	protected int distId;
	protected int blockId;
	protected int priceMin;
	protected int priceMax;
	protected int estateId;
	protected String keyword;
	
	
	protected int start;
	protected int limit;

	public EstateSearchParam() {
	}

	public int getCityId() {
		return cityId;
	}

	public void setCityId(int cityId) {
		this.cityId = cityId;
	}


	public int getPriceMin() {
		return priceMin;
	}

	public int getEstateId() {
		return estateId;
	}

	public void setEstateId(int estateId) {
		this.estateId = estateId;
	}

	public void setPriceMin(int priceMin) {
		this.priceMin = priceMin;
	}

	public int getPriceMax() {
		return priceMax;
	}

	public void setPriceMax(int priceMax) {
		this.priceMax = priceMax;
	}


	public int getDistId() {
		return distId;
	}

	public void setDistId(int distId) {
		this.distId = distId;
	}

	public int getBlockId() {
		return blockId;
	}

	public void setBlockId(int blockId) {
		this.blockId = blockId;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}


}
