/*
 * Created on 01.03.2005 
 *
 * $Header: /Users/wova/laufend/cvs/pt/de/vahrson/pt/LocationRemapperTest.java,v 1.1 2005/03/01 14:18:41 wova Exp $
 */
package de.vahrson.pt;

import java.util.*;


import de.vahrson.seq.Region;
import de.vahrson.seq.parser.LocationParser;

import junit.framework.TestCase;

/**
 * @author wova
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class LocationRemapperTest extends TestCase {
	public void testMapping() throws Exception{
		LocationParser lp= new LocationParser("join(101..200,501..700,801..850)");
		List regions= lp.parse();
		LocationRemapper lr= new LocationRemapper(regions);
		assertEquals( 101, lr.mapPosition(1,true));
		assertEquals( 200, lr.mapPosition(100,true));
		assertEquals( 501, lr.mapPosition(101,true));
		assertEquals( 700, lr.mapPosition(300,true));
		assertEquals( 801, lr.mapPosition(301,true));
		assertEquals( 850, lr.mapPosition(350,true));		
	}
	
	public void testMappingComplement() throws Exception{
		LocationParser lp= new LocationParser("join(complement(801..850),complement(501..700),complement(101..200))");
		List regions= lp.parse();
		LocationRemapper lr= new LocationRemapper(regions);
		assertEquals( 850, lr.mapPosition(1,false));
		assertEquals( 801, lr.mapPosition(50,false));
		assertEquals( 700, lr.mapPosition(51,false));
		assertEquals( 501, lr.mapPosition(250,false));
		assertEquals( 200, lr.mapPosition(251,false));
		assertEquals( 101, lr.mapPosition(350,false));
	}
	
	public void testFindRegion() throws Exception{
		LocationParser lp= new LocationParser("join(101..200,501..700,801..850)");
		List regions= lp.parse();
		LocationRemapper lr= new LocationRemapper(regions);
		Region r= lr.findRegion(1);
		assertEquals(101, r.getLeftPosition().getPosition());
		r= lr.findRegion(101);
		assertEquals(501, r.getLeftPosition().getPosition());
		r= lr.findRegion(301);
		assertEquals(801, r.getLeftPosition().getPosition());
	}
}
