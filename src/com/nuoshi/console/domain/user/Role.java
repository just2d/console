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
public class Role implements Serializable {
	private static final long serialVersionUID = 8123256021446381792L;
	private Integer id;
	private String name;//角色名称
	private String code;//角色编码
	private Integer status;//状态
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getStatusLabel() {
		if(this.status==1){
			return "有效";
		}else{
			return "无效";
		}
		
	}
}
