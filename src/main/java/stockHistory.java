import java.net.URL;
import java.net.HttpURLConnection;
import com.google.gson.*;

public class stockHistory {
    public static void main(String[] args) {

        String apiKey = "C9RGET7L238ZT2QE";
        String symbol = "GOOG";

        String urlString ="https://www.alphavantage.co/query?function=TIME_SERIES_MONTHLY"
                + "&symbol=" + symbol
                + "&apikey=" + apiKey;


        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            Gson gson = new Gson();

            int responseCode = connection.getResponseCode();


        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
