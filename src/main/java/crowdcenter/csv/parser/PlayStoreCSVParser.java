package crowdcenter.csv.parser;

import java.io.IOException;
import java.io.Reader;
import java.util.Iterator;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import crowdcenter.csv.output.Output;

public class PlayStoreCSVParser implements Parser {
	
	private Reader reader;
	private Output output;
	private CSVParser parser;
	
	public PlayStoreCSVParser() {
		
	}
	
	@Override
	public void setReader(Reader reader) {
		this.reader = reader;
	}
	
	@Override
	public void setOutput(Output output) {
		this.output = output;
	}
	
	@Override
	public void initialize() throws IOException {
		if (output == null) throw new NullPointerException("Reader and outputter must be defined");
		
		try {
			parser = new CSVParser(reader, buildFormat());
		} catch (IOException e) {
			throw new IOException("Error when trying initilize the CSV Parser", e);
		}
	}
	
	@Override
	public void execute() throws Exception {
		if (parser == null) throw new IOException("The parser has not been initialized");
		
		Iterator<CSVRecord> iterator = parser.iterator();
		Integer numberFields = parser.getHeaderMap().size();
		
		while (iterator.hasNext()) {
			CSVRecord record = iterator.next();
			
			if (! record.isConsistent()) continue;
			
			for (int i = 0; i < numberFields; i++) {
				String field = record.get(i).trim();
				output.setField(i, field);
			}
			
			output.flush();
		}
		parser.close();
	}

	private CSVFormat buildFormat() {
		return CSVFormat.newFormat(';')
				.withHeader()
				.withIgnoreEmptyLines()
				.withIgnoreHeaderCase()
				.withRecordSeparator('\n');
	}
}
