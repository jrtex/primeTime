/*
 * Created on 27.01.2004
 *
 * $Header: /Users/wova/laufend/cvs/Utils/de/vahrson/util/IntRangeTest.java,v 1.2 2004/01/28 15:31:03 wova Exp $
 *  */
package de.vahrson.util;

import java.util.*;

import junit.framework.TestCase;

/**
 * @author wova
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class IntRangeTest extends TestCase {

	/**
	 * 
	 */
	public IntRangeTest() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param arg0
	 */
	public IntRangeTest(String arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public void testComparison() {
		IntRange[] ir =
			new IntRange[] { new IntRange(-2, 1), new IntRange(-2, 3), new IntRange(-2, 1), new IntRange(2, 1)};
		assertTrue(ir[0].compareTo(ir[1]) < 0);
		assertTrue(ir[0].compareTo(ir[2]) == 0);
		assertTrue(ir[1].compareTo(ir[2]) > 0);
		assertTrue(ir[0].equals(ir[2]));
		assertTrue(ir[2].compareTo(ir[3]) < 0);
		assertTrue(ir[3].compareTo(ir[2]) > 0);
	}

	public void testIntersects() {
		/**
		 * Answer whether this IntRange intersects with another IntRange r
		 *
		 *   -----------------------------------> t
		 *1)       a0[=====]az	
		 *      r0[=====]rz
		 *2)
		 *	       a0[=====]az
		 *             r0[=====]rz
		 *3)
		 *
		 *         a0[=====]az
		 *    r0[=================]rz
		 *4)
		 *         ao[=====]az
		 *            r0[=]rz
		 * (already covered by 1)||2))
		 *
		 * N.B.: az==r0, rz==a0 are not intersecting! (upper bounds are exclusive]
		 *
		 */
		assertTrue(new IntRange(3, 5).intersects(new IntRange(2, 5)));
		assertTrue(new IntRange(-2, 0).intersects(new IntRange(-2, 0)));
		assertTrue(new IntRange(-2, 0).intersects(new IntRange(-3, 1)));
		assertTrue(new IntRange(-4, 4).intersects(new IntRange(-1, 1)));

		assertTrue(!new IntRange(-4, 4).intersects(new IntRange(4, 8)));
		assertTrue(!new IntRange(-4, 0).intersects(new IntRange(0, 4)));
	}

	public void testIntersection() {
		assertEquals(new IntRange(3, 5), new IntRange(3, 5).intersection(new IntRange(2, 5)));
		assertEquals(new IntRange(-2, 0), new IntRange(-2, 0).intersection(new IntRange(-2, 0)));
		assertEquals(new IntRange(-2, 0), new IntRange(-2, 0).intersection(new IntRange(-3, 1)));
		assertEquals(new IntRange(-1, 1), new IntRange(-4, 4).intersection(new IntRange(-1, 1)));

		assertEquals(IntRange.EMPTY, new IntRange(-4, 4).intersection(new IntRange(4, 8)));
		assertEquals(IntRange.EMPTY, new IntRange(-4, 0).intersection(new IntRange(0, 4)));
	}

	public void testUnion() {
		assertEquals(new IntRange(2, 5), new IntRange(3, 5).union(new IntRange(2, 5)));
		assertEquals(new IntRange(-2, 0), new IntRange(-2, 0).union(new IntRange(-2, 0)));
		assertEquals(new IntRange(-3, 1), new IntRange(-2, 0).union(new IntRange(-3, 1)));
		assertEquals(new IntRange(-4, 4), new IntRange(-4, 4).union(new IntRange(-1, 1)));

		assertEquals(new IntRange(-4, 8), new IntRange(-4, 4).union(new IntRange(4, 8)));
		assertEquals(new IntRange(-4, 4), new IntRange(-4, 0).union(new IntRange(0, 4)));

		assertEquals(new IntRange(-4, 4), new IntRange(-4, -2).union(new IntRange(2, 4)));
	}

	public void testAllIntersections() {
		IntRange[] ir1 = new IntRange[] { new IntRange(2, 3), new IntRange(5, 7), new IntRange(-2, 1)};
		IntRange[] ir2 = new IntRange[] { new IntRange(-1, 1), new IntRange(7, 9), new IntRange(4, 6)};
		List x = IntRange.allIntersections(Arrays.asList(ir1), Arrays.asList(ir2));
		assertEquals(2, x.size());
		assertEquals(new IntRange(-1, 1), x.get(0));
		assertEquals(new IntRange(5, 6), x.get(1));
	}

}
