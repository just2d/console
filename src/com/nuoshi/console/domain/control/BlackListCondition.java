package com.nuoshi.console.domain.control;

/**
 * 用户黑名单查询条件
 * 
 */
public class BlackListCondition {
	private String id;
	/**
	 * 经纪人电话或者登录名
	 */
	private String loginName;
	/**
	 * 类别    1 普通用户  2 经纪人
	 */
	private String userType;
	/**
	 * 列表类型   经纪人，问答（101经纪人，102问答）
	 */ 
	private String listType;
	/**
	 * 原因 
	 */
	private String reason;
	/**
	 * 备注说明
	 */
	private String comments;
	/**
	 * 状态   是否生效（1生效，0不生效）
	 */
	private String status;
	/**
	 * 黑名单用户的id
	 */
	private String blackUserId;
	/**
	 * 黑名单用户名
	 */
	private String blackUserName;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public String getListType() {
		return listType;
	}
	public void setListType(String listType) {
		this.listType = listType;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getBlackUserId() {
		return blackUserId;
	}
	public void setBlackUserId(String blackUserId) {
		this.blackUserId = blackUserId;
	}
	public String getBlackUserName() {
		return blackUserName;
	}
	public void setBlackUserName(String blackUserName) {
		this.blackUserName = blackUserName;
	}
	
	

}