/*
 * Created on 13.02.2005 
 *
 * $Header: /Users/wova/laufend/cvs/Seq/de/vahrson/seq/parser/FeatureParserTest.java,v 1.2 2005/02/26 14:46:54 wova Exp $
 */
package de.vahrson.seq.parser;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.*;

import de.vahrson.seq.*;

import junit.framework.TestCase;

/**
 * @author wova
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class FeatureParserTest extends TestCase {
    public void testEMBL() throws Exception {
        String ref = initEMBLRef();
        driveTest(ref);

    }

    public void testGB() throws Exception {
        String ref = initGBRef();
        driveTest(ref);

    }

    /**
	 * @param ref
	 * @throws Exception
	 */
	private void driveTest(String ref) throws Exception {
		StringReader sr = new StringReader(ref);
        FeatureParser parser = new FeatureParser(sr);
        List features = parser.parse();
        assertEquals(4, features.size());

        Feature f = (Feature) features.get(0);
        //        "FT promoter complement(169546)\n" +
        //        "FT /note=\"TATA: TACATAAGC EDL1 promoter before BNLF1 gives\n" +
        //        "FT 2.5kb latent RNA (LMP)\"\n" +
        assertEquals("promoter", f.getKey());
        List regions = f.getRegions();
        assertEquals(1, regions.size());
        List qualifiers = f.getQualifiers();
        assertEquals(1, qualifiers.size());
        assertEquals("/note=\"TATA: TACATAAGC EDL1 promoter before BNLF1 gives\n2.5kb latent RNA (LMP)\"", qualifiers
                .get(0));

        f = (Feature) features.get(1);
        //        "FT CDS complement(167304..167486)\n" +
        //        "FT /db_xref=\"UniProt/TrEMBL:Q04361\"\n" +
        //        "FT /note=\"BNLF2a reading frame\"\n" +
        //        "FT /protein_id=\"CAA24812.1\"\n" +
        //        "FT /translation=\"MVHVLERALLEQQSSACGLPGSSTETRPSHPCPEDPDVSRLRLLL\n" +
        //        "FT VVLCVLFGLLCLLLI\"\n" +
        assertEquals("CDS", f.getKey());
        regions = f.getRegions();
        assertEquals(1, regions.size());
        qualifiers = f.getQualifiers();
        assertEquals(4, qualifiers.size());
        assertEquals("/db_xref=\"UniProt/TrEMBL:Q04361\"", qualifiers.get(0));
        assertEquals("/note=\"BNLF2a reading frame\"", qualifiers.get(1));
        assertEquals("/protein_id=\"CAA24812.1\"", qualifiers.get(2));
        assertEquals("/translation=\"MVHVLERALLEQQSSACGLPGSSTETRPSHPCPEDPDVSRLRLLL\nVVLCVLFGLLCLLLI\"", qualifiers
                .get(3));

        f = (Feature) features.get(2);
        //      "FT misc_feature complement(164851)\n" +
        //      "FT /note=\"polyA signal: AATAAA\"\n" +
        assertEquals("misc_feature", f.getKey());
        regions = f.getRegions();
        assertEquals(1, regions.size());
        qualifiers = f.getQualifiers();
        assertEquals(1, qualifiers.size());
        assertEquals("/note=\"polyA signal: AATAAA\"", qualifiers.get(0));

        f = (Feature) features.get(3);
        //    "FT CDS complement(161384..164770)\n" +
        //    "FT /db_xref=\"GOA:P03227\"\n" +
        //    "FT /db_xref=\"UniProt/Swiss-Prot:P03227\"\n" +
        //    "FT /note=\"BALF2 early reading frame, homologous to RF 29 VZV\n" +
        //    "FT and major DNA binding protein HSV. 3.9kb early RNA\"\n" +
        //    "FT /protein_id=\"CAA24808.1\"\n" +
        //    "FT /translation=\"MQGAQTSEDNLGSQSQPGPCGYIYFYPLATYPLREVATLGTGYAG\n" +
        //    "FT HRCLTVPLLCGITVEPGFSINVKALHRRPDPNCGLLRATSYHRDIYVFHNAHMVPPIFE\n" +
        //		....
        //    "FT GQGSGGRRKRRLATVLPGLEV\"\n";
        assertEquals("CDS", f.getKey());
        regions = f.getRegions();
        assertEquals(1, regions.size());
        qualifiers = f.getQualifiers();
        assertEquals(5, qualifiers.size());
        assertEquals("/translation=\"MQGAQTSEDNLGSQSQPGPCGYIYFYPLATYPLREVATLGTGYAG\n" + 
                "HRCLTVPLLCGITVEPGFSINVKALHRRPDPNCGLLRATSYHRDIYVFHNAHMVPPIFE\n" + 
                "GPGLEALCGETREVFGYDAYSALPRESSKPGDFFPEGLDPSAYLGAVAITEAFKERLYS\n" + 
                "GNLVAIPSLKQEVAVGQSASVRVPLYDKEVFPEGVPQLRQFYNSDLSRCMHEALYTGLA\n" + 
                "QALRVRRVGKLVELLEKQSLQDQAKVAKVAPLKEFPASTISHPDSGALMIVDSAACELA\n" + 
                "VSYAPAMLEASHETPASLNYDSWPLFADCEGPEARVAALHRYNASLAPHVSTQIFATNS\n" + 
                "VLYVSGVSKSTGQGKESLFNSFYMTHGLGTLQEGTWDPCRRPCFSGWGGPDVTGTNGPG\n" + 
                "NYAVEHLVYAASFSPNLLARYAYYLQFCQGQKSSLTPVPETGSYVAGAAASPMCSLCEG\n" + 
                "RAPAVCLNTLFFRLRDRFPPVMSTQRRDPYVISGASGSYNETDFLGNFLNFIDKEDDGQ\n" + 
                "RPDDEPRYTYWQLNQNLLERLSRLGIDAEGKLEKEPHGPRDFVKMFKDVDAAVDAEVVQ\n" + 
                "FMNSMAKNNITYKDLVKSCYHVMQYSCNPFAQPACPIFTQLFYRSLLTILQDISLPICM\n" + 
                "CYENDNPGLGQSPPEWLKGHYQTLCTNFRSLAIDKGVLTAKEAKVVHGEPTCDLPDLDA\n" +
                "ALQGRVYGRRLPVRMSKVLMLCPRNIKIKNRVVFTGENAALQNSFIKSTTRRENYIING\n" + 
                "PYMKFLNTYHKTLFPDTKLSSLYLWHNFSRRRSVPVPSGASAEEYSDLALFVDGGSRAH\n" + 
                "EESNVIDVVPGNLVTYAKQRLNNAILKACGQTQFYISLIQGLVPRTQSVPARDYPHVLG\n" + 
                "TRAVESAAAYAEATSSLTATTVVCAATDCLSQVCKARPVVTLPVTINKYTGVNGNNQIF\n" + 
                "QAGNLGYFMGRGVDRNLLQAPGAGLRKQAGGSSMRKKFVFATPTLGLTVKRRTQAATTY\n" + 
                "EIENIRAGLEAIISQKQEEDCVFDVVCNLVDAMGEACASLTRDDAEYLLGRFSVLADSV\n" + 
                "LETLATIASSGIEWTAEAARDFLEGVWGGPGAAQDNFISVAEPVSTASQASAGLLLGGG\n" + 
                "GQGSGGRRKRRLATVLPGLEV\"", qualifiers.get(4));
	}

	/**
     * @return
     */
    private String initEMBLRef() {
        String ref = "FT   promoter        complement(169546)\n"
                + "FT                   /note=\"TATA: TACATAAGC EDL1 promoter before BNLF1 gives\n"
                + "FT                   2.5kb latent RNA (LMP)\"\n"
                + "FT   CDS             complement(167304..167486)\n"
                + "FT                   /db_xref=\"UniProt/TrEMBL:Q04361\"\n"
                + "FT                   /note=\"BNLF2a reading frame\"\n"
                + "FT                   /protein_id=\"CAA24812.1\"\n"
                + "FT                   /translation=\"MVHVLERALLEQQSSACGLPGSSTETRPSHPCPEDPDVSRLRLLL\n"
                + "FT                   VVLCVLFGLLCLLLI\"\n" + "FT   misc_feature    complement(164851)\n"
                + "FT                   /note=\"polyA signal: AATAAA\"\n"
                + "FT   CDS             complement(161384..164770)\n"
                + "FT                   /db_xref=\"GOA:P03227\"\n"
                + "FT                   /db_xref=\"UniProt/Swiss-Prot:P03227\"\n"
                + "FT                   /note=\"BALF2 early reading frame, homologous to RF 29 VZV\n"
                + "FT                   and major DNA binding protein HSV. 3.9kb early RNA\"\n"
                + "FT                   /protein_id=\"CAA24808.1\"\n"
                + "FT                   /translation=\"MQGAQTSEDNLGSQSQPGPCGYIYFYPLATYPLREVATLGTGYAG\n"
                + "FT                   HRCLTVPLLCGITVEPGFSINVKALHRRPDPNCGLLRATSYHRDIYVFHNAHMVPPIFE\n"
                + "FT                   GPGLEALCGETREVFGYDAYSALPRESSKPGDFFPEGLDPSAYLGAVAITEAFKERLYS\n"
                + "FT                   GNLVAIPSLKQEVAVGQSASVRVPLYDKEVFPEGVPQLRQFYNSDLSRCMHEALYTGLA\n"
                + "FT                   QALRVRRVGKLVELLEKQSLQDQAKVAKVAPLKEFPASTISHPDSGALMIVDSAACELA\n"
                + "FT                   VSYAPAMLEASHETPASLNYDSWPLFADCEGPEARVAALHRYNASLAPHVSTQIFATNS\n"
                + "FT                   VLYVSGVSKSTGQGKESLFNSFYMTHGLGTLQEGTWDPCRRPCFSGWGGPDVTGTNGPG\n"
                + "FT                   NYAVEHLVYAASFSPNLLARYAYYLQFCQGQKSSLTPVPETGSYVAGAAASPMCSLCEG\n"
                + "FT                   RAPAVCLNTLFFRLRDRFPPVMSTQRRDPYVISGASGSYNETDFLGNFLNFIDKEDDGQ\n"
                + "FT                   RPDDEPRYTYWQLNQNLLERLSRLGIDAEGKLEKEPHGPRDFVKMFKDVDAAVDAEVVQ\n"
                + "FT                   FMNSMAKNNITYKDLVKSCYHVMQYSCNPFAQPACPIFTQLFYRSLLTILQDISLPICM\n"
                + "FT                   CYENDNPGLGQSPPEWLKGHYQTLCTNFRSLAIDKGVLTAKEAKVVHGEPTCDLPDLDA\n"
                + "FT                   ALQGRVYGRRLPVRMSKVLMLCPRNIKIKNRVVFTGENAALQNSFIKSTTRRENYIING\n"
                + "FT                   PYMKFLNTYHKTLFPDTKLSSLYLWHNFSRRRSVPVPSGASAEEYSDLALFVDGGSRAH\n"
                + "FT                   EESNVIDVVPGNLVTYAKQRLNNAILKACGQTQFYISLIQGLVPRTQSVPARDYPHVLG\n"
                + "FT                   TRAVESAAAYAEATSSLTATTVVCAATDCLSQVCKARPVVTLPVTINKYTGVNGNNQIF\n"
                + "FT                   QAGNLGYFMGRGVDRNLLQAPGAGLRKQAGGSSMRKKFVFATPTLGLTVKRRTQAATTY\n"
                + "FT                   EIENIRAGLEAIISQKQEEDCVFDVVCNLVDAMGEACASLTRDDAEYLLGRFSVLADSV\n"
                + "FT                   LETLATIASSGIEWTAEAARDFLEGVWGGPGAAQDNFISVAEPVSTASQASAGLLLGGG\n"
                + "FT                   GQGSGGRRKRRLATVLPGLEV\"\n";
        return ref;
    }
    
    private String initGBRef() {
        String ref = "     promoter        complement(169546)\n"
                + "                     /note=\"TATA: TACATAAGC EDL1 promoter before BNLF1 gives\n"
                + "                     2.5kb latent RNA (LMP)\"\n"
                + "     CDS             complement(167304..167486)\n"
                + "                     /db_xref=\"UniProt/TrEMBL:Q04361\"\n"
                + "                     /note=\"BNLF2a reading frame\"\n"
                + "                     /protein_id=\"CAA24812.1\"\n"
                + "                     /translation=\"MVHVLERALLEQQSSACGLPGSSTETRPSHPCPEDPDVSRLRLLL\n"
                + "                     VVLCVLFGLLCLLLI\"\n" + "     misc_feature    complement(164851)\n"
                + "                     /note=\"polyA signal: AATAAA\"\n"
                + "     CDS             complement(161384..164770)\n"
                + "                     /db_xref=\"GOA:P03227\"\n"
                + "                     /db_xref=\"UniProt/Swiss-Prot:P03227\"\n"
                + "                     /note=\"BALF2 early reading frame, homologous to RF 29 VZV\n"
                + "                     and major DNA binding protein HSV. 3.9kb early RNA\"\n"
                + "                     /protein_id=\"CAA24808.1\"\n"
                + "                     /translation=\"MQGAQTSEDNLGSQSQPGPCGYIYFYPLATYPLREVATLGTGYAG\n"
                + "                     HRCLTVPLLCGITVEPGFSINVKALHRRPDPNCGLLRATSYHRDIYVFHNAHMVPPIFE\n"
                + "                     GPGLEALCGETREVFGYDAYSALPRESSKPGDFFPEGLDPSAYLGAVAITEAFKERLYS\n"
                + "                     GNLVAIPSLKQEVAVGQSASVRVPLYDKEVFPEGVPQLRQFYNSDLSRCMHEALYTGLA\n"
                + "                     QALRVRRVGKLVELLEKQSLQDQAKVAKVAPLKEFPASTISHPDSGALMIVDSAACELA\n"
                + "                     VSYAPAMLEASHETPASLNYDSWPLFADCEGPEARVAALHRYNASLAPHVSTQIFATNS\n"
                + "                     VLYVSGVSKSTGQGKESLFNSFYMTHGLGTLQEGTWDPCRRPCFSGWGGPDVTGTNGPG\n"
                + "                     NYAVEHLVYAASFSPNLLARYAYYLQFCQGQKSSLTPVPETGSYVAGAAASPMCSLCEG\n"
                + "                     RAPAVCLNTLFFRLRDRFPPVMSTQRRDPYVISGASGSYNETDFLGNFLNFIDKEDDGQ\n"
                + "                     RPDDEPRYTYWQLNQNLLERLSRLGIDAEGKLEKEPHGPRDFVKMFKDVDAAVDAEVVQ\n"
                + "                     FMNSMAKNNITYKDLVKSCYHVMQYSCNPFAQPACPIFTQLFYRSLLTILQDISLPICM\n"
                + "                     CYENDNPGLGQSPPEWLKGHYQTLCTNFRSLAIDKGVLTAKEAKVVHGEPTCDLPDLDA\n"
                + "                     ALQGRVYGRRLPVRMSKVLMLCPRNIKIKNRVVFTGENAALQNSFIKSTTRRENYIING\n"
                + "                     PYMKFLNTYHKTLFPDTKLSSLYLWHNFSRRRSVPVPSGASAEEYSDLALFVDGGSRAH\n"
                + "                     EESNVIDVVPGNLVTYAKQRLNNAILKACGQTQFYISLIQGLVPRTQSVPARDYPHVLG\n"
                + "                     TRAVESAAAYAEATSSLTATTVVCAATDCLSQVCKARPVVTLPVTINKYTGVNGNNQIF\n"
                + "                     QAGNLGYFMGRGVDRNLLQAPGAGLRKQAGGSSMRKKFVFATPTLGLTVKRRTQAATTY\n"
                + "                     EIENIRAGLEAIISQKQEEDCVFDVVCNLVDAMGEACASLTRDDAEYLLGRFSVLADSV\n"
                + "                     LETLATIASSGIEWTAEAARDFLEGVWGGPGAAQDNFISVAEPVSTASQASAGLLLGGG\n"
                + "                     GQGSGGRRKRRLATVLPGLEV\"\n";
        return ref;
    }
    
    public void testHSMYCCFeatures() throws Exception{
    	String src= 
    		"FT   source          1..10996\n" + 
			"FT                   /chromosome=\"8\"\n" + 
			"FT                   /db_xref=\"taxon:9606\"\n" + 
			"FT                   /mol_type=\"genomic DNA\"\n" + 
			"FT                   /organism=\"Homo sapiens\"\n" + 
			"FT                   /map=\"8q24\"\n" + 
			"FT                   /clone=\"lambda-M1; pUC9-myc\"\n" + 
			"FT                   /clone_lib=\"Lawn et al.\"\n" + 
			"FT   CDS             2304..2870\n" + 
			"FT                   /db_xref=\"UniProt/TrEMBL:Q16591\"\n" + 
			"FT                   /note=\"ORF1\"\n" + 
			"FT                   /product=\"hypothetical protein\"\n" + 
			"FT                   /function=\"unknown\"\n" + 
			"FT                   /protein_id=\"CAA25105.1\"\n" + 
			"FT                   /translation=\"MRGSGRLRTPELCCSRPPPPGPGRPWLPSCLEKGRASQRLGGKKN\n" + 
			"FT                   GGRDRAEYKSRFSGLYLTRCSNSSERQRERAGGRLGWKSRASRAALRASWEGRSGANRG\n" + 
			"FT                   LRLWPSPPADPPASGPQPLPHPRNFAHSSGRALCTGTYNTRARTRLSRRGEAILPIWGH\n" + 
			"FT                   FPAAARTRFSERLSLQLLRRWIFFG\"\n" + 
			"FT   exon            2327..2881\n" + 
			"FT                   /note=\"alternative splicing\"\n" + 
			"FT                   /number=1\n" + 
			"FT                   /gene=\"MYC\"\n" + 
			"FT   exon            2502..2881\n" + 
			"FT                   /note=\"alternative splicing\"\n" + 
			"FT                   /number=1\n" + 
			"FT                   /gene=\"MYC\"\n" + 
			"FT   mRNA            join(2327..2881,4506..5277,6654..>7216)\n" + 
			"FT                   /note=\"alternative splicing\"\n" + 
			"FT                   /gene=\"MYC\"\n" + 
			"FT   mRNA            join(2502..2881,4506..5277,6654..>7216)\n" + 
			"FT                   /note=\"alternative splicing\"\n" + 
			"FT                   /gene=\"MYC\"\n" + 
			"FT   CDS             join(4521..5277,6654..7216)\n" + 
			"FT                   /db_xref=\"GOA:P01106\"\n" + 
			"FT                   /db_xref=\"Genew:7553\"\n" + 
			"FT                   /db_xref=\"UniProt/Swiss-Prot:P01106\"\n" + 
			"FT                   /product=\"48K protein\"\n" + 
			"FT                   /gene=\"MYC\"\n" + 
			"FT                   /protein_id=\"CAA25106.1\"\n" + 
			"FT                   /translation=\"MPLNVSFTNRNYDLDYDSVQPYFYCDEEENFYQQQQQSELQPPAP\n" + 
			"FT                   SEDIWKKFELLPTPPLSPSRRSGLCSPSYVAVTPFSLRGDNDGGGGSFSTADQLEMVTE\n" + 
			"FT                   LLGGDMVNQSFICDPDDETFIKNIIIQDCMWSGFSAAAKLVSEKLASYQAARKDSGSPN\n" + 
			"FT                   PARGHSVCSTSSLYLQDLSAAASECIDPSVVFPYPLNDSSSPKSCASQDSSAFSPSSDS\n" + 
			"FT                   LLSSTESSPQGSPEPLVLHEETPPTTSSDSEEEQEDEEEIDVVSVEKRQAPGKRSESGS\n" + 
			"FT                   PSAGGHSKPPHSPLVLKRCHVSTHQHNYAAPPSTRKDYPAAKRVKLDSVRVLRQISNNR\n" + 
			"FT                   KCTSPRSSDTEENVKRRTHNVLERQRRNELKRSFFALRDQIPELENNEKAPKVVILKKA\n" + 
			"FT                   TAYILSVQAEEQKLISEEDLLRKRREQLKHKLEQLRNSCA\"\n" + 
			"FT   exon            4506..5277\n" + 
			"FT                   /number=2\n" + 
			"FT                   /gene=\"MYC\"\n" + 
			"FT   repeat_region   6299..6499\n" + 
			"FT                   /note=\"Alu-like repeat\"\n" + 
			"FT                   /rpt_type=DISPERSED\n" + 
			"FT   exon            6654..7657\n" + 
			"FT                   /number=3\n" + 
			"FT                   /gene=\"MYC\"\n" + 
			"FT   polyA_signal    7511..7516\n" + 
			"FT                   /note=\"pot. polyA signal\"\n" + 
			"FT                   /gene=\"MYC\"\n" + 
			"FT   polyA_signal    7652..7657\n" + 
			"FT                   /note=\"pot. polyA signal\"\n" + 
			"FT                   /gene=\"MYC\"\n" + 
			"\n";
		StringReader sr = new StringReader(src);
        FeatureParser parser = new FeatureParser(sr);
        List features = parser.parse();

    }
    
    public void testFullHSMYCC() throws Exception {
    	String src=
    		"\n" + 
			"EBI Home Page 	\n" + 
			"Image\n" + 
			"	Get  		 for  		  		 ? \n" + 
			"	 Site search  		  		 ? \n" + 
			"EBI Home Page 	Image\n" + 
			"Image\n" + 
			"							\n" + 
			"DATABASE BROWSING\n" + 
			"Image\n" + 
			"Image\n" + 
			"Image 	Image\n" + 
			"ImageImage 	\n" + 
			" EBI  	 Dbfetch\n" + 
			"Image\n" + 
			"\n" + 
			"ID   HSMYCC     standard; genomic DNA; HUM; 10996 BP.\n" + 
			"XX\n" + 
			"AC   X00364; J00120; K01908; M23541; V00501;\n" + 
			"XX\n" + 
			"SV   X00364.2\n" + 
			"XX\n" + 
			"DT   07-JUN-1987 (Rel. 12, Created)\n" + 
			"DT   03-JUN-2002 (Rel. 72, Last updated, Version 17)\n" + 
			"XX\n" + 
			"DE   Homo sapiens MYC gene for c-myc proto-oncogene and ORF1\n" + 
			"XX\n" + 
			"KW   Alu repeat; c-myc oncogene; myc gene; repeat region.\n" + 
			"XX\n" + 
			"OS   Homo sapiens (human)\n" + 
			"OC   Eukaryota; Metazoa; Chordata; Craniata; Vertebrata; Euteleostomi; Mammalia;\n" + 
			"OC   Eutheria; Primates; Catarrhini; Hominidae; Homo.\n" + 
			"XX\n" + 
			"RN   [1]\n" + 
			"RP   1-8082\n" + 
			"RX   MEDLINE; 84182501.\n" + 
			"RX   PUBMED; 6714223.\n" + 
			"RA   Gazin C., De Dinechin S.D., Hampe A., Masson J., Martin P., Stehelin D.,\n" + 
			"RA   Galibert F.;\n" + 
			"RT   \"Nucleotide sequence of the human c-myc locus: provocative open reading\n" + 
			"RT   frame within the first exon\";\n" + 
			"RL   EMBO J. 3(2):383-387(1984).\n" + 
			"XX\n" + 
			"RN   [2]\n" + 
			"RP   3507-7559\n" + 
			"RX   MEDLINE; 83141777.\n" + 
			"RX   PUBMED; 6298632.\n" + 
			"RA   Colby W.W., Chen E.Y., Smith D.H., Levinson A.D.;\n" + 
			"RT   \"Identification and nucleotide sequence of a human locus homologous to the\n" + 
			"RT   v-myc oncogene of avian myelocytomatosis virus MC29\";\n" + 
			"RL   Nature 301(5902):722-725(1983).\n" + 
			"XX\n" + 
			"RN   [3]\n" + 
			"RX   MEDLINE; 84170251.\n" + 
			"RX   PUBMED; 6324175.\n" + 
			"RA   Saito H., Hayday A.C., Wiman K.G., Hayward W.S., Tonegawa S.;\n" + 
			"RT   \"Activation of the c-myc gene by translocation: a model for translational\n" + 
			"RT   control.\";\n" + 
			"RL   Proc. Natl. Acad. Sci. U.S.A. 80(24):7476-7480(1983).\n" + 
			"XX\n" + 
			"RN   [4]\n" + 
			"RX   MEDLINE; 87053865.\n" + 
			"RX   PUBMED; 2430795.\n" + 
			"RA   Gazin C., Rigolet M., Briand J.P., Van Regenmortel M.H.V., Galibert F.;\n" + 
			"RT   \"Immunochemical detection of proteins related to the human c-myc exon 1\";\n" + 
			"RL   EMBO J. 5(9):2241-2250(1986).\n" + 
			"XX\n" + 
			"RN   [5]\n" + 
			"RX   DOI; 10.1016/0378-1119(88)90131-X\n" + 
			"RX   MEDLINE; 89211899.\n" + 
			"RX   PUBMED; 3243428.\n" + 
			"RA   Guilhot S., Petridou B., Syed-Hussain S., Galibert F.;\n" + 
			"RT   \"Nucleotide sequence 3' to the human c-myc oncogene; presence of a long\n" + 
			"RT   inverted repeat.\";\n" + 
			"RL   Gene 72(1-2):105-108(1988).\n" + 
			"XX\n" + 
			"DR   EPD; EP11146; HS_MYC_1.\n" + 
			"DR   EPD; EP11148; HS_MYC_2.\n" + 
			"DR   GDB; GDB:120208.\n" + 
			"DR   GDB; GDB:678974.\n" + 
			"DR   GDB; GDB:7123817.\n" + 
			"DR   TRANSFAC; R01804; HS$CMYC_04.\n" + 
			"DR   TRANSFAC; R01916; HS$CMYC_07.\n" + 
			"DR   TRANSFAC; R04076; HS$CMYC_12.\n" + 
			"DR   TRANSFAC; T00140; T00140.\n" + 
			"XX\n" + 
			"FH   Key             Location/Qualifiers\n" + 
			"FH\n" + 
			"FT   source          1..10996\n" + 
			"FT                   /chromosome=\"8\"\n" + 
			"FT                   /db_xref=\"taxon:9606\"\n" + 
			"FT                   /mol_type=\"genomic DNA\"\n" + 
			"FT                   /organism=\"Homo sapiens\"\n" + 
			"FT                   /map=\"8q24\"\n" + 
			"FT                   /clone=\"lambda-M1; pUC9-myc\"\n" + 
			"FT                   /clone_lib=\"Lawn et al.\"\n" + 
			"FT   CDS             2304..2870\n" + 
			"FT                   /db_xref=\"UniProt/TrEMBL:Q16591\"\n" + 
			"FT                   /note=\"ORF1\"\n" + 
			"FT                   /product=\"hypothetical protein\"\n" + 
			"FT                   /function=\"unknown\"\n" + 
			"FT                   /protein_id=\"CAA25105.1\"\n" + 
			"FT                   /translation=\"MRGSGRLRTPELCCSRPPPPGPGRPWLPSCLEKGRASQRLGGKKN\n" + 
			"FT                   GGRDRAEYKSRFSGLYLTRCSNSSERQRERAGGRLGWKSRASRAALRASWEGRSGANRG\n" + 
			"FT                   LRLWPSPPADPPASGPQPLPHPRNFAHSSGRALCTGTYNTRARTRLSRRGEAILPIWGH\n" + 
			"FT                   FPAAARTRFSERLSLQLLRRWIFFG\"\n" + 
			"FT   exon            2327..2881\n" + 
			"FT                   /note=\"alternative splicing\"\n" + 
			"FT                   /number=1\n" + 
			"FT                   /gene=\"MYC\"\n" + 
			"FT   exon            2502..2881\n" + 
			"FT                   /note=\"alternative splicing\"\n" + 
			"FT                   /number=1\n" + 
			"FT                   /gene=\"MYC\"\n" + 
			"FT   mRNA            join(2327..2881,4506..5277,6654..>7216)\n" + 
			"FT                   /note=\"alternative splicing\"\n" + 
			"FT                   /gene=\"MYC\"\n" + 
			"FT   mRNA            join(2502..2881,4506..5277,6654..>7216)\n" + 
			"FT                   /note=\"alternative splicing\"\n" + 
			"FT                   /gene=\"MYC\"\n" + 
			"FT   CDS             join(4521..5277,6654..7216)\n" + 
			"FT                   /db_xref=\"GOA:P01106\"\n" + 
			"FT                   /db_xref=\"Genew:7553\"\n" + 
			"FT                   /db_xref=\"UniProt/Swiss-Prot:P01106\"\n" + 
			"FT                   /product=\"48K protein\"\n" + 
			"FT                   /gene=\"MYC\"\n" + 
			"FT                   /protein_id=\"CAA25106.1\"\n" + 
			"FT                   /translation=\"MPLNVSFTNRNYDLDYDSVQPYFYCDEEENFYQQQQQSELQPPAP\n" + 
			"FT                   SEDIWKKFELLPTPPLSPSRRSGLCSPSYVAVTPFSLRGDNDGGGGSFSTADQLEMVTE\n" + 
			"FT                   LLGGDMVNQSFICDPDDETFIKNIIIQDCMWSGFSAAAKLVSEKLASYQAARKDSGSPN\n" + 
			"FT                   PARGHSVCSTSSLYLQDLSAAASECIDPSVVFPYPLNDSSSPKSCASQDSSAFSPSSDS\n" + 
			"FT                   LLSSTESSPQGSPEPLVLHEETPPTTSSDSEEEQEDEEEIDVVSVEKRQAPGKRSESGS\n" + 
			"FT                   PSAGGHSKPPHSPLVLKRCHVSTHQHNYAAPPSTRKDYPAAKRVKLDSVRVLRQISNNR\n" + 
			"FT                   KCTSPRSSDTEENVKRRTHNVLERQRRNELKRSFFALRDQIPELENNEKAPKVVILKKA\n" + 
			"FT                   TAYILSVQAEEQKLISEEDLLRKRREQLKHKLEQLRNSCA\"\n" + 
			"FT   exon            4506..5277\n" + 
			"FT                   /number=2\n" + 
			"FT                   /gene=\"MYC\"\n" + 
			"FT   repeat_region   6299..6499\n" + 
			"FT                   /note=\"Alu-like repeat\"\n" + 
			"FT                   /rpt_type=DISPERSED\n" + 
			"FT   exon            6654..7657\n" + 
			"FT                   /number=3\n" + 
			"FT                   /gene=\"MYC\"\n" + 
			"FT   polyA_signal    7511..7516\n" + 
			"FT                   /note=\"pot. polyA signal\"\n" + 
			"FT                   /gene=\"MYC\"\n" + 
			"FT   polyA_signal    7652..7657\n" + 
			"FT                   /note=\"pot. polyA signal\"\n" + 
			"FT                   /gene=\"MYC\"\n" + 
			"XX\n" + 
			"SQ   Sequence 10996 BP; 2747 A; 2723 C; 2733 G; 2793 T; 0 other;\n" + 
			"     agcttgtttg gccgttttag ggtttgttgg aatttttttt tcgtctatgt acttgtgaat        60\n" + 
			"     tatttcacgt ttgccattac cggttctcca tagggtgatg ttcattagca gtggtgatag       120\n" + 
			"     gttaattttc accatctctt atgcggttga atagtcacct ctgaaccact ttttcctcca       180\n" + 
			"     gtaactcctc tttcttcgga ccttctgcag ccaacctgaa agaataacaa ggaggtggct       240\n" + 
			"     ggaaacttgt tttaaggaac cgcctgtcct tcccccgctg gaaaccttgc acctcggacg       300\n" + 
			"     ctcctgctcc tgcccccacc tgacccccgc cctcgttgac atccaggcgc gatgatctct       360\n" + 
			"     gctgccagta gagggcacac ttactttact ttcgcaaacc tgaacgcggg tgctgcccag       420\n" + 
			"     agagggggcg gagggaaaga cgctttgcag caaaatccag catagcgatt ggttgctccc       480\n" + 
			"     cgcgtttgcg gcaaaggcct ggaggcagga gtaatttgca atccttaaag ctgaattgtg       540\n" + 
			"     cagtgcatcg gatttggaag ctactatatt cacttaacac ttgaacgctg agctgcaaac       600\n" + 
			"     tcaacgggta ataacccatc ttgaacagcg tacatgctat acacacaccc ctttcccccg       660\n" + 
			"     aattgttttc tcttttggag gtggtggagg gagagaaaag tttacttaaa atgcctttgg       720\n" + 
			"     gtgagggacc aaggatgaga agaatgtttt ttgtttttca tgccgtggaa taacacaaaa       780\n" + 
			"     taaaaaatcc cgagggaata tacattatat attaaatata gatcatttca gggagcaaac       840\n" + 
			"     aaatcatgtg tggggctggg caactagctg agtcgaagcg taaataaaat gtgaatacac       900\n" + 
			"     gtttgcgggt tacatacagt gcactttcac tagtattcag aaaaaattgt gagtcagtga       960\n" + 
			"     actaggaaat taatgcctgg aaggcagcca aattttaatt agctcaagac tccccccccc      1020\n" + 
			"     ccccaaaaaa aggcacggaa gtaatactcc tctcctcttc tttgatcaga atcgatgcat      1080\n" + 
			"     tttttgtgca tgaccgcatt tccaataata aaaggggaaa gaggacctgg aaaggaatta      1140\n" + 
			"     aacgtccggt ttgtccgggg aggaaagagt taacggtttt tttcacaagg gtctctgctg      1200\n" + 
			"     actcccccgg ctcggtccac aagctctcca cttgcccctt ttaggaagtc cggtcccgcg      1260\n" + 
			"     gttcgggtac cccctgcccc tcccatattc tcccgtctag cacctttgat ttctcccaaa      1320\n" + 
			"     cccggcagcc cgagactgtt gcaaaccggc gccacagggc gcaaagggga tttgtctctt      1380\n" + 
			"     ctgaaacctg gctgagaaat tgggaactcc gtgtgggagg cgtgggggtg ggacggtggg      1440\n" + 
			"     gtacagactg gcagagagca ggcaacctcc ctctcgccct agcccagctc tggaacaggc      1500\n" + 
			"     agacacatct cagggctaaa cagacgcctc ccgcacgggg ccccacggaa gcctgagcag      1560\n" + 
			"     gcggggcagg aggggcggta tctgctgctt tggcagcaaa ttgggggact cagtctgggt      1620\n" + 
			"     ggaaggtatc caatccagat agctgtgcat acataatgca taatacatga ctccccccaa      1680\n" + 
			"     caaatgcaat gggagtttat tcataacgcg ctctccaagt atacgtggca atgcgttgct      1740\n" + 
			"     gggttatttt aatcattcta ggcatcgttt tcctccttat gcctctatca ttcctcccta      1800\n" + 
			"     tctacactaa catcccacgc tctgaacgcg cgcccattaa tacccttctt tcctccactc      1860\n" + 
			"     tccctgggac tcttgatcaa agcgcggccc tttccccagc cttagcgagg cgccctgcag      1920\n" + 
			"     cctggtacgc gcgtggcgtg gcggtgggcg cgcagtgcgt tctctgtgtg gagggcagct      1980\n" + 
			"     gttccgcctg cgatgattta tactcacagg acaaggatgc ggtttgtcaa acagtactgc      2040\n" + 
			"     tacggaggag cagcagagaa agggagaggg tttgagaggg agcaaaagaa aatggtaggc      2100\n" + 
			"     gcgcgtagtt aattcatgcg gctctcttac tctgtttaca tcctagagct agagtgctcg      2160\n" + 
			"     gctgcccggc tgagtctcct ccccaccttc cccaccctcc ccaccctccc cataagcgcc      2220\n" + 
			"     cctcccgggt tcccaaagca gagggcgtgg gggaaaagaa aaaagatcct ctctcgctaa      2280\n" + 
			"     tctccgccca ccggcccttt ataatgcgag ggtctggacg gctgaggacc cccgagctgt      2340\n" + 
			"     gctgctcgcg gccgccaccg ccgggccccg gccgtccctg gctcccctcc tgcctcgaga      2400\n" + 
			"     agggcagggc ttctcagagg cttggcggga aaaagaacgg agggagggat cgcgctgagt      2460\n" + 
			"     ataaaagccg gttttcgggg ctttatctaa ctcgctgtag taattccagc gagaggcaga      2520\n" + 
			"     gggagcgagc gggcggccgg ctagggtgga agagccgggc gagcagagct gcgctgcggg      2580\n" + 
			"     cgtcctggga agggagatcc ggagcgaata gggggcttcg cctctggccc agccctcccg      2640\n" + 
			"     ctgatccccc agccagcggt ccgcaaccct tgccgcatcc acgaaacttt gcccatagca      2700\n" + 
			"     gcgggcgggc actttgcact ggaacttaca acacccgagc aaggacgcga ctctcccgac      2760\n" + 
			"     gcggggaggc tattctgccc atttggggac acttccccgc cgctgccagg acccgcttct      2820\n" + 
			"     ctgaaaggct ctccttgcag ctgcttagac gctggatttt tttcgggtag tggaaaacca      2880\n" + 
			"     ggtaagcacc gaagtccact tgccttttaa tttatttttt tatcacttta atgctgagat      2940\n" + 
			"     gagtcgaatg cctaaatagg gtgtcttttc tcccattcct gcgctattga cacttttctc      3000\n" + 
			"     agagtagtta tggtaactgg ggctggggtg gggggtaatc cagaactgga tcggggtaaa      3060\n" + 
			"     gtgacttgtc aagatgggag aggagaaggc agagggaaaa cgggaatggt ttttaagact      3120\n" + 
			"     accctttcga gatttctgcc ttatgaatat attcacgctg actcccggcc ggtcggacat      3180\n" + 
			"     tcctgcttta ttgtgttaat tgctctctgg gttttggggg gctgggggtt gctttgcggt      3240\n" + 
			"     gggcagaaag ccccttgcat cctgagctcc ttggagtagg gaccgcatat cgcctgtgtg      3300\n" + 
			"     agccagatcg ctccgcagcc gctgacttgt ccccgtctcc gggagggcat ttaaatttcg      3360\n" + 
			"     gctcaccgca tttctgacag ccggagacgg acactgcggc gcgtcccgcc cgcctgtccc      3420\n" + 
			"     cgcggcgatt ccaacccgcc ctgatccttt taagaagttg gcatttggct ttttaaaaag      3480\n" + 
			"     caataataca atttaaaacc tgggtctcta gaggtgttag gacgtggtgt tgggtaggcg      3540\n" + 
			"     caggcagggg aaaagggagg cgaggatgtg tccgattctc ctggaatcgt tgacttggaa      3600\n" + 
			"     aaaccagggc gaatctccgc acccagccct gactcccctg ccgcggccgc cctcgggtgt      3660\n" + 
			"     cctcgcgccc gagatgcgga ggaactgcga ggagcggggc tctgggcggt tccagaacag      3720\n" + 
			"     ctgctaccct tggtggggtg gctccggggg aggtatcgca gcggggtctc tggcgcagtt      3780\n" + 
			"     gcatctccgt attgagtgcg aagggaggtg cccctattat tatttgacac cccccttgta      3840\n" + 
			"     tttatggagg ggtgttaaag cccgcggctg agctcgccac tccagccggc gagagaaaga      3900\n" + 
			"     agaaaagctg gcaaaaggag tgttggacgg gggcggtact gggggtgggg acgggggcgg      3960\n" + 
			"     tggagaggga aggttgggag gggctgcggt gccggcgggg gtaggagagc ggctagggcg      4020\n" + 
			"     cgagtgggaa cagccgcagc ggaggggccc cggcgcggag cggggttcac gcagccgcta      4080\n" + 
			"     gcgcccaggc gcctctcgcc ttctccttca ggtggcgcaa aactttgtgc cttggatttt      4140\n" + 
			"     ggcaaattgt tttcctcacc gccacctccc gcggcttctt aagggcgcca gggccgattt      4200\n" + 
			"     cgattcctct gccgctgcgg ggccgactcc cgggctttgc gctccgggct cccgggggag      4260\n" + 
			"     cgggggctcg gcgggcacca agccgctggt tcactaagtg cgtctccgag atagcagggg      4320\n" + 
			"     actgtccaaa gggggtgaaa gggtgctccc tttattcccc caccaagacc acccagccgc      4380\n" + 
			"     tttaggggat agctctgcaa ggggagaggt tcgggactgt ggcgcgcact gcgcgctgcg      4440\n" + 
			"     ccaggtttcc gcaccaagac ccctttaact caagactgcc tcccgctttg tgtgccccgc      4500\n" + 
			"     tccagcagcc tcccgcgacg atgcccctca acgttagctt caccaacagg aactatgacc      4560\n" + 
			"     tcgactacga ctcggtgcag ccgtatttct actgcgacga ggaggagaac ttctaccagc      4620\n" + 
			"     agcagcagca gagcgagctg cagcccccgg cgcccagcga ggatatctgg aagaaattcg      4680\n" + 
			"     agctgctgcc caccccgccc ctgtccccta gccgccgctc cgggctctgc tcgccctcct      4740\n" + 
			"     acgttgcggt cacacccttc tcccttcggg gagacaacga cggcggtggc gggagcttct      4800\n" + 
			"     ccacggccga ccagctggag atggtgaccg agctgctggg aggagacatg gtgaaccaga      4860\n" + 
			"     gtttcatctg cgacccggac gacgagacct tcatcaaaaa catcatcatc caggactgta      4920\n" + 
			"     tgtggagcgg cttctcggcc gccgccaagc tcgtctcaga gaagctggcc tcctaccagg      4980\n" + 
			"     ctgcgcgcaa agacagcggc agcccgaacc ccgcccgcgg ccacagcgtc tgctccacct      5040\n" + 
			"     ccagcttgta cctgcaggat ctgagcgccg ccgcctcaga gtgcatcgac ccctcggtgg      5100\n" + 
			"     tcttccccta ccctctcaac gacagcagct cgcccaagtc ctgcgcctcg caagactcca      5160\n" + 
			"     gcgccttctc tccgtcctcg gattctctgc tctcctcgac ggagtcctcc ccgcagggca      5220\n" + 
			"     gccccgagcc cctggtgctc catgaggaga caccgcccac caccagcagc gactctggta      5280\n" + 
			"     agcgaagccc gcccaggcct gtcaaaagtg ggcggctgga tacctttccc attttcattg      5340\n" + 
			"     gcagcttatt taacgggcca ctcttattag gaaggagaga tagcagatct ggagagattt      5400\n" + 
			"     gggagctcat cacctctgaa accttgggct ttagcgtttc ctcccatccc ttccccttag      5460\n" + 
			"     actgcccatg tttgcagccc ccctccccgt ttgtctccca cccctcagga atttcattta      5520\n" + 
			"     ggtttttaaa ccttctggct tatcttacaa ctcaatccac ttcttcttac ctcccgttaa      5580\n" + 
			"     cattttaatt gccctggggc ggggtggcag ggagtgtatg aatgaggata agagaggatt      5640\n" + 
			"     gatctctgag agtgaatgaa ttgcttccct cttaacttcc gagaagtggt gggatttaat      5700\n" + 
			"     gaactatcta caaaaatgag gggctgtgtt tagaggctag gcagggcctg cctgagtgcg      5760\n" + 
			"     ggagccagtg aactgcctca agagtgggtg ggctgaggag ctgggatctt ctcagcctat      5820\n" + 
			"     tttgaacact gaaaagcaaa tccttgccaa agttggactt ttttttttct tttattcctt      5880\n" + 
			"     cccccgccct cttggacttt tggcaaaact gcaatttttt tttttttatt tttcatttcc      5940\n" + 
			"     agtaaaatag ggagttgcta aagtcatacc aagcaatttg cagctatcat ttgcaacacc      6000\n" + 
			"     tgaagtgttc ttggtaaagt ccctcaaaaa taggaggtgc ttgggaatgt gctttgcttt      6060\n" + 
			"     gggtgtgtcc aaagcctcat taagtcttag gtaagaattg gcatcaatgt cctatcctgg      6120\n" + 
			"     gaagttgcac ttttcttgtc catgccataa cccagctgtc tttcccttta tgagactctt      6180\n" + 
			"     accttcatgg tgagaggagt aagggtggct ggctagattg gttctttttt tttttttttc      6240\n" + 
			"     cttttttaag acggagtctc actctgtcac taggctggag tgcagtggcg caatcaacct      6300\n" + 
			"     ccaaccccct ggttcaagag attctcctgc ctcagcctcc caagtagctg ggactacagg      6360\n" + 
			"     tgcacaccac catgccaggc taatttttgt aattttagta gagatggggt ttcatcgtgt      6420\n" + 
			"     tggccaggat ggtctctcct gacctcacga tccgcccacc tcggcctccc aaagtgctgg      6480\n" + 
			"     gattacaggt gtgagccagg gcaccaggct tagatgtggc tctttgggga gataattttg      6540\n" + 
			"     tccagagacc tttctaacgt attcatgcct tgtatttgta cagcattaat ctggtaattg      6600\n" + 
			"     attattttaa tgtaaccttg ctaaaggagt gatttctatt tcctttctta aagaggagga      6660\n" + 
			"     acaagaagat gaggaagaaa tcgatgttgt ttctgtggaa aagaggcagg ctcctggcaa      6720\n" + 
			"     aaggtcagag tctggatcac cttctgctgg aggccacagc aaacctcctc acagcccact      6780\n" + 
			"     ggtcctcaag aggtgccacg tctccacaca tcagcacaac tacgcagcgc ctccctccac      6840\n" + 
			"     tcggaaggac tatcctgctg ccaagagggt caagttggac agtgtcagag tcctgagaca      6900\n" + 
			"     gatcagcaac aaccgaaaat gcaccagccc caggtcctcg gacaccgagg agaatgtcaa      6960\n" + 
			"     gaggcgaaca cacaacgtct tggagcgcca gaggaggaac gagctaaaac ggagcttttt      7020\n" + 
			"     tgccctgcgt gaccagatcc cggagttgga aaacaatgaa aaggccccca aggtagttat      7080\n" + 
			"     ccttaaaaaa gccacagcat acatcctgtc cgtccaagca gaggagcaaa agctcatttc      7140\n" + 
			"     tgaagaggac ttgttgcgga aacgacgaga acagttgaaa cacaaacttg aacagctacg      7200\n" + 
			"     gaactcttgt gcgtaaggaa aagtaaggaa aacgattcct tctaacagaa atgtcctgag      7260\n" + 
			"     caatcaccta tgaacttgtt tcaaatgcat gatcaaatgc aacctcacaa ccttggctga      7320\n" + 
			"     gtcttgagac tgaaagattt agccataatg taaactgcct caaattggac tttgggcata      7380\n" + 
			"     aaagaacttt tttatgctta ccatcttttt tttttcttta acagatttgt atttaagaat      7440\n" + 
			"     tgtttttaaa aaattttaag atttacacaa tgtttctctg taaatattgc cattaaatgt      7500\n" + 
			"     aaataacttt aataaaacgt ttatagcagt tacacagaat ttcaatccta gtatatagta      7560\n" + 
			"     cctagtatta taggtactat aaaccctaat tttttttatt taagtacatt ttgcttttta      7620\n" + 
			"     aagttgattt ttttctattg tttttagaaa aaataaaata actggcaaat atatcattga      7680\n" + 
			"     gccaaatctt aagttgtgaa tgttttgttt cgtttcttcc ccctcccaac caccaccatc      7740\n" + 
			"     cctgtttgtt ttcatcaatt gccccttcag agggcggtct taagaaaggc aagagttttc      7800\n" + 
			"     ctctgttgaa atgggtctgg gggccttaag gtctttaagt tcttggaggt tctaagatgc      7860\n" + 
			"     ttcctggaga ctatgataac agccagagtt gacagttaga aggaatggca gaaggcaggt      7920\n" + 
			"     gagaaggtga gaggtaggca aaggagatac aagaggtcaa aggtagcagt taagtacaca      7980\n" + 
			"     aagaggcata aggactgggg agttgggagg aaggtgagga agaaactcct gttactttag      8040\n" + 
			"     ttaaccagtg ccagtcccct gctcactcca aacccaggaa ttctgcccag ttgatgggga      8100\n" + 
			"     cacggtggga accagcttct gctgccttca caaccaggcg ccagtcctgt ccatgggtta      8160\n" + 
			"     tctcgcaaac cccagaggat ctctgggagg aatgctacta ttaaccctat ttcacaaaca      8220\n" + 
			"     aggaaataga agagctcaaa gaggttatgt aacttatctg tagccacgca gataatacaa      8280\n" + 
			"     agcagcaatc tggacccatt ctgttcaaaa cacttaaccc ttcgctatca tgccttggtt      8340\n" + 
			"     catctgggtc taatgtgctg agatcaagaa ggtttaggac ctaatggaca gactcaagtc      8400\n" + 
			"     ataacaatgc taagctctat ttgtgtccca agcactccta agcattttat ccctaactct      8460\n" + 
			"     acatcaaccc catgaaggag atactgttga tttccccata ttagaagtag agagggaagc      8520\n" + 
			"     tgaggcacac aaagactcat ccacatgccc aagattcact gatagggaaa agtggaagcg      8580\n" + 
			"     agatttgaac ccaggctgtt tactcctaac ctgtccaagc cacctctcag acgacggtag      8640\n" + 
			"     gaatcagctg gctgcttgtg agtacaggag ttacagtcca gtgggttatg ttttttaagt      8700\n" + 
			"     ctcaacatct aagcctggtc aggcatcagt tccccttttt ttgtgattta ttttgttttt      8760\n" + 
			"     attttgttgt tcattgttta atttttcctt ttacaatgag aaggtcacca tcttgactcc      8820\n" + 
			"     taccttagcc atttgttgaa tcagactcat gacggctcct gggaagaagc cagttcagat      8880\n" + 
			"     cataaaataa aacatattta ttctttgtca tgggagtcat tattttagaa actacaaact      8940\n" + 
			"     ctccttgctt ccatcctttt ttacatactc atgacacatg ctcatcctga gtccttgaaa      9000\n" + 
			"     aggtattttt gaacatgtgt attaattata agcctctgaa aacctatggc ccaaaccaga      9060\n" + 
			"     aatgatgttg attatatagg taaatgaagg atgctattgc tgttctaatt acctcattgt      9120\n" + 
			"     ctcagtctca aagtaggtct tcagctccct gtactttggg attttaatct accaccaccc      9180\n" + 
			"     ataaatcaat aaataattac tttctttgac tctgactcct agaataatct attcaaaacc      9240\n" + 
			"     ttaatgtctt tttcttgatc cttcttttga gtcctaagta ccgccattac agcttcaaat      9300\n" + 
			"     tggcacgtca tataggcgaa tttcaaaggg agatgcaatc cacagaagta tagtagttca      9360\n" + 
			"     aagggttaca aaagcaaggc gctcttaaac agctcagtct ttgccccttt gtggcctagg      9420\n" + 
			"     gctggagtgc agctctgggg tgactcactt gggaatcggg aaggtgttag tctgaatcac      9480\n" + 
			"     taagtccagg caagccctca gaataggaga gagtgttcct agcaaggaaa acaactctcc      9540\n" + 
			"     attccaaata atcaggaaag aactttaggg atgtggagct tggctatggg aatagaaagg      9600\n" + 
			"     aaccattcca agtgcctatt aggccgctct tacctttact gagccagaga atggctctga      9660\n" + 
			"     aaacaggaca gatgccaact tccttcccga aagtcaggct gatcttgacc acaatacaaa      9720\n" + 
			"     ttggccctta gagcctatac agggagatcc caggggtctc tgccattgtg caacctattt      9780\n" + 
			"     tgtagataat aatcaagaat cggacgtgaa ggggaggagt ttgcaacttg gtcaggaatg      9840\n" + 
			"     tataagaagg aataagctaa ttctgactat gccctttatc catgacacta tccaggaatt      9900\n" + 
			"     aatgactctc ccagaggatt cctggaatga ttttgttgag ggatggaatg tataaagagg      9960\n" + 
			"     aaggaagtgt tattttatgc tgccatttgg aagcaacaaa ggagatcaac agtatgaaaa     10020\n" + 
			"     caatcaatca aatttgaaaa tgaacaaagt tttcacaatc ccagcctaat acttagagag     10080\n" + 
			"     ctcacagctt ggatgcataa gtaaagagtt ctctgctggt ctttaagaca aactctcaca     10140\n" + 
			"     caaaacttgg gaaaaaggac aaaaatgttg cattaggggg ttttctgtgg tttgtttgca     10200\n" + 
			"     ataactataa ttggctcaat caataattat tttttagtat acacactaag ggcccctgta     10260\n" + 
			"     gcattttttc ccatcgataa ataatcctta gtctagaaaa tgccgaggga tgttctccac     10320\n" + 
			"     ccttgtctat aaatgcactt ctagatgact ttataaaagg ctcctccttc aagtttatag     10380\n" + 
			"     aatattataa gactacatta aaggagaaga gaggccggtc gaggtagctc acacctgtaa     10440\n" + 
			"     tcccagcact ttgggaggcc gaagtgggcg gatcatgagg tcaggagatg gagaccatcc     10500\n" + 
			"     tggctaaaac agtgaaaccc cgtctctact aaaaatacaa aaaattagcc gggcgtggtg     10560\n" + 
			"     gcacagcctg cagtcccagc tactcaggag gctgaggcag gagaatcgct tgaacctggg     10620\n" + 
			"     aggcagaggt tacagtgagc tgagattgtg ccactgcatt ccagcctgga tgacacagcg     10680\n" + 
			"     agactccgtc tcaaaaataa ataaataata aataaataaa taaataaagg agaaaaagta     10740\n" + 
			"     aaaacaaagc cagtaggatg ggagcaagga cttattttta aaaataaaac taaaaagact     10800\n" + 
			"     ctgctaccta cctccaaagc cttagcaaaa agtctttttt ctagctcctt caggagagaa     10860\n" + 
			"     cttacaccaa ctctccattt agaggaaaac acccagaaat gctggctttg ccaaactggt     10920\n" + 
			"     tggagacgac tgtaaatgac tgtaagttga tttcattttt aattttattt tattcactat     10980\n" + 
			"     tagcttatac taagct                                                     10996\n" + 
			"//\n" + 
			"\n";
    	BufferedReader r = new BufferedReader(new StringReader(src));
		FeatureParser.windForwardToFeatures(r);
        FeatureParser parser = new FeatureParser(r);
        List features = parser.parse();
   }

    
    
}