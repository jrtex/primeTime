package de.vahrson.util;
import junit.framework.TestCase;
/**
 * Insert the type's description here.
 * Creation date: (12.03.2002 21:41:38)
 * @author: 
 */
public class BeanUtilTest extends TestCase {
/**
 * BeanUtilTest constructor comment.
 */
public BeanUtilTest() {
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
 * Creation date: (12.03.2002 21:42:39)
 */
public void testURLification() {
	ABean bean= new ABean();
	bean.setAFieldContent("A value with spaces");
	bean.setB(24);
	bean.setC(null);

	String url= BeanUtils.asURL(bean);
	assertEquals(url, "aFieldContent=A+value+with+spaces&b=24");
}
}