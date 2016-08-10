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

public class PlayStoreDataBaseOutput implements Output {
	private Logger logger = LogManager.getFormatterLogger(PlayStoreDataBaseOutput.class);
	private Connection conn;
	private Map<Integer, String> values;
	private PreparedStatement statement;
	
	public PlayStoreDataBaseOutput() throws Exception {
		this.conn = ConnectionFactory.getConnection();
		this.values = new HashMap<>();
		this.statement = conn.prepareStatement(sql());
	}
	
	private String sql() {
		return "INSERT INTO play_store " +
				"	(" +
				"		appid, " + //1
				"		name, " + //2
				"		url, " + //3
				"		referencedate, " + //4
				"		developer, " + //5
				"		istopdeveloper, " + //6
				"		developerurl, " + //7
				"		developerdomain, " + //8
				"		category, " + //9
				"		price, " + //10
				"		score_total, " + //11
				"		score_count, " + //12
				"		score_fivestars, " + //13
				"		score_fourstars, " + //14
				"		score_threestars, " + //15
				"		score_twostars, " + //16
				"		score_onestars, " + //17
				"		instalations, " + //18
				"		haveinapppurchases, " + //19
				"		developeremail, " + //20
				"		developerwebsite, " + //21
				"		physicaladdress, " + //22
				"		lastupdatedate, " + //23
				"		import_time" + //24
				"	) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)" ;
	}
	
	@Override
	public void setField(Integer index, String value) {
		values.put(index, value);
	}
	
	@SuppressWarnings("unused")
	@Override
	public void flush() throws Exception {
		String name = values.get(0);
		String url = values.get(1);
		String appid = values.get(2);
		String relatedurls = values.get(3);
		String referencedate = values.get(4);
		String developer = values.get(5);
		String istopdeveloper = values.get(6);
		String developerurl = values.get(7);
		String developernormalizeddomain = values.get(8);
		String category = values.get(9);
		String isfree = values.get(10);
		String price = values.get(11);
		String screenshots = values.get(12);
		String reviewers = values.get(13);
		String score_total = values.get(14);
		String score_count = values.get(15);
		String score_fivestars = values.get(16);
		String score_fourstars = values.get(17);
		String score_threestars = values.get(18);
		String score_twostars = values.get(19);
		String score_onestars = values.get(20);
		String instalations = values.get(21);
		String currentversion = values.get(22);
		String appsize = values.get(23);
		String minimumosversion = values.get(24);
		String contentrating = values.get(25);
		String haveinapppurchases = values.get(26);
		String developeremail = values.get(27);
		String developerwebsite = values.get(28);
		String physicaladdress = values.get(29);
		String lastupdatedate = values.get(30);
		String coverimgurl = values.get(31);
		String interactiveelements = values.get(32);
		String permissions = values.get(33);
		String permissiondescriptions = values.get(34);
		String description = values.get(35);
		String whatsnew = values.get(36);
		
		setString(1, appid);
		setString(2, name);
		setString(3, url);
		setDate(4, referencedate);
		setString(5, developer);
		setBoolean(6, istopdeveloper);
		setString(7, developerurl);
		setString(8, developernormalizeddomain);
		setString(9, category);
		setFloat(10, price);
		setFloat(11, score_total);
		setInt(12, score_count);
		setInt(13, score_fivestars);
		setInt(14, score_fourstars);
		setInt(15, score_threestars);
		setInt(16, score_twostars);
		setInt(17, score_onestars);
		setString(18, instalations);
		setBoolean(19, haveinapppurchases);
		setString(20, developeremail);
		setString(21, developerwebsite);
		setString(22, physicaladdress);
		setDate(23, lastupdatedate);
		statement.setTimestamp(24, new Timestamp(new Date().getTime()));
		
		try {
			statement.executeUpdate();
			logger.info("Record was successfully outputed: " + name);
		} catch (SQLIntegrityConstraintViolationException e) {
			logger.error("Failed to insert. Constraint violation: " + e.getMessage());
		} catch (SQLException e) {
			if (! e.getMessage().startsWith("Incorrect string value"))
				throw new SQLException("Some error has ocurred when tried to insert in the BD("+ name +"): " + e.getMessage(), e);
			else 
				logger.error("Incorrect value for string in " + values.get(1));
		} 
		
		this.values = new HashMap<>();
	}
	
	private void setString(Integer index, String str) throws SQLException {
		statement.setString(index, str.isEmpty() ? null : str);
	}
	
	private void setFloat(Integer index, String number) throws SQLException {
		Float value = 0f;
		try {
			value = Float.valueOf(number);
		} catch (NumberFormatException e) {}
		statement.setFloat(index, value / 100);
	}
	
	private void setDate(Integer index, String value) throws SQLException {
		java.sql.Date date = null;
		try {
			date = java.sql.Date.valueOf(value);
		} catch (IllegalArgumentException e) {}
		statement.setDate(index, date);
	}
	
	private void setInt(Integer index, String number) throws SQLException {
		Integer value = 0;
		try {
			value = Integer.valueOf(number);
		} catch (NumberFormatException e) {}
		
		statement.setInt(index, value);
	}
	
	private void setBoolean(Integer index, String value) throws SQLException {
		statement.setInt(index, "True".equals(value) ? 1 : 0);
	}
}
