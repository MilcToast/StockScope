import com.google.gson.*;
import java.net.URI;
import java.net.http.*;
import java.util.*;

public class StockFetcher {
    public List<StockData> fetch(int maxDays, String symbol, String apikey) {

        String url = "https://www.alphavantage.co/query?function=TIME_SERIES_DAILY"
                + "&symbol=" + symbol
                + "&outputsize=full"
                + "&apikey=" + apikey;

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
            for (Map.Entry<String, JsonElement> entry : timeSeries.entrySet()) {
                if (count++ >= maxDays) break;

                String timestamp = entry.getKey();
                JsonObject data = entry.getValue().getAsJsonObject();

                double close = data.get("4. close").getAsDouble();

                entries.add(new StockData(timestamp, close));
            }

            // Sort from Most Recent
            entries.sort(Comparator.comparing(StockData::getTimestamp));

        return entries;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public void printData(List<StockData> entries) {
        for (StockData e : entries) {
                    System.out.println(e);
                }
    }

}
