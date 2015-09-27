package edu.stevens.cs555.project;

public abstract class Rule {
	
	protected String id;
	
	public abstract void test(Family[] families, Individual[] individuals);
	
	public void printAnomaly(String anomaly) {
		System.out.println("Anomoly " + this.id + ": " + anomaly);
	}
	
	public void printError(String error) {
		System.out.println("Error " + this.id + ": " + error);
	}
	
}
