package crowdcenter.whois;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.TimeZone;

import com.fasterxml.jackson.core.JsonGenerator.Feature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import crowdcenter.aa.db.ConnectionFactory;

public class WhoisParser {
	
	Connection conn;

	public static void main(String[] args) {
		TimeZone.setDefault(TimeZone.getTimeZone("GMT"));
		try {
			new WhoisParser().run();
		} catch (Exception e) {
			System.err.println("Something went wrong");
			e.printStackTrace();
		}
	}
	
	public WhoisParser() throws Exception {
		conn = ConnectionFactory.getConnection();
	}
	
	public void run() throws SQLException {
		ResultSet rs = null;
		PreparedStatement statement = null;
		try {
			rs = conn.createStatement().executeQuery("SELECT domain, whoxy_whois FROM alexa_brasil WHERE whoxy_whois IS NOT NULL AND processed = 0 ORDER BY global_rank");
			statement = conn.prepareStatement(sql());
		} catch (SQLException e) {
			System.err.println("Critical error trying acess data base");
			e.printStackTrace();
			System.exit(0);
		}
		
		ObjectMapper mapper = new ObjectMapper();
		
		while (rs.next()) {
			String domain = rs.getString(1);
			String jsonRecord = rs.getString(2);
			
			System.out.print("Processing domain: " + domain + "...");
			
			WhoisType whois = null;
			try {
				whois = mapper.readValue(jsonRecord, WhoisType.class);
			} catch (Exception e) {
				System.err.println("Parse the register have failed: " + domain);
				System.out.println(e.getMessage());
				continue;
			}
			
			statement.setString(1, domain); //original_domain
			statement.setString(2, whois.domain_name); //domain_name
			statement.setDate(3, new Date(whois.query_time.getTime())); //query_time
			statement.setString(4, whois.whois_server); //whois_server
			statement.setString(5, whois.domain_registered); //domain_registered
			statement.setDate(6, whois.create_date == null ? null : new Date(whois.create_date.getTime())); //create_date
			statement.setDate(7, whois.update_date == null ? null : new Date(whois.update_date.getTime())); //update_date
			statement.setDate(8, whois.expiry_date == null ? null : new Date(whois.expiry_date.getTime())); //expiry_date
			statement.setString(9, whois.getDomain_registrar().iana_id); 
			statement.setString(10, whois.getDomain_registrar().registrar_name); //domain_registrar_registrar_name
			statement.setString(11, whois.getDomain_registrar().whois_server); //domain_registrar_whois_server
			statement.setString(12, whois.getDomain_registrar().website_url); //domain_registrar_website_url
			statement.setString(13, whois.getDomain_registrar().email_address); //domain_registrar_email_address
			statement.setString(14, whois.getDomain_registrar().phone_number); //domain_registrar_phone_number
			statement.setString(15, whois.getRegistrant_contact().full_name); //registrant_contact_full_name
			statement.setString(16, whois.getRegistrant_contact().company_name); //registrant_contact_company_name
			statement.setString(17, whois.getRegistrant_contact().mailing_address); //registrant_contact_mailing_address
			statement.setString(18, whois.getRegistrant_contact().city_name); //registrant_contact_city_name
			statement.setString(19, whois.getRegistrant_contact().state_name); //registrant_contact_state_name
			statement.setString(20, whois.getRegistrant_contact().zip_code); //registrant_contact_zip_code
			statement.setString(21, whois.getRegistrant_contact().country_name); //registrant_contact_country_name
			statement.setString(22, whois.getRegistrant_contact().country_code); //registrant_contact_country_code
			statement.setString(23, whois.getRegistrant_contact().email_address); //registrant_contact_email_address
			statement.setString(24, whois.getRegistrant_contact().phone_number); //registrant_contact_phone_numberfax_number
			statement.setString(25, whois.getRegistrant_contact().fax_number); //registrant_contact_fax_number
			statement.setString(26, whois.getAdministrative_contact().full_name); //administrative_contact_full_name
			statement.setString(27, whois.getAdministrative_contact().company_name); //administrative_contact_company_name
			statement.setString(28, whois.getAdministrative_contact().mailing_address); //administrative_contact_mailing_address	
			statement.setString(29, whois.getAdministrative_contact().city_name); //administrative_contact_city_name
			statement.setString(30, whois.getAdministrative_contact().state_name); //administrative_contact_state_name
			statement.setString(31, whois.getAdministrative_contact().zip_code); //administrative_contact_zip_code
			statement.setString(32, whois.getAdministrative_contact().country_name); //administrative_contact_country_name
			statement.setString(33, whois.getAdministrative_contact().country_code); //administrative_contact_country_code
			statement.setString(34, whois.getAdministrative_contact().email_address); //administrative_contact_email_address
			statement.setString(35, whois.getAdministrative_contact().phone_number); //administrative_contact_phone_number
			statement.setString(36, whois.getAdministrative_contact().fax_number); //administrative_contact_fax_number
			statement.setString(37, whois.getTechnical_contact().full_name); //technical_contact_full_name
			statement.setString(38, whois.getTechnical_contact().company_name); //technical_contact_company_name
			statement.setString(39, whois.getTechnical_contact().mailing_address); //technical_contact_mailing_address
			statement.setString(40, whois.getTechnical_contact().city_name); //technical_contact_city_name
			statement.setString(41, whois.getTechnical_contact().state_name); //technical_contact_state_name
			statement.setString(42, whois.getTechnical_contact().zip_code); //technical_contact_zip_code
			statement.setString(43, whois.getTechnical_contact().country_name); //technical_contact_country_name
			statement.setString(44, whois.getTechnical_contact().country_code); //technical_contact_country_code
			statement.setString(45, whois.getTechnical_contact().email_address); //technical_contact_email_address
			statement.setString(46, whois.getTechnical_contact().phone_number); //technical_contact_phone_number
			statement.setString(47, whois.getTechnical_contact().fax_number); //technical_contact_fax_number
			statement.setString(48, whois.getBilling_contact().full_name); //billing_contact_full_name
			statement.setString(49, whois.getBilling_contact().company_name); //billing_contact_company_name
			statement.setString(50, whois.getBilling_contact().mailing_address); //billing_contact_mailing_address
			statement.setString(51, whois.getBilling_contact().city_name); //billing_contact_city_name
			statement.setString(52, whois.getBilling_contact().state_name); //billing_contact_state_name
			statement.setString(53, whois.getBilling_contact().zip_code); //billing_contact_zip_code
			statement.setString(54, whois.getBilling_contact().country_name); //billing_contact_country_name
			statement.setString(55, whois.getBilling_contact().country_code); //billing_contact_country_code
			statement.setString(56, whois.getBilling_contact().email_address); //billing_contact_email_address
			statement.setString(57, whois.getBilling_contact().phone_number); //billing_contactt_phone_number
			statement.setString(58, whois.getBilling_contact().fax_number); //billing_contact_fax_number
			
			try {
				statement.execute();
				conn.createStatement().execute("UPDATE alexa_brasil SET processed=1 WHERE domain='" + domain + "'");
				System.out.println("DONE");
			} catch (SQLException e) {
				System.err.println("Somenthing went wrong.");
				e.printStackTrace();
			}
		}
	}
	
	private String sql() {
		return "INSERT INTO whois (" +
				"	original_domain, " +
				"	domain_name, " +
				"	query_time, " +
				"	whois_server, " +
				"	domain_registered, " +
				"	create_date, " +
				"	update_date, " +
				"	expiry_date, " +
				"	domain_registrar_iana_id, " +
				"	domain_registrar_registrar_name, " +
				"	domain_registrar_whois_server, " +
				"	domain_registrar_website_url, " +
				"	domain_registrar_email_address, " +
				"	domain_registrar_phone_number, " +
				"	registrant_contact_full_name, " +
				"	registrant_contact_company_name, " +
				"	registrant_contact_mailing_address, " +
				"	registrant_contact_city_name, " +
				"	registrant_contact_state_name, " +
				"	registrant_contact_zip_code, " +
				"	registrant_contact_country_name, " +
				"	registrant_contact_country_code, " +
				"	registrant_contact_email_address, " +
				"	registrant_contact_phone_number, " +
				"	registrant_contact_fax_number, " +
				"	administrative_contact_full_name, " +
				"	administrative_contact_company_name, " +
				"	administrative_contact_mailing_address, " +
				"	administrative_contact_city_name, " +
				"	administrative_contact_state_name, " +
				"	administrative_contact_zip_code, " +
				"	administrative_contact_country_name, " +
				"	administrative_contact_country_code, " +
				"	administrative_contact_email_address, " +
				"	administrative_contact_phone_number, " +
				"	administrative_contact_fax_number, " +
				"	technical_contact_full_name, " +
				"	technical_contact_company_name, " +
				"	technical_contact_mailing_address, " +
				"	technical_contact_city_name, " +
				"	technical_contact_state_name, " +
				"	technical_contact_zip_code, " +
				"	technical_contact_country_name, " +
				"	technical_contact_country_code, " +
				"	technical_contact_email_address, " +
				"	technical_contact_phone_number, " +
				"	technical_contact_fax_number," +
				"	billing_contact_full_name, " + 
				"	billing_contact_company_name, " + 
				"	billing_contact_mailing_address, " + 
				"	billing_contact_city_name, " + 
				"	billing_contact_state_name, " + 
				"	billing_contact_zip_code, " + 
				"	billing_contact_country_name, " + 
				"	billing_contact_country_code, " + 
				"	billing_contact_email_address, " + 
				"	billing_contact_phone_number, " + 
				"	billing_contact_fax_number" +
				") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	}
	
	@SuppressWarnings("unused")
	private void printConfig(ObjectMapper mapper) {
		System.out.println("-------FEATURES-------");
		for (Feature feature : Feature.values()) {
			Boolean value = mapper.isEnabled(feature);
			System.out.println(feature + "=" + value);
		}
		
		System.out.println("\n----MAPPER FEATUES----");
		for (MapperFeature mapperFeature : MapperFeature.values()) {
			Boolean value = mapper.isEnabled(mapperFeature);
			System.out.println(mapperFeature + "=" + value);
		}
		
		System.out.println("\n----SERIAL FEATURES----");
		for (SerializationFeature serialFeatures : SerializationFeature.values()) {
			Boolean value = mapper.isEnabled(serialFeatures);
			System.out.println(serialFeatures + "=" + value);
		}
	}
}
