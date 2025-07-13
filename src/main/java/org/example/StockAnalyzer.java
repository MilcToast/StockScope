package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class StockAnalyzer {
    private final List<StockData> entries;
    public StockAnalyzer(List<StockData> entries) {this.entries = entries;}

    // Calculate all the Percent Changes
    public List<Double> percentChanges () {
        List<Double> percentChanges = new ArrayList<>();

        for (int i = 1; i < entries.size(); i++) {
            double previousClose = entries.get(i-1).getClose();
            double currentClose = entries.get(i).getClose();
            double percentChange = (currentClose-previousClose)/previousClose * 100;

            percentChanges.add(percentChange);
        }
        return percentChanges;
    }

    // CALCULATE MONTHLY CHANGES

    public List<Double> monthlyPercentChanges() {
        Map<String, Double> lastClosePerMonth = new TreeMap<>();

        for (StockData entry : entries) {
            String date = entry.getTimestamp();
            String yearMonth = date.substring(0, 7);

            lastClosePerMonth.put(yearMonth, entry.getClose());
        }

        List<Double> monthlyCloses = new ArrayList<>(lastClosePerMonth.values());
        List<Double> percentChanges = new ArrayList<>();

        for (int i = 1; i < monthlyCloses.size(); i++) {
            double previousClose = monthlyCloses.get(i-1);
            double currentClose = monthlyCloses.get(i);
            double percentChange = (currentClose - previousClose)/previousClose * 100;

            percentChanges.add(percentChange);
        }

        if (!percentChanges.isEmpty()) {
            percentChanges.remove(percentChanges.size() - 1);
        }

        return percentChanges;
    }

    // SIMPLE AVERAGE
    public double simpleMovingAverage (int period) {
        if (entries.size() < period) return Double.NaN;

        int start = entries.size() - period;
        double average = 0;
        for (int i = start; i < entries.size(); i++) {
            average += entries.get(i).getClose();
        }
        average /= period;

        return average;
    }

    // STANDARD DEVIATION
    public double rollingStdDev (int period) {
        double average = simpleMovingAverage(period);

        int start = entries.size() - period;
        double sum = 0;
        for (int i = start; i < entries.size(); i++) {
            sum += Math.pow(entries.get(i).getClose()- average, 2);
        }

        // Calculate Stdev and return
        return Math.sqrt(sum/(period - 1));
    }

    public double sharpeRatio(List<Double> monthlyChanges, double riskFreeRate) {
        double initialPrice = entries.get(entries.size() - 252).getClose();
        double finalPrice = entries.get(entries.size() - 1).getClose();

        double annualReturn = (finalPrice - initialPrice) / initialPrice * 100;
        double stdev = PortfolioUtilities.computeStdev(monthlyChanges, 12);

        return  (annualReturn - riskFreeRate) / stdev;
    }

    public double beta(List<Double> stockReturns, List<Double> marketReturns) {
        int daysInYear = 250;

        double covariance = PortfolioUtilities.computeCovariance(stockReturns, marketReturns, daysInYear);
        double variance = Math.pow(PortfolioUtilities.computeStdev(marketReturns,daysInYear), 2);

        return covariance/variance;
    }

}
