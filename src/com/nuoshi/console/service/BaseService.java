package com.nuoshi.console.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.log4j.Logger;

import com.google.gson.Gson;

public class BaseService {
	protected Logger log = Logger.getLogger(this.getClass());
	protected static Gson gson = new Gson();
	
	public String callPostService(String urlStr, String jsonData) {
		String result = "";
		URL url;
		HttpURLConnection httpUrlConnection;
		BufferedReader in;
		String line;
		try {
			url = new URL(urlStr);
			httpUrlConnection = (HttpURLConnection) url.openConnection();
			httpUrlConnection.setDoOutput(true);
			httpUrlConnection.setUseCaches(false); 
			httpUrlConnection.setRequestProperty("Content-type", "application/x-java-serialized-object");   
			httpUrlConnection.setRequestMethod("POST");
			httpUrlConnection.connect();
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(httpUrlConnection.getOutputStream(), "utf-8"));
			
			writer.write(jsonData);
			writer.flush();   
			writer.close();
			in = new BufferedReader(new InputStreamReader(httpUrlConnection.getInputStream(), "utf-8"));
			while ((line = in.readLine()) != null) {
				result += line;
			}
			in.close();

		} catch (Exception e) {
			log.error(e.toString());
			e.printStackTrace();
		}
		return result;
	}
	
	
	public static String callService(String urlStr,  Object... objs) {
		String result = "";
		URL url;
		HttpURLConnection httpUrl;
		BufferedReader in;
		String line;
		try {
			String var = "";
			for(int i = 0; i < objs.length; i++) {
				var += "/" + objs[i].toString();
			}
			url = new URL(urlStr + var );
			httpUrl = (HttpURLConnection) url.openConnection();
			in = new BufferedReader(new InputStreamReader(
					httpUrl.getInputStream(), "utf-8"));
			while ((line = in.readLine()) != null) {
				result += line;
			}
			in.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	
}
