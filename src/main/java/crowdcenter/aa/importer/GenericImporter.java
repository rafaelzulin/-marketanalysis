package crowdcenter.aa.importer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import crowdcenter.aa.output.Output;
import crowdcenter.aa.output.OutputFactory;
import crowdcenter.aa.parser.Parser;
import crowdcenter.aa.parser.ParserFactory;

public class GenericImporter {
	
	private static Logger logger = LogManager.getFormatterLogger(GenericImporter.class);

	public static void main(String[] args) throws IOException {
		File inputFile = getParams(args);
		File[] arrFiles = getFileArray(inputFile);
		FileReader[] arrReaders = getReaders(arrFiles);
		Output out = OutputFactory.getOutput();
		
		for (int i = 0; i < arrReaders.length; i++) {
			logger.trace("Processing File: " + arrFiles[i]);
			FileReader fileReader = arrReaders[i];
			
			Parser parser = ParserFactory.getParser(fileReader, out);
			try {
				parser.initialize();
			} catch (IOException e) {
				logger.fatal("Fatal error when trying initalize the parser. " + e.getMessage());
				System.exit(0);
			}
			
			try {
				parser.execute();			
			} catch (Exception e) {
				logger.fatal("Some fatal error has occurred. The process will be finished: " + e.getMessage());
				e.printStackTrace();
				break;
			} finally {
				fileReader.close();
			}
		}
		
		logger.info("Finished");
	}

	private static FileReader[] getReaders(File[] listFiles) {
		FileReader[] listReaders = new FileReader[listFiles.length];
		for (int i = 0; i < listFiles.length; i++) {
			try {
				listReaders[i] = new FileReader(listFiles[i]);
			} catch (FileNotFoundException e) {
				logger.error("File not found: " + listFiles[i]);
				System.exit(0);
				break;
			}
		}
		return listReaders;
	}

	private static File[] getFileArray(File inputFile) {
		if (!inputFile.exists()) {
			logger.error("File or directory not found.");
			System.exit(0);
		}
		
		File[] listFiles = null;
		if (inputFile.isDirectory()) {
			listFiles = inputFile.listFiles();
		} else {
			listFiles = new File[] { inputFile };
		}
		return listFiles;
	}

	private static File getParams(String[] args) {
		Options options = new Options();
		Option optFile = new Option("f", "file", true, "Input file/directory");
		optFile.setRequired(true);
		optFile.setType(File.class);
		options.addOption(optFile);
		
		CommandLineParser cliParser = new DefaultParser();
		HelpFormatter formatter = new HelpFormatter();
		File inputFile = null;
		
		try {
			CommandLine cmd = cliParser.parse(options, args);
			inputFile = (File) cmd.getParsedOptionValue("f");
		} catch (ParseException e) {
			logger.error(e.getMessage());
			formatter.printHelp("help", options);
			System.exit(0);
		}
		return inputFile;
	}
}
