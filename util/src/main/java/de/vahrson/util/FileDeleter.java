package de.vahrson.util;
import java.io.File;
import org.apache.log4j.Logger;
/**
 * Insert the type's description here.
 * Creation date: (05.11.2002 18:08:51)
 * @author: Wolfgang Vahrson
 */
public class FileDeleter implements org.apache.commons.collections.Closure {
	private static Logger log= Logger.getLogger(FileDeleter.class);

/**
 * FileDeleter constructor comment.
 */
public FileDeleter() {
	super();
}


/** Performs some operation on the input object
     */
public void execute(Object input) {
	if (input instanceof File) {
		if (!((File) input).delete()) {
			log.warn("FileDeleter: Could not delete: " + String.valueOf(input));
		}
	}
}
}