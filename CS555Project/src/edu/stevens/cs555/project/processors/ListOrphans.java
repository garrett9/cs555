package edu.stevens.cs555.project.processors;

import edu.stevens.cs555.project.Family;
import edu.stevens.cs555.project.GEDCOMValidationException;
import edu.stevens.cs555.project.Individual;

public class ListOrphans extends GEDCOMProcessor {
	
	public ListOrphans() {
		super("US33");
	}

	@Override
	public void run(Family[] families, Individual[] individuals) {
		for (Individual individual : individuals) {
            if (individual == null) {
            	continue;
            }
         
            if(individual.getAge() < 18) {
            	Family f = families[individual.getFamc()];
            	Individual father = individuals[f.getHusb()];
            	Individual mother = individuals[f.getWife()];
            	if(father.getDeat() != null && mother.getDeat() != null) {
            		this.addValidationException(new GEDCOMValidationException(individual.getName() + " is orphaned.", individual.getLineNumber()));
            	}
            }
            
            
		}

	}

}
