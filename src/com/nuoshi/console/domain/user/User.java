package com.nuoshi.console.domain.user;

import java.io.Serializable;
import java.util.Date;

/**
 * smc <b>function:</b>管理员
 * 
 * @author lizhenmin
 * @createDate 2011 Jul 20, 2011 2:16:20 PM
 * @email lizm@taofang.com
 * @version 1.0
 */
public class User implements Serializable {
	private static final long serialVersionUID = 8123256021446381792L;
	// 对应数据库字段id
	private int id;
	// 对应数据库字段user_name
	private String userName;
	// 数据库字段password
	private String password;
	// 数据库字段sex
	private int sex;
	// 电话mobile
	private String mobile;
	// 注册时间entry_datetime
	private Date entryDateTime;
	// 最后登录时间last_login_date
	private Date lastLoginDate;
	// 是否在线online
	private int online;
	// 登录IP login_ip
	private String loginIP;
	// 邮箱
	private String email;
	// 中文名字
	private String chnName;
	// 角色
	private String role;
	// 角色
	private String roleName;
	// 用户问题数量
	private Integer questions;
	// 用户答案数量
	private Integer answers;
	
	
	public Integer getQuestions() {
		return questions;
	}

	public void setQuestions(Integer questions) {
		this.questions = questions;
	}

	public Integer getAnswers() {
		return answers;
	}

	public void setAnswers(Integer answers) {
		this.answers = answers;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	// 城市ID
	private int cityId;

	private String status;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Date getEntryDateTime() {
		return entryDateTime;
	}

	public void setEntryDateTime(Date entryDateTime) {
		this.entryDateTime = entryDateTime;
	}

	public Date getLastLoginDate() {
		return lastLoginDate;
	}

	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	public int getOnline() {
		return online;
	}

	public void setOnline(int online) {
		this.online = online;
	}

	public String getLoginIP() {
		return loginIP;
	}

	public void setLoginIP(String loginIP) {
		this.loginIP = loginIP;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getChnName() {
		return chnName;
	}

	public void setChnName(String chnName) {
		this.chnName = chnName;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public int getCityId() {
		return cityId;
	}

	public void setCityId(int cityId) {
		this.cityId = cityId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
