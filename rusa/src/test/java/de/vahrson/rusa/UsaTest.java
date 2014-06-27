/*
 * Created on 19.02.2005 
 *
 * $Header: /Users/wova/laufend/cvs/Rusa/de/vahrson/rusa/UsaTest.java,v 1.1 2005/02/20 11:44:00 wova Exp $
 */
package de.vahrson.rusa;

import java.util.*;

import junit.framework.TestCase;

/**
 * @author wova
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class UsaTest extends TestCase {
    public void testParseUsa() {
        String ddb="embl";
        String df="embl";
        Usa u= Usa.parseUsa("HSMYCC", ddb, df);
        assertEquals("hsmycc", u.getId());
        assertEquals(ddb, u.getDb());
        assertEquals(df, u.getFormat());
        
        u= Usa.parseUsa("FASTA::HSMYCC", ddb, df);
        assertEquals("hsmycc", u.getId());
        assertEquals(ddb, u.getDb());
        assertEquals("fasta", u.getFormat());
        
        u= Usa.parseUsa("FASTA::GB:HSMYCC", ddb, df);
        assertEquals("hsmycc", u.getId());
        assertEquals("gb", u.getDb());
        assertEquals("fasta", u.getFormat());
    }
}
