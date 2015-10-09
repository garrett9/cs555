package edu.stevens.cs555.project;

/**
 * A Custom Exception to be thrown when a validation error occurs in a provided GEDCOM file.
 */
public class GEDCOMValidationException extends Exception {

    private static final long serialVersionUID = 1L;

    /**
     * The line number of the record the validation error has occured with.
     */
    private int line_number;
    
    /**
     * Initialize a new instance of a GEDCOMValidationException.
     * 
     * @param message The message of the exception.
     * @param line_number The line number in the GEDCOM file of the record the validation error has occured with.
     */
    public GEDCOMValidationException(String message, int line_number) {
	super(message);
	this.line_number = line_number;
    }
    
    /**
     * Return the line number of the record the validation error has occured on.
     * 
     * @return The line number of the record the validation error has occured on.
     */
    public int getLineNumber() {
	return this.line_number;
    }
    
    /*
     * (non-Javadoc)
     * @see java.lang.Throwable#toString()
     */
    public String toString() {
	return "Anomaly with the record at line " + this.getLineNumber() + ": " + this.getMessage();
    }
}
