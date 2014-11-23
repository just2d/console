package com.nuoshi.console.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.nuoshi.console.domain.estate.EstateMd5;
import com.nuoshi.console.persistence.read.taofang.estate.EstateMd5ReadMapper;
import com.nuoshi.console.persistence.write.taofang.estate.EstateMd5WriteMapper;

@Service
public class EstateMd5Service {

	@Resource
	private EstateMd5ReadMapper estateMd5ReadMapper;
	@Resource
	private EstateMd5WriteMapper estateMd5WriteMapper;

	/**
	 * 判断Md5值是否存在
	 * 
	 * @param md5
	 * @param category
	 * @param estateId
	 * @return
	 */
	public EstateMd5 queryMd5Exist(String md5, String category, Integer estateId) {
		return estateMd5ReadMapper.queryMd5Exist(md5, category, estateId);
	}

	/**
	 * 存储小区图md5信息
	 * 
	 * @param category
	 * @param estateId
	 * @param md5
	 */
	public void saveEstateMd5Info(String category, Integer estateId, String md5, Integer photoId) {
		estateMd5WriteMapper.saveEstateMd5Info(category, estateId, md5, photoId);
	}
	
}
