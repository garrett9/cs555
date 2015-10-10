package edu.stevens.cs555.project.processors;

import edu.stevens.cs555.project.Family;
import edu.stevens.cs555.project.GEDCOMValidationException;
import edu.stevens.cs555.project.Individual;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


/*
 * To create an exception with death date earlier than birth date from GEDCOM
 * Kevin Cho
 */
public class BirthBeforeDeath extends GEDCOMProcessor {

	public BirthBeforeDeath() {
		super("US03");
	}
	
	protected boolean validMarrDate = true;
		
    public static boolean isValidDeat(Date birtdate, Date deatdate) {
			if(birtdate.after(deatdate)){
			    return false;
			}
		    return true;
		}
	
	@Override
	public void run(Family[] families, Individual[] individuals) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy");
		
		for(Individual i : individuals) {
			if(i != null) {			
				String birt = i.getBirt();
				String deat = i.getDeat();
				Date birth, death;
				if(birt != null) {
					try {					
						birth = formatter.parse(birt);
						System.out.println(birth);
						if(deat != null) {
							try {
								death = formatter.parse(deat);
								if(BirthBeforeDeath.isValidDeat(birth, death) == false) {
									this.addValidationException(new GEDCOMValidationException(i.getName() + " 's Deathdate is earlier than birth date", i.getLineNumber()));
								}
								
							} catch (ParseException e) {											
								//syntax error
							}
						} else {
							//Left it blank
						}
					} catch (ParseException e) {
						//syntax error
					}
				}						
			}			
		}
			
	}

}
