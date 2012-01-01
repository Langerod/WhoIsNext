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
	
	private float[][] points;
	private float ex;
	private float ey;
	private boolean winner;
	private boolean isActive;
	private boolean countDown;
	private String countDownTime;
	
	private int height;
	private int width;
	
	private Paint cdText;
	private Paint lines;
	private Paint ball;
	private Paint winnerBall;
		
	public GUI(WhoIsNextActivity activity, Calculator calc) {
		super(activity);
				
		points = null;
		ex = 50;
		ey = 20;
		
		Display display = activity.getWindowManager().getDefaultDisplay(); 
		width = display.getWidth();
		height = display.getHeight();
		
		winner = false;
		
		System.out.println(width+" "+height);

		cdText =  new Paint();
		cdText.setColor(Color.WHITE);
		cdText.setTextSize(width / 8);
		
		lines = new Paint();
		lines.setColor(Color.BLACK);
		lines.setStrokeWidth(2);
		
		ball = new Paint();
		ball.setColor(Color.YELLOW);		

		winnerBall = new Paint();
		winnerBall.setColor(Color.RED);		
						
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
			public void surfaceChanged(SurfaceHolder  holder, int format, int width,
					int height) {
				
			}
			
			
		});
		System.out.println("GUI: constructor");
		invalidate();
	}
	
	public void putPoints(float[][] points){
		this.points = points;
	}
	
	public float[][] getPoints(){
		return points;
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
		if(isActive && event.getAction() != MotionEvent.ACTION_UP){
			return false;
		}
		
		if(event.getAction() != MotionEvent.ACTION_MOVE){
			gameLoop.addMotionEvent(MotionEvent.obtainNoHistory(event));
		}
		return true;
	}

	@Override
	protected void onDraw(Canvas canvas) {
				
		canvas.drawColor(Color.GRAY);
		
		//System.out.println("GUI(onDraw): active("+isActive+") countDown("+countDown);
		
		if(countDown){
			canvas.drawText(countDownTime, width / 2 - width / 8, height / 2, cdText);
		}else if(isActive){
			drawLines(canvas);
			if(!winner)
				canvas.drawCircle(ex, ey, width / 30, ball);
			else
				canvas.drawCircle(ex, ey, width / 20, winnerBall);
		}
		
		super.onDraw(canvas);
	}
	
	private void drawLines(Canvas canvas){
		
		for(int i = 0; i < points.length - 1; i++){
			canvas.drawLine(points[i][0], points[i][1], points[i+1][0], points[i+1][1], lines);
		}
		
		canvas.drawLine(points[0][0], points[0][1], points[points.length-1][0], points[points.length-1][1], lines);
		
		
	}
	
	public void winner(float x, float y){
		setXY(x, y);
		winner = true;
	}
	
	public void setXY(float x, float y){
		ex = x;
		ey = y;
	}

	public void gotWinner(boolean winner) {
		this.winner = winner;		
	}
}




