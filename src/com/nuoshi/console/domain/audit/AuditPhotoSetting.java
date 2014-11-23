package com.nuoshi.console.domain.audit;

public class AuditPhotoSetting {
	private int cityId;
	private float illegalRate;
	private int dayMaxPhotoCount;
	private int auditCount;
	private int timeRule;
	public int getCityId() {
		return cityId;
	}
	public void setCityId(int cityId) {
		this.cityId = cityId;
	}
	public float getIllegalRate() {
		return illegalRate;
	}
	public void setIllegalRate(float illegalRate) {
		this.illegalRate = illegalRate;
	}
	public int getDayMaxPhotoCount() {
		return dayMaxPhotoCount;
	}
	public void setDayMaxPhotoCount(int dayMaxPhotoCount) {
		this.dayMaxPhotoCount = dayMaxPhotoCount;
	}
	public int getAuditCount() {
		return auditCount;
	}
	public void setAuditCount(int auditCount) {
		this.auditCount = auditCount;
	}
	public int getTimeRule() {
		return timeRule;
	}
	public void setTimeRule(int timeRule) {
		this.timeRule = timeRule;
	}
	

}
