/*
 * COPYRIGHT 2003 Wolfgang Vahrson (mail@vahrson.de). ALL RIGHTS RESERVED.
 * 
 * Created on 02.08.2003
 *
 * $Header: /Users/wova/laufend/cvs/Utils/de/vahrson/util/DayComparator.java,v 1.1 2003/08/03 16:40:09 wova Exp $
 */
package de.vahrson.util;

import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @author wova
 *
 * Compare dates as days, disregard hours etc.
 */
public class DayComparator implements Comparator {

	/* (non-Javadoc)
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	public int compare(Object a, Object b) {
		Calendar c1= checkType(a);
		Calendar c2= checkType(b);
		return daysSinceChrist(c1)-daysSinceChrist(c2);
	}
	
	static Calendar checkType(Object x) {
		Calendar answer;
		if (x instanceof Date) {
			answer= new GregorianCalendar();
			answer.setTime((Date)x);
		}
		else if (x instanceof Calendar) {
			answer= (Calendar)x;		
		}
		else {
			throw new IllegalArgumentException("Neither Date nor Calendar" + x);
		}
		return answer;
	}
	
	static int daysSinceChrist(Calendar cal) {
		return cal.get(Calendar.YEAR) * 365 + cal.get(Calendar.DAY_OF_YEAR);
	}

}
