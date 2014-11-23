package com.nuoshi.console.view.multiselect;

/**
 * Created by IntelliJ IDEA. User: pekky Date: 2009-12-23 Time: 18:00:14 To
 * change this template use File | Settings | File Templates.
 */
public class Equipment extends CheckboxGroup {
	@Override
	public String getResourcePrefix() {
		return resourcePrefix;
	}

	@Override
	public int getItemCount() {
		return itemCount;
	}

	private final static String resourcePrefix = "rent.equipment";
	private final static int itemCount = 12;
	public static void main(String[] args) {
		Equipment ep= new Equipment();
		ep.setValue(30);
		ep.toString();
	}
}
