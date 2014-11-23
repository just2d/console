package com.nuoshi.console.domain.estate;

import java.io.Serializable;

public class EstateChangeMessage implements Serializable {

	private static final long serialVersionUID = 1489834809351811126L;

	public static enum ChangeStatus {
		Add, Modify, Delete
	};

	private int estateId;

	private ChangeStatus changeStatus;

	public int getEstateId() {
		return estateId;
	}

	public void setEstateId(int estateId) {
		this.estateId = estateId;
	}

	public ChangeStatus getChangeStatus() {
		return changeStatus;
	}

	public void setChangeStatus(ChangeStatus changeStatus) {
		this.changeStatus = changeStatus;
	}

}
