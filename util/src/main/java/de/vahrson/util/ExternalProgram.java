/*
 * Created on 08.02.2005 
 *
 * $Header: /Users/wova/laufend/cvs/Utils/de/vahrson/util/ExternalProgram.java,v 1.2 2005/03/31 09:04:11 wova Exp $
 */
package de.vahrson.util;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;

import org.apache.log4j.Logger;

/**
 * @author wova
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ExternalProgram implements Runnable {

    protected String fName;
    protected Map fOptions;
    protected List fArguments;
    private boolean fCaptureOutput = false;
    protected String fOutput;
    static Logger log = Logger.getLogger(ExternalProgram.class);

    /**
     * 
     */
    public ExternalProgram(String name) {
        super();
        fName = System.getProperty(name,name);
        fOptions = new HashMap();
        fArguments = new ArrayList();
    }
    
    public void setOption(String key, String value) {
        fOptions.put(key, value);
    }

    public void setOption(String key) {
        fOptions.put(key, "");
    }

    public String getOption(String key) {
        return (String) fOptions.get(key);
    }

    public void addArgument(String arg) {
        fArguments.add(arg);
    }

    public String getArgument(int index) {
        return (String) fArguments.get(index);
    }

    public String toString() {
        StringBuffer answer = new StringBuffer(fName);
        for (Iterator i = fOptions.keySet().iterator(); i.hasNext();) {
            String key = (String) i.next();
            String value = (String) fOptions.get(key);
            answer.append(' ');
            answer.append(key);
            answer.append(' ');
            answer.append(value);
        }
    
        for (Iterator i = fArguments.iterator(); i.hasNext();) {
            answer.append(' ');
            answer.append(i.next());
        }
    
        return answer.toString();
    }

    public void run() {
        try {
            Process p = Runtime.getRuntime().exec(this.toString());
            InputStream is = p.getInputStream();
            OutputStream os;
            if (isCaptureOutput()) {
                os = new ByteArrayOutputStream(10240);
            } else {
                os = System.out;
            }
            IOUtils.copyStreamAsync(is, os);
    
            InputStream es = p.getErrorStream();
            IOUtils.copyStreamAsync(es, System.err);
    
            int result = p.waitFor();
    
            // after completion of command
            if (result != 0) {
                throw new Exception("resultcode: " + result);
            }
            if (isCaptureOutput()) {
                fOutput = ((ByteArrayOutputStream) os).toString();
            }
        } catch (Exception e) {
            log.error(fName + ".run() : ", e);
        }
    }

    /**
     * Tell this whether it should capture the output of the command
     * 
     * @param captureOutput
     *            The captureOutput to set.
     */
    public void setCaptureOutput(boolean captureOutput) {
        fCaptureOutput = captureOutput;
    }

    /**
     * Get the captured output Throws an Illegal
     * 
     * @return Returns the output.
     */
    public String getOutput() throws Exception {
        if (!isCaptureOutput()) {
            throw new Exception("Cannot answer output: Output has not been captured");
        }
        return fOutput;
    }

    /**
     * @return Returns the captureOutput.
     */
    public boolean isCaptureOutput() {
        return fCaptureOutput;
    }

}
