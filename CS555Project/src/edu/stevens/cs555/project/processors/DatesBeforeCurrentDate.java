package edu.stevens.cs555.project.processors;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import edu.stevens.cs555.project.Family;
import edu.stevens.cs555.project.GEDCOMValidationException;
import edu.stevens.cs555.project.Individual;

/**
 * Checks that dates (birth, marriage, divorce, death) should not be after the current date
 */
public class DatesBeforeCurrentDate extends GEDCOMProcessor {
	
	DateFormat format;
	
    public DatesBeforeCurrentDate() {
        super("US01");
        format = new SimpleDateFormat("d MMM yyyy");
    }

    /*
     * Check if a GEDCOM date (string) is after the current date
     * @return false if the gedcomDate is after the current date, true otherwise
     */
    private boolean isValidGEDCOMDate(String gedcomDate) {
    	Date now = new Date();
    	
    	// Check marriage date
        if (gedcomDate != null) {
            try {
				Date date = format.parse(gedcomDate);
				if (date != null && date.after(now)) {
					return false;
				}
			} catch (ParseException e) {
				e.printStackTrace();				
			}
        }
        
        return true;
    	
    }
    
    private void checkFamilyMarriage(Family family) {   	
    	if(!isValidGEDCOMDate(family.getMarr())) {
    		this.addValidationException(new GEDCOMValidationException("Marriage date is after current date.", family.getLineNumber()));
    	}
    }
    
    private void checkFamilyDivorce(Family family) {
    	if(!isValidGEDCOMDate(family.getDiv())) {
    		this.addValidationException(new GEDCOMValidationException("Divorce date is after current date.", family.getLineNumber()));
    	}
    }
    
    private void checkIndividualBirth(Individual individual) {
    	if(!isValidGEDCOMDate(individual.getBirt())) {
    		this.addValidationException(new GEDCOMValidationException("Birth date is after current date.", individual.getLineNumber()));
    	}    	
    }
    
    private void checkIndividualDeath(Individual individual) {
    	if(!isValidGEDCOMDate(individual.getDeat())) {
    		this.addValidationException(new GEDCOMValidationException("Death date is after current date.", individual.getLineNumber()));
    	}    	
    }
    
    @Override
    public void run(Family[] families, Individual[] individuals) {
    	
    	// Check dates associated with families
        for (Family family : families) {
            if (family == null) {
            	continue;
            }                       
            
            checkFamilyMarriage(family);
            checkFamilyDivorce(family);
            
        }

        // Check dates associated with individuals
        for (Individual individual : individuals) {
            if (individual == null) {
            	continue;
            }
            
            checkIndividualBirth(individual);
            checkIndividualDeath(individual);
            
        }
    }
}
