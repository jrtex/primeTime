/*
 * Created on 21.10.2004
 *
 * $Header: /Users/wova/laufend/cvs/UrlBlast/de/vahrson/blast/PrimerPairBlast.java,v 1.4 2005/03/27 18:01:16 wova Exp $
 */
package de.vahrson.blast;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

import org.apache.log4j.Logger;

import de.vahrson.seq.Sequence;
import de.vahrson.seq.SequenceFormatter;
import de.vahrson.util.StopWatch;
import de.vahrson.util.TabReader;
/**
 * @author wova
 * 
 * PrimerPairBlast is a driver class performing BLAST searches of PCR primer
 * pairs. It uses the UrlBlastClient to perfom the searches proper and then
 * performs pairwise comparisons of the results.
 */
public class PrimerPairBlast {
    // input:
    private Reader fPrimerTab;
    private Properties fQuery;
    private boolean fWantQuery = true;

    // output:
    /**
     * directory where result files are written
     */
    private File fResultDir;
    private OutputStream fResult;

    // state:
    private TabReader fPrimerTabReader;
    private Sequence fPrimer1, fPrimer2;
    private String fSimpleName1, fSimpleName2;
    
    private PrintWriter fWriter;

    // logging:
    private static Logger log = Logger.getLogger(PrimerPairBlast.class);

    public PrimerPairBlast(Properties query, Reader primerTab) throws FileNotFoundException {
        fQuery = query;
        fPrimerTab = primerTab;
    }

    String createResultDirName() {
        String answer;
        if (fQuery.getProperty("resultDir") != null) {
            answer = fQuery.getProperty("resultDir");
        } else {
            String nowStr = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
            answer = "results" + "-" + nowStr;
        }
        return answer;
    }

    public void run() {
        try {
            log.info(StopWatch.timeStamp() + " ppblast started.");
            StopWatch timeTaken = new StopWatch();
            init();
            if (isWantQuery()) {
                fQuery.list(new PrintStream(fResult));
            }

            boolean local= fQuery.getProperty("localblast")!=null && Boolean.valueOf(fQuery.getProperty("localblast")).booleanValue();
            while (nextPrimers()) {
                if (local) {
                    localBlast(fPrimer1);
                    localBlast(fPrimer2);
                }
                else {
                    urlblast(fPrimer1);
                    urlblast(fPrimer2);
                }
                InputStream in1= new FileInputStream(createOutputFile( fSimpleName1 ));
                InputStream in2= new FileInputStream(createOutputFile( fSimpleName2 ));
                
                PairedBlastResultChecker checker= new PairedBlastResultChecker(fPrimer1.getSequence(), in1, fPrimer2.getSequence(), in2);                
                List matches= checker.examineBlastResults();
                
                in1.close();
                in2.close();
                
                for (Iterator i = matches.iterator(); i.hasNext();) {
					PairedBlastResultChecker.Match m= (PairedBlastResultChecker.Match) i.next();
					report(m);
				}
            }
            cleanup();

            timeTaken.stop();
            log.info("time taken: " + timeTaken.toString());
            log.info(StopWatch.timeStamp() + " ppblast done.");
        } catch (Exception e) {
            log.error("uncaught exception in run()\n", e);
        }
    }

    private void init() throws IOException {
        fPrimerTabReader = new TabReader(fPrimerTab);
        fPrimerTabReader.open();
        fResultDir = new File(createResultDirName());
        fResultDir.mkdirs();	
      
        fResult = new FileOutputStream(new File(fResultDir, fQuery.getProperty("resultFileName", "result")));
        fWriter = new PrintWriter(fResult);
        reportHeader();
    }

    private void cleanup() throws IOException {
       fPrimerTabReader.close();
       reportDone();
       fWriter.close();
    }

    private boolean nextPrimers() throws IOException {
        String[] fields = fPrimerTabReader.next();
        boolean answer = fields != null;
        if (answer) {
            if (fields.length == 4) {
                fPrimer1 = new Sequence(fields[0], fields[1].toLowerCase());
                fSimpleName1= simpleNameFromUsa(fPrimer1.getName() );
                fPrimer2 = new Sequence(fields[2], fields[3].toLowerCase());
                fSimpleName2= simpleNameFromUsa(fPrimer2.getName() );
           } else {
                log.error("Unexpected number of fields: " + fields.length + "; expected: 4. " + fields);
                log.warn("Expected file format: primer_name_1 \t primer_seq_1 \t primer_name_2 \t primer_seq_2");
                answer = nextPrimers(); // recursion
            }
        }
        return answer;
    }

    private void urlblast(Sequence primer) throws FileNotFoundException {
        fQuery.setProperty("put.query", new SequenceFormatter().formatAsFastA(primer));
        UrlBlastClient b = new UrlBlastClient(fQuery, new FileOutputStream(createOutputFile(primer.getName())));
        b.setWantQuery(true);
        b.run();
    }
    
    private void localBlast(Sequence primer) throws Exception {
        String fasta= new SequenceFormatter().formatAsFastA(primer);
        String simpleName= simpleNameFromUsa( primer.getName() );
		String tempNamePrefix= "Primer-"+ simpleName;
        log.info(tempNamePrefix);
		File seqFile= File.createTempFile(tempNamePrefix,".fasta");
        log.info(seqFile.getPath());
        FileWriter fw= new FileWriter(seqFile);
        fw.write(fasta);
        fw.close();
        
        fQuery.setProperty("-i", seqFile.getPath());
        fQuery.setProperty("-o", createOutputFile( simpleName ).getPath());
        LocalBlastClient blast= new LocalBlastClient(fQuery,System.out);
        blast.run();
        seqFile.delete();        
    }
    
    private static String simpleNameFromUsa( String usa) {
		String [] fields= usa.split("[:/]+");
        return fields[fields.length-1];
    }

    /**
     * @param name
     * @return
     */
    File createOutputFile(String name) {
        return new File(fResultDir, name + ".blast");
    }
 
    void report(PairedBlastResultChecker.Match m) {
        fWriter.println(m.toString());
    }
    void reportHeader() {
        fWriter.println("------------Matches------------");
        fWriter.println("Subject-ID" + "\t" + "ProductLength" + "\t" + "Mismatches 1" + "\t" + "Strand 1" + "\t" + "Position 1" + "\t" + "Mismatches 2" + "\t" + "Strand 2" + "\t" + "Position 2" );
    }
    void reportDone() {
        fWriter.println("-------------Done--------------");
    }


    /**
     * @return Returns the wantQuery.
     */
    public boolean isWantQuery() {
        return fWantQuery;
    }

    /**
     * @param wantQuery
     *            The wantQuery to set.
     */
    public void setWantQuery(boolean wantQuery) {
        fWantQuery = wantQuery;
    }
}