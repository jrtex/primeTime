package de.vahrson.util;

import java.util.*;

import junit.framework.TestCase;
/**
 * Insert the type's description here.
 * Creation date: (13.10.2002 18:51:12)
 * @author: Wolfgang Vahrson
 */
public class CollectionsTest extends TestCase {
	/**
	 * CollectionsTest constructor comment.
	 */
	public CollectionsTest() {
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
	 * Creation date: (13.10.2002 18:51:32)
	 */
	public void testExtractFrom() {
		Set source= new HashSet(Arrays.asList(new String[] { "a", "b", "d", "e" }));
		Set ex= new HashSet(Arrays.asList(new String[] { "a", "d", "f" }));
		Set r= CollectionUtils.extractFrom(source, ex);
		assertEquals(r.size(), 2);
		assertTrue(r.contains("a"));
		assertTrue(r.contains("d"));

		assertEquals(source.size(), 2);
		assertTrue(source.contains("b"));
		assertTrue(source.contains("e"));

	}

	public void testCreateCollection() {
		Vector v= new Vector();
		Collection x= CollectionUtils.createCollection(v);
		assertEquals(v.getClass().getName(), x.getClass().getName());

		String[] s= { "A", "B", "C" };
		List l= Arrays.asList(s);
		x= CollectionUtils.createCollection(l);
		assertEquals("java.util.ArrayList", x.getClass().getName());
	}
	
	public void testJunction() {
		String [] S= {"A", "B", "C"};
		String [] R= {"B", "C", "D"};
		
		Collection x= CollectionUtils.junction(Arrays.asList(S), Arrays.asList(R));
		assertEquals(2, x.size());
		assertTrue(x.contains(S[1]));
		assertTrue(x.contains(S[2]));
	}
	
	public static class IComp implements Comparator {
		
			/* (non-Javadoc)
		 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
		 */
		public int compare(Object o1, Object o2) {
			return  o1.toString().toLowerCase().compareTo(o2.toString().toLowerCase());
		}
}
	
	public void testReplace() {
		String [] s= {"A", "B", "C"};
		Collection coll= new ArrayList();
		coll.addAll(Arrays.asList(s));	
		assertTrue(!coll.contains("b"));
		assertTrue(coll.contains("B"));
		CollectionUtils.replace(coll, "b", new IComp());
		assertTrue(coll.contains("b"));
		assertTrue(!coll.contains("B"));
	}
}
