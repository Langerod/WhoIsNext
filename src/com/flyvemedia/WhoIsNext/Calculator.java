package com.flyvemedia.WhoIsNext;

import java.util.TreeSet;

public class Calculator{
	
	private TreeSet<Coordinates> points;
	private int IDCounter;
	
	public Calculator() {
		points = new TreeSet<Coordinates>();
		IDCounter = 0;
	}
	
	private Coordinates getClosest(int x, int y){
		System.out.println("Coordinates: is empty? "+ points.isEmpty());
		if(points.isEmpty())
			return null;
		
		for(Coordinates c : points)
			c.setMotion(x, y);
		
		return points.first();
	}
	
	public boolean hasCome(int x, int y){
		
		System.out.println("Coordinates: has come x:"+x+" y"+ y);
		
		if(x < 0 || y < 0)
			return false;
		
		return points.add(new Coordinates(x, y, IDCounter++));
	}
	
	public boolean hasGone(int x, int y){
		Coordinates c = getClosest(x, y);
		return points.remove(c);
	}
	
	public int[][] getCoordinates(){
		int[][] coordinates = new int[points.size()][2];
		Coordinates[] cObjects = new Coordinates[points.size()];
		cObjects = points.toArray(cObjects);
		
		for(int i = 0; i < cObjects.length; i++){
			Coordinates c = null;
			
			for(int j = 0; j < cObjects.length; j++){
				if(c != null && cObjects[j] != null && (c.getID() > cObjects[j].getID())){
					c = cObjects[j];
					cObjects[j] = null;
				}else if(c == null && cObjects[j] != null){
					c = cObjects[j];
					cObjects[j] = null;
				}
			}
			
			if(c != null){
				coordinates[i][0] = c.getX();
				coordinates[i][1] = c.getY();
			}
		}
		return coordinates;
	}
		
}
