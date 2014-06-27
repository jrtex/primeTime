/*
 * Created on 20.04.2004
 *
 * $Header: /Users/wova/laufend/cvs/Utils/de/vahrson/util/StopWatch.java,v 1.2 2004/10/23 10:45:59 wova Exp $
 *  */
package de.vahrson.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author wova
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class StopWatch {
	private long fStarted;
	private long fStopped;

	/**
	 * 
	 */
	public StopWatch() {
		super();
		start();
	}
	
	public void start() {
		fStarted= System.currentTimeMillis();
	}
	
	public void stop() {
		fStopped= System.currentTimeMillis();
	}
	
	public long elapsedTimeMs() {
		long elapsed;
		if (fStopped == 0) {
			elapsed= System.currentTimeMillis() - fStarted;
		}
		else {
			elapsed= fStopped - fStarted;
		}
		return elapsed;
	}
	
	
	static final int S= 1024; //ms
	static final int MIN= 60 * S;
	static final int H= 60 * MIN;
    static final SimpleDateFormat DATE_FORMAT= new SimpleDateFormat("yyyy-MM-dd_HH:mm");
	
	public String toString() {
		long t= elapsedTimeMs();
		long h= t/H;
		t= t-h*H;
		long min= t/MIN;
		t= t-min*MIN;
		long s= t/S;
		
		return h + ":" + min +":" + s;
	}

    public static String timeStamp() {
    	return DATE_FORMAT.format(new Date());
    }
}
