package com.flyvemedia.WhoIsNext;

import java.util.TreeSet;

public class Calculator{
	
	private TreeSet<Coordinates> points;
	private int IDCounter;
	
	public Calculator() {
		points = new TreeSet<Coordinates>();
		IDCounter = 0;
	}
	
	private Coordinates getClosest(float x, float y){
		if(points.isEmpty())
			return null;
		
		Coordinates.dx = x;
		Coordinates.dy = y;
		
		//B¿r lage en egen klasse som er spesialisert til Œ hente ut n¾rmeste koordinater,
		//ved ett kall (get(float x, floaty y))
		
		return points.first();
	}
	
	public boolean hasCome(float x, float y){
		System.out.println("Coordinates: has come x:"+x+" y"+ y);
		
		if(x < 0 || y < 0)
			return false;
		
		return points.add(new Coordinates(x, y, IDCounter++));
	}
	
	public boolean hasGone(float x, float y){
		System.out.println("Coordinates: has gone x:"+x+" y"+ y);
		
		Coordinates c = getClosest(x, y);
		return points.remove(c);
	}
	
	public float[][] getCoordinates(){
		float[][] coordinates = new float[points.size()][2];
		//Coordinates[] cObjects = new Coordinates[points.size()];
		//cObjects = points.toArray(cObjects);
		
		/*for(int i = 0; i < cObjects.length; i++){
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
		}*/
		int i = 0;
		for(Coordinates c : points){
			coordinates[i][0] = c.getX();
			coordinates[i++][1] = c.getY();
		}
		
		return coordinates;
	}
		
}
