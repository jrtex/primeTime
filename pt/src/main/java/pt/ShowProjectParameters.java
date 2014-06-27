/*
 * Created on 17.03.2005 
 *
 * $Header: /Users/wova/laufend/cvs/pt/pt/ShowProjectParameters.java,v 1.1 2005/03/18 23:26:15 wova Exp $
 */
package pt;

import java.util.*;

import de.vahrson.pt.Parameters;
import de.vahrson.pt.om.Project;

/**
 * @author wova
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class ShowProjectParameters {

	public static void main(String[] args) {
		try {
			RuntimeSupport.getInstance();
			int projectId= Integer.parseInt(args[0]);
			Parameters p=Project.createParameters(projectId);
			System.out.println(p);
//			for (Iterator i= pl.iterator(); i.hasNext();) {
//				System.out.println(i.next());
//			}
		}
		catch (Throwable e) {
			e.printStackTrace(System.err);
		}
	}
}