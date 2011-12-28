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
	private boolean countDown = false;
	private long countDownTime;
	
	public GameLoop(GUI gui, Calculator calc){
		this.gui = gui;
		this.calc = calc;
		this.y = -1;
		this.x = -1;
		this.events = new LinkedBlockingQueue<MotionEvent>();
	}
	
	public void setRunning(boolean running){
		this.running = running;
	}
	
	public synchronized boolean addMotionEvent(MotionEvent m){
		return events.add(m);
	}
		
	@Override
	public void run(){
		long pause = 50;
		long startTime;
		long sleepTime;
		
		while(running){
			Canvas c = null;
			MotionEvent m = null;
			startTime = System.currentTimeMillis();
			
			if(countDown)
			
			
			if(events.size() != 0){
				
				try {
					m = events.poll(20, TimeUnit.MILLISECONDS);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				
				if(m != null){
					
					switch (m.getAction()) {
					case MotionEvent.ACTION_DOWN:
						calc.hasCome((int)m.getX(), (int)m.getY());
						break;
						
					case MotionEvent.ACTION_MOVE:
						calc.hasMoved((int)m.getX(), (int)m.getY());						
						break;
		
					case MotionEvent.ACTION_UP:
						calc.hasGone((int)m.getX(), (int)m.getY());								
						break;
						
					default:
						break;
					}
					
					gui.putPoints(calc.getCoordinates());
					
					
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
			
			
			sleepTime = pause - (System.currentTimeMillis() - startTime);
			
			try{
				if(sleepTime > 0){
					sleep(sleepTime);
				}
			}catch(Exception e){
				
			}			
		}
	}
}
