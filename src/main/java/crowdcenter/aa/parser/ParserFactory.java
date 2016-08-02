package crowdcenter.aa.parser;

import java.io.Reader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import crowdcenter.aa.output.Output;

public class ParserFactory {
	
	private static Logger logger = LogManager.getLogger(ParserFactory.class);
	
	private ParserFactory() {}
	
	public static Parser getParser(Reader reader, Output output) {
		String classParser = System.getProperty("parser");
		if (classParser == null) throw new RuntimeException("Parser not defined");
		
		logger.info("Output defined: " + classParser);
		
		try {
			Parser parser = (Parser) Class.forName(classParser).newInstance();
			parser.setReader(reader);
			parser.setOutput(output);
			return parser;
		} catch (ClassNotFoundException|IllegalAccessException|InstantiationException e) {
			throw new RuntimeException("Invalid parser");
		}
	}
}
