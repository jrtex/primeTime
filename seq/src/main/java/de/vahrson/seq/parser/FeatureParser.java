/*
 * Created on 10.02.2005 
 *
 * $Header: /Users/wova/laufend/cvs/Seq/de/vahrson/seq/parser/FeatureParser.java,v 1.3 2005/03/01 20:59:20 wova Exp $
 */
package de.vahrson.seq.parser;

import java.io.*;
import java.util.*;

import de.vahrson.seq.Feature;
import de.vahrson.util.StringUtil;

/**
 * @author wova
 * 
 * Parse an EMBL formatted sequence entry and return a list of Features
 */
public class FeatureParser {
    private BufferedReader fReader;

    // parser state:
    private String fFtKey;

    private List fQualifiers;

    private List fRegions;

    private String fLocation;

    private boolean fLocationOpen;

    private String fQualifier;

    private boolean fQualifierOpen;

    // what it's all about:
    private List fFeatures;

    public FeatureParser(Reader r) {
        fReader = new BufferedReader(r);
    }

    public List parse() throws Exception {
        fFeatures = new ArrayList();
        String line;
        while ((line = fReader.readLine()) != null) {
            if (line.startsWith("FT")||line.startsWith("  ")) { // EMBL/GenBank
                String key = line.substring(5, 21);
                key = key.trim();
                String value = line.substring(21);
                value = value.trim();
                if (key.length() > 0) { // new feature
                    addCompletedFeature();
                    resetFeature(key);
                    resetLocation(value);
                    parseCompletedLocation();
                } else { // continue previous feature
                    if (fLocationOpen) {
                        continueLocation(value);
                        parseCompletedLocation();
                    } else if (fQualifierOpen) { // continue from previous qualifier
                        continueQualifier(value);
                        addCompletedQualifier();
                    } else if (value.startsWith("/")) { //  first line of qualifier
                        resetQualifier(value);
                        addCompletedQualifier();
                    } else {
                        throw new Exception("Unexpected state for key: " + fFtKey + " value: " + value);
                    }
                }
            }
            else if (line.startsWith("SQ") || line.startsWith("ORIGIN")) {
            	break;
            }
        }

        addCompletedFeature();
        return fFeatures;
    }

    /**
     * @param value
     */
    private void continueLocation(String value) {
        fLocation = fLocation + value;
    }

    /**
     * @param value
     */
    private void resetLocation(String value) {
        fLocation = value;
    }

    /**
     * @param value
     */
    private void continueQualifier(String value) {
        fQualifier = fQualifier + "\n" + value;
        fQualifierOpen = !fQualifier.endsWith("\"");
    }

    /**
     * @param value
     */
    private void resetQualifier(String value) {
        fQualifier = value;
        fQualifierOpen = !(StringUtil.count(value, '"') == 2);
    }

    /**
     * @throws Exception
     */
    private void addCompletedQualifier() throws Exception {
        if (!fQualifierOpen) {
            if (fQualifier == null) {
                throw new Exception("Trying to add null Qualifier");
            }
            fQualifiers.add(fQualifier);
            fQualifier = null;
        }
    }

    /**
     * @param key
     */
    private void resetFeature(String key) {
        // reset
        fFtKey = key;
        fRegions = new ArrayList();
        fQualifiers = new ArrayList();
    }

    /**
     *  
     */
    private void addCompletedFeature() {
        if (fFtKey != null) { // save previous feature
            fFeatures.add(new Feature(fFtKey, fRegions, fQualifiers));
        }
    }

    /**
     * Parse a location String and place the resulting Regions into regions
     * 
     * @param regions
     * @param location
     */
    void parseCompletedLocation() throws Exception {
        fLocationOpen = fLocation.endsWith(",");
        if (!fLocationOpen) {
            LocationParser lp = new LocationParser(fLocation);
            fRegions.addAll(lp.parse());
        }
    }

	/**
	 * @param r
	 * @throws IOException
	 * @throws Exception
	 */
	public static void windForwardToFeatures(BufferedReader r) throws Exception {
		// wind reader forward to feature table:
		String line= null;
		boolean foundFeature= false;
		while (!foundFeature && (line=r.readLine())!=null ) {
			if (line.startsWith("FEATURES             Location/Qualifiers")) {
				foundFeature= true;
			}
			else if (line.startsWith("FH")) {
				line= r.readLine();
				if ( line== null) {
					throw new Exception("Bad feature table format, expected line after FT: " + line);
				}
				foundFeature= line.startsWith(("FH"));
			}
		}
		// ... next line will be first feature
	
	}
	
	/**
	 * Extract a list of features from input. 
	 * Assumes input is either EMBL or GenBank format
	 * @param input
	 * @param filterKey
	 * @return
	 * @throws Exception
	 */
	public static List extractFeatures(Reader input) throws Exception{
		BufferedReader r= new BufferedReader( input);		
		windForwardToFeatures(r);	
		FeatureParser fp= new FeatureParser(r);
		return fp.parse();
	}
}