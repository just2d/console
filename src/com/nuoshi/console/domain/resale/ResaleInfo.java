package com.nuoshi.console.domain.resale;

import java.io.Serializable;
import java.util.List;

import com.nuoshi.console.domain.agent.HousePhoto;

/**
 * 房源基本信息
 * 
 * @author Administrator
 * 
 */

public class ResaleInfo extends Resale implements Serializable {
	private static final long serialVersionUID = 8595409641766961257L;

	 private String authorName;
		private String brand;
	    private String brandAddress;
	    private String authorCallnumber;
	    private Long hxNum;
	    private Long snNum;
	    private Long xqNum;
	    private String cityDir;
	    private String authorMobile;
	    private String title;
		private List<HousePhoto> houseImgUrls;// 普通户型图
		private List<HousePhoto> estateImgUrls;// 普通小区图
		private List<HousePhoto> roomImgUrls;// 普通室内图
		private Integer inalbum;



	public Integer getInalbum() {
			return inalbum;
		}

		public void setInalbum(Integer inalbum) {
			this.inalbum = inalbum;
		}

	public String getAuthorName() {
			return authorName;
		}

		public void setAuthorName(String authorName) {
			this.authorName = authorName;
		}

		public String getBrand() {
			return brand;
		}

		public void setBrand(String brand) {
			this.brand = brand;
		}

		public String getBrandAddress() {
			return brandAddress;
		}

		public void setBrandAddress(String brandAddress) {
			this.brandAddress = brandAddress;
		}

		public String getAuthorCallnumber() {
			return authorCallnumber;
		}

		public void setAuthorCallnumber(String authorCallnumber) {
			this.authorCallnumber = authorCallnumber;
		}

		public Long getHxNum() {
			return hxNum;
		}

		public void setHxNum(Long hxNum) {
			this.hxNum = hxNum;
		}

		public Long getSnNum() {
			return snNum;
		}

		public void setSnNum(Long snNum) {
			this.snNum = snNum;
		}

		public Long getXqNum() {
			return xqNum;
		}

		public void setXqNum(Long xqNum) {
			this.xqNum = xqNum;
		}

		public String getCityDir() {
			return cityDir;
		}

		public void setCityDir(String cityDir) {
			this.cityDir = cityDir;
		}

		public String getAuthorMobile() {
			return authorMobile;
		}

		public void setAuthorMobile(String authorMobile) {
			this.authorMobile = authorMobile;
		}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<HousePhoto> getHouseImgUrls() {
		return houseImgUrls;
	}

	public void setHouseImgUrls(List<HousePhoto> houseImgUrls) {
		this.houseImgUrls = houseImgUrls;
	}

	public List<HousePhoto> getEstateImgUrls() {
		return estateImgUrls;
	}

	public void setEstateImgUrls(List<HousePhoto> estateImgUrls) {
		this.estateImgUrls = estateImgUrls;
	}

	public List<HousePhoto> getRoomImgUrls() {
		return roomImgUrls;
	}

	public void setRoomImgUrls(List<HousePhoto> roomImgUrls) {
		this.roomImgUrls = roomImgUrls;
	}




}
