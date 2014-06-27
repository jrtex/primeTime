/*
 * Created on 01.03.2005 
 *
 * $Header: /Users/wova/laufend/cvs/pt/de/vahrson/pt/OrfHitRemapperTest.java,v 1.3 2005/04/26 17:03:41 wova Exp $
 */
package de.vahrson.pt;

import java.util.*;

import de.vahrson.pt.om.Hit;
import de.vahrson.pt.om.Orf;

import junit.framework.TestCase;

/**
 * @author wova
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class OrfHitRemapperTest extends TestCase {
    public void testRemapOrfComplement() throws Exception {
        Orf orf = new Orf(); // incomplete Orf, testing only!
        String usa = "embl::embl:hsmycc";
        orf.setSourceUsa(usa);
        orf.setLocation("join(complement(801..850),complement(501..700))");
        Hit s1 = createHit(1, true);
        OrfHitRemapper tested = new OrfHitRemapper(1);
        Hit h1 = tested.remapOrf(s1, orf);
        assertEquals(usa, h1.getUsa());
        assertEquals(850, h1.getPosition());
        assertEquals(false, h1.getStrand());

        s1 = createHit(50, false);
        tested = new OrfHitRemapper(1);
        h1 = tested.remapOrf(s1, orf);
        assertEquals(usa, h1.getUsa());
        assertEquals(801, h1.getPosition());
        assertEquals(true, h1.getStrand());
 
        s1 = createHit(51, false);
        tested = new OrfHitRemapper(1);
        h1 = tested.remapOrf(s1, orf);
        assertEquals(usa, h1.getUsa());
        assertEquals(700, h1.getPosition());
        assertEquals(true, h1.getStrand());
 
        s1 = createHit(250, false);
        tested = new OrfHitRemapper(1);
        h1 = tested.remapOrf(s1, orf);
        assertEquals(usa, h1.getUsa());
        assertEquals(501, h1.getPosition());
        assertEquals(true, h1.getStrand());
    }
    
    public void testRemapOrfDirect() throws Exception {
        Orf orf = new Orf(); // incomplete Orf, testing only!
        String usa = "embl::embl:hsmycc";
        orf.setSourceUsa(usa);
        orf.setLocation("join(501..700,801..850)");
        Hit s1 = createHit(1, true);
        OrfHitRemapper tested = new OrfHitRemapper(1);
        Hit h1 = tested.remapOrf(s1, orf);
        assertEquals(usa, h1.getUsa());
        assertEquals(501, h1.getPosition());
        assertEquals(true, h1.getStrand());

        s1 = createHit(200, false);
        tested = new OrfHitRemapper(1);
        h1 = tested.remapOrf(s1, orf);
        assertEquals(usa, h1.getUsa());
        assertEquals(700, h1.getPosition());
        assertEquals(false, h1.getStrand());

        s1 = createHit(201, true);
        tested = new OrfHitRemapper(1);
        h1 = tested.remapOrf(s1, orf);
        assertEquals(usa, h1.getUsa());
        assertEquals(801, h1.getPosition());
        assertEquals(true, h1.getStrand());

        s1 = createHit(250, true);
        tested = new OrfHitRemapper(1);
        h1 = tested.remapOrf(s1, orf);
        assertEquals(usa, h1.getUsa());
        assertEquals(850, h1.getPosition());
        assertEquals(true, h1.getStrand());

    }

    // incomplete Hit, testing only!
    private Hit createHit(int pos, boolean directStrand) throws Exception {
        Hit answer = new Hit();
        answer.setPrimerId(1);
        answer.setPosition(pos);
        answer.setLength(20);
        answer.setStrand(directStrand);
        answer.setMismatches(0);
        answer.setCreator("orf-hit-remapper-test");
        return answer;
    }

}
