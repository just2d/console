package com.nuoshi.console.domain.topic;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Estate implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4816512085718889455L;
	private int id;// 小区id--主键
	private String address;
	private Integer blockId;
	private String blockName;
	private Integer cityId;
	private String cityName;
	private String cityCode;
	private String completion;
	private Integer distId;
	private String distName;
	private int estateId;
	private String estateName;
	private float lat;
	private float lon;
	private String photoURL;
	private Integer photoId;
	private int rentCount;
	private int resaleAvgPrice;
	private int rentAvgPrice;
	private float resaleAvgRate;
	private int resaleCount;
	private int sourceId;
	private List<String> text;
	private String type;
	private String basicInfo;
	private String wyType;// 物业类型
	private String areaRate;// 容积率
	private String greenRate;// 绿化率
	private String delStatus;// 删除状态
	private String authStatus;// 审核状态
	private String desc;// 项目描述
	private String alias;// 小区别名
	private String namepy;// 小区拼音
	private String origStatus;
	private Integer fromHouse;// 小区数据来源于淘房还是58
	private String pinYin;

	private String wyCompany;// 物业公司
	private String wyFee;// 物业费
	private String devCompany;// 开发商;
	private String buildYear;// 建筑年代
	private String buildArea;// 建筑面积
	private String area;// 占地面积

	private String carInfo;// 车位信息;
	private String university;// 大学
	private String school;// 中小学
	private String nursery;// 幼儿园
	private String market;// 超市
	private String hospital;// 医院
	private String postOffice;// 邮局
	private String bank;// 银行
	private String otherInfo;// 其他
	private String bus;// 公交;
	private String subWay;// 地铁;
	private String floor;// 楼层状况
	private String estateAddr;
	private String remark;
	private Integer rentNum;
	private Integer resaleNum;

	private int createUserId;//创建者id
	private String createUserName;//创建者用户名
	private String createNickName;//创建者昵称.
	private Date createTime;

	
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getWyCompany() {
		if (wyCompany == null) {
			return "";
		}
		return wyCompany;
	}

	public void setWyCompany(String wyCompany) {
		this.wyCompany = wyCompany;
	}

	public String getWyFee() {
		if (wyFee == null) {
			return "";
		}
		return wyFee;
	}

	public void setWyFee(String wyFee) {
		this.wyFee = wyFee;
	}

	public String getDevCompany() {
		if (devCompany == null) {
			return "";
		}
		return devCompany;
	}

	public void setDevCompany(String devCompany) {
		this.devCompany = devCompany;
	}

	public String getBuildYear() {
		if (buildYear == null) {
			return "";
		}
		return buildYear;
	}

	public void setBuildYear(String buildYear) {
		this.buildYear = buildYear;
	}

	public String getBuildArea() {
		if (buildArea == null) {
			return "";
		}
		return buildArea;
	}

	public void setBuildArea(String buildArea) {
		this.buildArea = buildArea;
	}

	public String getArea() {
		if (area == null) {
			return "";
		}
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getCarInfo() {
		if (carInfo == null) {
			return "";
		}
		return carInfo;
	}

	public void setCarInfo(String carInfo) {
		this.carInfo = carInfo;
	}

	public String getUniversity() {
		if (university == null) {
			return "";
		}
		return university;
	}

	public void setUniversity(String university) {
		this.university = university;
	}

	public String getSchool() {
		if (school == null) {
			return "";
		}
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	public String getNursery() {
		if (nursery == null) {
			return "";
		}
		return nursery;
	}

	public void setNursery(String nursery) {
		this.nursery = nursery;
	}

	public String getMarket() {
		if (market == null) {
			return "";
		}
		return market;
	}

	public void setMarket(String market) {
		this.market = market;
	}

	public String getHospital() {
		if (hospital == null) {
			return "";
		}
		return hospital;
	}

	public void setHospital(String hospital) {
		this.hospital = hospital;
	}

	public String getPostOffice() {
		if (postOffice == null) {
			return "";
		}
		return postOffice;
	}

	public void setPostOffice(String postOffice) {
		this.postOffice = postOffice;
	}

	public String getBank() {
		if (bank == null) {
			return "";
		}
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public String getOtherInfo() {
		if (otherInfo == null) {
			return "";
		}
		return otherInfo;
	}

	public void setOtherInfo(String otherInfo) {
		this.otherInfo = otherInfo;
	}

	public String getBus() {
		if (bus == null) {
			return "";
		}
		return bus;
	}

	public void setBus(String bus) {
		this.bus = bus;
	}

	public String getSubWay() {
		if (subWay == null) {
			return "";
		}
		return subWay;
	}

	public void setSubWay(String subWay) {
		this.subWay = subWay;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

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

	public String getCompletion() {
		return completion;
	}

	public void setCompletion(String completion) {
		this.completion = completion;
	}

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

	public int getEstateId() {
		return estateId;
	}

	public void setEstateId(int estateId) {
		this.estateId = estateId;
	}

	public String getEstateName() {
		return estateName;
	}

	public void setEstateName(String estateName) {
		this.estateName = estateName;
	}

	public float getLat() {
		return lat;
	}

	public void setLat(float lat) {
		this.lat = lat;
	}

	public float getLon() {
		return lon;
	}

	public void setLon(float lon) {
		this.lon = lon;
	}

	public String getPhotoURL() {
		return photoURL;
	}

	public void setPhotoURL(String photoURL) {
		this.photoURL = photoURL;
	}

	public int getRentCount() {
		return rentCount;
	}

	public void setRentCount(int rentCount) {
		this.rentCount = rentCount;
	}

	public int getResaleAvgPrice() {
		return resaleAvgPrice;
	}

	public void setResaleAvgPrice(int resaleAvgPrice) {
		this.resaleAvgPrice = resaleAvgPrice;
	}

	public float getResaleAvgRate() {
		return resaleAvgRate;
	}

	public void setResaleAvgRate(float resaleAvgRate) {
		this.resaleAvgRate = resaleAvgRate;
	}

	public int getResaleCount() {
		return resaleCount;
	}

	public void setResaleCount(int resaleCount) {
		this.resaleCount = resaleCount;
	}

	public int getSourceId() {
		return sourceId;
	}

	public void setSourceId(int sourceId) {
		this.sourceId = sourceId;
	}

	public List<String> getText() {
		return text;
	}

	public void setText(List<String> text) {
		this.text = text;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getBasicInfo() {
		return basicInfo;
	}

	public void setBasicInfo(String basicInfo) {
		this.basicInfo = basicInfo;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getWyType() {
		return wyType;
	}

	public void setWyType(String wyType) {
		this.wyType = wyType;
	}

	public String getAreaRate() {
		return areaRate;
	}

	public void setAreaRate(String areaRate) {
		this.areaRate = areaRate;
	}

	public String getGreenRate() {
		return greenRate;
	}

	public void setGreenRate(String greenRate) {
		this.greenRate = greenRate;
	}

	public String getDelStatus() {
		return delStatus;
	}

	public void setDelStatus(String delStatus) {
		this.delStatus = delStatus;
	}

	public String getAuthStatus() {
		return authStatus;
	}

	public void setAuthStatus(String authStatus) {
		this.authStatus = authStatus;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getNamepy() {
		return namepy;
	}

	public void setNamepy(String namepy) {
		this.namepy = namepy;
	}

	public int getRentAvgPrice() {
		return rentAvgPrice;
	}

	public void setRentAvgPrice(int rentAvgPrice) {
		this.rentAvgPrice = rentAvgPrice;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getOrigStatus() {
		return origStatus;
	}

	public void setOrigStatus(String origStatus) {
		this.origStatus = origStatus;
	}

	public Integer getFromHouse() {
		return fromHouse;
	}

	public void setFromHouse(Integer fromHouse) {
		this.fromHouse = fromHouse;
	}

	public Integer getPhotoId() {
		return photoId;
	}

	public void setPhotoId(Integer photoId) {
		this.photoId = photoId;
	}

	public String getPinYin() {
		return pinYin;
	}

	public void setPinYin(String pinYin) {
		this.pinYin = pinYin;
	}

	public String getFloor() {
		return floor;
	}

	public void setFloor(String floor) {
		this.floor = floor;
	}

	public String getEstateAddr() {
		return estateAddr;
	}

	public void setEstateAddr(String estateAddr) {
		this.estateAddr = estateAddr;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getRentNum() {
		return rentNum;
	}

	public void setRentNum(Integer rentNum) {
		this.rentNum = rentNum;
	}

	public Integer getResaleNum() {
		return resaleNum;
	}

	public void setResaleNum(Integer resaleNum) {
		this.resaleNum = resaleNum;
	}

	public int getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(int createUserId) {
		this.createUserId = createUserId;
	}

	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	public String getCreateNickName() {
		return createNickName;
	}

	public void setCreateNickName(String createNickName) {
		this.createNickName = createNickName;
	}

}
