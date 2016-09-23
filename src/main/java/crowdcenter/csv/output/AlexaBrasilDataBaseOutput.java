package crowdcenter.csv.output;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import crowdcenter.db.ConnectionFactory;

public class AlexaBrasilDataBaseOutput implements Output {
	private Logger logger = LogManager.getFormatterLogger(AlexaBrasilDataBaseOutput.class);
	private Connection conn;
	private Map<Integer, String> values;
	private PreparedStatement statement;
	private PreparedStatement validadeStatement; 
	
	public AlexaBrasilDataBaseOutput() throws Exception {
		this.conn = ConnectionFactory.getConnection();
		this.values = new HashMap<>();
		this.statement = conn.prepareStatement(sql());
		this.validadeStatement = conn.prepareStatement("select 1 from alexa_brasil where domain = ?");
	}
	
	private String sql() {
		return "INSERT INTO alexa_brasil (" +
				"	domain, " +
				"	category, " +
				"	language, " +
				"	global_rank, " +
				"	medium_time, " +
				"   import_time " +
				") VALUES (?, ?, ?, ?, ?, ?)";
	}
	
	@Override
	public void setField(Integer index, String value) {
		values.put(index, value);
	}
	
	@SuppressWarnings("unused")
	@Override
	public void flush() throws Exception {
		String domain = values.get(0);
		String category = values.get(1);
		String language = values.get(2);
		String global_rank = values.get(3);
		String unique_visitors = values.get(4);
		String unique_visitors_growth = values.get(5);
		String pageviews = values.get(6);
		String medium_time = values.get(7);
		
		if (validatesExistence(domain)) {
			logger.info("Register already exists: " + domain);
			return;
		}
		
		setString(1, domain);
		setString(2, category);
		setString(3, language);
		setString(4, global_rank);
		setInt(5, medium_time);
		statement.setTimestamp(6, new Timestamp(new Date().getTime()));
		
		try {
			statement.executeUpdate();
			logger.info("Record was successfully outputed: " + domain);
		} catch (SQLIntegrityConstraintViolationException e) {
			logger.error("Failed to insert. Constraint violation: " + e.getMessage());
		} catch (SQLException e) {
			if (! e.getMessage().startsWith("Incorrect string value"))
				throw new SQLException("Some error has ocurred when tried to insert in the BD: " + e.getMessage(), e);
			else 
				logger.error("Incorrect value for string in " + domain);
		} 
		
		this.values = new HashMap<>();
	}
	
	private Boolean validatesExistence(String domain) throws SQLException {
		validadeStatement.setString(1, domain);
		
		ResultSet rs = validadeStatement.executeQuery();
		
		return rs.next();
	}

	private void setString(Integer index, String str) throws SQLException {
		statement.setString(index, str.isEmpty() ? null : str);
	}
	
	private void setInt(Integer index, String number) throws SQLException {
		Integer value = 0;
		try {
			value = Integer.valueOf(number);
		} catch (NumberFormatException e) {}
		
		statement.setInt(index, value);
	}
}
