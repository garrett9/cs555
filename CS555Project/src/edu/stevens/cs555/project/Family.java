package edu.stevens.cs555.project;

import java.util.ArrayList;

/**
 * A class representing a Family from a GEDCOM file.
 * 
 * @author garrettshevach@gmail.com
 *
 */
public class Family extends GEDCOMRecord {

    /**
     * The Tag that specifies a set of GEDCOM lines belongs to a Family record.
     */
    public static final String TAG = "FAM";
    
    /**
     * The regular expression used to extract the numeric ID from the XREF ID associated to a family record.
     */
    private static final String XREF_FORMAT = "@F(\\d+)@";
    
    /**
     * The ID of the husband in the family.
     */
    private int husb;
    
    /**
     * The ID of the wife in the family.
     */
    private int wife;
    
    /**
     * The list of IDs of individuals that are children in the family.
     */
    private ArrayList<Integer> children;
    
    /**
     * Create a new Family instance given its ID.
     * 
     * @param id The ID of the family.
     */
    public Family(int id) {
	super(id);
	this.children = new ArrayList<Integer>();
    }
    
    /**
     * @return the husb
     */
    public int getHusb() {
        return husb;
    }

    /**
     * Get the husband of the family.
     * 
     * @param husb The husband of the family.
     */
    public void setHusb(int husb) {
        this.husb = husb;
    }

    /**
     * Get the wife of the family.
     * 
     * @return The wife of the family.
     */
    public int getWife() {
        return wife;
    }

    /**
     * Set the wife of the family.
     * 
     * @param wife Set The wife of the family.
     */
    public void setWife(int wife) {
        this.wife = wife;
    }

    /**
     * Get the children of the family.
     * 
     * @return The children of the family.
     */
    public ArrayList<Integer> getChildren() {
        return children;
    }

    /**
     * Add a child to the family.
     * 
     * @param children The child to add to the family.
     */
    public void addChild(int child) {
        this.children.add(child);
    }
    
    /**
     * Extract the numeric ID from an Family record's XREF ID.
     * 
     * @param xref_id The XREF ID of the record.
     * @return The resulting numeric ID.
     * @throws GEDCOMParseException If the numeric ID has an invalid format.
     */
    public static int getNumericIdFromXrefId(String xref_id) throws GEDCOMParseException {
	return getNumericIdFromXrefId(xref_id, XREF_FORMAT);
    }

    /**
     * Creates a new instance of a Family object given the Xref ID read from a GEDCOM file.
     * 
     * @param xref_id The XREF-ID read from the GEDCOM file.
     * @return The new instance of a Family record.
     * @throws GEDCOMParseException if the xref_id is invalid.
     */
    public static Family createFromXrefId(String xref_id) throws GEDCOMParseException {
	return new Family(getNumericIdFromXrefId(xref_id));
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
	return getClass().getName() + " {\n\thusb: " + husb + "\n\twife: "
		+ wife + "\n\tchildren: " + children + "\n}\n";
    } 
}
