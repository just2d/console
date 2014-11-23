package com.nuoshi.console.persistence.read.admin.control;

import java.util.List;

import com.nuoshi.console.domain.control.HouseDescLinkWord;

public interface HouseDescLinkWordReadMapper {

	//根据ID查找
	public HouseDescLinkWord getById(int id);

	public List<HouseDescLinkWord> getHouseDescLinkWordListByPage(HouseDescLinkWord searchView);

	public int getHouseDescLinkWordNum(HouseDescLinkWord houseDescLinkWord);
  
}