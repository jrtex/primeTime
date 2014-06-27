/*
 * Created on 02.04.2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package de.vahrson.pt;

import java.util.*;

import pt.RuntimeSupport;

import de.vahrson.pt.om.Orf;
import de.vahrson.pt.om.Project;

/**
 * @author wova
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ProjectPrimerFinder {
    int fProjectId;
    String fSourceUsa;
    
    ProjectPrimerFinder(int projectId, String sourceUsa) {
        fProjectId= projectId;
        fSourceUsa= sourceUsa;
    }
    
    void run() throws Exception{
		List orfs= Orf.listMatchingOrfs(fSourceUsa);
		for (Iterator i= orfs.iterator(); i.hasNext();) {
			Orf orf= (Orf) i.next();
			PrimerFinder pf= new PrimerFinder(fProjectId, orf.getUsa());
			pf.run();
		}        
    }

    public static void main(String[] args) {
        try {
            RuntimeSupport.getInstance();
            ProjectPrimerFinder ppf= new ProjectPrimerFinder(Integer.parseInt(args[0]), args[1]);
            ppf.run();
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }
}
