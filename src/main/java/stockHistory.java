import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.google.gson.*;

public class stockHistory {
    public static void main(String[] args) {

        String apiKey = Secret.apiKey;
        String symbol = "GOOG";

        String url ="https://www.alphavantage.co/query?function=TIME_SERIES_MONTHLY"
                + "&symbol=" + symbol
                + "&apikey=" + apiKey;


        try {
            // Create HTTP client
            HttpClient httpClient = HttpClient.newHttpClient();

            // Create HTTP request
            HttpRequest getRequest = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            Gson gson = new Gson();

            HttpResponse<String> response = httpClient.send(getRequest, HttpResponse.BodyHandlers.ofString());
            gson.fromJson(response.body(), Data.class);


        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
