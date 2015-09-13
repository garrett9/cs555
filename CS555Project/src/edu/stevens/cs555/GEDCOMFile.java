/**
 * 
 */
package edu.stevens.cs555;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;

/**
 * @author gshevac1
 *
 */
public class GEDCOMFile extends File {

    private static final long serialVersionUID = 1L;

    /**
     * The lines read from the GEDCOM file when opened. It will remain NULL until the {@link #read()} method successfully opens and read from the file.
     */
    private String[] lines = null;

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
    public String[] getLines() {
	return this.lines;
    }

    /**
     * Opens the file, and reads each line into an Array.
     * 
     * @throws FileNotFoundException If the file was not found.
     * @throws IOException If the file could not be read from.
     * @return The ArrayList
     */
    public void read() throws FileNotFoundException, IOException {
	FileReader file_reader = new FileReader(this);
	BufferedReader buffered_reader = new BufferedReader(file_reader);

	String line;
	IOException io_e = null;
	try {
	    while((line = buffered_reader.readLine()) != null) {

	    }
	} catch(IOException e) {
	    io_e = e;
	}

	buffered_reader.close();
	if(io_e != null)
	    throw io_e;
    }

}
