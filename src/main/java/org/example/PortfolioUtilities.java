package org.example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PortfolioUtilities {

    public static double computeCovariance (List<Double> returnsOne, List<Double> returnsTwo, int period) {
        System.out.println("THIS IS A CALCULATION FOR COVARIANCE");
        double averageOne = computeAverage(returnsOne, period);
        double averageTwo = computeAverage(returnsTwo, period);

        int start = Math.max(0, returnsOne.size() - period);
        int end = Math.min(returnsOne.size(), returnsTwo.size());

        double sum = 0;
        for (int i = start; i < end; i++) {
//            double value1 = returnsOne.get(i) - averageOne;
//            double value2 = returnsTwo.get(i) - averageTwo;
//            System.out.println("value 1: " + value1 + ", value 2: " + value2);
            sum += (returnsOne.get(i) - averageOne) * (returnsTwo.get(i) - averageTwo);
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
        System.out.println("start: " + start);
        System.out.println("end: " + list.size());

        double sum = 0;
        for (int i = start; i < list.size(); i++) {
            sum += list.get(i);
        }
        return sum/ (list.size() - start);
    }

    public static double computeStdev (List<Double> list, int period) {
        System.out.println("THIS IS A CALCULATION FOR STANDARD DEVIATION");
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
        System.out.println("THIS IS CALCULATING PORTFOLIO AVERAGE RETURN");
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
