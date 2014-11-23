 /* 
  * TemplateService.java
  * 模板参数替换
  * 
  * 何雨潇 2011.4.26
  * www.taofang.com 淘房网版权所有 
  */ 

package com.nuoshi.console.common.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TemplateUtil {
	
	private String regPattern = "\\{[a-zA-Z0-9_]+\\}";
	private String templateText = "";
	private HashMap<String, String> paramMap = new HashMap<String, String>();
	private Pattern pattern = Pattern.compile(regPattern);
	
	/**
	 * 设置参数名称和值
	 * @param key
	 * @param value
	 */
	public void setParameter(String key, String value) {
		if(key != null)
			paramMap.put(key, value);
	}
	
	/**
	 * 清除参数和模板文本
	 */
	public void clearAll() {
		clearParameters();
		clearTemplateText();
	}
	
	/**
	 * 清除参数Map
	 */
	public void clearParameters() {
		paramMap.clear();
	}
	
	/**
	 * 清除指定参数
	 * @param key
	 */
	public void removeParameter(String key) {
		paramMap.remove(key);
	}
	
	/**
	 * 获取设定的参数的值
	 * @param key
	 * @return
	 */
	public String getParameter(String key) {
		return paramMap.get(key) == null ? null : paramMap.get(key).toString();
	}
	
	/**
	 * 清除模板文本
	 */
	public void clearTemplateText() {
		templateText = "";
	}
	
	/**
	 * 从文件里读入模板文本
	 * @param filename
	 * @throws IOException
	 */
	public void loadTemplateFromResource(String filename) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(
				ClassLoader.getSystemResourceAsStream(filename)));		
		StringBuffer sb = new StringBuffer();		
		String line = null;
		while ((line = br.readLine()) != null) {
			sb.append(line + "\n");
		}
		br.close();
		this.templateText = sb.toString();
	}
	
	/**
	 * 用自身参数替换自身模板
	 * @param text
	 * @return
	 */
	public String replace() {
		return replace(this.templateText, this.paramMap);
	}
	
	/**
	 * 用自带的参数Map进行替换指定文本
	 * @param text
	 * @return
	 */
	public String replace(String text) {
		return replace(text, this.paramMap);
	}

	/**
	 * 用指定的参数Map进行替换指定文本
	 * @param text
	 * @param hm
	 * @return
	 */
	public String replace(String text, HashMap<String, String> hm) {
		
		StringBuffer sb = new StringBuffer();
		
		Matcher m = pattern.matcher(text);
		while(m.find()) {
			m.appendReplacement(sb, 
					hm.containsKey(m.group()) ? hm.get(m.group()) : "");
		}
		
		m.appendTail(sb);
		
		return sb.toString();
	}
}
