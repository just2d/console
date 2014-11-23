package com.nuoshi.console.domain.estate;

import java.io.Serializable;
import java.util.Date;

import com.nuoshi.console.common.util.Utilities;


public class UEstateMapping implements Serializable {

	private Integer id ;
	/**
	* 淘房小区ID
	*/
	private Integer estateId ;
	/**
	* 淘房小区的区域ID
	*/
	private Integer estateDistId ;
	/**
	* 淘房小区的地标ID
	*/
	private Integer estateBlockId ;
	/**
	* 联盟的小区ID
	*/
	private Integer uEstateId ;
	/**
	* 来源标识
	*/
	private Integer sourceId ;
	/**
	* 创建时间
	*/
	private Date createTime ;
	/**
	* 最后更新时间
	*/
	private Date lastUpdateTime ;
	/**
	* 最后修改人ID
	*/
	private Integer lastUpdateUserId ;
	
	//tf小区名
	private String estateName;
	
	private static final long serialVersionUID = 1L;
	
	
	//以下属性只用作显示
	//外部小区名
	private String uEstateName;
	
	private String createTimeStr;
	
	private String lastUpdateTimeStr;

	public Integer getId() {
			return this.id;
		}
	public void setId(Integer id) {
			this.id = id;
		}
	/**
	* 淘房小区ID
	*/
	public Integer getEstateId() {
			return this.estateId;
		}
	/**
	* 淘房小区ID
	*/
	public void setEstateId(Integer estateId) {
			this.estateId = estateId;
		}
	/**
	* 淘房小区的区域ID
	*/
	public Integer getEstateDistId() {
			return this.estateDistId;
		}
	/**
	* 淘房小区的区域ID
	*/
	public void setEstateDistId(Integer estateDistId) {
			this.estateDistId = estateDistId;
		}
	/**
	* 淘房小区的地标ID
	*/
	public Integer getEstateBlockId() {
			return this.estateBlockId;
		}
	/**
	* 淘房小区的地标ID
	*/
	public void setEstateBlockId(Integer estateBlockId) {
			this.estateBlockId = estateBlockId;
		}
	/**
	* 来源标识
	*/
	public Integer getSourceId() {
			return this.sourceId;
		}
	/**
	* 来源标识
	*/
	public void setSourceId(Integer sourceId) {
			this.sourceId = sourceId;
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
	* 最后更新时间
	*/
	public Date getLastUpdateTime() {
			return this.lastUpdateTime;
		}
	/**
	* 最后更新时间
	*/
	public void setLastUpdateTime(Date lastUpdateTime) {
			this.lastUpdateTime = lastUpdateTime;
		}
	/**
	* 最后修改人ID
	*/
	public Integer getLastUpdateUserId() {
			return this.lastUpdateUserId;
		}
	/**
	* 最后修改人ID
	*/
	public void setLastUpdateUserId(Integer lastUpdateUserId) {
			this.lastUpdateUserId = lastUpdateUserId;
		}
	public String getEstateName() {
		return estateName;
	}
	public void setEstateName(String estateName) {
		this.estateName = estateName;
	}
	public String getuEstateName() {
		return uEstateName;
	}
	public void setuEstateName(String uEstateName) {
		this.uEstateName = uEstateName;
	}
	public Integer getuEstateId() {
		return uEstateId;
	}
	public void setuEstateId(Integer uEstateId) {
		this.uEstateId = uEstateId;
	}
	public String getCreateTimeStr() {
		if(createTime != null){
			return Utilities.formatDefaultDate(createTime);
		}
		return createTimeStr;
	}
	public String getLastUpdateTimeStr() {
		if(lastUpdateTime != null){
			return Utilities.formatDefaultDate(lastUpdateTime);
		}
		return lastUpdateTimeStr;
	}
}