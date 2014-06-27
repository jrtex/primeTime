/*
 * Created on 22.02.2005 
 *
 * $Header: /Users/wova/laufend/cvs/Rusa/de/vahrson/rusa/NCBIeFetchClient.java,v 1.1 2005/02/22 12:22:39 wova Exp $
 */
package de.vahrson.rusa;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.Writer;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

/**
 * @author wova
 *
 * This class queries the NCBI eFetch service
 * http://eutils.ncbi.nlm.nih.gov/entrez/query/static/efetchseq_help.html
 */
public class NCBIeFetchClient implements DbFetcher{

    public void fetch(Usa usa, Writer out) throws Exception {
        URL service= createServiceUrl(usa);    
        BufferedReader r= new BufferedReader(new InputStreamReader(service.openStream()));
        BufferedWriter w= new BufferedWriter(out);
        String line= null;
        while((line= r.readLine())!=null){
            w.write(line);
            w.write('\n');
        }
        w.flush();
        r.close();
    }

    /**
     * @param usa
     */
    private URL createServiceUrl(Usa usa) throws MalformedURLException{
        String db= usa.getDb();
        if (db.equals("gb")) { // allow gb as synonym for nucleotide
            db="nucleotide";
        }
        URL service= new URL("http://eutils.ncbi.nlm.nih.gov/entrez/eutils/efetch.fcgi?db=" + db + "&id=" + usa.getId() + "&rettype=" + usa.getFormat() + "&tool=eFetchClient&email=mail@vahrson.de&retmode=text&complexity=1");
        return service;
    }
}
