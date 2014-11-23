package com.nuoshi.console.domain.agent;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.nuoshi.console.common.util.Utilities;


public class TfAsAccount implements Serializable {

	private Integer id ;
	/**
	* 用户名
	*/
	private String userName ;
	/**
	* 密码
	*/
	private String password ;
	/**
	 * 公司ID
	 */
	private Integer companyId;
	/**
	 * 公司名称
	 */
	private String companyName;
	/**
	* 门店ID
	*/
	private Integer storeId ;
	/**
	* 门店名称
	*/
	private String storeName ;
	/**
	* 门店地址
	*/
	private String storeAddr ;
	/**
	* 门店电话
	*/
	private String telphone ;
	/**
	* 联系人
	*/
	private String contact ;
	private String mobile ;
	/**
	* 简介
	*/
	private String storeDetail ;
	/**
	* 创建时间
	*/
	private Date createTime ;
	/**
	* 登录次数
	*/
	private Integer loginCount ;
	/**
	* 最后登录时间
	*/
	private Date lastLoginTime ;
	/**
	* 登录IP
	*/
	private String loginIp ;
	
	/**
	 * 角色：1-门店账号；2-公司账号；3-片区账号
	 */
	private int role;
	
	private int areaId;
	
	private String areaName;
	
	private static final long serialVersionUID = 1L;

	public Integer getId() {
			return this.id;
		}
	public void setId(Integer id) {
			this.id = id;
		}
	/**
	* 用户名
	*/
	public String getUserName() {
			return this.userName;
		}
	/**
	* 用户名
	*/
	public void setUserName(String userName) {
			this.userName = userName;
		}
	/**
	* 密码
	*/
	public String getPassword() {
			return this.password;
		}
	/**
	* 密码
	*/
	public void setPassword(String password) {
			this.password = password;
		}
	/**
	* 门店ID
	*/
	public Integer getStoreId() {
			return this.storeId;
		}
	/**
	* 门店ID
	*/
	public void setStoreId(Integer storeId) {
			this.storeId = storeId;
		}
	/**
	* 门店名称
	*/
	public String getStoreName() {
			return this.storeName;
		}
	/**
	* 门店名称
	*/
	public void setStoreName(String storeName) {
			this.storeName = storeName;
		}
	/**
	* 门店地址
	*/
	public String getStoreAddr() {
			return this.storeAddr;
		}
	/**
	* 门店地址
	*/
	public void setStoreAddr(String storeAddr) {
			this.storeAddr = storeAddr;
		}
	/**
	* 门店电话
	*/
	public String getTelphone() {
			return this.telphone;
		}
	/**
	* 门店电话
	*/
	public void setTelphone(String telphone) {
			this.telphone = telphone;
		}
	/**
	* 联系人
	*/
	public String getContact() {
			return this.contact;
		}
	/**
	* 联系人
	*/
	public void setContact(String contact) {
			this.contact = contact;
		}
	public String getMobile() {
			return this.mobile;
		}
	public void setMobile(String mobile) {
			this.mobile = mobile;
		}
	/**
	* 简介
	*/
	public String getStoreDetail() {
			return this.storeDetail;
		}
	/**
	* 简介
	*/
	public void setStoreDetail(String storeDetail) {
			this.storeDetail = storeDetail;
		}
	/**
	* 创建时间
	*/
	public Date getCreateTime() {
			return this.createTime;
		}
	/**
	* 创建时间
	*/
	public void setCreateTime(Date createTime) {
			this.createTime = createTime;
		}
	/**
	* 登录次数
	*/
	public Integer getLoginCount() {
			return this.loginCount;
		}
	/**
	* 登录次数
	*/
	public void setLoginCount(Integer loginCount) {
			this.loginCount = loginCount;
		}
	/**
	* 最后登录时间
	*/
	public Date getLastLoginTime() {
		return this.lastLoginTime;
	}
	/**
	 * 最后登录时间
	 */
	public String getLastLoginTimeLabel() {
		return Utilities.formatDefaultDate(this.lastLoginTime);
	}
	
	public String getLastLoginTimeTxt() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
		try {
			return sdf.format(lastLoginTime);
		} catch(Exception e) {
			
		}
		return null;
	}
	
	/**
	* 最后登录时间
	*/
	public void setLastLoginTime(Date lastLoginTime) {
			this.lastLoginTime = lastLoginTime;
		}
	/**
	* 登录IP
	*/
	public String getLoginIp() {
			return this.loginIp;
		}
	/**
	* 登录IP
	*/
	public void setLoginIp(String loginIp) {
			this.loginIp = loginIp;
		}
	public Integer getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public int getRole() {
		return role;
	}
	public void setRole(int role) {
		this.role = role;
	}
	public int getAreaId() {
		return areaId;
	}
	public void setAreaId(int areaId) {
		this.areaId = areaId;
	}
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
}