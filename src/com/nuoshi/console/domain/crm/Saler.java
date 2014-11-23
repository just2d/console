package com.nuoshi.console.domain.crm;

import java.util.Date;
import java.util.List;

public class Saler {
	// 中文名字
	private String chnName;
	// 城市ID
	private int cityId;
	//城市名称
	private String cityName;
	//当前城市ID
	private int currentCityId;
	// 邮箱
	private String email;
	// 注册时间entry_datetime
	private Date entryDateTime;
	//组
	private int groupId;
	//组名称
	private String groupName;
	// 对应数据库字段id
	private int id;
	// 最后登录时间last_login_date
	private Date lastLoginDate;
	// 登录IP login_ip
	private String loginIP;
	// 电话mobile
	private String mobile;
	// 是否在线online
	private int online;
	// 数据库字段password
	private String password;
	// 角色
	private String role;
	// 角色
	private String roleName;
	// 数据库字段sex
	private int sex;
	
	private String status;
	
	
	//用户管辖的城市Id
	private List<Integer> userCitys;

	// 对应数据库字段user_name
	private String userName;
	
	

	public int getCurrentCityId() {
		return currentCityId;
	}

	public void setCurrentCityId(int currentCityId) {
		this.currentCityId = currentCityId;
	}

	public String getChnName() {
		return chnName;
	}

	public int getCityId() {
		return cityId;
	}

	public String getCityName() {
		return cityName;
	}

	public String getEmail() {
		return email;
	}

	public Date getEntryDateTime() {
		return entryDateTime;
	}

	public int getGroupId() {
		return groupId;
	}

	public String getGroupName() {
		return groupName;
	}

	public int getId() {
		return id;
	}

	public Date getLastLoginDate() {
		return lastLoginDate;
	}

	public String getLoginIP() {
		return loginIP;
	}

	public String getMobile() {
		return mobile;
	}

	public int getOnline() {
		return online;
	}

	public String getPassword() {
		return password;
	}

	public String getRole() {
		return role;
	}

	public String getRoleName() {
		return roleName;
	}

	public int getSex() {
		return sex;
	}

	public String getStatus() {
		return status;
	}

	public List<Integer> getUserCitys() {
		return userCitys;
	}

	public String getUserName() {
		return userName;
	}

	public void setChnName(String chnName) {
		this.chnName = chnName;
	}

	public void setCityId(int cityId) {
		this.cityId = cityId;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setEntryDateTime(Date entryDateTime) {
		this.entryDateTime = entryDateTime;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	public void setLoginIP(String loginIP) {
		this.loginIP = loginIP;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public void setOnline(int online) {
		this.online = online;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setUserCitys(List<Integer> userCitys) {
		this.userCitys = userCitys;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
}
