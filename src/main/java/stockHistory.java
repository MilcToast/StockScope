import java.util.*;
import javafx.*;

public class stockHistory {
    public static void main(String[] args) {

        int maxDays = 750;

        String apiKey = Secret.apiKey1;

        Map<String, Double> portfolio = new HashMap<>();
        portfolio.put("GOOG", 0.6);
        portfolio.put("TSLA", 0.4);

        ReturnsBuilder returnsBuilder = new ReturnsBuilder();
        PortfolioUtilities portfolioUtil = new PortfolioUtilities();

        // Make a List of the Log Returns
        Map<String, List<Double>> returns = returnsBuilder.buildReturns(portfolio, maxDays, apiKey);

        // Calculate whatever user wants
        portfolioUtil.computePortfolioStdev(portfolio, returns);


    }
}
