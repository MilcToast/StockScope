package org.example;

import java.util.*;
// import javafx.*;

public class stockHistory {
    public static void main(String[] args) {
        int maxDays = 50;
        String apiKey = Secret.apiKey1;

        HashMap<String, Double> rawPortfolio = new HashMap<>();
        rawPortfolio.put("RDDT", 0.1);
        rawPortfolio.put("GOOG", 0.2);
        rawPortfolio.put("TSLA", 0.8);

        ReturnsBuilder returnsBuilder = new ReturnsBuilder();
        PortfolioUtilities portfolioUtil = new PortfolioUtilities();
        // Make a List of the Log Returns
        HashMap<String,Double> portfolio = portfolioUtil.normalizeWeights(rawPortfolio);
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
