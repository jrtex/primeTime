/*
 * Created on 20.04.2004
 *
 * $Header: /Users/wova/laufend/cvs/Seq/de/vahrson/seq/SequenceFormatterTest.java,v 1.1 2004/04/20 15:11:18 wova Exp $
 *  */
package de.vahrson.seq;

import junit.framework.TestCase;

/**
 * @author wova
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class SequenceFormatterTest extends TestCase {
	public void testFromatAsFastA() {
		Sequence s= new Sequence("My Description", "acgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgt"); // 132 bp
		String fs= new SequenceFormatter().formatAsFastA(s);
		String ref= ">My Description\n" 
		+ "acgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgt\n"
		+ "acgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgt\n"
		+ "acgtacgtacgt\n"; 
		assertEquals(ref, fs);
	}
}
