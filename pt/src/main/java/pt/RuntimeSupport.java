/*
 * Created on 22.02.2005 
 *
 * $Header: /Users/wova/laufend/cvs/pt/pt/RuntimeSupport.java,v 1.4 2005/04/14 14:11:32 wova Exp $
 */
package pt;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.torque.Torque;
import org.apache.commons.configuration.PropertiesConfiguration;

import de.vahrson.emboss.EmbossApp;

import java.sql.DatabaseMetaData;

/**
 * @author wova
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class RuntimeSupport {
	static final String PROPERTY_PATH = "pt.path";
	static final String CONFIGURATION_DIR = "conf";

	private static RuntimeSupport gInstance;
	private static final Object gMutEx = new Object();
	
	private File fBaseDir;
	private File fConfigurationDir;

	private static Logger log= null; // may only be used after Torque.init()
	
	public static RuntimeSupport getInstance() throws Exception {
		if (gInstance == null) {
			synchronized (gMutEx) {
				if (gInstance == null) {
					gInstance = new RuntimeSupport();
				}
			}
		}
		return gInstance;
	}

	private RuntimeSupport() throws Exception {
		String basePath = System.getProperty(PROPERTY_PATH, "/usr/local/pt");
		fBaseDir= initDir(basePath, "Cannot find base directory ");
		fConfigurationDir = initDir(basePath + "/" + CONFIGURATION_DIR, "Cannot find configuration directory ");
		
		initProperties();
		initLog();
		initTorque();
		checkEmbossVersion();
	}

	/**
	 * @param configurationPath
	 * @return
	 * @throws FileNotFoundException
	 */
	private File initDir(String configurationPath, String msg) throws FileNotFoundException {
		File f = new File(configurationPath);
		if (!f.exists()) {
			throw new FileNotFoundException( msg + f.getPath());
		}
		return f;
	}

	private void initProperties() throws IOException, FileNotFoundException {
		Properties props = new Properties();
		File ptPropertiesPath = new File(fConfigurationDir, "pt.properties");
		if (!ptPropertiesPath.exists()) {
			// silently ignore?
		}
		else {
			props.load(new FileInputStream(ptPropertiesPath));
			for (Iterator i = props.keySet().iterator(); i.hasNext();) {
				String key = (String) i.next();
				System.setProperty(key, props.getProperty(key));
			}
		}
	}

	private void initLog() {
		File log4j = new File(fConfigurationDir, "log4j.properties");
		PropertyConfigurator.configure(log4j.getPath());
	}
	
	private void checkEmbossVersion() throws Exception {
		EmbossApp embossversion= new EmbossApp("embossversion");
		embossversion.setCaptureOutput(true);
		embossversion.run();
		String version= embossversion.getOutput();
		log.debug("EMBOSS version: " + version);
		String expectedVersion= "5.0.0";
		if (!version.startsWith(expectedVersion)) {
			log.warn("Found a different EMBOSS version than expected (found: "+ version + ", expected: " + expectedVersion + "). This might cause problems.");
		}
	}
	
	private void initTorque() throws Exception{
		File torquePath = new File(fConfigurationDir, "torque.properties");
		
		PropertiesConfiguration config= new PropertiesConfiguration();
		config.load(new FileInputStream(torquePath));
		
		String dbSpecificProperties= System.getProperty("pt.db", "pt");
		if (dbSpecificProperties!=null) {
		    config.load(new FileInputStream(new File(fConfigurationDir, dbSpecificProperties +".properties")));		    
		}
		
		File log4j = new File(fConfigurationDir, "log4j.properties");
		if (log4j.exists()) {
		    config.load(new FileInputStream(log4j));		    		    
		}
		
		Torque.init(config);
		
		DatabaseMetaData dmd= Torque.getConnection().getMetaData();
		log= Logger.getLogger(RuntimeSupport.class);
		log.info("Connected to: " + dmd.getURL() + " as user: " + dmd.getUserName());
	}
	
	public static void main(String[]args) {
	    try {
            RuntimeSupport.getInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
	}

}