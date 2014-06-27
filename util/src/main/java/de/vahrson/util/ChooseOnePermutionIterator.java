package de.vahrson.util;
import java.util.*;
/**
 * This iterator takes a list of collections as input. 
 * On each iteration, it answers a list that contains 
 * one element of each input collection. The iterator
 * terminates, when all permutations are exhausted.
 * Creation date: (12.03.03 13:12:56)
 * @author: 
 */
public class ChooseOnePermutionIterator implements Iterator{
	private Collection [] fCollections; // input
	private int fNum; //number of input collections 
	// state:
	private Iterator [] fIterators;
	private int fCurrentIterator; // the iterator being advanced
	private Object [] fResult;

/**
 * ChooseOnePermutionIterator constructor comment.
 */
public ChooseOnePermutionIterator(List collections) {
	super();
	fNum= collections.size();
	fCollections= (Collection[]) collections.toArray(new Collection[fNum]);

	// allocate
	fIterators= new Iterator[fNum];
	fResult= new Object[fNum];
	// init
	resetSubordinateIterators(-1);
	int index= findFreeSubordinateIterator();
	for (int i= 0; i < index; i++) {
		fResult[i]= fIterators[i].next();
	}
}


/**
 * Insert the method's description here.
 * Creation date: (12.03.03 13:29:04)
 */
private int countInputElements() {
	int answer= 0;
	for (int i= 0; i<fNum; i++) {
		answer+= fCollections[i].size();
	}
	return answer;
}


/**
 * Insert the method's description here.
 * Creation date: (12.03.03 13:50:55)
 */
private int findFreeSubordinateIterator() {
	int answer= -1;
	for (int i= fNum - 1; i >= 0 && answer == -1; i--) {
		if (fIterators[i].hasNext()) {
			answer= i;
		}
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
	return findFreeSubordinateIterator() != -1;
}


/**
 * Insert the method's description here.
 * Creation date: (12.03.03 13:27:15)
 * @return boolean
 */
private boolean isInputEmpty() {
	return countInputElements() == 0;
}


/**
 * Returns the next element in the interation.
 *
 * @returns the next element in the interation.
 * @exception NoSuchElementException iteration has no more elements.
 */
public java.lang.Object next() {
	int index= findFreeSubordinateIterator();
	if (index != -1) {
		resetSubordinateIterators(index);
		for (int i= index; i < fNum; i++) {
			fResult[i]= fIterators[i].next();
		}
	}
	return fResult;
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


/**
 * Reset the iterators "below" index
 * Creation date: (12.03.03 13:45:26)
 * @param index int
 */
private void resetSubordinateIterators(int index) {
	for (int i= index + 1; i < fNum; i++) {
		fIterators[i]= fCollections[i].iterator();
	}
}
}