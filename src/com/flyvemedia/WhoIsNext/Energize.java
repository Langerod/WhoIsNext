package com.flyvemedia.WhoIsNext;

public class Energize{

	private GUI gui;
	private GameLoop gameLoop;

	
	private int stepIndex;
	private float[][] steps;
	
	private int currentStepRound;
	
	private int numStepIndex;
	private int[] numSteps = {3, 8};
	
	private int currentRound;
	
	private int roundsIndex;
	private int[] rounds = {3, 2};
	
	private int pointsIndex;
	private float[][] points;	

	public Energize(GUI gui, GameLoop gameLoop) {
		this.gui = gui;
		this.gameLoop = gameLoop;
		this.points = null;
	}

	public float[] getNextXY(){
		float[] xy = new float[2];

		if(points == null){
			points = gui.getPoints();
			pointsIndex = (int)(Math.random() * points.length);
			currentRound = 0;
			currentStepRound = 0;
			roundsIndex = 0;
			numStepIndex = 0;
			stepIndex = 0;
		}

		if(steps != null){
			if(++stepIndex == steps.length){
				stepIndex = 0;
				if(++currentStepRound == points.length){
					currentStepRound = 0;

					if(++currentRound == rounds[roundsIndex]){
						currentRound = 0;
						if(++numStepIndex == numSteps.length || ++roundsIndex == rounds.length){
							if(++pointsIndex != points.length){
								gameLoop.winner(points[pointsIndex][0], points[pointsIndex][0]);
							}else{
								gameLoop.winner(points[0][0], points[0][0]);
							}
							return null;
						}
					}
				}
				if(pointsIndex + 1 == points.length){
					steps = calcSteps(points[pointsIndex][0], points[pointsIndex][1],
							points[0][0], points[0][1], numSteps[numStepIndex]);
					pointsIndex = 0;
				}else{
					steps = calcSteps(points[pointsIndex][0], points[pointsIndex][1],
							points[pointsIndex + 1][0], points[pointsIndex + 1][1], numSteps[numStepIndex]);
					pointsIndex++;
				}
			}
		}else{
			if(pointsIndex + 1 == points.length){
				steps = calcSteps(points[pointsIndex][0], points[pointsIndex][1],
						points[0][0], points[0][1], numSteps[numStepIndex]);
				pointsIndex = 0;
			}else{
				steps = calcSteps(points[pointsIndex][0], points[pointsIndex][1],
						points[pointsIndex + 1][0], points[pointsIndex + 1][1], numSteps[numStepIndex]);
				pointsIndex++;
			}			
		}
			

		/*System.out.println("stepIndex"+stepIndex+" steps.length"+steps.length+" currentStepRound"+currentStepRound
				+" numStepIndex"+numStepIndex+" numSteps.length"+numSteps.length);
		System.out.println("currentRound"+currentRound+" rundsIndex"+roundsIndex+" rounds.length"+rounds.length
				+" pointsIndex"+pointsIndex+" points.length"+points.length);
*/
		xy[0] = steps[stepIndex][0];
		xy[1] = steps[stepIndex][1];
		
		return xy;
	}

	private float[][] calcSteps(float x, float y, float dx, float dy, int steps){
		float[][] dots = new float[steps][2];
		steps++;
		float xLength = (dx > x) ? (dx - x) / steps : (x - dx) / steps;
		float yLength = (dy > y) ? (dy - y) / steps : (y - dy) / steps;
		float startX = (dx > x) ? x : dx;
		float startY = (dy > y) ? y : dy;

		for(int i = 0; i < dots.length; i++){
			dots[i][0] = startX + (i * xLength);
			dots[i][1] = startY + (i * yLength);
		}

		return dots;
	}
	
	public void resetPoints(){
		points = null;
	}
}
