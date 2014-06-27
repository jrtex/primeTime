/*
 * Created on 03.03.2005 
 *
 * $Header: /Users/wova/laufend/cvs/Seq/de/vahrson/emboss/InfoSeq.java,v 1.1 2005/03/18 23:27:13 wova Exp $
 */
package de.vahrson.emboss;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.*;

/**
 * @author wova
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class InfoSeq {
	public static class Info {
		String fUsa;
		String fName;
		String fAccession;
		String fType;
		int fLength;
		double fGC;
		String fDescription;
		/**
		 * @return Returns the accession.
		 */
		public String getAccession() {
			return fAccession;
		}
		/**
		 * @return Returns the description.
		 */
		public String getDescription() {
			return fDescription;
		}
		/**
		 * @return Returns the gC.
		 */
		public double getGC() {
			return fGC;
		}
		/**
		 * @return Returns the length.
		 */
		public int getLength() {
			return fLength;
		}
		/**
		 * @return Returns the name.
		 */
		public String getName() {
			return fName;
		}
		/**
		 * @return Returns the type.
		 */
		public String getType() {
			return fType;
		}
		/**
		 * @return Returns the usa.
		 */
		public String getUsa() {
			return fUsa;
		}
	}

	// tembl-id:PAAMIR PAAMIR X13776 N 2167 66.54 Pseudomonas aeruginosa amiC
	// and amiR gene for aliphatic amidase regulation

	public List run(File seqPath) throws IOException {
		File tmp= File.createTempFile("InfoSeq", "result");
		runInfoSeq(seqPath, tmp);
		List answer= parse(new FileReader(tmp));
		tmp.delete();
		return answer;
	}

	List parse(Reader rr) throws IOException {
		List answer= new ArrayList();
		BufferedReader r= new BufferedReader(rr);
		String line= null;
		while ((line= r.readLine()) != null) {
			Info info= parseLine(line);			
			answer.add(info);
		}
		return answer;
	}

	/**
	 * @param line
	 * @return
	 * @throws IOException
	 */
	private Info parseLine(String line) throws IOException {
		line= line.trim();
		Info info= new Info();
		StringReader sr= new StringReader(line);
		StringBuffer b= new StringBuffer();

		fillNonWS(sr, b);
		info.fUsa= b.toString();
		b.setLength(0);
		
		skipWS(sr);
		fillNonWS(sr, b);
		info.fName= b.toString();
		b.setLength(0);

		skipWS(sr);
		fillNonWS(sr, b);
		info.fAccession= b.toString();
		b.setLength(0);

		skipWS(sr);
		fillNonWS(sr, b);
		info.fType= b.toString();
		b.setLength(0);

		skipWS(sr);
		fillNonWS(sr, b);
		info.fLength= Integer.parseInt(b.toString());
		b.setLength(0);

		skipWS(sr);
		fillNonWS(sr, b);
		info.fGC= Double.parseDouble(b.toString());
		b.setLength(0);
		
		int c;
		while ((c= sr.read()) != -1) {
			b.append((char) c);
		}
		info.fDescription= b.toString();
		b.setLength(0);
		return info;
	}

	/**
	 * @param sr
	 * @param b
	 * @throws IOException
	 */
	private void fillNonWS(StringReader sr, StringBuffer b) throws IOException {
		int c;
		while ((c= sr.read()) != -1 && !Character.isWhitespace((char) c)) {
			b.append((char) c);
		}
	}

	private void skipWS(StringReader sr) throws IOException {
		int c;
		sr.mark(1);
		while ((c= sr.read()) != -1 && Character.isWhitespace((char) c)) {
			sr.mark(1); 
		}
		sr.reset();
	}

	/**
	 * @param seqPath
	 * @param tmp
	 */
	private void runInfoSeq(File seqPath, File tmp) {
		EmbossApp infoseq= new EmbossApp("infoseq");
		infoseq.addArgument("-noheading");
		infoseq.addArgument("-only ");
		infoseq.addArgument("-usa");
		infoseq.addArgument("-name");
		infoseq.addArgument("-accession");
		infoseq.addArgument("-type");
		infoseq.addArgument("-length");
		infoseq.addArgument("-pgc");
		infoseq.addArgument("-auto");
		infoseq.setOption("-outfile", tmp.getPath());
		infoseq.addArgument(seqPath.getPath());
		infoseq.setCaptureOutput(false);
		infoseq.run();
	}

}