package de.vahrson.util;

import org.apache.log4j.Logger;

/**
 * Manages stuff that depends on the client's screen resolution
 * Creation date: (27.08.2002 16:44:17)
 * @author: 
 */
public class ScreenDimensionsBean {
	private int fScreenHeight;
	private int fScreenWidth;

	public static String VGA= "vga"; // 640 x 480
	public static String SVGA= "svga"; // 800 x 600
	public static String XGA= "xga"; // 1024 x 768
	public static String SXGA= "sxga"; // 1280 x 1024

	private static Logger log= Logger.getLogger(ScreenDimensionsBean.class);
	
/**
 * ScreenDimensionsBean constructor comment.
 */
public ScreenDimensionsBean() {
	super();
	setScreenHeight(480);
	setScreenWidth(640);
}


/**
 * Answer whether hwight x width fit into the clients screen
 * Creation date: (27.08.2002 16:57:48)
 * @return boolean
 * @param height int
 * @param width int
 */
public boolean fitsInto(int width, int height) {
	return fScreenHeight >= height && fScreenWidth >= width;
}


/**
 * Answer the largest mode fitting into the clients screen resolution
 * Creation date: (27.08.2002 16:55:10)
 * @return java.lang.String
 */
public String getResolutionMode() {
	String answer= null;
	if (fitsInto(1280, 1024)) {
		answer= SXGA;
	}
	else if (fitsInto(1024, 768)) {
		answer= XGA;
	}
	else if (fitsInto(800, 600)) {
		answer= SVGA;
	}
	else if ( fitsInto( 640, 480)) {
		answer= VGA;
	}
	return answer;
}


/**
 * Insert the method's description here.
 * Creation date: (27.08.2002 16:45:54)
 * @return int
 */
public String getScreenHeight() {
	return String.valueOf(fScreenHeight);
}


/**
 * Insert the method's description here.
 * Creation date: (27.08.2002 16:45:54)
 * @return int
 */
public String getScreenWidth() {
	return String.valueOf(fScreenWidth);
}


/**
 * Insert the method's description here.
 * Creation date: (27.08.2002 16:45:54)
 * @param newScreenHeight int
 */
public void setScreenHeight(int newScreenHeight) {
	fScreenHeight = newScreenHeight;
	log.debug("height: " + fScreenHeight);
}


/**
 * Insert the method's description here.
 * Creation date: (27.08.2002 16:45:54)
 * @param newScreenHeight int
 */
public void setScreenHeight(String newScreenHeight) {
	setScreenHeight(Integer.parseInt(newScreenHeight));
}


/**
 * Insert the method's description here.
 * Creation date: (27.08.2002 16:45:54)
 * @param newScreenWidth int
 */
public void setScreenWidth(int newScreenWidth) {
	fScreenWidth = newScreenWidth;
	log.debug("width: " + fScreenWidth);
}


/**
 * Insert the method's description here.
 * Creation date: (27.08.2002 16:45:54)
 * @param newScreenWidth int
 */
public void setScreenWidth(String newScreenWidth) {
	setScreenWidth(Integer.parseInt(newScreenWidth));
}
}