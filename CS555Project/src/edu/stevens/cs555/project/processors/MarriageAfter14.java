package edu.stevens.cs555.project.processors;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import edu.stevens.cs555.project.Family;
import edu.stevens.cs555.project.GEDCOMValidationException;
import edu.stevens.cs555.project.Individual;

/**
 * Checks that age at marriage should be more than 14
 */

public class MarriageAfter14 extends GEDCOMProcessor {

	public MarriageAfter14() {
		super("US10");	
	}
	
	SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy");
	
	public static int getAge(Date birtdate, Date marrdate) {
		Calendar marriDate = Calendar.getInstance();
	    Calendar birthDate = Calendar.getInstance();

	    int age = 0;
	    marriDate.setTime(marrdate);
	    birthDate.setTime(birtdate);
	    age = marriDate.get(Calendar.YEAR) - birthDate.get(Calendar.YEAR);
	    
	return age;
	}
	
	@Override
	public void run(Family[] families, Individual[] individuals) {
		int IDi = 0;
		int ageAtMarriage= 0;
		Date birth, marri;

		for(Family f : families) {
			if(f != null) {			
				String marr = f.getMarr();
				if(marr != null) {
						IDi = f.getId();
						for(Individual i : individuals) 
						{
							if(i != null && i.getId() == IDi) {
								String birt = i.getBirt();
								if(birt != null) {
									try {
										//System.out.println(i.getId());
										birth = formatter.parse(birt);
										marri = formatter.parse(marr);
										ageAtMarriage = MarriageAfter14.getAge(birth, marri); 
											if( ageAtMarriage < 14 ) {
												this.addValidationException(new GEDCOMValidationException(i.getName() + " 's Age at Marriage is less than  14", i.getLineNumber()));
											}		
										}
														
									catch (ParseException e) {
										//syntax error
									}
								}
								
							}
						}

				}						
			}
		}
		
	}
}



