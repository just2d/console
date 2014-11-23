package com.nuoshi.console.dao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.nuoshi.console.domain.adv.AdvHouse;
import com.nuoshi.console.domain.adv.AdvHouseSearch;
import com.nuoshi.console.persistence.read.adv.AdvHouseReadMapper;
import com.nuoshi.console.persistence.write.adv.AdvHouseWriterMapper;


@Repository
public class AdvHouseDao {
	@Resource
	private AdvHouseWriterMapper advHouseWriterMapper;
	@Resource
	private AdvHouseReadMapper advHouseReadMapper;
	
	public void addAdvHouse(AdvHouse  house) {
		advHouseWriterMapper.addAdvHouse(house);
	}
	public void updateAdvHouse(AdvHouse  house) {
		advHouseWriterMapper.updateAdvHouse(house);
	}
	public void delAdvHouse(int id) {
		advHouseWriterMapper.delAdvHouse(id);
	}
	
	public  List<AdvHouse>  getAdvHouseByPage(AdvHouseSearch  search){
		 return advHouseReadMapper.getAdvHouseByPage(search);
	}
	public  AdvHouse  getAdvHouseById(int id){
		 return advHouseReadMapper.getAdvHouseById(id);
	}
	public  int  getAdvHouseByCount(AdvHouseSearch  search){
		 return advHouseReadMapper.getAdvHouseByCount(search);
	}
	public List<AdvHouse> getAllAdvHouse(AdvHouseSearch search) {
		return advHouseReadMapper.getAllAdvHouse(search);
	}

}
