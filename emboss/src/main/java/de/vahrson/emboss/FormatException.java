/*
 * Created on 06.02.2004
 *
 * $Header: /Users/wova/laufend/cvs/Seq/de/vahrson/emboss/FormatException.java,v 1.1 2004/04/20 15:11:18 wova Exp $
 *  */
package de.vahrson.emboss;

/**
 * @author wova
 *
 * This exception indicates that a particular (file-) format was expected 
 * for the input but not found.  
 */
public class FormatException extends Exception {

	/**
	 * 
	 */
	public FormatException() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 */
	public FormatException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param cause
	 */
	public FormatException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 */
	public FormatException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

}
