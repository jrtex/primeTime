/*
 * Created on 10.02.2005 
 *
 * $Header: /Users/wova/laufend/cvs/Seq/de/vahrson/seq/Position.java,v 1.1 2005/02/12 17:41:34 wova Exp $
 */
package de.vahrson.seq;

import java.util.*;

/**
 * @author wova
 *
 * A position in a sequence
 */
public class Position {
    public static final String EXACT="";
    public static final String LESS="<";
    public static final String GREATER=">";   
    
    private int fPosition;
    private String fLimit; 

    
    public Position(int pos) {
        this(pos,EXACT);
    }
    
    public Position(int pos, String limit) {
        if (pos<1) {
            throw new IllegalArgumentException("Position must be >0, was: " +pos);
        }
        fPosition= pos;
        if (!(limit.equals(EXACT) || limit.equals(LESS) || limit.equals(GREATER))) {
            throw new IllegalArgumentException("Limit must be one of '', '<', '>'");
        }
        fLimit= limit;
    }
    
    public String toString() {
        return fLimit+fPosition;
    }
    
    public boolean isDetermined() {
        return fLimit.equals(EXACT);
    }
    
    public boolean isLessOrEqual() {
        return fLimit.equals(LESS);
    }
    public boolean isGreaterOrEqual() {
        return fLimit.equals(GREATER);
    }
    
    /**
     * @return Returns the position.
     */
    public int getPosition() {
        return fPosition;
    }
}
