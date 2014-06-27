/*
 * Created on 19.02.2005 
 *
 * $Header: /Users/wova/laufend/cvs/Rusa/de/vahrson/rusa/RusaArguments.java,v 1.2 2005/02/25 11:02:34 wova Exp $
 */
package de.vahrson.rusa;

import jargs.gnu.CmdLineParser;
import jargs.gnu.IllegalOptionValueException;
import jargs.gnu.Option;
import jargs.gnu.UnknownOptionException;

/**
 * @author wova
 *
 * Args for the rusa program
 */
public class RusaArguments {
	private String[] fArgs;
	private CmdLineParser fParser;

	private Option.StringOption fUsaOption;
	private Option.StringOption fSourceFileOption;
	private Option.BooleanOption fHelpOption;
	private Option.BooleanOption fVersionOption;

	/**
	 * 
	 */
	public RusaArguments(String[] args)
		throws IllegalOptionValueException, UnknownOptionException {
		super();
		fArgs= args;
		fParser= new CmdLineParser();
		initOptions();
		fParser.parse(fArgs);
	}

	private void initOptions() {
		fUsaOption= new  Option.StringOption("usa");
		fUsaOption.setDescription(
			"String containing USA [format::][db:]id");
		fParser.addPositionalOption(fUsaOption);

		fSourceFileOption= new  Option.StringOption('p', "put");
		fSourceFileOption.setDescription("Put [file] into local database under usa's name");
		fParser.addOption(fSourceFileOption);

		fHelpOption= new  Option.BooleanOption('h', "help");
		fHelpOption.setDescription("Show this documentation.");
		fParser.addOption(fHelpOption);

		fVersionOption= new  Option.BooleanOption('v', "version");
		fVersionOption.setDescription("Show program version.");
		fParser.addOption(fVersionOption);
	}

	public String getUsa() {
		String answer= (String) fUsaOption.getValue();
		return answer;
	}

	public String getSourcFile() {
		String answer= (String) fSourceFileOption.getValue();
		return answer;
	}

	public boolean isWantHelp() {
		return ((Boolean) fHelpOption.getValue()).booleanValue();
	}

	public boolean isWantVersion() {
		return ((Boolean) fVersionOption.getValue()).booleanValue();
	}

	public String documentation() {	
		return "Synopsis:\njava rusa " + fParser.synopsis() + "\n\nDescription:" + fParser.documentation();
	}


}
