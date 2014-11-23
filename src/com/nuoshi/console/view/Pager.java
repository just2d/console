package com.nuoshi.console.view;

public class Pager {

	private int start;// 开始索引
	private int limit;// 每次读取条数

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

}
