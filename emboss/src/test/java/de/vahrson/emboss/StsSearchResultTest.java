/*
 * Created on 06.02.2004
 *
 * $Header: /Users/wova/laufend/cvs/Seq/de/vahrson/emboss/StsSearchResultTest.java,v 1.1 2004/04/20 15:11:18 wova Exp $
 *  */
package de.vahrson.emboss;

import junit.framework.TestCase;

/**
 * @author wova
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class StsSearchResultTest extends TestCase {

	/**
	 * 
	 */
	public StsSearchResultTest() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param arg0
	 */
	public StsSearchResultTest(String arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}
	
	public void testCreate() throws Exception{
		String l1="1a.M62321: 1a.AF-1 PrimerA matched at 6064";
		String l2="1a.M62321: (rev) 1a.AF-1 PrimerB matched at 6165";
		
		StsSearchResult r= StsSearchResult.createStsSearchResult(l1);
		assertEquals(r.getUsa(), "1a.M62321");
		assertEquals(r.getPrimerPairId(), "1a.AF-1");
		assertEquals(r.getPosition(), 6064);
		assertTrue(r.isFwd());
		assertTrue(r.isMatchA());
		
		r= StsSearchResult.createStsSearchResult(l2);
		assertEquals(r.getUsa(), "1a.M62321");
		assertEquals(r.getPrimerPairId(), "1a.AF-1");
		assertEquals(r.getPosition(), 6165);
		assertTrue(!r.isFwd());
		assertTrue(!r.isMatchA());

	}


}
