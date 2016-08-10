package crowdcenter.csv.parser;

import java.io.IOException;
import java.io.Reader;

import crowdcenter.csv.output.Output;

public interface Parser {
	void setReader(Reader reader);
	void setOutput(Output output);
	void initialize() throws IOException;
	void execute() throws Exception;
}
