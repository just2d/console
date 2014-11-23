package com.nuoshi.console.domain.stat;

import java.text.SimpleDateFormat;

import org.apache.commons.lang.StringUtils;

public class AuditSearch  implements Comparable {
// 查询参数 
	private String startDate;
	private String endDate;
	/** 城市id */
	private Integer cityId;
	/**  统计类型:0全部，2二手房，1租房  */
	private String rateType;
	/** 审核状态-1全部，0待审核，1通过，2拒绝	 */
	private Integer rateStatus;
	/** 查询类型 :0经纪人手机；1经纪人姓名；2经纪人id */
	private String rateAgentType;
	/** 查询内容 */
	private String rateAgent;
	
//类型及结果转换
	private String sqlDateStart;
	private String sqlDateEnd;
	private String pageDate;
	
//结果集
	private String cityName;
	private String pubDate;
	private int baseCount;//基本数目
	private int innerCount;//室内图数目
	private int estateCount;//小区图数目
	private int layoutCount;//户型图数目
	
	/** 查询任务列表中的数目 */
	/** 1出租房；2二手房 */
	private String houseType;
	private int auditTaskCount;
	
	private String everyMonth;
	
	public String getRateAgentType() {
		return rateAgentType;
	}
	public void setRateAgentType(String rateAgentType) {
		this.rateAgentType = rateAgentType;
	}
	public String getRateAgent() {
		return rateAgent;
	}
	public void setRateAgent(String rateAgent) {
		this.rateAgent = rateAgent;
	}
	public String getPageDate() {
		return pageDate;
	}
	public void setPageDate(String pageDate) {
		this.pageDate = pageDate;
	}
	public int getAuditTaskCount() {
		return auditTaskCount;
	}
	public void setAuditTaskCount(int auditTaskCount) {
		this.auditTaskCount = auditTaskCount;
	}
	public String getHouseType() {
		return houseType;
	}
	public void setHouseType(String houseType) {
		this.houseType = houseType;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getPubDate() {
		return pubDate;
	}
	public void setPubDate(String pubDate) {
		this.pubDate = pubDate;
	}
	public int getBaseCount() {
		return baseCount;
	}
	public void setBaseCount(int baseCount) {
		this.baseCount = baseCount;
	}
	public int getInnerCount() {
		return innerCount;
	}
	public void setInnerCount(int innerCount) {
		this.innerCount = innerCount;
	}
	public int getEstateCount() {
		return estateCount;
	}
	public void setEstateCount(int estateCount) {
		this.estateCount = estateCount;
	}
	public int getLayoutCount() {
		return layoutCount;
	}
	public void setLayoutCount(int layoutCount) {
		this.layoutCount = layoutCount;
	}
	public String getStartDate() {
		if(StringUtils.isBlank(startDate)){
			return "";
		}
		startDate = startDate.replaceAll("-", "");
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		if(StringUtils.isBlank(endDate)){
			return "";
		}
		endDate = endDate.replaceAll("-", "");
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public Integer getCityId() {
		return cityId;
	}
	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}
	public String getRateType() {
		return rateType;
	}
	public void setRateType(String rateType) {
		this.rateType = rateType;
	}
	public Integer getRateStatus() {
		return rateStatus;
	}
	public void setRateStatus(Integer rateStatus) {
		this.rateStatus = rateStatus;
	}
	public String getSqlDateStart() {
		return sqlDateStart;
	}
	public String getSqlDateEnd() {
		return sqlDateEnd;
	}
	
	public void setSqlDateStart(String sqlDateStart) {
		this.sqlDateStart = sqlDateStart;
	}
	public void setSqlDateEnd(String sqlDateEnd) {
		this.sqlDateEnd = sqlDateEnd;
	}
	@Override
	public int compareTo(Object o) {
		if(o==null) return 0;
	    AuditSearch other = (AuditSearch)o;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			if(StringUtils.isBlank(other.getPageDate()))return -1;
			if(StringUtils.isBlank(this.getPageDate()))return 0;
			long l1 = sdf.parse(other.getPageDate()).getTime();
			long l2 = sdf.parse(this.getPageDate()).getTime();
			return (int)(l1-l2);
		} catch (Exception e) {
			e.printStackTrace();
		}
	    
	    
		return 0;
	}
	public String getEveryMonth() {
		return everyMonth;
	}
	public void setEveryMonth(String everyMonth) {
		this.everyMonth = everyMonth;
	}
	
}
