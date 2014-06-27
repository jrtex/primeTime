/*
 * Created on 25.02.2005 
 *
 * $Header: /Users/wova/laufend/cvs/pt/de/vahrson/pt/OrfExtractorTest.java,v 1.1 2005/02/25 11:07:58 wova Exp $
 */
package de.vahrson.pt;

import java.util.*;

import de.vahrson.rusa.Usa;

import junit.framework.TestCase;

/**
 * @author wova
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class OrfExtractorTest extends TestCase {

	public void testCreateOrfUsa() {
		Usa answer= OrfExtractor.createOrfUsa("embl:hsmycc",3);
		assertEquals("local", answer.getDb());
		assertEquals("fasta", answer.getFormat());
		assertEquals("hsmycc-orfs/hsmycc-orf-3", answer.getId());
	}

}
