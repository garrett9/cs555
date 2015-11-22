package edu.stevens.cs555.project.processors;

import edu.stevens.cs555.project.Family;
import edu.stevens.cs555.project.GEDCOMValidationException;
import edu.stevens.cs555.project.Individual;

public class ListLivingSingles extends GEDCOMProcessor {
	
	public ListLivingSingles() {
		super("US31");
	}

	@Override
	public void run(Family[] families, Individual[] individuals) {
		for (Individual individual : individuals) {
            if (individual == null) {
            	continue;
            }
         
            if(individual.getAge() > 30 && individual.getDeat() == null) {
            	if (families[individual.getFamc()] == null ) 
            	{
            		this.addValidationException(new GEDCOMValidationException(individual.getName() + " is a Living Single.", individual.getLineNumber()));
            	}
            	else
            	{
            	Family f = families[individual.getFamc()];
            	if(f.getIsMarried() == null) {
            		this.addValidationException(new GEDCOMValidationException(individual.getName() + " is a Living Single.", individual.getLineNumber()));
            	}
            	else if(f.getIsMarried() == false) {
            		this.addValidationException(new GEDCOMValidationException(individual.getName() + " is a Living Single.", individual.getLineNumber()));
            	}
            	}
            }
            
            
		}

	}

}
