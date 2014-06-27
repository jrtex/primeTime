/*
 * Created on 26.04.2004
 *
 * $Header: /Users/wova/laufend/cvs/UrlBlast/de/vahrson/blast/BlastParser.java,v 1.4 2005/02/08 20:21:27 wova Exp $
 *  */
package de.vahrson.blast;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * @author wova
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 >gi|26086004|dbj|AK037685.1| Mus musculus 16 days neonate thymus cDNA, RIKEN full-length
           enriched library, clone:A130037H21 product:unknown EST,
           full insert sequence
          Length = 2574

 Score =  571 bits (288), Expect = e-160
 Identities = 313/323 (96%), Gaps = 10/323 (3%)
 Strand = Plus / Plus

                                                                       
Query: 1   gtgccgtatcctgttgggacagtcaccgcttcttccttgcttctgcatgtgccagaaaac 60
           ||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||
Sbjct: 121 gtgccgtatcctgttgggacagtcaccgcttcttccttgcttctgcatgtgccagaaaac 180

                                                                       
Query: 61  tcccgggagctcaagatgtctgtccaactgaggcttggtctttgtgcttctgggagtggc 120
           ||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||
Sbjct: 181 tcccgggagctcaagatgtctgtccaactgaggcttggtctttgtgcttctgggagtggc 240

                                                                       
Query: 121 tcccagggcctcaggcattcagggtaatccttgtaccctagagttgcaatctaactatct 180
           ||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||
Sbjct: 241 tcccagggcctcaggcattcagggtaatccttgtaccctagagttgcaatctaactatct 300

                                                                       
Query: 181 ctttttcctgataaagttctttctcccttaagaactctcctctggtcactgcatgggtcc 240
           ||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||
Sbjct: 301 ctttttcctgataaagttctttctcccttaagaactctcctctggtcactgcatgggtcc 360

                                                                       
Query: 241 ----------attgtttttaaaatgtcatatttacttagatcaaacaaaaagccactata 290
                     ||||||||||||||||||||||||||||||||||||||||||||||||||
Sbjct: 361 tggtgaacttattgtttttaaaatgtcatatttacttagatcaaacaaaaagccactata 420

                                  
Query: 291 ttgaaaatgagatagttaactgc 313
           |||||||||||||||||||||||
Sbjct: 421 ttgaaaatgagatagttaactgc 443



 Score =  353 bits (178), Expect = 2e-94
 Identities = 178/178 (100%)
 Strand = Plus / Plus

                                                                       
Query: 353 aaagagaattcctatcttttcctgccaagagcactatatgtattttagtcacatcaggaa 412
           ||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||
Sbjct: 483 aaagagaattcctatcttttcctgccaagagcactatatgtattttagtcacatcaggaa 542

                                                                       
Query: 413 gtggagatgaatttgacaccttctattttatctaagaaagcattttactgtggttttgcc 472
           ||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||
Sbjct: 543 gtggagatgaatttgacaccttctattttatctaagaaagcattttactgtggttttgcc 602

                                                                     
Query: 473 ttgtaacaaaggtaaaagggataaaaacaccttaactcttcagggtcagaattaattt 530
           ||||||||||||||||||||||||||||||||||||||||||||||||||||||||||
Sbjct: 603 ttgtaacaaaggtaaaagggataaaaacaccttaactcttcagggtcagaattaattt 660



 **/
public class BlastParser {
	private BufferedReader fReader;
	private final static int MARK_BUFFER_SIZE= 255;

	private BlastResult fResult;
	private BlastResult.Subject fSubject;
	private BlastResult.Alignment fAlignment;

	private boolean fSeenAlignments;

	private String fLine;
	private int fLineNumber;
	/**
	 * 
	 */

	public static class ParseException extends Exception {

		/**
		* 
		*/
		public ParseException() {
			super();
		}

		/**
		 * @param message
		 */
		public ParseException(String message) {
			super(message);
		}

		/**
		 * @param cause
		 */
		public ParseException(Throwable cause) {
			super(cause);
		}

		/**
		 * @param message
		 * @param cause
		 */
		public ParseException(String message, Throwable cause) {
			super(message, cause);
		}

	}
	public BlastParser(BufferedReader reader) {
		super();
		fReader= reader;
		reset();
	}
	
	private void reset() {
		fLine="";
		fResult= new BlastResult();
		fAlignment= null;
		fSubject= null;
		fSeenAlignments= false;
	}

	public BlastResult parse() throws IOException, ParseException {
		reset();
		while (null != nextLine()) {
			if (fLine.equals("ALIGNMENTS") || fLine.startsWith(">")) {
				fSeenAlignments= true;
			}
			if (fSeenAlignments) {
				if (fLine.startsWith(">")) {
					parseSubjectHeader();
				}
				else if (fLine.startsWith("Score = ")) {
					fAlignment= fSubject.addAlignment();
					parseAlignmentHeader();
					parseAlignment();
				}
			}
			if (fLine.startsWith("Number of Hits to DB:")) { // end of one Blast Result
				fResult.setParsedSuccessfully(true);
				break;
			}
		}

		return fResult;
	}

	private void parseSubjectHeader() throws IOException {
		fSubject= fResult.addSubject();
		String[] fields= fLine.split("\\s+", 2);
		fSubject.setId(fields[0].substring(1));
		StringBuffer description= new StringBuffer(fields[1]);
		nextLine();
		while (!fLine.matches("Length = \\d+")) {
			description.append(' ');
			description.append(fLine);
			nextLine();
		}
		fSubject.setDescription(description.toString().trim());
		fields= splitLine();
		fSubject.setLength(Integer.parseInt(fields[2]));
	}

	private void parseAlignmentHeader() throws IOException {
		//Score =  353 bits (178), Expect = 2e-94
		fAlignment.setLineNumber(fLineNumber);
		String[] fields= splitLine();
		fAlignment.setScoreBits(Double.parseDouble(fields[2]));
		fAlignment.setExpect(Double.parseDouble(fields[7]));

		nextLine();
		// Identities = 313/323 (96%), Gaps = 10/323 (3%)

		fields= splitLine();
		String identities= fields[2];
		String[] idenFields= identities.split("/");
		fAlignment.setIdentities(Integer.parseInt(idenFields[0]));
		fAlignment.setAlignmentLength(Integer.parseInt(idenFields[1]));

		if (fields.length > 4) { //gaps
			String gaps= fields[6];
			String[] gapFields= gaps.split("/");
			fAlignment.setGaps(Integer.parseInt(gapFields[0]));
		}

		nextLine();
		//Strand = Plus / Plus

		fields= splitLine();
		fAlignment.setQueryStrand(fields[2].equals("Plus") ? true : false);
		fAlignment.setSubjectStrand(fields[4].equals("Plus") ? true : false);
	}

	private void parseAlignment() throws IOException, ParseException {
		/*
		Query: 1   gtgccgtatcctgttgggacagtcaccgcttcttccttgcttctgcatgtgccagaaaac 60
		   ||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||
		Sbjct: 121 gtgccgtatcctgttgggacagtcaccgcttcttccttgcttctgcatgtgccagaaaac 180
		
		                                                               
		Query: 61  tcccgggagctcaagatgtctgtccaactgaggcttggtctttgtgcttctgggagtggc 120
		   ||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||
		Sbjct: 181 tcccgggagctcaagatgtctgtccaactgaggcttggtctttgtgcttctgggagtggc 240
		 */
		StringBuffer querySeq, subjectSeq;
		querySeq= new StringBuffer();
		subjectSeq= new StringBuffer();

		while (null != nextLine() && !fLine.startsWith("Query:"));

		//Query: 1   gtgccgtatcctgttgggacagtcaccgcttcttccttgcttctgcatgtgccagaaaac 60
		String[] fields= splitLine();
		fAlignment.setQueryStartPosition(Integer.parseInt(fields[1]));

		fAlignment.setQueryEndPosition(Integer.parseInt(fields[3]));
		querySeq.append(fields[2]);
		nextLine();
		nextLine();

		//Sbjct: 121 gtgccgtatcctgttgggacagtcaccgcttcttccttgcttctgcatgtgccagaaaac 180
		fields= splitLine();
		if (!fields[0].equals("Sbjct:")) {
			throw new ParseException("Expected Sbjct: line but got:  " + fLine);
		}
		fAlignment.setSubjectStartPosition(Integer.parseInt(fields[1]));
		fAlignment.setSubjectEndPosition(Integer.parseInt(fields[3]));
		subjectSeq.append(fields[2]);

		while (null != nextLine()
			&& (fLine.startsWith("Query:")
				|| fLine.startsWith("|")
				|| fLine.startsWith("Sbjct:")
				|| fLine.length() == 0)) {
			fields= splitLine();

			if (fields[0].equals("Query:")) {
				querySeq.append(fields[2]);
				fAlignment.setQueryEndPosition(Integer.parseInt(fields[3]));
			}
			else if (fields[0].equals("Sbjct:")) {
				subjectSeq.append(fields[2]);
				;
				fAlignment.setSubjectEndPosition(Integer.parseInt(fields[3]));
			}
		}
		
		fAlignment.setQuerySequence(querySeq.toString().trim());
		fAlignment.setSubjectSequence(subjectSeq.toString().trim());
		// one too far:
		back();

	}

	private String[] splitLine() {
		return fLine.split("\\s+");
	}

	String nextLine() throws IOException {
		fReader.mark(MARK_BUFFER_SIZE);
		fLine= fReader.readLine();
		if (fLine != null) {
			fLine= fLine.trim();
			fLineNumber++;
		}
		return fLine;
	}

	void back() throws IOException {
		fReader.reset();
		fLineNumber--;
		fLine= "";
	}

	/**
	 * @return
	 */
	public String getLine() {
		return fLine;
	}

	/**
	 * @return
	 */
	public int getLineNumber() {
		return fLineNumber;
	}

}
