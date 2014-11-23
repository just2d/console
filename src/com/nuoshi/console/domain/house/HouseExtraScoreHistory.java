package com.nuoshi.console.domain.house;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


public class HouseExtraScoreHistory implements Serializable {

	private Integer id ;
	/**
	* 房源类型： 1-二手房，2－出租房
	*/
	private int houseType ;
	/**
	* 房源ID
	*/
	private Integer houseId ;
	private BigDecimal score ;
	/**
	* 录入时间
	*/
	private Date entryDate ;
	private String operator ;
	private static final long serialVersionUID = 1L;

	public Integer getId() {
			return this.id;
		}
	public void setId(Integer id) {
			this.id = id;
		}
	/**
	* 房源类型： 1-二手房，2－出租房
	*/
	public int getHouseType() {
			return this.houseType;
		}
	/**
	* 房源类型： 1-二手房，2－出租房
	*/
	public void setHouseType(int houseType) {
			this.houseType = houseType;
		}
	/**
	* 房源ID
	*/
	public Integer getHouseId() {
			return this.houseId;
		}
	/**
	* 房源ID
	*/
	public void setHouseId(Integer houseId) {
			this.houseId = houseId;
		}
	public BigDecimal getScore() {
			return this.score;
		}
	public void setScore(BigDecimal score) {
			this.score = score;
		}
	/**
	* 录入时间
	*/
	public Date getEntryDate() {
			return this.entryDate;
		}
	/**
	* 录入时间
	*/
	public void setEntryDate(Date entryDate) {
			this.entryDate = entryDate;
		}
	public String getOperator() {
			return this.operator;
		}
	public void setOperator(String operator) {
			this.operator = operator;
		}
}