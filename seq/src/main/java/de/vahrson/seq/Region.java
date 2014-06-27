/*
 * Created on 10.02.2005 
 *
 * $Header: /Users/wova/laufend/cvs/Seq/de/vahrson/seq/Region.java,v 1.3 2005/03/01 14:19:44 wova Exp $
 */
package de.vahrson.seq;

import java.util.*;

/**
 * @author wova
 *
 * A region of a (possibly double-stranded) sequence
 */
public class Region {
    private Position fLeftPosition;
    private Position fRightPosition;
    /**
     * direct=true; rev-compl= false;
     */
    private boolean fStrand;
    
    public Region(Position lpos, Position rpos, boolean directStrand) {
        if (lpos==null) {
            throw new IllegalArgumentException("left position argument must not be null");
        }
        fLeftPosition= lpos;
        if (rpos==null) {
            throw new IllegalArgumentException("right position argument must not be null");
        }
        fRightPosition= rpos;
        fStrand= directStrand;
    }
    
    public Region(int lpos, String llimit, int rpos, String rlimit, boolean directStrand) {
        this(new Position(lpos,llimit), new Position(rpos,rlimit), directStrand);
    }
    
    /**
     * Special case: left==right
     * @param pos
     * @param strand
     */
    public Region(Position pos, boolean directStrand) {
        this(pos,pos,directStrand);
    }
    /**
     * @return Returns the leftPosition.
     */
    public Position getLeftPosition() {
        return fLeftPosition;
    }
    /**
     * @return Returns the rightPosition.
     */
    public Position getRightPosition() {
        return fRightPosition;
    }
    /**
     * @return Returns the strand.
     */
    public boolean isDirectStrand() {
        return fStrand;
    }
    
    public boolean isComplementaryStrand() {
        return !fStrand;
    }
    
    String asLocation() {
        StringBuffer answer= new StringBuffer();
        if (isComplementaryStrand()) {
            answer.append("complement(");
        }
        answer.append(fLeftPosition);
        if (!fLeftPosition.equals(fRightPosition)) {
            answer.append("..");
            answer.append(fRightPosition);
        }
        if (isComplementaryStrand()) {
            answer.append(")");
        }
        return answer.toString();
    }
    
    public static String asFeatureLocation(List regions) {
        StringBuffer answer= new StringBuffer();
        if (regions.size()>1) {
            answer.append("join(");
        }
        int n=0;
        for (Iterator i = regions.iterator(); i.hasNext(); n++) {
            if (n>0) {
                answer.append(",");
            }
            Region r = (Region) i.next();
            answer.append(r.asLocation());
        }
        if (regions.size()>1) {
            answer.append(")");
        }
        return answer.toString();
    }
    
    public int length() {
    	return fRightPosition.getPosition() - fLeftPosition.getPosition() + 1;
    }
    
    /**
     * determine the accumulated length of a list of regions
     * @param regions
     * @return
     */
    public static int length(List regions) {
		int len= 0;
		for (Iterator i = regions.iterator(); i.hasNext();) {
			Region r = (Region) i.next();
			len+= r.length();
		}
		return len;
    }
}
