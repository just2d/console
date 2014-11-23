package com.nuoshi.console.domain.user;

import com.nuoshi.console.domain.base.BaseFunction;

public class Function extends BaseFunction  {
	private static final long serialVersionUID = 1L;

	/**
	 * 功能集的分隔符
	 */
	public static final String FUNC_SPLIT = "@";
	/**
	 * 下拉列表树
	 */
	private String selectTree;

	public String getSelectTree() {
		return selectTree;
	}

	public String getTreeName() {
		return getName();
	}

	public Function getTreeParent() {
		return getParent();
	}

	/* [CONSTRUCTOR MARKER BEGIN] */
	public Function() {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public Function(java.lang.Long id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public Function(java.lang.Long id, java.lang.Integer priority,
			java.lang.Boolean menu) {

		super(id, priority, menu);
	}


}