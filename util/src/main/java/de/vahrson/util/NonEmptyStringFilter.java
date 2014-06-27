package de.vahrson.util;
/**
 * Accept only non-null and non-"" String
 * Creation date: (08.11.2002 19:25:18)
 * @author: Wolfgang Vahrson
 */
public class NonEmptyStringFilter extends AbstractFilter {
/**
 * Answer whether the Object passes this filter
 * Creation date: (08.11.2002 19:25:18)
 * @return boolean
 * @param r de.vahrson.reservation.Reservation
 */
public boolean accept(Object o) {
	return !TextUtils.isEmptyString(o);
}
}