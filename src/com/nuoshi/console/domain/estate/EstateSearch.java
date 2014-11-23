package com.nuoshi.console.domain.estate;



/**
 * Created by IntelliJ IDEA.
 * User: pekky
 * Date: 2009-12-2
 * Time: 17:39:44
 * To change this template use File | Settings | File Templates.
 */
public class EstateSearch {
	private Integer blockId;
	private String blockName;
	private String address;
	private Integer cityId;
	private String cityName;
	//private Integer completion;
	private Integer distId;
	private String distName;
	private Integer estateId;
	private String estateName;
	private Integer photoCnt;
	private Integer layoutCnt;
	private Integer rentCount;
	private Integer resaleCount;
	private Integer resaleAvgPrice;
	private Float resaleAvgRate;
	private Integer sourceId;
	public Integer getBlockId() {
		return blockId;
	}
	public void setBlockId(Integer blockId) {
		this.blockId = blockId;
	}
	public String getBlockName() {
		return blockName;
	}
	public void setBlockName(String blockName) {
		this.blockName = blockName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Integer getCityId() {
		return cityId;
	}
	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	/*public Integer getCompletion() {
		return completion;
	}
	public void setCompletion(Integer completion) {
		this.completion = completion;
	}*/
	public Integer getDistId() {
		return distId;
	}
	public void setDistId(Integer distId) {
		this.distId = distId;
	}
	public String getDistName() {
		return distName;
	}
	public void setDistName(String distName) {
		this.distName = distName;
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
	public Integer getPhotoCnt() {
		return photoCnt;
	}
	public void setPhotoCnt(Integer photoCnt) {
		this.photoCnt = photoCnt;
	}
	public Integer getLayoutCnt() {
		return layoutCnt;
	}
	public void setLayoutCnt(Integer layoutCnt) {
		this.layoutCnt = layoutCnt;
	}
	public Integer getRentCount() {
		return rentCount;
	}
	public void setRentCount(Integer rentCount) {
		this.rentCount = rentCount;
	}
	public Integer getResaleCount() {
		return resaleCount;
	}
	public void setResaleCount(Integer resaleCount) {
		this.resaleCount = resaleCount;
	}
	public Integer getResaleAvgPrice() {
		return resaleAvgPrice;
	}
	public void setResaleAvgPrice(Integer resaleAvgPrice) {
		this.resaleAvgPrice = resaleAvgPrice;
	}
	public Float getResaleAvgRate() {
		return resaleAvgRate;
	}
	public void setResaleAvgRate(Float resaleAvgRate) {
		this.resaleAvgRate = resaleAvgRate;
	}
	public Integer getSourceId() {
		return sourceId;
	}
	public void setSourceId(Integer sourceId) {
		this.sourceId = sourceId;
	}
	
	
}
