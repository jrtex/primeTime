/*
 * Created on 10.04.2004
 *
 * $Header: /Users/wova/laufend/cvs/Utils/de/vahrson/loop/Loops.java,v 1.1 2004/04/20 15:08:49 wova Exp $
 *  */
package de.vahrson.loop;

import java.util.*;

import de.vahrson.util.CollectionUtils;
import de.vahrson.util.ErrorHandler;

/**
 * @author wova
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class Loops {

	static public class InvocationError extends Error {
		InvocationError(Throwable e) {
			super(e);
		}
	}

	private static ErrorHandler gErrorHandler;

	/**
	 * @return
	 */
	public static ErrorHandler getErrorHandler() {
		return gErrorHandler;
	}

	/**
	 * @param handler
	 */
	public static void setErrorHandler(ErrorHandler handler) {
		gErrorHandler= handler;
	}

	/**
	 * 
	 */
	private Loops() {}

	/**
	 * Perform Block on all elements
	 * Requires a Block implementing: Object execute(Object arg)
	 * ST: do:
	 * @param col
	 * @param closure
	 * @throws Exception
	 */
	public static void forEach(final Collection col, final Block block1Arg) {
		Block block= new DefaultBlock() {
			public Object execute() throws Exception {
				forEachE(col, block1Arg);
				return null;
			}
		};
		invokeAndHandleExceptions(block);
	}

	public static void forEachE(Collection col, Block block) throws Exception {
		for (Iterator i= col.iterator(); i.hasNext();) {
			block.execute(i.next());
		}
	}

	/**
	 * Answer a new Collection containing all elements from col
	 * for which the block.execute(arg) returns true.
	 * Requires a Block that answers to: boolean test(Object arg)
	 * ST: select
	 * @param col
	 * @return
	 */
	public static Collection select(final Collection col, final Block blockTest1arg) {
		Block block= new DefaultBlock() {
			public Object execute() throws Exception {
				return selectE(col, blockTest1arg);
			}
		};
		return (Collection) invokeAndHandleExceptions(block);
	}

	public static Collection selectE(Collection col, Block block) throws Exception {
		return choose(col, block, false);
	}

	/**
	 * Answer a new Collection containing all elements from col
	 * for which the block.execute(arg) returns false.
	 * Requires a Block that answers to: boolean test(Object arg)
	 * ST: reject
	 * @param col
	 * @return
	 */
	public static Collection reject(final Collection col, final Block blockTest1arg) {
		Block block= new DefaultBlock() {
			public Object execute() throws Exception {
				return rejectE(col, blockTest1arg);
			}
		};
		return (Collection) invokeAndHandleExceptions(block);
	}

	public static Collection rejectE(Collection col, Block block) throws Exception {
		return choose(col, block, true);
	}

	private static Collection choose(Collection col, Block block, boolean invert) throws Exception {
		Collection answer;
		answer= CollectionUtils.createCollection(col);

		for (Iterator i= col.iterator(); i.hasNext();) {
			Object item= i.next();
			Object value= block.execute(item);
			if (invert ^ block.test(item)) {
				answer.add(item);
			}
		}
		return answer;
	}

	private static boolean isTrue(Object value) {
		return value != null && value instanceof Boolean && ((Boolean) value).booleanValue();
	}

	public static Object injectIntoE(Collection col, Block block2args, Object startVaue) throws Exception {
		Object subTotal= startVaue;
		for (Iterator i= col.iterator(); i.hasNext();) {
			subTotal= block2args.execute(subTotal, i.next());
		}
		return subTotal;
	}

	/**
	 * Feeds result of previous iteration and the current element into block.
	 * Requires a Block implementing: Object execute( Object prevResult, Object currentElement) 
	 * @param col
	 * @param block2args
	 * @param startVaue
	 * @return
	 * @throws Exception
	 */
	public static Object injectInto(final Collection col, final Block block2args, final Object startVaue) {
		Block block= new DefaultBlock() {
			public Object execute() throws Exception {
				return injectIntoE(col, block2args, startVaue);
			}
		};
		return invokeAndHandleExceptions(block);
	}

	/**
	 * Answer Collection with the same number of elements as col. Each element
	 * in answer is the result of applying block to element in col.
	 * Requires a Block implementing: Object execute(Object arg)
	 * @param col
	 * @param block1arg
	 * @return
	 */
	public static Collection collect(final Collection col, final Block block1arg) {
		Block block= new DefaultBlock() {
			public Object execute() throws Exception {
				return collectE(col, block1arg);
			}
		};
		return (Collection) invokeAndHandleExceptions(block);
	}

	public static Collection collectE(Collection col, Block block1arg) throws Exception {
		Collection answer= CollectionUtils.createCollection(col);
		for (Iterator i= col.iterator(); i.hasNext();) {
			answer.add(block1arg.execute(i.next()));
		}
		return answer;
	}

	private static Object invokeAndHandleExceptions(Block block) {
		Object answer= null;
		try {
			answer= block.execute();
		}
		catch (Exception e) {
			if (gErrorHandler != null) {
				gErrorHandler.handleError(e);
			}
			else {
				throw new InvocationError(e);
			}
		}
		return answer;
	}

	public static void uniquePairsE(Collection col, Block block2args) throws Exception {
		if (col instanceof List) { // use more effective implementation
			uniquePairsList(col, block2args);
		}
		else {
			uniquePairsCollection(col, block2args);
		}
	}

	private static void uniquePairsCollection(Collection col, Block block2args) throws Exception {
		// this implementation assumes: Iterators return elements in constant order 
		for (Iterator upper= col.iterator(); upper.hasNext();) {
			Object upperElement= upper.next();
			Iterator lower= col.iterator();
			Object lowerElement= null;
			while (lower.hasNext() && lowerElement != upperElement) { // wind forward
				lowerElement= lower.next();
			}
			// do your trick
			while (lower.hasNext()) {
				lowerElement= lower.next();
				block2args.execute(upperElement, lowerElement);
			}
		}
	}

	private static void uniquePairsList(Collection col, Block block2args) throws Exception {
		List list= (List) col;
		final int n= list.size();
		for (int upper= 0; upper < n; upper++) {
			Object upperElement= list.get(upper);
			for (int lower= upper + 1; lower < n; lower++) {
				Object lowerElement= list.get(lower);
				block2args.execute(upperElement, lowerElement);
			}
		}
	}

	/**
	 * Answer a list of pairs as Object[2].
	 * Possible variations in forming pairs of elements from a Collection (A,B)
	 * Identical elements: (A,B) --> (A,A),(B,B),...
	 * commutative pairs: (A,B) -->  (A,B),(B,A),...
	 * For many practical applications, we don't want identical elements or commutative pairs
	 * (A,B,C) --> (A,B), (A,C), (B,C)
	 * @param col
	 * @return
	 */
	public static List listPairs(Collection col) {
		return listPairs(col, false, false);
	}

	public static List listPairs(Collection col, boolean wantsIdenticalElements, boolean wantsCommutativePairs) {
		List answer;
		if (col instanceof List) {
			answer= listPairsList((List) col, wantsIdenticalElements, wantsCommutativePairs);
		}
		else {
			answer= listPairsCollection(col, wantsIdenticalElements, wantsCommutativePairs);
		}

		return answer;
	}

	static List listPairsList(List list, boolean wantsIdenticalElements, boolean wantsCommutativePairs) {
		List answer= new ArrayList();
		final int n= list.size();
		for (int upper= 0; upper < n; upper++) {
			Object upperElement= list.get(upper);
			int lower;
			if (wantsCommutativePairs) {
				lower= 0;
			} else {
				if (wantsIdenticalElements) {
					lower= upper;
				}
				else {
					lower= upper + 1;
				}
			}
			
			for (; lower < n; lower++) {
				Object lowerElement= list.get(lower);
				if (wantsIdenticalElements || lowerElement != upperElement) {
					answer.add(new Object[] { upperElement, lowerElement });
				}
			}
		}
		return answer;
	}

	static List listPairsCollection(Collection col, boolean wantsIdenticalElements, boolean wantsCommutativePairs) {
		List answer= new ArrayList();

		for (Iterator upper= col.iterator(); upper.hasNext();) {
			Object upperElement= upper.next();
			Iterator lower= col.iterator();
			Object lowerElement= null;
			if (!wantsCommutativePairs) {
				while (lower.hasNext() && lowerElement != upperElement) { // wind forward
					lowerElement= lower.next();
				}
				if (wantsIdenticalElements || lowerElement != upperElement) {
					answer.add(new Object[] { upperElement, lowerElement });
				}
			}
			while (lower.hasNext()) {
				lowerElement= lower.next();
				if (wantsIdenticalElements || lowerElement != upperElement) {
					answer.add(new Object[] { upperElement, lowerElement });
				}
			}
		}
		return answer;
	}

	static boolean containsPair(Collection col, Object[] pair) {
		boolean found= false;
		for (Iterator i= col.iterator(); !found && i.hasNext();) {
			Object[] x= (Object[]) i.next();
			found= pair[0].equals(x[0]) && pair[1].equals(x[1]);
		}
		return found;
	}

}
