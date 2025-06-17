import java.util.List;

public class PortfolioUtilities {
    public double computeCovariance (List<Double> returnsOne, List<Double> returnsTwo) {
        double averageOne = computeAverage(returnsOne);
        double averageTwo = computeAverage(returnsTwo);

        int n = Math.min(returnsOne.size(), returnsTwo.size());
        double sum = 0;
        for (int i = 0; i < n; i++) {
            sum += (returnsOne.get(i) - averageOne) * (returnsTwo.get(i) - averageTwo);
        }
        double covariance = sum/(n-1);

        return covariance;
    }

    public double computeCorrelation(List<Double> returnsOne, List<Double> returnsTwo) {
        double covariance = computeCovariance(returnsOne, returnsTwo);
        double stdevOne = computeStdev(returnsOne);
        double stdevTwo = computeStdev(returnsTwo);

        double correlation = covariance / (stdevOne * stdevTwo);
        return correlation;
    }

    public static double computeAverage (List<Double> list) {
        double sum = 0;
        for (int i = 0; i < list.size(); i++) {
            sum += list.get(i);
        }
        return sum/list.size();
    }

    public static double computeStdev (List<Double> list) {
        double mean = computeAverage(list);
        double sum = 0;

        for (int i = 0; i < list.size(); i++) {
            sum += Math.pow(list.get(i) - mean, 2);
        }

        return Math.sqrt(sum/(list.size() - 1));
    }

    public double computePortfolioStdev (List<List<Double>> allReturns, List<Double> weights) {
        int n = allReturns.size();
        double variance = 0;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                double covariance = computeCovariance(allReturns.get(i),allReturns.get(j));
                variance += weights.get(i) * weights.get(j) * covariance;
            }
        }
        return Math.sqrt(variance);
    }

}
