/*
 * Created on 06.02.2004
 *
 * $Header: /Users/wova/laufend/cvs/Seq/de/vahrson/io/LineWise.java,v 1.1 2004/04/20 15:11:18 wova Exp $
 *  */
package de.vahrson.io;

import java.io.BufferedReader;
import java.io.Reader;

/**
 * @author wova
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class LineWise {
	private BufferedReader fReader;
	private RecordPerformer fPerformer;
	private int fLineNumber;
	private int fLinesOK;
	/**
	 * 
	 */
	public LineWise(Reader reader, RecordPerformer performer) {
		super();
		fReader= new BufferedReader(reader);
		fPerformer= performer;
	}

	public void run() throws Exception{
		String line;
		while ((line = fReader.readLine()) != null) {
			try {
				fLineNumber++;
				fPerformer.perform(line);
				fLinesOK++;
			} catch (Exception e) {
				System.err.println("Error reading line " + fLineNumber + " |" + line + "|");
				System.err.println(e.getMessage());
				e.printStackTrace(System.err);
			}
		}
		fPerformer.end();
	}
	
	public String getSummary() {
		return 	fPerformer.describe() + " (" + fLineNumber + " records total / " + fLinesOK + " OK)" ;
	}

}
