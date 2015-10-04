package edu.stevens.cs555.project.processors;

import edu.stevens.cs555.project.Family;
import edu.stevens.cs555.project.Individual;

/**
 * Checks that dates (birth, marriage, divorce, death) should not be after the current date
 */
public class DatesBeforeCurrentDate extends GEDCOMProcessor {
    public DatesBeforeCurrentDate() {
        super("US01");
    }

    @Override
    public void run(Family[] families, Individual[] individuals) {
        for (Family family : families) {
            // TODO - write this
        }

        // TODO - finish
    }
}
