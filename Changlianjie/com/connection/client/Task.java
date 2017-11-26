package com.connection.client;

public class Task implements Runnable {
	
	int a=0;
	public Task(int a) {
		this.a=a;
	}
	
	@Override
	public void run() {
		new SocketClient("111.202.58.60", 9172,"ttt"+a);
	}
	
}
