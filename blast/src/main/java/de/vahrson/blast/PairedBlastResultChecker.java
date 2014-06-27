/*
 * Created on 26.02.2005 
 *
 * $Header: /Users/wova/laufend/cvs/UrlBlast/de/vahrson/blast/PairedBlastResultChecker.java,v 1.4 2005/04/07 06:36:17 wova Exp $
 */
package de.vahrson.blast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

import org.apache.log4j.Logger;

import de.vahrson.io.FastaReader;
import de.vahrson.seq.Sequence;

/**
 * @author wova
 * 
 * Examines Blast results for a pair of primers. Creates hit for pairs producing
 * a putative PCRProduct
 */
public class PairedBlastResultChecker {
	private String fPrimerSeq1;

	private String fPrimerSeq2;

	private InputStream fBlastResult1, fBlastResult2;

	private int fDistanceThreshold = 4000;

	private int fMismatchThreshold = 8;

	private List fMatches;

	private static Logger log = Logger
			.getLogger(PairedBlastResultChecker.class);

	public class Match {
		private String fUsa;

		private BlastResult.Alignment fAlignment1, fAlignment2;

		private int fDistance, fMismatches1, fMismatches2;

		public Match(String usa, BlastResult.Alignment a1,
				BlastResult.Alignment a2, int distance, int mismatches1,
				int mismatches2) {
			fUsa = usa;
			fAlignment1 = a1;
			fAlignment2 = a2;
			fDistance = distance;
			fMismatches1 = mismatches1;
			fMismatches2 = mismatches2;
		}

		/**
		 * @return Returns the alignment1.
		 */
		public BlastResult.Alignment getAlignment1() {
			return fAlignment1;
		}

		/**
		 * @return Returns the alignment2.
		 */
		public BlastResult.Alignment getAlignment2() {
			return fAlignment2;
		}

		/**
		 * @return Returns the usa.
		 */
		public String getUsa() {
			return fUsa;
		}

		/**
		 * @return Returns the distance.
		 */
		public int getDistance() {
			return fDistance;
		}

		/**
		 * @return Returns the mismatches.
		 */
		public int getMismatches1() {
			return fMismatches1;
		}

		public int getMismatches2() {
			return fMismatches2;
		}
		
		public String toString() {
			StringBuffer answer= new StringBuffer();
			answer.append(getUsa());
			answer.append('\t');
			answer.append(getDistance());
			answer.append('\t');
			answer.append(getMismatches1());
			answer.append('\t');
			answer.append(getAlignment1());
			answer.append('\t');
			answer.append(getMismatches2());
			answer.append('\t');
			answer.append(getAlignment2());
			return answer.toString(); 
		}
	}

	public PairedBlastResultChecker(String primerSeq1,
			InputStream blastResult1, String primerSeq2,
			InputStream blastResult2) {
		fPrimerSeq1 = primerSeq1;
		fBlastResult1 = blastResult1;
		fPrimerSeq2 = primerSeq2;
		fBlastResult2 = blastResult2;
		fMatches = new ArrayList();
	}

	public List examineBlastResults() throws IOException,
			BlastParser.ParseException {
		//List subjectList1 =
		// parseBlastResult(PrimerPairBlast.createOutputFile(fResultDir,
		// fPrimer1.getName())).getSubjects();
		List subjectList1 = parseBlastResult(fBlastResult1).getSubjects();
		List subjectList2 = parseBlastResult(fBlastResult2).getSubjects();
		int nReported = 0;
		for (Iterator i1 = subjectList1.iterator(); i1.hasNext();) {
			BlastResult.Subject s1 = (BlastResult.Subject) i1.next();
			for (Iterator i2 = subjectList2.iterator(); i2.hasNext();) {
				BlastResult.Subject s2 = (BlastResult.Subject) i2.next();
				if (s1.getId().equals(s2.getId())) {
					checkAlignments(s1.getId(), s1.getAlignments(), s2
							.getAlignments());
				}
			}
		}
		log.debug("Examined : " + subjectList1.size() + ", "
				+ subjectList2.size());
		return fMatches;
	}

	boolean checkAlignments(String sid, List alignmentList1, List alignmentList2) {
		boolean answer = false;
		for (Iterator i1 = alignmentList1.iterator(); i1.hasNext();) {
			BlastResult.Alignment a1 = (BlastResult.Alignment) i1.next();
			for (Iterator i2 = alignmentList2.iterator(); i2.hasNext();) {
				BlastResult.Alignment a2 = (BlastResult.Alignment) i2.next();
				if (a1.isSubjectStrand() != a2.isSubjectStrand()) { // are
					// primers
					// on
					// opposite
					// strands?
					boolean orientationOK = false;
					if (a1.isSubjectStrand()) { // a1: Plus , a2: Minus
						orientationOK = a1.getSubjectStartPosition() < a2
								.getSubjectStartPosition();
					} else { // a1: Minus, a2: Plus
						orientationOK = a1.getSubjectStartPosition() > a2
								.getSubjectStartPosition();
					}
					if (orientationOK) {
						//						log.debug("orientationOK:" + sid + "\t" +
						// distance(a1, a2) + "\t" + formatAlignment(a1) + "\t"
						//								+ mismatches(fPrimerSeq1, a1) + "\t" +
						// formatAlignment(a2) + "\t"
						//								+ mismatches(fPrimerSeq2, a2));
						if (isSimilar(fPrimerSeq1, a1)
								&& isSimilar(fPrimerSeq2, a2)) {
							//							log.debug("areSimilar:" + sid + "\t" +
							// distance(a1, a2) + "\t" + formatAlignment(a1) +
							// "\t"
							//									+ mismatches(fPrimerSeq1, a1) + "\t" +
							// formatAlignment(a2) + "\t"
							//									+ mismatches(fPrimerSeq2, a2));
							if (areClose(a1, a2)) {
								answer = true;
								fMatches.add(new Match(sid, a1, a2, distance(
										a1, a2), mismatches(fPrimerSeq1, a1),
										mismatches(fPrimerSeq2, a2)));
							}
						}
					}
				}
			}
		}
		return answer;
	}

	private BlastResult parseBlastResult(InputStream in) throws IOException,
			BlastParser.ParseException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		BlastParser p = new BlastParser(reader);
		BlastResult result = p.parse();
		//result.setFileName(f.getName());
		reader.close();
		return result;
	}

	int distance(BlastResult.Alignment a1, BlastResult.Alignment a2) {
		return Math.abs(a1.getSubjectStartPosition()
				- a2.getSubjectStartPosition())+1;
	}

	boolean areClose(BlastResult.Alignment a1, BlastResult.Alignment a2) {
		return distance(a1, a2) < fDistanceThreshold;
	}

	boolean isSimilar(String primer, BlastResult.Alignment a) {
		return mismatches(primer, a) < fMismatchThreshold;
	}

	int mismatches(String primer, BlastResult.Alignment a) {
		return primer.length() - a.getAlignmentLength();
	}

	/**
	 * @return Returns the distanceThreshold.
	 */
	public int getDistanceThreshold() {
		return fDistanceThreshold;
	}

	/**
	 * @param distanceThreshold
	 *            The distanceThreshold to set.
	 */
	public void setDistanceThreshold(int distanceThreshold) {
		fDistanceThreshold = distanceThreshold;
	}

	/**
	 * @return Returns the mismatchThreshold.
	 */
	public int getMismatchThreshold() {
		return fMismatchThreshold;
	}

	/**
	 * @param mismatchThreshold
	 *            The mismatchThreshold to set.
	 */
	public void setMismatchThreshold(int mismatchThreshold) {
		fMismatchThreshold = mismatchThreshold;
	}

	/**
	 * Sample application: takes as input fasta file containing primers
	 * Compares each subsequent primer pairs 
	 * Looks for results from previous ConcatBlast run
	 * Assumes naming convention: seqname.blast
	 * @param args
	 */
	
	public static void main(String[] args) {
		try {
			File input = new File(args[0]);
			FastaReader r = new FastaReader(new FileReader(input));
			List seqs = new ArrayList();
			Sequence s = null;
			while (null != (s = r.nextSequence())) {
				seqs.add(s);
			}

			for (Iterator i = seqs.iterator(); i.hasNext();) {
				Sequence s1 = (Sequence) i.next();
				Sequence s2 = (Sequence) i.next();
				InputStream r1= new FileInputStream(s1.getName()+".blast");
				InputStream r2= new FileInputStream(s2.getName()+".blast");
				PairedBlastResultChecker runner= new PairedBlastResultChecker(s1.getSequence(), r1, s2.getSequence(), r2);
				List matches= runner.examineBlastResults();
				System.out.println(matches.size());
				for (Iterator mi = matches.iterator(); mi.hasNext();) {
					Match m = (Match) mi.next();
					System.out.println(m);
				}
			}
			
		} catch (Throwable e) {
			System.err.println(e);
			e.printStackTrace(System.err);
		}
	}

}