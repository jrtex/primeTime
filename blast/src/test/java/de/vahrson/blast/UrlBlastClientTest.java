/*
 * Created on 19.04.2004
 *
 * $Header: /Users/wova/laufend/cvs/UrlBlast/de/vahrson/blast/UrlBlastClientTest.java,v 1.1 2004/04/20 15:09:52 wova Exp $
 *  */
package de.vahrson.blast;

import java.io.*;
import java.net.URL;
import java.util.*;

import junit.framework.TestCase;

/**
 * @author wova
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class UrlBlastClientTest extends TestCase {

	public void testExtractResultInfo() throws Exception{
		String fakeReply=
			"<!--QBlastInfoBegin\n"
				+ "	RID = 954517067-8610-1647\n"
				+ "	RTOE = 207\n"
				+ "QBlastInfoEnd\n"
				+ "-->\n";
		InputStream in= new ByteArrayInputStream(fakeReply.getBytes());
		UrlBlastClient tested= new UrlBlastClient(null,null);
		tested.extractResultInfo(in);
		assertEquals("954517067-8610-1647", tested.getRID());
		assertEquals(207, tested.getRTOE());
	}
	
	public void testFilterProperties() throws Exception{
		Properties p= new Properties();
		p.setProperty("test.a", "A");
		p.setProperty("test.b", "B");
		p.setProperty("notest.b", "X");
		
		UrlBlastClient tested= new UrlBlastClient(null,null);
		List r= tested.filterProperties("test.", p);
		assertEquals(2, r.size());
		assertTrue( r.contains("a=A") );
		assertTrue( r.contains("b=B") );
	}
	
	public void testCreateUrl() throws Exception{
		UrlBlastClient tested= new UrlBlastClient(null,null);
		List p= new ArrayList();
		p.add("a=A");
		p.add("b=B");
		URL url= tested.createUrl("test",p);
		assertEquals("http://www.ncbi.nlm.nih.gov/blast/Blast.cgi?a=A&b=B&CMD=test", url.toString());
	}

}
