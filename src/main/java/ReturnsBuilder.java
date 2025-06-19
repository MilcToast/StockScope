import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ReturnsBuilder {
    // Make a List of the Log Returns
    public HashMap<String, List<Double>> buildReturns(Map<String, Double> portfolio, int maxDays, String apikey) {
        Map<String, List<Double>> returns = new HashMap<>();
        Set<String> symbols = portfolio.keySet();
        StockFetcher fetcher = new StockFetcher();

        for (String symbol : symbols) {
            List<StockData> entries = fetcher.fetch(maxDays, symbol, apikey);
            StockAnalyzer analyzer = new StockAnalyzer(entries);

            returns.put(symbol, analyzer.logChanges());
        }
        return (HashMap<String, List<Double>>) returns;
    }
}
