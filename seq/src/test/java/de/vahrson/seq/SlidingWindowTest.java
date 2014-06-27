/*
 * Created on 01.08.2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
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
public class SlidingWindowTest extends TestCase {
    public void test() {
        String seq = "123456789";
        String[] W = { "123", "234", "345", "456", "567", "678", "789" };
        SlidingWindow t = new SlidingWindow(seq, 3);
        assertEquals(3, t.getLength());
        for (int i = 0; i < W.length; i++) {
            assertTrue(t.hasNext());
            t.next();
            assertEquals(W[i], new String(t.getWindow()));
            assertEquals(i + 1, t.getPosition());
        }
        assertTrue(!t.hasNext());
    }

}
