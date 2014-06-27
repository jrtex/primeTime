package de.vahrson.util;


import java.util.*;
/**
 * Iterator over all permutations of a list
 * It is assumed that elements in the list are unique
 * Creation date: (16.10.2002 23:38:53)
 * @author: Wolfgang Vahrson
 */
public class ListPermutationsIterator implements java.util.Iterator {
	private List fSource;
	private int [] fBeenHere;
	private long fMaxBeenHere;
	private List fCurrent, fNext;
	private long fMaxPermutations;
	private long fPermutationIndex;
/**
 * ListPermutations constructor comment.
 */
public ListPermutationsIterator(List source) {
	super();
	fSource= source;
	fBeenHere= new int[fSource.size()];
	fMaxBeenHere= factorial(fSource.size()-1);
	fMaxPermutations= factorial(fSource.size());
}
/**
 * Insert the method's description here.
 * Creation date: (16.10.2002 23:53:13)
 * @return long
 * @param n long
 */
public static long factorial(final long n) {
	long answer;
	if (n>1) {
		answer= n * factorial(n-1);
	}
	else {
		answer= n;
	}	
	return answer;
}
/**
 * Returns <tt>true</tt> if the iteration has more elements. (In other
 * words, returns <tt>true</tt> if <tt>next</tt> would return an element
 * rather than throwing an exception.)
 *
 * @return <tt>true</tt> if the iterator has more elements.
 */
public boolean hasNext() {
	if (fCurrent == null) {
		next();
	}
	return fNext != null;
}
	/**
	 * Returns the next element in the interation.
	 *
	 * @returns the next element in the interation.
	 * @exception NoSuchElementException iteration has no more elements.
	 */
public Object next() {
	if (fCurrent==null) {
		fNext= nextPermutation();
	}
	fCurrent= fNext;
	fNext= nextPermutation();
	return fCurrent;
}
/**
 * Insert the method's description here.
 * Creation date: (16.10.2002 23:46:44)
 * @return java.util.List
 */
private List nextPermutation() {
	List answer= null;
	if (fPermutationIndex < fMaxPermutations) {
		final int n= fSource.size();
		Object[] permut= new Object[n];
		for (int k= 0; k < n; k++) {
			Object o= fSource.get(k);
			for (int i= 0; i < n; i++) {
				if (permut[i] == null && fBeenHere[k] < fMaxBeenHere) {
					permut[i]= o;
					fBeenHere[k]++;
					break;
				}
			}
		}
		answer= Arrays.asList(permut);
		fPermutationIndex++;
	}

	return answer;
}
/**
 * 
 * Removes from the underlying collection the last element returned by the
 * iterator (optional operation).  This method can be called only once per
 * call to <tt>next</tt>.  The behavior of an iterator is unspecified if
 * the underlying collection is modified while the iteration is in
 * progress in any way other than by calling this method.
 *
 * @exception UnsupportedOperationException if the <tt>remove</tt>
 *		  operation is not supported by this Iterator.
 
 * @exception IllegalStateException if the <tt>next</tt> method has not
 *		  yet been called, or the <tt>remove</tt> method has already
 *		  been called after the last call to the <tt>next</tt>
 *		  method.
 */
public void remove() {
	throw new UnsupportedOperationException();
}
}
