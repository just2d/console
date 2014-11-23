package com.nuoshi.console.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.nuoshi.console.dao.ConvertRateDao;
import com.nuoshi.console.domain.stat.AuditSearch;
import com.nuoshi.console.domain.stat.ConvertRate;
import com.nuoshi.console.domain.stat.HouseQuality;
import com.nuoshi.console.domain.stat.PhotoAuditStatis;
import com.nuoshi.console.domain.stat.RateSearch;
import com.nuoshi.console.domain.user.User;

@Service
public class ConvertRateService {
	@Resource
	ConvertRateDao convertRateDao;
	
	public List<ConvertRate> queryRateByPage(RateSearch rateSearch){
		List<ConvertRate> list=convertRateDao.queryRateByPage(rateSearch);
		return list;
	}
	
	public List<PhotoAuditStatis> getPhotoAuditStatisList(AuditSearch as){
		return convertRateDao.getPhotoAuditStatisListByPage(as);
	}
	
	public List<PhotoAuditStatis> getPhotoAuditStatisListToExcel(AuditSearch as){
		return convertRateDao.getPhotoAuditStatisListToExcel(as);
	}
	
	public List<List<HouseQuality>> getAuditHouseQualityList(HouseQuality hq,String userName){
		return convertRateDao.getAuditHouseQualityList(hq,userName);
	}
	
	public List<List<HouseQuality>> getAuditHouseQualityHistoryListByPage(HouseQuality hq,String userName){
		return convertRateDao.getAuditHouseQualityHistoryListByPage(hq,userName);
	}
	
	public List<User> getAllUsersByRoleId(int id){
		return convertRateDao.getAllUsersByRoleId(id);
	}
}
