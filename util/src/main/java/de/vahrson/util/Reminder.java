package de.vahrson.util;
// Import log4j classes.
import org.apache.log4j.Logger;

/**
 * This class serves as a marker. When used in a method, 
 * VAJ displays the warning sign, because this class is deprecated.
 * Creation date: (26.01.00 22:32:50)
 * @author: wv
 */
final public class Reminder {
	private static Logger log= Logger.getLogger(Reminder.class);

/**
 * Reminder constructor comment.
 * @deprecated
 */
public Reminder() {
	super();
}


/**
 * Insert the method's description here.
 * @deprecated 
 */
public void remindWV(String msg) {
	log.warn(msg);
}
}