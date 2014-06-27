/*
 * Created on 26.04.2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package de.vahrson.blast;

import java.io.*;
import java.util.*;

import org.apache.log4j.Logger;

import de.vahrson.seq.Sequence;

/**
 * @author wova
 *
 * This class sorts the results from a ConcatBlastSearch and distributes
 * them into individual files
 */
public class ConcatBlastSorter {
	private File fConcatResults;
	private File fIndexFile;
	private File fWD;
    // maping between concatenated and individual seqs:
	private int[] fPositionsIndex;
	private String[] fSequenceNameIndex;
	
	static Logger log= Logger.getLogger(ConcatBlastSorter.class);

	public ConcatBlastSorter(File concatResults, File index, File workingDir) throws IOException{
	    fConcatResults= concatResults;
	    fIndexFile= index;
	    fWD= workingDir;
	    initIndex();
	}
	
	private void initIndex() throws IOException{
	    List pos= new ArrayList();
	    List names= new ArrayList();
	    BufferedReader r= new BufferedReader(new FileReader(fIndexFile));
	    String line;
	    while ((line=r.readLine())!=null) {
	        String [] F= line.split("\t");
	        pos.add(F[0]);
	        names.add(F[1]);
	    }
	    r.close();
	    fPositionsIndex= new int[pos.size()];
	    fSequenceNameIndex= new String[names.size()];
	    for (int i = 0; i < fPositionsIndex.length; i++) {
            fPositionsIndex[i]= Integer.parseInt((String)pos.get(i));
            fSequenceNameIndex[i]= (String)names.get(i);
        }
	}
	
	public void run() throws Exception {
		BufferedReader r= new BufferedReader(new FileReader(fConcatResults));
		String line= null;
		int nDescriptions=0;
		int nHits= 0;
		String description= null;
		StringBuffer hit= new StringBuffer();
		boolean seenStart= false;
		boolean seenSbjct= false;
		int pos= -1;
		while (null != (line= r.readLine())) {
			if (line.startsWith(">")) {
				nDescriptions++;
				description= line;
				while (null != (line= r.readLine()) && line.length() > 0) {
					description+= "\n" + line;
				}
			}

			if (line.matches("^\\s+Score.*Expect.*$")) {
				seenStart= true;
				nHits++;
			}
			if (seenStart) {
				hit.append(line);
				hit.append('\n');
			}
			if (line.startsWith("Query:") && pos == -1) {
				String[] fields= line.split("\\s+");
				pos= Integer.parseInt(fields[1]);
			}
			if (line.startsWith("Sbjct:")) {
				seenSbjct= true;
			}
			if (seenSbjct && line.length() < 2) {
				report(description, hit.toString(), pos);
				seenStart= false;
				seenSbjct= false;
				pos= -1;
				hit= new StringBuffer();
			}
		}
		log.info(nDescriptions + " descriptions scanned. " + nHits +" hits.");
	}

	private void report(String description, String hit, int pos) throws Exception {
		String name= identify(pos);
		PrintWriter w= new PrintWriter(new FileWriter(ConcatBlast.hitResultFile(fWD, name), true));
		w.println();
		w.write(description);
		w.println();
		w.println();
		w.write(hit);
		w.println();
		w.println();
		w.close();
	}

	private String identify(int pos) throws Exception{
		int found= -1;
		for (int i= 0; i < fPositionsIndex.length && found == -1; i++) {
			if (pos < fPositionsIndex[i]) {
				found= i-1;
			}
		}
		if (pos >= fPositionsIndex[fPositionsIndex.length-1]) {
			found= fPositionsIndex.length-1;
		}
		if (found==-1) {
			throw new Exception("Could not identify: " + pos);
		}
		return fSequenceNameIndex[found];
	}
   
    public static void main(String[] args) {
        try {
            ConcatBlastSorter runner= new ConcatBlastSorter(new File(args[0]),new File(args[1]),new File(args[2]));
            runner.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
