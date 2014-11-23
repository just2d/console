package com.nuoshi.console.common.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

	private static SimpleDateFormat sdf  = new SimpleDateFormat("yyyy-MM");
	
	public static String formatDate(Date date){
		return sdf.format(date);
		
	}
	
	public static String getLastMonthDateStr(Date date){
		if(date ==null) return null;
		
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MONTH, -1);
		Date result = c.getTime();
		System.out.println(DateUtil.formatDate(result));
		return DateUtil.formatDate(result);
	}
	
}
