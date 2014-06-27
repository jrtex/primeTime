/*
 * Created on 28.03.2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package de.vahrson.pt;

import java.io.File;
import java.util.*;

import org.apache.log4j.Logger;
import org.apache.torque.util.Criteria;

import pt.RuntimeSupport;
import sun.tools.tree.DoStatement;

import de.vahrson.blast.ConcatBlast;
import de.vahrson.pt.om.*;
import de.vahrson.seq.Sequence;
import de.vahrson.util.StringUtil;

/**
 * @author wova
 * 
 * Perform ConcatBlast searches for the primers of a project/subprojojects This
 * class decouples the blast step from the pairwise primer blast hit comparison
 */
public class ProjectBlast extends AbstractProjectBlast{

	public ProjectBlast(int projectId) {
		super(projectId);
	}

	/**
     * @throws Exception
     */
    protected void perform() throws Exception {
        fWD.mkdirs();
        log.info("Working Directory: " + fWD.getPath());
        log.info("Before Blasting: " + fPrimers.size() + " primers.");
        ConcatBlast blast = new ConcatBlast(seqsFromPrimers(fPrimers), fParameters
        		.createPropertiesWithClippedKeys("blast."), fWD);
        blast.run();
        log.info("Blast done.");
    }

    public static void main(String[] args) {
		try {
			RuntimeSupport.getInstance();
			ProjectBlast runner = new ProjectBlast(Integer.parseInt(args[0]));
			runner.run();
		} catch (Throwable e) {
			System.err.println(e);
			e.printStackTrace(System.err);
		}
	}
}
