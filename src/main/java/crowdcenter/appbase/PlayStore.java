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

public class PlayStore {
	
	public static final String TABLE_NAME = "play_store";

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
					"		position, " + //1
					"		name, " + //2
					"		url, " + //3
					"		appid, " + //4
					"		referencedate, " + //5
					"		developer, " + //6
					"		istopdeveloper, " + //7
					"		developerurl, " + //8
					"		developerdomain, " + //9
					"		category, " + //10
					"		isfree, " + //11
					"		price, " + //12
					"		score_total, " + //13
					"		score_count, " + //14
					"		score_fivestars, " + //15
					"		score_fourstars, " + //16
					"		score_threestars, " + //17
					"		score_twostars, " + //18
					"		score_onestars, " + //19
					"		instalations, " + //20
					"		currentversion, " + //21 
					"		haveinapppurchases, " + //22
					"		developeremail, " + //23 
					"		developerwebsite, " + //24
					"		physicaladdress, " + //25
					"		lastupdatedate, " + //26
					"		description" + //27
					"	) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)" ;
			
			PreparedStatement preparedStatement = conn.prepareStatement(statement);
			int position = 0;
			while (iterator.hasNext()) {
				CSVRecord record = (CSVRecord) iterator.next();
				
				if (record.isConsistent()) {
					position++;
					System.out.println(position);
					
					if (position <= startPos) continue;	
					
					preparedStatement.setInt(1, position);
					preparedStatement.setString(2, record.get(0)); //Name
					preparedStatement.setString(3, record.get("Url"));
					preparedStatement.setString(4, record.get("AppId"));
					preparedStatement.setDate(5, java.sql.Date.valueOf(record.get("ReferenceDate")));
					preparedStatement.setString(6, record.get("Developer"));
					preparedStatement.setString(7, record.get("IsTopDeveloper"));
					preparedStatement.setString(8, record.get("DeveloperURL"));
					preparedStatement.setString(9, record.get("DeveloperNormalizedDomain"));
					preparedStatement.setString(10, record.get("Category"));
					preparedStatement.setString(11, record.get("IsFree"));
					preparedStatement.setFloat(12, Float.parseFloat(record.get("Price")));
					preparedStatement.setFloat(13, Float.parseFloat(record.get("Score.Total")));
					preparedStatement.setInt(14, Integer.parseInt(record.get("Score.Count")));
					preparedStatement.setInt(15, Integer.parseInt(record.get("Score.FiveStars")));
					preparedStatement.setInt(16,Integer.parseInt(record.get("Score.FourStars")));
					preparedStatement.setInt(17, Integer.parseInt(record.get("Score.ThreeStars")));
					preparedStatement.setInt(18, Integer.parseInt(record.get("Score.TwoStars")));
					preparedStatement.setInt(19, Integer.parseInt(record.get("Score.OneStars")));
					preparedStatement.setString(20, record.get("Instalations"));
					preparedStatement.setString(21, trunc(record.get("CurrentVersion"), 100));
					preparedStatement.setString(22, record.get("HaveInAppPurchases"));
					preparedStatement.setString(23, record.get("DeveloperEmail"));
					preparedStatement.setString(24,	record.get("DeveloperWebsite"));
					preparedStatement.setString(25, record.get("PhysicalAddress"));
					preparedStatement.setDate(26, java.sql.Date.valueOf(record.get("LastUpdateDate")));
					preparedStatement.setString(27, record.get("Description"));
				
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

	private static String trunc(String string, int size) {
		if (string.length() >=  size) {
			return string.substring(0, size - 1);
		}
		return string;
	}
}
