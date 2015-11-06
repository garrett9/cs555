package edu.stevens.cs555.project;

import edu.stevens.cs555.project.processors.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

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
     * Prints the usage for the program, and then exits.
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
        if(args.length <= 0) {
            usage();
        }

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

        Individual[] individuals = new Individual[5000];
        Family[] families = new Family[1000];

        // A list to hold any validation exceptions when validating the GEDCOM source file.
        ArrayList<GEDCOMValidationException> validation_exceptions = new ArrayList<GEDCOMValidationException>();
        
        /*
         * The try/catch block checks for a valid XREF ID when creating the correct type of record (Family/Individual)
         */
        try {
            GEDCOMRecord last_record = null;

            for(int i = 0; i < gedcom_lines.size(); i++) {
                GEDCOMLine line = gedcom_lines.get(i);
        
                // If the line is an instance of GEDCOMLineWithXref, then it's the start of a set of lines belonging to either a Family or Individual.
                if(line instanceof GEDCOMLineWithXref) {
                    GEDCOMLineWithXref xref_line = (GEDCOMLineWithXref) line;

                    /*
                     * If the line has the FAM tag, then we need to create a Family record.
                     * If the line has the INDI tag, then we need to create an Individual record.
                     */
                    if(xref_line.tag.equals(Family.TAG)) {
                        Family family = Family.createFromXrefId(xref_line.getXrefId(), line.getLineNumber());
                        
                        if(families[family.getId()] == null) {                
                            families[family.getId()] = family;
                            last_record = family;
                        }
                        else {
                           validation_exceptions.add(new GEDCOMValidationException("A family with the same ID already exists!", line.getLineNumber())); 
                           last_record = null;
                        }
                       
                    } else {
                        Individual individual = Individual.createFromXrefId(xref_line.getXrefId(), line.getLineNumber());
                        
                        if(individuals[individual.getId()] == null) {
                            individuals[individual.getId()] = individual;
                            last_record = individual;
                        }
                        else {
                            validation_exceptions.add(new GEDCOMValidationException("An individual with the same ID already exists!", line.getLineNumber()));
                            last_record = null;
                        }
                    }
                } else {
                    /*
                     * If we made it here, that means we're dealing with a GEDCOM line that doesn't have an XREF ID as the second paramter.
                     * Therefore, if the level here is 0, then it's a GEDCOM line with a second parameter tag of HEAD, TRLR, or NOTE, meaning 
                     * it's no use to use. If this is the case, then we skip the current line, and move on.
                     */
                    if(line.level <= 0) {
                        last_record = null;
                        continue;
                    }
            
                    // If there was no last record recorded yet, then we can't do anything, so we simply skip this line
                    if(last_record == null) {
                        continue;
                    }
                
                    /*
                     * We performed all of our checks, so we can safely move on to start record information to our records.
                     * If the last recorded record was an instance of Family, then we look out for Family related date. 
                     * Otherwise, we look out for Individual related date.
                     */
                    GEDCOMLineWithArgs args_line = (GEDCOMLineWithArgs) line;

                    if(last_record instanceof Family) {
                        // The last record recorded was a Family instance. Therefore, we watch out here for all Family related information.
                        Family family = (Family) last_record; 
                        String tag = args_line.getTag().toLowerCase();

                        if(tag.equals("marr")) { // A marriage event for the family
                            // If we hit MARR, then the next line is the date we're looking for
                            GEDCOMLineWithArgs date_line = (GEDCOMLineWithArgs)gedcom_lines.get(i + 1);
                            family.setMarr(date_line.getArgsAsString());
                
                            // Since we already read the next line, we skip it by incrementing the count
                            i++;
                        } else if(tag.equals("husb")) { // The husband identifier for the family
                            family.setHusb(Individual.getNumericIdFromXrefId(args_line.getArgs().get(0)));
                        } else if(tag.equals("wife")) { // The wife identifier for the family
                            family.setWife(Individual.getNumericIdFromXrefId(args_line.getArgs().get(0)));
                        } else if(tag.equals("chil")) { // A child identifier for the family
                            family.addChild(Individual.getNumericIdFromXrefId(args_line.getArgs().get(0)));
                        } else if(tag.equals("div")) { // A divorce event for the family
                            // If we hit MARR, then the next line is the date we're looking for
                            GEDCOMLineWithArgs date_line = (GEDCOMLineWithArgs)gedcom_lines.get(i + 1);
                            family.setDiv(date_line.getArgsAsString());
                            
                            // Since we already read the next line, we skip it by incrementing the count
                            i++;
                        }
                    } else {
                        // The last record recorded was an Individual instance. Therefore, we watch out here for all Individual related information.
                        Individual individual = (Individual) last_record;
                        
                        String tag = args_line.getTag().toLowerCase();

                        if(tag.equals("name")) { // The name of the individual
                            individual.setName(args_line.getArgsAsString());
                        } else if(tag.equals("sex")) { // The sex of the individual
                            individual.setSex(args_line.getArgsAsString());
                        } else if(tag.equals("birt")) { // Birth date of the individual
                            // If we hit birt, then the next line is the date we're looking for
                            GEDCOMLineWithArgs date_line = (GEDCOMLineWithArgs)gedcom_lines.get(i + 1);
                            individual.setBirt(date_line.getArgsAsString());
                            
                            // Since we already read the next line, we skip it by incrementing the count
                            i++;
                        } else if(tag.equals("deat")) { // Death date of the individual
                            // If we hit MARR, then the next line is the date we're looking for
                            GEDCOMLineWithArgs date_line = (GEDCOMLineWithArgs)gedcom_lines.get(i + 1);
                            individual.setDeat(date_line.getArgsAsString());
                            
                            // Since we already read the next line, we skip it by incrementing the count
                            i++;
                        } else if(tag.equals("famc")) { // Family ID the individual is a child to
                            individual.setFamc(Family.getNumericIdFromXrefId(args_line.getArgs().get(0)));
                        } else if(tag.equals("fams")) { // Family ID the individual is a spouse to
                            individual.setFams(Family.getNumericIdFromXrefId(args_line.getArgs().get(0)));
                        }
                    }
                }
            }

            // Print Individuals
            System.out.println("\nIndividuals:\n");
            for(Individual individual : individuals) {
                if (individual == null) {
                    continue;
                }

                System.out.println(individual.getId() + " " + individual.getName()); 
                //Printing the age of the individuals
                System.out.println("Age is "+ individual.getAge() + " ");  
            }

            // Print familes
            System.out.println("\n\nFamilies:\n");
            for(Family family : families) {
                if (family == null) {
                    continue;
                }

                Individual husband = individuals[family.getHusb()];
                String husbandName = husband != null ? husband.getName() : "";

                Individual wife = individuals[family.getWife()];
                String wifeName = wife != null ? wife.getName() : "";
                System.out.println(family.getId());
                System.out.println("  Husband: " + husbandName);
                System.out.println("  Wife: " + wifeName);
            }
        } catch(GEDCOMParseException e) {
            error(e.getMessage());
            System.out.println(Arrays.toString(individuals));
            System.out.println(Arrays.toString(families));
        }
        
        // Here's where we can add the GEDCOMProcessor to run.
        ArrayList<GEDCOMProcessor> gedcom_functions = new ArrayList<GEDCOMProcessor>();
        gedcom_functions.add(new UniqueNameAndBirth());
        gedcom_functions.add(new Under150());
        gedcom_functions.add(new ListDeceased());
        gedcom_functions.add(new BirthBeforeDeath());
        gedcom_functions.add(new BirthBeforeMarriage());
        gedcom_functions.add(new DatesBeforeCurrentDate());
        gedcom_functions.add(new ValidDates());
        gedcom_functions.add(new ListRecentBirths());
        gedcom_functions.add(new ListRecentDeaths());
        gedcom_functions.add(new DivorceBeforeDeath());
        gedcom_functions.add(new MarriageBeforeDeath());
        gedcom_functions.add(new BirthAfterMarriageOfParents());
        gedcom_functions.add(new BirthBeforeDeathOfParents());
        gedcom_functions.add(new UniqueFirstNameInFamilies());
        gedcom_functions.add(new UniqueFamiliesBySpouses());
        gedcom_functions.add(new FewerThan15Siblings());
        gedcom_functions.add(new MarriageAfter14());
        gedcom_functions.add(new MaleLastNames());
        gedcom_functions.add(new GenderRoles());

        for(GEDCOMProcessor gedcom_function : gedcom_functions) {
            gedcom_function.run(families, individuals);
            validation_exceptions.addAll(gedcom_function.getValidationErrors());
        }
        
        // Here's where we print out the validation errors
        if(validation_exceptions.size() > 0) {
            System.out.println("\nThe following validation anomalies were found with the GEDCOM file...\n");
            for(GEDCOMValidationException e : validation_exceptions)
                System.out.println("\t" + e);
        }
    }
}
