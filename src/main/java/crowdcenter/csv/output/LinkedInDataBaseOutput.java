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

import crowdcenter.csv.parser.LinkedInCSVParser;
import crowdcenter.db.ConnectionFactory;

public class LinkedInDataBaseOutput implements Output {
	private Logger logger = LogManager.getFormatterLogger(LinkedInCSVParser.class);
	private Connection conn;
	private Map<Integer, String> values;
	private PreparedStatement statement;
	
	public LinkedInDataBaseOutput() throws Exception {
		this.conn = ConnectionFactory.getConnection();
		this.values = new HashMap<>();
		this.statement = conn.prepareStatement(sql());
	}
	
	private String sql() {
		return "INSERT INTO appdata.linkedin ( "
				+ "	company_name, "
				+ "	domain, "
				+ "	industry, "
				+ "	employees, "
				+ "	locality, "
				+ "	region, "
				+ "	country, "
				+ "	linkedin_link, "
				+ "	followers, "
				+ "	date, "
				+ "	phone, "
				+ "	website, "
				+ "	website_status, "
				+ "	street, "
				+ "	postal_code, "
				+ "	year_established, "
				+ "	import_time "
				+ ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";
	}
	
	@Override
	public void setField(Integer index, String value) {
		values.put(index, value);
	}
	
	@Override
	public void flush() throws Exception {
		String id = values.get(0);
		String company_name = values.get(1);
		String domain = values.get(2);
		String industry = values.get(3);
		String employees = values.get(4);
		String locality = values.get(5);
		String region = values.get(6);
		String country = values.get(7);
		String social_network_type = values.get(8);
		String social_network_link = values.get(9);
		String followers = values.get(10);
		String date = values.get(11);
		String hour = values.get(12);
		String phone = values.get(13);
		String website = values.get(14);
		String website_status = values.get(15);
		String description = values.get(16);
		String street = values.get(17);
		String postal_code = values.get(18);
		String year_established = values.get(19);
		String file = values.get(20);
		
		setString(1, company_name);
		setString(2, domain);
		setString(3, industry);
		setString(4, employees);
		setString(5, locality);
		setString(6, region);
		setString(7, country);
		setString(8, social_network_link);
		setInt(9, followers);
		setString(10, date);
		setString(11, phone);
		setString(12, website);
		setString(13, website_status);
		setString(14, street);
		setString(15, postal_code);
		setString(16, year_established);
		statement.setTimestamp(17, new Timestamp(new Date().getTime()));
		
		try {
			statement.execute();
			logger.info("RECORD ADDED: " + company_name);
		} catch (SQLIntegrityConstraintViolationException e) {
			logger.error(" Failed to insert. Constraint violation: " + e.getMessage());
		} catch (SQLException e) {
			throw new SQLException("Some error has ocurred when tried to insert in the BD: " + e.getMessage(), e);
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
		statement.setDate(7, date);
	}
	
	private void setInt(Integer index, String number) throws SQLException {
		Integer value = 0;
		try {
			value = Integer.valueOf(number);
		} catch (NumberFormatException e) {}
		
		statement.setInt(index, value);
	}
}
