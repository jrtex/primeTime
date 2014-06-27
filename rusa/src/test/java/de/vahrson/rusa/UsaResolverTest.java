/*
 * Created on 19.02.2005 
 *
 * $Header: /Users/wova/laufend/cvs/Rusa/de/vahrson/rusa/UsaResolverTest.java,v 1.2 2005/02/25 11:02:34 wova Exp $
 */
package de.vahrson.rusa;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.*;

import junit.framework.TestCase;

/**
 * @author wova
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class UsaResolverTest extends TestCase {

    public void testResolveUsa() throws Exception{
        System.setProperty(UsaResolver.PROPERTY_PATH,"C:\\rusa");
        File ref= new File ("C:\\rusa\\data\\embl\\embl\\hsmycc");
        assertTrue(!ref.exists());
        UsaResolver u= new UsaResolver();
        u.resolveUsa("embl:HSMYCC");
        assertTrue(ref.exists());
        File ref2= u.getFromCache(new Usa("embl","embl","hsmycc"));
        assertTrue(ref2.exists());
        ref.delete();      

        ref= new File ("C:\\rusa\\data\\embl\\fasta\\hsmycc");
        assertTrue(!ref.exists());
        u= new UsaResolver();
        u.resolveUsa("fasta::embl:HSMYCC");
        assertTrue(ref.exists());
        ref2= u.getFromCache(new Usa("embl","fasta","hsmycc"));
        assertTrue(ref2.exists());
        ref.delete();
   }
    
    public void testPutLocal() throws Exception {
        System.setProperty(UsaResolver.PROPERTY_PATH,"C:\\rusa");
        File ref= new File ("C:\\rusa\\data\\local\\fasta\\wova\\test1");
        assertTrue(!ref.exists());
        
        File src= File.createTempFile("testPutLocal", "xxx");
        BufferedWriter w= new BufferedWriter(new FileWriter(src));
        w.write(">test1\n");
        w.write("acgtacgtacgt\n");
        w.close();
        
        UsaResolver u= new UsaResolver();
        u.putLocal("fasta::test1", src);
        src.delete();
        
        assertTrue(ref.exists());
        BufferedReader r= new BufferedReader( new FileReader(ref));
        assertEquals(">test1", r.readLine());
        assertEquals("acgtacgtacgt", r.readLine());
        r.close();
        
        UsaResolver x= new UsaResolver();
        File path= x.resolveUsa("fasta::local:test1");
        assertEquals(ref, path);
        
        ref.delete();
    }
}
