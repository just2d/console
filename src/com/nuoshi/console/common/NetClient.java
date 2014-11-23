package com.nuoshi.console.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.log4j.Logger;

public class NetClient {
	private static Logger logger = Logger.getLogger(NetClient.class);
	private static final int DEFAULT_TIMEOUT = 600000;

	public static String getHttpResponse(String url) throws Exception {
		return getHttpResponse(url, DEFAULT_TIMEOUT);
	}
	
	public static String getHttpResponse(String url,HashMap<String,String> param) throws Exception {
		return getHttpResponse(url, DEFAULT_TIMEOUT,param);
	}

	public static String getHttpResponseByGetMethod(String url, int timeout) throws Exception {
		// 转码
		//url = URLEncoder.encode(url, "UTF-8");
		String response = "";
		InputStream inputStream = null;
		logger.debug("REQUEST URL>>>>>>>>>>>" + url);
		HttpClient httpClient = new HttpClient();
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(timeout);
		GetMethod getMethod = new GetMethod(url);
		try {
			getMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
			int returnCode = httpClient.executeMethod(getMethod);
			inputStream = getMethod.getResponseBodyAsStream();  
	        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));   
	        StringBuffer stringBuffer = new StringBuffer();   
	        String str= "";   
	        while((str = br.readLine()) != null){   
	            stringBuffer.append(str );   
	        }   
			response = stringBuffer.toString();
			if (returnCode != HttpStatus.SC_OK) { throw new HttpException("call url:" + url
					+ " return [" + returnCode + "]"); }
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				inputStream.close();
			} catch (Exception e2) {
				// TODO: handle exception
			}
			try {
				getMethod.releaseConnection();
			} catch (Exception e2) {
				// TODO: handle exception
			}
			
		}
		logger.debug("RESPONSE<<<<<<<<<" + response);
		return response;
	}
	public static String getHttpResponseByGetMethod(String url) throws Exception {
		  return getHttpResponseByGetMethod(url, DEFAULT_TIMEOUT);
	}
	public static String getHttpResponse(String url, int timeout) throws Exception {
		// 转码
		//url = url.replaceAll("\\[", "%5B").replaceAll("\\]", "%5D").replaceAll(" ", "%20");
		String response = "";
		logger.debug("REQUEST URL>>>>>>>>>>>" + url);
		HttpClient httpClient = new HttpClient();
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(timeout);
		PostMethod postMethod = new PostMethod(url);
		try {
			postMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
			int returnCode = httpClient.executeMethod(postMethod);
			response = getString(postMethod.getResponseBodyAsStream());
			if (returnCode != HttpStatus.SC_OK) { throw new HttpException("call url:" + url
					+ " return [" + returnCode + "]"); }
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				postMethod.releaseConnection();
			} catch (Exception e2) {
				// TODO: handle exception
			}
			
		}
		logger.debug("RESPONSE<<<<<<<<<" + response);
		return response;
	}
	public static String getHttpResponse(String url, int timeout,HashMap<String,String> param) throws Exception {
		// 转码
		//url = url.replaceAll("\\[", "%5B").replaceAll("\\]", "%5D").replaceAll(" ", "%20");
		String response = "";
		logger.debug("REQUEST URL>>>>>>>>>>>" + url);
		HttpClient httpClient = new HttpClient();
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(timeout);
		PostMethod postMethod = new PostMethod(url);
		try {
			if(param!=null&&!param.isEmpty()){
				 Set<Entry<String, String>> entrySet = param.entrySet();
				for (Entry<String, String> entry : entrySet) {
					System.out.println("传过去的参数时这样的 "+entry.getKey()+"参数值"+ entry.getValue());
					postMethod.addParameter(entry.getKey(), entry.getValue());
					
				}
			}
			
			postMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
			int returnCode = httpClient.executeMethod(postMethod);
			response =getString(postMethod.getResponseBodyAsStream());
			if (returnCode != HttpStatus.SC_OK) { throw new HttpException("call url:" + url
					+ " return [" + returnCode + "]"); }
		} catch (Exception e) {
			throw e;
		} finally {
			postMethod.releaseConnection();
		}
		logger.debug("RESPONSE<<<<<<<<<" + response);
		return response;
	}
	
	private static String getString(InputStream inputStream){
		StringBuffer stringBuffer = new StringBuffer(); 
		try {
			 BufferedReader br = new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));
			   
		        String str= "";   
		        while((str = br.readLine()) != null){   
		            stringBuffer.append(str );   
		        }   
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				if(inputStream!=null){
					inputStream.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return stringBuffer.toString(); 
	}
}