package com.nuoshi.console.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.nuoshi.console.dao.AdvHouseDao;
import com.nuoshi.console.domain.adv.AdvHouse;
import com.nuoshi.console.domain.adv.AdvHouseSearch;
@Service
public class AdvHouseService {
	protected static Logger LOG = Logger.getLogger(AdvHouseService.class);
	@Resource
	private AdvHouseDao  advHouseDao;
	
	public void addAdvHouse(AdvHouse  house) {
		try {
			advHouseDao.addAdvHouse(house);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void updateAdvHouse(AdvHouse  house) {
		advHouseDao.updateAdvHouse(house);
	}
	public void delAdvHouse(int id) {
		advHouseDao.delAdvHouse(id);
	}
	public  List<AdvHouse>  getAdvHouseByPage(AdvHouseSearch  search){
		 return advHouseDao.getAdvHouseByPage(search);
	}
	public  List<AdvHouse>  getAllAdvHouse(AdvHouseSearch  search){
		 return advHouseDao.getAllAdvHouse(search);
	}
	public  int  getAdvHouseByCount(AdvHouseSearch  search){
		return advHouseDao.getAdvHouseByCount(search);
	}
	public  AdvHouse  getAdvHouseById(int id){
		 return advHouseDao.getAdvHouseById(id);
	}
}
