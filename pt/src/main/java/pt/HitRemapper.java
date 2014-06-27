/*
 * Created on 22.02.2005 
 *
 * $Header: /Users/wova/laufend/cvs/pt/pt/HitRemapper.java,v 1.1 2005/03/01 20:58:44 wova Exp $
 */
package pt;

import java.util.*;

import de.vahrson.pt.OrfHitRemapper;
import de.vahrson.pt.PrimerFinder;

/**
 * @author wova
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class HitRemapper {

	public static void main(String[] args) {
		try {
			RuntimeSupport.getInstance();
			OrfHitRemapper runner= new OrfHitRemapper(Integer.parseInt(args[0]));
			runner.run();
		}
		catch (Exception e) {
			System.err.println(e);
			e.printStackTrace(System.err);
		}
	}
}
