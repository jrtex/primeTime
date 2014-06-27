/*
 * Created on 20.04.2004
 *
 * $Header: /Users/wova/laufend/cvs/Seq/de/vahrson/io/FastaReaderTest.java,v 1.1 2004/04/20 15:11:18 wova Exp $
 *  */
package de.vahrson.io;

import java.io.StringReader;

import de.vahrson.seq.Sequence;

import junit.framework.TestCase;

/**
 * @author wova
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class FastaReaderTest extends TestCase {
	public void testNextSequence() throws Exception {
		String input=
				"\n"
				+ "\n"
				+ ">gi|333914|gb|K00046.1|RRV26S ross river virus 26s subgenomic rna and junction region\n"
				+ "TTATTTAAACTAGGTAAACCTTTA\n"
				+ "\n"
				+ "\n"
				+ "\n"
				+ "\n"
				+ ">gi|15128154|gb|AF397202.1|AF397202 Regina ranavirus clone PstI-3.8 LCDV orf3-like protein and LCDV orf5-like protein genes, partial cds\n"
				+ "CTGCAGGCGCAAGCTAGAGTTTTCCCTGCCCTACATCAAGCAGTATGTGCCCGTGGGACTGGTTTTTAAG\n"
				+ "GCCCTTGGGAAAACTGCGAGGGAGGCCGTGGCCATGTGTGGGCTGGGGTCCTTTTCGGGAGACGAGGTCT\n"
				+ "CGTGCAGGACGGTCCACCACCAGAGCATGGCGGCCCTCCTCATGGAGCAGCACGCGTCCGCCCCCGAGGA\n"
				+ "CCCCGTGGCGGATCTGGCCAAGCACGTCCCCGACGGCAGGTCCGTCTACTTGAAGCAGGCCGAAGGCGCG\n"
				+ "AAGCAGGCCGAAAACGCCGATCGGGCCTCTGTGGAAGAGGACCGCAACTCCAAGGGCTCCAAGGAGGACT\n"
				+ "ACGTCAAGCACGTCCTGGCCGGAGAAATCTTTCTTCACGGCGGAGACGCTGCGGAGCACCTAGGGTGGAT\n"
				+ ">gi|14149395|gb|AF368231.1|AF368231 Regina ranavirus clone PstI-3.0 LCDV orf 88-like protein gene, partial cds; and neurofilament triplet H1-like protein and LCDV orf 58-like protein genes, complete cds\n"
				+ "GTACCGAATGAGGTCCCTGAGATCCATGTCTGTCACCTCTTCCATAAAACTCTGAACGTGAGACAGTCTC\n"
				+ "CCCTTATTTATGGATCCCCTCCCTCCGTCTGTCACGTAGAAACCGAGGACAGTGCCCATSGGGTTCTTGT\n"
				+ "AGTTTGGGTAGGTTATCCTCAGGACTGAAGAGAGGAGCCTAGTCTGCTCCTGCTTGTTTAGCTTGATGTG\n"
				+ "ACCATCCATGATGAGGTTTCTCCCGGCCGACTCTATCCCGTTGTAGAACCTCTGCACCGGGTCTCCCTCC\n"
				+ "\n"
				+ "\n"
				+ "\n"
				+ "\n"
				+ "\n"
				+ "\n";
		FastaReader r= new FastaReader(new StringReader(input));
		Sequence s1= r.nextSequence();
		assertEquals("gi|333914|gb|K00046.1|RRV26S ross river virus 26s subgenomic rna and junction region", s1.getName());
		assertEquals("ttatttaaactaggtaaaccttta", s1.getSequence());
		Sequence s2= r.nextSequence();
		assertEquals("gi|15128154|gb|AF397202.1|AF397202 Regina ranavirus clone PstI-3.8 LCDV orf3-like protein and LCDV orf5-like protein genes, partial cds", s2.getName());
		assertEquals("ctgcaggcgcaagctagagttttccctgccctacatcaagcagtatgtgcccgtgggactggtttttaaggcccttgggaaaactgcgagggaggccgtggccatgtgtgggctggggtccttttcgggagacgaggtctcgtgcaggacggtccaccaccagagcatggcggccctcctcatggagcagcacgcgtccgcccccgaggaccccgtggcggatctggccaagcacgtccccgacggcaggtccgtctacttgaagcaggccgaaggcgcgaagcaggccgaaaacgccgatcgggcctctgtggaagaggaccgcaactccaagggctccaaggaggactacgtcaagcacgtcctggccggagaaatctttcttcacggcggagacgctgcggagcacctagggtggat", s2.getSequence());
		Sequence s3= r.nextSequence();
		assertEquals("gi|14149395|gb|AF368231.1|AF368231 Regina ranavirus clone PstI-3.0 LCDV orf 88-like protein gene, partial cds; and neurofilament triplet H1-like protein and LCDV orf 58-like protein genes, complete cds", s3.getName());
		assertEquals("gtaccgaatgaggtccctgagatccatgtctgtcacctcttccataaaactctgaacgtgagacagtctccccttatttatggatcccctccctccgtctgtcacgtagaaaccgaggacagtgcccatsgggttcttgtagtttgggtaggttatcctcaggactgaagagaggagcctagtctgctcctgcttgtttagcttgatgtgaccatccatgatgaggtttctcccggccgactctatcccgttgtagaacctctgcaccgggtctccctcc", s3.getSequence());
		Sequence s4= r.nextSequence();
		assertTrue(null == s4);
	}
}
