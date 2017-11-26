package com.connection.client;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
	public static void main(String[] args) {
		ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
		for (int i = 0; i < 1; i++) {
			Task task = new Task(i);
			cachedThreadPool.submit(task);
		}
		cachedThreadPool.shutdown();
	}
}
