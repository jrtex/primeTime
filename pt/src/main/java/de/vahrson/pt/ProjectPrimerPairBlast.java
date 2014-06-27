/*
 * Created on 08.03.2005 
 *
 * $Header: /Users/wova/laufend/cvs/pt/de/vahrson/pt/ProjectPrimerPairBlast.java,v 1.3 2005/04/10 13:06:24 wova Exp $
 */
package de.vahrson.pt;


import java.io.StringReader;
import java.util.*;


import de.vahrson.blast.PrimerPairBlast;
import de.vahrson.pt.om.PcrProduct;
import de.vahrson.pt.om.Primer;
import de.vahrson.util.StringUtil;

/**
 * @author wova
 *
 * Run a ppblast against for all primers found in PrimeTime project
 */
public class ProjectPrimerPairBlast {
	private int fProjectId;
	private Parameters fParameters;
	
	public ProjectPrimerPairBlast(int projectId, Parameters params) {
		fProjectId= projectId;
		fParameters= params;
	}
	public void run() throws Exception{
		String primerList= createPrimerList(fProjectId);
		PrimerPairBlast ppblast= new PrimerPairBlast(fParameters.createPropertiesWithClippedKeys("blast."), new StringReader(primerList));
		ppblast.run();
	}
	
	/**
	 * @return
	 * @throws Exception
	 */
	static String createPrimerList(int projectId) throws Exception {
		StringBuffer sink= new StringBuffer();
		List products= PcrProduct.listPcrProducts(projectId);
		for (Iterator pi= products.iterator(); pi.hasNext();) {
			PcrProduct p= (PcrProduct) pi.next();
			Primer l, r;
			l= Primer.primerFromHit(p.getLeftHitId());
			r= Primer.primerFromHit(p.getRightHitId());
			sink.append(StringUtil.join(new String[]{l.getLocalName(), l.getSequence(),r.getLocalName(),r.getSequence()},"\t"));
			sink.append("\n");
		}
		String primerList= sink.toString();
		return primerList;
	}
}
