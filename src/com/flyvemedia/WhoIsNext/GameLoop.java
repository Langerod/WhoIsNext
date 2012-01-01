package com.flyvemedia.WhoIsNext;

import java.util.concurrent.LinkedBlockingQueue;

import android.graphics.Canvas;
import android.os.SystemClock;
import android.view.MotionEvent;

public class GameLoop extends Thread {
	private GUI gui;
	private Calculator calc;
	private boolean running = false;
	private LinkedBlockingQueue<MotionEvent> events;
	
	private boolean isActive = false;
	private boolean countDown = false;
	private CountDown countDownThread;
	
	private Energize energize;
	
	//testing

	MotionEvent m1 = MotionEvent.obtain(SystemClock.uptimeMillis()+40, 14l, MotionEvent.ACTION_DOWN, 20f, 200f,0);
	MotionEvent m2 = MotionEvent.obtain(SystemClock.uptimeMillis()+10000, 14l, MotionEvent.ACTION_DOWN, 100f, 20f,0);
	//MotionEvent m3 = MotionEvent.obtain(SystemClock.uptimeMillis()+40, 14l, MotionEvent.ACTION_UP, 20f, 200f,0);
	//MotionEvent m4 = MotionEvent.obtain(SystemClock.uptimeMillis()+100, 14l, MotionEvent.ACTION_UP, 100f, 20f,0);

	//end testing

	public GameLoop(GUI gui, Calculator calc){
		super("GameLoop");
		this.gui = gui;
		this.calc = calc;
		this.energize = new Energize(gui, this);
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
		long pause = 20;
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

						if(!isActive && !countDown){
							countDown = true;
							countDownThread.start();

							if(m1 != null){
								gui.onTouchEvent(m1);
								m1 = null;
							}
							if(m2 != null){
								gui.onTouchEvent(m2);
								m2 = null;
							}
						}


						calc.hasCome(m.getX(), m.getY());
						break;

					case MotionEvent.ACTION_UP:

						System.out.println("UP");
						if(isActive || countDown){

							/*if(m3 != null){
								gui.onTouchEvent(m3);
								m3 = null;
							}

							if(m4 != null){
								gui.onTouchEvent(m4);
								m4 = null;
							}*/

							isActive = false;
							gui.setActive(false);
							countDown = false;
							gui.setCountDown(false);
							countDownThread.setRunning(false);
							countDownThread = null;
							countDownThread = new CountDown(gui, this);
						}
						calc.hasGone(m.getX(), m.getY());								
						break;

					default:
						break;
					}

					gui.putPoints(calc.getCoordinates());

					callDraw();
				}
			}else if(isActive){
				float[] xy = energize.getNextXY();
				
				if(xy != null)
					gui.setXY(xy[0], xy[1]);
				else
					energize.resetPoints();
				
				callDraw();
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
	
	public void winner(float x, float y){
		gui.winner(x,y);
		callDraw();
	}

	public void setActive(boolean active){
		this.isActive = active;
	}

	public void setCountDown(boolean countDown){
		this.countDown = countDown;
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
