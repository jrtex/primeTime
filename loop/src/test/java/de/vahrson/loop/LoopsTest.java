/*
 * Created on 10.04.2004
 *
 * $Header: /Users/wova/laufend/cvs/Utils/de/vahrson/loop/LoopsTest.java,v 1.1 2004/04/20 15:08:49 wova Exp $
 *  */
package de.vahrson.loop;
import java.util.*;

import de.vahrson.util.CollectionUtils;
import junit.framework.TestCase;

/**
 * @author wova
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class LoopsTest extends TestCase {

	/**
	 * 
	 */
	public LoopsTest() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param arg0
	 */
	public LoopsTest(String arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public void testSelectReset() {
		String[] items= { "A", "B", "C", "D" };
		Collection col= Arrays.asList(items);

		Block testForEquality= new DefaultBlock() {
			public boolean test(Object item) {
				return item.equals("A") || item.equals("D");
			}
		};

		Collection x= Loops.select(col, testForEquality);
		assertEquals(2, x.size());
		assertTrue(x.contains(items[0]));
		assertTrue(x.contains(items[3]));

		Collection y= Loops.reject(col, testForEquality);
		assertEquals(2, x.size());
		assertTrue(y.contains(items[1]));
		assertTrue(y.contains(items[2]));
	}

	public void testInject() {
		Integer[] items= { new Integer(2), new Integer(3), new Integer(4)};
		Collection col= Arrays.asList(items);

		Block sum= new DefaultBlock() {
			public Object execute(Object item1, Object item2) {
				int subtotal= ((Integer) item1).intValue();
				int curr= ((Integer) item2).intValue();
				return new Integer(subtotal + curr);
			}
		};

		Integer total= (Integer) Loops.injectInto(col, sum, new Integer(0));
		assertEquals(9, total.intValue());
	}

	public void testCollect() {
		Integer[] I= { new Integer(1), new Integer(2), new Integer(3)};
		Collection col= Arrays.asList(I);
		Block squareBlock= new DefaultBlock() {
			public Object execute(Object item) {
				return new Integer((int) Math.pow(((Integer) item).intValue(), 2));
			}
		};
		List x= (List) Loops.collect(col, squareBlock);
		assertEquals(1, ((Integer) x.get(0)).intValue());
		assertEquals(4, ((Integer) x.get(1)).intValue());
		assertEquals(9, ((Integer) x.get(2)).intValue());
	}

	public void testUniquePairs() throws Exception {
		String[] S= { "A", "B" };

		Collection col= Arrays.asList(S);

		class CollectPairs extends DefaultBlock {
			ArrayList fPairs= new ArrayList();
			public Object execute(Object item1, Object item2) {
				fPairs.add(new Object[] { item1, item2 });
				return null;
			}
			List getPairs() {
				return fPairs;
			}
		}
		CollectPairs collectPairs= new CollectPairs();

		Loops.uniquePairsE(col, collectPairs);
		List x= collectPairs.getPairs();
		assertEquals(1, x.size());
		Object[] pair= (Object[]) x.get(0);
		assertTrue(S[0] == pair[0]);
		assertTrue(S[1] == pair[1]);

		String[] R= { "c", "a", "b" };
		Set rSet= new HashSet();
		rSet.addAll(Arrays.asList(R));
		CollectPairs cp2= new CollectPairs();
		Loops.uniquePairsE(rSet, cp2);
		List y= cp2.getPairs();
		assertEquals(3, y.size());
	}

	public void testListPairsCollection() {
		String[] S= { "A", "B" };
		Set R= new HashSet();
		R.addAll(Arrays.asList(S));

		List pairs1= Loops.listPairsCollection(R, false, false);
		assertEquals(1, pairs1.size());
		assertTrue(containsAnyPair(pairs1, S[0], S[1]));
		 
		List pairs2= Loops.listPairsCollection(R, true, false);
		assertEquals(3, pairs2.size());
		assertTrue(containsAnyPair(pairs2, S[0], S[1]));
		assertTrue(containsAnyPair(pairs2, S[0], S[0]));
		assertTrue(containsAnyPair(pairs2, S[1], S[1]));
		 
		List pairs3= Loops.listPairsCollection(R, false, true);
		assertEquals(2, pairs3.size());
		assertTrue(containsAnyPair(pairs3, S[0], S[1]));
		assertTrue(!containsAnyPair(pairs3, S[0], S[0]));
		assertTrue(!containsAnyPair(pairs3, S[1], S[1]));
		 
		List pairs4= Loops.listPairsCollection(R, true, true);
		assertEquals(4, pairs4.size());
		assertTrue(containsAnyPair(pairs4, S[0], S[1]));
		assertTrue(containsAnyPair(pairs4, S[0], S[0]));
		assertTrue(containsAnyPair(pairs4, S[1], S[1]));
	}
	
	public void testListPairsList() {
		String[] S= { "A", "B" };
		Set R= new HashSet();
		R.addAll(Arrays.asList(S));

		List pairs1= Loops.listPairsList(Arrays.asList(S), false, false);
		assertEquals(1, pairs1.size());
		assertTrue(containsAnyPair(pairs1, S[0], S[1]));
		 
		List pairs2= Loops.listPairsList(Arrays.asList(S), true, false);
		assertEquals(3, pairs2.size());
		assertTrue(containsAnyPair(pairs2, S[0], S[1]));
		assertTrue(containsAnyPair(pairs2, S[0], S[0]));
		assertTrue(containsAnyPair(pairs2, S[1], S[1]));
		 
		List pairs3= Loops.listPairsList(Arrays.asList(S), false, true);
		assertEquals(2, pairs3.size());
		assertTrue(containsAnyPair(pairs3, S[0], S[1]));
		assertTrue(!containsAnyPair(pairs3, S[0], S[0]));
		assertTrue(!containsAnyPair(pairs3, S[1], S[1]));
		 
		List pairs4= Loops.listPairsList(Arrays.asList(S), true, true);
		assertEquals(4, pairs4.size());
		assertTrue(containsAnyPair(pairs4, S[0], S[1]));
		assertTrue(containsAnyPair(pairs4, S[0], S[0]));
		assertTrue(containsAnyPair(pairs4, S[1], S[1]));
	}
	
	static boolean containsAnyPair(Collection col, Object a, Object b) {
		return Loops.containsPair(col, new Object[] { a, b })
		    || Loops.containsPair(col, new Object[] { b, a });
	}

}
