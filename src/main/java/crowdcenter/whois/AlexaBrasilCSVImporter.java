package crowdcenter.whois;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Iterator;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import crowdcenter.db.ConnectionFactory;

public class AlexaBrasilCSVImporter {
	
	private Connection conn;
	
	public static void main(String[] args) {
		String filePath = null;
		try {
			filePath = args[0];
		} catch (Exception e) {
			System.out.println("The file is required as param");
			System.exit(0);
		}
		
		File file = new File(filePath);
		if (! file.exists()) {
			System.out.println("File not found");
			System.exit(0);
		}				
		
		try {
			new AlexaBrasilCSVImporter().run(file);
		} catch (Exception e) {
			System.out.println("Some error has ocurred: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	public AlexaBrasilCSVImporter() throws Exception {
		conn = ConnectionFactory.getConnection();
	}
	
	public void run(File file) throws SQLException, IOException {
		CSVParser parser = null;
		try {
			parser = new CSVParser(new FileReader(file), buildFormat());
		} catch (IOException e) {
			System.out.println("Error try to opening file: " + file);
			e.printStackTrace();
			System.exit(0);
		}
		
		String sql = "INSERT INTO alexa_brasil (domain, language, global_rank, medium_time, sufix, rank, category, sub_category) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
		PreparedStatement statement = conn.prepareStatement(sql);
		
		Iterator<CSVRecord> iterator = parser.iterator();
		
		while (iterator.hasNext()) {
			CSVRecord record = (CSVRecord) iterator.next();
			
			String domain = record.get("domain").trim();
			String language = record.get("language").trim();
			Integer global_rank = Integer.valueOf(record.get("global_rank").trim());
			Integer medium_time = Integer.valueOf(record.get("medium_time").trim());
			String sufix = record.get("sufix").trim();
			String rank = record.get("rank").trim();
			String category = record.get("category").trim();
			String sub_category = record.get("sub_category").trim();
			
			statement.setString(1, domain);
			statement.setString(2, language);
			statement.setInt(3, global_rank);
			statement.setInt(4, medium_time);
			statement.setString(5, sufix);
			statement.setString(6, rank);
			statement.setString(7, category);
			statement.setString(8, sub_category);
			
			try {
				statement.execute();
				System.out.println("Domain was successfully insert: " + domain);
			} catch (SQLIntegrityConstraintViolationException e) {
				System.out.println("Failed to insert.Duplicated domain: " + domain);
			} catch (SQLException e) {
				System.out.println("Some error has ocurred when tried to insert the domain in the BD: " + domain);
				e.printStackTrace();
			} 
		}
		parser.close();
	}

	private CSVFormat buildFormat() {
		return CSVFormat.newFormat(',')
				.withHeader()
				.withIgnoreEmptyLines()
				.withIgnoreHeaderCase()
				.withRecordSeparator('\n');
	}
}
