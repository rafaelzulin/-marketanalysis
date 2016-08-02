package spikes;

import java.io.File;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class Main {

	public static void main(String[] args) throws ParseException {
		Options options = new Options();
		Option optFile = new Option("f", "file", true, "Input file");
		optFile.setRequired(true);
		optFile.setType(File.class);
		options.addOption(optFile);
		
		CommandLineParser cliParser = new DefaultParser();
		HelpFormatter formatter = new HelpFormatter();
		CommandLine cmd = null;
		
		try {
			cmd = cliParser.parse(options, args);
		} catch (ParseException e) {
			System.out.println(e.getMessage());
			formatter.printHelp("help", options);
		}
		
		File file = (File) cmd.getParsedOptionValue("f");
		System.out.println(file);
	}

}
