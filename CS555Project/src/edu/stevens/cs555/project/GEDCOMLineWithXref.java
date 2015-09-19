/**
 * 
 */
package edu.stevens.cs555.project;

/**
 * A class representation of a GEDCOMLine with an Xred ID.
 * 
 * @author garrettshevach@gmail.com
 *
 */
public class GEDCOMLineWithXref extends GEDCOMLine {

    /**
     * The XRED ID associated to the line.
     */
    private String xref_id;
    
    /**
     * Creates a new instance of a GEDCOMLineWithXref
     * 
     * @param xref_id The XREF ID of the line.
     * @param tag The Tag of the line.
     */
    public GEDCOMLineWithXref(String xref_id, String tag) {
	// Lines with an XREF ID will always have a level of 0
	this.setLevel(0);
	this.setXrefId(xref_id);
	this.setTag(tag);
    }

    /**
     * Return the XREF ID of the line.
     * 
     * @return The xref_id of the line.
     */
    public String getXrefId() {
        return xref_id;
    }

    /**
     * Set the XREF ID of the line.
     * 
     * @param xref_id The xref_id to set for the line.
     */
    public void setXrefId(String xref_id) {
        this.xref_id = xref_id.trim();
    }
    
    /**
     * Returns the string representation of the GEDCOMLineWithXref
     * 
     * @return The String representation.
     */
    public String toString() {
	String line = this.level + " " + this.xref_id + " " + this.tag;
	
	return "Line:\t" + line + "\n" +
		"Level:\t" + this.level + "\n" +
		"Tag:\t" + this.tag;
    }
}
