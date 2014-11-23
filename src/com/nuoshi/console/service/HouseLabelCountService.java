package com.nuoshi.console.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.nuoshi.console.domain.stat.HouseLabelInfo;
import com.nuoshi.console.persistence.read.stat.HouseLabelReadMapper;

@Service
public class HouseLabelCountService  extends BaseService{

	@Resource
	private HouseLabelReadMapper houseLabelReadMapper;
	
	public List<HouseLabelInfo> getHouseLabelCount(String cityId, String startDate,String endDate) {
		StringBuffer condition =new StringBuffer();
		
		if(!StringUtils.isBlank(cityId)&&!"-1".equals(cityId)){
			condition.append(" and city_id = '");
			condition.append(cityId);
			condition.append("'");
		}
		
		if(StringUtils.isBlank(startDate)&&StringUtils.isBlank(endDate)){
			Calendar ca  = Calendar.getInstance();
			
			ca.add(Calendar.MONTH, -1);
			Date d = ca.getTime();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			endDate = sdf.format(d);
			
			condition.append(" and entry_datetime >= '");
			condition.append(endDate);
			condition.append("'");
			condition.append("  group by entry_datetime order by entry_datetime asc");
			
		}else if(!StringUtils.isBlank(startDate)&&!StringUtils.isBlank(endDate)&&startDate.equals(endDate)){
			condition.append(" and entry_datetime = '");
			String tempDate = endDate.replaceAll("-", "");
			condition.append(tempDate);
			condition.append("'");
		}else{
		
			if(!StringUtils.isBlank(startDate)){
				String tempDate = startDate.replaceAll("-", "");
				condition.append(" and entry_datetime >= '");
				condition.append(tempDate);
				condition.append("'");
			}
			if(!StringUtils.isBlank(endDate)){
				String tempDate = endDate.replaceAll("-", "");
				condition.append(" and entry_datetime <= '");
				condition.append(tempDate);
				condition.append("'");
			}
			
			condition.append(" group by entry_datetime order by entry_datetime asc");
		}
		
		HashMap<String,String> params =new HashMap<String, String>();
		params.put("condition", condition.toString());
		List<HouseLabelInfo> labelsList=null;
		if(!StringUtils.isBlank(cityId)&&!"-1".equals(cityId)){
			labelsList = houseLabelReadMapper.getHouseLabelCount(params);
		}else{
			labelsList = houseLabelReadMapper.getHouseLabelCountCountry(params);
		}
		return labelsList;
	}

}
