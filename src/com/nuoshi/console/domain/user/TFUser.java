package com.nuoshi.console.domain.user;

import java.io.Serializable;
import java.util.Date;

public class TFUser implements Serializable {
    private static final long serialVersionUID = 3014395015363770050L;

    public static final int ONLINE_NO = 0;
    public static final int ONLINE_YES = 1;

    public static final int ROLE_USER = 1;
    public static final int ROLE_AGENT = 2;
    
    private int id;
    private String userName;
    private String nickName;
    private String password;
    private String passwordMd5;
    private int role;
    private String name;
    private int sex;
    private String email;
    private String mobile;
    private int cityId;
    private int distId;
    private int blockId;
    private int online;
    private String regIp;
    private String openId;
    private int regSource;
    private String headTiny;
    private String headBrowse;
    private Date createTime;
    private Date updateTime;
    private int loginCount;
    private Date loginTime;
    private String loginIp;
    
    /**
     * 没有读取的站内信数量
     */
    private int msgInCount;
    
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
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPasswordMd5() {
		return passwordMd5;
	}
	public void setPasswordMd5(String passwordMd5) {
		this.passwordMd5 = passwordMd5;
	}
	public int getRole() {
		return role;
	}
	public void setRole(int role) {
		this.role = role;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getSex() {
		return sex;
	}
	public void setSex(int sex) {
		this.sex = sex;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public int getCityId() {
		return cityId;
	}
	public void setCityId(int cityId) {
		this.cityId = cityId;
	}
	public int getOnline() {
		return online;
	}
	public void setOnline(int online) {
		this.online = online;
	}
	public String getRegIp() {
		return regIp;
	}
	public void setRegIp(String regIp) {
		this.regIp = regIp;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public int getRegSource() {
		return regSource;
	}
	public void setRegSource(int regSource) {
		this.regSource = regSource;
	}
	public String getHeadTiny() {
		return headTiny;
	}
	public void setHeadTiny(String headTiny) {
		this.headTiny = headTiny;
	}
	public String getHeadBrowse() {
		return headBrowse;
	}
	public void setHeadBrowse(String headBrowse) {
		this.headBrowse = headBrowse;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public int getLoginCount() {
		return loginCount;
	}
	public void setLoginCount(int loginCount) {
		this.loginCount = loginCount;
	}
	public Date getLoginTime() {
		return loginTime;
	}
	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}
	public String getLoginIp() {
		return loginIp;
	}
	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}
    
	public boolean isAgent() {
		return this.role == ROLE_AGENT;
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
	public int getMsgInCount() {
		return msgInCount;
	}
	public void setMsgInCount(int msgInCount) {
		this.msgInCount = msgInCount;
	}
	
}
