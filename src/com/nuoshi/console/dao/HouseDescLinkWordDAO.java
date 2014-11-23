package com.nuoshi.console.dao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.nuoshi.console.dao.HouseDescLinkWordDAO;
import com.nuoshi.console.persistence.write.admin.control.HouseDescLinkWordWriteMapper;
import com.nuoshi.console.persistence.read.admin.control.HouseDescLinkWordReadMapper;
import com.nuoshi.console.domain.control.HouseDescLinkWord;

@Repository
public class HouseDescLinkWordDAO {

	@Resource
	private HouseDescLinkWordWriteMapper houseDescLinkWordWriteMapper;
	
	@Resource
	private HouseDescLinkWordReadMapper houseDescLinkWordReadMapper;
	
	public int add(HouseDescLinkWord houseDescLinkWord){
		return houseDescLinkWordWriteMapper.add(houseDescLinkWord);
	} 
	public int deleteById(int id){
		return houseDescLinkWordWriteMapper.deleteById(id);
	} 
	public HouseDescLinkWord getById(int id){
		return houseDescLinkWordReadMapper.getById(id);
	}
	public List<HouseDescLinkWord> getHouseDescLinkWordList(HouseDescLinkWord searchView) {
		return houseDescLinkWordReadMapper.getHouseDescLinkWordListByPage(searchView);
	}
	public int updateHouseDescLinkWord(HouseDescLinkWord houseDescLinkWord) {
		return houseDescLinkWordWriteMapper.updateHouseDescLinkWord(houseDescLinkWord);
	}
	public boolean isHouseDescLinkWordExist(HouseDescLinkWord houseDescLinkWord) {
		int num = houseDescLinkWordReadMapper.getHouseDescLinkWordNum(houseDescLinkWord);
		if(num > 0 ) return true;
		return false;
	} 
	
}
