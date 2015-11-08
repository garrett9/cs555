package edu.stevens.cs555.project.processors;

import java.util.ArrayList;

import edu.stevens.cs555.project.Family;
import edu.stevens.cs555.project.GEDCOMValidationException;
import edu.stevens.cs555.project.Individual;

/**
 * Ensures that no siblings should marry one another.
 * 
 * @author garrettshevach@gmail.com
 *
 */
public class SiblingsShouldNotMarry extends GEDCOMProcessor {

	/**
	 * The constructor.
	 */
	public SiblingsShouldNotMarry() {
		super("US18");
	}
	
	/**
	 * (non-Javadoc)
	 * @see edu.stevens.cs555.project.processors.GEDCOMProcessor#run(edu.stevens.cs555.project.Family[], edu.stevens.cs555.project.Individual[])
	 */
	@Override
	public void run(Family[] families, Individual[] individuals) {
		/*
		 * In order to complete this rule, we simply loop through each family, and then check that the children of
		 * each family have not married another child in the same family
		 */
		for(Family family : families) {
			if(family != null) {
				ArrayList<Integer> children = family.getChildren();
				for(int id : children) {
					int fams = individuals[id].getFams();
					if(families[fams] != null) {
						int spouse = families[fams].getHusb();
						if(id == spouse)
							spouse = families[fams].getWife();

						if(children.contains(spouse))
							this.addValidationException(new GEDCOMValidationException(individuals[id].getName() + " cannot be married to a sibling!", individuals[id].getLineNumber()));
					}
				}
			}
		}
	}

}
