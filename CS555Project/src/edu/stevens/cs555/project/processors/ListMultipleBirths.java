package edu.stevens.cs555.project.processors;

import java.util.ArrayList;
import java.util.HashMap;

import edu.stevens.cs555.project.Family;
import edu.stevens.cs555.project.GEDCOMValidationException;
import edu.stevens.cs555.project.Individual;

/**
 * Lists all multiple births in a GEDCOM file.
 * 
 * @author garrettshevach@gmail.com
 *
 */
public class ListMultipleBirths extends GEDCOMProcessor {

	/**
	 * The constructor.
	 */
	public ListMultipleBirths() 
	{
		super("US32");
	}

	@Override
	public void run(Family[] families, Individual[] individuals) {
		/*
		 * To figure out if there is a multiple birth in a family, we check the birth dates of each married couple's kids to see if they match exactly.
		 * Therefore, we must loop through each family, and then each child within that family.
		 */

		for(Family family : families) {
			if(family != null) {
				ArrayList<Integer> children = family.getChildren();
				if(children.size() > 0) {
					HashMap<String, Individual> map = new HashMap<String, Individual>();
					for(int i : children) {
						if(individuals[i] != null) {
							if(map.get(individuals[i].getBirt()) != null) {
								this.addValidationException(new GEDCOMValidationException("The family at this record has had a multiple birth.", family.getLineNumber()));
								break;
							}
							map.put(individuals[i].getBirt(), individuals[i]);
						}
					}
				}
			}
		}

	}

}
