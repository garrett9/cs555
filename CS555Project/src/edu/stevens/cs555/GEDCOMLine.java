/**
 * 
 */
package edu.stevens.cs555;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A class representation of a single GEDCOM line in a GEDCOM file.
 * 
 * @author garrettshevach@gmail.com
 *
 */
public abstract class GEDCOMLine {
    
    /**
     * Whether or not the class has a valid tag.
     */
    protected boolean hasValidTag = false;
    
    /**
     * The level of the GEDCOM Line.
     */
    protected int level;
    
    /**
     * The Tag associated to the GEDCOM Line.
     */
    protected String tag;
    
    /**
     * A list of valid tags for a GEDCOM line.
     */
    private static String[] valid_tags = {
	"INDI",
	"NAME",
	"SEX",
	"BIRT",
	"DEAT",
	"FAMC",
	"FAMS",
	"FAM",
	"MARR",
	"HUSB",
	"WIFE",
	"CHIL",
	"DIV",
	"DATE",
	"HEAD",
	"TRLR",
	"NOTE"
    };
    
    /**
     * Given a tag, this function returns true if the tag is valid, and false otherwise.
     * 
     * @param tag The tag to validate.
     * @return True if the tag is valid, and false otherwise.
     */
    public static boolean isValidTag(String str) {
	for(String tag: valid_tags)
	    if(tag.equals(str))
		return true;
	return false;
    }
    
    /**
     * Tests whether a given string is a valid XREF ID by using a regular expression.
     * 
     * @param xref_id The XREF ID to validate.
     * @return True if the XREF ID is valid, and false otherwise.
     */
    public static boolean isValidXrefId(String xref_id) {
	String regex = "@.+@";
	Pattern pattern = Pattern.compile(regex);
	Matcher m = pattern.matcher(xref_id);
	if(m.matches())
	    return true;
	return false;
    }

    /**
     * Get the level of the line.
     * 
     * @return The level of the line.
     */
    public int getLevel() {
        return level;
    }

    /**
     * Set the level of the line.
     * 
     * @param level The level to set for the line.
     */
    public void setLevel(int level) {
        this.level = level;
    }

    /**
     * Get the tag of the line.
     * 
     * @return The tag of the line.
     */
    public String getTag() {
        return tag;
    }

    /**
     * Set the tag of thee line. If the tag is not valid, then the tag will still be set, but {@link #hasValidTag()} will return false when it's called.
     * 
     * @param tag The tag to set for the line.
     */
    public void setTag(String tag) {
        this.tag = tag.trim();
        this.hasValidTag = isValidTag(tag) ? true : false;
    }
    
    /**
     * Returns whether or not the Line has a valid tag.
     * 
     * @return True if the line has a valid tag, and false otherwise.
     */
    public boolean hasValidTag() {
	return this.hasValidTag;
    }
}
