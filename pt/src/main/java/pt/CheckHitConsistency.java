/*
 * Created on 12.04.2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package pt;

import java.util.*;

import org.apache.torque.util.Criteria;

import de.vahrson.pt.om.*;

/**
 * @author wova
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class CheckHitConsistency {
    int fProjectId;
    static java.util.logging.Logger log = java.util.logging.Logger
            .getLogger("CheckHitConsistency");

    CheckHitConsistency(int projectId) {
        fProjectId = projectId;
    }

    void run() throws Exception {
        List primers = Project.listPrimers(fProjectId);
        log.info("Project: " + fProjectId + " " + primers.size() + " primer.");
        int c, cPhHits, cOhHits, cPbHits, cMatchHits, cNoMatchHits, cOK, cRejected;
        c= cPhHits= cOhHits= cPbHits= cMatchHits= cNoMatchHits= cOK= cRejected=0;
        
        boolean accept;
        for (Iterator i = primers.iterator(); i.hasNext();) {
            c++;
//            if (c%100==0) {
//                log.info("## " + c + " / " + primers.size());
//            }
            accept= true;
            Primer p = (Primer) i.next();
            Criteria query= new Criteria();
            query.add(HitPeer.PRIMER_ID,p.getPrimerId());
            query.add(HitPeer.CREATOR,"primer3");
            List phits= HitPeer.doSelect(query);
            if (phits.size()!=1) {
                log.warning("Wrong number (" + phits.size() + ") of primer3 hits found for primer:" + p.getPrimerId());
                accept= false;
                cPhHits++;
            }
            //
            query= new Criteria();
            query.add(HitPeer.PRIMER_ID,p.getPrimerId());
            query.add(HitPeer.CREATOR,"orf-hit-remapper");
            List ohhits= HitPeer.doSelect(query);
            if (ohhits.size()!=1) {
                log.warning("Wrong number (" + ohhits.size() + ") of orf-hit-remapper hits found for primer:" + p.getPrimerId());
                accept= false;
                cOhHits++;
          }
            Hit oh= (Hit)ohhits.get(0);
            //         
            query= new Criteria();
            query.add(HitPeer.PRIMER_ID,p.getPrimerId());
            query.add(HitPeer.CREATOR,"pairedBlast");
            List pbhits= HitPeer.doSelect(query);
            if (pbhits.size()!=1) {
                log.info("pairedBlastHits: " + pbhits.size() + " found for primer:" + p.getPrimerId());
                cPbHits++;
            }
            Hit.HitEqualFilter f= new Hit.HitEqualFilter(oh);
            List matchHits= f.filter(pbhits);
            if (matchHits.size()!=1) {
                log.warning("Match pairedBlastHits: " + matchHits.size() + " found for Hit:" + oh.getHitId());
                cMatchHits++;
           }
            
            f.setInvert(true);
            List noMatchHits= f.filter(pbhits);
            cNoMatchHits+= noMatchHits.size();
            for (Iterator no = noMatchHits.iterator(); no.hasNext();) {
                Hit noMaHit = (Hit) no.next();
                log.info("Non-Matching pairedBlastHit: " + noMaHit.getHitId());              
            }
            
            if (accept) {
                cOK++;
            }
            else {
                cRejected++;
            }
        }
        log.info("Accepted: " + cOK + " Rejected: " + cRejected);
        log.info("Problems with primer3 hits: " + cPhHits);
        log.info("Problems with orf-hit-remapper hits: " + cOhHits);
        log.info("Problems with pairedBlast hits: " + cPbHits);
        log.info("Problems with missing matching hits: " + cMatchHits);
        log.info("Additional pairedBlast hits: " + cNoMatchHits);
        log.info("Done.");
    }
    
    public static void main(String [] args) {
        try {
            RuntimeSupport.getInstance();
            CheckHitConsistency runner= new CheckHitConsistency(Integer.parseInt(args[0]));
            runner.run();
        } catch (Exception e) {
            log.severe(e.getMessage());
            e.printStackTrace();
        }
    }
}
