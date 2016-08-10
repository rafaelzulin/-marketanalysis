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

public class AppStoreDataBaseOutput implements Output {
	private Logger logger = LogManager.getFormatterLogger(AppStoreDataBaseOutput.class);
	private Connection conn;
	private Map<Integer, String> values;
	private PreparedStatement statement;
	
	public AppStoreDataBaseOutput() throws Exception {
		this.conn = ConnectionFactory.getConnection();
		this.values = new HashMap<>();
		this.statement = conn.prepareStatement(sql());
	}
	
	private String sql() {
		return "INSERT INTO app_store " +
			"	(" +
			"		url, " + 
			"		name, " + 
			"		developername, " +
			"		developerurl, " +
			"		price, " + 
			"		category, " + 
			"		updatedate, " +
			"		version, " + 
			"		languages, " + 
			"		starscurrversion, " +
			"		starsallversions, " + 
			"		ratingcurrversion, " +
			"		ratingallversions, " +
			"		developerwebsite, " +
			"		supportwebsite, " +
			"		import_time" +
			"	) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	}
	
	@Override
	public void setField(Integer index, String value) {
		values.put(index, value);
		

	}
	
	@SuppressWarnings("unused")
	@Override
	public void flush() throws Exception {
		String url = values.get(0);
		String name = values.get(1);
		String developerName = values.get(2);
		String developerUrl = values.get(3);
		String price = values.get(4);
		String isFree = values.get(5);
		String thumbnailUrl = values.get(6);
		String compatibility = values.get(7);
		String category = values.get(8);
		String updateDate = values.get(9);
		String version = values.get(10);
		String size = values.get(11);
		String languages = values.get(12);
		String minimumAge = values.get(13);
		String ageRatingReasons = values.get(14);
		String rating_starsRatingCurrentVersion = values.get(15);
		String rating_starsVersionAllVersions = values.get(16);
		String rating_ratingsCurrentVersion = values.get(17);
		String rating_ratingsAllVersions = values.get(18);
		String developerWebsite = values.get(19);
		String supportWebsite = values.get(20);
		String licenseAgreement = values.get(21);
		String description = values.get(22);
		
		setString(1, url);
		setString(2, name);
		setString(3, developerName);
		setString(4, developerUrl);
		setFloat(5, price);
		setString(6, category);
		setDate(7, updateDate);
		setString(8, version);
		setString(9, languages);
		setInt(10, rating_starsRatingCurrentVersion);
		setInt(11, rating_starsVersionAllVersions);
		setInt(12, rating_ratingsCurrentVersion);
		setInt(13, rating_ratingsAllVersions);
		setString(14, developerWebsite);
		setString(15, supportWebsite);
		statement.setTimestamp(16, new Timestamp(new Date().getTime()));
		
		try {
			statement.executeUpdate();
			logger.info("Record was successfully outputed: " + values.get(1));
		} catch (SQLIntegrityConstraintViolationException e) {
			logger.error("Failed to insert. Constraint violation: " + e.getMessage());
		} catch (SQLException e) {
			if (! e.getMessage().startsWith("Incorrect string value"))
				throw new SQLException("Some error has ocurred when tried to insert in the BD: " + e.getMessage(), e);
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
