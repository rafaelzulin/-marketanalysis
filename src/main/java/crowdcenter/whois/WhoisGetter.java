package crowdcenter.whois;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import crowdcenter.aa.db.ConnectionFactory;

public class WhoisGetter {
	
	Connection conn;
	
	public WhoisGetter() throws Exception {
		conn = ConnectionFactory.getConnection();
	}

	public static void main(String[] args) {
		String key = null;
		try {
			key = args[0];
		} catch (Exception e) {
			System.out.println("The key is required as param");
			System.exit(0);
		}
		
		try {
			new WhoisGetter().run(key);
		} catch (Exception e) {
			System.out.println("Some error has ocurred: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void run(String key) throws SQLException {
		Whoxy whoxy = new Whoxy(key);
		
		ResultSet rs = conn.createStatement().executeQuery("SELECT domain FROM alexa_brasil WHERE whoxy_whois IS NULL ORDER BY global_rank");
		int count = 1;
		int errorCount = 0;
		while (rs.next()) {
			if (errorCount > 50) break;
			
			String domain = rs.getString(1);
			
			System.out.print("Getting data for: " + domain + "...");
			
			String response;
			try {
				response = whoxy.getWhois(domain);
			} catch (WhoxyException e) {
				e.printStackTrace();
				errorCount++;
				continue;
			}
			
			response = response.replace("\\", "\\\\");
			response = response.replace("'", "\\'");
			response = response.replace("\"", "\\\"");
			
			Statement statement = conn.createStatement();
			String sql = "UPDATE alexa_brasil SET whoxy_whois='" + response + "' WHERE domain='" + domain +"'";
			statement.execute(sql);
			
			System.out.println("DONE: " + count++);
		}
	}
}
