package de.vahrson.util;
import java.io.*;
import java.util.*;
/**
 * Insert the type's description here.
 * Creation date: (05.07.2001 09:25:50)
 * @author: 
 */
public class IOUtils {
	/**
	 * Copy all Files from srcDir into destDir. Recursively descend into subdirs
	 * Creation date: (05.11.2002 19:18:51)
	 * @param srcDir java.io.File
	 * @param destDir java.io.File
	 */
	public static void copyDirectoryTree(File srcDir, File destDir) throws IOException {
		File srcParent = srcDir.getParentFile();
		Iterator i = new FileIterator(srcDir);
		while (i.hasNext()) {
			File f = (File) i.next();
			String path = pathRelativeTo(srcParent, f);
			File x = new File(destDir, path);
			if (f.isDirectory()) {
				x.mkdirs();
			} else {
				File parent = x.getParentFile();
				if (parent != null) {
					parent.mkdirs();
				}
				IOUtils.copyFile(f, x);
			}
		}

	}

	/**
	 * Insert the method's description here.
	 * Creation date: (30.01.01 09:22:29)
	 * @param src java.io.File
	 * @param dest java.io.File
	 */
	public static void copyFile(File src, File dest) throws IOException {
		InputStream ss = null;
		OutputStream ds = null;
		try {
			ss = new BufferedInputStream(new FileInputStream(src));
			ds = new BufferedOutputStream(new FileOutputStream(dest));
			copyStream(ss, ds);
		} finally {
			if (ds != null) {
				ds.close();
			}
			if (ss != null) {
				ss.close();
			}
		}
	}

	/**
	 * Insert the method's description here.
	 * Creation date: (30.01.01 09:22:29)
	 * @param src java.io.File
	 * @param dest java.io.File
	 */
	public static void copyStream(InputStream src, OutputStream dest) throws IOException {
		int c;
		while ((c = src.read()) != -1) {
			dest.write(c);
		}
	}
	
	static class Copier implements Runnable {
	    InputStream fSrc;
	    OutputStream fDest;
	    boolean fSuccess= false;
	    Exception fLastException;
	    
	    public Copier(InputStream src, OutputStream dest) {
	        fSrc= src;
	        fDest= dest;
	    }
	    
        public void run() {
            try {
                copyStream(fSrc,fDest);
                fSuccess= true;
            }
            catch (IOException ioe) {
                fLastException= ioe;
            }
        }
	}
	
	public static void copyStreamAsync(InputStream src, OutputStream dest) throws IOException {
	    Copier c= new Copier(src,dest);
	    Thread t= new Thread(c);
	    t.start();
	}
	

	/**
	 * Answer whether two Streams are equal in a byte-by-byte comparison
	 * Creation date: (03.07.01 17:23:29)
	 * @param z1 java.util.zip.ZipEntry
	 * @param z2 java.util.zip.ZipEntry
	 */
	public static boolean isSameContents(InputStream i1, InputStream i2) throws IOException {
		boolean answer = true;
		int c1, c2;
		int count = 0;
		do {
			c1 = i1.read();
			c2 = i2.read();
			count++;
			answer = c1 == c2;
		} while (answer && c1 != -1);

		return answer;
	}

	/**
	 * Answer a path for child relative to parent; 
	 * i.e., all dirs up tp and including parent are removed
	 * parent = E:/A/B
	 * child = E:/A/B/C/D
	 * answer= C/D
	 * Creation date: (06.11.2002 10:06:11)
	 * @return java.lang.String
	 * @param parent java.io.File
	 * @param child java.io.File
	 */
	public static String pathRelativeTo(File parent, File child) {
		String pStr = parent.getAbsolutePath();
		if (pStr.charAt(pStr.length() - 1) != File.separatorChar) {
			pStr = pStr + File.separator;
		}
		String cStr = child.getAbsolutePath();
		if (!cStr.startsWith(pStr)) {
			throw new IllegalArgumentException("Child must be a subdirectory of parent: \n" + pStr + "\n" + cStr);
		}
		return cStr.substring(pStr.length());
	}
	/**
	 * COpy nLines from Redaer to Writer
	 * @param src
	 * @param dest
	 * @param lines
	 */
	public static void head(Reader src, Writer dest, final int nLines) throws IOException {
		BufferedReader br = new BufferedReader(src);
		BufferedWriter wr = new BufferedWriter(dest);
		String line;
		final String eol = System.getProperty("line.separator", "\n");
		for (int i = 0; i < nLines && null != (line = br.readLine()); i++) {
			wr.write(line);
			wr.write(eol);
		}
		wr.flush();
	}
}