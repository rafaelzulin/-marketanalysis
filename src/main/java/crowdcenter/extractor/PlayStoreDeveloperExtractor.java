package crowdcenter.extractor;

import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import crowdcenter.db.ConnectionFactory;

public class PlayStoreDeveloperExtractor {
	
	private Logger logger = LogManager.getLogger(PlayStoreDeveloperExtractor.class);
	private final Integer LIMIT = 1000;
	private Connection conn;
	private PreparedStatement selectPlayStorePrepStatement;
	private PreparedStatement updatePlayStoreDevPrepStatement;

	public static void main(String[] args) throws Exception {
		new PlayStoreDeveloperExtractor().execute();
	}
	
	public PlayStoreDeveloperExtractor() throws Exception {
		logger.info("Creating connections...");
		conn = ConnectionFactory.getConnection();
		selectPlayStorePrepStatement = conn.prepareStatement("select developer, istopdeveloper, developerurl, developerdomain, instalations, developeremail, developerwebsite, physicaladdress from play_store where developer = ?");
		updatePlayStoreDevPrepStatement = conn.prepareStatement("update play_store_developers set istopdeveloper = ?, developerurl = ?, developerdomain = ?, instalations = ?, developeremail = ?, developerwebsite = ?,  physicaladdress = ?, processed = 1 where developer = ?");
	}

	private void execute() throws Exception {
		logger.info("Starting process");
		
		logger.info("Default Charset: " + Charset.defaultCharset());
		logger.info("File encoding: " + System.getProperty("file.encoding"));

		String selectPlayStoreDevelopers  = "select developer from play_store_developers where processed=0 order by qty_apps desc limit " + LIMIT;
		
		while(true) {
			List<String> developers = getDevelopers(selectPlayStoreDevelopers);
			
			if (developers.isEmpty()) {
				logger.info("Finishing proccess");
				break;
			}
			
			Map<String, List<DeveloperData>> developersData = getDevelopersData(developers);
			
			Map<String, DeveloperData> processedDevelopersData = processDevelopersData(developersData);
			
			updataDataBase(processedDevelopersData);
			logger.info("Cycle finished");
		}
	}

	private void updataDataBase(Map<String, DeveloperData> processedDevelopersData) throws SQLException {
		for (String developer : processedDevelopersData.keySet()) {
			logger.info("Updating " + developer);
			DeveloperData data = processedDevelopersData.get(developer);
			
			updatePlayStoreDevPrepStatement.setInt(1, data.istopdeveloper);
			updatePlayStoreDevPrepStatement.setString(2, data.developerurl);
			updatePlayStoreDevPrepStatement.setString(3, data.developerdomain);
			
			try {
				updatePlayStoreDevPrepStatement.setInt(4, Integer.valueOf(data.instalations));
			} catch (NumberFormatException e) {
				updatePlayStoreDevPrepStatement.setInt(4, 0);
			}
			
			updatePlayStoreDevPrepStatement.setString(5, data.developeremail);
			updatePlayStoreDevPrepStatement.setString(6, data.developerwebsite);
			updatePlayStoreDevPrepStatement.setString(7, data.physicaladdress);
			updatePlayStoreDevPrepStatement.setString(8, developer);
			
			updatePlayStoreDevPrepStatement.executeUpdate();
		}
		
		
	}

	private List<String> getDevelopers(String sql) throws SQLException {
		logger.info("Getting developers...");
		List<String> developers = new ArrayList<String>();
		
		ResultSet rs = conn.createStatement().executeQuery(sql);
		
		while(rs.next()) {
			developers.add(rs.getString(1));
		}
		
		logger.info("#developers: " + developers.size());
		return developers;
	}
	
	private Map<String, List<DeveloperData>> getDevelopersData(List<String> developers) throws SQLException {
		Map<String, List<DeveloperData>> map = new HashMap<>();
		
		for (String developer : developers) {
			logger.info("Getting data for " + developer);
			
			List<DeveloperData> list = new ArrayList<>();
			selectPlayStorePrepStatement.setString(1, developer);
			
			ResultSet rs = selectPlayStorePrepStatement.executeQuery();
			
			while (rs.next()) {
				DeveloperData developerData = new DeveloperData();
				developerData.istopdeveloper = rs.getInt("istopdeveloper");
				developerData.developerurl = rs.getString("developerurl");
				developerData.developerdomain = rs.getString("developerdomain");
				developerData.instalations = rs.getString("instalations");
				developerData.developeremail = rs.getString("developeremail");; 
				developerData.developerwebsite = rs.getString("developerwebsite");;
				developerData.physicaladdress = rs.getString("physicaladdress");;
				
				list.add(developerData);
			}
			map.put(developer, list);
		}
		return map;
	}
	
	private Map<String, DeveloperData> processDevelopersData(Map<String, List<DeveloperData>> developersData) {
		Map<String, DeveloperData> resultMap = new HashMap<>();
		
		for (String developer : developersData.keySet()) {
			List<DeveloperData> list = developersData.get(developer);
			
			logger.info("Processing " + developer);
			
			Integer istopdeveloper = 0;
			Map<String, Integer> developerurl = new HashMap<>();
			Map<String, Integer> developerdomain = new HashMap<>();
			Map<String, Integer> instalations = new HashMap<>();
			Map<String, Integer> developeremail = new HashMap<>();
			Map<String, Integer> developerwebsite = new HashMap<>();
			Map<String, Integer> physicaladdress = new HashMap<>();
			
			for (DeveloperData developerData : list) {
				if (istopdeveloper == 0) {
					if (developerData.istopdeveloper == 1) istopdeveloper = 1;
				}
				
				valueControl(developerurl, developerData.developerurl);
				valueControl(developerdomain, developerData.developerdomain);
				valueControl(instalations, developerData.instalations);
				valueControl(developeremail, developerData.developeremail);
				valueControl(developerwebsite, developerData.developerwebsite);
				valueControl(physicaladdress, developerData.physicaladdress);
			}
			
			DeveloperData result = new DeveloperData();
			result.istopdeveloper = istopdeveloper;
			result.developerurl = valueReturn(developerurl);
			result.developerdomain = valueReturn(developerdomain);
			result.instalations = valueReturnInstalations(instalations);
			result.developeremail = valueReturn(developeremail);
			result.developerwebsite = valueReturn(developerwebsite);
			result.physicaladdress = valueReturn(physicaladdress);
			
			resultMap.put(developer, result);
		}
		return resultMap;
	}

	private void valueControl(Map<String, Integer> map, String key) {
		if (key == null || key.isEmpty()) return;
		
		if (map.containsKey(key)) {
			map.put(key, map.get(key) + 1);
		} else {
			map.put(key, 1);
		}
	}
	
	private String valueReturn(Map<String, Integer> map) {
		if (map == null || map.isEmpty()) return null;
		Integer maxValue = 0;
		String keyMaxValue = null;
		
		for (String key : map.keySet()) {
			Integer value = map.get(key);
			if (value > maxValue) {
				maxValue = value;
				keyMaxValue = key;
			}
		}
		return keyMaxValue;
	}
	
	private String valueReturnInstalations(Map<String, Integer> map) {
		if (map == null || map.isEmpty()) return null;
		
		Integer sum = 0;
		
		for (String key : map.keySet()) {
			Integer value = map.get(key);
			sum = sum + instalationsConversition(key) * value;
		}
		return String.valueOf(sum);
	}
	
	private Integer instalationsConversition(String instalations) {
		switch (instalations) {
		case "1 - 5": 
			return 1;
		case "5 - 10": 
			return 5;
		case "10 - 50": 
			return 10;
		case "50 - 100": 
			return 50;
		case "100 - 500": 
			return 100;
		case "500 - 1,000": 
			return 500;
		case "1,000 - 5,000": 
			return 1000;
		case "5,000 - 10,000": 
			return 5000;
		case "10,000 - 50,000": 
			return 10000;
		case "50,000 - 100,000": 
			return 50000;
		case "100,000 - 500,000": 
			return 100000;
		case "500,000 - 1,000,000": 
			return 500000;
		case "1,000,000 - 5,000,000": 
			return 1000000;
		case "5,000,000 - 10,000,000": 
			return 5000000;
		case "10,000,000 - 50,000,000": 
			return 10000000;
		case "50,000,000 - 100,000,000": 
			return 50000000;
		case "100,000,000 - 500,000,000": 
			return 100000000;
		case "500,000,000 - 1,000,000,000": 
			return 500000000;
		case "1,000,000,000 - 5,000,000,000": 
			return 1000000000;
		default: 
			return 0; 
		}
	}

	public class DeveloperData {
		public Integer istopdeveloper;
		public String developerurl;
		public String developerdomain;
		public String instalations;
		public String developeremail;
		public String developerwebsite;
		public String physicaladdress;
	}
	
	/*INSERT INTO appdata.app_store_developers (
			developername,
			sum_starsallversions,
			sum_ratingallversions,
			qty_apps
		)
		select developername
			, sum(starsallversions) sum_starsallversions
			, sum(ratingallversions) sum_ratingallversions
			, count(developername) qty_apps
		from app_store
		group by developername
		order by qty_apps desc
		;*/
}
