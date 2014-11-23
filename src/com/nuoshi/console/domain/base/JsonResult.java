package com.nuoshi.console.domain.base;

import java.io.Serializable;

public class JsonResult implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7087158020948904411L;
	
	private  boolean result;
	private String data;
	private long numFound;
	private String message;
	
	
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public boolean isResult() {
		return result;
	}
	public void setResult(boolean result) {
		this.result = result;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public long getNumFound() {
		return numFound;
	}
	public void setNumFound(long numFound) {
		this.numFound = numFound;
	}
	
}
