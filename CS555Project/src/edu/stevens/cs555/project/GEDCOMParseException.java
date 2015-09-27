package edu.stevens.cs555.project;

/**
 * A custom exception to be thrown if there was an error parsing the GEDCOM file.
 * 
 * @author garrettshevach@gmail.com
 *
 */
public class GEDCOMParseException extends Exception {

    private static final long serialVersionUID = 1L;

    /**
     * Create a new instance of a GEDCOMParseException
     * 
     * @param msg The message to attach to the exception.
     */
    public GEDCOMParseException(String msg) {
	super(msg);
    }
}
