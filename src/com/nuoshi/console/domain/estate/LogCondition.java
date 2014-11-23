package com.nuoshi.console.domain.estate;

public class LogCondition extends UnionLog {
	private String showHouse ;

	public String getShowHouse() {
		if(showHouse == null){
			showHouse = "0";
		}
		return showHouse;
	}

	public void setShowHouse(String showHouse) {
		this.showHouse = showHouse;
	}

}
