package com.nuoshi.console.common.util;

import org.apache.commons.lang.StringUtils;

public class SearcherConditionUtil {
	
	
	
	public static String setCondition(String condition,String property,String searcherType ){
		if(StringUtils.isNotBlank(condition)){
			condition +=  " and "+ property+"" ;
		}
		
		return condition;
		
	}
}
