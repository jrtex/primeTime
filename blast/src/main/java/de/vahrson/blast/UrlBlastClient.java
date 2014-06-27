/*
 * Created on 19.04.2004
 *
 * $Header: /Users/wova/laufend/cvs/UrlBlast/de/vahrson/blast/UrlBlastClient.java,v 1.4 2004/10/23 10:43:57 wova Exp $
 *  */
package de.vahrson.blast;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import de.vahrson.util.IOUtils;
import de.vahrson.util.StopWatch;

/**
 * @author wova
 *
 * A client for NCBI's <a href="http://www.ncbi.nlm.nih.gov/BLAST/Doc/urlapi.html">URL API</a> to QBlast.
 * It querying NCBI's Blast server and retrieving the reults.
 */
public class UrlBlastClient {
	public static final String PREFIX_INFO= "info.";
	public static final String PREFIX_PUT= "put.";
	public static final String PREFIX_GET= "get.";
	public static final String PREFIX_WEB= "web.";

	private Properties fQuery;
	private OutputStream fSink;

	private boolean fWantInfo= true;
	private boolean fWantQuery= true;

	private String fRID;
	private int fRTOE; // expected time of arrival in s

	private static Logger log= Logger.getLogger(UrlBlastClient.class);

	/**
	 * 
	 */
	public UrlBlastClient(Properties query, OutputStream resultSink) {
		super();
		fQuery= query;
		fSink= resultSink;
	}

	public void run() {
		try {
			log.debug(StopWatch.timeStamp() + " url blast started.");
			StopWatch timeTaken= new StopWatch();
			if (isWantQuery()) {
				fQuery.list(new PrintStream(fSink));
			}

			if (isWantInfo()) {
				doInfo();
			}
			doPut();
			doGet();
			doDelete();
			//	doWeb();
			reset();

			timeTaken.stop();
			log.debug("time taken: " + timeTaken.toString());
			log.debug(StopWatch.timeStamp() + " url blast done.");
		}
		catch (Exception e) {
			log.error("uncaught exception in run()\n", e);
		}
	}

	void reset() {
		fRID= null;
		fRTOE= 0;
	}

	void doInfo() throws MalformedURLException, IOException {
		List params= filterProperties(PREFIX_INFO, fQuery);
		URL url= createUrl("Info", params);
		log.debug(url.toString());
		InputStream in= url.openStream();
		IOUtils.copyStream(in, fSink);
		in.close();
	}

	void doPut() throws MalformedURLException, IOException {
		List params= filterProperties(PREFIX_PUT, fQuery);
		if (!params.isEmpty()) {
			URL url= createUrl("Put", params);
			log.debug(url.toString());
			InputStream in= url.openStream();
			extractResultInfo(in);
			log.debug("RID=" + fRID);
			in.close();
		}
	}

	static final int BUF_SIZE= 32 * 1024;
	void doGet() throws MalformedURLException, IOException {
		List params= filterProperties(PREFIX_GET, fQuery);
		if (fRID != null) {
			int cycles= 0;
			while (fRTOE > 0) {
				cycles++;
				waitForResults();
				URL url= createUrl("Get", params);
				log.debug(url.toString());
				InputStream in= url.openStream();
				BufferedInputStream bin= new BufferedInputStream(in, BUF_SIZE);
				bin.mark(BUF_SIZE);
				if (extractStatus(bin)) {
					bin.reset();
					IOUtils.copyStream(bin, fSink);
					fRTOE= 0;
				}
				else {
					fRTOE= 10; // sec
				}
				if (cycles > 100) {
					log.warn("timeout on RID: " + fRID);
				}
			}
		}
	}

	void doDelete() throws MalformedURLException, IOException {
		if (fRID != null) {
			URL url= createUrl("Delete", new ArrayList());
			log.debug(url.toString());
			InputStream in= url.openStream();
			//IOUtils.copyStream(in, fSink);
			in.close();
		}
	}

	synchronized void waitForResults() {
		try {
			log.debug("Waiting for " + fRTOE + " s.");
			Thread.sleep(fRTOE * 1000);
		}
		catch (InterruptedException ie) {
			log.warn("interrupted while waiting for result", ie);
		}
	}
	/**
	 * read RID and RTOE from in
	 * @param in
	 */
	private final static String RE_RID= "[^R]*RID = ([0-9-]+[\\S]*).*";
	private static Pattern REP_RID= Pattern.compile(RE_RID);

	private final static String RE_RTOE= "[^R]*RTOE = ([0-9]+).*";
	private static Pattern REP_RTOE= Pattern.compile(RE_RTOE);

	void extractResultInfo(InputStream in) throws IOException {
		BufferedReader r= new BufferedReader(new InputStreamReader(in));
		String line;
		while ((line= r.readLine()) != null) {
			log.debug(line);
			Matcher m= REP_RID.matcher(line);
			if (m.matches()) {
				fRID= m.group(1);
			}

			m= REP_RTOE.matcher(line);
			if (m.matches()) {
				String rtoe= m.group(1);
				fRTOE= Integer.parseInt(rtoe);
			}
		}
	}

	/**
	 * Answer whether or not contains Status=READY
	 * As a side-effect, in is wound forward until after the Status line. 
	 * @return
	 */
	boolean extractStatus(InputStream in) throws IOException {
		BufferedReader r= new BufferedReader(new InputStreamReader(in), 256);
		String line;
		boolean answer= false;
		boolean seenStatus= false;
		while (!seenStatus && (line= r.readLine()) != null) {
			log.debug(line);
			seenStatus= line.indexOf("Status=") > -1;
			answer= seenStatus && line.indexOf("Status=READY") > -1;
		}
		return answer;
	}

	/**
	 * Answer a List of Strings, each a pair of 'key=value', that originally match prefix
	 * Remove prefix in the answer.
	 * @param prefix
	 * @return
	 */
	List filterProperties(String prefix, Properties query) throws UnsupportedEncodingException {
		List answer= new ArrayList();

		for (Enumeration i= query.propertyNames(); i.hasMoreElements();) {
			String key= (String) i.nextElement();
			if (key.startsWith(prefix)) {
				String truncKey= key.substring(prefix.length());
				answer.add(truncKey.toUpperCase() + "=" + URLEncoder.encode(query.getProperty(key), "UTF-8"));
			}
		}

		return answer;
	}

	URL createUrl(String cmd, List parameters) throws MalformedURLException, UnsupportedEncodingException {
		final StringBuffer s= new StringBuffer("http://");
		s.append(System.getProperty("url", "www.ncbi.nlm.nih.gov/blast/Blast.cgi"));
		s.append('?');

		for (Iterator i= parameters.iterator(); i.hasNext();) {
			String param= (String) i.next();
			s.append(param);
			s.append('&');
		}

		if (fRID != null) {
			s.append("RID=");
			s.append(fRID);
			s.append('&');
		}

		s.append("CMD=");
		s.append(cmd);

		return new URL(s.toString());
	}

	/**
	 * @return
	 */
	String getRID() {
		return fRID;
	}

	/**
	 * @return
	 */
	int getRTOE() {
		return fRTOE;
	}

	/**
	 * @return
	 */
	public boolean isWantInfo() {
		return fWantInfo;
	}

	/**
	 * @param b
	 */
	public void setWantInfo(boolean b) {
		fWantInfo= b;
	}

	/**
	 * @return
	 */
	public boolean isWantQuery() {
		return fWantQuery;
	}

	/**
	 * @param b
	 */
	public void setWantQuery(boolean b) {
		fWantQuery= b;
	}

}
