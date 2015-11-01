package edu.stevens.cs555.project.processors;

import edu.stevens.cs555.project.Family;
import edu.stevens.cs555.project.GEDCOMValidationException;
import edu.stevens.cs555.project.Individual;

public class FewerThan15Siblings extends GEDCOMProcessor {

	public FewerThan15Siblings() {
		super("US15");
	}
	
	@Override
	public void run(Family[] families, Individual[] individuals) {
		for (Family family : families) {
            if (family == null) {
            	continue;
            }

			if (family.getChildren().size() >= 15) {
				this.addValidationException(new GEDCOMValidationException("Family has 15 or more siblings.", family.getLineNumber()));
			}
        }		
	}
}
