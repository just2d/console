package com.nuoshi.console.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.nuoshi.console.domain.web.DayWebStatis;
import com.nuoshi.console.domain.web.MonthWebStatis;
import com.nuoshi.console.persistence.read.stat.DayWebStatisReadMapper;
import com.nuoshi.console.persistence.read.stat.MonthWebStatisReadMapper;

@Service
public class WebStatisService  extends BaseService{

	@Resource
	private DayWebStatisReadMapper dayWebStatisReadMapper;
	@Resource
	private MonthWebStatisReadMapper monthWebStatisReadMapper;
	
	public  List<DayWebStatis>  getDayCityData(int date) {
		
		/*List<DayWebStatis> all =  dayWebStatisReadMapper.getCityDataByDate(date);
		Map<Integer ,DayWebStatis> cityDataMap =  new LinkedHashMap<Integer,DayWebStatis>();
		
		if(all!=null&&all.size()>0){
			for (DayWebStatis dayWebStatis : all) {
				DayWebStatis cityData = cityDataMap.get(dayWebStatis.getCityId());
				if(cityData==null){
					cityData = new DayWebStatis();
					cityData.setCityId(dayWebStatis.getCityId());
					cityData.setCityName(dayWebStatis.getCityName());
					cityDataMap.put(dayWebStatis.getCityId(),cityData);
				}
				cityData.setDistName(distName);
				
			}
		}*/
		
		return dayWebStatisReadMapper.getCityDataByDate(date);
		 
	}
	public  List<DayWebStatis>  getDayAllData(int cityId,int distId,int startDate,int endDate) {
		
		return dayWebStatisReadMapper.getAllDataByDate(cityId,distId,startDate,endDate);
		
	}
	public  List<MonthWebStatis>  getMonthCityData(int date) {
		
		return monthWebStatisReadMapper.getCityDataByDate(date);
		
	}
	public  List<MonthWebStatis>  getMonthAllData(int cityId,int distId,int statrDate,int endDate) {
		
		return monthWebStatisReadMapper.getAllDataByDate(cityId,distId,statrDate,  endDate);
		
	}

}
