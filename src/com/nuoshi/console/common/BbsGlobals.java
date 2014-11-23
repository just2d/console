package com.nuoshi.console.common;

import java.util.Map;
import java.util.TreeMap;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.nuoshi.console.domain.bbs.TfBbsForums;

public class BbsGlobals {
	public static final int OTHER_CITY_ID= -1;
	public static final int FORUM_1 = 1;
	public static final int FORUM_2= 2;
	public static final int CITY_BEIJING=1;
	public static final int CITY_SHENZHEN=4;
	
	public static final int TURNOFF_STATUS= 0;
	
	public static Map<Byte,String>  visibleRoleName=new TreeMap<Byte,String>();
	
	public static Map<Integer,String>  statusName=new TreeMap<Integer,String>();
	
	static {
		visibleRoleName.put(Byte.valueOf((byte)1), "普通用户");
		visibleRoleName.put(Byte.valueOf((byte)2), "经纪人");
		
		statusName.put(-1, "删除");
		statusName.put(0, "关闭");
		statusName.put(1, "开启");
		
		
	}
	public static  String getVisibleRoleName(Byte visibleRole){
		if(visibleRole==null){
			return "";
		}
		String tmp=visibleRoleName.get(visibleRole);
		return tmp==null?"":tmp;
	}
	public static  String getStatusName(int status){
		String tmp=statusName.get(status);
		return tmp==null?"":tmp;
	}

}
