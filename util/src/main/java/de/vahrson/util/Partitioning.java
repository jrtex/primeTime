/*
 * COPYRIGHT 2003 Wolfgang Vahrson (mail@vahrson.de). ALL RIGHTS RESERVED.
 * 
 * Created on 23.07.2003
 *
 * $Header: /Users/wova/laufend/cvs/Utils/de/vahrson/util/Partitioning.java,v 1.1 2003/07/30 13:17:41 wova Exp $
 */
package de.vahrson.util;

/**
 * @author wova
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class Partitioning {
	/**
	 * Answer an array of indices that partition a range of [k..[q elements into 
	 * partitions of at least lenghth l. k and q themselves are part of the array (as first, last)
	 * If there are n partitions, answer will contain n+1 positions
	 * @param k start (inclusive)
	 * @param q stop (exclusive)
	 * @param l minimum length
	 * @return
	 */
	public static int [] partitionPositions(final int k, final int q, final int l){
		int [] lengths= partitionLengths(k,q,l);
		// generate the positions from lengths
		final int pn= lengths.length  > 0 ? lengths.length - 1 +2: 2;
		int [] answer= new int[pn];
		answer[0]= k;
		for (int i=1; i<pn-1; i++) {
			answer[i]= answer[i-1]+lengths[i-1];
		}
		answer[pn-1]= q;
		
		return answer;
	}
	
	/**
	 * Answer an array of lengths that partition a range of [k..[q elements into 
	 * partitions of at least lenghth l.  
	 * If there are n partitions, answer will contain n lengths
	 * @param k start (inclusive)
	 * @param q stop (exclusive)
	 * @param l minimum length
	 * @return
	 */
	public static int [] partitionLengths(final int k, final int q, final int l){
		final int n= (q-k)/l;
		int rest= (q-k)-(n*l);
		int [] lengths= new int[n];
		for (int i= 0; i<n; i++) {
			lengths[i]= l;
		}
		
		// distribute rest
		while (rest>0) {
			for (int i= 0; i<n && rest>0; i++) {
				lengths[i]++;
				rest--;
			}			
		}
		return  lengths;
	}

}
