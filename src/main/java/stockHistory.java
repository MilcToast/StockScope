import java.util.*;

public class stockHistory {
    public static void main(String[] args) {

        int maxDays = 750;

        String apiKey = Secret.apiKey;
        List<String> symbols = Arrays.asList("GOOG", "TSLA");

        for (String symbol : symbols) {
            StockFetcher fetcher = new StockFetcher();

            List<StockData> entries = fetcher.fetch(maxDays, symbol, apiKey);

            StockAnalyzer analyzer = new StockAnalyzer(entries);
            double average = analyzer.simpleMovingAverage(50);
            double stdev = analyzer.rollingStdDev(50);

            System.out.println("Stock symbol: " + symbol);
            System.out.printf("50 day average: %.2f\n", average);
            System.out.printf("50 day stdev: %.2f\n", stdev);
        }

    }
}
