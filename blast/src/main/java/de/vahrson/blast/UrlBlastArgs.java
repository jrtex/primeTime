/*
 * Created on 21.04.2004
 *
 * $Header: /Users/wova/laufend/cvs/UrlBlast/de/vahrson/blast/UrlBlastArgs.java,v 1.3 2004/04/26 10:09:50 wova Exp $
 *  */
package de.vahrson.blast;

import java.io.*;

import jargs.gnu.*;

/**
 * @author wova
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class UrlBlastArgs {
	private String[] fArgs;
	private CmdLineParser fParser;

	private Option.InputStreamOption fSequencesOption;
	private Option.InputStreamOption fQueryOption;
	private Option.OutputStreamOption fOutputOption;
	private Option.BooleanOption fInfoOption;
	private Option.BooleanOption fEchoQueryOption;
	private Option.BooleanOption fHelpOption;
	private Option.BooleanOption fVersionOption;

	/**
	 * 
	 */
	public UrlBlastArgs(String[] args)
		throws IllegalOptionValueException, UnknownOptionException {
		super();
		fArgs= args;
		fParser= new CmdLineParser();
		initOptions();
		fParser.parse(fArgs);
	}

	private void initOptions() {
		fSequencesOption= new  Option.InputStreamOption("[sequences_file]");
		fSequencesOption.setDescription(
			"Filename,url,'-' containing sequences in fasta format. If not specified, stdin is used.");
		fParser.addPositionalOption(fSequencesOption);

		fQueryOption= new  Option.InputStreamOption('q', "query-options");
		fQueryOption.setDescription("file,url containing options for searching/formatting in java .properties format");
		fParser.addOption(fQueryOption);

		fOutputOption= new  Option.OutputStreamOption('o', "output");
		fOutputOption.setDescription("file where output should be directed. Default is stdout.");
		fParser.addOption(fOutputOption);

		fInfoOption= new  Option.BooleanOption('i', "info");
		fInfoOption.setDescription("Perform an 'Info' query.");
		fParser.addOption(fInfoOption);

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

	public InputStream getSequences() {
		InputStream answer= (InputStream) fSequencesOption.getValue();
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

	public boolean isWantInfo() {
		return ((Boolean) fInfoOption.getValue()).booleanValue();
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
