package com.nuoshi.console.domain.stat;

import com.nuoshi.console.service.LocaleService;


public class ConvertRate {
	/**
	 * '城市ID，0代表全国',
	 */
	private Integer cityId;
	/**
	 * '出租房详情页PV',
	 */
    private Integer rentVpPv; 
    /**
     * 租房列表uv数
     */
    private Integer rentListUv;
    /**
     * 租房uv转化率
     */
    private Double rentUvConvRate;
    /**
     * 二手房列表uv数
     */
    private Integer resaleListUv; 
    /**
     * 二手房uv转化率
     */
    private Double resaleUvConvRate;
    /**
     * '二手房详情页PV',
     */
    private Integer resaleVpPv; 
    /**
     * '出租房列表页PV',
     */
    private Integer rentListPv;
    /**
     * '二手房列表页PV',
     */
    private Integer resaleListPv;
    /**
     * '出租房转化率，详情页PV/列表页PV',
     */
    private Double rentConvRate;
    /**
     * '二手房转化率，详情页PV/列表页PV',
     */
    private Double resaleConvRate;
    
    
    
    private String entryDate;
    
	public Integer getCityId() {
		return cityId;
	}
	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}
	public Integer getRentVpPv() {
		return rentVpPv;
	}
	public void setRentVpPv(Integer rentVpPv) {
		this.rentVpPv = rentVpPv;
	}
	public Integer getResaleVpPv() {
		return resaleVpPv;
	}
	public void setResaleVpPv(Integer resaleVpPv) {
		this.resaleVpPv = resaleVpPv;
	}
	
	public Integer getRentListPv() {
		return rentListPv;
	}
	public void setRentListPv(Integer rentListPv) {
		this.rentListPv = rentListPv;
	}
	public Integer getResaleListPv() {
		return resaleListPv;
	}
	public void setResaleListPv(Integer resaleListPv) {
		this.resaleListPv = resaleListPv;
	}
	public Integer getresaleListPv() {
		return resaleListPv;
	}
	public void setresaleListPv(Integer resaleListPv) {
		this.resaleListPv = resaleListPv;
	}
	public Double getRentConvRate() {
		return rentConvRate;
	}
	public void setRentConvRate(Double rentConvRate) {
		this.rentConvRate = rentConvRate;
	}
	public Double getResaleConvRate() {
		return resaleConvRate;
	}
	public void setResaleConvRate(Double resaleConvRate) {
		this.resaleConvRate = resaleConvRate;
	}
	public String getEntryDate() {
		if(entryDate.length()==8){
			String year=entryDate.substring(0,4);
			String month=entryDate.substring(4,6);
			String day=entryDate.substring(6,8);
			return year+"-"+month+"-"+day;
		}
		return entryDate;
	}
	public void setEntryDate(String entryDate) {
		this.entryDate = entryDate;
	}
	
	public String getCityName() {
		if(cityId==0){
			return "全国";
		}
		if(cityId < 1) {
			return "其他";
		}
		return LocaleService.getName(this.cityId);
	}
	public Integer getRentListUv() {
		return rentListUv;
	}
	public void setRentListUv(Integer rentListUv) {
		this.rentListUv = rentListUv;
	}
	public Integer getResaleListUv() {
		return resaleListUv;
	}
	public void setResaleListUv(Integer resaleListUv) {
		this.resaleListUv = resaleListUv;
	}
	public Double getRentUvConvRate() {
		return rentUvConvRate;
	}
	public void setRentUvConvRate(Double rentUvConvRate) {
		this.rentUvConvRate = rentUvConvRate;
	}
	public Double getResaleUvConvRate() {
		return resaleUvConvRate;
	}
	public void setResaleUvConvRate(Double resaleUvConvRate) {
		this.resaleUvConvRate = resaleUvConvRate;
	}
	
    
}
