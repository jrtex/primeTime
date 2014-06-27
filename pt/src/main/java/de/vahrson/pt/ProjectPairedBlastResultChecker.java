/*
 * Created on 28.03.2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package de.vahrson.pt;

import java.io.File;
import java.io.FileInputStream;
import java.util.*;

import org.apache.log4j.Logger;
import org.apache.torque.TorqueException;
import org.apache.torque.util.Criteria;

import pt.RuntimeSupport;
import sun.tools.tree.DoStatement;

import de.vahrson.blast.BlastResult;
import de.vahrson.blast.ConcatBlast;
import de.vahrson.blast.PairedBlastResultChecker;
import de.vahrson.blast.BlastResult.Alignment;
import de.vahrson.pt.om.*;
import de.vahrson.rusa.Usa;
import de.vahrson.seq.Sequence;
import de.vahrson.util.StringUtil;

/**
 * @author wova
 * 
 * Examine the results of previous blast searches for pairwise matches
 */
public class ProjectPairedBlastResultChecker extends AbstractProjectBlast {

    public static final String CREATOR_PAIRED_BLAST = "pairedBlast";

    public ProjectPairedBlastResultChecker(int projectId) {
        super(projectId);
    }

    /*
     * (non-Javadoc)
     * 
     * @see de.vahrson.pt.AbstractProjectBlast#perform()
     */
    protected void perform() throws Exception {
        int count = 0;
        int skipped=0;
        for (Iterator i = fPrimers.iterator(); i.hasNext();) {
            Primer lp = (Primer) i.next();
            Primer rp = (Primer) i.next();
            try {
                File lr = ConcatBlast.hitResultFile(fWD, idFromUsa(lp
                        .getLocalName()));
                File rr = ConcatBlast.hitResultFile(fWD, idFromUsa(rp
                        .getLocalName()));
                if (lr.exists() && rr.exists()) { // it is OK if a file does not exist: it means, no blast hits have been found
                    PairedBlastResultChecker pbc = new PairedBlastResultChecker(
                            lp.getSequence(), new FileInputStream(lr), rp
                                    .getSequence(), new FileInputStream(rr));
                    List matches = pbc.examineBlastResults();
                    count += matches.size();
                    int n = 0;
                    for (Iterator k = matches.iterator(); k.hasNext();) {
                        PairedBlastResultChecker.Match m = (PairedBlastResultChecker.Match) k
                                .next();
                        Hit lh = createHit(lp.getPrimerId(), m.getUsa(), m
                                .getAlignment1());
                        Hit rh = createHit(rp.getPrimerId(), m.getUsa(), m
                                .getAlignment2());
                        log.info("#" + (++n) + "\t" + m + "\t" + lr.getName()
                                + ":" + m.getAlignment1().getLineNumber()
                                + "\t" + rr.getName() + ":"
                                + m.getAlignment2().getLineNumber());
                        createPcrProduct(lh.getHitId(), rh.getHitId(), m
                                .getDistance());
                    }
                }
                else {
                    log.info("Skipping: " + lp.getLocalName() + " || " + rp.getLocalName());
                    skipped++;
                }
            } catch (Exception e) {
                log.error("Processing primers: " + lp.getLocalName() + ", "
                        + rp.getLocalName());
                log.error(tellState());
                log.error(e);
            }
        }
        log.info(count + " paired matches.");
        log.info(skipped + " files skipped.");
    }

    public static String idFromUsa(String usaStr) {
        Usa usa = Usa.parseUsa(usaStr, "", "");
        String[] fields = usa.getId().split("/");
        return fields[fields.length - 1];
    }

    /**
     * @param primerId
     * @param usa
     * @param a
     * @return
     * @throws Exception
     * @throws TorqueException
     */
    private Hit createHit(int primerId, String usa, Alignment a)
            throws Exception, TorqueException {
        Hit h = new Hit();
        h.setPrimerId(primerId);
        h.setUsa(usa.toLowerCase());
        // positions: blast alignments on the indirect strand have reversed positions
        // as compared to the way Hit defines its positions
        h.setPosition(Math.min(a.getSubjectStartPosition(), a.getSubjectEndPosition()));
        h.setLength(a.getAlignmentLength());
        h.setStrand(a.isSubjectStrand());
        h.setMismatches(a.getAlignmentLength() - a.getIdentities());
        h.setCreator(CREATOR_PAIRED_BLAST);
        h.save();

        ProjectHit ph = new ProjectHit();
        ph.setProjectId(fProjectId);
        ph.setHitId(h.getHitId());
        ph.save();
        return h;
    }

    private PcrProduct createPcrProduct(int lhid, int rhid, int len)
            throws Exception {
        PcrProduct answer = new PcrProduct();
        answer.setLeftHitId(lhid);
        answer.setRightHitId(rhid);
        answer.setSeqlen(len);
        answer.setCreator(CREATOR_PAIRED_BLAST);
        answer.save();

        ProjectPcrProduct ppp = new ProjectPcrProduct();
        ppp.setProjectId(fProjectId);
        ppp.setPcrProductId(answer.getPcrProductId());
        ppp.save();

        return answer;
    }

    public static void main(String[] args) {
        try {
            RuntimeSupport.getInstance();
            ProjectPairedBlastResultChecker runner = new ProjectPairedBlastResultChecker(
                    Integer.parseInt(args[0]));
            runner.run();
        } catch (Throwable e) {
            System.err.println(e);
            e.printStackTrace(System.err);
        }
    }
}
