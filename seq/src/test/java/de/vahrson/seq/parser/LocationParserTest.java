/*
 * Created on 11.02.2005 
 *
 * $Header: /Users/wova/laufend/cvs/Seq/de/vahrson/seq/parser/LocationParserTest.java,v 1.2 2005/02/17 08:25:16 wova Exp $
 */
package de.vahrson.seq.parser;

import java.util.*;

import de.vahrson.seq.Region;

import junit.framework.TestCase;

/**
 * @author wova
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class LocationParserTest extends TestCase {
    public void testOneRegionParser() throws Exception {
        String ref;

        ref = "11810..12484";
        Region r = oneRegion(ref);
        assertTrue(r.isDirectStrand());
        assertTrue(r.getLeftPosition().isDetermined());
        assertEquals(11810, r.getLeftPosition().getPosition());
        assertTrue(r.getRightPosition().isDetermined());
        assertEquals(12484, r.getRightPosition().getPosition());

        ref = "complement(12865..13047)";
        r = oneRegion(ref);
        assertTrue(r.isComplementaryStrand());
        assertTrue(r.getLeftPosition().isDetermined());
        assertEquals(12865, r.getLeftPosition().getPosition());
        assertTrue(r.getRightPosition().isDetermined());
        assertEquals(13047, r.getRightPosition().getPosition());

        ref = "21956";
        r = oneRegion(ref);
        assertTrue(r.isDirectStrand());
        assertTrue(r.getLeftPosition().isDetermined());
        assertEquals(21956, r.getLeftPosition().getPosition());
        assertTrue(r.getRightPosition().isDetermined());
        assertEquals(21956, r.getRightPosition().getPosition());

        ref = "<24323..24511";
        r = oneRegion(ref);
        assertTrue(r.isDirectStrand());
        assertTrue(r.getLeftPosition().isLessOrEqual());
        assertEquals(24323, r.getLeftPosition().getPosition());
        assertTrue(r.getRightPosition().isDetermined());
        assertEquals(24511, r.getRightPosition().getPosition());

        ref = "complement(34756..>35782)";
        r = oneRegion(ref);
        assertTrue(r.isComplementaryStrand());
        assertTrue(r.getLeftPosition().isDetermined());
        assertEquals(34756, r.getLeftPosition().getPosition());
        assertTrue(r.getRightPosition().isGreaterOrEqual());
        assertEquals(35782, r.getRightPosition().getPosition());

        ref = "complement(<41259..>41570)";
        r = oneRegion(ref);
        assertTrue(r.isComplementaryStrand());
        assertTrue(r.getLeftPosition().isLessOrEqual());
        assertEquals(41259, r.getLeftPosition().getPosition());
        assertTrue(r.getRightPosition().isGreaterOrEqual());
        assertEquals(41570, r.getRightPosition().getPosition());

        ref = "<62929..>62930";
        r = oneRegion(ref);
        assertTrue(r.isDirectStrand());
        assertTrue(r.getLeftPosition().isLessOrEqual());
        assertEquals(62929, r.getLeftPosition().getPosition());
        assertTrue(r.getRightPosition().isGreaterOrEqual());
        assertEquals(62930, r.getRightPosition().getPosition());

        ref = "142420..>142869";
        r = oneRegion(ref);
        assertTrue(r.isDirectStrand());
        assertTrue(r.getLeftPosition().isDetermined());
        assertEquals(142420, r.getLeftPosition().getPosition());
        assertTrue(r.getRightPosition().isGreaterOrEqual());
        assertEquals(142869, r.getRightPosition().getPosition());
    }

    public void testMultiRegionsParser() throws Exception {
        String ref;
        List regions;
        Region r;
        ref = "complement(join(48245..49473,49577..49778))";
        regions= multiRegions(ref, 2);
        
        r= (Region)regions.get(0);
        assertTrue(r.isComplementaryStrand());
        assertTrue(r.getLeftPosition().isDetermined());
        assertEquals(49577, r.getLeftPosition().getPosition());
        assertTrue(r.getRightPosition().isDetermined());
        assertEquals(49778, r.getRightPosition().getPosition());
        r= (Region)regions.get(1);
        assertTrue(r.isComplementaryStrand());
        assertTrue(r.getLeftPosition().isDetermined());
        assertEquals(48245, r.getLeftPosition().getPosition());
        assertTrue(r.getRightPosition().isDetermined());
        assertEquals(49473, r.getRightPosition().getPosition());
        
        ref = "join(<27110..27195,27279..>27504)";
        regions= multiRegions(ref, 2);
        
        r= (Region)regions.get(0);
        assertTrue(r.isDirectStrand());
        assertTrue(r.getLeftPosition().isLessOrEqual());
        assertEquals(27110, r.getLeftPosition().getPosition());
        assertTrue(r.getRightPosition().isDetermined());
        assertEquals(27195, r.getRightPosition().getPosition());
        r= (Region)regions.get(1);
        assertTrue(r.isDirectStrand());
        assertTrue(r.getLeftPosition().isDetermined());
        assertEquals(27279, r.getLeftPosition().getPosition());
        assertTrue(r.getRightPosition().isGreaterOrEqual());
        assertEquals(27504, r.getRightPosition().getPosition());
        
        ref = "join(160609..160788,160865..<161065,>161149..161295)";
        regions= multiRegions(ref, 3);
        
        r= (Region)regions.get(0);
        assertTrue(r.isDirectStrand());
        assertTrue(r.getLeftPosition().isDetermined());
        assertEquals(160609, r.getLeftPosition().getPosition());
        assertTrue(r.getRightPosition().isDetermined());
        assertEquals(160788, r.getRightPosition().getPosition());
        
        r= (Region)regions.get(1);
        assertTrue(r.isDirectStrand());
        assertTrue(r.getLeftPosition().isDetermined());
        assertEquals(160865, r.getLeftPosition().getPosition());
        assertTrue(r.getRightPosition().isLessOrEqual());
        assertEquals(161065, r.getRightPosition().getPosition());
        
        r= (Region)regions.get(2);
        assertTrue(r.isDirectStrand());
        assertTrue(r.getLeftPosition().isGreaterOrEqual());
        assertEquals(161149, r.getLeftPosition().getPosition());
        assertTrue(r.getRightPosition().isDetermined());
        assertEquals(161295, r.getRightPosition().getPosition());
        
        ref = "join(complement(126316..126810),complement(125871..126161))";
        regions= multiRegions(ref, 2);
        
        r= (Region)regions.get(0);
        assertTrue(r.isComplementaryStrand());
        assertTrue(r.getLeftPosition().isDetermined());
        assertEquals(126316, r.getLeftPosition().getPosition());
        assertTrue(r.getRightPosition().isDetermined());
        assertEquals(126810, r.getRightPosition().getPosition());

        r= (Region)regions.get(1);
        assertTrue(r.isComplementaryStrand());
        assertTrue(r.getLeftPosition().isDetermined());
        assertEquals(125871, r.getLeftPosition().getPosition());
        assertTrue(r.getRightPosition().isDetermined());
        assertEquals(126161, r.getRightPosition().getPosition());
    }

    /**
     * @param ref
     * @return
     * @throws Exception
     */
    private Region oneRegion(String ref) throws Exception {
        LocationParser lp;
        List regions;
        lp = new LocationParser(ref);
        regions = lp.parse();
        assertEquals(1, regions.size());
        Region r = (Region) regions.get(0);
        return r;
    }

    private List multiRegions(String ref, int n) throws Exception {
        LocationParser lp;
        List regions;
        lp = new LocationParser(ref);
        regions = lp.parse();
        assertEquals(n, regions.size());
        return regions;
    }
}