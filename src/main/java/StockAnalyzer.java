import java.util.ArrayList;
import java.util.List;
public class StockAnalyzer {
    private final List<StockData> entries;

    public StockAnalyzer(List<StockData> entries) {
        this.entries = entries;
    }

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

    // Calculate the Exponential Moving Average (EMA)
    public Double calcChangeEMA (int period) {
        List<Double> changes = percentChanges();
        if (changes.size() == 0) return null;

        double alpha = 2.0/(period + 1);

        // Initial EMA
        double sum = 0;
        for (int i = 0; i < period; i++) {
            sum += changes.get(i);
        }
        double ema = sum/period;

        // Recursive EMA
        for (int i = period; i < changes.size(); i++) {
            double current = changes.get(i);
            ema = (current * alpha) + (ema * (1-alpha));
        }

        // Latest EMA
        return ema;
    }

    public List<Double> logChanges () {
        List<Double> logChanges = new ArrayList<>();

        for (int i = 1; i < entries.size(); i++) {
            double prevClose = entries.get(i-1).getClose();
            double currClose = entries.get(i).getClose();

            if (prevClose > 0) {
                double logChange = Math.log(currClose / prevClose);
                logChanges.add(logChange);
            }
        }

        return logChanges;
    }

    public Double logChangeEMA (int period) {
        List<Double> changes = logChanges();
        if (changes.size() < period) return null;

        double alpha = 2.0/(period + 1);

        // Initial EMA
        double sum = 0;
        for (int i = 0; i < period; i++) {
            sum += changes.get(i);
        }
        double ema = sum/period;

        // Recursive EMA
        for (int i = period; i < changes.size(); i++) {
            double current = changes.get(i);
            ema = (current * alpha) + (ema * (1-alpha));
        }

        // Convert to percentage and return
        return (Math.exp(ema) - 1) * 100;
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

    public double exponentialMovingAverage(int period) {
        double sum = 0;

        // Set up initial Average
        for (int i = 0; i < period; i++) {
              sum += entries.get(i).getClose();
        }
        double average = sum/period;

        // Calculate moving average
        double alpha = 2.0 / (period + 1);
        for (int i = period; i < entries.size(); i++) {
            average = entries.get(i).getClose() * alpha + average * (1 - alpha);
        }

        return average;
    }


}
