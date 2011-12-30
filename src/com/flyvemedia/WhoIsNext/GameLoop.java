package com.flyvemedia.WhoIsNext;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import android.graphics.Canvas;
import android.view.MotionEvent;

public class GameLoop extends Thread {
	private GUI gui;
	private Calculator calc;
	private boolean running = false;
	private LinkedBlockingQueue<MotionEvent> events;
	private int x;
	private int y;
	private boolean isActive = false;
	private int fingersOnScreen = 0;
	private CountDown countDownThread;
	
	public GameLoop(GUI gui, Calculator calc){
		super("GameLoop");
		this.gui = gui;
		this.calc = calc;
		this.y = -1;
		this.x = -1;
		this.events = new LinkedBlockingQueue<MotionEvent>();
		this.countDownThread = new CountDown(gui, this);
	}
	
	public void setRunning(boolean running){
		this.running = running;
	}
	
	public synchronized boolean addMotionEvent(MotionEvent m){
		boolean added = events.add(m);
		//System.out.println("GameLoop: put: "+m.getAction()+" added: "+added);
		
		return added;
	}
		
	@Override
	public void run(){
		long pause = 50;
		long startTime;
		long sleepTime;
				
		while(running){
			MotionEvent m = null;
			startTime = System.currentTimeMillis();
												
			if(events.size() != 0){
				
				
				/*
				System.out.println("GameLoop: events.size() == "+events.size());

				
				System.out.println("EVENTS:");
				
				for(MotionEvent me : events){
					String eventID = "";
					switch (me.getAction()) {
					case MotionEvent.ACTION_DOWN:
						eventID = "down";
						break;
						
					case MotionEvent.ACTION_MOVE:
						eventID = "move";			
						break;

					case MotionEvent.ACTION_UP:
						eventID = "up";
						break;
					}
					

					System.out.println(eventID);
				}*/
				
				m = events.poll();

				
				if(m != null){
					switch (m.getAction()) {
					case MotionEvent.ACTION_DOWN:

						if(fingersOnScreen == 0){
							countDownThread.start();
						}

						calc.hasCome((int)m.getX(), (int)m.getY());
						fingersOnScreen++;

						break;
		
					case MotionEvent.ACTION_UP:
						if(isActive){
							isActive = false;
							
						}
						
						if(fingersOnScreen == 1){
							countDownThread.setRunning(false);
							countDownThread = null;
							countDownThread = new CountDown(gui, this);
						}
						
						calc.hasGone((int)m.getX(), (int)m.getY());								
						fingersOnScreen--;
						break;
						
					default:
						break;
					}
					
					gui.putPoints(calc.getCoordinates());
					
					callDraw();
				}
			}
			
			
			sleepTime = pause - (System.currentTimeMillis() - startTime);
			
			try{
				if(sleepTime > 0){
					sleep(sleepTime);
				}
			}catch(Exception e){
				
			}			
		}
	}
	
	public void setActive(boolean active){
		this.isActive = active;
	}
	
	public void callDraw(){
		Canvas c = null;
		
		try{
			c = gui.getHolder().lockCanvas();
			synchronized (gui.getHolder()) {
				gui.onDraw(c);
			}
		} finally{
			if(c != null){
				gui.getHolder().unlockCanvasAndPost(c);
			}
		}
	}
	
}
