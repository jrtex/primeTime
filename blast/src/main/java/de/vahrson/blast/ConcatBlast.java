/*
 * Created on 27.03.2005 
 *
 * $Header: /Users/wova/laufend/cvs/UrlBlast/de/vahrson/blast/ConcatBlast.java,v 1.5 2005/04/26 17:02:19 wova Exp $
 */
package de.vahrson.blast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.*;

import org.apache.log4j.Logger;

import de.vahrson.io.FastaReader;
import de.vahrson.seq.Sequence;
import de.vahrson.seq.SequenceFormatter;

/**
 * @author wova
 * 
 * Perform a Blast search, transparently concatenating input sequences for
 * better blast performance. After the search, sort the results and write bona
 * fide blast result files ofr the individual input sequences.
 */
public class ConcatBlast {
	private File fWD;
	private Properties fQuery;
	private List fSequences;
	private int fNumberPerChunk;
	private int fCurrentIndex= 0;
	//
	private List fCurrentSeqs;
	private Sequence fConcatSequence;
	// maping between concatenated and individual seqs:
	private int[] fPositionsIndex;
	private Sequence[] fSequenceIndex;
	//
	private boolean fLocal= true;
	private File fConcatResults;

	private final static String SPACER= "NNNNNNNNNN";
	private final static int SPACER_LENGTH= SPACER.length();
	private static Logger log= Logger.getLogger(ConcatBlast.class);
    private File fIndexFile;

	public ConcatBlast(List sequences, Properties query, File workingDirectory) {
		fSequences= new ArrayList(sequences);
		fQuery= query;
		fWD= workingDirectory;
		init();
	}

	private void init() {
	    //numberPerChunk
		fNumberPerChunk= Integer.parseInt(fQuery.getProperty("numberperchunk", "100"));
		fLocal= fQuery.getProperty("localblast") != null
				&& Boolean.valueOf(fQuery.getProperty("localblast")).booleanValue();
	}

	public void run() throws Exception {
		while (fCurrentIndex < fSequences.size()) {
			int toIndex= Math.min(fCurrentIndex + fNumberPerChunk, fSequences.size());
			log.info("working on chunk: " + fCurrentIndex + "-" + toIndex);
			fCurrentSeqs= fSequences.subList(fCurrentIndex, toIndex);
			concatenateAndIndex();
			dumpIndex();
			blast();
			new ConcatBlastSorter(fConcatResults, fIndexFile, fWD).run();
			fCurrentIndex= toIndex;
		}
	}

	private void concatenateAndIndex() {
		final int n= fCurrentSeqs.size();
		fSequenceIndex= new Sequence[n];
		fPositionsIndex= new int[n];

		StringBuffer concat= new StringBuffer();
		int offset= 1;
		for (int i= 0; i < n; i++) {
			Sequence s= (Sequence) fCurrentSeqs.get(i);
			concat.append(s.getSequence());
			concat.append(SPACER);
			fPositionsIndex[i]= offset;
			fSequenceIndex[i]= s;
			offset+= s.getSequence().length() + SPACER_LENGTH;
		}

		String name= fSequenceIndex[0].getName() + ".." + fSequenceIndex[n - 1].getName();
		fConcatSequence= new Sequence(name, concat.toString());
	}
	
	private void dumpIndex() throws Exception{
		fIndexFile = File.createTempFile("ConcatSeqs-"+fConcatSequence.getName() + "-",".index", fWD);
        PrintWriter w= new PrintWriter(new FileWriter(fIndexFile));
		for (int i = 0; i < fPositionsIndex.length; i++) {
			w.println(fPositionsIndex[i] + "\t" + fSequenceIndex[i].getName());
		}
		w.close();
	}

	private void blast() throws Exception {
		if (fLocal) {
			localBlast();
		}
		else {
			//            urlblast(fPrimer1);
		}
	}

	private void localBlast() throws Exception {
		String fasta= new SequenceFormatter().formatAsFastA(fConcatSequence);
		File seqFile= File.createTempFile("ConcatSeqs-" + fConcatSequence.getName() + "-", ".fasta", fWD);
		log.debug(seqFile.getPath());
		FileWriter fw= new FileWriter(seqFile);
		fw.write(fasta);
		fw.close();

		fConcatResults= File.createTempFile("ConcatSeqs-" + fConcatSequence.getName() + "-", ".blast", fWD);
		fQuery.setProperty("-i", seqFile.getPath());
		fQuery.setProperty("-o", fConcatResults.getPath());
		LocalBlastClient blast= new LocalBlastClient(fQuery, System.out);
		blast.run();
	}



	File hitResultFile(Sequence s) {
		return hitResultFile(fWD, s.getName() );
	}
	
	static public File hitResultFile(File directory, String seqname) {
	    return new File(directory, seqname + ".blast");
	}

	// fasta-file, query-file, workingDir
	public static void main(String[] args) {
		try {
			File input= new File(args[0]);
			FastaReader r= new FastaReader(new FileReader(input));
			List seqs= new ArrayList();
			Sequence s= null;
			while (null != (s= r.nextSequence())) {
				seqs.add(s);
			}
			Properties query;
			if (args.length>1) {
				query= new Properties();
				query.load(new FileInputStream(args[1]));
			}
			else {
				query= System.getProperties();
			}
			
			File wd;
			if (args.length>2) {
				wd= new File(args[2]);
				wd.mkdirs();
			}
			else {
				wd= File.createTempFile("ConcatBlast", "WD");
			}
			
			ConcatBlast runner= new ConcatBlast(seqs,query,wd);
			runner.run();
		}
		catch (Throwable e) {
			System.err.println(e);
			e.printStackTrace(System.err);
		}
	}

}