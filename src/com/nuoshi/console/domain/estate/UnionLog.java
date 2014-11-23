package com.nuoshi.console.domain.estate;

import java.io.Serializable;
import java.util.Date;

public class UnionLog implements Serializable {

	private static final long serialVersionUID = -2936798360118405385L;
	private Integer id;
	private Integer estateid;
	private String estatename;
	private Integer cityid;
	private String t_CityCode;
	private Integer distid;
	private Integer blockid;
	private Date cts ;// 创建时间
	private Integer houseId;
	private Integer targetId;// 合并后的小区id
	private String targetName;// 和并后的小区名
	private String fromTable;
	private String operator;// 操作人
	private String s_authStatus;// 合并前的小区审核状态
	private int num;// 房源数量

	public Integer getTargetId() {
		return targetId;
	}

	public void setTargetId(Integer targetId) {
		this.targetId = targetId;
	}

	public String getTargetName() {
		return targetName;
	}

	public void setTargetName(String targetName) {
		this.targetName = targetName;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getEstateid() {
		return estateid;
	}

	public void setEstateid(Integer estateid) {
		this.estateid = estateid;
	}

	public String getEstatename() {
		return estatename;
	}

	public void setEstatename(String estatename) {
		this.estatename = estatename;
	}

	public Integer getCityid() {
		return cityid;
	}

	public void setCityid(Integer cityid) {
		this.cityid = cityid;
	}

	public Integer getDistid() {
		return distid;
	}

	public void setDistid(Integer distid) {
		this.distid = distid;
	}

	public Integer getBlockid() {
		return blockid;
	}

	public void setBlockid(Integer blockid) {
		this.blockid = blockid;
	}


	public Integer getHouseId() {
		return houseId;
	}

	public void setHouseId(Integer houseId) {
		this.houseId = houseId;
	}

	public String getFromTable() {
		return fromTable;
	}

	public void setFromTable(String fromTable) {
		this.fromTable = fromTable;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getT_CityCode() {
		return t_CityCode;
	}

	public void setT_CityCode(String tCityCode) {
		t_CityCode = tCityCode;
	}

	public String getS_authStatus() {
		return s_authStatus;
	}

	public void setS_authStatus(String sAuthStatus) {
		s_authStatus = sAuthStatus;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public Date getCts() {
		return cts;
	}

	public void setCts(Date cts) {
		this.cts = cts;
	}

}
