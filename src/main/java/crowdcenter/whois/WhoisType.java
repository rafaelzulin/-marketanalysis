package crowdcenter.whois;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class WhoisType implements Serializable {
	private static final long serialVersionUID = 1L;
	@JsonIgnore
	public Integer status;
	public String domain_name; //1
	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	public Date query_time; //2
	public String whois_server; //3
	public String domain_registered; //4
	public Date create_date; //5
	public Date update_date; //6
	public Date expiry_date; //7
	
	public DomainRegister domain_registrar; ///8-13
	public Contact registrant_contact; //14-24
	public Contact administrative_contact; //25-35
	public Contact technical_contact; //36-46
	public Contact billing_contact;
	
	@JsonIgnore
	public String[] name_servers;
	@JsonIgnore
	public String[] domain_status;
	@JsonIgnore
	public String raw_whois;
	
		
	public DomainRegister getDomain_registrar() {
		if (domain_registrar == null) return new DomainRegister();
		return domain_registrar;
	}

	public Contact getRegistrant_contact() {
		if (registrant_contact == null) return new Contact();
		return registrant_contact;
	}

	public Contact getAdministrative_contact() {
		if (administrative_contact == null) return new Contact();
		return administrative_contact;
	}

	public Contact getTechnical_contact() {
		if (technical_contact == null) return new Contact();
		return technical_contact;
	}
	
	public Contact getBilling_contact() {
		if (billing_contact == null) return new Contact();
		return billing_contact;
	}

	@Override
	public String toString() {
		return "WhoisType [status=" + status + ", domain_name=" + domain_name + ", query_time=" + query_time
				+ ", whois_server=" + whois_server + ", domain_registered=" + domain_registered + ", create_date="
				+ create_date + ", update_date=" + update_date + ", expiry_date=" + expiry_date + ", domain_registrar="
				+ domain_registrar + ", registrant_contact=" + registrant_contact + ", administrative_contact="
				+ administrative_contact + ", technical_contact=" + technical_contact + ", name_servers="
				+ Arrays.toString(name_servers) + ", domain_status=" + Arrays.toString(domain_status) + ", raw_whois="
				+ raw_whois + "]";
	}	

	public class DomainRegister {
		public String iana_id;
		public String registrar_name;
		public String whois_server;
		public String website_url;
		public String email_address;
		public String phone_number;
		
		@Override
		public String toString() {
			return "DomainRegister [iana_id=" + iana_id + ", registrar_name=" + registrar_name + ", whois_server="
					+ whois_server + ", website_url=" + website_url + ", email_address=" + email_address
					+ ", phone_number=" + phone_number + "]";
		}
	}
	
	public class Contact {
		public String full_name;
		public String company_name;
		public String mailing_address;
		public String city_name;
		public String state_name;
		public String zip_code;
		public String country_name;
		public String country_code;
		public String email_address;
		public String phone_number;
		public String fax_number;
		
		@Override
		public String toString() {
			return "Contact [full_name=" + full_name + ", company_name=" + company_name + ", mailing_address="
					+ mailing_address + ", city_name=" + city_name + ", state_name=" + state_name + ", zip_code="
					+ zip_code + ", country_name=" + country_name + ", country_code=" + country_code
					+ ", email_address=" + email_address + ", phone_number=" + phone_number + ", fax_number="
					+ fax_number + "]";
		}
	}
}
