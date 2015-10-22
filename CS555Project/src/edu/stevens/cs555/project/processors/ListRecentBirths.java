package edu.stevens.cs555.project.processors;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;
import java.util.Date;

import edu.stevens.cs555.project.Family;
import edu.stevens.cs555.project.GEDCOMValidationException;
import edu.stevens.cs555.project.Individual;

public class ListRecentBirths extends GEDCOMProcessor {

	private SimpleDateFormat format = new SimpleDateFormat("dd MMM yyyy");
	
	public ListRecentBirths() {
		super("US35");
	}
	
	private long daysBetweenDates(Date d1, Date d2) {
		return TimeUnit.DAYS.convert(d2.getTime() - d1.getTime(), TimeUnit.MILLISECONDS);
	}
	
	@Override
	public void run(Family[] families, Individual[] individuals) {
		Date now = new Date();
		for (Individual individual : individuals) {
            if (individual == null) {
            	continue;
            }
                                	
            if (individual.getBirt() != null) {
                try {
    				Date date = format.parse(individual.getBirt());
    				if (date != null && daysBetweenDates(date, now) <= 30) {    					
    					this.addValidationException(new GEDCOMValidationException(individual.getName() + " was born in the last 30 days.", individual.getLineNumber()));
    				}
    			} catch (ParseException e) {
    				e.printStackTrace();				
    			}
            }
            
        }		
	}

}
