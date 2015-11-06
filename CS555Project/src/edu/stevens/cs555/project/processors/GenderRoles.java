package edu.stevens.cs555.project.processors;

import edu.stevens.cs555.project.Family;
import edu.stevens.cs555.project.GEDCOMValidationException;
import edu.stevens.cs555.project.Individual;

public class GenderRoles extends GEDCOMProcessor {

	public GenderRoles() {
		super("US21");
	}

	@Override
	public void run(Family[] families, Individual[] individuals) {
		for(Family f : families) {
			if (f == null) {
				continue;
			}
			
			int husbandID = f.getHusb();
			int wifeID = f.getWife();
			
			if(husbandID >= 0 && husbandID < individuals.length){
				Individual husband = individuals[husbandID];				
				if(!husband.getSex().equals("M")) {
					this.addValidationException(new GEDCOMValidationException("Husband " + husband.getName() + " is not a male.", f.getLineNumber()));
				}
			}
			
			if(wifeID >= 0 && wifeID < individuals.length){
				Individual wife = individuals[wifeID];				
				if(!wife.getSex().equals("F")) {
					this.addValidationException(new GEDCOMValidationException("Wife " + wife.getName() + " is not a female.", f.getLineNumber()));
				}
			}
			
		}

	}

}
