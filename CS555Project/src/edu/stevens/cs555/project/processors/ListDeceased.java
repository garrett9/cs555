package edu.stevens.cs555.project.processors;

import edu.stevens.cs555.project.Family;
import edu.stevens.cs555.project.GEDCOMValidationException;
import edu.stevens.cs555.project.Individual;

public class ListDeceased extends GEDCOMProcessor {

	public ListDeceased() {
		super("US29");
	}

	@Override
	public void run(Family[] families, Individual[] individuals) {
		for(Individual i : individuals) {
			if(i != null) {
				if(i.getDeat() != null) {
					this.addValidationException(new GEDCOMValidationException(i.getName() + " is deceased.", i.getLineNumber()));				
				}
			}
		}

	}

}
