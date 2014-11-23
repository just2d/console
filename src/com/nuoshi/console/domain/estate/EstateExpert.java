package com.nuoshi.console.domain.estate;

import java.io.Serializable;
import java.util.Date;


public class EstateExpert implements Serializable {

	private Integer id ;
	/**
	* 小区id
	*/
	private Integer estateId ;
	/**
	* 小区名
	*/
	private String estateName ;
	/**
	* 类型;1:金牌;2:银牌;
	*/
	private Byte expertType ;
	/**
	* 成为专家是今年的第几个星期(时分秒)
	*/
	private Date startTime ;
	/**
	* 专家结束时间(时分秒)
	*/
	private Date endTime ;
	/**
	* 经纪人ID
	*/
	private Integer agentId ;
	/**
	* 经纪人名字
	*/
	private String agentName ;
	/**
	* 电话号码
	*/
	private String agentPhone ;
	/**
	* 被谁推荐?1:机器自动;其他:后台审核人员ID
	*/
	private Integer assignedBy ;
	private static final long serialVersionUID = 1L;
	
	
	private Integer cityId;
	
	private String cityName;
	
	private Integer distId;
	
	private String distName;
	
	private Integer expertCount;
	
	private String pinyin;
	
	private String startTimeStr;
	
	private String endTimeStr;

	public Integer getId() {
			return this.id;
		}
	public void setId(Integer id) {
			this.id = id;
		}
	/**
	* 小区id
	*/
	public Integer getEstateId() {
			return this.estateId;
		}
	/**
	* 小区id
	*/
	public void setEstateId(Integer estateId) {
			this.estateId = estateId;
		}
	/**
	* 小区名
	*/
	public String getEstateName() {
			return this.estateName;
		}
	/**
	* 小区名
	*/
	public void setEstateName(String estateName) {
			this.estateName = estateName;
		}
	/**
	* 类型;1:金牌;2:银牌;
	*/
	public Byte getExpertType() {
			return this.expertType;
		}
	/**
	* 类型;1:金牌;2:银牌;
	*/
	public void setExpertType(Byte expertType) {
			this.expertType = expertType;
		}
	/**
	* 成为专家是今年的第几个星期(时分秒)
	*/
	public Date getStartTime() {
			return this.startTime;
		}
	/**
	* 成为专家是今年的第几个星期(时分秒)
	*/
	public void setStartTime(Date startTime) {
			this.startTime = startTime;
		}
	/**
	* 专家结束时间(时分秒)
	*/
	public Date getEndTime() {
			return this.endTime;
		}
	/**
	* 专家结束时间(时分秒)
	*/
	public void setEndTime(Date endTime) {
			this.endTime = endTime;
		}
	/**
	* 经纪人ID
	*/
	public Integer getAgentId() {
			return this.agentId;
		}
	/**
	* 经纪人ID
	*/
	public void setAgentId(Integer agentId) {
			this.agentId = agentId;
		}
	/**
	* 经纪人名字
	*/
	public String getAgentName() {
			return this.agentName;
		}
	/**
	* 经纪人名字
	*/
	public void setAgentName(String agentName) {
			this.agentName = agentName;
		}
	/**
	* 电话号码
	*/
	public String getAgentPhone() {
			return this.agentPhone;
		}
	/**
	* 电话号码
	*/
	public void setAgentPhone(String agentPhone) {
			this.agentPhone = agentPhone;
		}
	/**
	* 被谁推荐?1:机器自动;其他:后台审核人员ID
	*/
	public Integer getAssignedBy() {
			return this.assignedBy;
		}
	/**
	* 被谁推荐?1:机器自动;其他:后台审核人员ID
	*/
	public void setAssignedBy(Integer assignedBy) {
			this.assignedBy = assignedBy;
		}
	public Integer getCityId() {
		return cityId;
	}
	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public Integer getDistId() {
		return distId;
	}
	public void setDistId(Integer distId) {
		this.distId = distId;
	}
	public String getDistName() {
		return distName;
	}
	public void setDistName(String distName) {
		this.distName = distName;
	}
	public Integer getExpertCount() {
		return expertCount;
	}
	public void setExpertCount(Integer expertCount) {
		this.expertCount = expertCount;
	}
	public String getPinyin() {
		return pinyin;
	}
	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}
	public String getStartTimeStr() {
		return startTimeStr;
	}
	public void setStartTimeStr(String startTimeStr) {
		this.startTimeStr = startTimeStr;
	}
	public String getEndTimeStr() {
		return endTimeStr;
	}
	public void setEndTimeStr(String endTimeStr) {
		this.endTimeStr = endTimeStr;
	}
}