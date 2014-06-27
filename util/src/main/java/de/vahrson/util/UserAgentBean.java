package de.vahrson.util;
import java.util.regex.*;

/**
 * A UserAgentBean bundles information about the user's browser
 * Creation date: (13.12.02 11:24:24)
 * This is the java 1.4 version 
 * @author: 
 */
public class UserAgentBean implements UserAgent {
	private String fRequestHeaderUserAgent;

	private String fBrowser;
	private String fBrowserVersion;
	private String fOS;

	/**
	 * UserAgentBean constructor comment.
	 */
	public UserAgentBean() {
		super();
	}

	/**
	 * Insert the method's description here.
	 * Creation date: (13.12.02 11:27:14)
	 * @return java.lang.String
	 */
	public java.lang.String getBrowser() {
		return fBrowser;
	}

	/**
	 * Insert the method's description here.
	 * Creation date: (13.12.02 11:27:14)
	 * @return java.lang.String
	 */
	public java.lang.String getBrowserVersion() {
		return fBrowserVersion;
	}

	/**
	 * Insert the method's description here.
	 * Creation date: (13.12.02 11:27:14)
	 * @return java.lang.String
	 */
	public java.lang.String getOS() {
		return fOS;
	}

	/**
	 * Insert the method's description here.
	 * Creation date: (13.12.02 11:33:19)
	 * @return java.lang.String
	 */
	public java.lang.String getRequestHeaderUserAgent() {
		return fRequestHeaderUserAgent;
	}

	/**
	 * Insert the method's description here.
	 * Creation date: (13.12.02 11:55:04)
	 */
	public boolean isIE() {
		return getBrowser().equals(BROWSER_IE);
	}

	/**
	 * Insert the method's description here.
	 * Creation date: (13.12.02 11:55:04)
	 */
	public boolean isMac() {
		return getOS().equals(OS_MAC);
	}

	/**
	 * Insert the method's description here.
	 * Creation date: (13.12.02 11:55:04)
	 */
	public boolean isNS() {
		return getBrowser().equals(BROWSER_NS);
	}

	/**
	 * Insert the method's description here.
	 * Creation date: (13.12.02 11:55:04)
	 */
	public boolean isOtherBrowser() {
		return getBrowser().equals(BROWSER_OTHER);
	}

	/**
	 * Insert the method's description here.
	 * Creation date: (13.12.02 11:55:04)
	 */
	public boolean isOtherOS() {
		return getOS().equals(OS_OTHER);
	}

	/**
	 * Insert the method's description here.
	 * Creation date: (13.12.02 11:55:04)
	 */
	public boolean isWin() {
		return getOS().equals(OS_WIN);
	}

	/**
	 * Insert the method's description here.
	 * Creation date: (13.12.02 11:47:29)
	 * @param ua java.lang.String
	 */
	private void parseBrowser(String ua) {
		Pattern ie = Pattern.compile("MSIE");
		Pattern ns = Pattern.compile("Netscape");

		if (ie.matcher(ua).find()) {
			fBrowser = BROWSER_IE;
		} else if (ns.matcher(ua).find()) {
			fBrowser = BROWSER_NS;
		} else {
			fBrowser = BROWSER_OTHER;
		}
	}

	/**
	 * Insert the method's description here.
	 * Creation date: (13.12.02 11:47:29)
	 * @param ua java.lang.String
	 */
	private void parseBrowserVersion(String ua) {
		if (isOtherBrowser()) {
			fBrowserVersion = BROWSER_VERSION_UNKNOWN;
		} else {
			Pattern ver = null;
			if (isIE()) {
				ver = Pattern.compile("MSIE (\\d)");
			} else if (isNS()) {
				ver = Pattern.compile("Netscape (\\d)");
			}
			Matcher m = ver.matcher(ua);
			if (m.find()) {
				int i0 = m.start(1);
				int i1 = m.end(1);
				fBrowserVersion = ua.substring(i0, i1);
			} else {
				fBrowserVersion = "1";
			}
		}
	}

	/**
	 * Insert the method's description here.
	 * Creation date: (13.12.02 11:47:29)
	 * @param ua java.lang.String
	 */
	private void parseOS(String ua) {
		Pattern mac = Pattern.compile("Mac");
		Pattern win = Pattern.compile("Windows");
		if (mac.matcher(ua).find()) {
			fOS = OS_MAC;
		} else if (win.matcher(ua).find()) {
			fOS = OS_WIN;
		} else {
			fOS = OS_OTHER;
		}
	}

	/**
	 * Insert the method's description here.
	 * Creation date: (13.12.02 11:34:33)
	 * @param ua java.lang.String
	 */
	private void parseUserAgent(String ua) {
		parseOS(ua);
		parseBrowser(ua);
		parseBrowserVersion(ua);
	}

	/**
	 * Insert the method's description here.
	 * Creation date: (13.12.02 11:33:08)
	 * @param newRequestHeaderUserAgent java.lang.String
	 */
	public void setRequestHeaderUserAgent(
		java.lang.String newRequestHeaderUserAgent) {
		fRequestHeaderUserAgent = newRequestHeaderUserAgent;
		parseUserAgent(fRequestHeaderUserAgent);
	}
}