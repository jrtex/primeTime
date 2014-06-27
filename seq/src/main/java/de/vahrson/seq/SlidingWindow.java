/*
 * Created on 01.08.2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package de.vahrson.seq;

import java.util.*;

/**
 * @author wova
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class SlidingWindow {
    private String fSource;
    private int fLength;
    private char [] fWindow;
    private int fPosition; // 0 based position in source
    
    public SlidingWindow(String source, int length) {
        fSource= source;
        fLength= length;
        fWindow= new char[fLength];
        fPosition=-1;
    }
    
	public void next() {
        if (hasNext()) {
	        fPosition++;
	        for (int i=0; i<fLength; i++) {
	            fWindow[i]= fSource.charAt(fPosition+i);
	        }
        }
	}
    
    public boolean hasNext() {
        return fPosition + fLength < fSource.length();
    }
    
    
    

    public int getLength() {
        return fLength;
    }
    // answer 1-based position
    public int getPosition() {
        return fPosition+1;
    }
    public String getSource() {
        return fSource;
    }
    public char[] getWindow() {
        if (fPosition<0) {
            throw new IllegalStateException("Not initialized -- must call next() first");
        }
        return fWindow;
    }
}
