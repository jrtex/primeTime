/*
 * Created on 20.04.2004
 *
 * $Header: /Users/wova/laufend/cvs/Seq/de/vahrson/seq/SequenceFormatter.java,v 1.1 2004/04/20 15:11:18 wova Exp $
 *  */
package de.vahrson.seq;

/**
 * @author wova
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class SequenceFormatter {

	/**
	 * 
	 */
	public SequenceFormatter() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String formatAsFastA(Sequence s) {
		StringBuffer answer= new StringBuffer(">");
		answer.append(s.getName());
		answer.append('\n');

		String seq= s.getSequence();
		final int linelen= 60;
		final int len= seq.length();
		for (int i= 0; i < len; i += linelen) {
			int end= Math.min(i + linelen, len);
			answer.append(seq.substring(i, end));
			answer.append('\n');
		}
		return answer.toString();
	}
}
