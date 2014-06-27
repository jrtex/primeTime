/*
 * Created on 24.03.2004
 *
 * $Header: /Users/wova/laufend/cvs/Utils/de/vahrson/loop/Blocks.java,v 1.1 2004/04/20 15:08:49 wova Exp $
 *  */
package de.vahrson.loop;

/**
 * A Collection of general purpose Blocks
 * @author wova
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class Blocks {

	/**
	 * A Max Block to be used with Loops.injectInto()
	 * Assumes that arguments are Comparable.
	 * @author wova
	 *
	 * To change the template for this generated type comment go to
	 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
	 */
	public static class Max extends DefaultBlock {
		public Object execute(Object subTotal, Object item) {
			Object answer;
			Comparable c1= (Comparable)subTotal;
			if (c1.compareTo(item) < 0) {
				answer= item;
			}
			else {
				answer= subTotal; 
			}
			return answer;					
		}
	}
	
	/**
	 * A Max Block to be used with Loops.injectInto()
	 * Assumes that arguments are Comparable.
	 * @author wova
	 *
	 * To change the template for this generated type comment go to
	 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
	 */
	public static class Min extends DefaultBlock {
		public Object execute(Object subTotal, Object item) {
			Object answer;
			Comparable c1= (Comparable)subTotal;
			if (c1.compareTo(item) > 0) {
				answer= item;
			}
			else {
				answer= subTotal; 
			}
			return answer;					
		}
	}

/**
 * This block finds the max value in a collection (assuming double values)
 * @param source
 * @return
 */
public static class MaxDouble extends DefaultBlock {
	private double fMax= Double.MIN_VALUE;

	public Object execute(Object arg) {
		fMax= Math.max(fMax, ((Number) arg).doubleValue());
		return null;
	}

	/**
	 * @return
	 */
	public double getMax() {
		return fMax;
	}
}

/**
 * This block finds the min value in a collection (assuming double values)
 * @param source
 * @return
 */
public static class MinDouble extends DefaultBlock {
	private double fMin= Double.MAX_VALUE;

	public Object execute(Object arg) {
		fMin= Math.min(fMin, ((Number) arg).doubleValue());
		return null;
	}
	/**
	 * @return
	 */
	public double getMin() {
		return fMin;
	}
}

}
