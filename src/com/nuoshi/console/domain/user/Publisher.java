package com.nuoshi.console.domain.user;


/**
 * 发布问题和论坛的人儿，供seo选择的人儿
 * @author wangjh
 *
 */
public class Publisher  {

    public static final int ROLE_USER = 1;
    public static final int ROLE_AGENT = 2;
    
    private int id;
    private int userId;
    /**
     * 用户名，经纪人为真实姓名
     */
    private String userName;
    /**
     * 帐号
     */
    private String loginName;
    /**
     * 角色
     */
    private int role;
    /**
     * 城市id
     */
    private int cityId;
    /**
     * 类型：0为所有，1为论坛，2为问答
     */
    private int type;
    /**
     * 状态：0为可用
     */
    private int status;
    
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public int getRole() {
		return role;
	}
	public void setRole(int role) {
		this.role = role;
	}
	public int getCityId() {
		return cityId;
	}
	public void setCityId(int cityId) {
		this.cityId = cityId;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
}
