import java.util.List;
import java.util.Map;

public class PortfolioUtilities {
    public double computeCovariance (List<Double> returnsOne, List<Double> returnsTwo) {
        double averageOne = computeAverage(returnsOne);
        double averageTwo = computeAverage(returnsTwo);

        int n = Math.min(returnsOne.size(), returnsTwo.size());
        double sum = 0;
        for (int i = 0; i < n; i++) {
            sum += (returnsOne.get(i) - averageOne) * (returnsTwo.get(i) - averageTwo);
        }

        return sum/(n-1);
    }

    public double computeCorrelation(List<Double> returnsOne, List<Double> returnsTwo) {
        double covariance = computeCovariance(returnsOne, returnsTwo);
        double stdevOne = computeStdev(returnsOne);
        double stdevTwo = computeStdev(returnsTwo);

        return covariance / (stdevOne * stdevTwo);
    }

    public static double computeAverage (List<Double> list) {
        double sum = 0;
        for (Double aDouble : list) {
            sum += aDouble;
        }
        return sum/list.size();
    }

    public static double computeStdev (List<Double> list) {
        double mean = computeAverage(list);
        double sum = 0;

        for (Double aDouble : list) {
            sum += Math.pow(aDouble - mean, 2);
        }

        return Math.sqrt(sum/(list.size() - 1));
    }

    public double computePortfolioStdev (Map<String, Double> portfolio, Map<String, List<Double>> returns) {
        double variance = 0;

        for (Map.Entry<String,Double> i : portfolio.entrySet()) {
            String symbolI = i.getKey();
            Double weightI = i.getValue();
            List<Double> returnsI = returns.get(symbolI);

            for (Map.Entry<String,Double> j : portfolio.entrySet()) {
                String symbolJ = j.getKey();
                Double weightJ = j.getValue();
                List<Double> returnsJ = returns.get(symbolJ);

                double covariance = computeCovariance(returnsI, returnsJ);
                variance += weightI * weightJ * covariance;
            }
        }
        return Math.sqrt(variance);
    }

    public double computePorfolioAverageReturn (Map<String, Double> portfolio, Map<String, List<Double>> returns) {
        double average = 0;

        for (Map.Entry<String,Double> entry : portfolio.entrySet()) {
            String symbol = entry.getKey();
            Double weight = entry.getValue();

            List<Double> stockReturns = returns.get(symbol);
            average += computeAverage(stockReturns) * weight;

        }
        return average;
    }

}
