package com.nuoshi.console.domain.control;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class LinkWord {
	private int id;
	private String keyword;
	private String url;
	private Timestamp createTime;
	
	private String startDate;
	
	private String endDate;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
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
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try{
			return sdf.format(createTime);
		} catch(Exception e) {
			
		}
		return null;
	}
}
