/*
 * Created on 04.02.2004
 *
 * $Header: /Users/wova/laufend/cvs/pt/pt/CreateProject.java,v 1.2 2005/03/01 20:58:44 wova Exp $
 *  */
package pt;

import org.apache.torque.Torque;

import de.vahrson.pt.om.Project;

/**
 * @author wova
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CreateProject {

	public static void main(String[] args) {
		try {
			RuntimeSupport.getInstance();
			if (args.length != 2) {
				System.out.println(usage());
			}
			else {
				Project p= createProject(args[0],args[1]);
				System.out.println("Created Project:");
				System.out.println(p);
			}
		}
		catch (Exception e) {
			e.printStackTrace(System.err);
		}

	}
	
	/**
	 * @param args
	 * @return
	 * @throws Exception
	 */
	static Project createProject(String name, String description) throws Exception {
		Project p= new Project();
		p.setShortname(name);
		p.setName(description);
		p.save();
		return p;
	}

	static String usage() {
		return "pt.CreateProject 'short-name' 'long-name'"; 
	}
}
