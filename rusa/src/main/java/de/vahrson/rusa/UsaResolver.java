/*
 * Created on 19.02.2005 
 *
 * $Header: /Users/wova/laufend/cvs/Rusa/de/vahrson/rusa/UsaResolver.java,v 1.5 2005/02/25 11:02:34 wova Exp $
 */
package de.vahrson.rusa;

import java.util.*;
import java.io.*;

import org.apache.log4j.Logger;

import de.vahrson.util.IOUtils;
import de.vahrson.util.StringUtil;

/**
 * @author wova
 * 
 * Transparently resolve USAs as defined by EMBOSS:
 * http://emboss.sourceforge.net/docs/#Usa Download sequences if neccessary --
 * REQUIRES WsDbFetch Cache downloaded sequences at a well-known location
 * Provide read-only access to clients
 * 
 * Limitations: At present, handles dbname:entry_id only
 */
public class UsaResolver {
    public final static String PROPERTY_PATH = "rusa.path";
    public final static String PROPERTY_DATA_PATH = "rusa.data.path";
    public final static String PROPERTY_DEFAULT_DB = "rusa.default.db";
    public final static String PROPERTY_DEFAULT_FORMAT = "rusa.default.format";
    
    public final static String LOCAL_DB = "local";
    private File fBaseDir;
    private File fDataDir;
    private String fDefaultDB;
    private String fDefaultFormat;

    private static Logger log = Logger.getLogger(UsaResolver.class);

    public UsaResolver() {
        String path = System.getProperty(UsaResolver.PROPERTY_PATH);
        String dataPath = System.getProperty(PROPERTY_DATA_PATH, path + "/data");
        fDataDir = new File(dataPath);
        if (!fDataDir.exists()) {
            log.warn(fDataDir.getPath() + " does not exist.");
        }

        fDefaultDB = System.getProperty(PROPERTY_DEFAULT_DB, "embl");
        fDefaultFormat = System.getProperty(PROPERTY_DEFAULT_FORMAT, "embl");
    }

    /**
     * Answer local file for usa, null if usa could not be resolved
     * 
     * @param usa
     * @return
     */
    public File resolveUsa(String usa) throws Exception {
        Usa request = parseUsa(usa);

        File answer = null;
        answer = getFromCache(request);
        if (answer == null) {
            log.debug("Cache miss for " + request + ". Trying to convert.");
            convertFormat(request);
            answer = getFromCache(request);
        }
        if (answer == null) {
            log.debug("Cache miss for " + request + ". Trying to download.");
            download(request);
            answer = getFromCache(request);
        }
        return answer;
    }
    
    public void putLocal(String usaRepr, File src) throws IOException{
    	Usa orig= parseUsa(usaRepr);
    	Usa usa= new Usa(LOCAL_DB,orig.getFormat(),orig.getId()); // make sure we only allow local
    	File dest= location(usa);
    	if (dest.getParentFile()!=null) {
    		dest.getParentFile().mkdirs();
    	}
    	IOUtils.copyFile(src,dest);
    }

    /**
     * Answer a file from the cache or null if it is not there
     * 
     * @param request
     * @return
     */
    File getFromCache(Usa request) {
        File answer = location(request);
        if (!answer.exists()) {
            answer = null;
        }
        return answer;
    }

    /**
     * @param request
     * @return
     */
    File location(Usa request) {
    	String path;
    	if (request.getDb().equals(LOCAL_DB)) {
    		String user= System.getProperty("user.name");
            path = StringUtil.join(new String[] { request.getDb(), request.getFormat(), user, request.getId() }, "/");    		   		
    	}
    	else {
            path = StringUtil.join(new String[] { request.getDb(), request.getFormat(), request.getId() }, "/");    		
    	}
        File answer = new File(fDataDir, path);
        return answer;
    }

    void convertFormat(Usa request) {

    }

    void download(Usa request) throws Exception {
        Writer out = null;
        File dest = null;
        try {
            dest = location(request);
            File parent = dest.getParentFile();
            if (parent != null) {
                parent.mkdirs();
            }
            out = new FileWriter(dest);
            DbFetcher fetcher;
            if (request.getDb().equals("gb")) {
                fetcher = new NCBIeFetchClient();
            } else {
                fetcher = new WsDbFetchClient();
            }
            fetcher.fetch(request, out);
        } finally {
            if (out != null) {
                out.close();
            }
            if (dest.length() == 0) {
                dest.delete();
            }
        }
    }

    /**
     * Parse the format::db:entry representation
     * 
     * @param usa
     * @return
     */
    public Usa parseUsa(String usa) {
        return Usa.parseUsa(usa, fDefaultDB, fDefaultFormat);
    }

    /**
     * @return Returns the baseDir.
     */
    public File getBaseDir() {
        return fBaseDir;
    }

    /**
     * @return Returns the dataDir.
     */
    public File getDataDir() {
        return fDataDir;
    }
}