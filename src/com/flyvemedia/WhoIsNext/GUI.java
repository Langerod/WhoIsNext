package com.flyvemedia.WhoIsNext;

import android.view.SurfaceHolder.Callback;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

public class GUI extends SurfaceView {

	private SurfaceHolder holder;
	private GameLoop gameLoop;
	
	private int[][] points;
	private int ex;
	private int ey;
	private boolean isActive;
	private boolean countDown;
	private String countDownTime;
	private int horizontal;
	private int vertical;
	
	private int height;
	private int width;
	
	private Paint cdText;
		
	public GUI(WhoIsNextActivity activity, Calculator calc) {
		super(activity);
				
		points = null;
		ex = -1;
		ey = -1;
		
		
		
		Display display = activity.getWindowManager().getDefaultDisplay(); 
		width = display.getWidth();
		height = display.getHeight();
		
		System.out.println(width+" "+height);

		cdText =  new Paint();
		cdText.setColor(Color.WHITE);
		cdText.setTextSize(width / 20);
		
		
		//horizontal = activity.
				
		gameLoop = new GameLoop(this, calc);
		holder = getHolder();
		holder.addCallback(new Callback() {
			
			@Override
			public void surfaceDestroyed(SurfaceHolder holder) {
				gameLoop.setRunning(false);
			}
			
			@Override
			public void surfaceCreated(SurfaceHolder holder) {
				gameLoop.setRunning(true);
				gameLoop.start();
			}
			
			@Override
			public void surfaceChanged(SurfaceHolder holder, int format, int width,
					int height) {
				
			}
			
			
		});
		System.out.println("GUI: constructor");
		invalidate();
	}
	
	public void putPoints(int[][] points){
		this.points = points;
	}
	
	public void setCountDown(boolean countDown){
		this.countDown = countDown;
	}
	
	public void setCountDownTime(String countDownTime){
		this.countDownTime = countDownTime;
	}
	
	public void setActive(boolean active){
		this.isActive = active;
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if(event.getAction() != MotionEvent.ACTION_MOVE){
			gameLoop.addMotionEvent(MotionEvent.obtainNoHistory(event));
		}
		return true;
	}

	@Override
	protected void onDraw(Canvas canvas) {
				
		canvas.drawColor(Color.GRAY);
		canvas.drawText("Test", width / 2 - width / 8, height / 2 - height / 20, cdText);
		
		if(countDown){
			canvas.drawText(countDownTime, 100f, 400f, cdText);
		}
		
		
		
		
		super.onDraw(canvas);
	}
}
