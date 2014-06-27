/*
 * Created on 12.04.2004
 *
 * $Header: /Users/wova/laufend/cvs/Utils/de/vahrson/loop/BlocksTest.java,v 1.1 2004/04/20 15:08:49 wova Exp $
 *  */
package de.vahrson.loop;
import java.util.*;

import junit.framework.TestCase;

/**
 * @author wova
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class BlocksTest extends TestCase{

	/**
	 * 
	 */
	public BlocksTest() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public void testMaxMin() {
		Object [] O= {new Double(126), new Double(126.8), new Double((byte)127), new Double(126.9)
		};
		Collection col= Arrays.asList(O);
		Object x= Loops.injectInto(col, new Blocks.Max(), new Double(Double.MIN_VALUE));
		assertTrue( x == O[2]);
		
		Object y= Loops.injectInto(col, new Blocks.Min(), new Double(Double.MAX_VALUE));
		assertTrue( y == O[0]);
		
		String [] S= { "B", "A",  "D", "C"
		};
		col= Arrays.asList(S);

		Object z= Loops.injectInto(col, new Blocks.Max(), "");
		assertTrue( z == S[2]);
				
		Object zz= Loops.injectInto(col, new Blocks.Min(), "ZZZZZ");
		assertTrue( zz == S[1]);
				
	}

}
