package com.nuoshi.console.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StrUtils {
	/**
	 * 检测字符串是否不为空(null,"","null")
	 * 
	 * @param s
	 * @return 不为空则返回true，否则返回false
	 */
	public static boolean notEmpty(String s) {
		return s != null && !"".equals(s) && !"null".equals(s);
	}

	/**
	 * 检测字符串是否为空(null,"","null")
	 * 
	 * @param s
	 * @return 为空则返回true，不否则返回false
	 */
	public static boolean isEmpty(String s) {
		return s == null || "".equals(s) || "null".equals(s);
	}

	/**
	 * 字符串转换为字符串数组
	 * 
	 * @param str
	 *            字符串
	 * @param splitRegex
	 *            分隔符
	 * @return
	 */
	public static String[] str2StrArray(String str, String splitRegex) {
		if (isEmpty(str)) {
			return null;
		}
		return str.split(splitRegex);
	}

	/**
	 * 用默认的分隔符(,)将字符串转换为字符串数组
	 * 
	 * @param str
	 *            字符串
	 * @return
	 */
	public static String[] str2StrArray(String str) {
		return str2StrArray(str, ",\\s*");
	}

	/**
	 * 按照yyyy-MM-dd HH:mm:ss的格式，日期转字符串
	 * 
	 * @param date
	 * @return yyyy-MM-dd HH:mm:ss
	 */
	public static String date2Str(Date date) {
		return date2Str(date, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 按照yyyy-MM-dd HH:mm:ss的格式，字符串转日期
	 * 
	 * @param date
	 * @return
	 */
	public static Date str2Date(String date) {
		if (notEmpty(date)) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				return sdf.parse(date);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return new Date();
		} else {
			return null;
		}
	}

	/**
	 * 按照参数format的格式，日期转字符串
	 * 
	 * @param date
	 * @param format
	 * @return
	 */
	public static String date2Str(Date date, String format) {
		if (date != null) {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			return sdf.format(date);
		} else {
			return "";
		}
	}
	
	public static List<Integer> str2IntList(String str){
		String[] ids = str2StrArray(str);
		List<Integer> idList = null;
		if(ids != null&& ids.length >0){
			idList = new ArrayList<Integer>();
			for(String id :ids){
				idList.add(Integer.parseInt(id));
			}
		}
		return idList;
}

	// 文本计数器

	public static int textCounter(String text) {
		if (text == null) {
			return 0;
		}
		Double count = 0.0;
		for (int i = 0; i < text.length(); i++) {
			if (text.charAt(i) < 256)

			{
				count = count + 0.5;
			} else {
				count = count + 1;
			}
		}
		return count.intValue();
	}
	public static String Html2Text(String inputString){
	     String htmlStr = inputString; //含html标签的字符串
	     String textStr ="";
	     java.util.regex.Pattern p_script;
	     java.util.regex.Matcher m_script;
	     java.util.regex.Pattern p_style;
	     java.util.regex.Matcher m_style;
	     java.util.regex.Pattern p_html;
	     java.util.regex.Matcher m_html;

	    try{
	          String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>"; //定义script的正则表达式{或<script[^>]*?>[\\s\\S]*?<\\/script> }
	          String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>"; //定义style的正则表达式{或<style[^>]*?>[\\s\\S]*?<\\/style> }
	          String regEx_html = "<[^>]+>"; //定义HTML标签的正则表达式

	          p_script = Pattern.compile(regEx_script,Pattern.CASE_INSENSITIVE);
	          m_script = p_script.matcher(htmlStr);
	          htmlStr = m_script.replaceAll(""); //过滤script标签

	          p_style = Pattern.compile(regEx_style,Pattern.CASE_INSENSITIVE);
	          m_style = p_style.matcher(htmlStr);
	          htmlStr = m_style.replaceAll(""); //过滤style标签

	          p_html = Pattern.compile(regEx_html,Pattern.CASE_INSENSITIVE);
	          m_html = p_html.matcher(htmlStr);
	          htmlStr = m_html.replaceAll(""); //过滤html标签

	          textStr = htmlStr;
	     }catch(Exception e){
	        
	     }
	     return textStr;//返回文本字符串
	 }   

	public static int getParagraphCount(String desc) {
		if(desc==null){
			return 0;
		}
		desc = desc.replaceAll("\\s*", "");//去空白
		Pattern pattern = Pattern.compile("<p|</p>|<div|</div>");
		Matcher match = pattern.matcher(desc);
		int total = 0;
		int start = 0;
		while (match.find()) {
			int m = match.start();
			int n = match.end();
			String temp = desc.substring(start, m);
			// 过滤掉<br><br>这种情况
			if (temp != null && !temp.isEmpty() && !temp.equals(">")) {
				if (temp.contains(">")) {
					int begin = temp.indexOf(">");
					String str = temp.substring(begin + 1);
					if (str != null && !str.isEmpty()) {
						total++;
					}
				}
			}
			start = n;
		}

		if (desc.substring(start, desc.length()) != null && !desc.substring(start, desc.length()).isEmpty()) {
			total++;
		}
		return total;
	} 


}
