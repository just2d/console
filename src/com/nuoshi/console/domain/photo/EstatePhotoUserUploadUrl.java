package com.nuoshi.console.domain.photo;

import com.taofang.biz.domain.estate.EstatePhotoUserUpload;
import com.taofang.biz.local.EstatePhotoUserUploadUrlUtil;

public class EstatePhotoUserUploadUrl{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8224315587052019701L;
	
	public EstatePhotoUserUploadUrl(EstatePhotoUserUpload photo){
		this.photo =photo;
	}
	
	private EstatePhotoUserUpload photo;
	
	
	private String smallPhotoUrl;
	
	private String originalPhotoUrl;

	public String getSmallPhotoUrl() {
		return EstatePhotoUserUploadUrlUtil.getSmallPhotoUrl(photo);
	}

	public void setSmallPhotoUrl(String smallPhotoUrl) {
		this.smallPhotoUrl = smallPhotoUrl;
	}

	public String getOriginalPhotoUrl() {
		return EstatePhotoUserUploadUrlUtil.getOriginalPhotoUrl(photo);
	}

	public void setOriginalPhotoUrl(String originalPhotoUrl) {
		this.originalPhotoUrl = originalPhotoUrl;
	}

	public EstatePhotoUserUpload getPhoto() {
		return photo;
	}

	public void setPhoto(EstatePhotoUserUpload photo) {
		this.photo = photo;
	}

}
