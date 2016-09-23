package crowdcenter.csv.output;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import crowdcenter.db.ConnectionFactory;

public class EbitDataBaseOutput implements Output {
	private Logger logger = LogManager.getFormatterLogger(EbitDataBaseOutput.class);
	private Connection conn;
	private Map<Integer, String> values;
	private PreparedStatement statement;
	private Integer id;
	
	public EbitDataBaseOutput() throws Exception {
		this.conn = ConnectionFactory.getConnection();
		this.values = new HashMap<>();
		this.statement = conn.prepareStatement(sql());
		this.id = 1;
	}
	
	private String sql() {
		return "INSERT INTO ebit (" +
				"    id, " +
				"	 nome," +
				"	 mercado, " +
				"	 medalha, " +
				"	 avaliacoes, " +
				"	 site, " +
				"	 status_site, " +
				"	 import_time) " +
				"VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
	}
	
	@Override
	public void setField(Integer index, String value) {
		values.put(index, value);
	}
	
	@SuppressWarnings("unused")
	@Override
	public void flush() throws Exception {
		String nome = values.get(0);
		String mercado = values.get(1);
		String medalha = values.get(2);
		String avaliacoes = values.get(3);
		String site = values.get(4);
		String site_check = values.get(5);
		String status_site = values.get(6);
		
		statement.setInt(1, this.id);
		statement.setString(2, nome);
		statement.setString(3, mercado);
		statement.setString(4, medalha);
		statement.setString(5, avaliacoes);
		statement.setString(6, site);
		statement.setString(7, status_site);
		statement.setTimestamp(8, new Timestamp(new Date().getTime()));

		try {
			statement.executeUpdate();
			this.id++;
			logger.info("Record was successfully outputed: " + nome);
		} catch (SQLIntegrityConstraintViolationException e) {
			logger.error("Failed to insert. Constraint violation: " + e.getMessage());
		} catch (SQLException e) {
			throw new SQLException("Some error has ocurred when tried to insert in the BD: " + e.getMessage(), e);
		}
		
		this.values = new HashMap<>();
	}
}
