package com.nuoshi.console.view.multiselect;

/**
 * Created by IntelliJ IDEA. User: pekky Date: 2009-12-23 Time: 18:02:45 To
 * change this template use File | Settings | File Templates.
 */
public class Facility extends CheckboxGroup {
	@Override
	public String getResourcePrefix() {
		return resourcePrefix; // To change body of implemented methods use File
								// | Settings | File Templates.
	}

	@Override
	public int getItemCount() {
		return itemCount; // To change body of implemented methods use File |
							// Settings | File Templates.
	}

	private final static String resourcePrefix = "rent.facility";
	private final static int itemCount = 11;

}
