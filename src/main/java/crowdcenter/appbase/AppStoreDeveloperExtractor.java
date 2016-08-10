package crowdcenter.appbase;

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

public class AppStoreDeveloperExtractor {
	
	private Logger logger = LogManager.getLogger(AppStoreDeveloperExtractor.class);
	private final Integer LIMIT = 1000;
	private Connection conn;
	private PreparedStatement selectAppStorePrepStatement;
	private PreparedStatement updateAppStoreDevPrepStatement;

	public static void main(String[] args) throws Exception {
		new AppStoreDeveloperExtractor().execute();
	}
	
	public AppStoreDeveloperExtractor() throws Exception {
		logger.info("Creating connections...");
		conn = ConnectionFactory.getConnection();
		selectAppStorePrepStatement = conn.prepareStatement("select developername, developerurl, languages, developerwebsite, supportwebsite from app_store where developername = ?");
		updateAppStoreDevPrepStatement = conn.prepareStatement("update app_store_developers set developerwebsite = ?, supportwebsite = ?, developerurl = ?, languages = ?, processed = 1 where developername = ?");
	}

	private void execute() throws Exception {
		logger.info("Starting process");
		
		logger.info("Default Charset: " + Charset.defaultCharset());
		logger.info("File encoding: " + System.getProperty("file.encoding"));

		String selectAppStoreDevelopers  = "select developername from app_store_developers where processed=0 order by qty_apps desc limit " + LIMIT;
		
		while(true) {
			List<String> developers = getDevelopers(selectAppStoreDevelopers);
			
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
			
			updateAppStoreDevPrepStatement.setString(1, data.developerwebsite);
			updateAppStoreDevPrepStatement.setString(2, data.supportwebsite);
			updateAppStoreDevPrepStatement.setString(3, data.developerurl);
			updateAppStoreDevPrepStatement.setString(4, data.languages);
			updateAppStoreDevPrepStatement.setString(5, developer);
			
			updateAppStoreDevPrepStatement.executeUpdate();
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
			selectAppStorePrepStatement.setString(1, developer);
			
			ResultSet rs = selectAppStorePrepStatement.executeQuery();
			
			while (rs.next()) {
				DeveloperData developerData = new DeveloperData();
				developerData.developerurl = rs.getString("developerurl");
				developerData.languages = rs.getString("languages");
				developerData.developerwebsite = rs.getString("developerwebsite");
				developerData.supportwebsite = rs.getString("supportwebsite");
				
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
			
			String url = null;
			String site = null;
			String supportsite = null;
			List<String> languagesList = new ArrayList<>();
			
			for (DeveloperData developerData : list) {
				if (url == null) {
					if (!(developerData.developerurl == null || developerData.developerurl.isEmpty())) url = developerData.developerurl;
				}
				
				if (site == null) {
					if (!(developerData.developerwebsite == null || developerData.developerwebsite.isEmpty())) site = developerData.developerwebsite;
				}
				
				if (supportsite == null) {
					if (!(developerData.supportwebsite == null || developerData.supportwebsite.isEmpty())) supportsite = developerData.supportwebsite;
				}
				
				proccessLanguages(developerData.languages, languagesList);
			}
			
			DeveloperData result = new DeveloperData();
			result.developerurl = url;
			result.developerwebsite = site;
			result.supportwebsite = supportsite;
			result.languages = serializeLanguages(languagesList);
			
			resultMap.put(developer, result);
		}
		return resultMap;
	}
	
	private String serializeLanguages(List<String> languagesList) {
		if (languagesList.isEmpty()) return null;
		
		String result = new String();
		for (String language : languagesList) {
			if (! result.isEmpty()) result = result + "|";
			result = result + language;
		}
		return result;
	}

	private void proccessLanguages(String languages, List<String> languagesList) {
		if (languages == null || languages.isEmpty()) return;
		
		String[] arrLanguages = languages.split("[|]");
		for (String language : arrLanguages) {
			if (languagesList.contains(language)) continue;
			languagesList.add(language);
		}
	}

	public class DeveloperData {
		public String developerurl;
		public String languages;
		public String developerwebsite;
		public String supportwebsite;
		
		@Override
		public String toString() {
			return "DeveloperData [developerurl=" + developerurl + ", languages=" + languages + ", developerwebsite="
					+ developerwebsite + ", supportwebsite=" + supportwebsite + "]";
		}
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
