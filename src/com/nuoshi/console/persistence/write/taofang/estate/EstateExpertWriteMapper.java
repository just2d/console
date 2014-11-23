package com.nuoshi.console.persistence.write.taofang.estate;

import org.apache.ibatis.annotations.Param;

import com.nuoshi.console.domain.estate.EstateExpert;

public interface EstateExpertWriteMapper {

	//添加
	public int add(EstateExpert estateExpert);
	//删除
	public int deleteById(@Param("id")int id);
	//更新
	public void update(EstateExpert estateExpert);
  
}