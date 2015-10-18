package edu.stevens.cs555.project.processors;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import edu.stevens.cs555.project.Family;
import edu.stevens.cs555.project.GEDCOMValidationException;
import edu.stevens.cs555.project.Individual;

public class BirthBeforeMarriage extends GEDCOMProcessor {

	public BirthBeforeMarriage() {
		super("US02");	
	}
	
	protected boolean validMarrDate = true;
	SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy");
	
	public static boolean isValidMarr(Date birtdate, Date marrdate) {
		if(birtdate.after(marrdate)){
		    return false;
		}
	    return true;
	}
	
	
	@Override
	public void run(Family[] families, Individual[] individuals) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy");
		int IDi = 0;
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
											if(BirthBeforeMarriage.isValidMarr(birth, marri) == false) {
												this.addValidationException(new GEDCOMValidationException(i.getName() + " 's Marriage Date is earlier than birth date", i.getLineNumber()));
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



