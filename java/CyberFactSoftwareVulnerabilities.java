import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;

public class CyberFactSoftwareVulnerabilities {
    
    private static final String API_BASE_URL = "https://www.surfwatchlabs.com:443/api/v3";

    public static void main( String[] args ) throws Exception {

        Client restClient = ClientBuilder.newClient();
        WebTarget target = restClient.target( API_BASE_URL )
                .path( "/cyberFacts/softwareVulnerabilities" )
                .queryParam( "startDate", "2015-06-25" )
                .queryParam( "endDate", "2015-06-25" );

        MultivaluedMap headers = new MultivaluedHashMap<>();
        headers.add( "app_id", "your_app_id" );
        headers.add( "app_key", "your_app_key" );

        String response = target
                .request( MediaType.APPLICATION_JSON )  // alternately set "Content-Type" header
                .headers( headers )
                .get( String.class );

        System.out.print( response );
    }
    
}