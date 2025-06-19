import java.util.*;
// import javafx.*;

public class stockHistory {
    public static void main(String[] args) {
        int maxDays = 250;
        String apiKey = Secret.apiKey1;

        Map<String, Double> portfolio = new HashMap<>();
        portfolio.put("RDDT", 0.6);
        portfolio.put("GOOG", 0.4);
        // ADD THING THAT NORMALIZES WEIGHTS

        ReturnsBuilder returnsBuilder = new ReturnsBuilder();
        PortfolioUtilities portfolioUtil = new PortfolioUtilities();

        // Make a List of the Log Returns
        Map<String, List<Double>> returns = returnsBuilder.buildReturns(portfolio, maxDays, apiKey);

        // Calculate whatever user wants
        double meanLogReturn = portfolioUtil.computePorfolioAverageReturn(portfolio, returns);
        double meanPercentReturn = (Math.exp(meanLogReturn) - 1) * 100;
        double logstdev = portfolioUtil.computePortfolioStdev(portfolio, returns);
        double percentstdev = (Math.exp(logstdev) - 1) * 100;

        System.out.printf("Your Portfolio's Mean Return is: %.2f%%\n", meanPercentReturn);
        System.out.printf("Your Portfolio's Standard Deviation is: %.2f%%\n", percentstdev);



    }
}
