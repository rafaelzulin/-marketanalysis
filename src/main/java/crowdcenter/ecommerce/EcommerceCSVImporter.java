package crowdcenter.ecommerce;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import crowdcenter.aa.db.ConnectionFactory;

public class EcommerceCSVImporter {
	
	private Connection conn;
	Map<String, Float> valuesMap = new HashMap<String, Float>();
			
	public static void main(String[] args) {
		String filePath = "";
		try {
			filePath = args[0];
		} catch (Exception e) {
			log("ERROR: A CVS file is required as param");
			System.exit(0);
		}
		
		File file = new File(filePath);
		if (! file.exists()) {
			log("ERROR: File not found: " + filePath);
			System.exit(0);
		}			
		
		try {
			new EcommerceCSVImporter().run(file);
		} catch (Exception e) {
			log("ERROR: ["+ new Date() +"] Some error has ocurred: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	public EcommerceCSVImporter() throws Exception {
		conn = ConnectionFactory.getConnection();
	}
	
	public void run(File file) throws SQLException, IOException {
		log("Process started!!!");
		
		CSVParser parser = null;
		try {
			parser = new CSVParser(new FileReader(file), buildFormat());
		} catch (IOException e) {
			log("Error when trying to open file: " + file);
			e.printStackTrace();
			System.exit(0);
		}
		
		String sql = "INSERT INTO ecommerce ( cnpj, cnae_primario, cnae_descricao, razao_social, endereco, bairro, cidade, cep) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
		PreparedStatement statement = conn.prepareStatement(sql);
		
		Iterator<CSVRecord> iterator = parser.iterator();

		log("Initializing CVS parser");
		
		while (iterator.hasNext()) {
			CSVRecord record = (CSVRecord) iterator.next();
			
			if (! record.isConsistent()) continue;
			
			String cnpj = record.get("cnpj").trim();
			String cnae_primario = record.get("cnae_primario").trim();
			String cnae_descricao = record.get("cnae_descricao").trim();
			String razao_social = record.get("razao_social").trim();
			String endereco = record.get("endereco").trim();
			String bairro = record.get("bairro").trim();
			String cidade = record.get("cidade").trim();
			String cep = record.get("cep").trim();
			Float value = converts(record.get("item_valor_total").trim());
			
			if (cnpj == null || cnpj.isEmpty()) continue;
			
			if (valuesMap.containsKey(cnpj)) {
				valuesMap.put(cnpj, valuesMap.get(cnpj) + value);
				continue;
			} else { 
				valuesMap.put(cnpj, value);
			}

			statement.setString(1, cnpj);
			statement.setString(2, validates(cnae_primario));
			statement.setString(3, validates(cnae_descricao));
			statement.setString(4, validates(razao_social));
			statement.setString(5, validates(endereco));
			statement.setString(6, validates(bairro));
			statement.setString(7, validates(cidade));
			statement.setString(8, validates(cep));

			
			try {
				statement.execute();
				log("Register was successfully inserted..." + cnpj);
			} catch (SQLIntegrityConstraintViolationException e) {
				log("Failed to insert: " + cnpj + " - " + e.getMessage());
			} catch (SQLException e) {
				log("Some error has occurred when tried to insert in the BD: " + cnpj + " - " + e.getMessage());
			} 
		}
		parser.close();
		
		log("Parsing has finished");
		log("Update values");
		
		statement = conn.prepareStatement("UPDATE ecommerce SET total_vendido=? WHERE cnpj=?");
		
		for(String key : valuesMap.keySet()) {
			Float value = valuesMap.get(key);
			
			log("Updating: " + key + "=" + value);
			
			statement.setFloat(1, value);
			statement.setString(2, key);
			
			statement.executeUpdate();
		}
				
		Date finishTime = new Date();
		System.out.println("Process finished at " + finishTime);
	}
	
	private static void log(String message) {
		System.out.println("[" + new Date() + "] " + message);
	}
	
	private Float converts(String value) {
		if (value == null) return 0f;
		try {
			return Float.valueOf(value);
		} catch (NumberFormatException e) {
			return 0f;
		}
	}

	private String validates(String str) {
		if ("undefined".equals(str)) return null;
		if ("undefined undefined".equals(str)) return null;
		if ("NAO_ENCONTRADO".equals(str)) return null;
		return str;
	}

	private CSVFormat buildFormat() {
		return CSVFormat.newFormat('|')
				.withHeader()
				.withIgnoreEmptyLines()
				.withIgnoreHeaderCase()
				.withRecordSeparator('\n');
	}
}
