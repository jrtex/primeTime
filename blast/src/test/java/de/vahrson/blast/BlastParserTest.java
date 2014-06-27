/*
 * Created on 27.04.2004
 *
 * $Header: /Users/wova/laufend/cvs/UrlBlast/de/vahrson/blast/BlastParserTest.java,v 1.2 2004/10/19 21:01:31 wova Exp $
 *  */
package de.vahrson.blast;

import java.io.*;

import junit.framework.TestCase;

/**
 * @author wova
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class BlastParserTest extends TestCase {

	/**
	 * 
	 */
	public BlastParserTest() {
		super();
	}

	public void testParse() throws Exception {
		BufferedReader r= new BufferedReader(new StringReader(R1));
		BlastParser p= new BlastParser(r);

		BlastResult x= p.parse();
		assertTrue(x.isParsedSuccessfully());
		assertEquals(4, x.getSubjects().size());
		BlastResult.Subject s= (BlastResult.Subject) x.getSubjects().get(0);
		assertEquals("gi|15858957|emb|AL590503.11|", s.getId());
		assertEquals(
			"Mouse DNA sequence from clone RP23-128M23 on chromosome 13, complete sequence",
			s.getDescription());
		assertEquals(169547, s.getLength());

		assertEquals(1, s.getAlignments().size());
		BlastResult.Alignment a= (BlastResult.Alignment) s.getAlignments().get(0);
		assertEquals(238, a.getScoreBits(), 0.01);
		assertEquals(2e-60, a.getExpect(), 1e-60);
		assertEquals(120, a.getIdentities());
		assertEquals(120, a.getAlignmentLength());
		assertTrue(a.isQueryStrand());
		assertTrue(!a.isSubjectStrand());
		assertEquals(1, a.getQueryStartPosition());
		assertEquals(120, a.getQueryEndPosition());
		assertEquals(131279, a.getSubjectStartPosition());
		assertEquals(131160, a.getSubjectEndPosition());

		String ref=
			"gtgccgtatcctgttgggacagtcaccgcttcttccttgcttctgcatgtgccagaaaactcccgggagctcaagatgtctgtccaactgaggcttggtctttgtgcttctgggagtggc";
		assertEquals(ref, a.getQuerySequence());
		assertEquals(ref, a.getSubjectSequence());

		// 4th subject
		s= (BlastResult.Subject) x.getSubjects().get(3);
		assertEquals("gi|28209796|gb|AC122492.3|", s.getId());
		assertEquals("Mus musculus BAC clone RP24-390N7 from chromosome 5, complete sequence", s.getDescription());
		assertEquals(196275, s.getLength());

		assertEquals(2, s.getAlignments().size());
			// alignment 1
		a= (BlastResult.Alignment) s.getAlignments().get(0);
		assertEquals(38.2, a.getScoreBits(), 0.01);
		assertEquals(3.4, a.getExpect(), 0.01);
		assertEquals(19, a.getIdentities());
		assertEquals(19, a.getAlignmentLength());
		assertTrue(a.isQueryStrand());
		assertTrue(!a.isSubjectStrand());
		assertEquals(29, a.getQueryStartPosition());
		assertEquals(47, a.getQueryEndPosition());
		assertEquals(106299, a.getSubjectStartPosition());
		assertEquals(106281, a.getSubjectEndPosition());

		ref= "cttcttccttgcttctgca";
		assertEquals(ref, a.getQuerySequence());
		assertEquals(ref, a.getSubjectSequence());
			// alignment 2
		a= (BlastResult.Alignment) s.getAlignments().get(1);
		assertEquals(35.2, a.getScoreBits(), 0.01);
		assertEquals(3.3, a.getExpect(), 0.01);
		assertEquals(19, a.getIdentities());
		assertEquals(19, a.getAlignmentLength());
		assertTrue(a.isQueryStrand());
		assertTrue(!a.isSubjectStrand());
		assertEquals(615, a.getQueryStartPosition());
		assertEquals(634, a.getQueryEndPosition());
		assertEquals(8008, a.getSubjectStartPosition());
		assertEquals(8027, a.getSubjectEndPosition());

		ref= "cttcttccttgcttctgca";
		assertEquals(ref, a.getQuerySequence());
		assertEquals(ref, a.getSubjectSequence());

	}

	static final String R1=
		"BLASTN 2.2.8 [Jan-05-2004]\n"
			+ "\n"
			+ "Reference: Altschul, Stephen F., Thomas L. Madden, Alejandro A. Schaffer, \n"
			+ "Jinghui Zhang, Zheng Zhang, Webb Miller, and David J. Lipman (1997), \n"
			+ "Gapped BLAST and PSI-BLAST: a new generation of protein database search\n"
			+ "programs,  Nucleic Acids Res. 25:3389-3402.\n"
			+ "RID: 1083051143-31065-199474896562.BLASTQ3\n"
			+ "Query= xxx\n"
			+ "         (120 letters)\n"
			+ "\n"
			+ "Database: All GenBank+EMBL+DDBJ+PDB sequences (but no EST, STS,\n"
			+ "GSS,environmental samples or phase 0, 1 or 2 HTGS sequences) \n"
			+ "           2,200,567 sequences; 10,470,868,853 total letters\n"
			+ "\n"
			+ "\n"
			+ "\n"
			+ "                                                                 Score    E\n"
			+ "Sequences producing significant alignments:                      (bits) Value\n"
			+ "\n"
			+ "gi|15858957|emb|AL590503.11| Mouse DNA sequence from clone RP23-...   238   2e-60\n"
			+ "gi|26098533|dbj|AK079481.1| Mus musculus 16 days neonate thymus ...   238   2e-60\n"
			+ "gi|26086004|dbj|AK037685.1| Mus musculus 16 days neonate thymus ...   238   2e-60\n"
			+ "gi|28209796|gb|AC122492.3| Mus musculus BAC clone RP24-390N7 fro...    38   3.4  \n"
			+ "\n"
			+ "ALIGNMENTS\n"
			+ ">gi|15858957|emb|AL590503.11| Mouse DNA sequence from clone RP23-128M23 on chromosome 13, complete\n"
			+ "              sequence\n"
			+ "          Length = 169547\n"
			+ "\n"
			+ " Score =  238 bits (120), Expect = 2e-60\n"
			+ " Identities = 120/120 (100%)\n"
			+ " Strand = Plus / Minus\n"
			+ "\n"
			+ "                                                                          \n"
			+ "Query: 1      gtgccgtatcctgttgggacagtcaccgcttcttccttgcttctgcatgtgccagaaaac 60\n"
			+ "              ||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||\n"
			+ "Sbjct: 131279 gtgccgtatcctgttgggacagtcaccgcttcttccttgcttctgcatgtgccagaaaac 131220\n"
			+ "\n"
			+ "                                                                          \n"
			+ "Query: 61     tcccgggagctcaagatgtctgtccaactgaggcttggtctttgtgcttctgggagtggc 120\n"
			+ "              ||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||\n"
			+ "Sbjct: 131219 tcccgggagctcaagatgtctgtccaactgaggcttggtctttgtgcttctgggagtggc 131160\n"
			+ "\n"
			+ "\n"
			+ ">gi|26098533|dbj|AK079481.1| Mus musculus 16 days neonate thymus cDNA, RIKEN full-length enriched\n"
			+ "            library, clone:A130022D17 product:unknown EST, full\n"
			+ "            insert sequence\n"
			+ "          Length = 1278\n"
			+ "\n"
			+ " Score =  238 bits (120), Expect = 2e-60\n"
			+ " Identities = 120/120 (100%)\n"
			+ " Strand = Plus / Plus\n"
			+ "\n"
			+ "                                                                        \n"
			+ "Query: 1    gtgccgtatcctgttgggacagtcaccgcttcttccttgcttctgcatgtgccagaaaac 60\n"
			+ "            ||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||\n"
			+ "Sbjct: 970  gtgccgtatcctgttgggacagtcaccgcttcttccttgcttctgcatgtgccagaaaac 1029\n"
			+ "\n"
			+ "                                                                        \n"
			+ "Query: 61   tcccgggagctcaagatgtctgtccaactgaggcttggtctttgtgcttctgggagtggc 120\n"
			+ "            ||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||\n"
			+ "Sbjct: 1030 tcccgggagctcaagatgtctgtccaactgaggcttggtctttgtgcttctgggagtggc 1089\n"
			+ "\n"
			+ "\n"
			+ ">gi|26086004|dbj|AK037685.1| Mus musculus 16 days neonate thymus cDNA, RIKEN full-length\n"
			+ "           enriched library, clone:A130037H21 product:unknown EST,\n"
			+ "           full insert sequence\n"
			+ "          Length = 2574\n"
			+ "\n"
			+ " Score =  238 bits (120), Expect = 2e-60\n"
			+ " Identities = 120/120 (100%)\n"
			+ " Strand = Plus / Plus\n"
			+ "\n"
			+ "                                                                       \n"
			+ "Query: 1   gtgccgtatcctgttgggacagtcaccgcttcttccttgcttctgcatgtgccagaaaac 60\n"
			+ "           ||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||\n"
			+ "Sbjct: 121 gtgccgtatcctgttgggacagtcaccgcttcttccttgcttctgcatgtgccagaaaac 180\n"
			+ "\n"
			+ "                                                                       \n"
			+ "Query: 61  tcccgggagctcaagatgtctgtccaactgaggcttggtctttgtgcttctgggagtggc 120\n"
			+ "           ||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||\n"
			+ "Sbjct: 181 tcccgggagctcaagatgtctgtccaactgaggcttggtctttgtgcttctgggagtggc 240\n"
			+ "\n"
			+ "\n"
			+ ">gi|28209796|gb|AC122492.3| Mus musculus BAC clone RP24-390N7 from chromosome 5, complete sequence\n"
			+ "          Length = 196275\n"
			+ "\n"
			+ " Score = 38.2 bits (19), Expect = 3.4\n"
			+ " Identities = 19/19 (100%)\n"
			+ " Strand = Plus / Minus\n"
			+ "\n"
			+ "                                 \n"
			+ "Query: 29     cttcttccttgcttctgca 47\n"
			+ "              |||||||||||||||||||\n"
			+ "Sbjct: 106299 cttcttccttgcttctgca 106281\n"
			+ "\n"
			+ "\n"
			+ " Score = 35.2 bits (19), Expect = 3.3\n"
			+ " Identities = 19/19 (100%)\n"
			+ " Strand = Plus / Minus\n"
			+ "\n"
			+ "                                 \n"
			+ "Query: 615    cttcttccttgcttctgca 634\n"
			+ "              |||||||||||||||||||\n"
			+ "Sbjct: 8008   cttcttccttgcttctgca 8027\n"
			+ "\n"
			+ "\n"
			+ "\n"
			+ "\n"
			+ "Lambda     K      H\n"
			+ "    1.37    0.711     1.31 \n"
			+ "\n"
			+ "Gapped\n"
			+ "Lambda     K      H\n"
			+ "    1.37    0.711     1.31 \n"
			+ "\n"
			+ "Gap Penalties: Existence: 5, Extension: 2\n"
			+ "Number of Hits to DB: 1,307,011\n"
			+ "Number of Sequences: 2200567\n"
			+ "Number of extensions: 4586\n"
			+ "Number of successful extensions: 5\n"
			+ "Number of sequences better than 10.0: 0\n"
			+ "Number of HSP's better than 10.0 without gapping: 0\n"
			+ "Number of HSP's successfully gapped in prelim test: 5\n"
			+ "length of query: 120\n"
			+ "length of database: 10,470,868,853\n"
			+ "A: 0\n"
			+ "X1: 11 (20.0 bits)\n"
			+ "X2: 15 (30.0 bits)\n"
			+ "X3: 25 (50.0 bits)\n"
			+ "S1: 12 (25.0 bits)\n"
			+ "\n"
			+ "\n"
			+ "\n"
			+ "\n"
			+ "\n";
}
