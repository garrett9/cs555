package edu.stevens.cs555.project;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;

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
     * The MARRIAGE date for the family.
     */
    private String marr;
    
    /**
     * The MARRIAGE date for the family in date format.
     */
    
    private Date marrDate;
    
    private Boolean isAnniSoon;
    
    /**
     * The DIVORCE date for the family.
     */
    private String div;
    
    SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy");
    
    /**
     * Create a new Family instance given its ID.
     * 
     * @param id The ID of the family.
     * @param line_number The line number from the GEDCOM source file this record was created with.
     */
    public Family(int id, int line_number) {
        super(id, line_number);
        this.children = new ArrayList<Integer>();
    }
    
    
    // US39 Kevin Cho
    public boolean isAnniversarySoon(Date Marridate) {
    	Calendar today = Calendar.getInstance();
	    Calendar MarriageDate = Calendar.getInstance();

	    Boolean isAnniSoon = false;
	    MarriageDate.setTime(Marridate);
	    int MonthDiff = today.get(Calendar.MONTH) - MarriageDate.get(Calendar.MONTH) + 2;
	    int DateDiff = MarriageDate.get(Calendar.DAY_OF_MONTH) - today.get(Calendar.DAY_OF_MONTH);
	    
	    if (MonthDiff == 0 && DateDiff > 0 )
	    {
	    	isAnniSoon = true; 	
	    }
	    else if (MonthDiff < 2)
	    {
	    	isAnniSoon = true; 
	    }
	    
	return isAnniSoon;
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
     * @param child The child to add to the family.
     */
    public void addChild(int child) {
        this.children.add(child);
    }
    
    /**
     * Get the marriage date for the family.
     * 
     * @return The marriage date for the family.
     */
    public String getMarr() {
        return this.marr;
    }
    
    public Date getMarrDate() {
        return this.marrDate;
    }
    /**
     * Set the marriage date for the family.
     * 
     * @param marr The marriage date for the family.
     */
    public void setMarr(String marr) {
        this.marr = marr;
        try {
    	    this.marrDate = formatter.parse(marr);
    	    this.isAnniSoon = isAnniversarySoon(marrDate);
    	    }
    	    catch(ParseException e) {
    			//syntax error
    	    }
    }
    /**
     * Get isAnniSoon.
     * 
     * @return if the Anniversary is coming up.
     */
    public Boolean getIsAnniSoon() {
        return this.isAnniSoon;
    }
    
    /**
     * Get the divorce date for the family.
     * 
     * @return The divorce date for the family.
     */
    public String getDiv() {
        return this.div;
    }
    
    /**
     * Set the divorce date for the family.
     * 
     * @param div The divorce date for the family.
     */
    public void setDiv(String div) {
        this.div = div;
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
     * @param line_number The line number from the GEDCOM source file this record was created with.
     * @return The new instance of a Family record.
     * @throws GEDCOMParseException if the xref_id is invalid.
     */
    public static Family createFromXrefId(String xref_id, int line_number) throws GEDCOMParseException {
        return new Family(getNumericIdFromXrefId(xref_id), line_number);
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return getClass().getName() + " {\n\thusb: " + husb + "\n\twife: "
            + wife + "\n\tchildren: " + children + "\n\tmarr: " + marr
            + "\n\tdiv: " + div + "\n\tid: " + id + "\n}\n";
    }
}
