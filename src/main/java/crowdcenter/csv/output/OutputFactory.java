package crowdcenter.csv.output;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class OutputFactory {
	
	private static Logger logger = LogManager.getLogger(OutputFactory.class);
	private OutputFactory() {}
	
	public static Output getOutput() {
		String classOutput = System.getProperty("output");
		if (classOutput == null) throw new RuntimeException("Output not defined");
		
		logger.info("Output defined: " + classOutput);
		
		try {
			return (Output) Class.forName(classOutput).newInstance();
		} catch (ClassNotFoundException|IllegalAccessException|InstantiationException e) {
			throw new RuntimeException("Invalid output");
		}
	}
}
