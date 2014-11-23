package com.nuoshi.console.jms;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.nuoshi.console.domain.estate.EstateChangeMessage;

public class FreshEstateTaskThreadPoolManager {

	public static FreshEstateTaskThreadPoolManager INSTANCE = new FreshEstateTaskThreadPoolManager();

	private FreshEstateTaskThreadPoolManager() {
	}


	public final ExecutorService service = Executors.newFixedThreadPool(5);

	public void addTask(int estateId,EstateChangeMessage.ChangeStatus  changeStatus) {
		Runnable task = new FreshEstateRunnable(estateId,changeStatus);
		this.service.execute(task);
	}
}
