package com.nuoshi.console.domain.control;

import java.io.Serializable;
import java.util.Date;

import com.nuoshi.console.common.util.Utilities;


public class HouseDescLinkWord implements Serializable {

	/**
	* 主键
	*/
	private Integer id ;
	/**
	* 城市ID
	*/
	private int cityId ;
	/**
	* 频道 0、全部 1、二手房 2、租房
	*/
	private int channel ;
	/**
	* 关键词
	*/
	private String keyword ;
	/**
	* url
	*/
	private String url ;
	/**
	* 创建时间
	*/
	private Date createTime ;
	private static final long serialVersionUID = 1L;
	
	private String startDate;
	
	private String endDate;
	
	/**
	* 主键
	*/
	public Integer getId() {
			return this.id;
		}
	/**
	* 主键
	*/
	public void setId(Integer id) {
			this.id = id;
		}
	/**
	* 城市ID
	*/
	public int getCityId() {
			return this.cityId;
		}
	/**
	* 城市ID
	*/
	public void setCityId(int cityId) {
			this.cityId = cityId;
		}
	/**
	* 频道 0、全部 1、二手房 2、租房
	*/
	public int getChannel() {
			return this.channel;
		}
	/**
	* 频道 0、全部 1、二手房 2、租房
	*/
	public void setChannel(int channel) {
			this.channel = channel;
		}
	/**
	* 关键词
	*/
	public String getKeyword() {
			return this.keyword;
		}
	/**
	* 关键词
	*/
	public void setKeyword(String keyword) {
			this.keyword = keyword;
		}
	/**
	* url
	*/
	public String getUrl() {
			return this.url;
		}
	/**
	* url
	*/
	public void setUrl(String url) {
			this.url = url;
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
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	
	public String getCreateTimeStr(){
		if(createTime != null){
			try{
				return Utilities.formatDefaultDate(createTime);
			} catch(Exception e) {
				
			}
		}
		return null;
	}
}