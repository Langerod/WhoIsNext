package com.flyvemedia.WhoIsNext;

import java.util.TreeSet;

public class Calculator{
	
	private TreeSet<Coordinates> points;
	
	public Calculator() {
		points = new TreeSet<Coordinates>();
	}
	
	public boolean hasMoved(int x, int y){
		Coordinates c = getClosest(x, y);
		
		if(c == null)
			return false;
		
		c.setX(x);
		c.setY(y);
		
		return true;
	}
	
	private Coordinates getClosest(int x, int y){
		if(points.isEmpty())
			return null;
		
		for(Coordinates c : points)
			c.setMotion(x, y);
		
		return points.first();
	}
	
	public boolean hasCome(int x, int y){
		if(x < 0 || y < 0)
			return false;
		
		return points.add(new Coordinates(x, y));
	}
	
	public boolean hasGone(int x, int y){
		Coordinates c = getClosest(x, y);
		return points.remove(c);
	}
	
	public int[][] getCoordinates(){
		int[][] coordinates = new int[points.size()][2];
		int i = 0;
		
		for(Coordinates c : points){
			coordinates[i][0] = c.getX();
			coordinates[i][1] = c.getY();
		}
		
		return coordinates;
	}
		
}
