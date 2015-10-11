package edu.stevens.cs555.project.processors;

import edu.stevens.cs555.project.Family;
import edu.stevens.cs555.project.GEDCOMValidationException;
import edu.stevens.cs555.project.Individual;

import java.util.HashMap;

/**
 * A GEDCOM function for ensuring all individuals in a GEDCOM file have unique names and birth dates.
 * 
 * @author garrettshevach@gmail.com
 *
 */
public class UniqueNameAndBirth extends GEDCOMProcessor {

	public UniqueNameAndBirth() {
		super("US23");
	}

    /*
     * (non-Javadoc)
     * @see edu.stevens.cs555.project.processors.GEDCOMProcessor#run(edu.stevens.cs555.project.Family[], edu.stevens.cs555.project.Individual[])
     */
    @Override
    public void run(Family[] families, Individual[] individuals) {
        if(individuals == null)
            return;

        /*
         * To complete this function, we loop through each individual, and place the record in a hash map where the key is a concatenation
         * of the individual's name and birth date. Therefore, if a record already exists with the same key when trying to add a new record
         * to the hashmap, then we know that record has the duplicate name and birth date.
         */
        HashMap<String, Individual> hash_map = new HashMap<String, Individual>();
        for(Individual i : individuals) {
            if(i != null) {
                if(hash_map.get(i.getName() + i.getBirt()) == null)
                    hash_map.put(i.getName() + i.getBirt(), i);
                else
                    this.addValidationException(new GEDCOMValidationException("A record with the same name and birth date as this individual already exists!", i.getLineNumber()));
            }
        }
    }
}
