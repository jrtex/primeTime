/*
 * Created on 03.02.2005 
 *
 * $Header: /Users/wova/laufend/cvs/Seq/de/vahrson/emboss/EmbossApp.java,v 1.2 2005/02/08 11:35:14 wova Exp $
 */
package de.vahrson.emboss;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.StringReader;

import org.apache.log4j.Logger;

import de.vahrson.util.ExternalProgram;

/**
 * @author wova
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class EmbossApp extends ExternalProgram {
    static Logger log = Logger.getLogger(EmbossApp.class);

	private File fCapturedOutput;
	
    public EmbossApp(String name) {
        super(name);

        setOption("-auto");
    }
    
    
//  @override
// handle capture output by spooling to a file 
	public void run() {
		try {
			if (isCaptureOutput()) {
				if ( ! ( getOption("-outfile") == null || getOption("-outfile").equals("stdout") || getOption("-outfile").length() == 0 )) {
					throw new Exception("Conflicting output options: Cannot run with 'captureOutput enabled AND output file: " + getOption("-outfile"));
				}
				else {
					fCapturedOutput= File.createTempFile(fName, ".tmp");
					setOption("-outfile", fCapturedOutput.getAbsolutePath());
				}
			}
		
			super.run();
			
			if (isCaptureOutput()) {
				StringBuffer buf= new StringBuffer();
				BufferedReader r= new BufferedReader( new FileReader( fCapturedOutput ));
				String line;
				while ((line= r.readLine()) != null ) {
					buf.append(line);
					buf.append("\n");
				}
				fOutput= buf.toString();
				fCapturedOutput.delete();
				fCapturedOutput= null;
				setOption("-outfile", null);
			}
			
		} catch (Exception e) {
            log.error(fName + ".run() : ", e);
		}
		
	}

	public static void main(String[] args) {
        try {
            EmbossApp app = new EmbossApp(args[0]);
            app.setOption("-outfile", "stdout");
            app.setCaptureOutput(true);
            app.run();
            System.out.println("The process' output:");
            System.out.println(app.getOutput());
        } catch (Exception e) {
            System.err.println(e);
        }
    }
}