package edu.stevens.cs555.project.processors;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import edu.stevens.cs555.project.Family;
import edu.stevens.cs555.project.GEDCOMValidationException;
import edu.stevens.cs555.project.Individual;

/**
 * Lists all living spouses and descendants of people in a GEDCOM file who died in the last 30 days.
 * 
 * @author garrettshevach@gmail.com
 *
 */
public class ListRecentSurvivors extends GEDCOMProcessor {

	/**
	 * The data formatter to verify valid dates.
	 */
	private SimpleDateFormat formatter;

	/**
	 * The constructor.
	 */
	public ListRecentSurvivors() 
	{
		super("US37");
		this.formatter = new SimpleDateFormat("dd MMM yyyy");
	}

	/**
	 * Returns a Date object if the given date is a valid date; returns null otherwise.
	 * 
	 * @param date The date to verify.
	 * @return The date object if the date is valid; false otherwise.
	 */
	private Date validDate(String date) 
	{
		if(date != null) {
			try {
				return formatter.parse(date);
			}
			catch(ParseException e) {
			}
		}

		return null;
	}

	/**
	 * Returns the number of days between two dates.
	 * 
	 * @param d1 The first date.
	 * @param d2 The second date.
	 * @return The number of days between the two dates.
	 */
	private long daysBetweenDates(Date d1, Date d2) {
		return TimeUnit.DAYS.convert(d2.getTime() - d1.getTime(), TimeUnit.MILLISECONDS);
	}

	/**
	 * Returns true if the given date is valid and occurred within the last 30 days; false otherwise.
	 * 
	 * @param date The date to test.
	 * @return True if the given date is valid and occurred within the last 30 days; false otherwise.
	 */
	private boolean withinLast30Days(String date) {
		Date date_inst = this.validDate(date);
		Date now = new Date();

		if(date_inst != null)
			if(this.daysBetweenDates(date_inst, now) <= 30)
				return true;

		return false;
	}

	/**
	 * Adds all descendents in the given family to the list of recent survivors.
	 * 
	 * @param family The family to get the children of first.
	 * @param The families in the GEDCOM file.
	 * @param The individuals in the GEDCOM file.
	 * @param died The individual who has died.
	 */
	private void listDescendents(Family family, Family[] families, Individual[] individuals, Individual died) 
	{
		ArrayList<Integer> children = family.getChildren();
		for(int i : children) {
			Individual child = individuals[i];
			if(child.getDeat() == null)
				this.addValidationException(new GEDCOMValidationException("This record has survived an ancestor, " + died.getName() + ", that has passed away in the last 30 days.", child.getLineNumber()));
			if(child.getFams() > 0)
				this.listDescendents(families[child.getFams()], families, individuals, died);
		}
	}

	@Override
	public void run(Family[] families, Individual[] individuals) {
		for(Family family : families) {
			if(family != null) {
				Individual wife = individuals[family.getWife()];
				Individual husb = individuals[family.getHusb()];
				if(this.withinLast30Days(husb.getDeat())) {
					if(wife.getDeat() == null)
						this.addValidationException(new GEDCOMValidationException("This record has survived her husband, " + husb.getName() + ", that has passed away in the last 30 days.", wife.getLineNumber()));
					this.listDescendents(family, families, individuals, husb);
				}
				if(this.withinLast30Days(wife.getDeat())) {
					if(husb.getDeat() == null)
						this.addValidationException(new GEDCOMValidationException("This record has survived his wife, " + wife.getName() + ", that has passed away in the last 30 days.", husb.getLineNumber()));
					this.listDescendents(family, families, individuals, wife);
				}
			}
		}
	}

}
