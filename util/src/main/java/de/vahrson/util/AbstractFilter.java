package de.vahrson.util;

import java.util.*;

import de.vahrson.util.CollectionUtils;

abstract public class AbstractFilter implements Filter {
	/**
	 * Answer a filtered list of Reservations
	 * For compatibility; @see public Collection filter(Collection source)
	 */
	public List filter(List source) {
		return (List) filter((Collection)source);
	}
	
	public Collection filter(Collection source) {
		Collection answer= CollectionUtils.createDestinationCollection(source);
		for (Iterator i= source.iterator(); i.hasNext();) {
			Object o= (Object) i.next();
			if (accept(o)) {
				answer.add(o);
			}			
		}
		return answer;
	}
}