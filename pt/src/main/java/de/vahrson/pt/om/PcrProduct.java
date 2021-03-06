
package de.vahrson.pt.om;


import java.util.List;

import org.apache.torque.om.Persistent;
import org.apache.torque.util.Criteria;

/** 
 * The skeleton for this class was autogenerated by Torque on:
 *
 * [Wed Feb 04 11:59:44 CET 2004]
 *
 * You should add additional methods to this class to meet the
 * application requirements.  This class will only be generated as
 * long as it does not already exist in the output directory.
 */
public  class PcrProduct 
    extends de.vahrson.pt.om.BasePcrProduct
    implements Persistent
{
	public String toString() {
		return getPcrProductId() + "\t" + getLeftHitId() + "\t" + getRightHitId();
	}

    /**
     * list PcrProducts in project that have as creator "primer3"
     * @param projectId
     * @return
     * @throws Exception
     */
    public static List listPcrProducts(int projectId) throws Exception{
        return listPcrProducts( projectId,"primer3");
    }
    
    public static List listPcrProducts(int projectId, String creator) throws Exception{
    	Criteria query= new Criteria();
    	query.add(ProjectPcrProductPeer.PROJECT_ID,projectId);
     	query.addJoin(ProjectPcrProductPeer.PCR_PRODUCT_ID,PcrProductPeer.PCR_PRODUCT_ID);
    	query.add(PcrProductPeer.CREATOR,creator);
    	return PcrProductPeer.doSelect(query);
    }
    
    public Primer getLeftPrimer() throws Exception{
        return Primer.primerFromHit(getLeftHitId());
    }
    
    public Primer getRightPrimer() throws Exception{
        return Primer.primerFromHit(getRightHitId());
    }
}
