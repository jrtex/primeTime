/*
 * Created on 22.02.2005 
 *
 * $Header: /Users/wova/laufend/cvs/Rusa/de/vahrson/rusa/NCBIeFetchClientTest.java,v 1.1 2005/02/22 12:22:39 wova Exp $
 */
package de.vahrson.rusa;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Writer;

import junit.framework.TestCase;

/**
 * @author wova
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class NCBIeFetchClientTest extends TestCase {
    public void testFetch() throws Exception {
        Usa usa = new Usa("nucleotide", "gb", "hsmycc");
        File out = File.createTempFile("NCBIeFetchClientTest", "temp");
        Writer w = new FileWriter(out);
        NCBIeFetchClient client = new NCBIeFetchClient();
        client.fetch(usa, w);
        w.close();

        BufferedReader r = new BufferedReader(new FileReader(out));
        String line = r.readLine();
        assertEquals("LOCUS       HSMYCC                 10996 bp    DNA     linear   PRI 03-JUN-2002", line);
        String lastLine = null;
        while ((line = r.readLine()) != null) {
            if (line.length() > 0) {
                lastLine = line;
            }
        }
        assertEquals("//", lastLine);
        r.close();

        out.delete();
    }
}