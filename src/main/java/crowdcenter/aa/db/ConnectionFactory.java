package crowdcenter.aa.db;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionFactory {

	public static Connection conn;

	public static Connection getConnection() throws Exception {
		if (conn == null) {
			try {
				String mode = System.getProperty("mode", "test");
				String dataBaseUser = System.getProperty("dataBaseUser");
				String testDataBaseUser = System.getProperty("testDataBaseUser");
				String dataBasePassword = System.getProperty("dataBasePassword", "");
				String testDataBasePassword = System.getProperty("testDataBasePassword", "");
				String accessPoint = System.getProperty("accessPoint");
				String testAccessPoint = System.getProperty("testAccessPoint");

				if (!"test".equals(mode) && !"production".equals(mode)) throw new RuntimeException("Invalid mode: " + mode);
				
				String url = "production".equals(mode) ? accessPoint : testAccessPoint;
				String user = "production".equals(mode) ? dataBaseUser : testDataBaseUser;
				String pw = "production".equals(mode) ? dataBasePassword : testDataBasePassword;
				
				Class.forName("com.mysql.jdbc.Driver");
				conn = DriverManager.getConnection("jdbc:mysql://" + url + "/appdata?useSSL=false", user, pw);
			} catch (Exception e) {
				throw new Exception("Connection error. Verify VM params", e);
			}
		}
		return conn;
	}
}
