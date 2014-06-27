/*
 * Created on 15.02.2005 
 *
 * $Header: /Users/wova/laufend/cvs/Seq/de/vahrson/emboss/SequenceExtractor.java,v 1.2 2005/02/26 14:46:54 wova Exp $
 */
package de.vahrson.emboss;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.*;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import de.vahrson.seq.Residue;
import de.vahrson.seq.Sequence;
import de.vahrson.seq.Region;

/**
 * @author wova
 * 
 * Extract a sub-sequence. REQUIRES EMBOSS
 */
public class SequenceExtractor {
    private static Logger log= Logger.getLogger(SequenceExtractor.class);
    /**
     * Extract sequence from usaSrc
     * 
     * @param usaSrc
     *            USA of source sequence
     * @param regions
     *            list of Regions
     * @return
     */
    public Sequence extractSequence(String usaSrc, List regions) throws Exception {
        List tmpFiles = new ArrayList();
        for (Iterator i = regions.iterator(); i.hasNext();) {
            Region r = (Region) i.next();
            File tmp = File.createTempFile("seqret", "temporary");
            tmp.deleteOnExit();
            tmpFiles.add(tmp);
            EmbossApp seqret = createSeqRet(usaSrc, r, tmp);
            log.debug(seqret.toString());
            seqret.run();
        }

        StringBuffer seqBuf = new StringBuffer();
        for (Iterator i = tmpFiles.iterator(); i.hasNext();) {
            File tmp = (File) i.next();
            FileReader fr = new FileReader(tmp);
            int c;
            while ((c = fr.read()) != -1) {
                char k = (char) c;
                if (Residue.isResidue(k)) {
                    seqBuf.append(k);
                }
            }
            fr.close();
            tmp.delete();
        }
        return new Sequence(usaSrc +"_"+ Region.asFeatureLocation(regions), seqBuf.toString());
    }

    EmbossApp createSeqRet(String usaSrc, Region region, File outFile) {
        EmbossApp seqret = new EmbossApp("seqret");
        seqret.setOption("-auto");
        seqret.setOption("-osformat", "plain");
        if (region.isComplementaryStrand()) {
            seqret.setOption("-sreverse");
        }
        seqret.setOption("-sbegin", String.valueOf(region.getLeftPosition().getPosition()));
        seqret.setOption("-send", String.valueOf(region.getRightPosition().getPosition()));
        seqret.setOption("-outseq", outFile.getPath());

        seqret.addArgument(usaSrc);
        return seqret;
    }

    // run a self-test
    // because this depends on EMBOSS, it is not a JUnit TestCase but a
    // stand-alone test
    public static void main(String[] args) {
        PropertyConfigurator.configure("log4j.properties");
        System.out.println("SequenceExtractor self-test:");
        try {
            String ref = 
               //12345678901234567890
                "acgtaaccggttaaacccgggttt";
            File rf = File.createTempFile("SequenceExtractor", "selftest");
            FileWriter fw= new FileWriter(rf);
            fw.write(ref);
            fw.close();
            
            List regions= new ArrayList();
            regions.add(new Region(2,"",4,"",false));
            regions.add(new Region(5,"",5,"",true));
            regions.add(new Region(7,"",11,"",false));
            SequenceExtractor se= new SequenceExtractor();
            Sequence result= se.extractSequence(rf.getPath(), regions);
            if (!"acgaaccgg".equals(result.getSequence())) {
                throw new Exception("Expected: acgaaccgg but got: " +result.getSequence());
            }
            rf.delete();
            System.out.println("Test passed.");
       } catch (Exception e) {
            e.printStackTrace(System.err);
       }
    }
}