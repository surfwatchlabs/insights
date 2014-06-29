import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;

public class IndustryTargetSummaries {
    
    private static final String API_BASE_URL = "https://www.surfwatchanalytics.com:443/v2";

    public static void main( String[] args ) throws Exception {
        
        Client restClient = ClientBuilder.newClient();  
        WebTarget target = restClient.target( API_BASE_URL )
                .path( "/industryTargetSummaries" )
                .queryParam( "startDate", "2014-06-11" )
                .queryParam( "endDate", "2014-06-11" );

        MultivaluedMap<String, Object> headers = new MultivaluedHashMap<>();
        headers.add( "app_id", "your_app_id" );
        headers.add( "app_key", "your_app_key" );
        
        String response = target
                .request( MediaType.APPLICATION_JSON )  // alternately set "Content-Type" header
                .headers( headers )
                .get( String.class );
        
        System.out.print( response );
    }       
}
