/*
 * Created on 03.03.2005 
 *
 * $Header: /Users/wova/laufend/cvs/Seq/de/vahrson/emboss/InfoSeqTest.java,v 1.1 2005/03/18 23:27:13 wova Exp $
 */
package de.vahrson.emboss;

import java.io.StringReader;
import java.util.*;

import junit.framework.TestCase;

/**
 * @author wova
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class InfoSeqTest extends TestCase {
	public void testParse() throws Exception {
		InfoSeq tested= new InfoSeq();
		String ref= "tembl-id:PAAMIR PAAMIR X13776 N 2167 66.54 Pseudomonas aeruginosa amiC and amiR gene for aliphatic amidase regulation\n"
				+ "tembl-id:PAAMIR2 PAAMIR2 X13777 N 2168 67.54 Aeruginosa amiC and amiR gene for aliphatic amidase regulation";
		List infos= tested.parse(new StringReader(ref));
		InfoSeq.Info i1= (InfoSeq.Info) infos.get(0);
		assertEquals("tembl-id:PAAMIR", i1.getUsa());
		assertEquals("PAAMIR", i1.getName());
		assertEquals("X13776", i1.getAccession());
		assertEquals("N", i1.getType());
		assertEquals(2167, i1.getLength());
		assertEquals(66.54, i1.getGC(), 0.1);
		assertEquals("Pseudomonas aeruginosa amiC and amiR gene for aliphatic amidase regulation", i1.getDescription());

		i1= (InfoSeq.Info) infos.get(1);
		assertEquals("tembl-id:PAAMIR2", i1.getUsa());
		assertEquals("PAAMIR2", i1.getName());
		assertEquals("X13777", i1.getAccession());
		assertEquals("N", i1.getType());
		assertEquals(2168, i1.getLength());
		assertEquals(67.54, i1.getGC(), 0.1);
		assertEquals("Aeruginosa amiC and amiR gene for aliphatic amidase regulation", i1.getDescription());
	}
}