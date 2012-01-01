package com.flyvemedia.WhoIsNext;

public class Coordinates implements Comparable<Coordinates>{
	
	private float x;
	private float y;
	public static float dx;
	public static float dy;
	private float hyp;
	private int ID;
	
	public Coordinates(float x, float y, int ID){
		this.x = x;
		this.y = y;
		dx = 0;
		dy = 0;
		hyp = -1;
		this.ID = ID;
	}

	public Coordinates(int ID){
		this.x = -1;
		this.y = -1;
		dx = 0;
		dy = 0;
		hyp = -1;
		this.ID = ID;
	}
	
	public int getID(){
		return ID;
	}
	
	public boolean setX(int x){
		if(x <= 0)
			return false;
		
		this.x = x;
		hyp = -1;
		return true;
	}
	
	public float getX(){
		return x;
	}
	
	public boolean setY(int y){
		if(y <= 0)
			return false;
		
		this.y = y;
		hyp = -1;
		return true;
	}
	
	public float getY(){
		return y;
	}
	
	public float getHyp(){
		if(hyp == -1){
			float kant1 = (dx > x) ? (dx - x) : (x - dx);
			kant1 = kant1 * kant1;

			float kant2 = (dy > y) ? (dy - y) : (y - dy);
			kant2 = kant2 * kant2;

			hyp = (float)Math.sqrt((kant1 + kant2));
		}
		
		
		return hyp;
	}
	
	
	public int compareTo(Coordinates c){
		return this.ID - c.ID;
		//return (int)(getHyp() - c.getHyp());
	}

}