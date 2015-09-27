/**
 * 
 */
package edu.stevens.cs555.project;

import java.util.ArrayList;

/**
 * A class representation of a GEDCOMLine with arguments.
 * 
 * @author garrettshevach@gmail.com
 *
 */
public class GEDCOMLineWithArgs extends GEDCOMLine {

    /**
     * A list of arguments associated to the GEDCOM line.
     */
    private ArrayList<String> args;
    
    /**
     * Create a new instance of GEDCOMLineWithArgs with an already populat ArrayList of arguments.
     * 
     * @param level The level of the GEDCOM line.
     * @param tag The tag of the GEDCOM line.
     * @param arguments The ArrayList of arguments for the GEDCOMLine.
     */
    public GEDCOMLineWithArgs(int level, String tag, ArrayList<String> arguments) {
	this.setLevel(level);
	this.setTag(tag);
	this.args = arguments;
    }
    
    /**
     * Create a new instance of a GEDCOMLineWithArgs
     * 
     * @param level The level of the GEDCOM line.
     * @param tag The tag of the GEDCOM line.
     * @param arguments A string of arguments separated by white space of the GEDCOM line.
     */
    public GEDCOMLineWithArgs(int level, String tag, String arguments) {
	this.setLevel(level);
	this.setTag(tag);
	
	String[] args_split = arguments.trim().split(" ");
	this.args = new ArrayList<String>(args_split.length);
	for(String arg : args_split)
	    this.addArg(arg);
    }
    
    /**
     * Add an argument to the list of arguments for this GEDCOM line.
     * 
     * @param arg The argument to add for the line.
     */
    public void addArg(String arg) {
	this.args.add(arg.trim());
    }
    
    /**
     * Retrieves the list of arguments for the line.
     * 
     * @return The list of arguments for the line.
     */
    public ArrayList<String> getArgs() {
	return this.args;
    }
    
    /**
     * Returns the arguments to the line separated by white space.
     * 
     * @return The arguments of the line separated by white space.
     */
    public String getArgsAsString() {
	return this.getArgsAsString(" ");
    }
    
    /**
     * Returns the arguments to the line separated by a specified delimiter.
     * 
     * @param delimiter The delimiter to separate the arguments by.
     * @return The joined string.
     */
    public String getArgsAsString(String delimiter) {
	return String.join(delimiter, this.args);
    }
    
    /**
     * Returns the string representation of the GEDCOMLineWithArgs.
     * 
     * @return The String representation.
     */
    public String toString() {
	String line = this.level + " " + this.tag + " " + String.join(" ", this.args);
	String tag = this.hasValidTag ? this.tag : "\"Invalid Tag\"";
	
	return "Line:\t" + line.trim() + "\n" +
		"Level:\t" + this.level + "\n" +
		"Tag:\t" + tag;
    }
}
