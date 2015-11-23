package edu.stevens.cs555.project.processors;

import edu.stevens.cs555.project.Family;
import edu.stevens.cs555.project.GEDCOMValidationException;
import edu.stevens.cs555.project.Individual;

public class ListLivingMarried extends GEDCOMProcessor {
	
	public ListLivingMarried() {
		super("US30");
	}

	@Override
	public void run(Family[] families, Individual[] individuals) {
		for (Individual individual : individuals) {
            if (individual == null) {
            	continue;
            }
            
            boolean isAlive = individual.getDeat() == null;
            boolean isMarried = families[individual.getFamc()] != null;
            
            if (isAlive && isMarried) {
            	this.addValidationException(new GEDCOMValidationException(individual.getName() + " is living and married.", individual.getLineNumber()));
            }
		}
	}
}
