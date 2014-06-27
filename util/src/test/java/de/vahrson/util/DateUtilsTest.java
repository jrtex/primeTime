package de.vahrson.util;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import junit.framework.TestCase;
/**
 * Insert the type's description here.
 * Creation date: (10.12.02 12:03:27)
 * @author: 
 */
public class DateUtilsTest extends TestCase {
	/**
	 * DateUtilsTest constructor comment.
	 */
	public DateUtilsTest() {
		super();
	}

	/**
	 * Set up the test fixture, i.e. the local environment that is used by the test methods.
	 *
	 * @see #tearDown()
	 */
	public void setUp() throws Exception {
	}

	/**
	 * Tear down the test fixture erected by <code>setUp()</code>.
	 *
	 * @see #setUp()
	 */
	public void tearDown() throws Exception {
	}

	/**
	 * Insert the method's description here.
	 * Creation date: (10.12.02 12:03:43)
	 */
	public void testCompareAsDay() {
		Calendar cal= new GregorianCalendar();
		cal.set(2002, 12, 31);
		Date d1= cal.getTime();
		cal.set(2003, 12, 31);
		Date d2= cal.getTime();
		assertTrue(DateUtils.compareAsDays(d1, d2) < 0);

		cal.set(2002, 12, 31);
		d1= cal.getTime();
		cal.set(2003, 1, 1);
		d2= cal.getTime();
		assertTrue(DateUtils.compareAsDays(d1, d2) < 0);

		cal.set(2002, 12, 32);
		d1= cal.getTime();
		cal.set(2003, 1, 1);
		d2= cal.getTime();
		assertTrue(DateUtils.compareAsDays(d1, d2) == 0);
	}

	public void testParseYear() {
		/*
		 * (1) az 00.00.YYYY az
		 * (2) az YYYY.00.00 az
		 * (3) az 0.0.YY ay
		 * (4) az YYYY az
		 * (5) az YY az
		 */
		checkParsedYear("x?)8 8-astrudfk1nbcv1.09.2002cb6 wuieifukwghdmd;", 2002);
		checkParsedYear("x?)8 8-as2003fk1nbcv1999-12-22cb6 wu 95 if1990hdmd;", 1999);
		checkParsedYear("x?)8 8-as2001fk1nbcv10.3.3cb6 wu 95 if1990hdmd;", 2003);
		checkParsedYear("x?)8 8-as2003fk1nbcv1.1.50cb6 wu 95 if1990hdmd;", 1950);
		checkParsedYear("x?)8 8-as2003fk1nbcv cb6 wu 95 if1990hdmd;", 2003);
		checkParsedYear("1899-1999-2100", 1999);
		checkParsedYear("1.1.51", 1951);
		checkParsedYear("x?)8 8-as66fk1nbcvcb6 wu 95 if1899hdmd;", 1966);
		checkParsedYear("0", 2000);
	}

	private void checkParsedYear(String input, int ref) {
		int year= DateUtils.parseYear(input);
		assertEquals(ref, year);
	}
	
	public void testParsedDate()  throws Exception{
		checkParsedDate("x?)8 8-astrudfk1nbcv1.09.2002cb6 wuieifukwghdmd;", "2002-09-01");
		checkParsedDate("x?)8 8-as2003fk1nbcv1999-12-22cb6 wu 95 if1990hdmd;", "1999-12-22");
		checkParsedDate("x?)8 8-as2003fk1nbcv10.3.03cb6 wu 95 if1990hdmd;", "2003-03-10");
		checkParsedDate("x?)8 8-astrudfk1nbcv1.9.2cb6 wuieifukwghdmd;", "2002-09-01");		
	}
	
	private void checkParsedDate(String input, String ref) throws Exception{
		Date d= DateUtils.parseDate(input);
		Date dr= DateUtils.parseStandardDayFormat(ref);
		assertTrue( d + " | " + dr, DateUtils.isSameDay(d,dr));
	}

	public void testPattern() {
		String pstr= "(?:[0123][0-9])|[0-9]";
		Pattern p= Pattern.compile(pstr);
		Matcher m= p.matcher("xx1yy");
		assertTrue(m.find());
	}
	
	public void testClipTimeOfDay() {
		Calendar cal1= new GregorianCalendar(2003,12,17,23,30,0);
		Calendar cal2= new GregorianCalendar(2003,12,17,0,0,0);
		assertEquals(cal2.getTime().getTime(), DateUtils.clipTimeOfDay(cal1.getTime()).getTime());
		
	}
}