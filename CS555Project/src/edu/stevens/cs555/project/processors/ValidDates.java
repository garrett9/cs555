package edu.stevens.cs555.project.processors;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import edu.stevens.cs555.project.Family;
import edu.stevens.cs555.project.GEDCOMValidationException;
import edu.stevens.cs555.project.Individual;

/**
 * Checks that dates are valid.
 */
public class ValidDates extends GEDCOMProcessor {
	
    public ValidDates() {
        super("US42");
    }

    @Override
    public void run(Family[] families, Individual[] individuals) {
    	DateFormat format = new SimpleDateFormat("d MMM yyyy");
    	format.setLenient(false);
    	
    	// Check dates associated with families
        for (Family family : families) {
            if (family == null) {
            	continue;
            }
            
            // Check marriage date
            if (family.getMarr() != null) {
	            try {
					format.parse(family.getMarr());
				} catch (ParseException e) {
					this.addValidationException(new GEDCOMValidationException("Marriage date is invalid: " + family.getMarr(), family.getLineNumber()));
				}
            }
            
            // Check divorce date
            if (family.getDiv() != null) {
	            try {
					format.parse(family.getDiv());
				} catch (ParseException e) {
					this.addValidationException(new GEDCOMValidationException("Divorce date is invalid: " + family.getDiv(), family.getLineNumber()));
				}
            }
        }

        // Check dates associated with individuals
        for (Individual individual : individuals) {
            if (individual == null) {
            	continue;
            }
            
            // Check birth date
            if (individual.getBirt() != null) {
	            try {
					format.parse(individual.getBirt());
				} catch (ParseException e) {
					this.addValidationException(new GEDCOMValidationException("Birth date is invalid: " + individual.getBirt(), individual.getLineNumber()));
				}
            }
            
            // Check death date
            if (individual.getDeat() != null) {
	            try {
					format.parse(individual.getDeat());
				} catch (ParseException e) {
					this.addValidationException(new GEDCOMValidationException("Death date is invalid: " + individual.getDeat(), individual.getLineNumber()));
				}
            }
        }
    }
}
