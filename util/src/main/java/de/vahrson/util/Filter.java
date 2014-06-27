package de.vahrson.util;

import java.util.*;
/**
 * ReservationFilters work on lists of Reservations and filter them
 * Creation date: (02.09.2002 16:50:10)
 * @author: 
 */
public interface Filter {
/**
 * Answer whether the Object passes this filter
 * Creation date: (02.09.2002 17:02:20)
 * @return boolean
 * @param r de.vahrson.reservation.Reservation
 */
public boolean accept(Object r);
/**
 * Answer a filtered list of Reservations
 */
List filter(List source);

}
