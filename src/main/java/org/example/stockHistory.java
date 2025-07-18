package org.example;

import java.util.*;

public class stockHistory {

    public static void main(String[] args) {
        int maxDays = 250;
        String apiKey = Secret.apiKey2;

//        HashMap<String, Double> rawPortfolio = new HashMap<>();
//        rawPortfolio.put("RDDT", 1.0);

        StockFetcher fetcher = new StockFetcher();

        List<StockData> entries = fetcher.fetch(maxDays, "VOO", apiKey);

        StockAnalyzer analyzer = new StockAnalyzer(entries);

        analyzer.printClose();

    }
}
