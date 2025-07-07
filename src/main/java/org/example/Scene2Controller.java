package org.example;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class Scene2Controller implements Initializable {

    @FXML
    private VBox bottomContainer;
    @FXML
    private VBox container;
    @FXML
    private Button backButton;
    @FXML
    private Button submitButton;
    @FXML
    private Label averageLabel;
    @FXML
    private Label stdevLabel;
    @FXML
    private TextField sharpeText;

    private final HashMap<TextField, TextField> fieldMap = new HashMap<>();

    private int numberOfTextFields;
    private int maxDays;
    private final PortfolioUtilities portfolioUtil = new PortfolioUtilities();

    public void setNumberTextFields(int selectedCount, int maxDays) {
        this.numberOfTextFields = selectedCount;
        this.maxDays = maxDays;
        generateTextFields();
    }

    @FXML
    public void generateTextFields() {
        container.getChildren().clear();
        fieldMap.clear();

        for (int i = 0; i < numberOfTextFields; i++) {
            TextField textField = new TextField();
            textField.setPromptText("Stock " + (i+1));
            textField.setPrefWidth(200);

            TextField weightField = new TextField();
            weightField.setPromptText("Weight");
            weightField.setPrefWidth(100);

            fieldMap.put(textField, weightField);

            HBox row = new HBox(10);
            row.setPadding(new Insets(10, 0, 0, 20));
            row.getChildren().addAll(textField, weightField);
            container.getChildren().add(row);
        }
    }

    public void backToSceneOne(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("scene1.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void calculateResults() {
        bottomContainer.getChildren().clear();
        HashMap<String, Double> rawPortfolio = new HashMap<>();

        double riskFreeRate = Double.parseDouble(sharpeText.getText());

        for (Map.Entry<TextField, TextField> entry : fieldMap.entrySet()) {
            String stock = entry.getKey().getText().trim();
            double weight = Double.parseDouble(entry.getValue().getText().trim());
            rawPortfolio.put(stock, weight);
        }

        HashMap<String,Double> portfolio = portfolioUtil.normalizeWeights(rawPortfolio);
        HashMap<String, List<Double>> returns = new HashMap<>();

        Set<String> symbols = portfolio.keySet();
        StockFetcher fetcher = new StockFetcher();

        List<StockData> marketEntries = fetcher.fetch(250, "SPY", Secret.apiKey1);
        StockAnalyzer marketAnalyzer = new StockAnalyzer(marketEntries);

        List<Double> marketReturns = marketAnalyzer.percentChanges();

        for (String symbol : symbols) {
            List<StockData> entries = fetcher.fetch(1250, symbol, Secret.apiKey1);
            StockAnalyzer analyzer = new StockAnalyzer(entries);

            Label newLabel = new Label("Metrics for: " + symbol);
            newLabel.setStyle("-fx-font-weight: bold; -fx-padding: 10 0 5 10;");
            bottomContainer.getChildren().add(newLabel);

            List<Double> stockReturns = analyzer.percentChanges();

            System.out.println("Entries: " + entries.size());
            System.out.println("Percent Changes: " + stockReturns.size());

//            for (Double aDouble : stockReturns) {
//                System.out.println("return: " + aDouble);
//            }

            HBox newHorBox = new HBox(10);
            double simplMovAve = analyzer.simpleMovingAverage(maxDays);
            double simplMovStdev = analyzer.rollingStdDev(maxDays);
            double lastClose = entries.get(entries.size() - 1).getClose();
            System.out.println("CALCULATING IN CONTROLLER");
            double priceChangeAvg = PortfolioUtilities.computeAverage(stockReturns, maxDays);
            double priceChangeStdev = PortfolioUtilities.computeStdev(stockReturns, maxDays);
            double sharpeRatio = analyzer.sharpeRatio(priceChangeAvg, priceChangeStdev, riskFreeRate);
            double beta = analyzer.beta(stockReturns, marketReturns);

            String avg = String.format("%.2f", simplMovAve);
            String std = String.format("%.2f", simplMovStdev);
            String lastCloseStr = String.format("%.2f", lastClose);
            String changeAvgStr = String.format("%.2f", priceChangeAvg);
            String changeStdevStr = String.format("%.2f", priceChangeStdev);
            String sharpeRatioStr = String.format("%.2f", sharpeRatio);
            String betaStr = String.format("%.2f", beta);

            newHorBox.getChildren().addAll(new Label(maxDays + "-day Average: " + avg),
                                           new Label(maxDays + "-day Std dev: " + std),
                                           new Label("Last Closing Price: " + lastCloseStr),
                                           new Label(maxDays + "-day Average Daily Change: " + changeAvgStr + "%"),
                                           new Label(maxDays + "-day Daily Change Std dev.: " + changeStdevStr + "%"),
                                           new Label("Sharpe Ratio: " + sharpeRatioStr),
                                           new Label("1-year Beta (Market: SPY): " + betaStr)
                                           );
            bottomContainer.getChildren().add(newHorBox);

            returns.put(symbol, analyzer.percentChanges());
        }

        displayPortfolioAverage(portfolio, returns);
        displayPortfolioSTDEV(portfolio, returns);

    }

    public void displayPortfolioAverage(HashMap<String,Double> portfolio, Map<String, List<Double>> returns) {
        double meanReturn = portfolioUtil.computePortfolioAverageReturn(portfolio, returns, maxDays);
        String meanPercentReturn = String.format("%.2f", meanReturn);
        averageLabel.setText("Your Portfolio Average is: " + meanPercentReturn + "%");
    }

    public void displayPortfolioSTDEV(HashMap<String,Double> portfolio, Map<String, List<Double>> returns) {
        double stdev = portfolioUtil.computePortfolioStdev(portfolio, returns, maxDays);
        String percentstdev = String.format("%.2f", stdev);
        stdevLabel.setText("Your Portfolio Standard Deviation is: " + percentstdev + "%");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
