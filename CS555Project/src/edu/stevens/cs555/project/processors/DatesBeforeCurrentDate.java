package edu.stevens.cs555.project.processors;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

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
					Date marriageDate = format.parse(family.getMarr());
					if (marriageDate != null && marriageDate.after(now)) {
						this.addValidationException(new GEDCOMValidationException("Marriage date is after current date.", family.getLineNumber()));
					}
				} catch (ParseException e) {
				}
            }
            
            // Check divorce date
            if (family.getDiv() != null) {
	            try {
					Date divorceDate = format.parse(family.getDiv());
					if (divorceDate != null && divorceDate.after(now)) {
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
					Date birthDate = format.parse(individual.getBirt());
					if (birthDate != null && birthDate.after(now)) {
						this.addValidationException(new GEDCOMValidationException("Birth date is after current date.", individual.getLineNumber()));
					}
				} catch (ParseException e) {
				}
            }
            
            // Check death date
            if (individual.getDeat() != null) {
	            try {
					Date deathDate = format.parse(individual.getDeat());
					if (deathDate != null && deathDate.after(now)) {
						this.addValidationException(new GEDCOMValidationException("Death date is after current date.", individual.getLineNumber()));
					}
				} catch (ParseException e) {
				}
            }
        }
    }
}
