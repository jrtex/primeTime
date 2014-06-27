/*
 * Created on 22.02.2005 
 *
 * $Header: /Users/wova/laufend/cvs/pt/pt/PrimerFinderRunner.java,v 1.3 2005/03/08 14:34:34 wova Exp $
 */
package pt;

import java.util.*;

import de.vahrson.pt.PrimerFinder;

/**
 * @author wova
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class PrimerFinderRunner {

	public static void main(String[] args) {
		try {
			RuntimeSupport.getInstance();

			int projectId= Integer.parseInt(args[0]);
			String usa= args[1];
			int from= Integer.parseInt(args[2]);
			int to= Integer.parseInt(args[3]);

			PrimerFinder pf= new PrimerFinder(projectId, usa, from, to);
			pf.setFrom(Integer.parseInt(args[2]));
			pf.setTo(Integer.parseInt(args[3]));
			pf.run();
		}
		catch (Exception e) {
			System.err.println(e);
			e.printStackTrace(System.err);
		}
	}
}