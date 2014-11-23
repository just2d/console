package com.nuoshi.console.domain.house;


import java.io.Serializable;


public class DayHouseQuality implements Serializable {

	/**
	* 房源类型：　１　二手房，２　租房
	*/
	private Byte type ;
	/**
	* 选填项都选的房源数
	*/
	private Integer allOptionNum ;
	/**
	* 选填项大于等于４的房源数
	*/
	private Integer fourOptionNum ;
	/**
	* 有户型图的房源数：户型图>=1
	*/
	private Integer hasLayoutNum ;
	/**
	* 室内图≥4张
	*/
	private Integer hasFourInnerNum ;
	/**
	* 小区图≥2张
	*/
	private Integer hasTwoCommunity ;
	/**
	* 户型图1张，室内图4张以上，小区图2张以上
	*/
	private Integer hasAllPhoto ;
	/**
	* 已标识客厅、卧室等类别照片
	*/
	private Integer hasInnertypePhotoNum ;
	/**
	* 宽640*高480以上
	*/
	private Integer hasHdPhotoNum ;
	/**
	* 照片总数
	*/
	private Integer photoNum ;
	/**
	* 房源总数
	*/
	private Integer houseNum ;
	/**
	* 60分以下
	*/
	private Integer score0HouseNum ;
	/**
	* 61-70分
	*/
	private Integer score1HouseNum ;
	/**
	* 71-80分
	*/
	private Integer score2HouseNum ;
	/**
	* 81-90分
	*/
	private Integer score3HouseNum ;
	/**
	* 91分以上
	*/
	private Integer score4HouseNum ;
	/**
	* 录入时间-精确到某日
	*/
	private Integer entryDate ;
	private static final long serialVersionUID = 1L;

	/**
	* 房源类型：　１　二手房，２　租房
	*/
	public Byte getType() {
			return this.type;
		}
	/**
	* 房源类型：　１　二手房，２　租房
	*/
	public void setType(Byte type) {
			this.type = type;
		}
	/**
	* 选填项都选的房源数
	*/
	public Integer getAllOptionNum() {
			return this.allOptionNum;
		}
	/**
	* 选填项都选的房源数
	*/
	public void setAllOptionNum(Integer allOptionNum) {
			this.allOptionNum = allOptionNum;
		}
	/**
	* 选填项大于等于４的房源数
	*/
	public Integer getFourOptionNum() {
			return this.fourOptionNum;
		}
	/**
	* 选填项大于等于４的房源数
	*/
	public void setFourOptionNum(Integer fourOptionNum) {
			this.fourOptionNum = fourOptionNum;
		}
	/**
	* 有户型图的房源数：户型图>=1
	*/
	public Integer getHasLayoutNum() {
			return this.hasLayoutNum;
		}
	/**
	* 有户型图的房源数：户型图>=1
	*/
	public void setHasLayoutNum(Integer hasLayoutNum) {
			this.hasLayoutNum = hasLayoutNum;
		}
	/**
	* 室内图≥4张
	*/
	public Integer getHasFourInnerNum() {
			return this.hasFourInnerNum;
		}
	/**
	* 室内图≥4张
	*/
	public void setHasFourInnerNum(Integer hasFourInnerNum) {
			this.hasFourInnerNum = hasFourInnerNum;
		}
	/**
	* 小区图≥2张
	*/
	public Integer getHasTwoCommunity() {
			return this.hasTwoCommunity;
		}
	/**
	* 小区图≥2张
	*/
	public void setHasTwoCommunity(Integer hasTwoCommunity) {
			this.hasTwoCommunity = hasTwoCommunity;
		}
	/**
	* 户型图1张，室内图4张以上，小区图2张以上
	*/
	public Integer getHasAllPhoto() {
			return this.hasAllPhoto;
		}
	/**
	* 户型图1张，室内图4张以上，小区图2张以上
	*/
	public void setHasAllPhoto(Integer hasAllPhoto) {
			this.hasAllPhoto = hasAllPhoto;
		}
	/**
	* 已标识客厅、卧室等类别照片
	*/
	public Integer getHasInnertypePhotoNum() {
			return this.hasInnertypePhotoNum;
		}
	/**
	* 已标识客厅、卧室等类别照片
	*/
	public void setHasInnertypePhotoNum(Integer hasInnertypePhotoNum) {
			this.hasInnertypePhotoNum = hasInnertypePhotoNum;
		}
	/**
	* 宽640*高480以上
	*/
	public Integer getHasHdPhotoNum() {
			return this.hasHdPhotoNum;
		}
	/**
	* 宽640*高480以上
	*/
	public void setHasHdPhotoNum(Integer hasHdPhotoNum) {
			this.hasHdPhotoNum = hasHdPhotoNum;
		}
	/**
	* 照片总数
	*/
	public Integer getPhotoNum() {
			return this.photoNum;
		}
	/**
	* 照片总数
	*/
	public void setPhotoNum(Integer photoNum) {
			this.photoNum = photoNum;
		}
	/**
	* 房源总数
	*/
	public Integer getHouseNum() {
			return this.houseNum;
		}
	/**
	* 房源总数
	*/
	public void setHouseNum(Integer houseNum) {
			this.houseNum = houseNum;
		}
	/**
	* 60分以下
	*/
	public Integer getScore0HouseNum() {
			return this.score0HouseNum;
		}
	/**
	* 60分以下
	*/
	public void setScore0HouseNum(Integer score0HouseNum) {
			this.score0HouseNum = score0HouseNum;
		}
	/**
	* 61-70分
	*/
	public Integer getScore1HouseNum() {
			return this.score1HouseNum;
		}
	/**
	* 61-70分
	*/
	public void setScore1HouseNum(Integer score1HouseNum) {
			this.score1HouseNum = score1HouseNum;
		}
	/**
	* 71-80分
	*/
	public Integer getScore2HouseNum() {
			return this.score2HouseNum;
		}
	/**
	* 71-80分
	*/
	public void setScore2HouseNum(Integer score2HouseNum) {
			this.score2HouseNum = score2HouseNum;
		}
	/**
	* 81-90分
	*/
	public Integer getScore3HouseNum() {
			return this.score3HouseNum;
		}
	/**
	* 81-90分
	*/
	public void setScore3HouseNum(Integer score3HouseNum) {
			this.score3HouseNum = score3HouseNum;
		}
	/**
	* 91分以上
	*/
	public Integer getScore4HouseNum() {
			return this.score4HouseNum;
		}
	/**
	* 91分以上
	*/
	public void setScore4HouseNum(Integer score4HouseNum) {
			this.score4HouseNum = score4HouseNum;
		}
	/**
	* 录入时间-精确到某日
	*/
	public Integer getEntryDate() {
			return this.entryDate;
		}
	/**
	* 录入时间-精确到某日
	*/
	public void setEntryDate(Integer entryDate) {
			this.entryDate = entryDate;
		}
}