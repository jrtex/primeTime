/*
 * Created on 01.03.2005 
 *
 * $Header: /Users/wova/laufend/cvs/pt/de/vahrson/pt/OrfHitRemapper.java,v 1.3 2005/03/18 23:26:15 wova Exp $
 */
package de.vahrson.pt;

import java.util.*;

import org.apache.log4j.Logger;
import org.apache.torque.TorqueException;
import org.apache.torque.util.Criteria;

import de.vahrson.pt.om.Hit;
import de.vahrson.pt.om.HitPeer;
import de.vahrson.pt.om.Orf;
import de.vahrson.pt.om.OrfPeer;
import de.vahrson.pt.om.Project;
import de.vahrson.pt.om.ProjectHit;
import de.vahrson.pt.om.ProjectHitPeer;
import de.vahrson.seq.Region;
import de.vahrson.seq.parser.LocationParser;

/**
 * @author wova
 * 
 * Given a Hit relative to an Orf, create a Hit relative to the original
 * sequence
 */
public class OrfHitRemapper {
	public static final String ORF_HIT_REMAPPER= "orf-hit-remapper";
	private int fProjectId;
	private static Logger log= Logger.getLogger(OrfHitRemapper.class);

	public OrfHitRemapper(int projectId) {
		fProjectId= projectId;
	}

	public void run() throws Exception {
		List hits= listPrimer3Hits();
		log.info(hits.size() + " hits to remap");

		for (Iterator i= hits.iterator(); i.hasNext();) {
			Hit h= (Hit) i.next();
			if (!ORF_HIT_REMAPPER.equals(h.getCreator())) { // don't remap remapped hit
				remap(h);
			}
		}
	}
	
	private List listPrimer3Hits() throws Exception{
		Criteria query= new Criteria();
		query.add(ProjectHitPeer.PROJECT_ID,fProjectId);
		query.addJoin(ProjectHitPeer.HIT_ID,HitPeer.HIT_ID);
		query.add(HitPeer.CREATOR,"primer3");
		log.debug(query.toString());
		return HitPeer.doSelect(query);
	}

	Hit remap(Hit src) throws Exception {
		Orf orf= retrieveOrf(src);
		log.debug(orf.getName());
		Hit answer= remapOrf(src, orf);
		answer.save();
		createProjectHit(answer);
		return answer;
	}

	/**
	 * @param answer
	 * @throws TorqueException
	 * @throws Exception
	 */
	private void createProjectHit(Hit answer) throws TorqueException, Exception {
		ProjectHit ph= new ProjectHit();
		ph.setHitId(answer.getHitId());
		ph.setProjectId(fProjectId);
		ph.save();
	}

	/**
	 * This method has been factored out because it is independent of Torque and
	 * can be tested without an underlying database
	 * 
	 * @param src
	 * @param orf
	 * @return
	 * @throws Exception
	 */
	Hit remapOrf(Hit src, Orf orf) throws Exception {
		LocationParser lp= new LocationParser(orf.getLocation());
		List regions= lp.parse();
		boolean isOrfDirectStrand= ((Region) regions.get(0)).isDirectStrand();
		LocationRemapper lr= new LocationRemapper(regions);
		int srcPos= isOrfDirectStrand?src.getPosition():src.getEndPosition();
		int mappedPos= lr.mapPosition(srcPos, isOrfDirectStrand);
		Hit answer= createMappedHit(src, orf.getSourceUsa(), mappedPos, isOrfDirectStrand);
		return answer;
	}

	/**
	 * @param src
	 * @param orf
	 * @param mappedPos
	 * @param isOrfDirectStrand
	 * @return
	 * @throws Exception
	 */
	private Hit createMappedHit(Hit src, String usa, int mappedPos, boolean isOrfDirectStrand) throws Exception {
		Hit answer= new Hit();
		answer.setPrimerId(src.getPrimerId());
		answer.setUsa(usa);
		answer.setPosition(mappedPos);
		answer.setLength(src.getLength());
		answer.setStrand(!(src.getStrand() ^ isOrfDirectStrand));
		/*
		 * src orf --> T 
		 */
		answer.setMismatches(src.getMismatches());
		answer.setCreator(ORF_HIT_REMAPPER);
		return answer;
	}

	/**
	 * @param src
	 * @return
	 * @throws TorqueException
	 * @throws Exception
	 */
	private Orf retrieveOrf(Hit src) throws TorqueException, Exception {
		Criteria query= new Criteria();
		query.add(OrfPeer.USA, src.getUsa());
		log.debug(query.toString());
		List orfs= OrfPeer.doSelect(query);
		if (orfs.size() != 1) {
			throw new Exception("Expected 1, and only 1, Orf for USA: " + src.getUsa() + ". But found: " + orfs.size());
		}
		Orf orf= (Orf) orfs.get(0);
		return orf;
	}
}