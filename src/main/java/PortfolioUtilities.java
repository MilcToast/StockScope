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
