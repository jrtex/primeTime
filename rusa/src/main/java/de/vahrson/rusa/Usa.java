/*
 * Created on 19.02.2005
 * 
 * $Header: /Users/wova/laufend/cvs/Rusa/de/vahrson/rusa/Usa.java,v 1.3 2005/02/25 11:02:34 wova Exp $
 */
package de.vahrson.rusa;

import java.util.*;

public class Usa {
    private String fId;

    private String fDb;

    private String fFormat;

    public Usa(String db, String format, String id) {
        fDb = db.toLowerCase();
        fFormat = format.toLowerCase();
        fId = id.toLowerCase();
    }

    /**
     * @return Returns the db.
     */
    public String getDb() {
        return fDb;
    }

    /**
     * @return Returns the format.
     */
    public String getFormat() {
        return fFormat;
    }

    /**
     * @return Returns the id.
     */
    public String getId() {
        return fId;
    }

    /**
     * Parse the format::db:entry representation
     * 
     * @param usa
     * @return
     */
    public static Usa parseUsa(String usa, String defaultDb, String defaultFormat) {
        int i;
        String format = defaultFormat;
        if ((i = usa.indexOf("::")) > -1) {
            format = usa.substring(0, i);
            usa = usa.substring(i + 2);
        }

        String db = defaultDb;
        if ((i = usa.indexOf(":")) > -1) {
            db = usa.substring(0, i);
            usa = usa.substring(i + 1);
        }        
        

        String id = usa;
        return new Usa(db, format, id);
    }

    public String toString() {
        return fFormat + "::" + fDb + ":" + fId;
    }

    /**
     * @param format The format to set.
     */
    public void setFormat(String format) {
        fFormat = format;
    }
}