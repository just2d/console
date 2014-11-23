package com.nuoshi.console.domain.rent;

import java.io.Serializable;
import java.util.List;

import com.nuoshi.console.common.util.ApplicationUtil;
import com.nuoshi.console.domain.agent.HousePhoto;
import com.nuoshi.console.view.multiselect.Equipment;
import com.nuoshi.console.view.multiselect.Facility;

/**
 * 房源基本信息
 * 
 * @author Administrator
 * 
 */

public class RentInfo extends Rent implements Serializable {
	private static final long serialVersionUID = 2887950748807187547L;
	private Equipment houseEquipment = new Equipment();
    private Facility houseFacility = new Facility();
 
    private String authorName;
	private String brand;
    private String brandAddress;
    private String authorCallnumber;
    private Long hxNum;
    private Long snNum;
    private Long xqNum;
    private String cityDir;
    private String authorMobile;
    private Integer inalbum;

    
    public Integer getInalbum() {
		return inalbum;
	}


	public void setInalbum(Integer inalbum) {
		this.inalbum = inalbum;
	}
	private String title;
	private List<HousePhoto> houseImgUrls;// 普通户型图
	private List<HousePhoto> estateImgUrls;// 普通小区图
	private List<HousePhoto> roomImgUrls;// 普通室内图

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


	public String getAuthorMobile() {
		return authorMobile;
	}


	public void setAuthorMobile(String authorMobile) {
		this.authorMobile = authorMobile;
	}


	public String getAuthorName() {
		return authorName;
	}


	public void setAuthorName(String authorName) {
		this.authorName = authorName;
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


	public String getTitle() {
		return title;
	}


	public Equipment getHouseEquipment() {
		houseEquipment.setValue(this.getEquipment());
		return houseEquipment;
	}


	public void setHouseEquipment(Equipment houseEquipment) {
		this.houseEquipment = houseEquipment;
	}


	public Facility getHouseFacility() {
		this.houseFacility.setValue(this.getFacility());
		return houseFacility;
	}


	public void setHouseFacility(Facility houseFacility) {
		this.houseFacility = houseFacility;
	}


	public void setTitle(String title) {
		this.title = title;
	}




	

	/*private List<String> sHouseImgUrls;// 户型图(小图)
	private List<String> sEstateImgUrls;// 小区图(小图)
	private List<String> sRoomImgUrls;// 室内图(小图)

	private List<String> lHouseImgUrls;// 户型图(大图)
	private List<String> lEstateImgUrls;// 小区图(大图)
	private List<String> lRoomImgUrls;// 室内图(大图)
*/
	
	public String getHouseFaceto(){
		return ApplicationUtil.getHouseFaceto(this.getFaceto());
	}
	public String getHouseDecoration(){
		return ApplicationUtil.getDecoration(this.getDecoration());
	}
}
