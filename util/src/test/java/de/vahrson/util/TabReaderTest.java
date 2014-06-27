package de.vahrson.util;

import junit.framework.TestCase;

/**
 * Insert the type's description here.
 * Creation date: (17.01.03 16:30:59)
 * @author: 
 */
public class TabReaderTest extends TestCase {
/**
 * TabReaderTest constructor comment.
 */
public TabReaderTest() {
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
 * Creation date: (17.01.03 16:35:22)
 */
public void testJoin() {
	String multiField= TabReader.joinMultiFields(new String[] { "aaa", "bbb", "ccc" });
	assertEquals("aaa,bbb,ccc", multiField);

	multiField= TabReader.joinMultiFields(new String[] { "aaa"});
	assertEquals("aaa", multiField);

	multiField= TabReader.joinMultiFields(new String[] { "", "" });
	assertEquals(",", multiField);

	multiField= TabReader.joinMultiFields(new String[0]);
	assertEquals("", multiField);
}


/**
 * Insert the method's description here.
 * Creation date: (17.01.03 16:31:17)
 */
public void testSplit() {
	String[] fields= TabReader.splitMultiField("aaa,bbb,ccc");
	assertEquals("aaa", fields[0]);
	assertEquals("bbb", fields[1]);
	assertEquals("ccc", fields[2]);

	fields= TabReader.splitMultiField("aaa");
	assertEquals("aaa", fields[0]);

	fields= TabReader.splitMultiField(",");
	assertEquals(0, fields.length);

	fields= TabReader.splitMultiField("");
	assertEquals(1, fields.length);
}
}