package crowdcenter.csv.output;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import crowdcenter.csv.parser.LinkedInCSVParser;
import crowdcenter.db.ConnectionFactory;

public class LinkedInDataBaseOutput implements Output {
	private Logger logger = LogManager.getFormatterLogger(LinkedInCSVParser.class);
	private Connection conn;
	private String[] values;
	private PreparedStatement statement;
	
	public LinkedInDataBaseOutput() throws Exception {
		this.conn = ConnectionFactory.getConnection();
		this.values = new String[numberFields()];
		this.statement = conn.prepareStatement(sql());
	}
	
	public Integer numberFields() {
		return 16;
	}
	
	private String sql() {
		return "INSERT INTO linkedin (" +
				"	 company_name," +
				"	 domain," +
				"	 industry," +
				"	 employees," +
				"	 locality," +
				"	 region," +
				"	 country," +
				"	 social_network_type," +
				"	 social_network_link," +
				"	 phone," +
				"	 website," +
				"	 description," +
				"	 street," +
				"	 postal_code," +
				"	 year_established)" +
				"VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	}
	
	@Override
	public void setField(Integer index, String value) {
		values[index] = value;
	}
	
	@Override
	public void flush() throws Exception {
		logger.info("OUTPUTING: " + values[1]);
		
		for (int i = 1; i < values.length; i++) {
			statement.setString(i, values[i].isEmpty() ? null : values[i]);
		}
		
		try {
			statement.execute();
			logger.info("Record was successfully outputed");
		} catch (SQLIntegrityConstraintViolationException e) {
			logger.error(" Failed to insert. Constraint violation: " + e.getMessage());
		} catch (SQLException e) {
			throw new SQLException("Some error has ocurred when tried to insert in the BD: " + e.getMessage(), e);
		} 
		
		this.values = new String[numberFields()];
	}
}
