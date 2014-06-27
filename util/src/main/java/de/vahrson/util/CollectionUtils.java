package de.vahrson.util;

import java.util.*;

/**
 * Insert the type's description here. Creation date: (02.09.2002 22:29:15)
 * 
 * @author:
 */
abstract public class CollectionUtils {

	/**
	 * Answer a List of the same type as the source List Creation date:
	 * (02.09.2002 17:03:53)
	 * 
	 * @return java.util.List
	 * @param source
	 *            java.util.List
	 */
	public static List createDestinationList(List source) {
		List answer= null;
		answer= (List) createDestinationCollection(source);
		return answer;
	}

	public static Collection createDestinationCollection(Collection source) {
		Collection answer= null;
		try {
			answer= (Collection) source.getClass().newInstance();
		}
		catch (InstantiationException ie) {
			answer= new ArrayList();
		}
		catch (IllegalAccessException iae) {
			DefaultErrorHandler.handleError("Collections.createDestinationCollection", iae);
		}
		return answer;
	}

	/**
	 * Answer the first object in source that passes filter. Remove it from
	 * source Call repeatedly to remove all Creation date: (18.01.03 17:57:13)
	 */
	public static Object extract(Collection source, Filter filter) {
		Object answer= null;

		Iterator i= source.iterator();
		while (i.hasNext() && answer == null) {
			Object t= i.next();
			if (filter.accept(t)) {
				answer= t;
				i.remove();
			}
		}

		return answer;
	}

	/**
	 * Answer a set containing all elements that are both in source and in query
	 * Delete these elements from source Creation date: (13.10.2002 16:23:00)
	 * 
	 * @return java.util.Set
	 * @param source
	 *            java.util.Set
	 * @param elements
	 *            java.util.Collection
	 */
	public static Set extractFrom(Set source, Collection query) {
		Set answer= new HashSet();
		Iterator i= query.iterator();
		while (i.hasNext()) {
			Object o= i.next();
			if (source.remove(o)) {
				answer.add(o);
			}
		}
		return answer;
	}

	/**
	 * Answer a random element from source Creation date: (13.10.2002 17:08:01)
	 * 
	 * @return java.lang.Object
	 * @param source
	 *            java.util.Collection
	 */
	public static Object pickRandom(Collection source) {
		Object[] S= source.toArray();
		return S[MathUtils.random(0, S.length)];
	}

	/**
	 * Answer a list that consits of wrapper objects of the arrays primitive
	 * types
	 */
	public static List wrapPrimitive(double[] array) {
		ArrayList answer= new ArrayList();
		for (int i= 0; i < array.length; i++) {
			answer.add(new Double(array[i]));
		}
		return answer;
	}

	/**
	 * Answer a list that consits of wrapper objects of the arrays primitive
	 * types
	 */
	public static List wrapPrimitive(int[] array) {
		ArrayList answer= new ArrayList();
		for (int i= 0; i < array.length; i++) {
			answer.add(new Integer(array[i]));
		}
		return answer;
	}

	/**
	 * Create and answer a collection that is the same type, or the closest
	 * equivalent of col.
	 * 
	 * @param col
	 * @return
	 */
	public static Collection createCollection(Collection col) {
		Collection answer;
		try {
			answer= (Collection) col.getClass().newInstance();
		}
		catch (Exception e) {
			if (col instanceof List) {
				answer= new ArrayList();
			}
			else if (col instanceof SortedSet) {
				answer= new TreeSet();
			}
			else if (col instanceof Set) {
				answer= new HashSet();
			}
			else {
				answer= new ArrayList(); // should this throw an exception?
			}
		}
		return answer;
	}

	/**
	 * Answer the junction between a and b. Answer is of the same type as a.
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public static Collection junction(Collection a, Collection b) {
		Collection answer= createCollection(a);
		for (Iterator i= a.iterator(); i.hasNext();) {
			Object oa= i.next();
			for (Iterator k= b.iterator(); k.hasNext();) {
				Object ob= k.next();
				if (oa.equals(ob)) {
					answer.add(oa);
				}
			}
		}
		return answer;
	}
	/**
	 * Answer whether or not coll contains Object o, 
	 * but not based on equal(), but on compareTo()==0
	 * @param coll
	 * @param o
	 * @param c
	 * @return
	 */
	public static boolean contains(Collection coll, Object o, Comparator c) {
		boolean answer= false;
		for (Iterator i= coll.iterator(); i.hasNext() && !answer;) {
			answer= 0== c.compare(o, i.next());
		}
		return answer;
	}
	
	/**
	 * Replace all objects in coll with o, if c.compare(o,coll.element) == 0
	 * Answer number of replacements
	 * Does not preserve order!
	 * @param coll
	 * @param o
	 * @param c
	 * @return
	 */
	public static int replace(Collection coll, Object o, Comparator c) {
		List removals= new ArrayList();
		
		for (Iterator i= coll.iterator(); i.hasNext();) {
			Object old= i.next();
			if (0== c.compare(o, old)) {
				removals.add(old);
			}
		}
		for (Iterator i= removals.iterator(); i.hasNext();) {
			Object old= (Object) i.next();
			coll.remove(old);
		}
		coll.add(o);
		return removals.size();
	}

}