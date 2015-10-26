package edu.stevens.cs555.project.processors;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import edu.stevens.cs555.project.Family;
import edu.stevens.cs555.project.GEDCOMValidationException;
import edu.stevens.cs555.project.Individual;

public class DivorceBeforeDeath extends GEDCOMProcessor {

	public DivorceBeforeDeath() {
		super("US06");	
	}
	
	protected boolean validMarrDate = true;
	SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy");
	
	public static boolean isValidDivo(Date deatdate, Date divodate) {
		if(deatdate.before(divodate)){
		    return false;
		}
	    return true;
	}
	
	
	@Override
	public void run(Family[] families, Individual[] individuals) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy");
		int IDi = 0;
		Date death, divor;

		for(Family f : families) {
			if(f != null) {			
				String divo = f.getDiv();
				if(divo != null) {
						IDi = f.getId();
						for(Individual i : individuals) 
						{
							if(i != null && i.getId() == IDi) {
								String deat = i.getDeat();
								if(deat != null) {
									try {
										//System.out.println(i.getId());
										death = formatter.parse(deat);
										divor = formatter.parse(divo);
											System.out.println(death + " " + divor);
											if(DivorceBeforeDeath.isValidDivo(death, divor) == false) {
												this.addValidationException(new GEDCOMValidationException(i.getName() + " 's Death Date is earlier than Divorce date", i.getLineNumber()));
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
