package edu.stevens.cs555.project.processors;

import edu.stevens.cs555.project.Family;
import edu.stevens.cs555.project.GEDCOMValidationException;
import edu.stevens.cs555.project.Individual;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Under150 extends GEDCOMProcessor {

	public Under150() {
		super("US07");
	}
	
	private static int getDiffYears(Date first, Date last) {
	    Calendar a = getCalendar(first);
	    Calendar b = getCalendar(last);
	    int diff = b.get(Calendar.YEAR) - a.get(Calendar.YEAR);
	    if (a.get(Calendar.MONTH) > b.get(Calendar.MONTH) || 
	        (a.get(Calendar.MONTH) == b.get(Calendar.MONTH) && a.get(Calendar.DATE) > b.get(Calendar.DATE))) {
	        diff--;
	    }
	    return diff;
	}

	private static Calendar getCalendar(Date date) {
	    Calendar cal = Calendar.getInstance(Locale.US);
	    cal.setTime(date);
	    return cal;
	}
	
	@Override
	public void run(Family[] families, Individual[] individuals) {
		DateFormat df = new SimpleDateFormat("d MMM yyyy");
		for(Individual i : individuals) {
			if(i != null) {
				String birt = i.getBirt();
				String deat = i.getDeat();
				Date birth, death;
				if(birt != null) {
					try {					
						birth = df.parse(i.getBirt());
						if(deat != null) {
							try {
								death = df.parse(i.getDeat());
								if(Under150.getDiffYears(birth, death) > 150) {
									this.addValidationException(new GEDCOMValidationException(i.getName() + " lived to be over 150 years old.", i.getLineNumber()));
								}
								
							} catch (ParseException e) {											
								//syntax error?
							}
						} else {						
							if(Under150.getDiffYears(birth, new Date()) > 150) {
								this.addValidationException(new GEDCOMValidationException(i.getName() + " is over 150 years old.", i.getLineNumber()));
							}
						}
						
					} catch (ParseException e) {
						//syntax error with birth?
					}
				}						
			}			
		}
			
	}

}
