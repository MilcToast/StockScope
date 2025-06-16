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
    public Double calculateEMA(int period) {
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
}
