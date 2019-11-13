import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class APICall {
    private static final String CLIENT_ID = ""; // TODO: remove client id from source
    private static final String CLIENT_SECRET = ""; // TODO: remove client secret from source
    private static final String BAR_CATEGORY_ID = "4bf58dd8d48988d116941735"; // FourSquare Places API 'Bar' category ID
    private static final String API_URL = "https://api.foursquare.com/v2/venues/search";

    public static void main(String[] args) {
        try {
            PlacesResponse bars1000m = makeAPICall("47.608013", "-122.335167", "1000");
            PlacesResponse bars10m = makeAPICall("47.608013", "-122.335167", "10");
            System.out.println(bars10m);
            for(Venue v : bars1000m.response.venues) {
            	System.out.println("Name: " + v.name);
            	System.out.println("Location: " + v.location);
            }

        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    public static PlacesResponse makeAPICall(String latitude, String longitude, String radius) throws Exception {
        // format URL endpoint according to latitude, longitude, and radius parameters
        URL url = new URL(
                API_URL + "?client_id=" + CLIENT_ID + "&client_secret="
                        + CLIENT_SECRET + "&v=20130815" + "&ll=" + latitude + "," + longitude
                        + "&categoryId=" + BAR_CATEGORY_ID + "&radius=" + radius
        );

        // send HTTP GET request to API endpoint w/ specified parameters
        String result = "";
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String curLine;
        while ((curLine = reader.readLine()) != null) {
            result = result + curLine;
        }
        reader.close();
        Gson gson = new Gson();
        try {
            PlacesResponse response = gson.fromJson(result, PlacesResponse.class);
            return response;
        } catch(Exception e) {
        	System.out.println("Could not parse JSON response");
        	e.printStackTrace();
        	return null;
        }
    }
}
