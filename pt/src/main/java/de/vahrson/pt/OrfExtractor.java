/*
 * Created on 25.02.2005 
 *
 * $Header: /Users/wova/laufend/cvs/pt/de/vahrson/pt/OrfExtractor.java,v 1.3 2005/03/01 20:58:43 wova Exp $
 */
package de.vahrson.pt;

import java.io.*;
import java.util.*;

import org.apache.log4j.Logger;

import pt.RuntimeSupport;

import de.vahrson.emboss.SequenceExtractor;
import de.vahrson.pt.om.Orf;
import de.vahrson.pt.om.ProjectOrf;
import de.vahrson.rusa.Usa;
import de.vahrson.rusa.UsaResolver;
import de.vahrson.seq.Feature;
import de.vahrson.seq.FeatureFilter;
import de.vahrson.seq.Region;
import de.vahrson.seq.Sequence;
import de.vahrson.seq.SequenceFormatter;
import de.vahrson.seq.parser.FeatureParser;

/**
 * @author wova
 * 
 * Extract ORFs from a database entry, store them in pt.db and in rusa db
 */
public class OrfExtractor {
	static Logger log= Logger.getLogger(OrfExtractor.class.getName());
	
	private String fSourceUsa;
	private int fProjectId;
	private File fSourceFile;

	public OrfExtractor(int projectId, String sourceUsa) throws Exception {
		fProjectId= projectId;
		fSourceUsa= sourceUsa;
		fSourceFile= new UsaResolver().resolveUsa(fSourceUsa);
	}

	public void run() throws Exception {
		List orfs= new FeatureFilter("CDS").filter(parseFeatures());
		int orfNumber= 0;
		for (Iterator i= orfs.iterator(); i.hasNext();) {
			Feature f= (Feature) i.next();
			orfNumber++;
			Usa orfUsa= createOrfUsa(fSourceUsa, orfNumber);
			Orf orf= createOrf(f, orfUsa);
			SequenceExtractor se= new SequenceExtractor();
			Sequence s= se.extractSequence(fSourceFile.getPath(), f.getRegions());
			File tempFile= writeFastaToTempFile(orfNumber, s);

			UsaResolver ur= new UsaResolver();
			ur.putLocal(orf.getUsa(), tempFile);

			tempFile.delete();
		}
	}

	/**
	 * @param orfNumber
	 * @param s
	 * @return
	 * @throws IOException
	 */
	private File writeFastaToTempFile(int orfNumber, Sequence s) throws IOException {
		String name= "orf" + orfNumber;
		s.setName(name);
		String fs= new SequenceFormatter().formatAsFastA(s);
//		log.debug(fs);
		File tempFile= File.createTempFile("OrfExtractor", "" + orfNumber);
		Writer w= new FileWriter(tempFile);
		w.write(fs);
		w.close();
		return tempFile;
	}

	/**
	 * @param f
	 * @param orfUsa
	 * @throws Exception
	 */
	private Orf createOrf(Feature f, Usa orfUsa) throws Exception {
		Orf orf= new Orf();
		orf.setName(orfUsa.getId());
		orf.setUsa(orfUsa.toString());
		orf.setSourceUsa(fSourceUsa);
		orf.setLocation(Region.asFeatureLocation(f.getRegions()));
		orf.setLength(Region.length(f.getRegions()));
		orf.save();

		ProjectOrf po= new ProjectOrf();
		po.setProjectId(fProjectId);
		po.setOrfId(orf.getOrfId());
		po.save();

		return orf;
	}

	public static Usa createOrfUsa(String sourceUsa, int orfNumber) {
		Usa src= Usa.parseUsa(sourceUsa, "", "");
		String orfName= orfName(src, orfNumber);
		Usa answer= new Usa("local", "fasta", src.getId() + "-orfs/" + orfName);
		return answer;
	}

	/**
	 * @param sourceUsa
	 * @param orfNumber
	 * @return
	 */
	private static String orfName(Usa sourceUsa, int orfNumber) {
		return sourceUsa.getId() + "-orf-" + orfNumber;
	}

	/**
	 * @return
	 * @throws Exception
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private List parseFeatures() throws Exception, FileNotFoundException, IOException {
		UsaResolver ur= new UsaResolver();
		File source= ur.resolveUsa(fSourceUsa);
		Reader r= new FileReader(source);
		List features= FeatureParser.extractFeatures(r);
		r.close();
		return features;
	}

	public static void main(String[] args) {
		try {
			RuntimeSupport.getInstance();
			OrfExtractor extractor= new OrfExtractor(Integer.parseInt(args[0]), args[1]);
			extractor.run();
		}
		catch (Exception e) {
			e.printStackTrace(System.err);
		}
	}

}