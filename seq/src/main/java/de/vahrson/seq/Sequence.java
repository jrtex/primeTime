/*
 * Created on 20.04.2004
 *
 * $Header: /Users/wova/laufend/cvs/Seq/de/vahrson/seq/Sequence.java,v 1.1 2004/04/20 15:11:18 wova Exp $
 *  */
package de.vahrson.seq;

/**
 * @author wova
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class Sequence {
	private String fName="";
	private String fSequence="";

	/**
	 * 
	 */
	public Sequence() {
		super();
	}
	
	public Sequence(String name, String sequence) {
		fName= name;
		fSequence= sequence.toLowerCase();
	}
	/**
	 * @return
	 */
	public String getName() {
		return fName;
	}

	/**
	 * @return
	 */
	public String getSequence() {
		return fSequence;
	}

	public void setName(String name) {
		fName= name;
	}

}
