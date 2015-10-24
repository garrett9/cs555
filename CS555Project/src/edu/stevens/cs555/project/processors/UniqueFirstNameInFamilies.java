package edu.stevens.cs555.project.processors;

import java.util.ArrayList;
import java.util.HashMap;

import edu.stevens.cs555.project.Family;
import edu.stevens.cs555.project.GEDCOMValidationException;
import edu.stevens.cs555.project.Individual;

/**
 * A GEDCOM Processor for validating that no more than one child with the same name and birth date should appear in a family.
 * 
 * @author garrettshevach@gmail.com
 *
 */
public class UniqueFirstNameInFamilies extends GEDCOMProcessor {

    /**
     * Instantiate a new instance of UniqueFirstNameInFamilies
     */
    public UniqueFirstNameInFamilies() {
	super("US25");
    }

    @Override
    public void run(Family[] families, Individual[] individuals) {
	if(families == null || individuals == null)
	    return;

	// For each given family
	for(Family family : families) {
	    if(family != null) {
		/*
		 * For the current family in the loop, we get the children of the family, and we also create a hash map to store a mapping of the children of the family
		 * where the key is the concatenation of the child's name and birth date. Therefore, for each child, we attempt to add it to the hashmap so long as
		 * the key to the child doesn't already exist in the map. If it does, then we know that the child has the same name and birth date as another child within the same
		 * family, meaning we have to add a GEDCOMValidationException to the class for it.
		 */
		ArrayList<Integer> children = family.getChildren();
		HashMap<String, Individual> children_map = new HashMap<String, Individual>();
		for(int child_id : children) {
		    if(individuals[child_id] != null) {
			Individual i = individuals[child_id];
			String name_date = i.getName() + i.getBirt();
			if(children_map.get(name_date) != null)
			    children_map.put(name_date, i);
			else
			    this.addValidationException(new GEDCOMValidationException("There already exists an individual with the same name and birth date as this individual within the same family!", i.getLineNumber()));
		    }
		}
	    }
	}

    }

}
