package crowdcenter.support.id;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import crowdcenter.db.ConnectionFactory;

public class IdSetter {
	
	public static void main(String[] args) throws Exception {
		new IdSetter().execute();
	}

	private void execute() throws Exception {
		Connection conn = ConnectionFactory.getConnection();
		ResultSet rs = conn.createStatement().executeQuery(
				"SELECT domain, company_name, register_origin, register_origin_id FROM appdata.consolidated_data where id is null");
		
		Integer id = initialId(conn);
		System.out.println("Initial ID: " + id);
		while (rs.next()) {
			String domain = rs.getString(1);
			String company_name = rs.getString(2);
			Integer register_origin = rs.getInt(3);
			Integer register_origin_id = rs.getInt(4);
			
			String sql = sql(++id, register_origin, register_origin_id);
			
			conn.createStatement().executeUpdate(sql);
			System.out.println(" ID: " + id + " REG: " + (domain == null ? company_name : domain));
		}
	}

	private String sql(Integer id, Integer register_origin, Integer register_origin_id) {
		String sql = "update appdata.consolidated_data set id = " + id +" where ";
		sql += "register_origin = " + register_origin;
		sql += " and ";
		sql += "register_origin_id = " + register_origin_id;

		return sql;
	}
	
	private Integer initialId(Connection conn) throws SQLException {
		String sql = "select max(id) from consolidated_data";
		ResultSet rs = conn.createStatement().executeQuery(sql);
		
		return rs.next() ? rs.getInt(1) : 0;
	}
}
