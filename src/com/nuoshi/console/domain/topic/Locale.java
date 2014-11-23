package com.nuoshi.console.domain.topic;


public class Locale {
	public static final int SCALE_MAJOR = 0;
	public static final int SCALE_PRIMARY = 1;
	public static final int SCALE_SECONDARY = 2;
	public static final int SCALE_SMALL = 3;

	public static final int DEPTH_CITY = 0;
	public static final int DEPTH_DIST = 1;
	public static final int DEPTH_BLOCK = 2;

	private int id;
	private int parentId;
	private String code;
	private String name;
	private Integer scale;
	private int depth;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getDepth() {
		return depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}

	public Integer getScale() {
		return scale;
	}

	public void setScale(Integer scale) {
		this.scale = scale;
	}

}
