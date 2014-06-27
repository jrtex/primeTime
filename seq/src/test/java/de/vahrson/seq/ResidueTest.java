/*
 * Created on 20.04.2004
 *
 * $Header: /Users/wova/laufend/cvs/Seq/de/vahrson/seq/ResidueTest.java,v 1.1 2004/04/20 15:11:18 wova Exp $
 *  */
package de.vahrson.seq;

import junit.framework.TestCase;

/**
 * @author wova
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ResidueTest extends TestCase {
	public void testIsResidue() {
		String r= "acgtumrwsykvhdbnacefghiklmnpqrstvwxy";
		String R= r.toUpperCase();
		for (int i=0; i<r.length(); i++) {
			assertTrue(String.valueOf(i),Residue.isResidue(r.charAt(i)));		
			assertTrue(String.valueOf(i),Residue.isResidue(R.charAt(i)));		
		}
		
		String noR=" *<0123456789ß\u08ff\u0101";
		for (int i=0; i<noR.length(); i++) {
			assertTrue(String.valueOf(i),!Residue.isResidue(noR.charAt(i)));
		}
	}
	
}
