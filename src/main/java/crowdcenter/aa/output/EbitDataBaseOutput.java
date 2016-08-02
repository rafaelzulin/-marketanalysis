package crowdcenter.aa.output;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Timestamp;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import crowdcenter.aa.db.ConnectionFactory;

public class EbitDataBaseOutput implements Output {
	private Logger logger = LogManager.getFormatterLogger(EbitDataBaseOutput.class);
	private Connection conn;
	private String[] values;
	private PreparedStatement statement;
	
	public EbitDataBaseOutput() throws Exception {
		this.conn = ConnectionFactory.getConnection();
		this.values = new String[numberFields()];
		this.statement = conn.prepareStatement(sql());
	}
	
	@Override
	public Integer numberFields() {
		return 6;
	}
	
	private String sql() {
		return "INSERT INTO ebit (" +
				"	 nome," +
				"	 mercado, " +
				"	 medalha, " +
				"	 avaliacoes, " +
				"	 site, " +
				"	 status_site, " +
				"	 create_time) " +
				"VALUES (?, ?, ?, ?, ?, ?, ?)";
	}
	
	@Override
	public void setField(Integer index, String value) {
		values[index] = value;
	}
	
	@Override
	public void flush() throws Exception {
		for (int i = 0; i < values.length; i++) {
			statement.setString(i+1, values[i].isEmpty() ? null : values[i]);
		}
		
		statement.setTimestamp(7, new Timestamp(new Date().getTime()));
		
		try {
			statement.executeUpdate();
			logger.info("Record was successfully outputed: " + values[0]);
		} catch (SQLIntegrityConstraintViolationException e) {
			logger.error("Failed to insert. Constraint violation: " + e.getMessage());
		} catch (SQLException e) {
			throw new SQLException("Some error has ocurred when tried to insert in the BD: " + e.getMessage(), e);
		} 
		
		this.values = new String[numberFields()];
	}
}
