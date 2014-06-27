/*
 * Created on 03.03.2005 
 *
 * $Header: /Users/wova/laufend/cvs/pt/de/vahrson/pt/Parameters.java,v 1.2 2005/03/18 23:26:15 wova Exp $
 */
package de.vahrson.pt;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.*;

import de.vahrson.pt.om.Parameter;
import de.vahrson.util.AbstractFilter;

/**
 * @author wova
 * 
 * This class provides access to user set parameters
 */
public class Parameters {
	private List fParameterList;
	private Properties fParameters;
	private Properties fDefaultParameters;
	private Properties fNameSpaceParameters;

	private String fNameSpace;

	public Parameters(List parameters, List defaultParameters) {
		fParameterList= parameters;
		fParameters= initProperties(parameters);
		fDefaultParameters= initProperties(defaultParameters);
		fNameSpaceParameters= new Properties();
	}

	/**
	 * @param parameters
	 * @return
	 */
	private Properties initProperties(List parameters) {
		Properties prop= new Properties();
		for (Iterator i= parameters.iterator(); i.hasNext();) {
			Parameter p= (Parameter) i.next();
			prop.setProperty(p.getParameterKey().toLowerCase(), p.getParameterValue());
		}
		return prop;
	}

	public String get(String key) {
		String answer= null;
		key= key.toLowerCase();
		answer= fNameSpaceParameters.getProperty(key);
		if (answer == null) {
			answer= fParameters.getProperty(key);
		}
		if (answer == null) {
			answer= fDefaultParameters.getProperty(key);
		}
		return answer;
	}

	public Set getKeysInNameSpace() {
		return fNameSpaceParameters.keySet();
	}

	public Set getKeys() {
		return fParameters.keySet();
	}

	public Set getDefaultKeys() {
		return fDefaultParameters.keySet();
	}

	public Set getAllKeys() {
		Set answer= new HashSet();
		answer.addAll(getKeysInNameSpace());
		answer.addAll(getKeys());
		answer.addAll(getDefaultKeys());

		return answer;
	}

	/**
	 * @return Returns the nameSpace.
	 */
	public String getNameSpace() {
		return fNameSpace;
	}

	/**
	 * @param nameSpace
	 *            The nameSpace to set.
	 */
	public void setNameSpace(String nameSpace) {
		fNameSpace= nameSpace.toLowerCase();
		fNameSpaceParameters= createPropertiesWithClippedKeys(fNameSpace + ".");
	}

	/**
	 * @return
	 */
	public Properties createPropertiesWithClippedKeys(String prefix) {
		Properties prop= new Properties();
		Collection keys;
		if (prefix != null) {
			prefix= prefix.toLowerCase();
			keys= new PrefixFilter(prefix).filter(getAllKeys());
		}
		else {
			keys= getAllKeys();
			prefix= "";
		}

		for (Iterator i= keys.iterator(); i.hasNext();) {
			String key= (String) i.next();
			String newKey= key.substring(prefix.length());
			prop.setProperty(newKey, get(key));
		}
		return prop;
	}

	public Properties asProperties() {
		return createPropertiesWithClippedKeys(null);
	}

	public void dump(PrintWriter w) throws IOException {
		w.println("# namespace: " + fNameSpace);
		listSorted(fNameSpaceParameters, w);
		w.println("# other");
		listSorted(fParameters, w);
		w.println("# default");
		listSorted(fDefaultParameters, w);
	}

	void listSorted(Properties p, PrintWriter w) {
		List keys= new ArrayList(p.keySet());
		Collections.sort(keys);
		for (Iterator i= keys.iterator(); i.hasNext();) {
			String k= (String) i.next();
			String v= p.getProperty(k);
			w.println(k + "=" + v);
		}
	}

	public String toString() {
		try {
			StringWriter sink= new StringWriter();
			dump(new PrintWriter(sink));
			return sink.toString();
		}
		catch (IOException ioe) {
			throw new InternalError(ioe.getMessage());
		}
	}

	public static class PrefixFilter extends AbstractFilter {
		String fPrefix;

		public PrefixFilter(String prefix) {
			fPrefix= prefix;
		}

		/*
		 * filter for keys starting with the name space prefix
		 * 
		 * @see de.vahrson.util.Filter#accept(java.lang.Object)
		 */
		public boolean accept(Object r) {
			return String.valueOf(r).startsWith(fPrefix);
		}
	}
}