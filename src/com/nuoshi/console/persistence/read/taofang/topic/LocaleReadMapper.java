package com.nuoshi.console.persistence.read.taofang.topic;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.nuoshi.console.domain.topic.Locale;

public interface LocaleReadMapper {
	public Locale selLocaleById(int id);
	
	public List<Locale> selChildren(int pid);
	
	public List<Locale> selAllLocale(int depth);
	
	/** @author ningtao
	 * 根据城市code 获得地区信息
	 * @param cityCode
	 * @return
	 */
	@Select("SELECT DirName as code,p.LocalName as name, p.LocalID as id FROM t_local p where p.pid=#{pid}")
	public List<Locale> getDistListByCityId(@Param("pid")int cityId);
	
	/**
	 * 根据首字母获得城市信息
	 * @param fullPath
	 * @param dirName
	 * @param depth
	 * @return
	 */
	public List<Locale> getCityListByChar(@Param("dirName") String dirName,@Param("Depth")int depth);

	public Integer getCityIdByName(@Param("cityName")String cityName);
	
}
