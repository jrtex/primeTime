/*
 * Created on 22.02.2005 
 *
 * $Header: /Users/wova/laufend/cvs/Rusa/de/vahrson/rusa/DbFetcher.java,v 1.1 2005/02/22 12:22:38 wova Exp $
 */
package de.vahrson.rusa;

import java.io.Writer;
import java.util.*;

/**
 * @author wova
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public interface DbFetcher {
    public abstract void fetch(Usa usa, Writer out) throws Exception;
}