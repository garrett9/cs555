package edu.stevens.cs555.project.processors;

import edu.stevens.cs555.project.Family;
import edu.stevens.cs555.project.GEDCOMValidationException;
import edu.stevens.cs555.project.Individual;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Checks that children are born before deaths of parents
 */
public class BirthBeforeDeathOfParents extends GEDCOMProcessor {

	public BirthBeforeDeathOfParents() {
		super("US09");
	}
	
	@Override
	public void run(Family[] families, Individual[] individuals) {
		SimpleDateFormat format = new SimpleDateFormat("d MMM yyyy");

		for (Family family : families) {
			if (family == null) {
				continue;
			}

            // Get death date of husband and wife
			Date husbDeath = null;
			Date wifeDeath = null;

			try {
				int husbId = family.getHusb();
				Individual husb = individuals[husbId];
				if (husb != null) {
					String husbDeat = husb.getDeat();
					if (husbDeat != null) {
						husbDeath = format.parse(husbDeat);
					}
				}

				int wifeId = family.getWife();
				Individual wife = individuals[wifeId];
				if (wife != null) {
					String wifeDeat = wife.getDeat();
					if (wifeDeat != null) {
						wifeDeath = format.parse(wifeDeat);
					}
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}


            // Get IDs of children
			ArrayList<Integer> childrenIds = family.getChildren();
			if (childrenIds == null || childrenIds.isEmpty()) {
				continue;
			}

            // Check births for each child
			try {
                for (Integer childId : childrenIds) {
                    Individual child = individuals[childId];
                    if (child == null) {
                        continue;
                    }

                    String birt = child.getBirt();
                    if (birt == null) {
                        continue;
                    }

                    Date birthDate = format.parse(birt);

                    // Check with date of mother's death
                    if (wifeDeath != null && birthDate.after(wifeDeath)) {
                        this.addValidationException(new GEDCOMValidationException("Birth date of child is after mother's death.", child.getLineNumber()));
                    }

                    // Check with date of father's death (plus 9 months)
                    if (husbDeath != null) {
                        Calendar c = Calendar.getInstance();
                        c.setTime(husbDeath);
                        c.add(Calendar.MONTH, 9);
                        
                        if (birthDate.after(c.getTime())) {
                            this.addValidationException(new GEDCOMValidationException("Birth date of child is over 9 months after father's death", child.getLineNumber()));
                        }
                    }
                }
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
	}
}
