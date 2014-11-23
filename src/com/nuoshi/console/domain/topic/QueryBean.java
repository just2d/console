package com.nuoshi.console.domain.topic;


public class QueryBean {
	public static final int SCOPE_GLOABL = 0;
	public static final int SCOPE_RESALE = 1;
	public static final int SCOPE_RENT = 2;
	
	private String key;
	private String value;
	private int valMin;
	private int valMax;

	public String getKey() {
		return key;
	}

	public String getValue() {
		return value;
	}

	public int getValMin() {
		return valMin;
	}

	public int getValMax() {
		return valMax;
	}

}
