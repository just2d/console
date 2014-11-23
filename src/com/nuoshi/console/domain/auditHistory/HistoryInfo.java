package com.nuoshi.console.domain.auditHistory;

import java.util.List;

import com.nuoshi.console.domain.agent.HousePhoto;


/**
 * 历史信息查看时房源审核内容
 * @author wangjh
 *
 */
public class HistoryInfo extends SubAuditHistory{
	/**
	 * 历史信息显示图片的地址
	 */
	private String photoUrl;
	/**
	 * 图片描述
	 */
	private String photoDescribe;
	/**
	 * 审核最终结果
	 */
	private Integer finalOutcome;
	/**
	 * 房源类型
	 */
	private Integer houseType;
	
	private Integer reResult;
	
	/** 具体房源对象 */
	private Object houseObj;
	
	private int photoCount;
	
	private List<HousePhoto> layoutImgUrls;// 普通户型图
	private List<HousePhoto> estateImgUrls;// 普通小区图
	private List<HousePhoto> innerImgUrls;// 普通室内图
	
	
	public Object getHouseObj() {
		return houseObj;
	}
	public void setHouseObj(Object houseObj) {
		this.houseObj = houseObj;
	}
	public List<HousePhoto> getLayoutImgUrls() {
		return layoutImgUrls;
	}
	public void setLayoutImgUrls(List<HousePhoto> layoutImgUrls) {
		this.layoutImgUrls = layoutImgUrls;
	}
	public List<HousePhoto> getEstateImgUrls() {
		return estateImgUrls;
	}
	public void setEstateImgUrls(List<HousePhoto> estateImgUrls) {
		this.estateImgUrls = estateImgUrls;
	}
	public List<HousePhoto> getInnerImgUrls() {
		return innerImgUrls;
	}
	public void setInnerImgUrls(List<HousePhoto> innerImgUrls) {
		this.innerImgUrls = innerImgUrls;
	}
	public String getPhotoUrl() {
		return photoUrl;
	}
	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}
	public String getPhotoDescribe() {
		return photoDescribe;
	}
	public void setPhotoDescribe(String photoDescribe) {
		this.photoDescribe = photoDescribe;
	}
	public Integer getFinalOutcome() {
		return finalOutcome;
	}
	public void setFinalOutcome(Integer finalOutcome) {
		this.finalOutcome = finalOutcome;
	}
	public Integer getHouseType() {
		return houseType;
	}
	public void setHouseType(Integer houseType) {
		this.houseType = houseType;
	}
	public Integer getReResult() {
		return reResult;
	}
	public void setReResult(Integer reResult) {
		this.reResult = reResult;
	}
	public int getPhotoCount() {
		return photoCount;
	}
	public void setPhotoCount(int photoCount) {
		this.photoCount = photoCount;
	}

}
