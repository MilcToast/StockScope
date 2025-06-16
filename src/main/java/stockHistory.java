import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import com.google.gson.*;

public class stockHistory {
    public static void main(String[] args) {

        int maxDays = 365;

        String apiKey = Secret.apiKey;
        String symbol = "GOOG";

        String url ="https://www.alphavantage.co/query?function=TIME_SERIES_DAILY"
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

            // Parse JSON using Gson
            Gson gson = new Gson();
            HttpResponse<String> response = httpClient.send(getRequest, HttpResponse.BodyHandlers.ofString());
            JsonObject root = gson.fromJson(response.body(), JsonObject.class);

            JsonObject timeSeries = root.getAsJsonObject("Time Series (Daily)");


            // Data for Each Month
            List<StockData> entries = new ArrayList<>();

            int count = 0;
            for(Map.Entry<String,JsonElement> entry : timeSeries.entrySet()) {
                if (count++ >= maxDays) break;

                String timestamp = entry.getKey();
                JsonObject data = entry.getValue().getAsJsonObject();

                double close = data.get("4. close").getAsDouble();

                entries.add(new StockData(timestamp, close));
            }

            // Sort from Most Recent
            entries.sort(Comparator.comparing(StockData::getTimestamp));


            // Print Entries
            for (StockData e : entries) {
                System.out.println(e);
            }

            StockAnalyzer analyzer = new StockAnalyzer(entries);

            int period = 20;
            Double latestEMA = analyzer.calculateEMA(period);
            System.out.printf("Latest %d-day EMA of returns: %.2f%%\n", period, latestEMA);

        } catch (Exception e) {
            e.printStackTrace();
        }





    }
}
