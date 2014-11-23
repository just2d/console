package com.nuoshi.console.persistence.read.unionhouse.uresale;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.nuoshi.console.domain.estate.UnionLog;
import com.nuoshi.console.domain.resale.Resale;


@SuppressWarnings("rawtypes")
public interface UResaleReadMapper {
	public  HashMap<String, Object>  getResaleById(@Param("id") Integer id);//房源信息
	public  List<HashMap<String, Object>>  getAll58ResaleByPage(HashMap params);//所有的房源信息

	/**
	 * 统计小区二手房数量
	 * @param id
	 * @return
	 */
	public int countByEstateId(@Param("estateId")int id);
	
	
	/**
	 * 获得小区的所有二手房源
	 * 
	 * @param estateId
	 * @param targetId 合并后的小区id
	 * @param targetName 合并后的小区名称
	 * @return
	 */
	public List<UnionLog> getHouseByEstateId(Map paramMap);
	
	public List<Resale> getAllUnionResaleByPage(@Param("conditions")List<String> conditions);
}