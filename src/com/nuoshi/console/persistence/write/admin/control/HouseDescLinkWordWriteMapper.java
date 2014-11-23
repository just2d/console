package com.nuoshi.console.persistence.write.admin.control;

import com.nuoshi.console.domain.control.HouseDescLinkWord;

public interface HouseDescLinkWordWriteMapper {

	//添加
	public int add(HouseDescLinkWord houseDescLinkWord);
	//删除
	public int deleteById(int id);
	public int updateHouseDescLinkWord(HouseDescLinkWord houseDescLinkWord);
  
}