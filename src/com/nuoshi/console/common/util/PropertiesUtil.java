package com.nuoshi.console.common.util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class PropertiesUtil {
	private static Log logger = LogFactory.getLog(PropertiesUtil.class);
	public static String readValue(String filePath,String key){
		Properties props = new Properties();
		InputStream ips;
		try {
			ips = new BufferedInputStream(new FileInputStream(filePath));
			props.load(ips);
			String value = props.getProperty(key);
			System.out.println(key+"="+value);
			return value;
		} catch (Exception e) {
			logger.error(e);
			return null;
		}
	}
	
	public static void main(String args[]){
		System.out.println(PropertiesUtil.readValue("resources/jdbc.properties", "taofang.write.jdbc.url"));
	}
}
