package crowdcenter.whois;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

public class Whoxy {

	private Client client;
	private final String REST_SERVICE_URL = "http://api.whoxy.com/";
	private final String KEY;

	public Whoxy(String key) {
		this.client = ClientBuilder.newClient();
		this.KEY = key;
	}

	public String getWhois(String domain) throws WhoxyException {
		String response = client.target(REST_SERVICE_URL)
				.queryParam("key", KEY)
				.queryParam("whois", domain)
				.request()
				.get(String.class);
		
		if (! isValid(response)) throw new WhoxyException("Failed domain consult: " + domain); 
		return response;
	}
	
	public Boolean isValid(String response) {
		if (response == null) return false;
		
		if (response.indexOf("\"status\": 1") < 0) {
			return false;
		}
		return true;
	}
}
