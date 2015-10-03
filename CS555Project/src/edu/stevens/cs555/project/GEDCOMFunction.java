package edu.stevens.cs555.project;

import java.util.ArrayList;

/**
 * An abstract rule class to be extended from when implementing a custom rule.
 */
public abstract class GEDCOMFunction {
	
    	/**
    	 * The ID of the rule.
    	 */
	protected String id;
	
	/**
	 * An array list of validation exceptions that will hold any possible validation errors when running the GEDCOMFunction.
	 */
	private ArrayList<GEDCOMValidationException> validation_exceptions =  new ArrayList<GEDCOMValidationException>();
	
	/**
	 * Test the rule against the provided list of families and individuals.
	 * 
	 * @param families The list of familes.
	 * @param individuals The list of individuals.
	 */
	public abstract void run(Family[] families, Individual[] individuals);
	
	public void printAnomaly(String anomaly) {
		System.out.println("Anomoly " + this.id + ": " + anomaly);
	}
	
	/**
	 * Adds a GEDCOMValidationException to the GEDCOMFunction's list of validations exceptions.
	 * 
	 * @param e The GEDCOMValidationException to add.
	 */
	public void addValidationException(GEDCOMValidationException e) {
	    this.validation_exceptions.add(e);
	}
	
	/**
	 * Returns the GEDCOMValidationExceptions that occured when running the function. Returns null if the function hasn't been run with the {@link #run(Family[], Individual[])} method, or if there are no validation errors.
	 * 
	 * @return The GEDCOMValidationExceptions
	 */
	public ArrayList<GEDCOMValidationException> getValidationErrors() {
	    return this.validation_exceptions;
	}
}
