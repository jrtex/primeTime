/*
 * Created on 10.02.2005 
 *
 * $Header: /Users/wova/laufend/cvs/Seq/de/vahrson/seq/Feature.java,v 1.1 2005/02/12 17:41:34 wova Exp $
 */
package de.vahrson.seq;

import java.util.*;

/**
 * @author wova
 *
 * A feature annotation on a sequence
 */
public class Feature {
    private String fKey;
    private List fRegions;
    private List fQualifiers;
    
    public Feature(String key, List regions, List qualifiers) {
        fKey= key;
        fRegions= regions;
        fQualifiers= qualifiers;
    }
    /**
     * @return Returns the key.
     */
    public String getKey() {
        return fKey;
    }
    /**
     * @return Returns the qualifiers.
     */
    public List getQualifiers() {
        return Collections.unmodifiableList(fQualifiers);
    }
    /**
     * @return Returns the regions.
     */
    public List getRegions() {
        return Collections.unmodifiableList(fRegions);
    }
}
