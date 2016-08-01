package crowdcenter.linkedin;

import java.io.IOException;
import java.io.Reader;
import java.util.Iterator;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LinkedInCSVParser {
	
	private Logger logger = LogManager.getFormatterLogger(LinkedInCSVParser.class);
	private Reader reader;
	private DataBaseOutput out;
	private CSVParser parser;
	
	public LinkedInCSVParser(Reader reader, DataBaseOutput out) {
		this.out = out;
		this.reader = reader;
	}
	
	public void initialize() throws IOException {
		if (out == null) throw new NullPointerException("Reader and outputter must be defined");
		
		try {
			parser = new CSVParser(reader, buildFormat());
		} catch (IOException e) {
			throw new IOException("Error when trying initilize the CSV Parser", e);
		}
	}
	
	public void execute() throws Exception {
		if (parser == null) throw new IOException("The parser has not been initialized");
		
		Iterator<CSVRecord> iterator = parser.iterator();
		
		while (iterator.hasNext()) {
			CSVRecord record = iterator.next();
			
			if (! record.isConsistent()) continue;
			
			for (int i = 0; i < out.numberFields(); i++) {
				String field = record.get(i).trim();
				out.setField(i, field);
			}
			
			out.flush();
		}
		parser.close();
	}

	private CSVFormat buildFormat() {
		return CSVFormat.newFormat(',')
				.withHeader()
				.withIgnoreEmptyLines()
				.withIgnoreHeaderCase()
				.withRecordSeparator('\n')
				.withQuote('\"');
	}
}
