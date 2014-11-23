package com.nuoshi.console.common.util;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

import com.nuoshi.console.common.Resources;
import com.nuoshi.console.view.multiselect.Equipment;
import com.nuoshi.console.view.multiselect.Facility;

/**
 *<b>www.taofang.com</b>
 * <b>function:</b>
 * @author lizhenmin
 * @createDate 2011 Aug 11, 2011 11:06:01 AM
 * @email lizm@taofang.com
 * @version 1.0
 */
public class ApplicationUtil {
	private static int TimeoutInMilliseconds = 2000;
	
	public static String getHouseFaceto(int i){
		return Resources.getString("house.faceto."+i);
	}
	public static String getDecoration(int i){
		return Resources.getString("house.decoration."+i); 
	}
	public static String getFacility(int i){
	   Facility houseFacility = new Facility();
	   houseFacility.setValue(i);
		return houseFacility.toString();
	}
	public static String getEquipment(int i){
	   Equipment houseEquipment = new Equipment();
	   houseEquipment.setValue(i);
		return houseEquipment.toString();
	}
	
	public static String getHttpResponse(String url) {
		String response = null;
		HttpClient httpClient = new HttpClient();
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(TimeoutInMilliseconds);
		PostMethod postMethod = new PostMethod(url);
		try {
			postMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
			int returnCode = httpClient.executeMethod(postMethod);
			if (returnCode == HttpStatus.SC_OK) {
				response = postMethod.getResponseBodyAsString();
			} 
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			postMethod.releaseConnection();
		}
		return response;
	}

}
