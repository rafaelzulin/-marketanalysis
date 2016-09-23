package crowdcenter.whois;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import crowdcenter.db.ConnectionFactory;

public class WhoisGetter {
	
	Connection conn;
	private static Logger logger = LogManager.getFormatterLogger(WhoisGetter.class);
	
	public WhoisGetter() throws Exception {
		conn = ConnectionFactory.getConnection();
	}

	public static void main(String[] args) {
		Map<String, String> map = getParams(args);
		
		try {
			new WhoisGetter().run(map);
		} catch (Exception e) {
			logger.error("Some error has ocurred: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void run(Map<String, String> map) throws SQLException {
		Whoxy whoxy = new Whoxy(map.get("key"));
		
		ResultSet rs = conn.createStatement().executeQuery(map.get("sql"));
		
		rs.last();
		logger.info("Total rows: " + rs.getRow());
		rs.beforeFirst();

		int count = 0;
		int errorCount = 0;
		while (rs.next()) {
			if (errorCount > 50) break;
			
			String domain = rs.getString(1);
			
			logger.info("#" + ++count + " DOMAIN: " + domain);
			
			String response;
			try {
				response = whoxy.getWhois(domain);
			} catch (WhoxyException e) {
				e.printStackTrace();
				errorCount++;
				continue;
			}
			
			response = response.replace("\\", "\\\\");
			response = response.replace("'", "\\'");
			response = response.replace("\"", "\\\"");
			
			Statement statement = conn.createStatement();
			String sql = "INSERT INTO whois (original_domain, whoxy_whois) VALUES ('" + domain +"', '" + response + "')";
			statement.execute(sql);
		}
		
		logger.info("Proccess finished");
	}
	
	private static Map<String, String> getParams(String[] args) {
		Options options = new Options();
		Option optKey = new Option("k", "key", true, "Whoxy key");
		optKey.setRequired(true);
		optKey.setType(String.class);
		
		options.addOption(optKey);
		
		Option optCol = new Option("s", "sql", true, "sql");
		optCol.setRequired(true);
		optCol.setType(String.class);
		
		options.addOption(optCol);
		
		CommandLineParser cliParser = new DefaultParser();
		HelpFormatter formatter = new HelpFormatter();
		Map<String, String> map = new HashMap<>();
		
		try {
			CommandLine cmd = cliParser.parse(options, args);
			map.put("key", (String)cmd.getParsedOptionValue("k"));
			map.put("sql", (String)cmd.getParsedOptionValue("s"));
		} catch (ParseException e) {
			logger.error(e.getMessage());
			formatter.printHelp("help", options);
			System.exit(0);
		}
		return map;
	}
}
