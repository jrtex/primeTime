/*
 * Created on 03.02.2005 
 *
 * $Header: /Users/wova/laufend/cvs/pt/de/vahrson/pt/PrimerFinder.java,v 1.8 2005/04/26 17:03:40 wova Exp $
 */
package de.vahrson.pt;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.*;

import org.apache.log4j.Logger;

import pt.RuntimeSupport;

import de.vahrson.emboss.EmbossApp;
import de.vahrson.emboss.InfoSeq;
import de.vahrson.emboss.SequenceExtractor;
import de.vahrson.pt.om.Hit;
import de.vahrson.pt.om.Parameter;
import de.vahrson.pt.om.PcrProduct;
import de.vahrson.pt.om.Primer;
import de.vahrson.pt.om.Project;
import de.vahrson.pt.om.ProjectHit;
import de.vahrson.pt.om.ProjectPcrProduct;
import de.vahrson.pt.om.ProjectPrimer;
import de.vahrson.rusa.UsaResolver;
import de.vahrson.seq.Position;
import de.vahrson.seq.Region;
import de.vahrson.seq.Sequence;
import de.vahrson.util.StringUtil;

/**
 * @author wova
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class PrimerFinder {
	public static final String PRIMER3= "primer3";
	private String fSourceUsa;
	private File fSourceFile;
	private int fFrom;
	private int fTo;
	private int fProjectId;
	private Parameters fParameters;

	private EmbossApp fPrimer3;

	private int fNumReturn;
	private int fNumTargets;
	private int fTarget;
	private List fPrimerPairs;
	private int fIdCount;
	private int fTrials;

	// private static final String EXT = ".primer3";
	private static Logger log= Logger.getLogger(PrimerFinder.class);

	public PrimerFinder(int projectId, String usa) throws Exception {
		this(projectId, usa, 0, 0);
	}

	public PrimerFinder(int projectId, String usa, int from, int to) throws Exception {
		fSourceUsa= usa;
		fProjectId= projectId;
		fFrom= from;
		fTo= to;

		fParameters= Project.createParameters(fProjectId);
		fParameters.setNameSpace("primerFinder");
		log.debug(fParameters.toString());

		fNumReturn= Integer.parseInt(fParameters.get("primer3.numreturn"));
		fNumTargets= 10;
		fTarget= 0;
		fIdCount= 0;
		fTrials= 0;

		UsaResolver rusa= new UsaResolver();
		fSourceFile= rusa.resolveUsa(fSourceUsa);

		adjustBoundaries();
		fPrimer3= initPrimer3();
	}

	/**
	 * @throws IOException
	 * @throws Exception
	 */
	private void adjustBoundaries() throws IOException, Exception {
		if (fFrom == 0) {
			fFrom= 1;
		}
		if (fTo == 0) {
			InfoSeq infoseq= new InfoSeq();
			List infos= infoseq.run(fSourceFile);
			if (infos.size() > 1) {
				throw new Exception("Cannot handle files with more than one entry, found: " + infos.size() + " in: "
						+ fSourceFile);
			}
			fTo= ((InfoSeq.Info) infos.get(0)).getLength();
		}
	}

	private EmbossApp initPrimer3() {
		EmbossApp answer= new EmbossApp("eprimer3");

		answer.addArgument("-primer");		
		answer.setOption("-task", "1");
		answer.setOption("-numreturn", "" + fNumReturn);
		answer.setOption("-includedregion", fFrom + "," + fTo);
		answer.setOption("-outfile", "stdout");

		Properties prop= fParameters.createPropertiesWithClippedKeys("primer3.");
		for (Iterator i= prop.keySet().iterator(); i.hasNext();) {
			String key= (String) i.next();
			String value= prop.getProperty(key);
			String argument= "-" + key;
			log.debug(argument + " " + value);
			answer.setOption(argument, value);
		}

		answer.addArgument(fSourceFile.getPath());

		answer.setCaptureOutput(true);
		return answer;
	}

	public void run() throws Exception {
		fTrials= 0;
		fPrimerPairs= new ArrayList();
		while (isGoOn()) {
			fTrials++;
			nextTarget();
			if (isInRange()) {
				log.debug("trying " + fSourceUsa + ". " + fTarget + "," + (fTarget + 1));
				fPrimer3.setOption("-target", fTarget + "," + (fTarget + 1));
				log.debug(fPrimer3.toString());
				fPrimer3.run();
				if (countPrimerPairs() > 0) {
					fPrimerPairs.addAll(extractPrimerPairs());
				}
			}
		}
		summarize();
	}

	void nextTarget() {
		if (fTarget == 0) {
			fTarget= fTo - 50;
		}
		else {
			fTarget-= 100;
		}
	}

	String nextPrimerId() {
		fIdCount++;
		return fSourceUsa + "-" + fIdCount;
	}

	/**
	 * Answer the number of pairs in the current fPrimer3 result fPrimer3 must
	 * have run before
	 * 
	 * @return
	 */
	int countPrimerPairs() throws Exception {
		int answer= 0;
		BufferedReader reader= new BufferedReader(new StringReader(fPrimer3.getOutput()));
		String line;
		while ((line= reader.readLine()) != null) {
			log.debug(line);
			if (line.matches("^\\s*\\d+ PRODUCT SIZE: \\d+\\s*$")) {
				answer++;
			}
		}
		log.debug(answer + " primer pairs");
		return answer;
	}

	/**
	 * Answer a list of pairs in the current fPrimer3 result fPrimer3 must have
	 * run before
	 * 
	 * @return
	 */
	List extractPrimerPairs() throws Exception {
		List answer= new ArrayList();
		BufferedReader reader= new BufferedReader(new StringReader(fPrimer3.getOutput()));
		String line;
		int productLen, pos;
		productLen= pos= 0;
		Primer primer= null;
		Hit leftHit, rightHit;
		leftHit= rightHit= null;
		while ((line= reader.readLine()) != null) {
			line= line.trim();
			String[] fields= line.split("\\s+");
			if (line.indexOf("PRODUCT SIZE:") > -1) {
				log.debug(StringUtil.join(fields, "|"));
				productLen= Integer.parseInt(fields[3]);
			}
			if (line.indexOf(" PRIMER ") > -1) {
				log.debug(StringUtil.join(fields, "|"));
				primer= createPrimer(nextPrimerId(), fields[6], Double.parseDouble(fields[4]), Double
						.parseDouble(fields[5]));
				pos= Integer.parseInt(fields[2]);

				if (line.indexOf("FORWARD PRIMER") > -1) {
					leftHit= createHit(primer.getPrimerId(), true, pos, primer.getSeqlen());
				}
				else if (line.indexOf("REVERSE PRIMER") > -1) {
					rightHit= createHit(primer.getPrimerId(), false, pos, primer.getSeqlen());
					String seq= extractPcrProductSequence(leftHit, rightHit);
					answer.add(createPcrProduct(leftHit.getHitId(), rightHit.getHitId(), productLen, seq));
					primer= null;
					leftHit= rightHit= null;
					pos= productLen= 0;
				}
			}
		}

		return answer;
	}

	/**
	 * @param leftHit
	 * @param rightHit
	 * @return
	 * @throws Exception
	 */
	private String extractPcrProductSequence(Hit leftHit, Hit rightHit) throws Exception {
		SequenceExtractor se= new SequenceExtractor();
		Region r= new Region(leftHit.getPosition(), Position.EXACT, rightHit.getPosition() + rightHit.getLength() - 1,
				Position.EXACT, true);
		Sequence s= se.extractSequence(fSourceFile.getPath(), Collections.singletonList(r));
		return s.getSequence();
	}

	Primer createPrimer(String id, String seq, double tm, double gc) throws Exception {
		Primer answer= new Primer();
		answer.setLocalName(id);
		answer.setSequence(seq.toLowerCase().replaceAll("\\s", ""));
		answer.setSeqlen(seq.length());
		answer.setTm(tm);
		answer.setGc(gc);
		answer.save();

		ProjectPrimer ph= new ProjectPrimer();
		ph.setProjectId(fProjectId);
		ph.setPrimerId(answer.getPrimerId());
		ph.save();

		return answer;
	}

	Hit createHit(int primerId, boolean directStrand, int pos, int len) throws Exception {
		Hit answer= new Hit();
		answer.setPrimerId(primerId);
		answer.setUsa(fSourceUsa);
		answer.setStrand(directStrand);
		answer.setPosition(pos);
		answer.setLength(len);
		answer.setCreator(PRIMER3);
		answer.save();

		ProjectHit ph= new ProjectHit();
		ph.setProjectId(fProjectId);
		ph.setHitId(answer.getHitId());
		ph.save();

		return answer;
	}

	PcrProduct createPcrProduct(int leftHitId, int rightHitId, int productLen, String sequence) throws Exception {
		if (productLen != sequence.length()) {
			throw new IllegalArgumentException("productLen and sequence.length must be equal, but are " + productLen
					+ ", " + sequence.length());
		}
		PcrProduct answer= new PcrProduct();
		answer.setLeftHitId(leftHitId);
		answer.setRightHitId(rightHitId);
		answer.setSeqlen(productLen);
		answer.setSequence(sequence);
		answer.setCreator(PRIMER3);
		answer.save();

		ProjectPcrProduct ph= new ProjectPcrProduct();
		ph.setProjectId(fProjectId);
		ph.setPcrProductId(answer.getPcrProductId());
		ph.save();

		return answer;
	}

	boolean isGoOn() {
		return (fTarget == 0) || ((fTrials < fNumTargets) && (fPrimerPairs.size() < fNumReturn) && isInRange());
	}

	boolean isInRange() {
		return fFrom < fTarget && fTarget + 1 < fTo;
	}

	void summarize() {
		if (fPrimerPairs.size() < fNumReturn) {
			if (fPrimerPairs.size() == 0) {
				log.warn("No primers found for " + fSourceUsa + ". Gave up after searching whole sequence. Tried "
						+ fTrials + " times.");
			}
			else {
				log.warn("Only " + fPrimerPairs.size() + " primer pairs found for " + fSourceUsa);
			}
		}
	}

	/**
	 * @return Returns the from.
	 */
	public int getFrom() {
		return fFrom;
	}

	/**
	 * @param from
	 *            The from to set.
	 */
	public void setFrom(int from) {
		fFrom= from;
	}

	/**
	 * @return Returns the to.
	 */
	public int getTo() {
		return fTo;
	}

	/**
	 * @param to
	 *            The to to set.
	 */
	public void setTo(int to) {
		fTo= to;
	}

}