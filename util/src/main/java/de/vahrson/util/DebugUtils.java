/*
 * Created on 04.03.2004
 *
 * $Header: /Users/wova/laufend/cvs/Utils/de/vahrson/util/DebugUtils.java,v 1.1 2004/04/20 15:08:49 wova Exp $
 *  */
package de.vahrson.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

import org.apache.log4j.Logger;

/**
 * @author wova
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class DebugUtils {
	private static Logger log= Logger.getLogger(DebugUtils.class);

	/**
	 * 
	 */
	public DebugUtils() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public static void logStackTrace(int nLines) {
		try {
			StringWriter sw= new StringWriter();
			new Exception("Show Stacktrace:").printStackTrace(new PrintWriter(sw));
			StringWriter dest= new StringWriter();
			IOUtils.head( new StringReader(sw.getBuffer().toString()), dest, nLines);
			log.debug(dest.toString());
		}
		catch (IOException ioe){
			log.error(ioe);
		}
	}

}
