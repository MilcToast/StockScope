package org.example;

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

            if (timeSeries == null) {
                System.out.println("Error: Could not fetch Time Series for " + symbol);
                System.out.println("Response:\n" + root);
                return Collections.emptyList(); // or throw exception
            }
            if (root.has("Note")) {
                System.out.println("API limit hit: " + root.get("Note").getAsString());
                return Collections.emptyList();
            }

            // Data for Each Day
            List<String> dates = new ArrayList<>(timeSeries.keySet());
            dates.sort(Comparator.reverseOrder());

            List<StockData> entries = new ArrayList<>();

            int count = 0;
            for (String date : dates) {
                if (count++ >= maxDays) break;

                JsonObject data = timeSeries.getAsJsonObject(date);
                double close = data.get("4. close").getAsDouble();

                entries.add(new StockData(date, close));
            }

            entries.sort(Comparator.comparing(StockData::getTimestamp));

        return entries;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }



}
