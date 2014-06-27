/*
 * Created on 20.04.2004
 *
 * $Header: /Users/wova/laufend/cvs/Seq/de/vahrson/io/FastaReader.java,v 1.1 2004/04/20 15:11:18 wova Exp $
 *  */
package de.vahrson.io;

import java.io.*;

import de.vahrson.seq.Residue;
import de.vahrson.seq.Sequence;

/**
 * @author wova
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class FastaReader {
	private BufferedReader fReader;

	private StringBuffer fNameBuf;
	private StringBuffer fSeqBuf;

	/**
	 * 
	 */
	public FastaReader(Reader in) {
		super();
		fNameBuf= new StringBuffer();
		fSeqBuf= new StringBuffer();
		fReader= new BufferedReader(in);
	}

	/** 
	 * Answer the next Sequence in InputStream, or null if none avaliable
	 * @author wova
	 */
	public Sequence nextSequence() throws IOException {
		Sequence answer= null;
		readSequence();
		if (fSeqBuf.length() > 0) {
			answer= new Sequence(fNameBuf.toString(), fSeqBuf.toString());
		}

		return answer;
	}
	/**
	 * Implementation note: This code is likely ro break when 
	 * >ID lines are longer than READ_STORE_SIZE
	 * @throws IOException
	 */
	static final int READ_STORE_SIZE= 1024;

	private void readSequence() throws IOException {
		fNameBuf.setLength(0);
		fSeqBuf.setLength(0);
		String line;
		boolean seenName= false;
		fReader.mark(READ_STORE_SIZE);
		while (null != (line= fReader.readLine())) {
			line= line.trim();
			if (line.startsWith(">")) {
				if (line.length() > READ_STORE_SIZE) {
					throw new IOException(
						"ID line too long. Cannot read ID lines longer than "
							+ READ_STORE_SIZE
							+ " bytes. ["
							+ line
							+ "]");
				}
				if (seenName) { // terminate
					fReader.reset();
					break;
				}
				else {
					seenName= true;
					fNameBuf.append(line.substring(1));
				}
			}
			else {
				for (int i= 0; i < line.length(); i++) {
					char c= Character.toLowerCase(line.charAt(i));
					if (Residue.isResidue(c)) {
						fSeqBuf.append(c);
					}
				}
			}
			fReader.mark(READ_STORE_SIZE);
		}
	}

}
