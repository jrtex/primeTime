/*
 * Created on 01.03.2005 
 *
 * $Header: /Users/wova/laufend/cvs/Seq/de/vahrson/seq/FeatureFilter.java,v 1.1 2005/03/01 20:59:20 wova Exp $
 */
package de.vahrson.seq;

import java.util.*;

import de.vahrson.util.AbstractFilter;

/**
 * @author wova
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class FeatureFilter extends AbstractFilter {
	private String fKey;

	public FeatureFilter(String key) {
		fKey= key;
	}
	/* (non-Javadoc)
	 * @see de.vahrson.util.Filter#accept(java.lang.Object)
	 */
	public boolean accept(Object o) {
		Feature f= (Feature)o;
		return fKey==null || fKey.equals(f.getKey());
	}

}
