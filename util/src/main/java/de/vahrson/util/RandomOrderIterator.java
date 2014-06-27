package de.vahrson.util;
import java.util.*;
/**
 * An Iterator that randomly draws elements from its underlying collection.
 * N.B. This iterator does not recognize when the underlying collection is modified.
 * DO NOT MODIFY THE UNDERLYING COLLCTION WHILE ITERATING. 
 * Creation date: (26.08.2002 11:36:09)
 * @author: 
 */
public class RandomOrderIterator implements Iterator{
	private Collection fSource;
	private Iterator fDelegate;
	private int fIndex;

/**
 * RandomOrderIterator constructor comment.
 */
private RandomOrderIterator() {
	super();
}


/**
 * RandomOrderIterator constructor comment.
 */
public RandomOrderIterator(Collection c) {
	super();
	ArrayList l= new ArrayList(c);
	java.util.Collections.shuffle(l);
	fDelegate= l.iterator();
}


/**
 * Insert the method's description here.
 * Creation date: (26.08.2002 11:41:20)
 * @return boolean
 */
public boolean hasNext() {
	return fDelegate.hasNext();
}


/**
 * Insert the method's description here.
 * Creation date: (26.08.2002 11:41:40)
 * @return java.lang.Object
 */
public Object next() {
	fIndex++;
	return fDelegate.next();
}


/**
 * Insert the method's description here.
 * Creation date: (26.08.2002 11:42:26)
 */
public void remove() {
	throw new UnsupportedOperationException();
}


/**
 * Insert the method's description here.
 * Creation date: (20.10.2002 12:48:51)
 * @return java.lang.String
 */
public String toString() {
	return fIndex + "/" + fSource.size();
}
}