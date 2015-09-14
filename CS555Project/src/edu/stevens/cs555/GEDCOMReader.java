package edu.stevens.cs555;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

/**
 * The executable class for executing the command line interface for reading GEDCOM files.
 * 
 * @author garrettshevach@gmail.com
 *
 */
public class GEDCOMReader {

    /**
     * Prints the provided error message, and then exits the program.
     * 
     * @param msg The error message to print to the console.
     */
    private static void error(String msg) {
	System.out.println("ERROR: " + msg);
	System.exit(0);
    }

    /**
     * Prints the usage for the program, and then exists.
     */
    private static void usage() {
	System.out.println("Usage: GEDCOMReader [GEDCOM File Path]");
	System.exit(0);
    }

    /**
     * The main method for executing the program.
     * 
     * @param args Argument parameters to the program.
     */
    public static void main(String[] args) {
	if(args.length <= 0)
	    usage();

	GEDCOMFile gedcom_file = new GEDCOMFile(args[0]);
	ArrayList<GEDCOMLine> gedcom_lines = new ArrayList<GEDCOMLine>();
	try {
	    System.out.println("Reading GEDCOM file at \"" + gedcom_file.getAbsolutePath() + "\"...");
	    gedcom_lines = gedcom_file.read();
	} catch(FileNotFoundException e) {
	    error("The GEDCOM file at \"" + gedcom_file.getAbsolutePath() + "\" was not found!");
	} catch(IOException e) {
	    error("The GEDCOM file at \"" + gedcom_file.getAbsolutePath() + "\" could not be read from! (" + e.getMessage() + ")");
	}

	System.out.println("Finished reading the GEDCOM file. Now printing the contents...\n");
	for(GEDCOMLine line : gedcom_lines)
	    System.out.println(line + "\n");
    }

}
