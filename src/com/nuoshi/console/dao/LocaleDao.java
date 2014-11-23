package com.nuoshi.console.dao;

import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.nuoshi.console.domain.topic.Locale;
import com.nuoshi.console.persistence.read.taofang.topic.LocaleReadMapper;

@Repository
public class LocaleDao {
	@Resource
	private LocaleReadMapper localeReadMapper;
	
	public Locale selLocaleById(int id) {
		return localeReadMapper.selLocaleById(id);
	}
	
	public List<Locale> selChildren(int pid) {
		return localeReadMapper.selChildren(pid);
	}
	
	public List<Locale> selAllLocale(int depth) {
		return localeReadMapper.selAllLocale(depth);
	}
	
	
	/** @author ningtao
	 * 根据城市code 获得地区信息
	 * @param cityCode
	 * @return
	 */
	public List<Locale> getDistLocaleByCityId(int cityId){
			return localeReadMapper.getDistListByCityId(cityId);
	}
	
	/**
	 * 根据首字母获得城市信息
	 * @param fullPath
	 * @param dirName
	 * @param depth
	 * @return
	 */
	public List<Locale> getCityListByChar(@Param("dirName") String dirName,@Param("Depth")int depth){
		return localeReadMapper.getCityListByChar(dirName, depth);
	}

	public Integer getCityIdByName(String cityName) {
		return localeReadMapper.getCityIdByName(cityName);
	}
}
