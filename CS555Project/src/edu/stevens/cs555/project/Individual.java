package edu.stevens.cs555.project;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * A class for representing a single individual from a GEDCOM file.
 * 
 * @author garrettshevach@gmail.com
 *
 */
public class Individual extends GEDCOMRecord {

    /**
     * The TAG that specifies a set of GEDCOM lines belongs to an individual.
     */
    public static final String TAG = "INDI";

    /**
     * The regular expression used to extract the numeric ID from the XREF ID associated to an Individual record.
     */
    private static final String XREF_FORMAT = "@I(\\d+)@";

    /**
     * The name of the individual.
     */

    private String name;

    /**
     * Whether or not the person is a male.
     */
    private String sex;

    /**
     * The family ID this individual is a child to.
     */
    private int famc;

    /**
     * The family ID this individual is a spouse to.
     */
    private int fams;

    /**
     * The age of the indiviual
     */
    
    private int age;
    
    SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy");
    Date birth;
    /**
     * The birth date for the family.
     */
    private String birt;

    /**
     * The death date for the family.
     */
    private String deat;

    /**
     * Create a new instance of an Individual given its ID.
     * 
     * @param id The ID of the individual.
     * @param line_number The line number from the GEDCOM source file this record was created with.
     */
    
    public Individual(int id, int line_number) {
	    super(id, line_number);
    }

    /**
     * Get the name of the individual.
     * 
     * @return The name of the individual.
     */
    public String getName() {
	    return name;
    }

    /**
     * Set the name of the individual.
     * 
     * @param name The name of the individual.
     */
    public void setName(String name) {
	    this.name = name;
    }

    /**
     * Get the last name of an individual
     *
     * @return The last name of the individual.
     */
    public String getLastName() {
        int firstSlashIndex = name.indexOf('/');
        int lastSlashIndex = name.indexOf('/', firstSlashIndex + 1);

        return name.substring(firstSlashIndex + 1, lastSlashIndex);
    }

    public static int getAgeFromNow(Date birtdate) {
    	Calendar today = Calendar.getInstance();
	    Calendar birthDate = Calendar.getInstance();

	    int age = 0;
	    birthDate.setTime(birtdate);
	    age = today.get(Calendar.YEAR) - birthDate.get(Calendar.YEAR);
	    
	return age;
	}
    /**
     * Get the gender of the individual.
     * 
     * @return True if the individual is a male; False otherwise
     */
    public String getSex() {
	    return this.sex;
    }

    /**
     * Set the gender of the individual.
     * 
     * @param sex True if the individual is a male; False otherwise.
     */
    public void setSex(String sex) {
        this.sex = sex;
    }

    /**
     * Get the family ID this individual is a child to.
     * 
     * @return The family ID this individual is a child to.
     */
    public int getFamc() {
	    return famc;
    }

    /**
     * Set the family ID this individual is a child for.
     * 
     * @param famc The family ID this individual is a child for.
     */
    public void setFamc(int famc) {
	    this.famc = famc;
    }

    /**
     * Get the family ID this individual is a spouse for.
     * 
     * @return The family ID this individual is a spouse for.
     */
    public int getFams() {
        return fams;
    }

    /**
     * Set the family ID this individual is a spouse for.
     * 
     * @param fams The family ID this individual is a spouse for.
     */
    public void setFams(int fams) {
        this.fams = fams;
    }

    /**
     * Get the birth date of the individual.
     * 
     * @return The birth date of the individual
     */
    public String getBirt() {
	    return birt;
    }

    /**
     * Set the birth date of the individual.
     * 
     * @param birt The birth date of the individual.
     */
    public void setBirt(String birt) {
	    this.birt = birt;
	    try {
	    birth = formatter.parse(birt);
	    //adding age when birthdate is added US27
	    this.age = getAgeFromNow(birth);
	    }
	    catch(ParseException e) {
			//syntax error
	    }
    }
    /**
     * Get the death date of the individual.
     * 
     * @return The death date of the individual
     */
    public int getAge() {
	    return age;
    }

    /**
     * Get the death date of the individual.
     * 
     * @return The death date of the individual
     */
    public String getDeat() {
	    return deat;
    }

    /**
     * Set the death date of the individual.
     * 
     * @param deat The death date of the individual.
     * also checks if the death date is valid and return true also false when called
    */ 
    public void setDeat(String deat) {
	    this.deat = deat;
    }
    
    /**
     * Extract the numeric ID from an Individual record's XREF ID.
     * 
     * @param xref_id The XREF ID of the record.
     * @return The resulting numeric ID.
     * @throws GEDCOMParseException If the numeric ID has an invalid format.
     */
    public static int getNumericIdFromXrefId(String xref_id) throws GEDCOMParseException {
        return getNumericIdFromXrefId(xref_id, XREF_FORMAT);
    }

    /**
     * Creates a new instance of a Individual object given the Xref ID read from a GEDCOM file.
     * 
     * @param xref_id The XREF-ID read from the GEDCOM file.
     * @param line_number The line number from the GEDCOM source file this record was created with.
     * @return The new instance of an Invidual record.
     * @throws GEDCOMParseException if the xref_id is invalid.
     */
    public static Individual createFromXrefId(String xref_id, int line_number) throws GEDCOMParseException {
	    return new Individual(getNumericIdFromXrefId(xref_id), line_number);
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
	return getClass().getName() + " {\n\tname: " + name + "\n\tsex: " + sex
		+ "\n\tfamc: " + famc + "\n\tfams: " + fams + "\n\tbirt: "
		+ birt + "\n\tdeat: " + deat + "\n\tid: " + id + "\n}\n";
    }
}
