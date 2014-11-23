package com.nuoshi.console.domain.control;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author wanghongmeng
 * wangjh 2012-08-15改
 * 
 */
public class BlackList {
	private int id;
	/**
	 * 经纪人电话或者登录名
	 */
	private String loginName;
	/**
	 * 类别    1 普通用户  2 经纪人
	 */
	private int userType;
	/**
	 * 列表类型   经纪人，问答（101经纪人，102问答）
	 */ 
	private int listType;
	/**
	 * 原因 
	 */
	private String reason;
	/**
	 * 备注说明
	 */
	private String comments;
	/**
	 * 添加人
	 */
	private int lastEntryId;
	/**
	 * 添加人姓名
	 */
	private String lastEntryName;
	/**
	 * 添加时间
	 */
	private Date entryDate;
	/**
	 * 状态   是否生效（1生效，0不生效）
	 */
	private int status;
	/**
	 * 黑名单用户的id
	 */
	private int blackUserId;
	/**
	 * 黑名单用户名
	 */
	private String blackUserName;
	/**
	 * 更新时间
	 */
	private Date updateDate;



	/**
	 * @return 添加日期，按照yyyy-mm-dd hh:mm的格式返回
	 */
	public String getEntryDate() {
		if (null == entryDate) {
			return "";
		}
		SimpleDateFormat sdf = new SimpleDateFormat();
		sdf.applyPattern("yyyy-MM-dd HH:mm");
		return sdf.format(entryDate);
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
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
	public int getLastEntryId() {
		return lastEntryId;
	}
	public void setLastEntryId(int lastEntryId) {
		this.lastEntryId = lastEntryId;
	}
	public String getLastEntryName() {
		return lastEntryName;
	}
	public void setLastEntryName(String lastEntryName) {
		this.lastEntryName = lastEntryName;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getBlackUserId() {
		return blackUserId;
	}
	public void setBlackUserId(int blackUserId) {
		this.blackUserId = blackUserId;
	}
	public String getBlackUserName() {
		return blackUserName;
	}
	public void setBlackUserName(String blackUserName) {
		this.blackUserName = blackUserName;
	}
	public void setEntryDate(Date entryDate) {
		this.entryDate = entryDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	/**
	 * @return 添加日期，按照yyyy-mm-dd hh:mm的格式返回
	 */
	public String getUpdateDate() {
		if (null == updateDate) {
			return "";
		}
		SimpleDateFormat sdf = new SimpleDateFormat();
		sdf.applyPattern("yyyy-MM-dd HH:mm");
		return sdf.format(updateDate);
	}
	public int getUserType() {
		return userType;
	}
	public void setUserType(int userType) {
		this.userType = userType;
	}
	public int getListType() {
		return listType;
	}
	public void setListType(int listType) {
		this.listType = listType;
	}
	


}