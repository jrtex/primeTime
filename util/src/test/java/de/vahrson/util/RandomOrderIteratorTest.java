package de.vahrson.util;

import java.util.*;

import junit.framework.TestCase;
/**
 * Insert the type's description here.
 * Creation date: (26.08.2002 11:46:57)
 * @author: 
 */
public class RandomOrderIteratorTest extends TestCase {
/**
 * RandomOrderIteratorTest constructor comment.
 */
public RandomOrderIteratorTest() {
	super();
}
/**
 * Set up the test fixture, i.e. the local environment that is used by the test methods.
 *
 * @see #tearDown()
 */
public void setUp() throws Exception {}
/**
 * Tear down the test fixture erected by <code>setUp()</code>.
 *
 * @see #setUp()
 */
public void tearDown() throws Exception {}
/**
 * Insert the method's description here.
 * Creation date: (26.08.2002 11:47:19)
 */
public void testOrder() {
	List s=  Arrays.asList(new String []{"1", "2", "3", "4", "5", "6", "7", "8", "9"});
	RandomOrderIterator i= new RandomOrderIterator(s);
	ArrayList d= new ArrayList();
	while ( i.hasNext()) {
		d.add( i.next());
	}
	assertEquals(s.size(), d.size());
	int sameIndex= 0;
	for (int k= 0; k<s.size(); k++) {
		if ( s.get(k).equals(d.get(k)) ) {
			sameIndex++;
		}
	}
	assertTrue( "There is a suspiciously high number of equal elements", sameIndex < 4);
} 
}
