/*
 * Created on 15.02.2005 
 *
 * $Header: /Users/wova/laufend/cvs/Seq/de/vahrson/seq/RegionTest.java,v 1.1 2005/02/15 22:53:11 wova Exp $
 */
package de.vahrson.seq;

import java.util.*;

import junit.framework.TestCase;

/**
 * @author wova
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class RegionTest extends TestCase {
    public void testAsLocation() {

        Region r = new Region(20, "<", 30,"", false);
        String loc = r.asLocation();
        assertEquals("complement(<20..30)", loc);

        Position p1 = new Position(20);
        r = new Region(p1, true);
        loc = r.asLocation();
        assertEquals("20", loc);
    }
    
    public void testAsFeatrureLocation() {
        List R= new ArrayList();
        R.add(new Region(20, "<", 30,"", false));
        R.add(new Region(40, "", 50,">", false));
        R.add(new Region(60,"",70,"",false));
        String loc= Region.asFeatureLocation(R);
        assertEquals("join(complement(<20..30),complement(40..>50),complement(60..70))", loc);
        
        R.clear();
        R.add(new Region(60,"",70,"",true));
        loc= Region.asFeatureLocation(R);
        assertEquals("60..70", loc);
       
    }
}