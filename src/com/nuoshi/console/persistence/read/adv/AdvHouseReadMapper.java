package com.nuoshi.console.persistence.read.adv;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.nuoshi.console.domain.adv.AdvHouse;
import com.nuoshi.console.domain.adv.AdvHouseSearch;

public interface AdvHouseReadMapper {
	
	public  List<AdvHouse>  getAdvHouseByPage(AdvHouseSearch  search);
	
	public  List<AdvHouse>  getAllAdvHouse(AdvHouseSearch  search);
	
	@Select("select  *  from  advertise_house   where id=#{id}")
	public  AdvHouse  getAdvHouseById(@Param("id") int id);
	
	public  int  getAdvHouseByCount(AdvHouseSearch  search);
}
