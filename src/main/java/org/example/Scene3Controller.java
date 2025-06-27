package org.example;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class Scene3Controller implements Initializable {

    @FXML
    private Label averageLabel;
    @FXML
    private Label stdevLabel;

    private HashMap<String, Double> rawPortfolio = new HashMap<>();
    private HashMap<String,Double> portfolio;
    private Map<String, List<Double>> returns;

    private final ReturnsBuilder returnsBuilder = new ReturnsBuilder();
    private final PortfolioUtilities portfolioUtil = new PortfolioUtilities();

    public void setRawPortfolio(HashMap<String, Double> rawPortfolio) {
        this.rawPortfolio = rawPortfolio;
        this.portfolio =  portfolioUtil.normalizeWeights(rawPortfolio);
        this.returns = returnsBuilder.buildReturns(portfolio, 250, Secret.apiKey1);


        displayPortfolioAverage();
        displayPortfolioSTDEV();
    }

    public void displayPortfolioAverage() {
        double meanLogReturn = portfolioUtil.computePorfolioAverageReturn(portfolio, returns);
        String meanPercentReturn = String.format("%.2f", (Math.exp(meanLogReturn) - 1) * 100);
        averageLabel.setText("Your Portfolio Average is: " + meanPercentReturn + "%");
    }

    public void displayPortfolioSTDEV() {
        double logstdev = portfolioUtil.computePortfolioStdev(portfolio, returns);
        String percentstdev = String.format("%.2f", (Math.exp(logstdev) - 1) * 100);
        stdevLabel.setText("Your Portfolio Standard Deviation is: " + percentstdev + "%");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
