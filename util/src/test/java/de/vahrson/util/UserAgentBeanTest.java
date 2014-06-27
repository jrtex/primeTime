package de.vahrson.util;

import junit.framework.TestCase;

/**
 * Insert the type's description here.
 * Creation date: (18.12.02 13:34:25)
 * @author: 
 */
public class UserAgentBeanTest extends TestCase implements UserAgent{
/**
 * UserAgentBeanTest constructor comment.
 */
public UserAgentBeanTest() {
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
 * Creation date: (18.12.02 13:35:10)
 */
public void testIEMac() {
	UserAgentBean uab= new UserAgentBean();
	uab.setRequestHeaderUserAgent("Mozilla/4.0 (compatible; MSIE 5.16; Mac_PowerPC)");
	assertEquals(uab.getOS(), OS_MAC);
	assertEquals(uab.getBrowser(), BROWSER_IE);
	assertEquals(uab.getBrowserVersion(), "5");
}


/**
 * Insert the method's description here.
 * Creation date: (18.12.02 13:35:10)
 */
public void testIEWin6() {
	UserAgentBean uab= new UserAgentBean();
	uab.setRequestHeaderUserAgent("Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");
	assertEquals(uab.getOS(), OS_WIN);
	assertEquals(uab.getBrowser(), BROWSER_IE);
	assertEquals(uab.getBrowserVersion(), "6");
}
}