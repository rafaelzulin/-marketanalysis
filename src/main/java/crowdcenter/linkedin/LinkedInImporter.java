package crowdcenter.linkedin;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LinkedInImporter {
	
	private static Logger logger = LogManager.getFormatterLogger(LinkedInImporter.class);

	public static void main(String[] args) {
		String inPath = validatesParams(args);
		
		File inFile = new File(inPath);
		if (!inFile.exists()) {
			logger.error("File not found: " + inPath);
			System.exit(0);
		}
		
		File[] listFiles = null;
		if (inFile.isDirectory()) {
			listFiles = inFile.listFiles();
		} else {
			listFiles = new File[] { inFile };
		}
		
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
		
		DataBaseOutput out = null;
		try {
			out = new DataBaseOutput();
		} catch (Exception e) {
			logger.error("Some critical error has occurred whem trying initalize the outputter. " + e.getMessage());
			System.exit(0);
		}
		
		for (int i = 0; i < listReaders.length; i++) {
			logger.info("Processing File: " + listFiles[i]);
			FileReader fileReader = listReaders[i];
			
			LinkedInCSVParser parser = new LinkedInCSVParser(fileReader, out);
			try {
				parser.initialize();
			} catch (IOException e) {
				logger.error("Criticial error when trying initalize the parser. " + e.getMessage());
				System.exit(0);
			}
			
			try {
				parser.execute();			
			} catch (Exception e) {
				logger.error("Some critical error has occurred. The process was finished: " + e.getMessage());
				e.printStackTrace();
				break;
			}
		}
		
		logger.info("Finished");
	}

	private static String validatesParams(String[] args) {
		if (args == null || args.length < 1 || args.length > 1) {
			logger.error("Wrong number of parameters. Just the CSV file is required.");
			System.exit(0);
		}
		
		return args[0];
	}
}
