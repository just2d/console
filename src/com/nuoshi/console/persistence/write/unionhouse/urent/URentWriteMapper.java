package com.nuoshi.console.persistence.write.unionhouse.urent;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.nuoshi.console.domain.estate.UnionLog;

@SuppressWarnings("rawtypes")
public interface URentWriteMapper {
	public int deleteRent(int rentid);
	
	/**
	 * 合并小区
	 * @author ningt
	 * @param praramMap
	 */
	public void unionEstate(Map praramMap);
	
	/**
	 * 更新房源小区id（恢复到合并前的状态）
	 * @param condition
	 */
	public void updateEstateId(UnionLog condition);
	
	public int changeVisible(@Param("houseId")int houseId, @Param("sourceId")int sourceId, @Param("status")int status);
}