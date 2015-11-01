package edu.stevens.cs555.project.processors;

import java.util.HashMap;

import edu.stevens.cs555.project.Family;
import edu.stevens.cs555.project.GEDCOMValidationException;
import edu.stevens.cs555.project.Individual;

public class UniqueFamiliesBySpouses extends GEDCOMProcessor {

    public UniqueFamiliesBySpouses() {
	super("US24");
    }
    
    @Override
    public void run(Family[] families, Individual[] individuals) {
	if(families == null || individuals == null)
	    return;
	
	/*
	 * To complete this function, we must loop through each given family. However, we first create a hashmap to store the family records where
	 * the key of the map is a concatenation of the husband's name, wife's name, and marriage date. This way, when we attempt to add a family
	 * to this map, if the key already exists, then we know 
	 */
	HashMap<String, Family> family_map = new HashMap<String, Family>();
	for(Family family : families) {
	    if(family != null) {
		String spouses_mar = individuals[family.getHusb()].getName() + individuals[family.getWife()].getName() + family.getMarr();

		if(family_map.get(spouses_mar) == null)
		    family_map.put(spouses_mar, family);
		else
		    this.addValidationException(new GEDCOMValidationException("A family record with the same husband's name, wife's name, and marriage date as this family already exists!", family.getLineNumber()));
	    }
	}
    }

}
