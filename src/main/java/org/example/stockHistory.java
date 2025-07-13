package org.example;

import java.io.IOException;
import java.util.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class stockHistory {
//    @Override
//    public void start (Stage stage) throws IOException {
//        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("scene1.fxml")));
//        Scene scene = new Scene(root);
//
//        Image icon = new Image("img.png");
//        stage.getIcons().add(icon);
//
//        stage.setScene(scene);
//        stage.show();
//    }

    public static void main(String[] args) {
        int maxDays = 250;
        String apiKey = Secret.apiKey1;

//        HashMap<String, Double> rawPortfolio = new HashMap<>();
//        rawPortfolio.put("RDDT", 1.0);

        StockFetcher fetcher = new StockFetcher();

        List<StockData> entries = fetcher.fetch(250, "RDDT", Secret.apiKey1);

        StockAnalyzer analyzer = new StockAnalyzer(entries);
//
//        ReturnsBuilder returnsBuilder = new ReturnsBuilder()
//        PortfolioUtilities portfolioUtil = new PortfolioUtilities();
//        // Make a List of the Log Returns
//        HashMap<String,Double> portfolio = portfolioUtil.normalizeWeights(rawPortfolio);
//        Map<String, List<Double>> returns = returnsBuilder.buildReturns(portfolio, maxDays, apiKey);
//
//        // Calculate whatever user wants
//        double meanLogReturn = portfolioUtil.computePortfolioAverageReturn(portfolio, returns, maxDays);
//        double meanPercentReturn = (Math.exp(meanLogReturn) - 1) * 100;
//        double logstdev = portfolioUtil.computePortfolioStdev(portfolio, returns, maxDays);
//        double percentstdev = (Math.exp(logstdev) - 1) * 100;
//
//        System.out.printf("Your Portfolio's Mean Return is: %.2f%%\n", meanPercentReturn);
//        System.out.printf("Your Portfolio's Standard Deviation is: %.2f%%\n", percentstdev);

        List<Double> changes = analyzer.monthlyPercentChanges();

        for (int i = 0; i < changes.size(); i++) {
            System.out.printf("Month %d: %.2f%%\n", i + 1, changes.get(i));
        }


//        launch(args);

    }
}
