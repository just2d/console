package com.nuoshi.console.domain.estate;

import java.net.URLEncoder;

import org.apache.commons.lang.StringUtils;

import com.nuoshi.console.service.LocaleService;

public class EstateHouseCount {
	private int estateId;
	private String name;
	private int cityId;
	private int distId;
	private int blockId;
	private String cityCode;
	private String distCode;
	private String blockCode;
	private int resaleCount;
	private int rentCount;
	private String pinyin;
	private String url;
	// 1:旧小区URL；2：新小区URL;3:地标二手房；4:地标租房；5：关键字二手房；6：关键字租房
	private int type;
	
	private String popularizePlan;
	private String popularizeUnit;
	private String popularizeKeyword;
	

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getResaleCount() {
		return resaleCount;
	}
	public void setResaleCount(int resaleCount) {
		this.resaleCount = resaleCount;
	}
	public int getRentCount() {
		return rentCount;
	}
	public void setRentCount(int rentCount) {
		this.rentCount = rentCount;
	}
	public int getCityId() {
		return cityId;
	}
	public void setCityId(int cityId) {
		this.cityId = cityId;
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
	
	public String getPinyin() {
		return pinyin;
	}
	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}

	public String getCityName(){
		return LocaleService.getName(this.cityId);
	}
	
	public String getDistName() {
		return LocaleService.getName(this.distId);
	}
	
	public String getBlockName(){
		return LocaleService.getName(this.blockId);
	}
	public int getEstateId() {
		return estateId;
	}
	public void setEstateId(int estateId) {
		this.estateId = estateId;
	}
	public String getPopularizePlan() {
		return popularizePlan;
	}
	public void setPopularizePlan(String popularizePlan) {
		this.popularizePlan = popularizePlan;
	}
	public String getPopularizeUnit() {
		return popularizeUnit;
	}
	public void setPopularizeUnit(String popularizeUnit) {
		this.popularizeUnit = popularizeUnit;
	}
	public String getPopularizeKeyword() {
		return popularizeKeyword;
	}
	public void setPopularizeKeyword(String popularizeKeyword) {
		this.popularizeKeyword = popularizeKeyword;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
		if(StringUtils.isNotBlank(url)) {
			if(url.indexOf("xiaoqu") > -1) {
				if(url.indexOf("xq_") > -1) {
					this.type = 2;
				} else {
					this.type = 1;
				}
			} else if(url.indexOf("ershoufang") > -1) {
				this.type = 3;
			} else if(url.indexOf("zufang") > -1) {
				this.type = 4;
			} else if(url.indexOf("e_") > -1) {
				this.type = 5;
			} else if(url.indexOf("z_") > -1) {
				this.type = 6;
			}
		}
	}
	public String getCityCode() {
		return cityCode;
	}
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
	public String getDistCode() {
		return distCode;
	}
	public void setDistCode(String distCode) {
		this.distCode = distCode;
	}
	public String getBlockCode() {
		return blockCode;
	}
	public void setBlockCode(String blockCode) {
		this.blockCode = blockCode;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	
	public String getSearchLocationId(){
		StringBuilder sb = new StringBuilder();
		if(this.type == 3 || this.type == 4) {
			try{
				String[] array = this.url.split("/");
				this.cityId = LocaleService.getIdByCode(array[2].split("\\.")[0], 0);
				this.distId = LocaleService.getIdByCode(array[3], this.cityId);
				this.blockId = LocaleService.getIdByCode(array[4], this.distId);
			} catch(Exception e) {
				
			}
		}
		if(this.blockId > 0) {
			sb.append("fq=blockId:" + this.blockId);
		} else if(this.distId > 0) {
			sb.append("fq=distId:" + this.distId);
		} else {
			sb.append("fq=cityId:" + this.cityId);
		}
		return sb.toString();
	}
	
	public String getSearchKeywordCity(){
		StringBuilder sb = new StringBuilder();
		if(this.type == 5 || this.type == 6) {
			try{
				String[] array = this.url.split("/");
				this.cityId = LocaleService.getIdByCode(array[2].split("\\.")[0], 0);
			} catch(Exception e) {
				
			}
		}
		if(this.cityId > 0) {
			sb.append("fq=cityId:" + this.cityId);
		} 
		return sb.toString();
	}
	
	public String getSearchKeyword(){
		try{
			if(StringUtils.isNotBlank(this.popularizeKeyword)) {
				return "qt=searchKeyword&q=" + URLEncoder.encode(this.popularizeKeyword, "utf8");
			} 
		} catch(Exception e) {
			
		}
		return "qt=search";
	}
	
	public String getEstateUnique(){
		String[] array = this.url.split("/");
		if(array.length >= 4) {
			if(array[4].indexOf("xq_") > -1) {
				return array[4].substring(3, array[4].length());
			}
			return array[4];
		}
		return null;
	}
}
