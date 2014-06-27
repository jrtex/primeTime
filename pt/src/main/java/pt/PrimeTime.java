/*
 * Created on 24.02.2005 
 *
 * $Header: /Users/wova/laufend/cvs/pt/pt/PrimeTime.java,v 1.5 2005/04/14 09:35:47 wova Exp $
 */
package pt;

import java.util.*;

import org.apache.log4j.Logger;
import org.apache.torque.TorqueException;

import de.vahrson.pt.OrfExtractor;
import de.vahrson.pt.OrfHitRemapper;
import de.vahrson.pt.Parameters;
import de.vahrson.pt.PrimerFinder;
import de.vahrson.pt.ProjectPrimerPairBlast;
import de.vahrson.pt.om.Orf;
import de.vahrson.pt.om.Project;
import de.vahrson.pt.om.ProjectPeer;
import de.vahrson.rusa.Usa;

/**
 * @author wova
 * 
 * Run the full prime time process
 */
public class PrimeTime {
    private int fProjectId;
    private Parameters fParameters;
    private static Logger log = Logger.getLogger(PrimeTime.class);

    private Project fRoot;
    private Project fCurrentProject;

    public PrimeTime(int projectId) throws Exception {
        initRootProject(projectId);
    }

    /**
     * @param projectId
     * @throws TorqueException
     */
    private void initRootProject(int projectId) throws TorqueException {
        Project p = ProjectPeer.retrieveByPK(projectId);
        if (p.hasParent()) {
            fRoot = ProjectPeer.retrieveByPK(p.getParentId());
        } else {
            fRoot = p;
        }
    }

    public void run() throws Exception {
        changeProject(fRoot);

        int skip = Integer.parseInt(fParameters.get("skipToStage"));

        String[] usas = fParameters.get("usas").split("[, \n]");
        //String restricToUsa= fParameters.get("restrictToUsa");
        if (skip < 10) {
            if (isWantOrfs() && isWantOrfsExtract()) {
                status("Extract Orfs");
                for (int ui = 0; ui < usas.length; ui++) {
                    substatus(usas[ui]);
                    OrfExtractor orfex = new OrfExtractor(fProjectId, usas[ui]);
                    orfex.run();
                }
            }
        }
        // if we have child-projects, attach primers to them rather than root
        List subProjects = fCurrentProject.listChildren();
        if (subProjects.size() == 0) { 
            // if there are no subProjects, go on with current project
            subProjects = new ArrayList();
            subProjects.add(fCurrentProject);
        }
        for (Iterator s = subProjects.iterator(); s.hasNext();) {
            changeProject((Project) s.next());
            
            if (skip < 14) {
                if (isWantPrimers()) {
                    status("Find Primers");
                    if (isWantOrfs()) {
                        List orfs = Project.listOrfs(fProjectId);
                        for (Iterator i = orfs.iterator(); i.hasNext();) {
                            Orf orf = (Orf) i.next();
                            PrimerFinder pf = new PrimerFinder(fProjectId, orf
                                    .getUsa());
                            pf.run();
                        }
                    } else { // whole sequence
                        for (int ui = 0; ui < usas.length; ui++) {
                            substatus(usas[ui]);
                            PrimerFinder pf = new PrimerFinder(fProjectId,
                                    usas[ui]);
                            pf.run();
                        }
                    }
                }
            }

            // sequence independent from here on: -----------

            if (skip < 17) {
                if (isWantOrfs() && isWantOrfRemapping()) {
                    status("Remap Hits from Orfs to originals sequence");
                    OrfHitRemapper ohm = new OrfHitRemapper(fProjectId);
                    ohm.run();
                }
            }

            if (skip < 20) {
                if (isWantBlast1()) {
                    status("Blast 1");
                    fParameters.setNameSpace("blast1");
                    new ProjectPrimerPairBlast(fProjectId, fParameters).run();
                }
            }

            if (skip < 30) {
                if (isWantBlast2()) {
                    status("Blast 2");
                    fParameters.setNameSpace("blast2");
                    new ProjectPrimerPairBlast(fProjectId, fParameters).run();
                }
            }
        }
        status("PrimeTime done.");
    }

    /**
     * @param p
     * @throws Exception
     */
    private void changeProject(Project p) throws Exception {
        fCurrentProject = p;
        fProjectId = fCurrentProject.getProjectId();
        fParameters = Project.createParameters(fProjectId);
        fParameters.setNameSpace("primeTime");
        log.info("Changed project to: " + fProjectId);
    }

    private void status(String msg) {
        log.info("## " + msg);
    }

    private void substatus(String msg) {
        log.info("#\t\t" + msg);
    }

    /**
     * @param usa
     * @throws Exception
     */
    private void findPrimers(String usa) throws Exception {
    }

    public boolean isWantOrfs() {
        return Boolean.valueOf(fParameters.get("wantorfs")).booleanValue();
    }

    public boolean isWantOrfsExtract() {
        return Boolean.valueOf(fParameters.get("wantorfsExtract"))
                .booleanValue();
    }

    public boolean isWantPrimers() {
        return Boolean.valueOf(fParameters.get("wantPrimers")).booleanValue();
    }

    public boolean isWantOrfRemapping() {
        return Boolean.valueOf(fParameters.get("wantOrfRemapping"))
                .booleanValue();
    }

    public boolean isWantBlast1() {
        return Boolean.valueOf(fParameters.get("wantblast1")).booleanValue();
    }

    public boolean isWantBlast2() {
        return Boolean.valueOf(fParameters.get("wantblast2")).booleanValue();
    }

    public static void main(String[] args) {
        try {
        	log.info("PrimeTime 1.1.0");
            RuntimeSupport.getInstance();
            new PrimeTime(Integer.parseInt(args[0])).run();
        } catch (Throwable e) {
            e.printStackTrace(System.err);
        }
    }

}