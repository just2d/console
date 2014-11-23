package com.nuoshi.console.domain.version;

import java.io.Serializable;


public class ProjectVersion implements Serializable {

	private Integer id ;
	/**
	* 项目名称
	*/
	private String name ;
	/**
	* 刷新版本号的路径
	*/
	private String versionUrl ;
	private static final long serialVersionUID = 1L;

	public Integer getId() {
			return this.id;
		}
	public void setId(Integer id) {
			this.id = id;
		}
	/**
	* 项目名称
	*/
	public String getName() {
			return this.name;
		}
	/**
	* 项目名称
	*/
	public void setName(String name) {
			this.name = name;
		}
	/**
	* 刷新版本号的路径
	*/
	public String getVersionUrl() {
			return this.versionUrl;
		}
	/**
	* 刷新版本号的路径
	*/
	public void setVersionUrl(String versionUrl) {
			this.versionUrl = versionUrl;
		}
}