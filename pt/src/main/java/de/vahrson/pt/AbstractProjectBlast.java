/*
 * Created on 05.04.2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package de.vahrson.pt;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

import org.apache.log4j.Logger;

import de.vahrson.pt.om.Hit;
import de.vahrson.pt.om.HitPeer;
import de.vahrson.pt.om.PcrProduct;
import de.vahrson.pt.om.Primer;
import de.vahrson.pt.om.Project;
import de.vahrson.pt.om.ProjectPeer;
import de.vahrson.rusa.Usa;
import de.vahrson.seq.Sequence;
import de.vahrson.util.StopWatch;

/**
 * @author wova
 * 
 * This class provides a mini-framework for iterating over all sub-projects of a
 * project and all its USAs.
 */
public abstract class AbstractProjectBlast {

    protected int fProjectId;
    protected List fProjects;
    protected Parameters fParameters;
    protected Project fCurrentProject;
    protected String[] fUsas;
    protected String fCurrentUsa;
    protected File fWD;
    protected List fPrimers;

    static Logger log = Logger.getLogger(AbstractProjectBlast.class);

    protected AbstractProjectBlast(int projectId) {
        fProjectId = projectId;
    }

    protected static List listPrimers(int projectId, String usaStr)
            throws Exception {
        Usa usa= Usa.parseUsa(usaStr,"gb","gb");
        List answer = new ArrayList();
        List products = PcrProduct.listPcrProducts(projectId);
        log.info(products.size() + " PcrProducts.");
        for (Iterator pi = products.iterator(); pi.hasNext();) {
            PcrProduct p = (PcrProduct) pi.next();
            Hit lHit = HitPeer.retrieveByPK(p.getLeftHitId());
            if (lHit.getUsa().indexOf(usa.getId() + "-orf-") > -1) {
                Primer l, r;
                l = Primer.primerFromHit(p.getLeftHitId());
                r = Primer.primerFromHit(p.getRightHitId());
                answer.add(l);
                answer.add(r);
            }
        }
        return answer;
    }

    protected static List seqsFromPrimers(List primers) {
        ArrayList answer = new ArrayList();
        for (Iterator i = primers.iterator(); i.hasNext();) {
            Primer p = (Primer) i.next();
            answer.add(new Sequence(p.getSimpleName(), p.getSequence()));
        }
        return answer;
    }

    public void run() throws Exception {
        StopWatch sw= new StopWatch();
        log.info(StopWatch.timeStamp()  + " started.");
        log.debug("debugging: on");
        fProjects = Project.listChildren(fProjectId);
        if (fProjects.size()==0) {
            fProjects= new ArrayList();
            fProjects.add(ProjectPeer.retrieveByPK(fProjectId));
        }
        int count= 0;
        int pCount=1;
        for (Iterator i = fProjects.iterator(); i.hasNext();) {
            fCurrentProject = (Project) i.next();
            log.info("Current Project: (" + (pCount++) + "/" + fProjects.size() + "): " + fCurrentProject.getProjectId());
            fParameters = fCurrentProject.createParameters();
            String usas = fParameters.get("primeTime.usas");
            fUsas = usas.split(",");
            for (int j = 0; j < fUsas.length; j++) {
                fCurrentUsa = fUsas[j];
                log.info("Current USA (" + (j + 1) + "/" + fUsas.length + "): "
                        + fCurrentUsa);
                fPrimers = listPrimers(fCurrentProject.getProjectId(),
                        fCurrentUsa);
                log.info("  " + fPrimers.size() + " primers.");
                fWD = new File(fCurrentProject.getPath() + "/" + fCurrentUsa
                        + "/blast-hhv/");
                try {
                    log.info("Iteration # " + count);
                    perform();
                 } catch (Exception e) {
                    log.error(tellState());
                    log.error(e);
                 }
            }
        }
        log.info(StopWatch.timeStamp()  + " completed.");
        sw.stop();
        log.info( sw.elapsedTimeMs() + " ms.");
   }

    String tellState() {
        StringBuffer str = new StringBuffer();
        str.append("Current Project: ");
        str.append(fCurrentProject.getProjectId());
        str.append("\n");
        str.append("Current USA: ");
        str.append(fCurrentUsa);
        str.append("\n");
        str.append("Current Directory: ");
        str.append(fWD);
        str.append("\n");

        return str.toString();
    }

    /**
     * fCurrentProject, fCurrentUsa, fWD, fPrimers are now set; Subclasses must
     * implement this method.
     * 
     * @throws Exception
     */
    abstract protected void perform() throws Exception;

}
