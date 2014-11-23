package com.nuoshi.console.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nuoshi.console.dao.HouseExtraScoreHistoryDAO;
import com.nuoshi.console.domain.house.HouseExtraScoreHistory;

@Service
public class HouseExtraScoreHistoryService extends BaseService {
	@Autowired
	private HouseExtraScoreHistoryDAO houseExtraScoreHistoryDAO;

	public int add(HouseExtraScoreHistory houseExtraScoreHistory) {
		return  houseExtraScoreHistoryDAO.add(houseExtraScoreHistory);
	}
	public List<HouseExtraScoreHistory> getByHouseId(int houseId,int houseType) {
		return  houseExtraScoreHistoryDAO.getByHouseId(houseId, houseType);
	}
 
  
}
