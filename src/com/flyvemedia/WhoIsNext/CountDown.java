package com.flyvemedia.WhoIsNext;

public class CountDown extends Thread {

	private GUI gui;
	private GameLoop gameLoop;
	private boolean running = true;
	private long currentCountDown = 0;
	private final long COUNT_DOWN_TIME = 3000;
	
	public CountDown(GUI gui, GameLoop gameLoop) {
		super("CountDown");
		
		this.gui = gui;
		this.gameLoop = gameLoop;
		
		System.out.println("CD: constructor");
		
	}
	
	public void setRunning(boolean running){
		this.running = running;
	}
	
	@Override
	public void run() {
		
		int sec;
		int ds;
		int hs;
		
		System.out.println("CD: run");
		
		gui.setCountDown(true);
		
		long countDownStart = System.currentTimeMillis();
		
		
		
		while(running && currentCountDown < COUNT_DOWN_TIME){
			
			//System.out.println("CD: running (" + currentCountDown + ")");
			
			currentCountDown = System.currentTimeMillis() - countDownStart;
			sec = (int)(COUNT_DOWN_TIME - currentCountDown) / 1000;
			ds = (int)(COUNT_DOWN_TIME - currentCountDown  - sec * 1000) / 100;
			hs = (int)(COUNT_DOWN_TIME - currentCountDown - sec * 1000 - ds * 100) / 10;
			
			if(hs < 0)
				hs = 0;
			
			gui.setCountDownTime(sec + ":" + ds + "" + hs);
			gameLoop.callDraw();
		}
		
		if(currentCountDown >= COUNT_DOWN_TIME){
			gui.setCountDown(false);
			gui.setActive(true);
			gameLoop.setCountDown(false);
			gameLoop.setActive(true);
			gameLoop.callDraw();
		}
	}

}
