package edu.stevens.cs555.project.processors;

import edu.stevens.cs555.project.Family;
import edu.stevens.cs555.project.GEDCOMValidationException;
import edu.stevens.cs555.project.Individual;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class MaleLastNames extends GEDCOMProcessor {

	public MaleLastNames() {
		super("US16");
	}
	
	@Override
	public void run(Family[] families, Individual[] individuals) {
		for (Family family : families) {
            if (family == null) {
            	continue;
            }

            // Get list of last names of every male in family (sons plus husband)
            List<String> males_last_names = family.getChildren().stream()
                    .filter(i -> individuals[i] != null)
                    .map(i -> individuals[i])
                    .filter(individual -> individual.getSex().equals("M"))
                    .map(Individual::getLastName)
                    .collect(Collectors.toList());
            males_last_names.add(individuals[family.getHusb()].getLastName());

            // Convert list to HashSet to eliminate duplicates
            HashSet<String> last_names_set = new HashSet<>(males_last_names);

            // Size of HashSet should be 1 if everyone had the same name
            if (last_names_set.size() > 1) {
                this.addValidationException(new GEDCOMValidationException("Male family members have different last names.", family.getLineNumber()));
            }
        }		
	}
}
