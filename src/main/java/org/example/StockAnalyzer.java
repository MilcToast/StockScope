package org.example;

import java.util.ArrayList;
import java.util.List;
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

    public double sharpeRatio(double averageReturn, double stdev, double annualRate) {
        double riskFreeRate = Math.pow(1.0 + (annualRate / 100), 1.0 / 250) - 1;

        return  (averageReturn - riskFreeRate) / stdev;
    }

    public double beta(List<Double> stockReturns, List<Double> marketReturns) {
        int daysInYear = 250;

        double covariance = PortfolioUtilities.computeCovariance(stockReturns, marketReturns, daysInYear);
        double variance = Math.pow(PortfolioUtilities.computeStdev(marketReturns,daysInYear), 2);

        return covariance/variance;
    }

}
