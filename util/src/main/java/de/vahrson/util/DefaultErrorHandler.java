package de.vahrson.util;
// Import log4j classes.
import org.apache.log4j.Logger;
/**
 * This class ensures consistent top level error handling 
 * Creation date: (30.10.2002 18:05:05)
 * @author: Wolfgang Vahrson
 */
public class DefaultErrorHandler implements ErrorHandler {
	private static int gIncident;
	private static Logger log= Logger.getLogger(DefaultErrorHandler.class);

/**
 * ErrorHandler constructor comment.
 */
public DefaultErrorHandler() {
	super();
}


/**
 * Insert the method's description here.
 * Creation date: (30.10.2002 18:15:58)
 * @return java.lang.String
 */
public static String getUserLevelErrorText() {
	return null;
}


/**
 * Insert the method's description here.
 * Creation date: (30.10.2002 18:15:58)
 * @return java.lang.String
 */
public static String getUserLevelErrorText(int incident) {
	return "Ein interner Fehler ist aufgetreten (#" + incident + ")";
}


/**
 * Insert the method's description here.
 * Creation date: (30.10.2002 18:08:50)
 * @param msg java.lang.String
 * @param problem java.lang.Throwable
 */
public static synchronized int handleError(String msg, Throwable problem) {
	gIncident++;

	log.error("#" + gIncident + " " + msg, problem);

	return gIncident;
}

	/* (non-Javadoc)
	 * @see de.vahrson.util.ErrorHandler#handleError(java.lang.Throwable)
	 */
	public void handleError(Throwable problem) {
		handleError("",problem);
	}

}