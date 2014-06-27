/*
 * COPYRIGHT 2003 Wolfgang Vahrson (mail@vahrson.de). ALL RIGHTS RESERVED.
 * 
 * Created on 23.07.2003
 *
 * $Header: /Users/wova/laufend/cvs/Utils/de/vahrson/util/PartitioningTest.java,v 1.1 2003/07/30 13:17:41 wova Exp $
 */
package de.vahrson.util;

import junit.framework.TestCase;

/**
 * @author wova
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class PartitioningTest extends TestCase {

	/**
	 * Constructor for PartitioningTest.
	 * @param arg0
	 */
	public PartitioningTest(String arg0) {
		super(arg0);
	}
	
	public void testPartitioningLengths() {
		checkLengths(0, 10, 3, new int[] {4,3,3});
		checkLengths(0, 0, 3, new int[] {});
		checkLengths(1, 10, 3, new int[] {3,3,3});
		checkLengths(1, 11, 9, new int[] {10});
	}

	
	private void checkLengths(int k, int q, int l, int[] r) {
		int[] x= Partitioning.partitionLengths(k,q,l);
		int sum= 0;
		for (int i= 0; i<x.length; i++) {
			sum+= x[i];
		}
		assertEquals(q-k, sum);

		for (int i= 0; i<r.length; i++) {
			assertEquals(r[i], x[i]);
		}
	}
	
	public void testPartitioningPos() {
		checkPos(0, 10, 3, new int[] {0,4,7,10});
		checkPos(0, 0, 3, new int[] {0,0});
		checkPos(1, 10, 3, new int[] {1,4,7,10});
		checkPos(1, 11, 9, new int[] {1,11});
	}
	
	private void checkPos(int k, int q, int l, int[] r) {
		int[] x= Partitioning.partitionPositions(k,q,l);

		for (int i= 0; i<r.length; i++) {
			assertEquals(r[i], x[i]);
		}
	}



}
