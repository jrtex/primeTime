package de.vahrson.seq;

import java.util.*;

/*
 * Created on 20.04.2004
 *
 * $Header: /Users/wova/laufend/cvs/Seq/de/vahrson/seq/Residue.java,v 1.1 2004/04/20 15:11:18 wova Exp $
 *  */

/**
 * @author wova
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class Residue {
	final static int FLAG_SIZE=128;
	final static byte[] NUCLEOTIDES= { 'a', 'c', 'g', 't', 'u' };
	final static boolean[] NUC_FLAGS= new boolean[FLAG_SIZE];

	final static byte[] NUCLEOTIDES_IUB=
		{ 'a', 'b', 'c', 'd', 'g', 'h', 'i', 'k', 'm', 'n', 'r', 's', 't', 'u', 'v', 'w', 'y' };
	final static boolean[] NUC_IUB_FLAGS= new boolean[FLAG_SIZE];

	final static byte[] AMINO_ACIDS=
		{ 'a', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'k', 'l', 'm', 'n', 'p', 'q', 'r', 's', 't', 'v', 'w', 'x', 'y' };
	final static boolean[] AA_FLAGS= new boolean[FLAG_SIZE];

	final static boolean[] RESIDUE_FLAGS= new boolean[FLAG_SIZE];

	static {
		init();
	}

	private static void init() {
		initFlags(NUCLEOTIDES, NUC_FLAGS);
		initFlags(NUCLEOTIDES_IUB, NUC_IUB_FLAGS);
		initFlags(AMINO_ACIDS, AA_FLAGS);

		initFlags(AMINO_ACIDS, RESIDUE_FLAGS);
		for (int i= 0; i < NUCLEOTIDES_IUB.length; i++) {
			RESIDUE_FLAGS[NUCLEOTIDES_IUB[i]]= true;
		}

	}

	private static void initFlags(byte[] residues, boolean[] flags) {
		Arrays.fill(flags, false);
		for (int i= 0; i < residues.length; i++) {
			flags[residues[i]]= true;
		}
	}

	public static boolean isResidue(char c) {
		return RESIDUE_FLAGS[0x7f & Character.toLowerCase(c)];
	}
}
