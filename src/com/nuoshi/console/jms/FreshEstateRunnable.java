package com.nuoshi.console.jms;

import java.io.Serializable;

import com.nuoshi.console.domain.estate.EstateChangeMessage;

public class FreshEstateRunnable implements Runnable, Serializable {
	private EstateChangeMessage changeMessage;
	/**
	 * 
	 */
	private static final long serialVersionUID = 4297122682938113634L;

	FreshEstateRunnable(int estateId, EstateChangeMessage.ChangeStatus changeStatus) {
		changeMessage = new EstateChangeMessage();
		changeMessage.setEstateId(estateId);
		changeMessage.setChangeStatus(changeStatus);
	}

	public void run() {
		try {
			if (EstateJms.instance != null) {
				EstateJms.instance.sendMessage(changeMessage);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
