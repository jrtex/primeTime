/*
 * Created on 19.04.2004
 *
 * $Header: /Users/wova/laufend/cvs/UrlBlast/de/vahrson/blast/LocalBlastClient.java,v 1.4 2005/03/29 17:27:20 wova Exp $
 *  */
package de.vahrson.blast;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import de.vahrson.util.ExternalProgram;
import de.vahrson.util.IOUtils;
import de.vahrson.util.StopWatch;

/**
 * @author wova
 * 
 * A client to run a local blast search. It a similiar interface as
 * UrlBlastClient.
 */
public class LocalBlastClient {

    private Properties fQuery;

    private OutputStream fSink;

    private boolean fWantInfo = true;

    private boolean fWantQuery = true;

    private static final String OPTION_PREFIX = "-";

    private static Logger log = Logger.getLogger(LocalBlastClient.class);

    /**
     *  
     */
    public LocalBlastClient(Properties query, OutputStream resultSink) {
        super();
        fQuery = query;
        fSink = resultSink;
    }

    public void run() {
        try {
            log.info(StopWatch.timeStamp() + " local blast started.");
            StopWatch timeTaken = new StopWatch();
            if (isWantQuery()) {
                fQuery.list(new PrintStream(fSink));
            }

            if (isWantInfo()) {
            }
            blast();

            timeTaken.stop();
            log.debug("time taken: " + timeTaken.toString());
            log.debug(StopWatch.timeStamp() + " local blast done.");
        } catch (Exception e) {
            log.error("uncaught exception in run()\n", e);
        }
    }

    void blast() throws Exception {
        Properties params = filterProperties(OPTION_PREFIX, fQuery);
        ExternalProgram blastall = new ExternalProgram(System.getProperty("blastall.path", "blastall"));
        System.out.println(System.getProperty("blastall.path"));
        for (Enumeration i = params.keys(); i.hasMoreElements();) {
            String key = (String) i.nextElement();
            String value = params.getProperty(key);
            if (value == null) {
                value = "";
            }
            if (key.length()==3 && key.charAt(1) == key.charAt(2)) {
                key= "-" + key.toUpperCase().charAt(1);
            }
            blastall.setOption(key, value);
        }
        blastall.setCaptureOutput(true);
        log.info(blastall.toString());
        blastall.run();
        Writer out = new OutputStreamWriter(fSink);
        out.write(blastall.getOutput());
    }

    Properties filterProperties(String prefix, Properties query) throws UnsupportedEncodingException {
        Properties answer = new Properties();

        for (Enumeration i = query.propertyNames(); i.hasMoreElements();) {
            String key = (String) i.nextElement();
            if (key.startsWith(prefix)) {
                answer.setProperty(key, query.getProperty(key));
            }
        }

        return answer;
    }

    /**
     * @return
     */
    public boolean isWantInfo() {
        return fWantInfo;
    }

    /**
     * @param b
     */
    public void setWantInfo(boolean b) {
        fWantInfo = b;
    }

    /**
     * @return
     */
    public boolean isWantQuery() {
        return fWantQuery;
    }

    /**
     * @param b
     */
    public void setWantQuery(boolean b) {
        fWantQuery = b;
    }

    public static void main(String[] args) {
        try {
            Properties query = new Properties();
            query.load(System.in);
            LocalBlastClient blast = new LocalBlastClient(query, System.out);
            blast.run();
        } catch (Exception e) {
            System.err.println(e);
        }
    }
}