package edu.stevens.cs555.project.processors;

import edu.stevens.cs555.project.Family;
import edu.stevens.cs555.project.Individual;

import java.text.DateFormat;
import java.text.ParseException;
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
		DateFormat df = DateFormat.getDateInstance();
		for(int i = 0; i < individuals.length; i++) {
			Individual individual = individuals[i];
			if(individual == null) {
				continue;
			}							
			String birt = individual.getBirt();
			String deat = individual.getDeat();
			Date birth, death;
			this.printAnomaly("Under 150");
			if(birt != null) {
				try {
					
					birth = df.parse(individual.getBirt());
					System.out.println(birth);
					if(deat != null) {
						try {
							death = df.parse(individual.getDeat());
							if(Under150.getDiffYears(birth, death) > 150) {
	//							return false;
							}
							//death - birth < 150
							
						} catch (ParseException e) {											
							//syntax error?
						}
					} else {
						//now - birth < 150
					}
					
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
//					return false;
				}
			}						
			
		}
			
	}

}
