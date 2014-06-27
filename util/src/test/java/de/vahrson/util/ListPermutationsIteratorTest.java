package de.vahrson.util;

import java.util.*;

import junit.framework.TestCase;
/**
 * Insert the type's description here.
 * Creation date: (17.10.2002 00:11:57)
 * @author: Wolfgang Vahrson
 */
public class ListPermutationsIteratorTest extends TestCase {
/**
 * ListPermutationsIteratorTest constructor comment.
 */
public ListPermutationsIteratorTest() {
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
 * Creation date: (17.10.2002 00:12:10)
 */
public void test() {
	List s= Arrays.asList(new String[] { "A", "B", "C", "D" });
	ListPermutationsIterator i= new ListPermutationsIterator(s);
	while (i.hasNext()) {
		List x= (List) i.next();
		System.out.println(x);
	}
}
}
