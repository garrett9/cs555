package edu.stevens.cs555.project;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A class representing a single GEDCOM record (either FAM or INDI) from a GEDCOM file.
 * 
 * @author garrettshevach@gmail.com
 *
 */
public abstract class GEDCOMRecord {

    /**
     * The ID of the family.
     */
    protected int id;
    
    /**
     * The line number from the GEDCOM Source file this record was created from.
     */
    protected int line_number;
    
    /**
     * Instantiate a new instance of a GEDCOM record given its ID.
     * 
     * @param id The ID of the GEDCOM record.
     * @param line_number The line number the GEDCOMRecord was created from.
     */
    public GEDCOMRecord(int id, int line_number) {
        this.id = id;
        this.line_number = line_number;
    }
    
    /**
     * Return the ID of the record.
     * 
     * @return The ID of the record.
     */
    public int getId() {
        return this.id;
    }
    
    /**
     * Return the line number from the GEDCOM source file this record was created with.
     * 
     * @return The line number from the GEDCOM source file this record was created with.
     */
    public int getLineNumber() {
	return this.line_number;
    }
    
    /**
     * Returns the numeric ID extracted from the given XREF ID based off of the provided regular expression.
     * 
     * @param xred_id The XREF-ID read from the GEDCOM file.
     * @param regex The regular expression to use for extracting the numeric ID.
     * @throws GEDCOMParseException if the xref_id is invalid.
     * @return int The numeric XREF ID
     */
    protected static int getNumericIdFromXrefId(String xref_id, String regex) throws GEDCOMParseException {
        Pattern pattern = Pattern.compile(regex);
        Matcher m = pattern.matcher(xref_id);
        if(m.matches()) 
            return Integer.parseInt(m.group(1));

        throw new GEDCOMParseException("Invalid XREF ID of \"" + xref_id + "\"");
    }
}
