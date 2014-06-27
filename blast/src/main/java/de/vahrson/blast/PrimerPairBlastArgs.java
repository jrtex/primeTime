/*
 * Created on 21.10.2004 
 *
 * $Header: /Users/wova/laufend/cvs/UrlBlast/de/vahrson/blast/PrimerPairBlastArgs.java,v 1.1 2004/10/23 10:43:57 wova Exp $
 */
package de.vahrson.blast;

import jargs.gnu.CmdLineParser;
import jargs.gnu.IllegalOptionValueException;
import jargs.gnu.Option;
import jargs.gnu.UnknownOptionException;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author wova
 *
 * Commandline arguments for PrimerPairBlast
 */
public class PrimerPairBlastArgs {
	private String[] fArgs;
	private CmdLineParser fParser;

	private Option.InputStreamOption fPrimerTabOption;
	private Option.InputStreamOption fQueryOption;
	private Option.OutputStreamOption fOutputOption;
	private Option.BooleanOption fInfoOption;
	private Option.BooleanOption fEchoQueryOption;
	private Option.BooleanOption fHelpOption;
	private Option.BooleanOption fVersionOption;

	/**
	 * 
	 */
	public PrimerPairBlastArgs(String[] args)
		throws IllegalOptionValueException, UnknownOptionException {
		super();
		fArgs= args;
		fParser= new CmdLineParser();
		initOptions();
		fParser.parse(fArgs);
	}

	private void initOptions() {
		fPrimerTabOption= new  Option.InputStreamOption("[primer_pairs_tab]");
		fPrimerTabOption.setDescription(
			"Filename,url,'-' containing primer pairs in tab-delimited format. If not specified, stdin is used.");
		fParser.addPositionalOption(fPrimerTabOption);

		fQueryOption= new  Option.InputStreamOption('q', "query-options");
		fQueryOption.setDescription("file,url containing options for searching/formatting in java .properties format");
		fParser.addOption(fQueryOption);

		fEchoQueryOption= new  Option.BooleanOption('e', "echo-query");
		fEchoQueryOption.setDescription("Echo the query options.");
		fParser.addOption(fEchoQueryOption);

		fHelpOption= new  Option.BooleanOption('h', "help");
		fHelpOption.setDescription("Show this documentation.");
		fParser.addOption(fHelpOption);

		fVersionOption= new  Option.BooleanOption('v', "version");
		fVersionOption.setDescription("Show program version.");
		fParser.addOption(fVersionOption);
	}

	public InputStream getPrimerTab() {
		InputStream answer= (InputStream) fPrimerTabOption.getValue();
		if (answer == null) {
			answer= System.in;
		}
		return answer;
	}

	public InputStream getQueryOptions() {
		return (InputStream) fQueryOption.getValue();
	}
	
	public OutputStream getOutput() {
		OutputStream answer= (OutputStream) fOutputOption.getValue();
		if (answer == null) {
			answer= System.out;
		}
		return answer;
	}

	public boolean isWantEcho() {
		return ((Boolean) fEchoQueryOption.getValue()).booleanValue();
	}

	public boolean isWantHelp() {
		return ((Boolean) fHelpOption.getValue()).booleanValue();
	}

	public boolean isWantVersion() {
		return ((Boolean) fVersionOption.getValue()).booleanValue();
	}

	public String documentation() {	
		return "Synopsis:\njava urlblast " + fParser.synopsis() + "\n\nDescription:" + fParser.documentation();
	}

}
