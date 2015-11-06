package edu.stevens.cs555.project.processors;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import edu.stevens.cs555.project.Family;
import edu.stevens.cs555.project.GEDCOMValidationException;
import edu.stevens.cs555.project.Individual;

public class UpcomingBirthdays extends GEDCOMProcessor {

	private SimpleDateFormat format = new SimpleDateFormat("dd MMM yyyy");
	
	public UpcomingBirthdays() {
		super("US38"); 
	}

	boolean isDateWithinRange(Date testDate, Date startDate, Date endDate) {
	   return !(testDate.before(startDate) || testDate.after(endDate));
	}
	
	@Override
	public void run(Family[] families, Individual[] individuals) {
		Calendar c = new GregorianCalendar();
		c.add(Calendar.DATE, 30);
		
		Date currentDate = new Date();
		Date dateIn30Days = c.getTime();
		
		for(Individual i : individuals) {
			if(i == null) {
				continue;
			}
			
			Date birthday;
			try {
				birthday = format.parse(i.getBirt());
				birthday.setYear(currentDate.getYear());	//set the birthday to this year
				
				if(isDateWithinRange(birthday, currentDate, dateIn30Days)) {
					this.addValidationException(new GEDCOMValidationException(i.getName() + "'s birthday occurs in the next 30 days!", i.getLineNumber()));
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}			
			
		}

	}

}
