package com.nuoshi.console.persistence.write.unionhouse.estate;

import com.nuoshi.console.domain.estate.UEstateMapping;

public interface UEstateMappingWriteMapper {

	//添加
	public int add(UEstateMapping uEstateMapping);
	//更新
	public int update(UEstateMapping uEstateMapping);
	//删除
	public int deleteById(int id);
}