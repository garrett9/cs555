package edu.stevens.cs555.project.processors.tests;

import static org.junit.Assert.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import edu.stevens.cs555.project.Family;
import edu.stevens.cs555.project.Individual;
import edu.stevens.cs555.project.processors.DatesBeforeCurrentDate;

public class DatesBeforeCurrentDateTest {

	Individual[] individualsToFail;
	Individual[] individualsToPass;
	Family[] familiesToFail;
	Family[] familiesToPass;
	
	@Before
	public void setUp() throws Exception {
		Date lastYear = new Date();
		Date nextYear = new Date();
		lastYear.setYear(nextYear.getYear() - 1);
		nextYear.setYear(nextYear.getYear() + 1);
		
		DateFormat format = new SimpleDateFormat("d MMM yyyy");
		
		Individual birthFail = new Individual(1, 1);
		Individual deathFail = new Individual(2, 2);
		Family marriageFail = new Family(3, 3);
		Family divorceFail = new Family(4, 4);
		birthFail.setBirt(format.format(nextYear));
		deathFail.setDeat(format.format(nextYear));
		marriageFail.setMarr(format.format(nextYear));
		divorceFail.setMarr(format.format(nextYear));
		
		individualsToFail = new Individual[] {birthFail, deathFail};
		familiesToFail = new Family[] {marriageFail, divorceFail};
		
		Individual birthPass = new Individual(5, 5);
		Individual deathPass = new Individual(6, 6);
		Family marriagePass = new Family(7, 7);
		Family divorcePass = new Family(8, 8);
		birthPass.setBirt(format.format(lastYear));
		deathPass.setDeat(format.format(lastYear));
		marriagePass.setMarr(format.format(lastYear));
		divorcePass.setMarr(format.format(lastYear));

		individualsToPass = new Individual[] {birthPass, deathPass};
		familiesToPass = new Family[] {marriagePass, divorcePass};
		
	}

	@Test
	public void testRun() {		
		DatesBeforeCurrentDate failTest = new DatesBeforeCurrentDate();
		failTest.run(familiesToFail, individualsToFail);
		assertEquals(individualsToFail.length + familiesToFail.length, failTest.getValidationErrors().size());
		
		DatesBeforeCurrentDate passTest = new DatesBeforeCurrentDate();
		failTest.run(familiesToPass, individualsToPass);
		assertEquals(0, passTest.getValidationErrors().size());

	}

}
