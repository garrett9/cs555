package edu.stevens.cs555.project.processors;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import edu.stevens.cs555.project.Family;
import edu.stevens.cs555.project.GEDCOMValidationException;
import edu.stevens.cs555.project.Individual;

public class MarriageBeforeDeath extends GEDCOMProcessor {

	public MarriageBeforeDeath() {
		super("US05");	
	}
	
	protected boolean validMarrDate = true;
	SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy");
	
	public static boolean isValidMarr(Date deatdate, Date marrdate) {
		if(deatdate.before(marrdate)){
		    return false;
		}
	    return true;
	}
	
	
	@Override
	public void run(Family[] families, Individual[] individuals) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy");
		int IDi = 0;
		Date death, marri;

		for(Family f : families) {
			if(f != null) {			
				String marr = f.getMarr();
				if(marr != null) {
						IDi = f.getId();
						for(Individual i : individuals) 
						{
							if(i != null && i.getId() == IDi) {
								String deat = i.getDeat();
								if(deat != null) {
									try {
										//System.out.println(i.getId());
										death = formatter.parse(deat);
										marri = formatter.parse(marr);
											if(MarriageBeforeDeath.isValidMarr(death, marri) == false) {
												this.addValidationException(new GEDCOMValidationException(i.getName() + " 's Death Date is earlier than Marriage date", i.getLineNumber()));
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
