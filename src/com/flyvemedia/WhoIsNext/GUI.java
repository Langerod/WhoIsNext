package com.flyvemedia.WhoIsNext;

import android.view.SurfaceHolder.Callback;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

public class GUI extends SurfaceView {

	private SurfaceHolder holder;
	private GameLoop gameLoop;
	
	private int[][] points;
	private int ex;
	private int ey;
	private boolean countDown;
	private String countDownTime;
	private int horizontal;
	private int vertical;
		
	public GUI(WhoIsNextActivity activity, Calculator calc) {
		super(activity);
				
		points = null;
		ex = -1;
		ey = -1;
		
		//horizontal = activity.
				
		gameLoop = new GameLoop(this, calc);
		holder = getHolder();
		holder.addCallback(new Callback() {
			
			@Override
			public void surfaceDestroyed(SurfaceHolder holder) {}
			
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

	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawColor(Color.GRAY);
		
		
		if(countDown){
			//canvas.drawText(countDownTime, x, y, paint)
		}
		
		
		
		
		super.onDraw(canvas);
	}
}
