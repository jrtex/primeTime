package de.vahrson.util;
import java.util.*;
/**
 * Insert the type's description here.
 * Creation date: (18.01.03 20:52:27)
 * @author: 
 */
public class CollectionSizeComparator implements Comparator{
	private boolean fIncreasingOrder;

/**
 * CollectionSizeComparator constructor comment.
 */
public CollectionSizeComparator(boolean increasingOrder) {
	super();
	fIncreasingOrder= increasingOrder;
}


	/**
	 * Compares its two arguments for order.  Returns a negative integer,
	 * zero, or a positive integer as the first argument is less than, equal
	 * to, or greater than the second.<p>
	 *
	 * The implementor must ensure that <tt>sgn(compare(x, y)) ==
	 * -sgn(compare(y, x))</tt> for all <tt>x</tt> and <tt>y</tt>.  (This
	 * implies that <tt>compare(x, y)</tt> must throw an exception if and only
	 * if <tt>compare(y, x)</tt> throws an exception.)<p>
	 *
	 * The implementor must also ensure that the relation is transitive:
	 * <tt>((compare(x, y)&gt;0) &amp;&amp; (compare(y, z)&gt;0))</tt> implies
	 * <tt>compare(x, z)&gt;0</tt>.<p>
	 *
	 * Finally, the implementer must ensure that <tt>compare(x, y)==0</tt>
	 * implies that <tt>sgn(compare(x, z))==sgn(compare(y, z))</tt> for all
	 * <tt>z</tt>.<p>
	 *
	 * It is generally the case, but <i>not</i> strictly required that 
	 * <tt>(compare(x, y)==0) == (x.equals(y))</tt>.  Generally speaking,
	 * any comparator that violates this condition should clearly indicate
	 * this fact.  The recommended language is "Note: this comparator
	 * imposes orderings that are inconsistent with equals."
	 * 
	 * @return a negative integer, zero, or a positive integer as the
	 * 	       first argument is less than, equal to, or greater than the
	 *	       second. 
	 * @throws ClassCastException if the arguments' types prevent them from
	 * 	       being compared by this Comparator.
	 */
public int compare(java.lang.Object o1, java.lang.Object o2) {
	if (fIncreasingOrder) {
		return ((Collection)o1).size() - ((Collection)o2).size();
	}
	else {
		return ((Collection)o2).size() - ((Collection)o1).size();
	}		
}
}