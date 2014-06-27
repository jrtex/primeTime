package de.vahrson.util;
import java.util.*;

import junit.framework.TestCase;
/**
 * Insert the type's description here.
 * Creation date: (12.03.03 13:58:31)
 * @author: 
 */
public class ChooseOnePermutionIteratorTest extends TestCase {
/**
 * ChooseOnePermutionIteratorTest constructor comment.
 */
public ChooseOnePermutionIteratorTest() {
	super();
}


/**
 * Insert the method's description here.
 * Creation date: (12.03.03 14:05:59)
 * @param o java.lang.Object
 * @param ref char[]
 */
private void check(Object []o, char[] ref) {
	assertEquals(ref.length, o.length);
	for (int i= 0; i < o.length; i++) {
		assertEquals(ref[i], ((Character)o[i]).charValue());
	}
}


/**
 * Insert the method's description here.
 * Creation date: (12.03.03 14:05:59)
 * @param o java.lang.Object
 * @param ref char[]
 */
private void check(Object o, char[] ref) {
	Object [] x= (Object[])o;
	assertEquals(ref.length, x.length);
	for (int i= 0; i < x.length; i++) {
		assertEquals(ref[i], ((Character)x[i]).charValue());
	}
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
 * Creation date: (12.03.03 13:58:45)
 */
public void test1() {
	ArrayList A= new ArrayList();
	A.add( new Character('A'));
	A.add( new Character('B'));
	A.add( new Character('C'));

	ArrayList B= new ArrayList();
	B.add( new Character('K'));
	B.add( new Character('M'));
	
	ArrayList C= new ArrayList();
	C.add( new Character('X'));

	ArrayList input= new ArrayList();
	input.add(A);
	input.add(B);
	input.add(C);

	ChooseOnePermutionIterator i= new ChooseOnePermutionIterator(input);
	check( i.next(), new char[]{'A', 'K', 'X'});
	
	check( i.next(), new char[]{'A', 'M', 'X'});


	check( i.next(), new char[]{'B', 'K', 'X'});
	
	check( i.next(), new char[]{'B', 'M', 'X'});


	check( i.next(), new char[]{'C', 'K', 'X'});
	
	check( i.next(), new char[]{'C', 'M', 'X'});

	}


/**
 * Insert the method's description here.
 * Creation date: (12.03.03 13:58:45)
 */
public void test2() {
	ArrayList A= new ArrayList();
	A.add( new Character('A'));
	A.add( new Character('B'));
	A.add( new Character('C'));

	ArrayList B= new ArrayList();
	B.add( new Character('K'));
	B.add( new Character('M'));
	
	ArrayList C= new ArrayList();
	C.add( new Character('X'));
	C.add( new Character('Y'));

	ArrayList input= new ArrayList();
	input.add(A);
	input.add(B);
	input.add(C);

	ChooseOnePermutionIterator i= new ChooseOnePermutionIterator(input);
	check( i.next(), new char[]{'A', 'K', 'X'});
	check( i.next(), new char[]{'A', 'K', 'Y'});
	
	check( i.next(), new char[]{'A', 'M', 'X'});
	check( i.next(), new char[]{'A', 'M', 'Y'});


	check( i.next(), new char[]{'B', 'K', 'X'});
	check( i.next(), new char[]{'B', 'K', 'Y'});
	
	check( i.next(), new char[]{'B', 'M', 'X'});
	check( i.next(), new char[]{'B', 'M', 'Y'});


	check( i.next(), new char[]{'C', 'K', 'X'});
	check( i.next(), new char[]{'C', 'K', 'Y'});
	
	check( i.next(), new char[]{'C', 'M', 'X'});
	check( i.next(), new char[]{'C', 'M', 'Y'});

	}
}