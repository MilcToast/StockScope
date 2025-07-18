package org.example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PortfolioUtilities {

    public static double computeCovariance (List<Double> returnsOne, List<Double> returnsTwo, int period) {
        int size = Math.min(period, Math.min(returnsOne.size(), returnsTwo.size()));

        int startOne = returnsOne.size() - size;
        int startTwo = returnsTwo.size() - size;

        List<Double> tailOne = returnsOne.subList(startOne, returnsOne.size());
        List<Double> tailTwo = returnsTwo.subList(startTwo, returnsTwo.size());

        double averageOne = computeAverage(tailOne, size);
        double averageTwo = computeAverage(tailTwo, size);

        int start = Math.max(0, returnsOne.size() - period);
        int end = Math.min(returnsOne.size(), returnsTwo.size());

        double sum = 0;
        for (int i = 0; i < size; i++) {
            sum += (tailOne.get(i) - averageOne) * (tailTwo.get(i) - averageTwo);
        }

        return sum/(end - start - 1);
    }

//    public double computeCorrelation(List<Double> returnsOne, List<Double> returnsTwo, int period) {
//        double covariance = computeCovariance(returnsOne, returnsTwo, period);
//        double stdevOne = computeStdev(returnsOne, period);
//        double stdevTwo = computeStdev(returnsTwo, period);
//
//        return covariance / (stdevOne * stdevTwo);
//    }

    public static double computeAverage (List<Double> list, int period) {
        int start = Math.max(0, list.size() - period);

        double sum = 0;
        for (int i = start; i < list.size(); i++) {
            sum += list.get(i);
        }
        return sum/ (list.size() - start);
    }

    public static double computeStdev (List<Double> list, int period) {
        int start = Math.max(0, list.size() - period);
        List<Double> subList = list.subList(start, list.size());
        double mean = computeAverage(subList, subList.size());

        double sum = 0;
        for (double value : subList) {
            sum += Math.pow(value - mean, 2);
        }

        return Math.sqrt(sum/(subList.size() - 1));
    }

    public double computePortfolioStdev (Map<String, Double> portfolio, Map<String, List<Double>> returns, int period) {
        double variance = 0;

        for (Map.Entry<String,Double> i : portfolio.entrySet()) {
            String symbolI = i.getKey();
            Double weightI = i.getValue();
            List<Double> returnsI = returns.get(symbolI);

            for (Map.Entry<String,Double> j : portfolio.entrySet()) {
                String symbolJ = j.getKey();
                Double weightJ = j.getValue();
                List<Double> returnsJ = returns.get(symbolJ);

                double covariance = computeCovariance(returnsI, returnsJ, period);
                variance += weightI * weightJ * covariance;
            }
        }
        return Math.sqrt(variance);
    }

    public double computePortfolioAverageReturn (Map<String, Double> portfolio, Map<String, List<Double>> returns, int period) {
        double average = 0;

        for (Map.Entry<String,Double> entry : portfolio.entrySet()) {
            String symbol = entry.getKey();
            Double weight = entry.getValue();

            List<Double> stockReturns = returns.get(symbol);
            average += computeAverage(stockReturns, period) * weight;

        }
        return average;
    }

    public HashMap<String,Double> normalizeWeights(HashMap<String,Double> rawPortfolio) {
        double total = rawPortfolio.values().stream().mapToDouble(Double :: doubleValue).sum();
        HashMap<String, Double> portfolio = new HashMap<>();
        for (Map.Entry<String,Double> entry : rawPortfolio.entrySet()) {
            portfolio.put(entry.getKey(), entry.getValue() / total);
        }

        return portfolio;
    }

}
