/**
 * 
 */
package edu.stevens.cs555;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * A class for representing a single GEDCOM file.
 * 
 * @author garrettshevach@gmail.com
 *
 */
public class GEDCOMFile extends File {

    private static final long serialVersionUID = 1L;

    /**
     * The lines read from the GEDCOM file when opened. It will remain NULL until the {@link #read()} method successfully opens and read from the file.
     */
    private ArrayList<GEDCOMLine> gedcom_lines = null;

    /**
     * Works like {@link java.io.File#File(String)}.
     * 
     * @param path The path to the file.
     */
    public GEDCOMFile(String path) {
	super(path);
    }

    /**
     * Returns the lines read from the file if it was successfully opened.
     * @return
     */
    public ArrayList<GEDCOMLine> getGedcomLines() {
	return this.gedcom_lines;
    }

    /**
     * Opens the file, and reads each line into an ArrayList that is returned, or can be retrieved from {@link #getGedcomLines()}.
     * 
     * @throws FileNotFoundException If the file was not found.
     * @throws IOException If the file could not be read from.
     * @return The ArrayList
     */
    public ArrayList<GEDCOMLine> read() throws FileNotFoundException, IOException {
	FileReader file_reader = new FileReader(this);
	BufferedReader buffered_reader = new BufferedReader(file_reader);

	String line;
	IOException io_e = null;
	GEDCOMLineFactory factory = new GEDCOMLineFactory();
	this.gedcom_lines = new ArrayList<GEDCOMLine>();
	try {
	    while((line = buffered_reader.readLine()) != null)
		this.gedcom_lines.add(factory.createGEDCOMLineFromLine(line));
	} catch(IOException e) {
	    io_e = e;
	}

	buffered_reader.close();
	if(io_e != null)
	    throw io_e;
	return this.gedcom_lines;
    }

}
