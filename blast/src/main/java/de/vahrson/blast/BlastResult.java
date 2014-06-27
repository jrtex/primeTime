/*
 * Created on 26.04.2004
 *
 * $Header: /Users/wova/laufend/cvs/UrlBlast/de/vahrson/blast/BlastResult.java,v 1.7 2005/03/27 18:01:16 wova Exp $
 *  */
package de.vahrson.blast;

import java.util.*;

/**
 * @author wova
 * 
 * A BlastResult holds the results of an entire Blast run against a database. It
 * contains the following subsections: GlobalStatistics Subjects Subject1
 * Alignment1 Alignment2 Alignment3 ... Subject2 Alignment1 Alignment2 ...
 * Subject...n
 *  
 */

public class BlastResult {
	private List fSubjects = new ArrayList();

	private boolean fParsedSuccessfully = false;

	/**
	 *  
	 */
	public BlastResult() {
		super();
	}

	public class Subject {
		private List fAlignments = new ArrayList();

		private String fId, fDescription;

		private int fLength;

		public boolean equals(Object other) {
			return (other != null) && (other instanceof Subject)
					&& (((Subject) other).getId().equals(this.getId()));
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Object#hashCode()
		 */
		public int hashCode() {
			return fId.hashCode();
		}

		public Alignment addAlignment() {
			Alignment answer = new Alignment();
			fAlignments.add(answer);
			return answer;
		}

		/**
		 * @return
		 */
		public String getDescription() {
			return fDescription;
		}

		/**
		 * @return
		 */
		public String getId() {
			return fId;
		}

		/**
		 * @return
		 */
		public int getLength() {
			return fLength;
		}

		/**
		 * @param string
		 */
		public void setDescription(String string) {
			fDescription = string;
		}

		/**
		 * @param string
		 */
		public void setId(String string) {
			fId = string;
		}

		/**
		 * @param i
		 */
		public void setLength(int i) {
			fLength = i;
		}

		/**
		 * @return
		 */
		public List getAlignments() {
			return fAlignments;
		}

		public String toString() {
			return getId() + " (" + getLength() + " bp) " + getDescription();
		}

	};

	public class Alignment {
		private int fSubjectStartPosition, fSubjectEndPosition,
				fQueryStartPosition, fQueryEndPosition;

		private String fSubjectSequence, fQuerySequence; // with gaps

		private int fAlignmentLength, fIdentities, fGaps;

		private double fScoreBits, fExpect;

		private boolean fQueryStrand, fSubjectStrand;
		private int fLineNumber=-1; // line number of "Score" line in original blast output 

		/**
		 * @return
		 */
		public int getAlignmentLength() {
			return fAlignmentLength;
		}

		/**
		 * @return
		 */
		public double getExpect() {
			return fExpect;
		}

		/**
		 * @return
		 */
		public int getGaps() {
			return fGaps;
		}

		/**
		 * @return
		 */
		public int getIdentities() {
			return fIdentities;
		}

		/**
		 * @return
		 */
		public int getQueryEndPosition() {
			return fQueryEndPosition;
		}

		/**
		 * @return
		 */
		public String getQuerySequence() {
			return fQuerySequence;
		}

		/**
		 * @return
		 */
		public int getQueryStartPosition() {
			return fQueryStartPosition;
		}

		/**
		 * @return
		 */
		public boolean isQueryStrand() {
			return fQueryStrand;
		}

		/**
		 * @return
		 */
		public double getScoreBits() {
			return fScoreBits;
		}

		/**
		 * @return
		 */
		public int getSubjectEndPosition() {
			return fSubjectEndPosition;
		}

		/**
		 * @return
		 */
		public String getSubjectSequence() {
			return fSubjectSequence;
		}

		/**
		 * @return
		 */
		public int getSubjectStartPosition() {
			return fSubjectStartPosition;
		}

		/**
		 * @return
		 */
		public boolean isSubjectStrand() {
			return fSubjectStrand;
		}

		/**
		 * @param i
		 */
		public void setAlignmentLength(int i) {
			fAlignmentLength = i;
		}

		/**
		 * @param d
		 */
		public void setExpect(double d) {
			fExpect = d;
		}

		/**
		 * @param i
		 */
		public void setGaps(int i) {
			fGaps = i;
		}

		/**
		 * @param i
		 */
		public void setIdentities(int i) {
			fIdentities = i;
		}

		/**
		 * @param i
		 */
		public void setQueryEndPosition(int i) {
			fQueryEndPosition = i;
		}

		/**
		 * @param string
		 */
		public void setQuerySequence(String string) {
			fQuerySequence = string;
		}

		/**
		 * @param i
		 */
		public void setQueryStartPosition(int i) {
			fQueryStartPosition = i;
		}

		/**
		 * @param b
		 */
		public void setQueryStrand(boolean b) {
			fQueryStrand = b;
		}

		/**
		 * @param i
		 */
		public void setScoreBits(double i) {
			fScoreBits = i;
		}

		/**
		 * @param i
		 */
		public void setSubjectEndPosition(int i) {
			fSubjectEndPosition = i;
		}

		/**
		 * @param string
		 */
		public void setSubjectSequence(String string) {
			fSubjectSequence = string;
		}

		/**
		 * @param i
		 */
		public void setSubjectStartPosition(int i) {
			fSubjectStartPosition = i;
		}

		/**
		 * @param b
		 */
		public void setSubjectStrand(boolean b) {
			fSubjectStrand = b;
		}

		/**
		 * @return Returns the lineNumber.
		 */
		public int getLineNumber() {
			return fLineNumber;
		}
		/**
		 * @param lineNumber The lineNumber to set.
		 */
		public void setLineNumber(int lineNumber) {
			fLineNumber = lineNumber;
		}
		
	    String strand(boolean s) {
	        return s ? "-->" : "<--";
	    }
		
		public String toString() {
			return strand(isSubjectStrand()) + "\t" + getSubjectStartPosition();
		}
	};

	/**
	 * @return
	 */
	public List getSubjects() {
		return fSubjects;
	}

	public Subject addSubject() {
		Subject answer = new Subject();
		fSubjects.add(answer);
		return answer;
	}

	/**
	 * @return
	 */
	public boolean isParsedSuccessfully() {
		return fParsedSuccessfully;
	}

	/**
	 * @param b
	 */
	public void setParsedSuccessfully(boolean b) {
		fParsedSuccessfully = b;
	}
}