package com.nuoshi.console.service;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationConfig.Feature;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Service;

import com.nuoshi.console.common.Globals;
import com.nuoshi.console.domain.estate.EstateHouseCount;
import com.nuoshi.console.persistence.read.taofang.estate.EstateReadMapper;

@Service
public class EstateHouseCountService extends BaseService {
	
	@Resource
	private EstateReadMapper estateReadMapper;

	public void getHouseCount(List<EstateHouseCount> list){
		EstateHouseCount houseCount = null;
		for(EstateHouseCount para : list) {
			try {
				switch(para.getType()) {
				case 1:
					houseCount = estateReadMapper.getEstateHouseCount(Integer.parseInt(para.getEstateUnique()));
					para.setResaleCount(houseCount.getResaleCount());
					para.setRentCount(houseCount.getRentCount());
					break;
				case 2:
					houseCount = estateReadMapper.getNewEstateHouseCount(para.getEstateUnique());
					para.setResaleCount(houseCount.getResaleCount());
					para.setRentCount(houseCount.getRentCount());
					break;
				case 3:
					para.setResaleCount(getResaleListCount(para));
					break;
				case 4:
					para.setRentCount(getRentListCount(para));
					break;
				case 5:
					para.setResaleCount(getResaleKeywordCount(para));
					break;
				case 6:
					para.setRentCount(getRentKeywordCount(para));
					break;
				default:
					break;
				}
			} catch(Exception e){
				
			}

		}
	}
	
	public int getEstateHouseCount(EstateHouseCount houseCount){
		
		return 1;
	}
	
	public int getResaleListCount(EstateHouseCount houseCount) {
		StringBuffer sb = new StringBuffer();
		sb.append(Globals.SEARCH_RESALE_URL + "?qt=search");
		sb.append("&").append(houseCount.getSearchLocationId()).append("&rows=0").append("&wt=json");
		String result = callService(sb.toString());
		return searchJsonResultToMap(result, "numFound");
	}
	
	public int getRentListCount(EstateHouseCount  houseCount) {
		StringBuffer sb = new StringBuffer();
		sb.append(Globals.SEARCH_RENT_URL + "?qt=search");
		sb.append("&").append(houseCount.getSearchLocationId()).append("&rows=0").append("&wt=json");
		String result = callService(sb.toString());
		return searchJsonResultToMap(result, "numFound");
	}
	
	public int getResaleKeywordCount(EstateHouseCount houseCount) {
		StringBuffer sb = new StringBuffer();
		sb.append(Globals.SEARCH_RESALE_URL).append("?").append(houseCount.getSearchKeyword());
		sb.append("&").append(houseCount.getSearchKeywordCity()).append("&rows=0").append("&wt=json");
		String result = callService(sb.toString());
		return searchJsonResultToMap(result, "numFound");
	}
	
	public int getRentKeywordCount(EstateHouseCount houseCount) {
		StringBuffer sb = new StringBuffer();
		sb.append(Globals.SEARCH_RENT_URL).append("?").append(houseCount.getSearchKeyword());
		sb.append("&").append(houseCount.getSearchKeywordCity()).append("&rows=0").append("&wt=json");
		String result = callService(sb.toString());
		return searchJsonResultToMap(result, "numFound");
	}
	
	private int searchJsonResultToMap(String jsonString,String noteName){
		if(StringUtils.isBlank(jsonString)){
			return 0;
		}
		ObjectMapper jsonMapper=new ObjectMapper();
		jsonMapper.getDeserializationConfig().set(Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		JsonNode jNode = null;
		try {
			jNode=jsonMapper.readTree(jsonString);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		List<String> count = jNode.findValuesAsText(noteName.trim());
		if(count != null && count.size() > 0) {
			return Integer.parseInt(count.get(0));
		}
		return 0;	
	}
}
