/*
 * COPYRIGHT 2003 Wolfgang Vahrson (mail@vahrson.de). ALL RIGHTS RESERVED.
 * 
 * Created on 25.07.2003
 *
 * $Header: /Users/wova/laufend/cvs/Utils/de/vahrson/util/StringUtil.java,v 1.3 2005/02/12 17:43:21 wova Exp $
 */
package de.vahrson.util;

import java.util.List;

/**
 * @author wova
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class StringUtil {
	public static String fill(final char c, final int n) {
		StringBuffer answer= new StringBuffer(n);
		fill(answer,c,n);
		return answer.toString();
	}
	
	public static void fill(StringBuffer buf, final char c, final int n) {
		for (int i= 0; i<n; i++) {
			buf.append(c);
		}
	}
	
	public static String rightPad(String field, char padding, int  width){
		String answer;
		if (field.length()<width) {
			answer= field + fill(padding, width-field.length());
		}
		else {
			answer= field;
		}
		return answer;
	}
	
	public static String join(String [] fields, String sep) {
		StringBuffer answer= new StringBuffer();
		if (fields!=null && fields.length>0) {
			answer.append(fields[0]);
			for (int i= 1; i<fields.length; i++) {
				answer.append(sep);
				answer.append(fields[i]);
			}
		}
		return answer.toString();
	}
	
	public static String join(List l, String sep) {
		String [] fields= (String[])l.toArray(new String[l.size()]);
		return join(fields,sep);
	}
	/**
	 * Count occurrences of c in src
	 * @param src
	 * @param c
	 * @return
	 */
	public static int count(String src, char c) {
	    int answer=0;
	    for (int i= 0; i<src.length(); i++) {
	        if (src.charAt(i)==c) {
	            answer++;
	        }
	    }
	    return answer;
	}
}
