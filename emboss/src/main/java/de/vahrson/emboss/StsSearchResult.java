/*
 * Created on 06.02.2004
 *
 * $Header: /Users/wova/laufend/cvs/Seq/de/vahrson/emboss/StsSearchResult.java,v 1.1 2004/04/20 15:11:18 wova Exp $
 *  */
package de.vahrson.emboss;

import java.util.*;
import java.util.regex.*;

/**
 * @author wova
 *
 * Representation of a result produced by EMBOSS stssearch
 */
public class StsSearchResult {
	private String fUsa;
	private String fPrimerPairId;
	private boolean fMatchA; // matches A or B?
	private boolean fFwd; // forward or reverse?
	private int fPosition;
	
	private static Pattern gFormatTester= Pattern.compile(".+:.+Primer[AB] matched at .+");

	/**
	 * 
	 */
	public StsSearchResult() {
		super();
	}
	
	/**
	 * StsSearch results come in this format:
	 * 
	 
	1a.M62321: 1a.AF-1 PrimerA matched at 6064
	1a.M62321: (rev) 1a.AF-1 PrimerB matched at 6165
	1a.M62321: 1a.AF-2 PrimerA matched at 4865

	 * @param line
	 * @return
	 * @throws FormatException
	 */
	public static StsSearchResult createStsSearchResult(String line) throws FormatException {
		StsSearchResult answer= new StsSearchResult();
		
		Matcher m= gFormatTester.matcher(line);
		if (!m.find()) {
			throw new FormatException("Expected line in format: USA: PRIMERPAIR PrimerA/B matched at POSITION, but got: " + line);
		}
		
		String [] level1= line.split(":");
		if (level1.length != 2) {
			throw new FormatException("Expected one ':' separator , but got: " + line);
		}
		answer.fUsa= level1[0];
		
		String [] level2= level1[1].trim().split("[ ]");
		String ppId;
		String AorB;
		if (level2[0].equals("(rev)")) {
			answer.fFwd= false;
			ppId= level2[1];
			AorB= level2[2];
		}
		else {
			answer.fFwd= true;
			ppId= level2[0];			
			AorB= level2[1];
		}
		
		answer.fPrimerPairId= ppId;
		
		if (AorB.equals("PrimerA")) {
			answer.fMatchA=true;
		}
		else if (AorB.equals("PrimerB")) {
			answer.fMatchA= false;
		}
		else {
			throw new FormatException("Expected PrimerA/PrimerB but got: " + AorB);
		}
		
		answer.fPosition= Integer.parseInt(level2[level2.length-1]);
		
		return answer; 
	}

	/**
	 * @return
	 */
	public boolean isFwd() {
		return fFwd;
	}

	/**
	 * @return
	 */
	public boolean isMatchA() {
		return fMatchA;
	}

	/**
	 * @return
	 */
	public int getPosition() {
		return fPosition;
	}

	/**
	 * @return
	 */
	public String getPrimerPairId() {
		return fPrimerPairId;
	}

	/**
	 * @return
	 */
	public String getUsa() {
		return fUsa;
	}

}
