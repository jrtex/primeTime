package de.vahrson.util;

import junit.framework.TestCase;

/**
 * Insert the type's description here.
 * Creation date: (08.10.2002 21:29:13)
 * @author: Wolfgang Vahrson
 */
public class MathUtilsTest extends TestCase {
/**
 * MathUtilsTest constructor comment.
 */
public MathUtilsTest() {
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
 * Creation date: (08.10.2002 21:29:38)
 */
public void testRandom1() {
	final int lbd= 2;
	final int ubd= 6;
	int seen[]= new int[ubd];
	for (int i= 0; i < 100; i++) {
		int x= MathUtils.random(lbd, ubd);
		seen[x]++;
		assertTrue("x must not be < " + lbd, x >= lbd);
		assertTrue("x must not be >= " + ubd, x < ubd);
	}

	for (int i= lbd; i < ubd; i++) {
		assertTrue(seen[i] > 0);
	}

}
}
