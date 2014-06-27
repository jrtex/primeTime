/*
 * Created on 24.06.2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */

package de.vahrson.util;

import java.util.*;
/**
 * @author wova
 *
 * A range of integers. It is defined as [from..[to , i.e. from is included 
 * and to is excluded from the range. 
 * IntRanges are immutable.
 */
public class IntRange implements Comparable {
	private int fFrom, fTo;

	public static final IntRange EMPTY = new IntRange();

	public IntRange() {
		this(0, 0); // empty
	}

	public IntRange(int from, int to) {
		fFrom = Math.min(from, to);
		fTo = Math.max(from, to);
	}

	public boolean equals(Object other) {
		boolean answer = other instanceof IntRange;
		if (answer) {
			answer = compareTo(other) == 0;
		}
		return answer;
	}

	public int hashCode() {
		return ((fFrom & 0xFFFF) << 16) + (fTo & 0xFFFF);
	}

	/* Natural ordering of IntRanges. IntRanges are ordered according to 
	 * from. 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(Object o) {
		IntRange other = (IntRange) o;
		int answer = this.fFrom - other.fFrom;
		if (answer == 0) {
			answer = this.fTo - other.fTo;
		}
		return answer;
	}

	public String toString() {
		return "[" + fFrom + "..[" + fTo;
	}

	public int width() {
		return fTo - fFrom;
	}

	public boolean isEmpty() {
		return width() < 1;
	}

	/**
	 * Answer whether this IntRange intersects with another IntRange r
	 * Creation date: (20.08.2002 10:19:59)
	 * @return boolean
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
	public boolean intersects(IntRange r) {
		int a0 = fFrom;
		int az = fTo;
		int r0 = r.fFrom;
		int rz = r.fTo;

		boolean answer = a0 < rz && rz <= az;
		answer = answer || a0 <= r0 && r0 < az;
		answer = answer || r0 <= a0 && az <= rz;

		return answer;
	}

	/**
	 * Answer the intersection between this range and another one.
	 * If no intersection exists, an empty intersection is returned.
	 * @author wova
	 *
	 * To change the template for this generated type comment go to
	 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
	 */
	public IntRange intersection(IntRange other) {
		IntRange answer;
		if (intersects(other)) {
			int from, to; // of intersection
			from = Math.max(this.fFrom, other.fFrom);
			to = Math.min(this.fTo, other.fTo);
			answer = new IntRange(from, to);
		} else {
			answer = EMPTY;
		}
		return answer;
	}
	/**
	 * Answer all intersection between this Range and a Collection of Ranges
	 * Filters out empty intersections
	 * @param ranges
	 * @return
	 */
	public List intersections(Collection ranges) {
		ArrayList answer = new ArrayList();
		Iterator i = ranges.iterator();
		while (i.hasNext()) {
			IntRange ir = (IntRange) i.next();
			IntRange inter = intersection(ir);
			if (!inter.isEmpty()) {
				answer.add(inter);
			}
		}
		Collections.sort(answer);
		return answer;
	}

	/** 
	 * Answer a list containing all intersections between between the two
	 * collections of IntRanges. Filters out empty intersections,
	 * @param ranges1
	 * @param ranges2
	 * @return
	 */
	public static List allIntersections(Collection ranges1, Collection ranges2) {
		ArrayList answer = new ArrayList();
		IntRange ir1;
		Iterator i1 = ranges1.iterator();
		while (i1.hasNext()) {
			ir1 = (IntRange) i1.next();
			answer.addAll(ir1.intersections(ranges2));
		}
		Collections.sort(answer);
		return answer;
	}

	/**
	 * Answer a range that covers all of this range and the other range.
	 * Note that if the two ranges did not intersect, the resulting union 
	 * will contain elements that have not been elements of either range.
	 * @param other
	 * @return
	 */
	public IntRange union(IntRange other) {
		int from, to;
		from = Math.min(this.fFrom, other.fFrom);
		to = Math.max(this.fTo, other.fTo);
		return new IntRange(from, to);
	}

	/**
	 * Answer the difference: this / other. Answer is a list of at most
	 * two IntRanges. Empty Ranges are filtered out. 
	 * @param other
	 * @return
	 */
	public List difference(IntRange other) {
		List answer= new ArrayList();
		if (intersects(other)) {
			int from , to;
			if (fFrom < other.fFrom) {
				answer.add( new IntRange(fFrom, other.fFrom));
			}
			if (fTo > other.fTo) {
				answer.add( new IntRange(other.fTo, fTo));
			}
		}
		return answer;
	}

	/**
	 * @return
	 */
	public int getFrom() {
		return fFrom;
	}

	/**
	 * @return
	 */
	public int getTo() {
		return fTo;
	}

}
