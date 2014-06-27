package de.vahrson.util;
/**
 * Insert the type's description here.
 * Creation date: (11.09.2002 23:24:00)
 * @author: 
 */
public class AcceptNoneFilter extends AbstractFilter {


/**
 * Answer whether the Reservation passes this filter
 * Creation date: (11.09.2002 23:24:00)
 * @return boolean
 * @param r de.vahrson.reservation.Reservation
 */
public boolean accept(Object r) {
	return false;
}
}