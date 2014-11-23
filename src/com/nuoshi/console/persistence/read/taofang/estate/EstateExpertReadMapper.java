package com.nuoshi.console.persistence.read.taofang.estate;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.nuoshi.console.domain.estate.EstateExpert;

public interface EstateExpertReadMapper {

	//根据ID查找
	public EstateExpert getById(int id);

	public List<EstateExpert> getEstateExpertByPage(@Param("cityId")Integer cityId,@Param("agentId") Integer agentId,@Param("agentName") String agentName,
			@Param("agentPhone")String agentPhone,@Param("estateName") String estateName);

	public Integer getExpertCountByEstateId(@Param("estateId")Integer estateId);

	public EstateExpert getExpertByEstateId(EstateExpert estateExpert);

	public List<EstateExpert> getEstateExpertDownload(@Param("cityId")Integer cityId,@Param("agentId") Integer agentId,@Param("agentName") String agentName,
			@Param("agentPhone")String agentPhone,@Param("estateName") String estateName);

  
}