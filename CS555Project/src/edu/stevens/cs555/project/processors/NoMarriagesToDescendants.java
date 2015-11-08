package edu.stevens.cs555.project.processors;

import edu.stevens.cs555.project.Family;
import edu.stevens.cs555.project.GEDCOMValidationException;
import edu.stevens.cs555.project.Individual;

/**
 * A GEDCOMProcessor for ensuring that no parents are married to any of their descendants.
 * 
 * @author garrettshevach@gmail.com
 *
 */
public class NoMarriagesToDescendants extends GEDCOMProcessor {

	/**
	 * The constructor.
	 */
	public NoMarriagesToDescendants() {
		super("US17");
	}

	/**
	 * Processes a single family, and all descendent families via recursion to ensure that no parent marries a descendent.
	 * 
	 * @param parent The Parent family that is being checked to see if its spouses are married to any descendent.
	 * @param check The family being checked to see if its children are marred to the parent's spouses.
	 * @param familes The array of Family instances.
	 * @param individuals The array of Individual instances.
	 */
	private void validateFamily(Family parent, Family check, Family[] families, Individual[] individuals) {
		if(parent == null || check == null || individuals == null)
			return;

		for(int id : check.getChildren()) {
			if(individuals[id] != null) {
				int fams = individuals[id].getFams();
				if(families[fams] != null) {
					int spouse = families[fams].getHusb();
					if(spouse == id)
						spouse = families[fams].getWife();
					if(spouse == parent.getHusb() || spouse == parent.getWife())
						this.addValidationException(new GEDCOMValidationException(individuals[id].getName() + " cannot be married to a parent he/she descended from!", individuals[id].getLineNumber()));
					else
						this.validateFamily(parent, families[fams], families, individuals);
				}
			}
		}
	}

	/**
	 * (non-Javadoc)
	 * @see edu.stevens.cs555.project.processors.GEDCOMProcessor#run(edu.stevens.cs555.project.Family[], edu.stevens.cs555.project.Individual[])
	 */
	@Override
	public void run(Family[] families, Individual[] individuals) {
		/*
		 * To complete this rule, we loop through all families, and then the children of those families to ensure that either
		 * spouse of the family has not married a descendant.
		 */
		for(Family family : families)
			this.validateFamily(family, family, families, individuals);
	}

}
