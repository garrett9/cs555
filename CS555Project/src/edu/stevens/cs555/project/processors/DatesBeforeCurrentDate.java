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
	
    public DatesBeforeCurrentDate() {
        super("US01");
    }

    @Override
    public void run(Family[] families, Individual[] individuals) {
    	Date now = new Date();
    	DateFormat format = new SimpleDateFormat("d MMM yyyy");
    	
    	// Check dates associated with families
        for (Family family : families) {
            if (family == null) {
            	continue;
            }
            
            // Check marriage date
            if (family.getMarr() != null) {
	            try {
					Date marriage_date = format.parse(family.getMarr());
					if (marriage_date != null && marriage_date.after(now)) {
						this.addValidationException(new GEDCOMValidationException("Marriage date is after current date.", family.getLineNumber()));
					}
				} catch (ParseException e) {
				}
            }
            
            // Check divorce date
            if (family.getDiv() != null) {
	            try {
					Date divorce_date = format.parse(family.getDiv());
					if (divorce_date != null && divorce_date.after(now)) {
						this.addValidationException(new GEDCOMValidationException("Divorce date is after current date.", family.getLineNumber()));
					}
				} catch (ParseException e) {
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
					Date birth_date = format.parse(individual.getBirt());
					if (birth_date != null && birth_date.after(now)) {
						this.addValidationException(new GEDCOMValidationException("Birth date is after current date.", individual.getLineNumber()));
					}
				} catch (ParseException e) {
				}
            }
            
            // Check death date
            if (individual.getDeat() != null) {
	            try {
					Date death_date = format.parse(individual.getDeat());
					if (death_date != null && death_date.after(now)) {
						this.addValidationException(new GEDCOMValidationException("Death date is after current date.", individual.getLineNumber()));
					}
				} catch (ParseException e) {
				}
            }
        }
    }
}
