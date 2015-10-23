package edu.stevens.cs555.project.processors;

import edu.stevens.cs555.project.Family;
import edu.stevens.cs555.project.GEDCOMValidationException;
import edu.stevens.cs555.project.Individual;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Checks that children are born after marriage and before divorce of parents
 */
public class BirthAfterMarriageOfParents extends GEDCOMProcessor {

	public BirthAfterMarriageOfParents() {
		super("US08");
	}
	
	@Override
	public void run(Family[] families, Individual[] individuals) {
		SimpleDateFormat format = new SimpleDateFormat("d MMM yyyy");

		for (Family family : families) {
			if (family == null) {
				continue;
			}

            // Get marriage and divorce
			String marr = family.getMarr();
            String div = family.getDiv();
			if (marr == null) {
				continue;
			}

            // Get IDs of children
			ArrayList<Integer> childrenIds = family.getChildren();
			if (childrenIds == null || childrenIds.isEmpty()) {
				continue;
			}

            // Check births for each child
			try {
				Date marriageDate = format.parse(marr);
                Date divorceDate = null;
                if (div != null) {
                    divorceDate = format.parse(marr);
                }

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

                    if (birthDate.before(marriageDate)) {
                        this.addValidationException(new GEDCOMValidationException("Birth date of child is before parents' marriage date.", child.getLineNumber()));
                    }

                    if (divorceDate != null && birthDate.after(divorceDate)) {
                        this.addValidationException(new GEDCOMValidationException("Birth date of child is after parents' divorce date.", child.getLineNumber()));
                    }
                }
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
	}
}
