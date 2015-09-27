/**
 * 
 */
package edu.stevens.cs555.project;

import java.util.ArrayList;

/**
 * A factory class for creating classes that are instances of a GEDCOMLine.
 * 
 * @author garrettshevach@gmail.com
 *
 */
public class GEDCOMLineFactory {

    /**
     * Create a GEDCOMLine from a line within a GEDCOM file. 
     * 
     * @param line The line to create the GEDCOMLine from.
     * @return The resulting GEDCOMLine instance. If less than 2 arguments were given, then NULL will be returned.
     */
    public GEDCOMLine createGEDCOMLineFromLine(String line) {
	String[] splits = line.split(" ");
	if(splits.length < 2)
	    return null;

	if(GEDCOMLine.isValidXrefId(splits[1]))
	    return createGEDCOMLineWithXref(splits[1], splits[2]);
	else {
	    ArrayList<String> args = new ArrayList<String>();
	    if(splits.length >= 3)
		for(int i = 2; i < splits.length; i++)
		    args.add(splits[i]);
	    return createGEDCOMLineWithArgs(Integer.parseInt(splits[0]), splits[1], args);
	}
    }
    
    /**
     * Create a GEDCOMLine extended to be a GEDCOMLineWithArgs.
     * 
     * @param level The level of the line.
     * @param tag The tag of the line.
     * @param args The arguments of the GEDCOM line separated by white space.
     * @return The resulting GEDCOMLine instance.
     */
    public GEDCOMLine createGEDCOMLineWithArgs(int level, String tag, ArrayList<String> args) {
	return new GEDCOMLineWithArgs(level, tag, args);
    }
    
    /**
     * Create a GEDCOMLine from a line within a GEDCOM file.
     * 
     * @param xref_id The XREF ID of the line.
     * @param tag The tag of the line.
     * @return The resulting GEDCOMLine instance.
     */
    public GEDCOMLine createGEDCOMLineWithXref(String xref_id, String tag) {
	return new GEDCOMLineWithXref(xref_id, tag);
    }
}
