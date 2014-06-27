/*
 * COPYRIGHT 2003 Wolfgang Vahrson (mail@vahrson.de). ALL RIGHTS RESERVED.
 * 
 * Created on 03.08.2003
 *
 * $Header: /Users/wova/laufend/cvs/Utils/de/vahrson/util/DayComparatorTest.java,v 1.1 2003/08/03 16:40:09 wova Exp $
 */
package de.vahrson.util;

import java.util.Calendar;
import java.util.GregorianCalendar;

import junit.framework.TestCase;

/**
 * @author wova
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class DayComparatorTest extends TestCase {

	/**
	 * Constructor for DayComparatorTest.
	 * @param arg0
	 */
	public DayComparatorTest(String arg0) {
		super(arg0);
	}

	public void testCompare() throws Exception{
		Calendar a= new GregorianCalendar(2003,7,3,12,0);
		Calendar b= new GregorianCalendar(2003,7,3,6,0);
		assertEquals(0, new DayComparator().compare(a,b));
		assertEquals(0, new DayComparator().compare(a.getTime(),b.getTime()));
		
		b= new GregorianCalendar(2003,7,2,23,59);
		assertTrue(new DayComparator().compare(a,b)>0);
		assertTrue(new DayComparator().compare(a.getTime(),b.getTime())>0);
		 
		b= new GregorianCalendar(2003,7,4,0,0);
		assertTrue(new DayComparator().compare(a,b)<0);
		assertTrue(new DayComparator().compare(a.getTime(),b.getTime())<0);
		
		
		 
	}

}
