package crowdcenter.appbase;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.Iterator;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import crowdcenter.db.ConnectionFactory;

public class AppStore {
	
	public static final String TABLE_NAME = "app_store";

	public static void main(String[] args) throws Exception {
		
		String fileLocation = args[0];
		
		Integer startPos;
		
		try {
			String $2 = args[1];
			startPos = Integer.valueOf($2);
		} catch (Exception e) {
			startPos = 0;
		}
		
		System.out.println("File: " + fileLocation);
		System.out.println("Start Position: " + startPos);

		Connection conn = null;
		FileOutputStream fos = null;
		CSVParser parser = null;
		try {
			File file = new File(fileLocation);
			
			Reader in = new FileReader(file);
			parser = new CSVParser(in, CSVFormat.newFormat(';').withHeader());
	
			Iterator<CSVRecord> iterator = parser.iterator();
	
			fos = new FileOutputStream("log.txt", true);
	
			conn = ConnectionFactory.getConnection();
			
			String statement = 
					"INSERT INTO " + TABLE_NAME +
							"	(" +
							"		position, " + // 1
							"		url, " + // 2
							"		name, " + // 3
							"		developername, " + // 4
							"		developerurl, " + // 5
							"		price, " + // 6
							"		isfree, " + // 7
							"		category, " + // 8
							"		updatedate, " + // 9
							"		version, " + // 10
							"		languages, " + // 11
							"		starscurrversion, " + // 12
							"		starsallversions, " + // 13 
							"		ratingcurrversion, " + // 14
							"		ratingallversions, " + // 15
							"		developerwebsite, " + // 16
							"		supportwebsite, " + // 17
							"		description" + //18
							"	) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			
			PreparedStatement preparedStatement = conn.prepareStatement(statement);
			int position = 0;
			while (iterator.hasNext()) {
				CSVRecord record = (CSVRecord) iterator.next();
				
				if (record.isConsistent()) {
					position++;
					System.out.println(position);
					
					if (position <= startPos) continue;	
					
					preparedStatement.setInt(1, position);
					preparedStatement.setString(2, record.get(0)); //url
					preparedStatement.setString(3, record.get("name"));
					preparedStatement.setString(4, record.get("developerName"));
					preparedStatement.setString(5, record.get("developerUrl"));
					preparedStatement.setFloat(6, Float.valueOf(record.get("price")));
					preparedStatement.setString(7, record.get("isFree"));
					preparedStatement.setString(8, record.get("category"));
					preparedStatement.setDate(9, java.sql.Date.valueOf(record.get("updateDate")));
					preparedStatement.setString(10, record.get("version"));
					preparedStatement.setString(11, record.get("languages"));
					preparedStatement.setInt(12, Integer.parseInt(record.get("rating.starsRatingCurrentVersion")));
					preparedStatement.setInt(13, Integer.parseInt(record.get("rating.starsVersionAllVersions")));
					preparedStatement.setInt(14, Integer.parseInt(record.get("rating.ratingsCurrentVersion")));
					preparedStatement.setInt(15, Integer.parseInt(record.get("rating.ratingsAllVersions")));
					preparedStatement.setString(16, record.get("developerWebsite"));
					preparedStatement.setString(17, record.get("supportWebsite"));
					preparedStatement.setString(18, record.get("description"));
					
					preparedStatement.executeUpdate();
				} else {
					fos.write(new Date().toString().getBytes());
					fos.write(("Line: " + record.getRecordNumber() + "\n").getBytes());
					fos.write(("Size: " + record.size() + "\n").getBytes());
					fos.write((record.toString() + "\n" + "\n").getBytes());
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				parser.close();
				fos.close();
				conn.close();
			} catch (Exception e) {
				
			}
		}			
	}
}
