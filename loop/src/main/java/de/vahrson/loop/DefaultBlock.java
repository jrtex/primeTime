/*
 * Created on 24.03.2004
 *
 * $Header: /Users/wova/laufend/cvs/Utils/de/vahrson/loop/DefaultBlock.java,v 1.1 2004/04/20 15:08:49 wova Exp $
 *  */
package de.vahrson.loop;


/**
 * This class provides dummy implementations for all execute methods
 * @author wova
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class DefaultBlock implements Block {


	/* (non-Javadoc)
	 * @see de.vahrson.util.Closure#execute()
	 */
	public Object execute() throws Exception {
		return null;
	}

	/* (non-Javadoc)
	 * @see de.vahrson.util.Closure#execute(java.lang.Object)
	 */
	public Object execute(Object arg1) throws Exception {
		return null;
	}

	/* (non-Javadoc)
	 * @see de.vahrson.util.Closure#execute(java.lang.Object, java.lang.Object)
	 */
	public Object execute(Object arg1, Object arg2) throws Exception {
		return null;
	}

	/* (non-Javadoc)
	 * @see de.vahrson.loop.Block#test()
	 */
	public boolean test() throws Exception {
		return false;
	}

	/* (non-Javadoc)
	 * @see de.vahrson.loop.Block#test(java.lang.Object, java.lang.Object)
	 */
	public boolean test(Object arg1, Object arg2) throws Exception {
		return false;
	}

	/* (non-Javadoc)
	 * @see de.vahrson.loop.Block#test(java.lang.Object)
	 */
	public boolean test(Object arg1) throws Exception {
		return false;
	}

}
