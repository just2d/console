package com.nuoshi.console.persistence.read.unionhouse.urent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.nuoshi.console.domain.estate.UnionLog;
import com.nuoshi.console.domain.rent.Rent;

@SuppressWarnings("rawtypes")
public interface URentReadMapper {
	public  HashMap<String, Object>  getRentById(@Param("id") Integer id);//房源信息
	public  List<HashMap<String, Object>>  getAll58RentByPage(HashMap params);//所有的房源信息
	

	/**
	 * 统计小区租房数量
	 * @param id
	 * @return
	 */
	public int countByEstateId(@Param("estateId")int id);
	
	/**
	 * 获得小区租房
	 * @param paramMap
	 * @return
	 */
	public List<UnionLog> getRentByEstateId(Map paramMap);
	
	public List<Rent> getAllUnionRentByPage(@Param("conditions")List<String> conditions);

}