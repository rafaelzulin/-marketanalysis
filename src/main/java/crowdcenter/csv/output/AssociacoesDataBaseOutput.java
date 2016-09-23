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

public class AssociacoesDataBaseOutput implements Output {
	private Logger logger = LogManager.getFormatterLogger(AssociacoesDataBaseOutput.class);
	private Connection conn;
	private Map<Integer, String> values;
	private PreparedStatement statement;
	private PreparedStatement validadeStatement; 
	
	public AssociacoesDataBaseOutput() throws Exception {
		this.conn = ConnectionFactory.getConnection();
		this.values = new HashMap<>();
		this.statement = conn.prepareStatement(sql());
		this.validadeStatement = conn.prepareStatement("select 1 from alexa_brasil where domain = ?");
	}
	
	private String sql() {
		return "INSERT INTO appdata.associacoes ( "
				+"  associacao, "
				+"	empresa, "
				+"	site, "
				+"	razao_social, "
				+"	email, "
				+"	telefone, "
				+"	contato, "
				+"  import_time"
				+") VALUES (?, ?, ?, ?, ?, ?, ?, ?) ";
	}
	
	@Override
	public void setField(Integer index, String value) {
		values.put(index, value);
	}
	
	@Override
	public void flush() throws Exception {
		String associacao = values.get(0);
		String empresa = values.get(1);
		String site = values.get(2);
		String razao_social = values.get(3);
		String email = values.get(4);
		String telefone = values.get(5);
		String contato = values.get(6);
		
		setString(1, associacao);
		setString(2, empresa);
		setString(3, site);
		setString(4, razao_social);
		setString(5, email);
		setString(6, telefone);
		setString(7, contato);

		statement.setTimestamp(8, new Timestamp(new Date().getTime()));
		
		try {
			statement.executeUpdate();
			logger.info("Record was successfully outputed: " + empresa);
		} catch (SQLIntegrityConstraintViolationException e) {
			logger.error("Failed to insert. Constraint violation: " + e.getMessage());
		} catch (SQLException e) {
			if (! e.getMessage().startsWith("Incorrect string value"))
				throw new SQLException("Some error has ocurred when tried to insert in the BD: " + e.getMessage(), e);
			else 
				logger.error("Incorrect value for string in " + empresa);
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
