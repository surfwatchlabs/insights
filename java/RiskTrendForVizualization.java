import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

public class RiskTrendForVizualization {
    
    private static final Gson gson;
    private static final String API_BASE_URL = "https://www.surfwatchlabs.com:443/api/v3";

    static {
        // deserialize to objects, could be done in conjunction with Jersey client
        GsonBuilder gb = new GsonBuilder();
        gb.setFieldNamingPolicy( FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES );
        // handle Joda DateTime object
        gb.registerTypeAdapter( DateTime.class, new DateTimeDeserializer() );
        gb.registerTypeAdapter( DateTime.class, new DateTimeSerializer() );
        gson = gb.create();
    }
    
    public static void main( String[] args ) throws Exception {
        
        MacroDelta macroDelta = updateMacroDelta( requestMacroTrendDeltaData( "2015-05-01", "2015-05-07" ), null );
        macroDelta = updateMacroDelta( requestMacroTrendDeltaData( "2015-05-08", "2015-05-14" ), macroDelta );
        macroDelta = updateMacroDelta( requestMacroTrendDeltaData( "2015-05-15", "2015-05-21" ), macroDelta );
        macroDelta = updateMacroDelta( requestMacroTrendDeltaData( "2015-05-22", "2015-05-28" ), macroDelta );
        macroDelta = updateMacroDelta( requestMacroTrendDeltaData( "2015-05-29", "2015-05-31" ), macroDelta );
        
        // create a chart using the json, or serialize to csv and use that
        System.out.println( gson.toJson( macroDelta ) );
    }
    
    private static String requestMacroTrendDeltaData( String startDate, String endDate ) {

        Client restClient = ClientBuilder.newClient();
        WebTarget target = restClient.target( API_BASE_URL )
                .path( "/summary/macroTrendDelta/monthly" )
                .queryParam( "startDate", startDate )
                .queryParam( "endDate", endDate )
                .queryParam( "tagSuperTypeId", 2 );

        MultivaluedMap headers = new MultivaluedHashMap<>();
        headers.add( "app_id", "your_app_id" );
        headers.add( "app_key", "your_app_key" );

        String response = target
                .request( MediaType.APPLICATION_JSON )  // alternately set "Content-Type" header
                .headers( headers )
                .get( String.class );
        
        restClient.close();

        // System.out.print( response );
        return response;
    }
    
    private static MacroDelta updateMacroDelta( String response, MacroDelta macroDeltaForMonth ) throws Exception {
        
        // deserialize
        Collection<MacroDelta> macroTrendDeltas = gson.fromJson( response, new TypeToken<List<MacroDelta>>(){}.getType() );
        
        // get Consumer Electronics macro delta
        MacroDelta macroDelta = getConsumerElectronics( macroTrendDeltas );
        
        // null on first response, return whole object
        if( macroDeltaForMonth == null )
            return macroDelta;
        
        // add all new deltas to existing object
        macroDeltaForMonth.getMacroTrendDeltas().addAll( macroDelta.getMacroTrendDeltas() );
        return macroDeltaForMonth;
    }
    
    private static MacroDelta getConsumerElectronics( Collection<MacroDelta> macroTrendDeltas ) throws Exception {
        for( MacroDelta md : macroTrendDeltas ) {
            if( md.getMacroTagId().equals( -238 ) )
                return md;
        }
        throw new Exception( "Response did not include expected Consumer Electronics macro" );
    }

    class MacroDelta {

        @SerializedName( "macro_tag_id")
        private Integer macroTagId;

        @SerializedName( "macro_tag")
        private String macroTag;

        @SerializedName( "tag_super_type_id")
        private Integer tagSuperTypeId;

        @SerializedName( "tag_super_type")
        private String tagSuperType;

        @SerializedName( "trend_delta_mean" )
        private Float trendDeltaMean;

        @SerializedName( "trend_delta_standard_dev" )
        private Float trendDeltaStandardDev;

        @SerializedName( "trend_delta_max" )
        private Float trendDeltaMax;

        @SerializedName( "trend_delta_min" )
        private Float trendDeltaMin;

        @SerializedName( "trend_delta_threshold" )
        private Float trendDeltaThreshold;

        @SerializedName( "deltas" )
        private List<MacroTrendDelta> macroTrendDeltas;

        
        public Integer getMacroTagId() {
            return macroTagId;
        }

        public void setMacroTagId(Integer macroTagId) {
            this.macroTagId = macroTagId;
        }

        public String getMacroTag() {
            return macroTag;
        }

        public void setMacroTag(String macroTag) {
            this.macroTag = macroTag;
        }

        public Integer getTagSuperTypeId() {
            return tagSuperTypeId;
        }

        public void setTagSuperTypeId(Integer tagSuperTypeId) {
            this.tagSuperTypeId = tagSuperTypeId;
        }

        public String getTagSuperType() {
            return tagSuperType;
        }

        public void setTagSuperType(String tagSuperType) {
            this.tagSuperType = tagSuperType;
        }

        public Float getTrendDeltaMean() {
            return trendDeltaMean;
        }

        public void setTrendDeltaMean(Float trendDeltaMean) {
            this.trendDeltaMean = trendDeltaMean;
        }

        public Float getTrendDeltaStandardDev() {
            return trendDeltaStandardDev;
        }

        public void setTrendDeltaStandardDev(Float trendDeltaStandardDev) {
            this.trendDeltaStandardDev = trendDeltaStandardDev;
        }

        public Float getTrendDeltaMax() {
            return trendDeltaMax;
        }

        public void setTrendDeltaMax(Float trendDeltaMax) {
            this.trendDeltaMax = trendDeltaMax;
        }

        public Float getTrendDeltaMin() {
            return trendDeltaMin;
        }

        public void setTrendDeltaMin(Float trendDeltaMin) {
            this.trendDeltaMin = trendDeltaMin;
        }

        public Float getTrendDeltaThreshold() {
            return trendDeltaThreshold;
        }

        public void setTrendDeltaThreshold(Float trendDeltaThreshold) {
            this.trendDeltaThreshold = trendDeltaThreshold;
        }

        public List<MacroTrendDelta> getMacroTrendDeltas() {
            return macroTrendDeltas;
        }

        public void setMacroTrendDeltas(List<MacroTrendDelta> macroTrendDeltas) {
            this.macroTrendDeltas = macroTrendDeltas;
        }

    }

    
    class MacroTrendDelta {

        @SerializedName( "analytic_day" )
        private DateTime analyticDay;

        @SerializedName( "macro_trend" )
        private Float macroTrend;

        @SerializedName( "macro_trend_delta" )
        private Float macroTrendDelta;

        public DateTime getAnalyticDay() {
            return analyticDay;
        }

        public void setAnalyticDay(DateTime analyticDay) {
            this.analyticDay = analyticDay;
        }

        public Float getMacroTrend() {
            return macroTrend;
        }

        public void setMacroTrend(Float macroTrend) {
            this.macroTrend = macroTrend;
        }

        public Float getMacroTrendDelta() {
            return macroTrendDelta;
        }

        public void setMacroTrendDelta(Float macroTrendDelta) {
            this.macroTrendDelta = macroTrendDelta;
        }

    }
    
    static class DateTimeDeserializer implements JsonDeserializer<DateTime> {
        @Override
        public DateTime deserialize( JsonElement json, Type typeOfT, JsonDeserializationContext context)  throws JsonParseException {
            return new DateTime( json.getAsString(), DateTimeZone.UTC );
        }
    }
    
    static class DateTimeSerializer implements JsonSerializer<DateTime> {
        @Override
        public JsonElement serialize( DateTime src, Type typeOfSrc, JsonSerializationContext context ) {
            return new JsonPrimitive( new DateTime( src, DateTimeZone.UTC ).toDateTimeISO().toString() );
        }
    }
    
}