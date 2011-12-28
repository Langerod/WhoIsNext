package com.flyvemedia.WhoIsNext;

public class Coordinates implements Comparable<Coordinates>{
	
	private int x;
	private int y;
	private int dx;
	private int dy;
	private int hyp;
	
	public Coordinates(int x, int y){
		this.x = x;
		this.y = y;
		dx = 0;
		dy = 0;
		hyp = -1;
	}

	public Coordinates(){
		this.x = -1;
		this.y = -1;
		dx = 0;
		dy = 0;
		hyp = -1;
	}
	
	public boolean setX(int x){
		if(x <= 0)
			return false;
		
		this.x = x;
		hyp = -1;
		return true;
	}
	
	public int getX(){
		return x;
	}
	
	public boolean setY(int y){
		if(y <= 0)
			return false;
		
		this.y = y;
		hyp = -1;
		return true;
	}
	
	public int getY(){
		return y;
	}
	
	public boolean setMotion(int x, int y){
		if(x <= 0 || y <= 0)
			return false;
		if(x != dx || y != dy)
			hyp = -1;
		
		this.dx = x;
		this.dy = y;
		
		return true;
	}
	
	public int compareTo(Coordinates c){
		if(hyp == -1){
			int kant1 = (c.getX() > x) ? (c.getX() - x) : (x - c.getX());
			kant1 = kant1 * kant1;

			int kant2 = (c.getY() > y) ? (c.getY() - y) : (y - c.getY());
			kant2 = kant2 * kant2;

			hyp = (int)Math.sqrt((kant1 + kant2));
		}
		return hyp;
	}

}