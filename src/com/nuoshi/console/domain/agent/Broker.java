package com.nuoshi.console.domain.agent;

import java.io.Serializable;

/**
 * Created by: liang Date: 2009-11-5 Time: 15:23:57
 */
public class Broker implements Serializable {
	private static final long serialVersionUID = 9189692905799743924L;
	private int id;
	private int province;
	private int city;
	private int district;
	private int block;
	private String brand;
	private String address;
	private String team;
	private int brandid;
	private int areaid;
	
    private String brandPinyin;
    private String addressPinyin;
    private Byte flags;
    private String cityName;
    private String districtName;
    private String blockName;
    /**
     * 公司类型：1.大客户，0.普通'
     */
    private String type;

    
    
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getBrandPinyin() {
		return brandPinyin;
	}

	public void setBrandPinyin(String brandPinyin) {
		this.brandPinyin = brandPinyin;
	}

	public String getAddressPinyin() {
		return addressPinyin;
	}

	public void setAddressPinyin(String addressPinyin) {
		this.addressPinyin = addressPinyin;
	}

	public Byte getFlags() {
		return flags;
	}

	public void setFlags(Byte flags) {
		this.flags = flags;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getDistrictName() {
		return districtName;
	}

	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}

	public String getBlockName() {
		return blockName;
	}

	public void setBlockName(String blockName) {
		this.blockName = blockName;
	}

	public int getBrandid() {
		return brandid;
	}

	public void setBrandid(int brandid) {
		this.brandid = brandid;
	}

	public int getAreaid() {
		return areaid;
	}

	public void setAreaid(int areaid) {
		this.areaid = areaid;
	}

	public int getDistrict() {
		return district;
	}

	public void setDistrict(int district) {
		this.district = district;
	}

	public int getBlock() {
		return block;
	}

	public void setBlock(int block) {
		this.block = block;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getProvince() {
		return province;
	}

	public void setProvince(int province) {
		this.province = province;
	}

	public int getCity() {
		return city;
	}

	public void setCity(int city) {
		this.city = city;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTeam() {
		return team;
	}

	public void setTeam(String team) {
		this.team = team;
	}

}
