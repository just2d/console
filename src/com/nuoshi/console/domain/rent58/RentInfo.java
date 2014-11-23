package com.nuoshi.console.domain.rent58;

import java.io.Serializable;
import java.util.List;

import com.nuoshi.console.common.util.ApplicationUtil;
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

	public List<String> getHouseImgUrls() {
		return houseImgUrls;
	}

	public void setHouseImgUrls(List<String> houseImgUrls) {
		this.houseImgUrls = houseImgUrls;
	}

	public List<String> getEstateImgUrls() {
		return estateImgUrls;
	}

	public void setEstateImgUrls(List<String> estateImgUrls) {
		this.estateImgUrls = estateImgUrls;
	}

	public List<String> getRoomImgUrls() {
		return roomImgUrls;
	}

	public void setRoomImgUrls(List<String> roomImgUrls) {
		this.roomImgUrls = roomImgUrls;
	}

	private String title;
	private List<String> houseImgUrls;// 普通户型图
	private List<String> estateImgUrls;// 普通小区图
	private List<String> roomImgUrls;// 普通室内图

	private List<String> sHouseImgUrls;// 户型图(小图)
	private List<String> sEstateImgUrls;// 小区图(小图)
	private List<String> sRoomImgUrls;// 室内图(小图)

	private List<String> lHouseImgUrls;// 户型图(大图)
	private List<String> lEstateImgUrls;// 小区图(大图)
	private List<String> lRoomImgUrls;// 室内图(大图)

	public List<String> getsHouseImgUrls() {
		return sHouseImgUrls;
	}

	public void setsHouseImgUrls(List<String> sHouseImgUrls) {
		this.sHouseImgUrls = sHouseImgUrls;
	}

	public List<String> getsEstateImgUrls() {
		return sEstateImgUrls;
	}

	public void setsEstateImgUrls(List<String> sEstateImgUrls) {
		this.sEstateImgUrls = sEstateImgUrls;
	}

	public List<String> getsRoomImgUrls() {
		return sRoomImgUrls;
	}

	public void setsRoomImgUrls(List<String> sRoomImgUrls) {
		this.sRoomImgUrls = sRoomImgUrls;
	}

	public List<String> getlHouseImgUrls() {
		return lHouseImgUrls;
	}

	public void setlHouseImgUrls(List<String> lHouseImgUrls) {
		this.lHouseImgUrls = lHouseImgUrls;
	}

	public List<String> getlEstateImgUrls() {
		return lEstateImgUrls;
	}

	public void setlEstateImgUrls(List<String> lEstateImgUrls) {
		this.lEstateImgUrls = lEstateImgUrls;
	}

	public List<String> getlRoomImgUrls() {
		return lRoomImgUrls;
	}

	public void setlRoomImgUrls(List<String> lRoomImgUrls) {
		this.lRoomImgUrls = lRoomImgUrls;
	}
	public String getHouseFaceto(){
		return ApplicationUtil.getHouseFaceto(this.getFaceto());
	}
	public String getHouseDecoration(){
		return ApplicationUtil.getDecoration(this.getDecoration());
	}
}
